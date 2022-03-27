package org.training.upskilling.onlineshop.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

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
public class ProductDto {

	private @NonNull Long id;
	private @NonNull String name;
	private @NonNull BigDecimal price;
	private @NonNull @DateTimeFormat(iso = ISO.DATE) LocalDate creationDate;

}
