package com.algaworks.algafood.core.data;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableTranslator {
	
	public static Pageable translator(Map<String, String> map, Pageable pageable) {
		
		var orders = pageable.getSort()
			.stream()
			.filter(order -> map.containsKey(order.getProperty()))
			.map(order -> new Sort.Order(order.getDirection(), map.get(order.getProperty())))
			.collect(Collectors.toList());
		
		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
	}

}
