package org.training.upskilling.onlineshop.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder
@EqualsAndHashCode(of = { "id" })
public class Product {

	private long id;
	private String name;
	private BigDecimal price;
	private LocalDate creationDate;

}
