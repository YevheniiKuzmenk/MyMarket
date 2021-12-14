package com.gmail.kuzmenk.yevhenii.yevhenii.controllers;

import com.gmail.kuzmenk.yevhenii.yevhenii.models.Appointments;
import com.gmail.kuzmenk.yevhenii.yevhenii.models.Customer;
import com.gmail.kuzmenk.yevhenii.yevhenii.models.Product;
import com.gmail.kuzmenk.yevhenii.yevhenii.models.Trademark;
import com.gmail.kuzmenk.yevhenii.yevhenii.service.CustomService;
import com.gmail.kuzmenk.yevhenii.yevhenii.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
public class ProductController {
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomService customService;

    @GetMapping("/product/add")
    public String productAddPage(Model model) {
        model.addAttribute("trademark", productService.findAllTrademark());
        model.addAttribute("appointments", productService.findAllAppointments());
        return "/product_add";
    }

    @PostMapping("/product/add")
    public String productAdd(@RequestParam("photo") MultipartFile photo,
                             @RequestParam(value = "appointmentsId") long appointmentsId,
                             @RequestParam(value = "trademarkId") long id,
                             @RequestParam String specification,
                             @RequestParam String name,
                             @RequestParam double weight,
                             @RequestParam double price, Model model) throws IOException {
        productService.addProducted(photo, appointmentsId, id, specification, name, weight, price);
        return "redirect:/product/add";
    }

    @GetMapping("/product/remove/{id}")
    public String productDelete(@PathVariable(value = "id") long id, Model model) {
        productService.productRemove(id);
        return "redirect:/";
    }

    @GetMapping("/product/edit/{id}")
    public String productEdit(@PathVariable(value = "id") long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "productEdit";
    }

    @PostMapping("/product/edit/{id}")
    public String productUpdate(@PathVariable(value = "id") long id,
                                @RequestParam("newPhoto") MultipartFile newPhoto,
                                @RequestParam String specification,
                                @RequestParam String name,
                                @RequestParam double weight,
                                @RequestParam double price, Model model) throws IOException {
        productService.productEdit(id, newPhoto, specification, name, weight, price);
        return "redirect:/";
    }

    @GetMapping("/product/details/{id}")
    public String productDetails(@PathVariable(value = "id") long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "productDetails";
    }

    @GetMapping("/trademark/{trademarkName}")
    public String trademarkList(@PathVariable(value = "trademarkName") String trademarkName, Model model) {
        Optional<Trademark> trademark = productService.findTrademarkByName(trademarkName);
        Iterable<Product> products = productService.findProductsByTrademark(trademark);
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/appointments/{appointmentsName}")
    public String appointmentsList(@PathVariable(value = "appointmentsName") String appointmentsName, Model model) {
        Optional<Appointments> appointments = productService.findAppointmentsByName(appointmentsName);
        Iterable<Product> products = productService.findProductsByAppointments(appointments);
        model.addAttribute("products", products);

        return "index";
    }

    @PostMapping("/admin/customer/edit/{id}")
    public String customerAdminUpdate(@PathVariable(value = "id") long id,
                                      @RequestParam("login") String login,
                                      @RequestParam("firstName") String firstName,
                                      @RequestParam("lastName") String lastName,
                                      @RequestParam("email") String email,
                                      @RequestParam("phoneNumber") String phoneNumber,
                                      Model model) {
        Customer customer = customService.findCustomerById(id);
        customer.setLogin(login);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customService.customerAdd(customer);
        return "redirect:/admin";
    }

    @PostMapping("/customer/edit/{id}")
    public String customerUpdate(@PathVariable(value = "id") long id,
                                 @RequestParam("login") String login,
                                 @RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastName,
                                 @RequestParam("email") String email,
                                 @RequestParam("phoneNumber") String phoneNumber,
                                 Model model) {
        Customer customer = customService.findCustomerById(id);
        customer.setLogin(login);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customService.customerAdd(customer);
        return "redirect:/home";
    }

    @GetMapping("/admin/customer/edit/{id}")
    public String customerUpdatePage(@PathVariable(value = "id") long id, Model model) {
        Customer customer = customService.findCustomerById(id);
        model.addAttribute("customer", customer);
        return "customerEdit";

    }

    @PostMapping("/search")
    public String search(@RequestParam("pattern") String pattern, Model model) {
        Iterable<Product> products = productService.findProductsByPattern(pattern);
        model.addAttribute("products", products);
        return "index";
    }


}
