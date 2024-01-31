package br.com.blz.testjava.fixture;

import br.com.blz.testjava.domain.Inventory;
import br.com.blz.testjava.domain.Product;
import br.com.blz.testjava.domain.Warehouse;

import java.util.Arrays;
import java.util.List;

public class ProductFixture {

    public static Product buildProduct() {
        Product product = new Product();
        product.setSku(43264);
        product.setMarketable(true);
        product.setName("L'Oréal Professionnel Expert Absolut Repair Cortex Lipidium - Máscara de Reconstrução 500g");
        product.setInventory(buildInventory());

        return product;
    }

    private static Inventory buildInventory() {
        Inventory inventory = new Inventory();
        inventory.setQuantity(15);
        inventory.setWarehouses(buildWarehouseList());

        return inventory;
    }

    private static List<Warehouse> buildWarehouseList() {
        Warehouse firstWarehouse = new Warehouse();
        firstWarehouse.setQuantity(3);
        firstWarehouse.setLocality("MOEMA");
        firstWarehouse.setType("PHYSICAL_STORE");

        Warehouse secondWarehouse = new Warehouse();
        secondWarehouse.setQuantity(3);
        secondWarehouse.setLocality("MOEMA");
        secondWarehouse.setType("PHYSICAL_STORE");

        return Arrays.asList(firstWarehouse, secondWarehouse);
    }
}
