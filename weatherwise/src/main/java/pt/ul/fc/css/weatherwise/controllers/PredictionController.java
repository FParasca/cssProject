package pt.ul.fc.css.weatherwise.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.weatherwise.business.dto.WeatherDataDTO;
import pt.ul.fc.css.weatherwise.service.WeatherService;

@RestController
@RequestMapping("/weather")
public class PredictionController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/predict")
    public ResponseEntity<WeatherDataDTO> predictWeather(@RequestParam String location, @RequestParam String date) {
        return ResponseEntity.ok(weatherService.predictWeather(location, date));
    }
}
