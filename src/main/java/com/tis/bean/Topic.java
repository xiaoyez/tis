package com.tis.bean;

import lombok.Data;
import org.springframework.stereotype.Controller;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;

@Data
public class Topic{
	@Id
	@Column
	private Integer id;
	@Column
	private String title;
	@Column
	private Integer senderId;
	@Column
	private String content;
	@Column
	private java.time.LocalDateTime sendTime;
	@Transient
	private String senderName;
}

/*
{
	id:,
	senderId:,
	content:,
	sendTime:
}

*/
