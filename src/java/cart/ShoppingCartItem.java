/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cart;

import entity.Car;

/**
 *
 * @author tgiunipero
 */
public class ShoppingCartItem {

    Car car;
    short quantity;

    public ShoppingCartItem(Car car) {
        this.car = car;
        quantity = 1;
    }

    public Car getCar() {
        return car;
    }

    public short getQuantity() {
        return quantity;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public void decrementQuantity() {
        quantity--;
    }

    public double getTotal() {
        double price = 0;
        price = (this.getQuantity() * car.getPrice().doubleValue());
        return price;
    }

}