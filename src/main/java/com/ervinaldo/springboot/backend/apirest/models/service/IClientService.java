package com.ervinaldo.springboot.backend.apirest.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ervinaldo.springboot.backend.apirest.models.entity.Bill;
import com.ervinaldo.springboot.backend.apirest.models.entity.Client;
import com.ervinaldo.springboot.backend.apirest.models.entity.Region;

public interface IClientService {
	public List<Client> findAll();
	public Page<Client> findAll(Pageable pageable);
	public Client save(Client client);
	public void delete(Long id);
	public Client findById(Long id);	
	public List<Region> findAllRegiones();
	public Bill findBillById(Long id);
	public Bill saveBill(Bill bill);
	public void deleteBillById(Long id);
}
