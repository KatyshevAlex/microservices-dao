package com.microservices.daoservice.dao.service;


import com.microservices.daoservice.dao.enity.User;


public interface IUserService {
    User findUser(User user);
    User updateUser(User user);
}
