package br.com.blz.testjava.domain;

import java.util.List;

public class Inventory {

    private int quantity;
    private List<Warehouse> warehouses;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }
}
