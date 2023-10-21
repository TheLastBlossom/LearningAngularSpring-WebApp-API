package com.ervinaldo.springboot.backend.apirest.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ervinaldo.springboot.backend.apirest.models.entity.Bill;
import com.ervinaldo.springboot.backend.apirest.models.service.IClientService;

@RestController
@RequestMapping("/api")
public class BillRestController {
	@Autowired
	private IClientService clientService;
	
	@GetMapping("/bills/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Bill show(@PathVariable Long id) {
		return clientService.findBillById(id);
	}
	
	@DeleteMapping("bills/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		clientService.deleteBillById(id);
	}

}
