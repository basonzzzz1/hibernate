package com.be.controller;

import com.be.model.*;
import com.be.service.ICategoryService;
import com.be.service.IProductService;
import com.be.service.impl.AccountServiceImpl;
import com.be.service.impl.LikeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ProductController {
    @Autowired
    IProductService productService;
    @Autowired
    ICategoryService categoryService;
    @Autowired
    LikeServiceImpl likeService;
    @Autowired
    AccountServiceImpl accountService;
    @GetMapping("/like/{productId}/{accountId}")
    @ResponseBody
    public ResponseEntity<Boolean> likeProduct(@PathVariable int productId, @PathVariable int accountId) {
        Account account = accountService.findById(accountId);
        Product product = productService.findById(productId);
        if (account != null) {
            List<ProductLike> existingLikes = likeService.findAllByProductAndAccount(product, account);
            if (existingLikes.size() > 0) {
                return ResponseEntity.ok(true);
            }
        }
        return ResponseEntity.ok(false);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductQuantity(@PathVariable int id, @RequestBody Product updatedProduct) {
        try {
            Product product = productService.findById(id);

            if (product == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            product.setQuantity(updatedProduct.getQuantity());
            productService.save(product);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping
    public ResponseEntity<?> createOrderDetail(@RequestBody Product product) {
        productService.save(product);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/like/{productId}/{accountId}")
    @ResponseBody
    public ResponseEntity<String> likeAdd(@PathVariable int productId, @PathVariable int accountId) {
        List<ProductLike > productLikes = likeService.getAll();
        Account account = accountService.findById(accountId);
        Product product = productService.findById(productId);
        ProductLike productLike = likeService.findByProductAndAccount(product, account);
        for (int i = 0; i < productLikes.size(); i++) {
            if(productLikes.get(i).getProduct() == product && productLikes.get(i).getAccount() == account){
                likeService.delete(productLike);
                return ResponseEntity.ok("Your like product!");
            }
        }
        likeService.save(new ProductLike(product, account, new Date()));
        return ResponseEntity.ok("Your like product!");
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAll() {
        List<Product> productList = productService.getAll();
        if (productList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> findOneProduct(@PathVariable int id) {
        Product product = productService.findById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/products/search/{name}")
    public ResponseEntity<List<Product>> searchByName(@PathVariable String name) {
        List<Product> productList = productService.searchByName(name);
        if (productList.isEmpty() || productList == null) {
            productList = productService.getAll();
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> Categories() {
        List<Category> categories = categoryService.getAll();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/productByCategory/{idCategory}")
    ResponseEntity<List<Product>> productByCategory(@PathVariable int idCategory) {
        List<Product> productList = productService.findByCategory(idCategory);
        if (productList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

}
