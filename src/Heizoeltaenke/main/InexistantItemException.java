package Heizoeltaenke.main;
public class InexistantItemException extends Exception {
    InexistantItemException(){
        super();
    }
    InexistantItemException(String message) {
        super(message);
    }
}
