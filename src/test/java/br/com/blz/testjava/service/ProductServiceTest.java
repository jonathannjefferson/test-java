package br.com.blz.testjava.service;

import br.com.blz.testjava.Fixture.ProductFixture;
import br.com.blz.testjava.domain.Product;
import br.com.blz.testjava.exception.ProductSkuAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private ProductService productService;
    private Product product;

    @BeforeEach
    void setUp() {
        productService = new ProductService();
        product = ProductFixture.buildProduct();

    }

    @Test
    void shouldCreateProductWhenSkuNotExists() {
        Product createdProduct = productService.createProduct(product);

        assertThat(product).isEqualTo(createdProduct);
    }

    @Test
    void shouldThrowExceptionWhenProductSkuAlreadyExists() {
        productService.createProduct(product);

        assertThatThrownBy(() -> productService.createProduct(product))
            .isInstanceOf(ProductSkuAlreadyExistsException.class)
            .hasMessage("Já existe produto cadastrado com essa sku");
    }

    @Test
    void shouldUpdateNameProductWhenItExists() {
        product.setName("Teste");

        productService.updateProduct(product, 43264);

        assertThat(product.getName()).isEqualTo("Teste");

    }

    @Test
    void shouldReturnProductWhenSkuExists() {
        productService.createProduct(product);

        Optional<Product> productFounded = productService.getProductBySku(43264);

        assertTrue(productFounded.isPresent());
        assertThat(productFounded.get()).isEqualTo(product);
        assertThat(productFounded.get().getInventory().getQuantity()).isEqualTo(6);
        assertThat(productFounded.get().isMarketable()).isTrue();
    }

    @Test
    void shouldDeleteProductWhenSkuExists() {
        productService.createProduct(product);
        productService.deleteProductBySku(43264);

        Optional<Product> productFound = productService.getProductBySku(43264);

        assertThat(productFound).isEmpty();
    }
}
