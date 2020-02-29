package com.ecommerce.product;

import com.ecommerce.aspect.Track;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.entity.ProductImageEntity;
import com.ecommerce.product.models.ProductRequest;
import com.ecommerce.product.models.ProductResponse;
import com.ecommerce.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/product")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class.getName());
    private static final String PRODUCT_IMAGES_LOCATION = "product-images/";
    @Autowired
    private ProductService productService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private Validator productValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(productValidator);
    }

    @Track
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Product createProduct(@RequestPart ProductRequest product,
                                 @RequestPart(required = false) MultipartFile [] files) {

        Product out = productService.saveProduct(product);
        handleFileUpload(out.getId(), files);
        return out;
    }

    @Track
    @GetMapping(value = "/{id}")
    public Product get(@PathVariable("id") long id) {
        return productService.getProduct(id);
    }

    @Track
    @GetMapping
    public List<ProductResponse> getAll() {
        return productService.getAllProducts();
    }

    @Track
    @PostMapping(value = "/{id}")
    public Product edit(@PathVariable("id") long id, @RequestBody @Valid ProductRequest product) {
        return productService.updateProduct(id, product);
    }

    @Track
    @PostMapping(value = "/{id}/uploadimage")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<ProductImageEntity> handleFileUpload(@PathVariable("id") Long id, @RequestParam("file") MultipartFile[] files) {
        String path = PRODUCT_IMAGES_LOCATION + id;
        List<ProductImageEntity> outList = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            String filename = storageService.store(files[i], path);
            ProductImageEntity imageEntity = productService.addProductImage(id, filename);
            outList.add(imageEntity);
        }
        return outList;
    }

    @Track
    @GetMapping("/{id}/images")
    public List<ProductImageEntity> viewImages(@PathVariable("id") String productId) {
        return productService.getProductImages(Long.parseLong(productId));
    }

    @Track
    @GetMapping("/image/{id}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable("id") long imageId) {
        ProductImageEntity image = productService.getImage(imageId);
        // Relative path to StorageProperties.rootLocation
        String path = PRODUCT_IMAGES_LOCATION + image.getProductId() + "/";

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
