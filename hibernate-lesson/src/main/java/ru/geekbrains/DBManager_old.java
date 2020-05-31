//package ru.geekbrains;
//
//import org.hibernate.cfg.Configuration;
//import ru.geekbrains.entities.OrderItem;
//import ru.geekbrains.entities.Product;
//import ru.geekbrains.entities.SaleDetails;
//import ru.geekbrains.entities.User;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import java.util.ArrayList;
//import java.util.List;
//
//public class DBManager_old {
//    private EntityManagerFactory emFactory;
//    private EntityManager em;
//
//    public DBManager_old() {
//        this.emFactory = new Configuration()
//                .configure("hibernate.cfg.xml")
//                .buildSessionFactory();
//        this.em = emFactory.createEntityManager();
//    }
//
//    public void findAllProducts(Long id){
//        OrderItem orderItem = em.find(OrderItem.class, id);
//        System.out.printf(orderItem.toString());
//    }
//
//    public void deleteOrder(Long id){
//        OrderItem orderItem = em.find(OrderItem.class, id);
//        em.getTransaction().begin();
//        em.remove(orderItem);
//        em.getTransaction().commit();
//    }
//
//    public List<Product> findProductsByID(Long...longs) {
//        List<Product> products = new ArrayList<>();
//        for (Long el:longs) {
//            Product product = em.find(Product.class, el);
//            if(product != null) {
//                products.add(product);
//            }
//        }
//        return products;
//    }
//
//    public void addOrder(User user, List<Product> products){
//        //Обновляет, но не создает. жалуется на какой-то fk.
//        em.getTransaction().begin();
//        OrderItem orderItem = em.find(OrderItem.class, user.getId());
//        if(orderItem == null){
//            em.persist(user);
//            products.forEach(product -> {
//            em.persist(product);
//        });
//            em.persist(new OrderItem(user, products));
//        } else{
//            orderItem.setProducts(products);
//        }
//        // создаем детализацию по паре «покупатель — товар» на момент покупки товара
////        products.forEach(product -> {
////            em.persist(getSaleDetails(user, product, product.getCost()));
////        });
//        em.getTransaction().commit();
//    }
//
//    public void deleteUser(Long id){
//        /*
//         * удаляем заказ пользователя т.к. таблицы связаны а потом пользователя
//         * в запросе обращаемся к сущности класса from Orders o where а не к таблице
//         */
//        List<OrderItem> orders = em.createQuery("from OrderItem o where o.user.id = :id", OrderItem.class)
//                .setParameter("id", id)
//                .getResultList();
//        User user = em.find(User.class, id);
//        em.getTransaction().begin();
//        em.remove(orders.get(0));
//        em.remove(user);
//        em.getTransaction().commit();
//    }
//
//    public void deleteProduct(Long id){
//        /*
//        * С каскадным удалением я так не разобрался. Указываешь cascade = CascadeType.ALL удаляются все связанные
//        * записи. Был вариант удалить вручную, но слишком сложная конструкция получается. Сначала нужно удалить
//        * все записи в Orders.List<Product> с этим товаром, а затем сам товар.
//        * Полагаю, существует более простое решение.
//        */
//        Product product = em.find(Product.class, id);
//        em.getTransaction().begin();
//        em.remove(product);
//        em.getTransaction().commit();
//    }
//
//    public SaleDetails getSaleDetails(User user, Product product, Float cost){
//        return new SaleDetails(null, user, product, cost);
//    }
//
//    // test
//    public void createNewTables(){
//        User user = new User(null, "Alex");
//        Product product1 = new Product(null,"Product1", 20f);
//        Product product2 = new Product(null,"Product2", 10f);
//        Product product3 = new Product(null,"Product3", 15f);
//        List<Product> products = new ArrayList<>();
//        products.add(product1);
//        products.add(product2);
//        products.add(product3);
//        OrderItem orderItem = new OrderItem(user, products);
////        product1.setOrder(orders);
////        product2.setOrder(orders);
////        product3.setOrder(orders);
//
//        em.getTransaction().begin();
//        em.persist(user);
//
//        em.persist(product1);
//        em.persist(product2);
//        em.persist(product3);
//
//        em.persist(orderItem);
//        em.getTransaction().commit();
//    }
//
//    public User findUserById(long id) {
//        return em.find(User.class, id);
//    }
//
//    public void addUser(String name) {
//        em.getTransaction().begin();
//        em.persist(new User(null, name));
//        em.getTransaction().commit();
//    }
//}
