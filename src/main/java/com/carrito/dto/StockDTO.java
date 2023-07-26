package com.carrito.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDTO {

	@Min(value = 0, message = "El valor debe ser mayor o igual a 0")
	@Max(value = 100, message = "El valor debe ser menor o igual a 100")
	private int amount;
}
