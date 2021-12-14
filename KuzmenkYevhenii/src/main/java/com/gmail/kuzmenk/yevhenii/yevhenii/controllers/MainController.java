package com.gmail.kuzmenk.yevhenii.yevhenii.controllers;

import com.gmail.kuzmenk.yevhenii.yevhenii.models.*;
import com.gmail.kuzmenk.yevhenii.yevhenii.service.CustomService;
import com.gmail.kuzmenk.yevhenii.yevhenii.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private ProductService productService;
    @Autowired
    private CustomService customService;

    @GetMapping("/")
    public String index(Model model) {
        Iterable<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customService.getUserByLogin(user.getUsername());
        List<ProductNumber> productNumbers = productService.findProductNumberByCustomer(customer);
        model.addAttribute("customer", customer);
        model.addAttribute("productNumbers", productNumbers);
        model.addAttribute("totalWeight", productService.getTotalWeight(productNumbers));
        model.addAttribute("totalPrise", productService.getTotalPrise(productNumbers));
        return "home";
    }

    @GetMapping("/productNumber/remove/{id}")
    public String productNumberRemove(@PathVariable(value = "id") long id, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customService.getUserByLogin(user.getUsername());
        productService.productNumberRemove(id, customer);
        return "redirect:/home";
    }

    @PostMapping("/productNumber/add/{id}")
    public String productNumberAdd(@PathVariable(value = "id") long id, @RequestParam("number") int number, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customService.getUserByLogin(user.getUsername());
        productService.addProductNumber(customer, id, number);
        return "redirect:/";
    }

    @RequestMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("appointments", productService.findAllAppointments());
        model.addAttribute("trademark", productService.findAllTrademark());
        model.addAttribute("customer", customService.findAllCustomer());
        model.addAttribute("orderr", productService.findAllOrderr());
        return "admin";
    }

    @PostMapping("/registration")
    public String createNewUser(@RequestParam String username,
                                @RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String email,
                                @RequestParam String phoneNumber,
                                @RequestParam String password, Model model) {
        if (customService.existsByLogin(username)) {
            model.addAttribute("exists", true);
            return "registration";
        }
        customService.addCcustomer(phoneNumber, username, password, firstName, lastName, email);
        return "login";
    }

    @GetMapping("/registration")
    public String registrPage(Model model) {
        return "/registration";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "/login";
    }

    @GetMapping("/unauthorized")
    public String unautorized(Model model) {
        return "/unauthorized";
    }

    @PostMapping("/trademark/add")
    public String trademarkAdd(@RequestParam String name, Model model) {
        productService.addTrademark(new Trademark(name));
        return "redirect:/product/add";
    }

    @PostMapping("/appointments/add")
    public String appointmentsAdd(@RequestParam String name, Model model) {
        productService.addAppointments(new Appointments(name));
        return "redirect:/product/add";
    }

    @GetMapping("/trademark/remove/{id}")
    public String trademarkRemove(@PathVariable(value = "id") long id, Model model) {
        productService.trademarkDelete(id);
        return "redirect:/admin";
    }

    @GetMapping("/appointments/remove/{id}")
    public String appointmentsRemove(@PathVariable(value = "id") long id, Model model) {
        productService.appointmentsDelete(id);
        return "redirect:/admin";
    }

    @GetMapping("/customer/remove/{id}")
    public String customerRemove(@PathVariable(value = "id") long id, Model model) {
        customService.customerDelete(id);
        return "redirect:/admin";
    }

    @GetMapping("/order")
    public String order(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customService.getUserByLogin(user.getUsername());
        productService.orderrAdd(customer);
        return "redirect:/";
    }


}
