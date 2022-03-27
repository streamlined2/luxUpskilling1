package org.training.upskilling.onlineshop.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class UserDto {

	private @NonNull Long id;
	private @NonNull String name;
	private @NonNull String encodedPassword;
	private @NonNull String salt;
	private @NonNull String role;

}
