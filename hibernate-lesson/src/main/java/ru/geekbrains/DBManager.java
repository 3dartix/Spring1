package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.entities.OrderItem;
import ru.geekbrains.entities.Product;
import ru.geekbrains.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;

public class DBManager {
    private EntityManagerFactory emFactory;
    private EntityManager em;

    public DBManager() {
        this.emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        this.em = emFactory.createEntityManager();
    }

//    public void createItem(){
//        em.getTransaction().begin();
//        User user = new User(null, "Alex");
//        Product product = new Product(null, "Product1", new BigDecimal(20.2));
//        user.addProduct(product);
//        em.persist(user);
//        em.getTransaction().commit();
//    }

    public void createOrder(){
        em.getTransaction().begin();
        User user = new User(null,"Alex");
        Product product1 = new Product(null, "Product1", new BigDecimal(20.4));
        Product product2 = new Product(null, "Product2", new BigDecimal(5));
        user.addProduct(product1, product2);
        OrderItem orderItem1 = new OrderItem(user, product1, product1.getCost());
        OrderItem orderItem2 = new OrderItem(user, product2, product1.getCost());
        em.persist(orderItem1);
        em.persist(orderItem2);
        em.getTransaction().commit();
    }

    public void deleteUser(Long id){
        em.getTransaction().begin();
        em.remove(em.find(User.class, id));
        em.getTransaction().commit();
    }

    public void deleteProduct(Long id){
        em.getTransaction().begin();
        em.remove(em.find(Product.class, id));
        em.getTransaction().commit();
    }

    public void createUser(String name){
        em.getTransaction().begin();
        em.persist(new User(null, name));
        em.getTransaction().commit();
    }

    public void createProduct(String name, BigDecimal cost){
        em.getTransaction().begin();
        em.persist(new Product(null, name, cost));
        em.getTransaction().commit();
    }

    public void findAllUserProductsByUserId (Long id){
        User user = em.find(User.class, id);
        for (Product el:user.getProducts()) {
            System.out.printf(el.toString());
        }
    }

    public void findAllUsersPurchasedProduct (Long id){
        Product product = em.find(Product.class, id);
        for (User el:product.getUsers()) {
            System.out.printf(el.toString());
        }
    }
}
