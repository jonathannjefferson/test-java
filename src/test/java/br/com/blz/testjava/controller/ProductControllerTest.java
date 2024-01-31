package br.com.blz.testjava.controller;

import br.com.blz.testjava.domain.Product;
import br.com.blz.testjava.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    private final int SKU = 43264;
    @Mock
    private ProductService productService;
    @InjectMocks
    private ProductController productController;

    @Mock
    private Product product;

    @Test
    void shouldReturnHttpStatusCreatedWhenProductIsCreated() {
        when(productService.createProduct(any())).thenReturn(product);

        ResponseEntity<Product> response = productController.createProduct(product);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(product);
    }

    @Test
    void shouldReturnHttpStatusOkWhenProductIsUpdated() {
        doNothing().when(productService).updateProduct(any(), anyInt());

        ResponseEntity<Void> response = productController.updateProduct(product);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnHttpStatusOkWhenProductIsFounded() {
        when(productService.getProductBySku(SKU)).thenReturn(Optional.of(product));

        ResponseEntity<Product> response = productController.getProductBySku(SKU);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(product);
    }

    @Test
    void shouldReturnHttpStatusNotFoundWhenProductDoesNotExists() {
        when(productService.getProductBySku(SKU)).thenReturn(Optional.empty());

        ResponseEntity<Product> response = productController.getProductBySku(SKU);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturnHttpStatusNoContentWhenProductIsDeleted() {
        doNothing().when(productService).deleteProductBySku(43264);

        ResponseEntity<Void> response = productController.deleteProductBySku(SKU);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
