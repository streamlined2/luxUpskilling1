package org.training.upskilling.onlineshop.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductDto(long id, String name, BigDecimal price, LocalDate creationDate) {}
