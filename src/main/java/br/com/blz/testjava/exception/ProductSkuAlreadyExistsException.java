package br.com.blz.testjava.exception;

public class ProductSkuAlreadyExistsException extends RuntimeException {

    public ProductSkuAlreadyExistsException(String message) {
        super(message);
    }
}
