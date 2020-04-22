package com.tis.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;

@Data
public class Homework{
	@Id
	@Column
	private Integer id;
	@Column
	private Integer lessonId;
	@Column
	private String homeworkPath;
	@Column
	private Integer studentId;
	@Column
	private String homeworkFilename;
	@Transient
	private String studentName;
	@Transient
	private String lessonName;
}

/*
{
	id:,
	lessonId:,
	homeworkPath:,
	studentId:,
	homeworkFilename:
}

*/
