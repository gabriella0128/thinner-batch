package com.nns.thinnerbatch.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class LogDto {
	@Getter
	@ToString
	@Builder(toBuilder = true)
	@EqualsAndHashCode
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Info {
		private Long logIdx;
		private String traceId;
		private String httpMessage;
		private String httpMethod;
		private String uri;
		private String log;
		private Long userIdx;
		private String userKey;
		private LocalDateTime createDt;


	}
}