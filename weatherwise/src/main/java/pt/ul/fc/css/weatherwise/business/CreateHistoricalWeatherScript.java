package pt.ul.fc.css.weatherwise.business;

import pt.ul.fc.css.weatherwise.dataaccess.rdgw.HistoricalWeatherRowDataGateway;
import pt.ul.fc.css.weatherwise.dataaccess.exception.PersistenceException;
import pt.ul.fc.css.weatherwise.business.dto.WeatherDataDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreateHistoricalWeatherScript {
    private final HistoricalWeatherRowDataGateway gateway;

    public CreateHistoricalWeatherScript(HistoricalWeatherRowDataGateway gateway) {
        this.gateway = gateway;
    }


    public void createWeather(String locationName, String condition, double temperature, LocalDate date) throws IllegalArgumentException {

        if (locationName == null || locationName.trim().isEmpty()) {
            throw new IllegalArgumentException("Região inválida");
        }
        if (condition == null || condition.trim().isEmpty()) {
            throw new IllegalArgumentException("Condição inválida");
        }
        if (date == null) {
            throw new IllegalArgumentException("Data inválida");
        }
        if (temperature < -100 || temperature > 100) {
            throw new IllegalArgumentException("Temperatura fora do intervalo permitido (-100 a 100)");
        }
        LocalDateTime timestamp = date.atStartOfDay();
        WeatherDataDTO dto = new WeatherDataDTO(locationName, condition, temperature, timestamp);

        try {
            gateway.insert(dto);
        } catch (PersistenceException eInsert) {
            try {
                gateway.update(dto);
            } catch (PersistenceException eUpdate) {
                throw new RuntimeException();
            }
        }
    }
}