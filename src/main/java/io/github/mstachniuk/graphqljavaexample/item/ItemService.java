package io.github.mstachniuk.graphqljavaexample.item;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import io.github.mstachniuk.graphqljavaexample.Company;
import io.github.mstachniuk.graphqljavaexample.Item;

@Service
public class ItemService {

	private Map<String, List<Item>> map = new HashMap<>();

	@PostConstruct
	public void init() {
		Company duckCompany = new Company("12", "Duck Company", "duck.com");
		Item duck = new Item("101", "Rubber duck", 2, "5", "USD", duckCompany);
		Company ballCompany = new Company("13", "Ball Company", "www.com");
		Item ball = new Item("102", "Magic Ball", 1, "10", "USD", ballCompany);
		map.put("55", Arrays.asList(duck, ball));

		Company bikeCompany = new Company("14", "Bike Company", "bike.bike");
		Item bike = new Item("103", "Super Bike", 1, "1000", "USD", bikeCompany);
		map.put("66", Arrays.asList(bike));
	}

	public List<Item> getItemsByOrderId(String orderId) {
		return map.get(orderId);
	}
}
