package ru.geekbrains.springbootlesson.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.springbootlesson.exception.NotFondException;
import ru.geekbrains.springbootlesson.persist.entity.Product;
import ru.geekbrains.springbootlesson.service.ProductService;

import java.util.List;

@RequestMapping("/api/v1/product")
@RestController
public class ProductResource {
    private ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    //Не работает xml
    //Resolved [org.springframework.http.converter.HttpMessageNotWritableException:
    // No converter for [class java.util.ArrayList] with preset Content-Type 'null']
    @GetMapping(path = "/all", produces = "application/json")
    public List<Product> findAll(){
        return productService.findAll();
    }

    @GetMapping(path = "/{id}/id")
    public Product findById(@PathVariable("id") long id){
        return productService.findById(id).orElseThrow(() -> new NotFondException("Not found"));
    }
// че такое @RequestBody? распознать тело запроса как параметр в данном случае продукт.
// за один раз можно передать только один параметр.
    // @RequestBody тело запроса необходимо присвоить в параметр product
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody Product product){
        if(product.getId() != null) {
            throw new IllegalArgumentException("ID is set automatically");
        }
        productService.save(product);
        return new ResponseEntity<>("product created", HttpStatus.OK);
    }

    @PutMapping
    public void updateProduct(@RequestBody Product product){
        productService.save(product);
    }

    @DeleteMapping(path = "/{id}/id")
    public void deleteProduct(@PathVariable("id") long id){
        productService.deleteProductByIe(id);
    }

}
