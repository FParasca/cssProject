
package pt.ul.fc.css.urbanwheels.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.urbanwheels.dto.BikeDTO;
import pt.ul.fc.css.urbanwheels.dto.MaintenanceDTO;
import pt.ul.fc.css.urbanwheels.entities.*;
import pt.ul.fc.css.urbanwheels.repository.BikeRepository;
import pt.ul.fc.css.urbanwheels.repository.StationRepository;
import pt.ul.fc.css.urbanwheels.repository.AdminRepository;
import pt.ul.fc.css.urbanwheels.repository.MaintenanceRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BikeService {

    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private MaintenanceRepository maintenanceRepository;

    private BikeDTO mapBiketoDto(Bike bike){
        BikeDTO bikeDTO = new BikeDTO();
        bikeDTO.setId(bike.getId());
        bikeDTO.setModelo(bike.getModelo());
        bikeDTO.setBikeStatus(bike.getStatus().name());

        Station currentStation = bike.getCurrentStation();
        if (currentStation != null){
            bikeDTO.setCurrentStationId(currentStation.getId());
        }
        return  bikeDTO;

    }

    private MaintenanceDTO mapMaintenanceToDto(Maintenance maintenance) {
        MaintenanceDTO maintenanceDTO = new MaintenanceDTO();

        maintenanceDTO.setId(maintenance.getId());
        maintenanceDTO.setDescription(maintenance.getDescricao());
        maintenanceDTO.setCost(maintenance.getCusto());
        maintenanceDTO.setDate(maintenance.getDate());

        Bike bike = maintenance.getBike();
        if (bike != null) {
            maintenanceDTO.setBikeId(bike.getId());
        }

        maintenanceDTO.setAdminId(maintenance.getAdmin().getId());

        return maintenanceDTO;
    }

    //Caso de Uso E
    public List<BikeDTO> listBikesByStatus (String statusFilter){
        List<Bike> bikes;

        if (statusFilter != null && !statusFilter.isEmpty()) {
            try{
                BikeStatus status = BikeStatus.valueOf(statusFilter.toUpperCase());

                bikes = bikeRepository.findByStatus(status);
            } catch (IllegalArgumentException e){
                throw new IllegalArgumentException("Invalid status filter: " + statusFilter);
            }
        } else {
            bikes = bikeRepository.findAll();
        }
        return bikes.stream().map(this::mapBiketoDto).collect(Collectors.toList());
    }

    //Caso de Uso D
    @Transactional
    public BikeDTO createBike(BikeDTO bikeDTO){

        Long StationId = bikeDTO.getCurrentStationId();
        if (StationId == null){
            throw new IllegalArgumentException("StationId is null, Initial Station must be given");
        }

        Optional<Station> stationOptional = stationRepository.findById(StationId);
        if (stationOptional.isEmpty()){
            throw new IllegalArgumentException("Station with id " + StationId + " not found");
        }
        Station station = stationOptional.get();

        int currentBikes = station.getBikesAssociated().size();
        int capacity = station.getCapacity();

        if (currentBikes >= capacity){
            throw new IllegalArgumentException("Station " + station.getName() + " is full");
        }
        Bike newBike = new Bike();
        newBike.setModelo(bikeDTO.getModelo());
        newBike.setStatus(BikeStatus.AVAILABLE);
        newBike.setCurrentStation(station);

        Bike savedBike = bikeRepository.save(newBike);

        station.getBikesAssociated().add(savedBike);

        return mapBiketoDto(savedBike);
    }

    //Caso de Uso F
    @Transactional
    public BikeDTO updateBikeStatus(Long bikeId, String newStatus) {

        Optional<Bike> bikeOptional = bikeRepository.findById(bikeId);
        if (bikeOptional.isEmpty()) {
            return null;
        }
        Bike bike = bikeOptional.get();

        BikeStatus newStatusEnum;
        try {
            newStatusEnum = BikeStatus.valueOf(newStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }

        switch (newStatusEnum) {
            case BEING_USED:
            case MAINTENANCE:
                if (bike.getCurrentStation() != null) {
                    bike.getCurrentStation().removeBike(bike);
                    bike.setCurrentStation(null);
                }
                break;

            case AVAILABLE:
                break;
            default:
        }
        bike.setStatus(newStatusEnum);
        Bike savedBike = bikeRepository.save(bike);
        return mapBiketoDto(savedBike);
    }

    //Caso de Uso G
    @Transactional
    public MaintenanceDTO registerMaintenance (Long bikeId, MaintenanceDTO maintenanceDTO){
        Bike bike = bikeRepository.findById(bikeId).orElseThrow(() -> new IllegalArgumentException("BikeRepository.java with ID " + bikeId + " not found."));
        Long adminId = maintenanceDTO.getAdminId();
        if (adminId == null) {
            throw new IllegalArgumentException("Admin ID must be provided to register maintenance.");
        }
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new IllegalArgumentException("Admin with ID " + adminId + " not found."));

        if (bike.getStatus() == BikeStatus.AVAILABLE){

            bike.setStatus(BikeStatus.MAINTENANCE);
            if (bike.getCurrentStation() != null){
                bike.getCurrentStation().removeBike(bike);
                bike.setCurrentStation(null);
            }
            bikeRepository.save(bike);
        }
        Maintenance newMaintenance = new Maintenance();

        newMaintenance.setDate(LocalDateTime.now());
        newMaintenance.setDescricao(maintenanceDTO.getDescription());
        newMaintenance.setCusto(maintenanceDTO.getCost());

        newMaintenance.setBike(bike);
        newMaintenance.setAdmin(admin);

        Maintenance savedMaintenance = maintenanceRepository.save(newMaintenance);

        return mapMaintenanceToDto(savedMaintenance);


    }

    @Transactional(readOnly = true)
    public BikeDTO getBikeWithMostMaintenanceLastYear() {
        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);

        List<Bike> bikes = bikeRepository.findBikesWithMostMaintenanceTimeSince(oneYearAgo);

        if (bikes.isEmpty()) {
            return null;
        }
        return mapBiketoDto(bikes.get(0));
    }
}
