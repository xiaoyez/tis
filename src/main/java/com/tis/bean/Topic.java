package com.tis.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class Topic{
	@Id
	@Column
	private Integer id;
	@Column
	private Integer senderId;
	@Column
	private String content;
	@Column
	private java.time.LocalDateTime sendTime;

}

/*
{
	id:,
	senderId:,
	content:,
	sendTime:
}

*/
