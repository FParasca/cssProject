package pt.ul.fc.css.weatherwise.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.weatherwise.business.dto.WeatherDataDTO;
import pt.ul.fc.css.weatherwise.service.WeatherService;

@RestController
@RequestMapping("/weather")
public class WeatherWiseController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/current")
    public ResponseEntity<WeatherDataDTO> getCurrentWeather(@RequestParam String location, @RequestParam String user) {
        WeatherDataDTO dto = weatherService.getCurrentWeather(location, user);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }
}
