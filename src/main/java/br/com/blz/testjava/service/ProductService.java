package br.com.blz.testjava.service;

import br.com.blz.testjava.domain.Inventory;
import br.com.blz.testjava.domain.Product;
import br.com.blz.testjava.domain.Warehouse;
import br.com.blz.testjava.exception.ProductSkuAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private List<Product> products = new ArrayList<>();

    public Product createProduct(Product product) {
        if (getProductBySku(product.getSku()).isPresent()) {
            throw new ProductSkuAlreadyExistsException("Já existe produto cadastrado com essa sku");
        }
        products.add(product);

        return product;
    }

    public void updateProduct(Product product, int sku) {
        getProductBySku(sku).ifPresent(productFound -> {
            productFound.setInventory(product.getInventory());
            productFound.setName(product.getName());
        });
    }

    public Optional<Product> getProductBySku(int sku) {
        return products.stream()
            .filter(product -> product.getSku() == sku)
            .peek(this::calculateInventoryQuantity)
            .peek(this::checkIfProductIsMarketable)
            .findFirst();
    }

    public void deleteProductBySku(int sku) {
        getProductBySku(sku).ifPresent(products::remove);
    }

    private void checkIfProductIsMarketable(Product product) {
        product.setMarketable(product.getInventory().getQuantity() > 0);
    }

    private void calculateInventoryQuantity(Product product) {
        Inventory inventory = product.getInventory();
        int totalQuantity = inventory.getWarehouses().stream()
            .mapToInt(Warehouse::getQuantity)
            .sum();
        inventory.setQuantity(totalQuantity);
    }
}
