package com.shop.engine.userServices;

import com.shop.engine.models.FranchiseProduct;
import com.shop.engine.models.Image;
import com.shop.engine.models.Product;
import com.shop.engine.models.User;
import com.shop.engine.repositories.FranchiseRepository;
import com.shop.engine.repositories.ProductRepository;
import com.shop.engine.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final FranchiseRepository franchiseRepository;
    private final UserRepository userRepository;

    public List<Product> listProducts(String title) {
        if (title != null) return productRepository.findByTitle(title);
        return productRepository.findAll();
    }

    public List<FranchiseProduct> listFranchiseProducts(String title) {
        if (title != null) return franchiseRepository.findByTitle(title);
        return franchiseRepository.findAll();
    }

    public void saveFranchiseProduct(Principal principal, FranchiseProduct franchiseProduct) {
        log.info("Saving new Product. Title: {}; Author email: {}", franchiseProduct.getTitle());
        franchiseRepository.save(franchiseProduct);
    }

    public FranchiseProduct getFranchiseProductById(Long id) {
        String result = franchiseRepository.findById(id).isPresent() ?
                franchiseRepository.findById(id).get().getTitle() : "CAN'T TAKE";
        log.info("Take product {}", result);
        return franchiseRepository.findById(id).get();
    }

    public void saveFranchiseToCatalog(FranchiseProduct franchiseProduct, Principal principal) {
        Product product = new Product();
        product.setUser(getUserByPrincipal(principal));
        product.setIsFranchise(true);
        product.setTitle(franchiseProduct.getTitle());
        String [] array = franchiseProduct.getPrice().split(" ");
        Integer price = 0;
        if(array.length==3){
            String priceString = array[0]+array[1];
            price = (int)Double.parseDouble(priceString);
        }
        else if (array.length==2) {
            String priceString = array[0];
            price = (int)Double.parseDouble(priceString);
        }
        product.setPrice(price);
        product.setDateOfCreated(franchiseProduct.getDateOfCreated());
        product.setLink(franchiseProduct.getLink());
        productRepository.save(product);
    }

    public void saveProduct(Principal principal, Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        product.setUser(getUserByPrincipal(principal));
        Image image1;
        Image image2;
        Image image3;
        if (file1.getSize() != 0) {
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            product.addImageToProduct(image1);
        }
        if (file2.getSize() != 0) {
            image2 = toImageEntity(file2);
            product.addImageToProduct(image2);
        }
        if (file3.getSize() != 0) {
            image3 = toImageEntity(file3);
            product.addImageToProduct(image3);
        }
        product.setIsFranchise(false);
        log.info("Saving new Product. Title: {}; Author email: {}", product.getTitle(), product.getUser().getEmail());
        Product productFromDb = productRepository.save(product);
        productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId());
        productRepository.save(product);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public void deleteFranchiseProduct(User user, Long id) {
        FranchiseProduct product = franchiseRepository.findById(id)
                .orElse(null);
        if (product != null) {

                franchiseRepository.delete(product);
                log.info("Product with id = {} was deleted", id);

        } else {
            log.error("Product with id = {} is not found", id);
        }
    }

    public void deleteProduct(User user, Long id) {
        Product product = productRepository.findById(id)
                .orElse(null);
        if (product != null) {
            if (product.getUser().getId().equals(user.getId())) {
                productRepository.delete(product);
                log.info("Product with id = {} was deleted", id);
            } else {
                log.error("User: {} haven't this product with id = {}", user.getEmail(), id);
            }
        } else {
            log.error("Product with id = {} is not found", id);
        }
    }


    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
