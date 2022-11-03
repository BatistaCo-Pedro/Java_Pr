package Heizoeltaenke.main;
public class InvalidDeliveryException extends Exception {
    InvalidDeliveryException() {
        super();
    }
    InvalidDeliveryException(String message) {
        super(message);
    }
}