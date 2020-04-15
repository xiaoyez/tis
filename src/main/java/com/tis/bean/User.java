package com.tis.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class User{
	@Id
	@Column
	private Integer id;
	@Column
	private String loginNumber;
	@Column
	private String password;
	@Column
	private Integer type;
	@Column
	private String username;

}

/*
{
	id:,
	loginNumber:,
	password:,
	type:,
	username:
}

*/
