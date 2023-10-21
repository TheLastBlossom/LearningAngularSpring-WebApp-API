package com.ervinaldo.springboot.backend.apirest.models.entity;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "c_rol")
public class CRol implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRol;

	@Column(unique = true, length = 20)
	private String rol;

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
