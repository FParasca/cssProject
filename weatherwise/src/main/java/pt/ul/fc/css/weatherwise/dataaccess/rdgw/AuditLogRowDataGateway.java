package pt.ul.fc.css.weatherwise.dataaccess.rdgw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import pt.ul.fc.css.weatherwise.dataaccess.DataSource;
import pt.ul.fc.css.weatherwise.dataaccess.exception.PersistenceException;
import pt.ul.fc.css.weatherwise.business.dto.AuditLogDTO;


public class AuditLogRowDataGateway {

    private static final String AUDIT_LOG_SQL_FIND_TOP_USER_BY_LOCATION = "SELECT u.nome AS top_user_name, COUNT(c.id) AS total_consultas FROM consulta c JOIN utilizador u ON c.id_utilizador = u.id JOIN localidade l ON c.id_localidade = l.id WHERE l.nome = ? GROUP BY u.nome ORDER BY total_consultas DESC LIMIT 1";    private static final String AUDIT_LOG_SQL_INSERT = "INSERT INTO consulta (id_utilizador, id_localidade, timestamp) VALUES ((SELECT id FROM utilizador WHERE nome = ?), (SELECT id FROM localidade WHERE nome = ?), ?)";
    private static final String AUDIT_LOG_SQL_FIND_ALL = "SELECT u.nome AS nome_utilizador, l.nome AS nome_localidade, c.timestamp FROM consulta c JOIN utilizador u ON c.id_utilizador = u.id JOIN localidade l ON c.id_localidade = l.id";
    private static final String AUDIT_LOG_SQL_FIND_COUNT_BY_LOCATION = "SELECT COUNT(c.id) AS total_consultas FROM consulta c JOIN localidade l ON c.id_localidade = l.id WHERE l.nome = ?";
    public AuditLogRowDataGateway(){}

    public void insert(AuditLogDTO log) throws PersistenceException {
        try (PreparedStatement statement = DataSource.INSTANCE.prepare(AUDIT_LOG_SQL_INSERT)) {
            statement.setString(1, log.getAuthor());
            statement.setString(2, log.getLocationName());
            if (log.getTimestamp() == null) {

                statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            } else {
                statement.setTimestamp(3, Timestamp.valueOf(log.getTimestamp()));

            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException("", e);
        }

    }

    public List<AuditLogDTO> findAll() throws PersistenceException{
        List<AuditLogDTO> logs = new ArrayList<>();

        try(PreparedStatement statement = DataSource.INSTANCE.prepare(AUDIT_LOG_SQL_FIND_ALL)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    AuditLogDTO dto = new AuditLogDTO();
                    dto.setAuthor(rs.getString("nome_utilizador"));
                    dto.setLocationName(rs.getString("nome_localidade"));
                    dto.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                    logs.add(dto);
                }
            }
        }catch (SQLException e){
            throw new PersistenceException("", e);
        }

        return logs;
    }

    public int getLogsByLocation (String localidade) throws PersistenceException{
        try (PreparedStatement statement = DataSource.INSTANCE.prepare(AUDIT_LOG_SQL_FIND_COUNT_BY_LOCATION)){
            statement.setString(1, localidade);
            try (ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                    return rs.getInt("total_consultas");
                } else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("", e);
        }
    }

    public static String getTopUserByLocation(String localidade) throws PersistenceException {
        try (PreparedStatement statement = DataSource.INSTANCE.prepare(AUDIT_LOG_SQL_FIND_TOP_USER_BY_LOCATION)){
            statement.setString(1, localidade);
            try (ResultSet rs = statement.executeQuery()){
                if (rs.next()){
                    return rs.getString("top_user_name");
                } else {
                    return "No users found";
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error searching for the user", e);
        }

    }
}
