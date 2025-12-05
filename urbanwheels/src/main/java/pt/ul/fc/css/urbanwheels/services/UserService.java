package pt.ul.fc.css.urbanwheels.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.urbanwheels.dto.*;
import pt.ul.fc.css.urbanwheels.entities.*;
import pt.ul.fc.css.urbanwheels.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public UserDTO getUserDetails(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return null;
        }
        return mapToDto(userOptional.get());
    }

    private UserDTO mapToDto(User user) {
        if (user instanceof Client) {
            return mapClientToDto((Client) user);
        } else if (user instanceof Admin) {
            return mapAdminToDto((Admin) user);
        } else {
            return new UserDTO(user.getId(), user.getName(), user.getEmail());
        }
    }

    private ClientDTO mapClientToDto(Client client) {
        List<TripDTO> tripDtos = client.getTrips().stream()
                .map(trip -> new TripDTO(
                        trip.getId(),
                        trip.getStartTime(),
                        trip.getEndTime(),
                        trip.getBike().getModelo(),
                        trip.getStartStation().getName(),
                        (trip.getEndStation() != null) ? trip.getEndStation().getName() : "Em curso"
                ))
                .collect(Collectors.toList());

        return new ClientDTO(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getSubscription(),
                tripDtos
        );
    }

    private AdminDTO mapAdminToDto(Admin admin) {
        List<MaintenanceDTO> maintenanceDtos = admin.getMaintenanceTasks().stream()
                .map(m -> new MaintenanceDTO(
                        m.getId(),
                        m.getAdmin().getId(),
                        m.getDescricao(),
                        m.getCusto(),
                        m.getDate(),
                        m.getBike().getId()
                ))
                .collect(Collectors.toList());

        return new AdminDTO(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                maintenanceDtos
        );

    }
}