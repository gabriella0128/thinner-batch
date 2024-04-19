package com.nns.thinnerbatch.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "log")
@DynamicUpdate
@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LogEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "log_idx")
	private Long logIdx;

	@Column(name = "trace_id")
	private String traceId;

	@Column(name = "http_message")
	private String httpMessage;

	@Column(name = "http_method")
	private String httpMethod;

	@Column(name = "uri")
	private String uri;

	@Column(name = "log", columnDefinition = "LONGTEXT")
	private String log;

	@Column(name = "user_idx")
	private Long userIdx;

	@Column(name = "user_key")
	private String userKey;

	@Column(name = "create_dt")
	private LocalDateTime createDt;
}

