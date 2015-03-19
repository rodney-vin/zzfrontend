package com.zz.backend.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * This kinds of class, the name is started with "Doc", means this class is used for elastic.
 * This class should only contain the indexed field, if you want to search the object, just keep the necessary field
 * @author liping
 *
 */
@Document(indexName = "user", type = "user", shards = 1, replicas = 1, refreshInterval = "-1")
public class DocUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3208979886934882276L;

	@Id
	private Long id;
	
	private String name;

	protected DocUser(){}
	
	public DocUser(String name) {
		this.name = name;
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
}
