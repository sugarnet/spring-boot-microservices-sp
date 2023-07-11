package com.dss.springboot.service.users.model.dao;

import com.dss.springboot.service.users.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "userapi")
public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
    @Query("select u from User u where u.username = ?1")
    User getByUsername(String username);
}
