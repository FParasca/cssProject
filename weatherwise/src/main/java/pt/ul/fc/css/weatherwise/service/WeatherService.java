package pt.ul.fc.css.weatherwise.service;

import org.springframework.stereotype.Service;
import pt.ul.fc.css.weatherwise.business.dto.WeatherDataDTO;
import pt.ul.fc.css.weatherwise.business.ConsultCurrentWeatherScript;
import pt.ul.fc.css.weatherwise.business.CreateHistoricalWeatherScript;
import pt.ul.fc.css.weatherwise.business.PredictWeatherScript;
import pt.ul.fc.css.weatherwise.dataaccess.rdgw.CurrentWeatherRowDataGateway;
import pt.ul.fc.css.weatherwise.dataaccess.rdgw.AuditLogRowDataGateway;
import pt.ul.fc.css.weatherwise.dataaccess.rdgw.HistoricalWeatherRowDataGateway;
import pt.ul.fc.css.weatherwise.business.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import pt.ul.fc.css.weatherwise.dataaccess.exception.PersistenceException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;

@Service
public class WeatherService {

    @Autowired
    private CurrentWeatherRowDataGateway currentWeatherRowDataGateway;

    @Autowired
    private AuditLogRowDataGateway auditLogRowDataGateway;

    @Autowired
    private HistoricalWeatherRowDataGateway historicalWeatherRowDataGateway;

    public WeatherDataDTO predictWeather(String location, String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            PredictWeatherScript script = new PredictWeatherScript(historicalWeatherRowDataGateway);
            String predictedCondition = script.predictWeather(location, localDate);
            WeatherDataDTO dto = new WeatherDataDTO();
            dto.setLocationName(location);
            dto.setCondition(predictedCondition);
            dto.setTimestamp(LocalDateTime.now());
            dto.setTemperature(Double.NaN);
            return dto;
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Invalid date format. Expected yyyy-MM-dd.", e);
        } catch (PersistenceException e) {
            throw new RuntimeException("Error predicting weather", e);
        }
    }

    public void createOrUpdateHistorical(WeatherDataDTO dto) {
        CreateHistoricalWeatherScript script = new CreateHistoricalWeatherScript(historicalWeatherRowDataGateway);
        script.createWeather(
                dto.getLocationName(),
                dto.getCondition(),
                dto.getTemperature(),
                dto.getTimestamp().toLocalDate()
        );
    }

    public WeatherDataDTO getCurrentWeather(String location, String user) {
        ConsultCurrentWeatherScript script = new ConsultCurrentWeatherScript(currentWeatherRowDataGateway, auditLogRowDataGateway);
        try {
            return script.consultCurrentWeather(location, user);
        } catch (ApplicationException e) {
            throw new RuntimeException("Error fetching current weather", e);
        }
    }

}
