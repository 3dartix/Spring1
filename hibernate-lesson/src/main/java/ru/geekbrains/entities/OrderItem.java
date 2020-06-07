package ru.geekbrains.entities;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderItem {

    @EmbeddedId
    private OrderItemID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    public User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    public Product product;

    @Column
    private BigDecimal cost;

    public OrderItem() {
    }

    public OrderItem(User user, Product product, BigDecimal cost) {
        id = new OrderItemID(user.getId(), product.getId());
        this.user = user;
        this.product = product;
        this.cost = cost;
    }

    public OrderItemID getId() {
        return id;
    }
    public void setId(OrderItemID id) {
        this.id = id;
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
    public BigDecimal getCost() {
        return cost;
    }
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
