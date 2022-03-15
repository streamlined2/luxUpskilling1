package org.training.upskilling.onlineshop.service.dto;

public record UserDto(long id, String name, String encodedPassword, String salt, String role) {}
