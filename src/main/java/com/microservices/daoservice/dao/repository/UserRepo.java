package com.microservices.daoservice.dao.repository;


import com.microservices.daoservice.dao.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface UserRepo  extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM accounts.users u WHERE u.email = ?1 ORDER BY id ASC LIMIT 1",
            nativeQuery = true)
    User findByEmail(String email);

    @Query(value = "SELECT * FROM accounts.users u WHERE u.login = ?1 ORDER BY id ASC LIMIT 1",
            nativeQuery = true)
    User findByLogin(String login);
}
