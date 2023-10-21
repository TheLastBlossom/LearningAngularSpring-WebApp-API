package com.ervinaldo.springboot.backend.apirest.models.service;

import com.ervinaldo.springboot.backend.apirest.models.entity.User;

public interface IUserService {
	public User findByUsername(String username);
}
