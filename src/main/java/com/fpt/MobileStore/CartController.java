package com.fpt.MobileStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("cart")
public class CartController {

	@Autowired
	private ProductService service;

	@RequestMapping(value = "buy/{product_id}", method = RequestMethod.GET)
	public String buy(@PathVariable("product_id") int product_id, ModelMap modelMap, HttpSession session) {

		if (session.getAttribute("cart") == null) {
			List<Item> cart = new ArrayList<Item>();
			cart.add(new Item(service.get(product_id), 1));
			session.setAttribute("cart", cart);
		} else {
			List<Item> cart = (List<Item>) session.getAttribute("cart");
			int index = IsExist(product_id, cart);
			if (index == -1) {
				cart.add(new Item(service.get(product_id), 1));
			} else {

				int quantity = cart.get(index).getQuantity() + 1;
				cart.get(index).setQuantity(quantity);
			}
			session.setAttribute("cart", cart);
		}
		modelMap.put("total", total(session));
		return "shoppingcart";
	}

	private int IsExist(int product_id, List<Item> cart) {
		for (int i = 0; i < cart.size(); i++) {
			if (cart.get(i).getProduct().getProduct_id() == product_id) {
				return i;
			}
		}
		return -1;
	}

	@RequestMapping(value = "remove/{product_id}", method = RequestMethod.GET)
	public String remove(@PathVariable("product_id") int product_id, HttpSession session, ModelMap modelMap) {
		List<Item> cart = (List<Item>) session.getAttribute("cart");
		int index = IsExist(product_id, cart);
		cart.remove(index);
		session.setAttribute("cart", cart);
		modelMap.put("total", total(session));
		return "shoppingcart";
	}

	@RequestMapping(value = "/removeall", method = RequestMethod.GET)
	public String remove(HttpSession session) {
		List<Item> cart = (List<Item>) session.getAttribute("cart");
			cart.removeAll(cart);
			return "index";
	}

	private double total(HttpSession session) {
		List<Item> cart = (List<Item>) session.getAttribute("cart");
		double s = 0;
		for (Item item : cart) {
			s += item.getQuantity() * item.getProduct().getPrice();
		}
		return s;
	}

}
