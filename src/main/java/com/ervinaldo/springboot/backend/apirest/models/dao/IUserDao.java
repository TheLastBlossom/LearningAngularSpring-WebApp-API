package com.ervinaldo.springboot.backend.apirest.models.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ervinaldo.springboot.backend.apirest.models.entity.User;

public interface IUserDao extends JpaRepository<User, Long>{
	public User findByUsername(String username);
}
