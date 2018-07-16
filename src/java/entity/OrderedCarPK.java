/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author tgiunipero
 */
@Embeddable
public class OrderedCarPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "customer_order_id")
    private int customerOrderId;
    @Basic(optional = false)
    @Column(name = "car_id")
    private int car_id;

    public OrderedCarPK() {
    }

    public OrderedCarPK(int customerOrderId, int car_id) {
        this.customerOrderId = customerOrderId;
        this.car_id = car_id;
    }

    public int getCustomerOrderId() {
        return customerOrderId;
    }

    public void setCustomerOrderId(int customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    public int getCarId() {
        return car_id;
    }

    public void setCarId(int car_id) {
        this.car_id = car_id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) customerOrderId;
        hash += (int) car_id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderedCarPK)) {
            return false;
        }
        OrderedCarPK other = (OrderedCarPK) object;
        if (this.customerOrderId != other.customerOrderId) {
            return false;
        }
        return this.car_id == other.car_id;
    }

    @Override
    public String toString() {
        return "entity.OrderedCarPK[customerOrderId=" + customerOrderId + ", car_id=" + car_id + "]";
    }

}