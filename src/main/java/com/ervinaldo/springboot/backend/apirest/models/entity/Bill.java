package com.ervinaldo.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Entity
@Table(name = "bills")
public class Bill implements Serializable {
	public Bill() {
		this.billItems = new ArrayList<>();
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private String observation;
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"bills", "hibernateLazyInitializer", "handler"})
	private Client client;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "bill_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<BillItem> billItems;

	@PrePersist
	public void prePersist() {
		this.createdAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public List<BillItem> getBillItems() {
		return billItems;
	}

	public void setBillItems(List<BillItem> billItems) {
		this.billItems = billItems;
	}

	public Double getTotal() {
		Double total = 0.00;
		for(BillItem item: this.billItems) {
			total +=item.getAmount();
		}
		return total;
			
	}

}
