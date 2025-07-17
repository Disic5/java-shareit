package ru.den.shareitserver.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.den.shareitserver.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}