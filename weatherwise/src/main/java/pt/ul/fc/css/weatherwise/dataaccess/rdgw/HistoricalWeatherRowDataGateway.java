package pt.ul.fc.css.weatherwise.dataaccess.rdgw;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import pt.ul.fc.css.weatherwise.business.dto.WeatherDataDTO;
import pt.ul.fc.css.weatherwise.dataaccess.DataSource;
import pt.ul.fc.css.weatherwise.dataaccess.exception.PersistenceException;

public class HistoricalWeatherRowDataGateway {
    private static final String HISTORICAL_WEATHER_SQL_INSERT = "INSERT INTO previsao (id_localidade, data, temperatura, condicao) VALUES ((SELECT id FROM localidade WHERE nome = ?), ?, ?, ?)";
    private static final String HISTORICAL_WEATHER_SQL_UPDATE = "UPDATE previsao SET temperatura = ?, condicao = ? WHERE id_localidade = (SELECT id FROM localidade WHERE nome = ?) AND data = ?";
    private static final String HISTORICAL_WEATHER_SQL_FIND_BY_LOCATION_SINCE = "SELECT l.nome, p.condicao, p.temperatura, p.data FROM previsao p JOIN localidade l ON p.id_localidade = l.id WHERE l.nome = ? AND p.data >= ? ORDER BY data DESC";


    public HistoricalWeatherRowDataGateway() {
    }

    public void insert(WeatherDataDTO wdto) throws PersistenceException {
        try (PreparedStatement statement = DataSource.INSTANCE.prepare(HISTORICAL_WEATHER_SQL_INSERT)) {

            statement.setString(1, wdto.getLocationName());
            statement.setDate(2, java.sql.Date.valueOf(wdto.getTimestamp().toLocalDate()));
            statement.setDouble(3, wdto.getTemperature());
            statement.setString(4, wdto.getCondition());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException("", e);
        }

    }

    public void update(WeatherDataDTO wdto) throws PersistenceException {
        try (PreparedStatement statement = DataSource.INSTANCE.prepare(HISTORICAL_WEATHER_SQL_UPDATE)) {

            statement.setDouble(1, wdto.getTemperature());
            statement.setString(2, wdto.getCondition());
            statement.setString(3, wdto.getLocationName());
            statement.setDate(4, java.sql.Date.valueOf(wdto.getTimestamp().toLocalDate()));

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException("", e);
        }
    }

    public List<WeatherDataDTO> findByLocationSince(String locationName, LocalDate sinceDate) throws PersistenceException {
        List<WeatherDataDTO> results = new ArrayList<>();
        try (PreparedStatement statement = DataSource.INSTANCE.prepare(HISTORICAL_WEATHER_SQL_FIND_BY_LOCATION_SINCE)) {
            statement.setString(1, locationName);
            statement.setDate(2, java.sql.Date.valueOf(sinceDate));
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    WeatherDataDTO dto = new WeatherDataDTO();
                    dto.setLocationName(rs.getString("nome"));
                    dto.setCondition(rs.getString("condicao"));
                    dto.setTemperature(rs.getDouble("temperatura"));
                    dto.setTimestamp(rs.getDate("data").toLocalDate().atStartOfDay());
                    results.add(dto);
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("", e);
        }

        return results;
    }
}










