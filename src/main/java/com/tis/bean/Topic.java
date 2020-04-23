package com.tis.bean;

import lombok.Data;
import org.springframework.stereotype.Controller;

import javax.persistence.*;

@Data
public class Topic{
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
