package org.training.upskilling.onlineshop.security;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TokenConverter {
	
	private final ObjectMapper mapper;
	
	public TokenConverter() {
		mapper = new ObjectMapper();
	}
	
	public String toString(Token token) throws JacksonException {
		return mapper.writeValueAsString(token);
	}
	
	public Token parse(String value) throws JacksonException {
		return mapper.readValue(value, Token.class);
	}

}
