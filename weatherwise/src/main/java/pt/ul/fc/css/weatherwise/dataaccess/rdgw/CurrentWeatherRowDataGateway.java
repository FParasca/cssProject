package pt.ul.fc.css.weatherwise.dataaccess.rdgw;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pt.ul.fc.css.weatherwise.business.dto.WeatherDataDTO;
import pt.ul.fc.css.weatherwise.dataaccess.DataSource;
import pt.ul.fc.css.weatherwise.dataaccess.rdgw.exception.PersistenceException;

public class CurrentWeatherRowDataGateway {
    private static final String FIND_BY_LOCATION_NAME = "SELECT p.condicao, p.temperatura, p.data, l.nome FROM previsao p JOIN localidade l ON p.id_localidade = l.id WHERE l.nome = ? AND p.data = CURRENT_DATE";

    public CurrentWeatherRowDataGateway() {}
    public WeatherDataDTO findByLocationName(String locationName) throws PersistenceException {
        try (PreparedStatement statement = DataSource.INSTANCE.prepare(FIND_BY_LOCATION_NAME)) {
            statement.setString(1, locationName);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    WeatherDataDTO dto = new WeatherDataDTO();

                    dto.setLocationName(rs.getString("nome"));
                    dto.setCondition(rs.getString("condicao"));
                    dto.setTemperature(rs.getDouble("temperatura"));
                    dto.setTimestamp(rs.getDate("data").toLocalDate().atStartOfDay());
                    return dto;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Erro ao procurar previsão para " + locationName, e);
        }
    }
}