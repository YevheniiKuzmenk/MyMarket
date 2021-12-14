package com.gmail.kuzmenk.yevhenii.yevhenii.service;

import com.gmail.kuzmenk.yevhenii.yevhenii.models.*;
import com.gmail.kuzmenk.yevhenii.yevhenii.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ProductService {
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TrademarkRepository trademarkRepository;
    @Autowired
    private AppointmentsRepository appointmentsRepository;
    @Autowired
    private ProductNumberRepository productNumberRepository;
    @Autowired
    private OrderrRepository orderrRepository;

    @Transactional
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    @Transactional
    public void addProducted(MultipartFile photo, long appointmentsId, long id, String specification, String name, double weight, double price) throws IOException {
        Trademark trademark = null;
        Appointments appointments = null;
        if (id != -1) trademark = trademarkRepository.getById(id);
        if (id != -1) appointments = appointmentsRepository.getById(appointmentsId);
        Product product = new Product(appointments, trademark, name, weight, price, specification);

        if (!photo.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String photoName = uuidFile + "." + photo.getOriginalFilename();
            photo.transferTo(new File(uploadPath + "/" + photoName));
            product.setPhotoName(photoName);
        }

        productRepository.save(product);
    }

    @Transactional
    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Product getProductById(long id) {
        return productRepository.getById(id);
    }

    @Transactional
    public void deleteById(long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void addTrademark(Trademark trademark) {
        trademarkRepository.save(trademark);
    }

    @Transactional
    public Iterable<Trademark> findAllTrademark() {
        return trademarkRepository.findAll();
    }

    @Transactional
    public Optional<Trademark> findTrademarkById(long id) {
        return trademarkRepository.findById(id);
    }

    @Transactional
    public Optional<Trademark> findTrademarkByName(String name) {
        return trademarkRepository.findTrademarkByName(name);
    }

    @Transactional
    public void trademarkDelete(long id) {
        trademarkRepository.deleteById(id);
    }

    @Transactional
    public Iterable<Product> findProductsByTrademark(Optional<Trademark> trademark) {
        return productRepository.findByTrademark(trademark);
    }

    @Transactional
    public void addAppointments(Appointments appointments) {
        appointmentsRepository.save(appointments);
    }

    @Transactional
    public Iterable<Appointments> findAllAppointments() {
        return appointmentsRepository.findAll();
    }

    @Transactional
    public Optional<Appointments> findAppointmentsById(long id) {
        return appointmentsRepository.findById(id);
    }

    @Transactional
    public Optional<Appointments> findAppointmentsByName(String name) {
        return appointmentsRepository.findAppointmentsByName(name);
    }

    @Transactional
    public void appointmentsDelete(long id) {
        appointmentsRepository.deleteById(id);
    }

    @Transactional
    public Iterable<Product> findProductsByAppointments(Optional<Appointments> appointments) {
        return productRepository.findByAppointments(appointments);
    }

    @Transactional
    public Iterable<Product> findProductsByPattern(String pattern) {
        return productRepository.findProductByPattern(pattern);
    }

//    @Transactional
//    public void addProduct(Product product) {
//        productRepository.save(product);
//    }

//    @Transactional
//    public void basketAdd(Long customerId, Long productId, int number){
//        Basket basket = new Basket(customerId, productId, number);
//        basketRepository.save(basket);
//    }
//
//    @Transactional
//    public Iterable<Product> findProductsByCustomer(Long customerId) {
//        Iterable<Long> productsId=basketRepository.findAllByCustomerId(customerId);
//        return productRepository.findAllById(productsId);
//    }
//
//    @Transactional
//    public Iterable<Integer> findAllNumbersByProductId(Iterable<Long> productsId) {
//        return basketRepository.findAllNumbersByProductId(productsId);
//
//    }

    @Transactional
    public void productNumberAdd(ProductNumber productNumber) {
        productNumberRepository.save(productNumber);
    }

    @Transactional
    public List<ProductNumber> findProductNumberByCustomer(Customer customer) {
        return productNumberRepository.findAllByCustomer(customer);
    }

    @Transactional
    public ProductNumber findProductNumberById(ProductNumberKey id) {
        return productNumberRepository.findProductNumberById(id);
    }

    @Transactional
    public void productNumberRemove(Long id, Customer customer) {
        Product product = productRepository.getById(id);
        ProductNumber productNumber = productNumberRepository.findProductNumberByProduct(product, customer);
        productNumberRepository.delete(productNumber);
    }

    @Transactional
    public void productNumbersRemove(List<ProductNumber> productNumbers) {
        productNumberRepository.deleteAllInBatch(productNumbers);
    }

    @Transactional
    public void orderrAdd(Customer customer) {
        List<ProductNumber> productNumbersInBasket = productNumberRepository.findAllByCustomer(customer);
        List<Orderr> orderrs = new ArrayList<Orderr>();
        Date invoice = new Date();
        for (ProductNumber productNumber : productNumbersInBasket) {
            Product product = productNumber.getProduct();
            int number = productNumber.getNumber();
            Orderr orderr = new Orderr(customer, product, number, invoice);
            orderrs.add(orderr);
        }
        orderrRepository.saveAll(orderrs);
        productNumberRepository.deleteAllInBatch(productNumbersInBasket);

    }

    @Transactional
    public List<Orderr> findAllOrderr() {
        return orderrRepository.findAll();
    }


    @Transactional
    public double getTotalWeight(List<ProductNumber> productNumbers) {
        double totalWeight = 0;
        for (ProductNumber productNumber : productNumbers) {
            totalWeight = totalWeight + productNumber.getProduct().getWeight() * productNumber.getNumber();
        }
        return totalWeight;
    }

    @Transactional
    public double getTotalPrise(List<ProductNumber> productNumbers) {
        double totalPrise = 0;
        for (ProductNumber productNumber : productNumbers) {
            totalPrise = totalPrise + productNumber.getProduct().getPrice() * productNumber.getNumber();

        }
        return totalPrise;
    }

    @Transactional
    public void addProductNumber(Customer customer, Long id, int number) {
        Product productNew = productRepository.getById(id);
        List<ProductNumber> productNumbersInBasket = productNumberRepository.findAllByCustomer(customer);
        ProductNumber productNumberNew = new ProductNumber(customer, productNew, number);
        for (ProductNumber productNumber : productNumbersInBasket) {
            if (productNumber.getProduct() == productNew) {
                productNumberNew = productNumberRepository.findProductNumberById(productNumber.getId());
                productNumberNew.setNumber(productNumber.getNumber() + number);
            }
        }
        productNumberRepository.save(productNumberNew);
    }

    @Transactional
    public void productRemove(Long id) {
        Product product = productRepository.getById(id);
        File photo = new File(uploadPath + "/" + product.getPhotoName());
        photo.delete();
        productRepository.delete(product);
    }

    @Transactional
    public void productEdit(long id, MultipartFile newPhoto, String specification, String name, double weight, double price) throws IOException {
        Product product = productRepository.getById(id);
        product.setName(name);
        product.setWeight(weight);
        product.setPrice(price);
        product.setSpecification(specification);
        if (!newPhoto.isEmpty()) {
            File photo = new File(uploadPath + "/" + product.getPhotoName());
            photo.delete();
            String uuidFile = UUID.randomUUID().toString();
            String photoName = uuidFile + "." + newPhoto.getOriginalFilename();
            newPhoto.transferTo(new File(uploadPath + "/" + photoName));
            product.setPhotoName(photoName);
        }
        productRepository.save(product);
    }


}
