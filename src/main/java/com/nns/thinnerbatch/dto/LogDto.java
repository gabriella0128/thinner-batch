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
		public Long logIdx;
		public String traceId;
		public String httpMessage;
		public String httpMethod;
		public String uri;
		public String log;
		public Long userIdx;
		public String userKey;
		public LocalDateTime createDt;


	}
}