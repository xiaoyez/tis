package com.tis.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class Info{
	@Id
	@Column
	private Integer id;
	@Column
	private String title;
	@Column
	private String content;
	@Column
	private java.time.LocalDateTime publishTime;
	@Column
	private Integer publisherId;

}

/*
{
	id:,
	title:,
	content:,
	publishTime:,
	publisherId:
}

*/
