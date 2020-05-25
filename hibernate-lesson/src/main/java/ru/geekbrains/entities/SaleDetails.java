package ru.geekbrains.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "sale_details")
public class SaleDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
//    @Temporal(TemporalType.DATE)
    private Date sqlData;
//    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    private Time sqlTime;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(nullable = false)
    private Float cost;

    public SaleDetails() {
    }

    public SaleDetails(Long id, User user, Product product, Float cost) {
        this.id = id;
        this.sqlData = new Date(new java.util.Date().getTime());
        this.sqlTime = new Time(new java.util.Date().getTime());
        this.user = user;
        this.product = product;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Date getSqlData() {
        return sqlData;
    }
    public void setSqlData(Date sqlData) {
        this.sqlData = sqlData;
    }
    public Time getSqlTime() {
        return sqlTime;
    }
    public void setSqlTime(Time sqlTime) {
        this.sqlTime = sqlTime;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Float getCost() {
        return cost;
    }
    public void setCost(Float cost) {
        this.cost = cost;
    }
}
