package pt.ul.fc.css.urbanwheels.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.urbanwheels.dto.AdminDTO;
import pt.ul.fc.css.urbanwheels.entities.Admin;
import pt.ul.fc.css.urbanwheels.repository.AdminRepository;
import pt.ul.fc.css.urbanwheels.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService  {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    public AdminDTO createAdmin(AdminDTO adminDto) {
        if (userRepository.existsByEmail(adminDto.getEmail())) {
            throw new IllegalArgumentException("Email já registado: " + adminDto.getEmail());
        }

        Admin admin = new Admin(adminDto.getEmail(), adminDto.getName());

        Admin savedAdmin = adminRepository.save(admin);

        return mapToDto(savedAdmin);
    }

    @Transactional(readOnly = true)
    public List<AdminDTO> getTopAdminsLastMonth() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

        List<Admin> topAdmins = adminRepository.findTopAdminsByMaintenanceSince(oneMonthAgo);


        return topAdmins.stream()
                .limit(10)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private AdminDTO mapToDto(Admin admin) {
        return new AdminDTO(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                new ArrayList<>() 
        );
    }
}