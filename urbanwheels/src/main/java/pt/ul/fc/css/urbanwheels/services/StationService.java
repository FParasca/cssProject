package pt.ul.fc.css.urbanwheels.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.urbanwheels.dto.BikeDTO;
import pt.ul.fc.css.urbanwheels.dto.StationDTO;
import pt.ul.fc.css.urbanwheels.entities.Bike;
import pt.ul.fc.css.urbanwheels.entities.Station;
import pt.ul.fc.css.urbanwheels.repository.StationRepository;
import pt.ul.fc.css.urbanwheels.repository.TripRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private TripRepository tripRepository;

    @Transactional
    public StationDTO createStation(StationDTO stationDTO) {
        Station station = new Station();
        station.setName(stationDTO.getName());
        station.setLatitude(stationDTO.getLatitude());
        station.setLongitude(stationDTO.getLongitude());
        station.setCapacity(stationDTO.getCapacity());

        Station savedStation = stationRepository.save(station);
        return mapToDto(savedStation);
    }

    public List<StationDTO> getAllStations() {
        return stationRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public StationDTO getStationDetails(String name) {
        List<Station> stations = stationRepository.findByNameContainingIgnoreCase(name);
        if (stations.isEmpty()) {
            return null;
        }
        // Retorna a primeira estação encontrada (ou pode retornar lista se necessário)
        return mapToDto(stations.get(0));
    }

    @Transactional(readOnly = true)
    public List<StationDTO> getStationsWithMostBikes() {
        List<Station> stations = stationRepository.findStationsWithMostBikes();
        return stations.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    private StationDTO mapToDto(Station station) {
        StationDTO dto = new StationDTO();
        dto.setId(station.getId());
        dto.setName(station.getName());
        dto.setLatitude(station.getLatitude());
        dto.setLongitude(station.getLongitude());
        dto.setCapacity(station.getCapacity());

        List<BikeDTO> bikeDTOs = new ArrayList<>();
        if (station.getBikesAssociated() != null) {
            bikeDTOs = station.getBikesAssociated().stream()
                    .map(this::mapBikeToDto)
                    .collect(Collectors.toList());
        }
        dto.setBikesAssociated(bikeDTOs);
        return dto;
    }
    @Transactional(readOnly = true)
    public StationDTO getMostUsedStationByUser(String email) {
        List<Station> startStations = tripRepository.findTopStartStationsByUser(email);
        List<Station> endStations = tripRepository.findTopEndStationsByUser(email);
    
        Station mostUsed = null;
        long startCount = startStations.isEmpty() ? 0
            : tripRepository.countByStartStationAndUser(startStations.get(0).getId(), email);
        long endCount = endStations.isEmpty() ? 0
            : tripRepository.countByEndStationAndUser(endStations.get(0).getId(), email);
    
        if (startCount >= endCount && !startStations.isEmpty()) {
            mostUsed = startStations.get(0);
        } else if (!endStations.isEmpty()) {
            mostUsed = endStations.get(0);
        }
        if (mostUsed == null) return null;
        return mapToDto(mostUsed);
    }

    private BikeDTO mapBikeToDto(Bike bike) {
        BikeDTO dto = new BikeDTO();
        dto.setId(bike.getId());
        dto.setModelo(bike.getModelo());
        dto.setBikeStatus(bike.getStatus().name());

        if (bike.getCurrentStation() != null) {
            dto.setCurrentStationId(bike.getCurrentStation().getId());
        }


        return dto;
    }
}