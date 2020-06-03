package com.microservices.daoservice.dao.config;

import com.microservices.daoservice.dao.enity.Privilege;
import com.microservices.daoservice.dao.enity.Role;
import com.microservices.daoservice.dao.enity.User;
import com.microservices.daoservice.dao.enity.enums.PrivilegeType;
import com.microservices.daoservice.dao.enity.enums.RoleType;
import com.microservices.daoservice.dao.repository.PrivilegeRepo;
import com.microservices.daoservice.dao.repository.RoleRepo;
import com.microservices.daoservice.dao.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PrivilegeRepo privilegeRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        List<Privilege> privilegeList = new ArrayList<>();


        for (PrivilegeType s: PrivilegeType.values()) {
            Privilege p = createPrivilegeIfNotFound(s);
            privilegeList.add(p);
        }

        for (RoleType rt: RoleType.values()){
            createRoleIfNotFound(rt,privilegeList);
        }

        createDefaultUser();
    }

    @Transactional
    private Privilege createPrivilegeIfNotFound(PrivilegeType pt) {

        Privilege privilege = privilegeRepo.findByType(pt.toString());
        if (privilege == null) {
            privilege = new Privilege(pt);
            privilegeRepo.save(privilege);
        }
        return privilege;
    }

    @Transactional
    private Role createRoleIfNotFound(RoleType roleType, List<Privilege> privileges) {
        Role role = roleRepo.findByRoleType(roleType.getRole());
        if (role == null) {
            role = new Role(roleType);
            role.setPrivileges(privileges);
            roleRepo.save(role);
        }
        return role;
    }

    @Transactional
    private void createDefaultUser(){
        User user = userRepo.findByLogin("eureka");
        if(user == null) {
            String role =  RoleType.ROLE_APPLICATION.getRole();
            Role appRole = roleRepo.findByRoleType(role);
            user = new User();
            user.setLogin("eureka");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRoles(Arrays.asList(appRole));
            user.setEnabled(true);
            userRepo.save(user);

            alreadySetup = true;
        }
    }
}
