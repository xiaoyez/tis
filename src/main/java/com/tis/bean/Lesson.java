package com.tis.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class Lesson{
	@Id
	@Column
	private Integer id;
	@Column
	private String name;
	@Column
	private Integer launcherId;
	@Column
	private java.time.LocalDateTime launchTime;

}

/*
{
	id:,
	name:,
	launcherId:,
	launchTime:
}

*/
