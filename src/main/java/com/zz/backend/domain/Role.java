package com.zz.backend.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Role implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6281411626142817230L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	private String name;

	protected Role(){}
	
	public Role(String name) {
		this.name = name;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}
}
