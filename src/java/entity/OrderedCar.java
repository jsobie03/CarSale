/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jon Sobier
 */
@Entity
@Table(name = "ordered_car")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderedCar.findAll", query = "SELECT o FROM OrderedCar o")
    , @NamedQuery(name = "OrderedCar.findByCustomerOrderId", query = "SELECT o FROM OrderedCar o WHERE o.orderedCarPK.customerOrderId = :customerOrderId")
    , @NamedQuery(name = "OrderedCar.findByCarModid", query = "SELECT o FROM OrderedCar o WHERE o.orderedCarPK.car_id = :car_id")})
public class OrderedCar implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrderedCarPK orderedCarPK;
    @JoinColumn(name = "car_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Car car;
    @JoinColumn(name = "customer_order_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CustomerOrder customerOrder;

    public OrderedCar() {
    }

    public OrderedCar(OrderedCarPK orderedCarPK) {
        this.orderedCarPK = orderedCarPK;
    }

    public OrderedCar(int customerOrderId, int car_id) {
        this.orderedCarPK = new OrderedCarPK(customerOrderId, car_id);
    }

    public OrderedCarPK getOrderedCarPK() {
        return orderedCarPK;
    }

    public void setOrderedCarPK(OrderedCarPK orderedCarPK) {
        this.orderedCarPK = orderedCarPK;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderedCarPK != null ? orderedCarPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderedCar)) {
            return false;
        }
        OrderedCar other = (OrderedCar) object;
        if ((this.orderedCarPK == null && other.orderedCarPK != null) || (this.orderedCarPK != null && !this.orderedCarPK.equals(other.orderedCarPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OrderedCar[ orderedCarPK=" + orderedCarPK + " ]";
    }
    
}
