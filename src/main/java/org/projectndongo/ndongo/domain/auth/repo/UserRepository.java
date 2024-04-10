package org.projectndongo.ndongo.domain.auth.repo;

        import org.projectndongo.ndongo.domain.auth.AuthType;
        import org.projectndongo.ndongo.domain.auth.User;
        import org.springframework.data.jpa.repository.Modifying;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.CrudRepository;
        import org.springframework.data.repository.query.Param;

        import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {


    @Modifying
    @Query("UPDATE User u SET u.authType = ?2 WHERE u.username = ?1")
    void updateAuthenticationType(String username, AuthType authType);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User getUserByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User getUserByEmail(String email);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role.id = 4 ")
    long countPlayers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role.id = 2")
    long countAdmins();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role.id = 1")
    long countProjectManagers();

    @Query("SELECT u FROM User u WHERE u.role.name = :role")
    List<User> getUsersByRole(@Param("role") String role);

    @Query("SELECT u FROM User u WHERE u.id = :id")
    User getUserById(@Param("id") String id);
}
