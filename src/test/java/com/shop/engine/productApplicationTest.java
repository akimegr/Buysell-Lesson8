package com.shop.engine;

import com.shop.engine.models.FranchiseProduct;
import com.shop.engine.models.Product;
import com.shop.engine.models.User;
import com.shop.engine.models.enums.Role;
import com.shop.engine.repositories.UserRepository;
import com.shop.engine.userServices.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class productApplicationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    public Product getTestProduct() {
        Product product = new Product();
        product.setId(16l);
        product.setCity("Минск");
        product.setDescription("Топ");
        product.setPreviewImageId(4l);
        product.setPrice(1000);
        product.setTitle("Машинка");
        product.setUser(userRepository.findById(1l).get());
        product.setIsFranchise(false);
        product.setLink(null);
        product.setSecretMessage("123450");
        return product;
    }


    @Test
    public void testListProductsWithWrongInSearch() {
        List<Product> allProducts = productService.listProducts(null);
        boolean check = false;
        if(allProducts.size()!=1) {
            check = true;
        }
        Assertions.assertTrue(check);
    }

    @Test
    public void testListFranchiseProductsWithWrongInSearch() {
        List<Product> allProducts = productService.listProducts(null);
        boolean check = false;
        if(allProducts.size()!=1) {
            check = true;
        }
        Assertions.assertTrue(check);
    }

    @Test
    public void checkFindFranchiseProductById() {
        FranchiseProduct franchiseProduct = this.productService
                .getFranchiseProductById(14l);
        Assertions.assertNotNull(franchiseProduct);
    }




}
