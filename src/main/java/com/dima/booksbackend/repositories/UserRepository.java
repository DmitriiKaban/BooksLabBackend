package com.dima.booksbackend.repositories;

import com.dima.booksbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Short> {
    Optional<User> findByFullName(String name);

    Optional<User> findByEmail(String email);
}
