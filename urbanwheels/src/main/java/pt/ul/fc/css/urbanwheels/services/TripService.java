package pt.ul.fc.css.urbanwheels.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.urbanwheels.dto.TripDTO;
import pt.ul.fc.css.urbanwheels.entities.Bike;
import pt.ul.fc.css.urbanwheels.entities.BikeStatus;
import pt.ul.fc.css.urbanwheels.entities.Station;
import pt.ul.fc.css.urbanwheels.entities.Trip;
import pt.ul.fc.css.urbanwheels.entities.Client;
import pt.ul.fc.css.urbanwheels.repository.BikeRepository;
import pt.ul.fc.css.urbanwheels.repository.StationRepository;
import pt.ul.fc.css.urbanwheels.repository.TripRepository;
import pt.ul.fc.css.urbanwheels.repository.ClientRepository;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private BikeRepository bikeRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public TripDTO takeBike(Long bikeId, Long stationId, Long clientId) {

        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new IllegalArgumentException("Estação não encontrada com ID: " + stationId));

        Bike bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new IllegalArgumentException("Bicicleta não encontrada com ID: " + bikeId));

        if (bike.getCurrentStation() == null || !bike.getCurrentStation().getId().equals(stationId)) {
            throw new IllegalArgumentException("A bicicleta não está na estação indicada.");
        }
        if (bike.getStatus() != BikeStatus.AVAILABLE) {
            throw new IllegalStateException("A bicicleta não está disponível para uso.");
        }

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + clientId));

        LocalDateTime now = LocalDateTime.now();

        Trip newTrip = new Trip(bike, client, station, now);

        bike.setCurrentStation(null);
        bike.setStatus(BikeStatus.BEING_USED);
        station.getBikesAssociated().remove(bike);

        tripRepository.save(newTrip);
        bikeRepository.save(bike);
        stationRepository.save(station);

        return mapToDto(newTrip);
    }

    @Transactional
    public TripDTO returnBike(Long bikeId, Long stationId) {

        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new IllegalArgumentException("Estação não encontrada com ID: " + stationId));

        if (station.getBikesAssociated().size() >= station.getCapacity()) {
            throw new IllegalArgumentException("A estação " + station.getName() + " está cheia.");
        }

        Bike bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new IllegalArgumentException("Bicicleta não encontrada com ID: " + bikeId));

        if (bike.getCurrentStation() != null) {
            throw new IllegalArgumentException("A bicicleta já se encontra estacionada na estação: " + bike.getCurrentStation().getName());
        }

        Trip activeTrip = tripRepository.findByBikeIdAndEndTimeIsNull(bikeId)
                .orElseThrow(() -> new IllegalArgumentException("Não existe viagem ativa para esta bicicleta."));

        LocalDateTime now = LocalDateTime.now();
        activeTrip.setEndStation(station);
        activeTrip.setEndTime(now);
        bike.setCurrentStation(station);
        bike.setStatus(BikeStatus.AVAILABLE);
        station.getBikesAssociated().add(bike);
        tripRepository.save(activeTrip);
        bikeRepository.save(bike);
        stationRepository.save(station);
        return mapToDto(activeTrip);
    }
    private TripDTO mapToDto(Trip trip) {
        return new TripDTO(
                trip.getId(),
                trip.getStartTime(),
                trip.getEndTime() != null ? trip.getEndTime() : null,
                trip.getBike().getModelo(),
                trip.getStartStation().getName(),
                trip.getEndStation() != null ? trip.getEndStation().getName() : null
        );
    }

    @Transactional(readOnly = true)
    public List<TripDTO> getTripsByRoute(Long startStationId, Long endStationId) {
        List<Trip> trips = tripRepository.findByStartStationIdAndEndStationId(startStationId, endStationId);

        return trips.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}