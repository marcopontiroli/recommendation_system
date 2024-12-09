package com.conentwise.recommendationsystem.recommendationsystem.datamodel.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.conentwise.recommendationsystem.recommendationsystem.datamodel.entities.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>{}
