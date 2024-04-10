package org.projectndongo.ndongo.domain.auth.service;

import org.projectndongo.ndongo.domain.auth.User;

import java.util.List;

public interface UserServiceInterface {


    List<User> getAllUsers();

      List<User> getUsersByRole(String role);
}
