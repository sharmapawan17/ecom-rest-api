package com.ecommerce.product;

import com.ecommerce.config.WebSecurityConfig;
import com.ecommerce.jwt.JwtRequest;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.models.ProductRequest;
import com.ecommerce.user.access.LoggedInUser;
import com.ecommerce.user.access.UserAuthorityRequest;
import com.ecommerce.user.account.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String HOST = "http://localhost:";

    @BeforeEach
    void before(){
        token = null;
        registerUser();
        authenticateUser();
        authorizeAdmin();
    }

    @AfterEach
    void after(){
        deactivateUser();
    }

    private String token;

    @Test
    void testProductCreationHappyPath() throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer "+token);

            ProductRequest request = new ProductRequest();
            request.setSubCategoryId(1L);
            request.setName("testProduct");


            BufferedImage image = ImageIO.read(new File("/images/samsung10.jpg"));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", out);

//            MultipartFile[] file = {out.toByteArray()};
//            request.setImageFiles(file);

            HttpEntity<ProductRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<Product> productResponseEntity = restTemplate.postForEntity(HOST+port + "/product", entity, Product.class);
            assertNotNull(productResponseEntity.getBody());
        }catch (Exception e){
            System.out.println(e);
        }
    }

    void registerUser(){
        UserDTO userDTO = new UserDTO();
        userDTO.setDeviceType("Android");
        userDTO.setFcmToken("gnrskhbrdakjfsfbdshjfnSFS87586");
        userDTO.setEmail("test@gmail.com");
        userDTO.setFirstname("test");
        userDTO.setLastname("test");
        userDTO.setPassword("12345");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(HOST+port + "/user/register", userDTO, String.class);
        assertNotNull(responseEntity);
    }

    void deactivateUser(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+token);
        HttpEntity entity = new HttpEntity(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(HOST+port + "/user/deactivate?email=test@gmail.com", entity, String.class);
        assertNotNull(responseEntity);
    }

    private void authorizeAdmin() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+token);

        UserAuthorityRequest request = new UserAuthorityRequest();
        request.setRolename("APP_ADMIN");
        request.setUsername("test@gmail.com");

        HttpEntity<UserAuthorityRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(HOST+port + "/user/role", entity, String.class);
        assertNotNull(responseEntity);
    }

    void authenticateUser(){
        JwtRequest request = new JwtRequest();
        request.setEmail("test@gmail.com");
        request.setPassword("12345");
        ResponseEntity<LoggedInUser> responseEntity = restTemplate.postForEntity(HOST+port + "/authenticate", request, LoggedInUser.class);
        token = responseEntity.getBody().getToken();
        assertNotNull(token);
    }
}