package org.training.upskilling.onlineshop.model;

import java.util.Arrays;
import java.util.Optional;

import org.training.upskilling.onlineshop.dao.DataConversionException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
@EqualsAndHashCode(of = { "id" })
public class User {

	public enum Role {
		USER, ADMIN;

		public static Optional<Role> findRole(String name) {
			return Arrays.stream(values()).filter(role -> role.name().equals(name)).findFirst();
		}

		public static Role getRole(String name) {
			return findRole(name)
					.orElseThrow(() -> new DataConversionException(String.format("can't find user role for name %s", name)));
		}

	}

	private Long id;
	private String name;
	private String encodedPassword;
	private String salt;
	private Role role;

}
