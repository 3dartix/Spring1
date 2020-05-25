package ru.geekbrains.entities;

import javax.persistence.*;
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private float cost;

//    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "orderId")
//    private Orders orderId;

    public Product() {
    }

    public Product(Long id, String name, float cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return name;
    }

    //get and set
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public float getCost() {
        return cost;
    }
    public void setCost(float cost) {
        this.cost = cost;
    }
//    public Orders getOrder() {
//        return orderId;
//    }
//    public void setOrder(Orders orderId) {
//        this.orderId = orderId;
//    }
}
