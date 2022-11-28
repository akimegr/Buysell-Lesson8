package com.shop.engine;

import com.shop.engine.models.FranchiseProduct;
import com.shop.engine.models.PrincipalOpen;
import com.shop.engine.models.Product;
import com.shop.engine.models.User;
import com.shop.engine.models.enums.Role;
import com.shop.engine.repositories.UserRepository;
import com.shop.engine.userServices.ProductService;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class productApplicationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    public ProductService productServiceMockito;

    private Product product;

    private PrincipalOpen principalExist;

    private PrincipalOpen principalNew;


    @Before
    public void setUp() {
        productServiceMockito = Mockito.mock(ProductService.class);

        principalExist.setName("akim.egor2013@yandex.ru");

        principalNew.setName("akimEgor@mail.ru");

        product = new Product();
        product.setId(9123123l);
        product.setCity("Минск");
        product.setDescription("Топ");
        product.setPreviewImageId(4l);
        product.setPrice(1000);
        product.setTitle("Машинка");
        product.setUser(userRepository.findById(1l).get());
        product.setIsFranchise(false);
        product.setLink(null);
        product.setSecretMessage("123450");
    }

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

    public Product getTestProduct(int price) {
        Product product = new Product();
        product.setId(16l);
        product.setCity("Минск");
        product.setDescription("Топ");
        product.setPreviewImageId(4l);
        product.setPrice(price);
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
    public void testSaveAndDeleteTestProduct() throws IOException {
        product = this.getTestProduct();
        MultipartFile multipartFile = new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };
        principalExist = new PrincipalOpen();
        principalNew = new PrincipalOpen();
        principalExist.setName("akim.egor2013@mail.ru");

        principalNew.setName("akimEgor@mail.ru");
        productService.saveProduct(principalExist, product, multipartFile,multipartFile,multipartFile);
        Product checkProduct = productService.getProductById(product.getId());
        Assertions.assertNotNull(checkProduct);
        Assertions.assertEquals("Минск", checkProduct.getCity());
        Assertions.assertEquals("Топ", checkProduct.getDescription());
        Assertions.assertEquals("Машинка", checkProduct.getTitle());
        Assertions.assertEquals("123450", checkProduct.getSecretMessage());
        Assertions.assertEquals(1000, checkProduct.getPrice());
        List<User> customerOnProduct = userRepository.findAll().stream()
                .filter(user -> user.getId()==product.getUser().getId()).collect(Collectors.toList());
        Assertions.assertTrue(!customerOnProduct.isEmpty());
        Assertions.assertFalse(checkProduct.getIsFranchise());
        Assertions.assertTrue(checkProduct.getLink()==null);

//        Mockito.verify(productService, Mockito.times(1))
//                .getProductById(product.getId());
        productService.deleteProduct(product.getUser(), product.getId());
        Product checkDeletedProduct = productService.getProductById(product.getId());
        Assertions.assertNull(checkDeletedProduct);

    }

    @Test
    public void canBuyProduct() {
        product = productService.getProductById(10l);
        User testForBuy = new User();
        testForBuy.setDollars(product.getPrice()+10.3);
        boolean canBuy = productService.canBuy(testForBuy, product.getId());
        Assertions.assertTrue(canBuy);
    }





}
