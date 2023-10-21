package com.ervinaldo.springboot.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.ervinaldo.springboot.backend.apirest.models.entity.Bill;

public interface IBillDao extends CrudRepository<Bill, Long>{
	
}
