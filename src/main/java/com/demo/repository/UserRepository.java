package com.demo.repository;

import com.demo.entity.Role;
import com.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    List<User> findAllByRole(Role role);
    Optional<User> findByIdAndRole(Integer id, Role role);
    User findByNickname(String nickname);
    List<User> findAllByRoleAndIsVerified(Role role, boolean isVerified);
    List<User> findAllByIsVerified(boolean isVerified);

}
