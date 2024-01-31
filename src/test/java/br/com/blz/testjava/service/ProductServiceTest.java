package br.com.blz.testjava.service;

import br.com.blz.testjava.domain.Product;
import br.com.blz.testjava.exception.ProductSkuAlreadyExistsException;
import br.com.blz.testjava.fixture.ProductFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private static final int SKU = 43264;
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
    void shouldUpdateProductWhenItExists() {
        product.setName("Teste");

        productService.updateProduct(product, SKU);

        assertThat(product.getName()).isEqualTo("Teste");
    }

    @Test
    void shouldReturnProductWhenSkuExists() {
        productService.createProduct(product);

        Optional<Product> productFounded = productService.getProductBySku(SKU);

        assertTrue(productFounded.isPresent());
        assertThat(productFounded.get()).isEqualTo(product);
        assertThat(productFounded.get().getInventory().getQuantity()).isEqualTo(6);
        assertThat(productFounded.get().isMarketable()).isTrue();
    }

    @Test
    void shouldDeleteProductWhenSkuExists() {
        productService.createProduct(product);
        productService.deleteProductBySku(SKU);

        Optional<Product> productFound = productService.getProductBySku(SKU);

        assertThat(productFound).isEmpty();
    }
}
