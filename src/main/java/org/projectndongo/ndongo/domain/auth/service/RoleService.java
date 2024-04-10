package org.projectndongo.ndongo.domain.auth.service;

import org.projectndongo.ndongo.domain.auth.Role;
import org.projectndongo.ndongo.domain.auth.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repo;

    public void save(Role role) {
        repo.save(role);
    }

    public Role getRoleByName(String name) {
        return repo.getRoleByName(name);
    }


    public Role getRoleById(int id) {
        return repo.getRoleById(id);
    }
}
