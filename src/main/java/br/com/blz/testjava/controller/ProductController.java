package br.com.blz.testjava.controller;

import br.com.blz.testjava.domain.Product;
import br.com.blz.testjava.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;


import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);

        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{sku}")
    public ResponseEntity<Void> updateProduct(@RequestBody Product product) {
        productService.updateProduct(product, product.getSku());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{sku}")
    public ResponseEntity<Product> getProductBySku(@PathVariable int sku) {
        Optional<Product> product = productService.getProductBySku(sku);

        return product.map(ResponseEntity::ok)
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<Void> deleteProductBySku(@PathVariable int sku) {
        productService.deleteProductBySku(sku);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
