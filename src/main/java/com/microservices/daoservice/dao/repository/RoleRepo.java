package com.microservices.daoservice.dao.repository;


import com.microservices.daoservice.dao.enity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo  extends JpaRepository<Role,Long> {


    @Query(value = "SELECT * FROM accounts.roles a WHERE a.role_type = CONCAT('ROLE_', ?1) ORDER BY id ASC LIMIT 1",
            nativeQuery = true)
    Role findByRoleType(String roleTypeString);

}
