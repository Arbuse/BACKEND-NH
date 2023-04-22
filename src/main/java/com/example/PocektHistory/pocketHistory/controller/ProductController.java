package com.example.PocektHistory.pocketHistory.controller;


import com.example.PocektHistory.pocketHistory.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.PocektHistory.pocketHistory.service.ProductService;

import java.util.concurrent.ExecutionException;



@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public String saveProduct(@RequestBody Product product) throws ExecutionException, InterruptedException {
        return productService.saveProduct(product);
    }

    @GetMapping("/welcome")
    public String t1() throws ExecutionException, InterruptedException {
        return "kurwy na bicie ziomal";

    }

}
