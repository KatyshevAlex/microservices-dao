package com.microservices.daoservice.dao.service.impl;


import com.microservices.daoservice.dao.enity.User;
import com.microservices.daoservice.dao.repository.UserRepo;
import com.microservices.daoservice.dao.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepo userRepo;

    @Override
    public User findUser(User user) {
        User response = null;
        if(user.getEmail() != null){
            response = userRepo.findByEmail(user.getEmail());
        } else if (user.getLogin() != null) {
            response = userRepo.findByLogin(user.getLogin());
        }

        return response;
    }

    public User updateUser(User user){
        if (user != null){
            return userRepo.save(user);
        }
        return null;
    }
}
