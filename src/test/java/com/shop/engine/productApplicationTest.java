package com.shop.engine;

import com.shop.engine.models.PrincipalOpen;
import com.shop.engine.models.Product;
import com.shop.engine.models.User;
import com.shop.engine.repositories.UserRepository;
import com.shop.engine.userServices.ProductService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    private PrincipalOpen principalNewOwner;

    private PrincipalOpen principalNew;


    @Before
    public void setUp() {
        productServiceMockito = Mockito.mock(ProductService.class);

        principalNewOwner.setName("akim.egor2013@yandex.ru");

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
        principalNewOwner = new PrincipalOpen();
        principalNew = new PrincipalOpen();
        principalNewOwner.setName("akim.egor2013@mail.ru");

        principalNew.setName("akimEgor@mail.ru");
        productService.saveProduct(principalNewOwner, product, multipartFile,multipartFile,multipartFile);
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

    @Test
    public void buyCarTest() {
        product = productService.getProductById(22l);
        User ownerUser = userRepository.findById(product.getUser().getId()).get();
        User customerUser = userRepository.findById(1l).get();
        principalNewOwner = new PrincipalOpen();
        principalNewOwner.setName(customerUser.getEmail());
        PrincipalOpen principalOldOwner = new PrincipalOpen();
        principalOldOwner.setName(ownerUser.getEmail());
        Double moneyNewOwner = customerUser.getDollars();
        Double moneyOldOwner = ownerUser.getDollars();
        int productPrice = product.getPrice();
        productService.buyCar(customerUser, 22l, principalNewOwner);
        product = productService.getProductById(22l);
        Double newMoneyNewOwner = userRepository.findById(customerUser.getId()).get().getDollars();
        Double newMoneyOldOwner = userRepository.findById(ownerUser.getId()).get().getDollars();
        Assertions.assertEquals(newMoneyNewOwner,moneyNewOwner - productPrice, 5);
        Assertions.assertEquals(newMoneyOldOwner,moneyOldOwner + productPrice, 5);
        productService.buyCar(ownerUser, 22l, principalOldOwner);
        User userForRevertSumFirstOwner = userRepository.findById(3l).get();
        User userForRevertSumSecondOwner = userRepository.findById(1l).get();
        userForRevertSumFirstOwner.setDollars(userForRevertSumFirstOwner.getDollars()+productPrice);
        userForRevertSumSecondOwner.setDollars(userForRevertSumSecondOwner.getDollars()+productPrice);
        userRepository.save(userForRevertSumFirstOwner);
        userRepository.save(userForRevertSumSecondOwner);
        User ownerUser2 = userRepository.findById(3l).get();
        User customerUser2 = userRepository.findById(1l).get();

        Double checkOldCustomerMoney = customerUser2.getDollars();
        Double checkOldOwnerMoney = ownerUser2.getDollars();

        Assertions.assertEquals(checkOldOwnerMoney,moneyOldOwner, 5);
        Assertions.assertEquals(checkOldCustomerMoney,moneyNewOwner , 5);

    }





}
