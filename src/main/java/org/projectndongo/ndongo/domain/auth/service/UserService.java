package org.projectndongo.ndongo.domain.auth.service;

import jakarta.transaction.Transactional;
import org.projectndongo.ndongo.domain.auth.AuthType;
import org.projectndongo.ndongo.domain.auth.User;
import org.projectndongo.ndongo.domain.auth.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository repo;

    @Autowired
    private RoleService roleService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private String username;

    @Transactional
    public void updateAuthenticationType(String username, String oauth2ClientName) {
        AuthType authType = AuthType.valueOf(oauth2ClientName.toUpperCase());
        repo.updateAuthenticationType(username, authType);
        System.out.println("Updated user's authentication type to " + authType);
    }

    public boolean userExists(String username) {
        return repo.getUserByUsername(username) != null;
    }

    public User getUserById(String id) {
        return repo.getUserById(id);
    }

    public boolean userExistsByEmail(String email) {
        return repo.getUserByEmail(email) != null;
    }

    public void save(User user) {
        repo.save(user);
    }

    public boolean validateAuth(String user, String password) {
        if (user == null || password == null) {
            return false;
        }
        User u = repo.getUserByUsername(user);
        System.out.println("User: " + u);
        return u != null && passwordEncoder.matches(password, u.getPassword());
    }

    public User getUserByUsername(String username) {
        return repo.getUserByUsername(username);
    }

    public int countUsers() {
        return (int) repo.count();
    }

    public long countPlayers() {
        return repo.countPlayers();
    }

    public long countAdmins() {
        return repo.countAdmins();
    }

    public long countProjectManagers() {
        return repo.countProjectManagers();
    }

    public long countTeams() {
        return 0;
        // return repo.countTeams();
    }



    public List<User> getUsersByRole(String role) {
        return repo.getUsersByRole(role);
    }
    public List<User> getAllUsers() {
        return (List<User>) repo.findAll();
    }

    public void updateUser(User user, String email, int role) {
        user.setEmail(email);
        user.setRole(roleService.getRoleById(role));
        repo.save(user);
    }

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleService.getRoleById(1));
        repo.save(user);
    }
}
