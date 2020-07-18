package com.qadah.demo.controller;

import com.qadah.demo.data.dto.OrderDto;
import com.qadah.demo.data.dto.OrderIncomingDto;
import com.qadah.demo.data.dto.OrderPageDto;
import com.qadah.demo.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Ehab Qadah
 */
@RestController()
@RequestMapping(path = {"/v1/orders"}, produces = APPLICATION_JSON_VALUE)
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	private static final String ID = "id";
	private static final String NEW_ORDER_LOG = "New order was created id:{}";
	private static final String ORDER_UPDATED_LOG = "Order:{} was updated";

	private final OrderService orderService;

	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@Operation(summary = "Crate a new order")
	@ApiResponse(responseCode = "201", description = "Order is created",
			content = {@Content(mediaType = APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = OrderDto.class))})
	@PostMapping(consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderIncomingDto orderIncomingDto) {
		final OrderDto createdOrder = orderService.createOrder(orderIncomingDto);
		logger.info(NEW_ORDER_LOG, createdOrder.toString());
		return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
	}

	@Operation(summary = "Get an oder by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the order",
					content = {@Content(mediaType = APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = OrderDto.class))}),
			@ApiResponse(responseCode = "404", description = "Order not found", content = @Content)})
	@GetMapping(path = "/{id}")
	public ResponseEntity<OrderDto> loadOrder(@PathVariable(value = ID) String id) {
		final Optional<OrderDto> order = orderService.loadOrder(id);
		if (order.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(order.get());
	}

	@Operation(summary = "Update an oder by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Order was updated",
					content = {@Content(mediaType = APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = OrderDto.class))}),
			@ApiResponse(responseCode = "404", description = "Order not found", content = @Content)})
	@PutMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderDto> updateCustomQuoteRequest(@PathVariable(value = ID) String id,
	                                                         @Valid @RequestBody OrderIncomingDto orderIncomingDto) {
		final Optional<OrderDto> updatedOrder = orderService.updateOrder(id, orderIncomingDto);
		if (updatedOrder.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		logger.info(ORDER_UPDATED_LOG, updatedOrder.toString());
		return ResponseEntity.ok(updatedOrder.get());
	}

	@Operation(summary = "Returns a list of orders and sorted/filtered based on the query parameters")
	@ApiResponse(responseCode = "200", description = "Order was updated",
			content = {@Content(mediaType = APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = OrderPageDto.class))})
	@GetMapping
	public ResponseEntity<OrderPageDto> getOrders(
			@RequestParam(required = false, name = "page", defaultValue = "0") int page,
			@RequestParam(required = false, name = "size", defaultValue = "20") int size,
			@RequestParam(required = false, name = "sortField", defaultValue = "createdAt") String sortField,
			@RequestParam(required = false, name = "direction", defaultValue = "DESC") String direction,
			@RequestParam(required = false, name = "status") List<String> status,
			@RequestParam(required = false, name = "search") String search
	) {
		final OrderPageDto ordersPage = orderService.getOrders(page, size, sortField, direction, status, search);
		return ResponseEntity.ok(ordersPage);
	}

}
