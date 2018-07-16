package session;

import cart.ShoppingCart;
import cart.ShoppingCartItem;
import entity.Customer;
import entity.CustomerOrder;
import entity.OrderedCar;
import entity.OrderedCarPK;
import entity.Car;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jon Sobier
 */
@SuppressWarnings("unchecked")
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class OrderManager {

    @PersistenceContext(unitName = "CarSalePU")
    private EntityManager em;
    @Resource
    private SessionContext context;
    @EJB
    private CarFacade carFacade;
    @EJB
    private CustomerOrderFacade customerOrderFacade;
    @EJB
    private OrderedCarFacade orderedCarFacade;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int placeOrder(String name, String email, String phone, String address, String city, String state, String ccNumber, ShoppingCart cart) {

        try {
            Customer customer = addCustomer(name, email, phone, address, city, state, ccNumber);
            CustomerOrder order = addOrder(customer, cart);
            addOrderedItems(order, cart);
            return order.getId();
        } catch (Exception e) {
            context.setRollbackOnly();
            return 0;
        }
    }

    private Customer addCustomer(String name, String email, String phone, String address, String city, String state, String ccNumber) {

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setCity(city);
        customer.setState(state);
        customer.setCcNumber(ccNumber);

        em.persist(customer);
        return customer;
    }

    private CustomerOrder addOrder(Customer customer, ShoppingCart cart) {

        // set up customer order
        CustomerOrder order = new CustomerOrder();
        order.setCustomer(customer);
        order.setAmount(BigDecimal.valueOf(cart.getTotal()));

        // create confirmation number
        Random random = new Random();
        int i = random.nextInt(999999999);
        order.setConfirmationNumber(i);

        em.persist(order);
        return order;
    }

    private void addOrderedItems(CustomerOrder order, ShoppingCart cart) {

        em.flush();

        List<ShoppingCartItem> items = cart.getItems();

        // iterate through shopping cart and create OrderedCars
        items.stream().map((ShoppingCartItem scItem) -> {
            int carId = scItem.getCar().getId();
            // set up primary key object
            OrderedCarPK orderedCarPK = new OrderedCarPK();
            orderedCarPK.setCustomerOrderId(order.getId());
            orderedCarPK.setCarId(carId);
            // create ordered item using PK object
            OrderedCar orderedItem = new OrderedCar(orderedCarPK);
            return orderedItem;
        }).forEachOrdered((orderedItem) -> {
            em.persist(orderedItem);
        });
    }

    public Map getOrderDetails(int orderId) {

        Map orderMap = new HashMap();

        // get order
        CustomerOrder order = customerOrderFacade.find(orderId);

        // get customer
        Customer customer = order.getCustomer();

        // get all ordered cars
        List<OrderedCar> orderedCars = orderedCarFacade.findByOrderId(orderId);

        // get car details for ordered items
        List<Car> cars = new ArrayList<>();

        orderedCars.stream().map((op) -> (Car) carFacade.find(op.getOrderedCarPK().getCarId())).forEachOrdered((p) -> {
            cars.add(p);
        });

        // add each item to orderMap
        orderMap.put("orderRecord", order);
        orderMap.put("customer", customer);
        orderMap.put("orderedCars", orderedCars);
        orderMap.put("cars", cars);

        return orderMap;
    }

}