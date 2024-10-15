package com.example.boubyantask.repo;

import com.example.boubyantask.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {
//    @Query("select U from User U where U.loginname = :loginName")
//    User findByLoginName(@Param("loginName")  String loginName);


    User findUserByLoginname( String loginName);
    User findUserById(int id);



}
