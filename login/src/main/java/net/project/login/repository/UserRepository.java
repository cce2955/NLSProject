package net.project.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.project.login.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

		
}
