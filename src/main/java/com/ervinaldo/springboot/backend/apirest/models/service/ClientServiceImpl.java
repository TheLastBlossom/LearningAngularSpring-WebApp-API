package com.ervinaldo.springboot.backend.apirest.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ervinaldo.springboot.backend.apirest.models.dao.IBillDao;
import com.ervinaldo.springboot.backend.apirest.models.dao.IClientDao;
import com.ervinaldo.springboot.backend.apirest.models.entity.Bill;
import com.ervinaldo.springboot.backend.apirest.models.entity.Client;
import com.ervinaldo.springboot.backend.apirest.models.entity.Region;

@Service
public class ClientServiceImpl implements IClientService {
	@Autowired
	private IClientDao clientdao;
	@Autowired 
	private IBillDao billdao;
	@Override
	@Transactional(readOnly = true)
	public List<Client> findAll() {
		// TODO Auto-generated method stub
		return (List<Client>) clientdao.findAll();
	}
	@Override
	@Transactional
	public Page<Client> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return clientdao.findAll(pageable);
	}
	@Override
	@Transactional
	public Client save(Client client) {
		// TODO Auto-generated method stub
		return clientdao.save(client);
	}
	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		clientdao.deleteById(id);
		
	}
	@Override
	@Transactional(readOnly = true)
	public Client findById(Long id) {		
		// TODO Auto-generated method stub
		return clientdao.findById(id).orElse(null);
	}
	@Override
	@Transactional(readOnly = true)
	public List<Region> findAllRegiones() {
		// TODO Auto-generated method stub
		return clientdao.findAllRegiones();
	}
	@Override
	@Transactional(readOnly = true)
	public Bill findBillById(Long id) {
		// TODO Auto-generated method stub
		return billdao.findById(id).orElse(null);
	}
	@Override
	@Transactional
	public Bill saveBill(Bill bill) {
		// TODO Auto-generated method stub
		return billdao.save(bill);
	}
	@Override
	@Transactional
	public void deleteBillById(Long id) {
		// TODO Auto-generated method stub
		billdao.deleteById(id);
		
	}
}
