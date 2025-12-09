package pt.ul.fc.css.weatherwise.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.weatherwise.business.dto.WeatherDataDTO;
import pt.ul.fc.css.weatherwise.service.WeatherService;

@RestController
@RequestMapping("/weather/historical")
public class HistoricalWeatherController {
    @Autowired
    private WeatherService weatherService;

    @PutMapping
    public ResponseEntity<?> createOrUpdateHistorical(@RequestBody WeatherDataDTO dto) {
        weatherService.createOrUpdateHistorical(dto);
        return ResponseEntity.ok().build();
    }
}
