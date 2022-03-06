package org.training.upskilling.onlineshop.model;

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

	private Long id;
	private String name;
	private String encodedPassword;
	private String salt;
	
}
