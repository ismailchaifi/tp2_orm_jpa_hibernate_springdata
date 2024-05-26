package ma.enset.hospital.service;

import lombok.AllArgsConstructor;
import ma.enset.hospital.entities.Role;
import ma.enset.hospital.entities.User;
import ma.enset.hospital.repositories.RoleRepository;
import ma.enset.hospital.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class IUserServiceImpl implements IUserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Override
    public User addNewUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Role addNewRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Role findRoleByRolename(String rolename) {
        return roleRepository.findByRoleName(rolename);
    }

    @Override
    public void addRoleToUser(String username, String rolename) {
        User user = findUserByUsername(username);
        Role role = findRoleByRolename(rolename);
        user.getRoles().add(role);
        role.getUsers().add(user);
    }

    @Override
    public User authenticate(String username, String password) {
        User user = findUserByUsername(username);
        if (user == null) throw new RuntimeException("Bad credentials");
        else if (user.getPassword().equals(password)) {
            return user;
        }
        throw new RuntimeException("Bad credentials");
    }
}
