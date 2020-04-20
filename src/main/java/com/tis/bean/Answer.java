package com.tis.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;

@Data
public class Answer{
	@Id
	@Column
	private Integer id;
	@Column
	private Integer questionId;
	@Column
	private Integer studentId;
	@Column
	private String answer;

	@Transient
	private String studentName;

}

/*
{
	id:,
	questionId:,
	studentId:,
	answer:
}

*/
