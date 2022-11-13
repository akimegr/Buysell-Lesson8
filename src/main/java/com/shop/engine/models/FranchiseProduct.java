package com.shop.engine.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productsFranchise2")
@Data
public class FranchiseProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String link;
    private String title;
    private String price;
    private String city;
    private LocalDateTime dateOfCreated;
    private Long idFranchise;
}
