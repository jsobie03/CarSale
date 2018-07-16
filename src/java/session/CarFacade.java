/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Car;
import entity.Make;
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
public class CarFacade extends AbstractFacade<Car> {

    @PersistenceContext(unitName = "CarSalePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    //manually created
    public List<Car> findForMake(Make make){
        return em.createQuery("SELECT c from Car c WHERE c.make = :make").setParameter("make", make).getResultList();
    }

    public CarFacade() {
        super(Car.class);
    }
    
}
