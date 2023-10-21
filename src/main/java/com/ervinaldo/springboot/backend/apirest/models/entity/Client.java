package com.ervinaldo.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "clients")
public class Client implements Serializable {
	
	public Client() {
		this.bills = new ArrayList<>();
	}

	private static final long serialVersionUID = -7106931204792143572L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "I cannot be null")
	@Size(min = 4, max = 12, message = "The size has to be between 4 and 12")
	@Column(nullable = false)
	private String name;
	@NotEmpty
	private String surname;
	@NotEmpty
	@Email
	@Column(nullable = false, unique = false)
	private String email;

	@NotNull(message = "It cannot be null")
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	private String photo;
	@NotNull(message = "The region cannot be null")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_region")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Region region;	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"client", "hibernateLazyInitializer", "handler"})
	private List<Bill> bills;

//	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public List<Bill> getBills() {
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}
	
}
