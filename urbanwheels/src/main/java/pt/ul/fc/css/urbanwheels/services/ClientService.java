package pt.ul.fc.css.urbanwheels.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.urbanwheels.dto.ClientDTO;
import pt.ul.fc.css.urbanwheels.dto.TripDTO;
import pt.ul.fc.css.urbanwheels.entities.Client;
import pt.ul.fc.css.urbanwheels.repository.ClientRepository;
import pt.ul.fc.css.urbanwheels.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService{

    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private UserRepository userRepository;

    public ClientDTO createClient(ClientDTO clientDto) {
        if (userRepository.existsByEmail(clientDto.getEmail())) {
            throw new IllegalArgumentException("Email já registado: " + clientDto.getEmail());
        }

        Client client = new Client(
                clientDto.getEmail(),
                clientDto.getName(),
                clientDto.getSubscription()
        );

        Client savedClient = clientRepository.save(client);

            return mapToDto(savedClient);
    }

    @Transactional(readOnly = true)
    public List<ClientDTO> getTopClientsLastMonth() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

        List<Client> topClients = clientRepository.findTopClientsByTripsSince(oneMonthAgo);

        return topClients.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    private ClientDTO mapToDto(Client client) {
        return new ClientDTO(
                client.getId(),
                client.getEmail(),
                client.getName(),
                client.getSubscription(),
                new ArrayList<>() 
        );
    }
}