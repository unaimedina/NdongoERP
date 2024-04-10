package org.projectndongo.ndongo.domain.auth.repo;

import org.projectndongo.ndongo.domain.auth.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Role getRoleByName(String name);

    @Query("SELECT r FROM Role r WHERE r.id = :id")
    Role getRoleById(int id);
}
