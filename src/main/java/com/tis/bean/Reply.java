package com.tis.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class Reply{
	@Id
	@Column
	private Integer id;
	@Column
	private Integer floorId;
	@Column
	private Integer senderId;
	@Column
	private String content;
	@Column
	private Integer replyId;
	@Column
	private Integer type;
	@Column
	private java.time.LocalDateTime sendTime;

}

/*
{
	id:,
	floorId:,
	senderId:,
	content:,
	replyId:,
	type:,
	sendTime:
}

*/
