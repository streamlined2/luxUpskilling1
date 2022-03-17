package org.training.upskilling.onlineshop.security.session;

import java.time.LocalDateTime;

import org.training.upskilling.onlineshop.security.token.Token;
import org.training.upskilling.onlineshop.service.dto.UserDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Session {

	private final Token token;
	private final UserDto user;
	private LocalDateTime expirationTime;

	public Session(Token token, UserDto user, long lifeTime) {
		this.token = token;
		this.user = user;
		expirationTime = LocalDateTime.now().plusSeconds(lifeTime);
	}
	
	public void prolongExpirationTime(long seconds) {
		expirationTime = expirationTime.plusSeconds(seconds);
	}
	
	public void invalidate() {
		expirationTime = LocalDateTime.MIN;
	}
	
}
