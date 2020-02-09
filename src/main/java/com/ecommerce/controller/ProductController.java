package com.ecommerce.controller;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.ProductImage;
import com.ecommerce.service.EcommerceService;
import com.ecommerce.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class.getName());
    @Autowired
    private EcommerceService ecommerceService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private Validator productValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(productValidator);
    }

    @PostMapping
    public Product create(@RequestBody @Valid Product product) {
        return ecommerceService.saveProduct(product);
    }

    @GetMapping(value = "/{id}")
    public Product get(@PathVariable("id") long id) {
        return ecommerceService.getProduct(id);
    }

    @PostMapping(value = "/{id}")
    public Product edit(@PathVariable("id") long id, @RequestBody @Valid Product product) {
        Product updatedProduct = ecommerceService.getProduct(id);
        updatedProduct.setName(product.getName());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setDescription(product.getDescription());
        return ecommerceService.saveProduct(updatedProduct);
    }

    @PostMapping("/{id}/uploadimage")
    public String handleFileUpload(@PathVariable("id") String id, @RequestParam("file") MultipartFile file) {

        // Relative path to the rootLocation in storageService
        String path = "/product-images/" + id;
        String filename = storageService.store(file, path);

        return ecommerceService.addProductImage(id, filename);
    }

    @GetMapping("/image/{id}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable("id") long id) {

        List<ProductImage> productImages = ecommerceService.getProductImages(id);
        ProductImage image = productImages.stream().findFirst().get();
        // Relative path to StorageProperties.rootLocation
        String path = "product-images/" + image.getProductId() + "/";

        Resource file = storageService.loadAsResource(path + image.getPath());
        String mimeType = "image/png";
        try {
            mimeType = file.getURL().openConnection().getContentType();
        } catch (IOException e) {
            log.error("Can't get file mimeType. " + e.getMessage());
        }
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, mimeType)
                .body(file);
    }
}
