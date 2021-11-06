package com.fpt.MobileStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class MainController implements WebMvcConfigurer {

	@Autowired
	private ProductService service;
	
	@RequestMapping("/")
	public String viewHomePage(Model model) {
	    List<Product> listProducts = service.listAll();
	    model.addAttribute("listProducts", listProducts);
	     
	    return "index";
	}
	
	@GetMapping("/login")
	public String loginPage(HttpServletRequest request, @RequestParam(required = false) Boolean error) {
		request.setAttribute("error", error);
		return "login";
	}
	
	@RequestMapping("/detail/{product_id}")
	public ModelAndView showEditProductPage(@PathVariable(name = "product_id") int product_id) {
		ModelAndView mav = new ModelAndView("detail");
		Product product = service.get(product_id);
		mav.addObject("product", product);
		
		return mav;
	}
	
	@RequestMapping("/admin")
	public String showNewProductForm(Model model) {
		Product product = new Product();
		model.addAttribute("product",product);
		
		return "admin";
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") Product product) {
		service.save(product);
		
		return "redirect:/";
	}
	
	@PostMapping("/errorLogin")
	public String checkLogin() {
			return "errorLogin";
	}
}
