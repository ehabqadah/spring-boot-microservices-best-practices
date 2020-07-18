package com.qadah.demo.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Incoming DTO to create a new record of {@link com.qadah.demo.data.model.Order}
 *
 * @author Ehab Qadah
 */
public class OrderIncomingDto {

	@JsonProperty(required = true)
	@NotEmpty
	@NotBlank
	private String customerId;

	@JsonProperty(required = true)
	@NotNull
	@PastOrPresent
	private Instant datePurchased;

	@JsonProperty(required = true)
	@NotNull
	@Positive
	private BigDecimal total;

	@JsonProperty(required = true)
	@NotEmpty
	@NotBlank
	@Size(min = 5, max = 10)
	private String status;

	@JsonProperty(required = true)
	@NotEmpty
	private List<ProductDto> orderProducts;

	@JsonProperty(required = true)
	@NotEmpty
	@NotBlank
	private String paymentType;

	@JsonProperty(required = true)
	@Size(min = 2, max = 10)
	private String shippingMode;

	@JsonProperty(required = true)
	@Email(message = "Customer email should be valid")
	private String customerEmailAddress;

	// standard setters and getters

}
