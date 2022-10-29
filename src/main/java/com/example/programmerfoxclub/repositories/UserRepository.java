package com.example.programmerfoxclub.repositories;

import com.example.programmerfoxclub.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Integer>{

}
