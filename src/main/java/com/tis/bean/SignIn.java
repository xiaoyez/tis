package com.tis.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class SignIn{
	@Id
	@Column
	private Integer id;
	@Column
	private Integer lessonId;
	@Column
	private Integer studentId;
	@Column
	private Integer hasSigned;

}

/*
{
	id:,
	lessonId:,
	studentId:,
	hasSigned:
}

*/
