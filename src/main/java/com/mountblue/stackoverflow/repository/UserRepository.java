package com.mountblue.stackoverflow.repository;

import com.mountblue.stackoverflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from users where email=:email", nativeQuery = true)
    User getUserByEmail(@Param("email") String email);

    User findByEmail(String email);
}
