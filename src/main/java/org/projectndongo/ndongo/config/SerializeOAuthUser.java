package org.projectndongo.ndongo.config;

import org.projectndongo.ndongo.domain.auth.User;
import org.projectndongo.ndongo.domain.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SerializeOAuthUser {

    @Autowired
    private UserService userService;

    public String createNewUsername(User user) {
        // Create a new username based on the user's email address.
        String email = user.getEmail();
        String username = email.split("@")[0];
        String newUsername = username;
        int i = 1;
        while (userService.userExists(newUsername)) {
            newUsername = username + i;
            i++;
        }

        return newUsername;
    }
}
