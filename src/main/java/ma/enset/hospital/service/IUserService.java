package ma.enset.hospital.service;

import ma.enset.hospital.entities.Role;
import ma.enset.hospital.entities.User;

public interface IUserService {
    User addNewUser(User user);
    Role addNewRole(Role role);
    User findUserByUsername(String username);
    Role findRoleByRolename(String rolename);
    void addRoleToUser(String username, String rolename);
    User authenticate(String username, String password);
}
