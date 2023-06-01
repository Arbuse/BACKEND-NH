package com.example.PocektHistory.pocketHistory.user.controller;


import com.example.PocektHistory.pocketHistory.user.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.PocektHistory.pocketHistory.user.service.ProductService;

import java.util.concurrent.ExecutionException;



@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public void saveProduct(@RequestBody Product product) throws ExecutionException, InterruptedException {
        productService.saveProduct(product);
    }

    @GetMapping("/welcome")
    public String t1() throws ExecutionException, InterruptedException {
        return "działa serwer ez";

    }

}
