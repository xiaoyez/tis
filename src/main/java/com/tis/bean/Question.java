package com.tis.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class Question{
	@Id
	@Column
	private Integer id;
	@Column
	private Integer lessonId;
	@Column
	private String questionDesc;

}

/*
{
	id:,
	lessonId:,
	questionDesc:
}

*/
