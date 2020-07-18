package com.qadah.demo.service;

import com.qadah.demo.data.dto.OrderDto;
import com.qadah.demo.data.dto.OrderPageDto;
import com.qadah.demo.data.dto.OrderIncomingDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
	public Optional<OrderDto> loadOrder(String id) {
		return Optional.empty();
	}

	public OrderDto createOrder(OrderIncomingDto orderIncomingDto) {
		return null;
	}

	public Optional<OrderDto> updateOrder(String id, OrderIncomingDto orderIncomingDto) {
		return Optional.empty();
	}

	public OrderPageDto getOrders(int page, int size, String sortField, String direction, List<String> status, String search) {

		return null;
	}
}
