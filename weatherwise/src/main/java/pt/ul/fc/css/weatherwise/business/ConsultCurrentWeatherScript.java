package pt.ul.fc.css.weatherwise.business;

import pt.ul.fc.css.weatherwise.business.dto.AuditLogDTO;
import pt.ul.fc.css.weatherwise.business.dto.WeatherDataDTO;
import pt.ul.fc.css.weatherwise.business.exception.ApplicationException;
import pt.ul.fc.css.weatherwise.dataaccess.rdgw.AuditLogRowDataGateway;
import pt.ul.fc.css.weatherwise.dataaccess.rdgw.CurrentWeatherRowDataGateway;
import pt.ul.fc.css.weatherwise.dataaccess.exception.RecordNotFoundException;
import pt.ul.fc.css.weatherwise.dataaccess.exception.PersistenceException;


import java.time.LocalDateTime;


public class ConsultCurrentWeatherScript {
    private final CurrentWeatherRowDataGateway currentWeatherGateway;
    private final AuditLogRowDataGateway auditLogGateway;


    public ConsultCurrentWeatherScript(CurrentWeatherRowDataGateway currentWeatherGateway, AuditLogRowDataGateway auditLogGateway) {
        this.currentWeatherGateway = currentWeatherGateway;
        this.auditLogGateway = auditLogGateway;
    }

    public WeatherDataDTO consultCurrentWeather(String nomeLocalidade, String nomeUtilizador) throws ApplicationException{

        if (!isFilled(nomeUtilizador) || !isFilled(nomeLocalidade)){
            throw new ApplicationException("Location and user name must be filled.");
        }

        try {
            WeatherDataDTO forecast = currentWeatherGateway.findByLocationName(nomeLocalidade);
            if (forecast == null){
                throw new RecordNotFoundException("Location or Forecast not found to: " + nomeLocalidade);
            }

            AuditLogDTO logConsulta = new AuditLogDTO(nomeUtilizador, nomeLocalidade, LocalDateTime.now());

            auditLogGateway.insert(logConsulta);
            forecast.setTimestamp(logConsulta.getTimestamp());
            return forecast;

        } catch (RecordNotFoundException e) {
            throw new ApplicationException(e.getMessage(), e);
        } catch (PersistenceException e) {
            throw new ApplicationException("Error on database during search or log", e);
        }

    }
    private boolean isFilled(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
