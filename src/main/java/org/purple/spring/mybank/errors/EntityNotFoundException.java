package org.purple.spring.mybank.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String stringId;
	private String type;
	
	public String getStringId() {
		return stringId;
	}

	public void setStringId(String stringId) {
		this.stringId = stringId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public EntityNotFoundException(Long id, String type) {
	    super("Could not find entity of type " + type + " and id " + id);
	    this.id = id;
	    this.type = type;
	  }
	
	public EntityNotFoundException(String id, String type) {
	    super("Could not find entity of type " + type + " and id " + id);
	    this.stringId = id;
	    this.type = type;
	  }
	}
