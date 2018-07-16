/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.OrderedCar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jon Sobier
 */
@SuppressWarnings("unchecked")
@Stateless
public class OrderedCarFacade extends AbstractFacade<OrderedCar> {
    @PersistenceContext(unitName = "CarSalePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderedCarFacade() {
        super(OrderedCar.class);
    }

    // manually created
    public List<OrderedCar> findByOrderId(Object id) {
        return em.createNamedQuery("OrderedCar.findByCustomerOrderId").setParameter("customerOrderId", id).getResultList();
    }

}
