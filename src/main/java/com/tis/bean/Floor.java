package com.tis.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;

@Data
public class Floor{
	@Id
	@Column
	private Integer id;
	@Column
	private Integer topicId;
	@Column
	private Integer senderId;
	@Column
	private String content;
	@Column
	private Integer floor;
	@Column
	private java.time.LocalDateTime sendTime;

	@Transient
	private String senderName;

}

/*
{
	id:,
	topicId:,
	senderId:,
	content:,
	floor:,
	sendTime:
}

*/
