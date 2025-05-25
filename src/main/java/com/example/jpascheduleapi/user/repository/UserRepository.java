package com.example.jpascheduleapi.user.repository;

import com.example.jpascheduleapi.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    default User findUserByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    default User findUserByEmailAndPasswordOrElseThrow(String email, String password) {
        return findByEmailAndPassword(email, password)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Does not exist email or incorrect password"));
    }

    Optional<User> findByEmailAndPassword(String email, String password);

    boolean existsUserByEmail(String email);
}
