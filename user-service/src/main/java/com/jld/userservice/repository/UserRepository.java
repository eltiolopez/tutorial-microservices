package com.jld.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jld.userservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
