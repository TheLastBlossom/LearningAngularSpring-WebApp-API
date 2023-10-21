package com.ervinaldo.springboot.backend.apirest.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ervinaldo.springboot.backend.apirest.models.dao.IUserDao;
import com.ervinaldo.springboot.backend.apirest.models.entity.User;
@Service
public class UserImpl implements IUserService {
	@Autowired
	private IUserDao userdao;
	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return userdao.findByUsername(username);
	}

}
