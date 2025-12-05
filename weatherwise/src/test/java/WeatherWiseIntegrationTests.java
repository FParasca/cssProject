import org.junit.jupiter.api.*;
import pt.ul.fc.css.weatherwise.business.*;
import pt.ul.fc.css.weatherwise.business.exception.ApplicationException;
import pt.ul.fc.css.weatherwise.dataaccess.DataSource;
import pt.ul.fc.css.weatherwise.dataaccess.rdgw.*;
import pt.ul.fc.css.weatherwise.dataaccess.rdgw.exception.PersistenceException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WeatherWiseIntegrationTests {

    private static final String SQL_SCHEMA =
            "DROP TABLE IF EXISTS consulta;" +
                    "DROP TABLE IF EXISTS previsao;" +
                    "DROP TABLE IF EXISTS localidade;" +
                    "DROP TABLE IF EXISTS utilizador;" +
                    "CREATE TABLE IF NOT EXISTS utilizador(" +
                    "    id SERIAL PRIMARY KEY," +
                    "    nome VARCHAR(50)" +
                    ");" +
                    "CREATE TABLE IF NOT EXISTS localidade (" +
                    "    id SERIAL PRIMARY KEY," +
                    "    nome VARCHAR(50) UNIQUE NOT NULL" +
                    ");" +
                    "CREATE TABLE IF NOT EXISTS previsao (" +
                    "    id_localidade INT," +
                    "    data DATE NOT NULL," +
                    "    temperatura DOUBLE PRECISION," +
                    "    condicao VARCHAR(20)," +
                    "    FOREIGN KEY (id_localidade) REFERENCES localidade(id)" +
                    ");" +
                    "CREATE TABLE IF NOT EXISTS consulta (" +
                    "    id SERIAL PRIMARY KEY," +
                    "    id_utilizador INT," +
                    "    id_localidade INT," +
                    "    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                    "    FOREIGN KEY (id_utilizador) REFERENCES utilizador (id)," +
                    "    FOREIGN KEY (id_localidade) REFERENCES localidade(id)" +
                    ");";


    private static final String SQL_DATA =
            "INSERT INTO LOCALIDADE (nome) VALUES " +
                    "    ('Lisboa'), ('Porto'), ('Braga'), ('Faro'), ('Coimbra');" +
                    "INSERT INTO utilizador (nome) VALUES " +
                    "    ('Ana Silva'), ('Bruno Costa'), ('Carla Dias');" +

                    "INSERT INTO previsao (id_localidade, data, temperatura, condicao) VALUES " +
                    "    (1, CURRENT_DATE, 22.5, 'Sol')," +
                    "    (1, DATEADD('DAY', -1, CURRENT_DATE), 21.0, 'Nuvens')," +
                    "    (2, CURRENT_DATE, 18.0, 'Chuva')," +
                    "    (2, DATEADD('DAY', -1, CURRENT_DATE), 17.5, 'Nuvens')," +
                    "    (4, DATEADD('DAY', -2, CURRENT_DATE), 20.0, 'Chuva')," +
                    "    (4, CURRENT_DATE, 25.0, 'Chuva')," +
                    "    (4, DATEADD('DAY', -3, CURRENT_DATE), 19.0, 'Chuva')," +
                    "    (4, DATEADD('DAY', -4, CURRENT_DATE), 22.0, 'Sol')," + // Nova entrada
                    "    (4, DATEADD('DAY', -5, CURRENT_DATE), 21.0, 'Trovoada')," + // Nova entrada
                    "    (5, DATEADD('DAY', -5, CURRENT_DATE), 15.0, 'Chuva')," +
                    "    (5, DATEADD('DAY', -10, CURRENT_DATE), 19.0, 'Sol')," +
                    "    (5, DATEADD('DAY', -15, CURRENT_DATE), 14.5, 'Chuva')," +
                    "    (5, DATEADD('DAY', -20, CURRENT_DATE), 16.0, 'Trovoada');" +

                    "INSERT INTO consulta (id_utilizador, id_localidade, timestamp) VALUES " +
                    "    (1, 1, TIMESTAMPADD('HOUR', -2, CURRENT_TIMESTAMP))," +
                    "    (1, 2, TIMESTAMPADD('HOUR', -1, CURRENT_TIMESTAMP))," +
                    "    (2, 1, TIMESTAMPADD('MINUTE', -30, CURRENT_TIMESTAMP))," +
                    "    (1, 1, TIMESTAMPADD('MINUTE', -15, CURRENT_TIMESTAMP))," +
                    "    (2, 3, TIMESTAMPADD('DAY', -1, CURRENT_TIMESTAMP))," +
                    "    (3, 3, TIMESTAMPADD('DAY', -2, CURRENT_TIMESTAMP))," +
                    "    (2, 3, TIMESTAMPADD('DAY', -3, CURRENT_TIMESTAMP))," +
                    "    (1, 3, TIMESTAMPADD('DAY', -4, CURRENT_TIMESTAMP))," +
                    "    (3, 3, TIMESTAMPADD('DAY', -5, CURRENT_TIMESTAMP))," +
                    "    (2, 3, TIMESTAMPADD('DAY', -6, CURRENT_TIMESTAMP))," +
                    "    (3, 5, TIMESTAMPADD('DAY', -1, CURRENT_TIMESTAMP));";


    private final AuditLogRowDataGateway auditLogGateway = new AuditLogRowDataGateway();
    private final HistoricalWeatherRowDataGateway historicalLogGateway = new HistoricalWeatherRowDataGateway();
    private final CurrentWeatherRowDataGateway currentWeatherGateway = new CurrentWeatherRowDataGateway();

    private ConsultCurrentWeatherScript consultCurrentWeatherScript;
    private CountLogsByLocationScript countLogsByLocationScript;
    private CreateHistoricalWeatherScript createHistoricalWeatherScript;
    private FindTopUserByLocationScript findTopUserByLocationScript;
    private GetAuditHistoryScript getAuditHistoryScript;
    private PredictWeatherScript predictWeatherScript;

    private int initialAuditCount;

    @BeforeAll
    public void connectToDatabase() throws PersistenceException {
        DataSource.INSTANCE.connect();
    }

    @AfterAll
    public void closeDatabaseConnection() {
        DataSource.INSTANCE.close();
    }

    private void executeSqlScript(Statement stmt, String sql) throws SQLException {
        for (String command : sql.split(";")) {
            if (!command.trim().isEmpty()) {
                stmt.execute(command);
            }
        }
    }

    @BeforeEach
    public void setUp() throws Exception {

        Connection conn = DataSource.INSTANCE.getConnection();

        try (Statement stmt = conn.createStatement()) {
            executeSqlScript(stmt, SQL_SCHEMA);
            executeSqlScript(stmt, SQL_DATA);
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao inicializar a base de dados de teste", e);
        }

        this.consultCurrentWeatherScript = new ConsultCurrentWeatherScript(currentWeatherGateway, auditLogGateway);
        this.countLogsByLocationScript = new CountLogsByLocationScript(auditLogGateway);
        this.findTopUserByLocationScript = new FindTopUserByLocationScript(auditLogGateway);
        this.predictWeatherScript = new PredictWeatherScript(historicalLogGateway);

        this.initialAuditCount = auditLogGateway.findAll().size();
    }

    @Test
    @DisplayName("Testa localidade com 1 consulta (Coimbra)")
    void testFindTopUserByLocation_Coimbra_FindsTopUser() throws ApplicationException {
        String topUser = findTopUserByLocationScript.findTopUserByLocation("Coimbra");
        assertEquals("Carla Dias", topUser);
    }

    @Test
    @DisplayName("Testa top user de Lisboa (Ana Silva)")
    void testFindTopUserByLocation_Lisboa() throws ApplicationException {
        String topUser = findTopUserByLocationScript.findTopUserByLocation("Lisboa");
        assertEquals("Ana Silva", topUser);
    }

    @Test
    @DisplayName("Testa top user de Braga (Bruno Costa)")
    void testFindTopUserByLocation_Braga_FindsTopUser() throws ApplicationException {
        String topUser = findTopUserByLocationScript.findTopUserByLocation("Braga");
        assertEquals("Bruno Costa", topUser);
    }

    @Test
    @DisplayName("Testa previsão para Faro (Chuva)")
    void testPredictWeather_Faro_ReturnsRain() throws PersistenceException {
        LocalDate sinceDate = LocalDate.now().minusDays(15);
        String prediction = predictWeatherScript.predictWeather("Faro", sinceDate);
        assertEquals("Chuva", prediction);
    }

    @Test
    @DisplayName("Testa previsão para Coimbra com dados (Chuva)")
    void testPredictWeather_Coimbra_WithData_ReturnsRain() throws PersistenceException {
        LocalDate sinceDate = LocalDate.now().minusMonths(1);
        String prediction = predictWeatherScript.predictWeather("Coimbra", sinceDate);
        assertEquals("Chuva", prediction);
    }
}