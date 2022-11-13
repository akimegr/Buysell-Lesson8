package com.shop.engine.controllers;

import com.shop.engine.models.FranchiseProduct;
import com.shop.engine.models.Product;
import com.shop.engine.models.User;
import com.shop.engine.userServices.ProductService;
import com.shop.engine.userServices.services.ParsingService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @Autowired
    @Qualifier("parsingServiceImpl")
    private ParsingService parsingService;

    @GetMapping("/")
    public String products(@RequestParam(name = "searchWord", required = false) String title, Principal principal, Model model) {
        model.addAttribute("products", productService.listProducts(title));
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("searchWord", title);
        return "products";
    }

    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model, Principal principal) {
        Product product = productService.getProductById(id);
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        model.addAttribute("authorProduct", product.getUser());
        return "product-info";
    }

    @PostMapping("/product/create")
    public String createProduct(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3, Product product, Principal principal) throws IOException {
        productService.saveProduct(principal, product, file1, file2, file3);
        return "redirect:/my/products";
    }

    @GetMapping("/product/franchise")
    public String productFranchise(@RequestParam(name = "searchWord", required = false) String title,Principal principal, Model model) {
        model.addAttribute("products", productService.listFranchiseProducts(title));
        return "franchise-products";
    }

    @PostMapping("/product/franchiseCreate")
    public String createFranchiseProduct(Principal principal) throws IOException {
        FranchiseProduct product = new FranchiseProduct();
        String href = parsingService.getLinkProduct();
        if (href != "") {
            Document first = Jsoup.connect(href).get();
            product.setLink(href);
            product.setTitle(parsingService.getTitleProduct(first));
            product.setPrice(parsingService.getPriceProduct(first));
            product.setCity(parsingService.getCityProduct(first));
            product.setDateOfCreated(parsingService.getDateOfCreatedProduct());
            product.setIdFranchise(2l);
            productService.saveFranchiseProduct(principal, product);
        }
        return "redirect:/product/franchise";
    }

    @PostMapping("/products/franchiseAllows/{id}")
    public String createFranchiseInStore (Principal principal, @PathVariable Long id) {
        FranchiseProduct franchiseProduct = productService.getFranchiseProductById(id);
        productService.saveFranchiseToCatalog(franchiseProduct, principal);
        productService.deleteFranchiseProduct(productService.getUserByPrincipal(principal), id);
        return "redirect:/product/franchise";
    }

    @PostMapping("/product/franchiseDelete/{id}")
    public String deleteFranchiseProduct(@PathVariable Long id, Principal principal) {
        productService.deleteFranchiseProduct(productService.getUserByPrincipal(principal), id);
        return "redirect:/product/franchise";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Principal principal) {
        productService.deleteProduct(productService.getUserByPrincipal(principal), id);
        return "redirect:/my/products";
    }

    @GetMapping("/my/products")
    public String userProducts(Principal principal, Model model) {
        User user = productService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        List<Product> productUser = user.getProducts().stream()
                        .filter(product -> !product.getIsFranchise())
                                .collect(Collectors.toList());
        model.addAttribute("products", productUser);
        return "my-products";
    }
}
