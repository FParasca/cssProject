package pt.ul.fc.css.weatherwise.business;

import pt.ul.fc.css.weatherwise.dataaccess.rdgw.HistoricalWeatherRowDataGateway;
import pt.ul.fc.css.weatherwise.dataaccess.rdgw.exception.PersistenceException;
import pt.ul.fc.css.weatherwise.business.dto.WeatherDataDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class PredictWeatherScript {
    private final HistoricalWeatherRowDataGateway gateway;

    public PredictWeatherScript(HistoricalWeatherRowDataGateway gateway) {
        this.gateway = gateway;
    }

    public String predictWeather(String location,LocalDate date) throws PersistenceException {
        List<WeatherDataDTO> weatherList = new ArrayList<>(gateway.findByLocationSince(location,date));

        int t = 0;
        int c = 0;
        int s = 0;

        for (WeatherDataDTO weatherDataDTO : weatherList) {
            String weather = weatherDataDTO.getCondition();
            switch (weather) {
                case "Sol":
                    s += 1;
                    break;
                case "Chuva":
                    c += 1;
                    break;
                case "Trovoada":
                    t += 1;
                    break;
                default:
                    System.out.println("Weather unrecognized");
            }
        }
        if (s >= c && s >= t) {
            return "Sol";
        } else if (c >= s && c >= t) {
            return "Chuva";
        } else {
            return "Trovoada";
        }
    }


}
