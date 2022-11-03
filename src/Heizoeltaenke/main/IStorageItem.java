package Heizoeltaenke.main;
public interface IStorageItem {

    /**
     * Method used to fill the Lager
     * @param Anzahl : By how much the Lager should be flled
     * @return void: Prints information to the console --> name, id, used capacity, remaining capacity
     */
    void Anlieferung(double Anzahl) throws InvalidDeliveryException, InexistantItemException;

    /**
     * Method used to empty the Lager
     * @param Anzahl : By how much the Lager should be emptied
     * @return void: Prints information to the console --> name, id, used capacity, remaining capacity
     */
    void Auslieferung(double Anzahl) throws InexistantItemException;

    /**
     * Method to print the lager items Info
     * @return Prints Info to the console
     */
    void printInfo();
    <T> T getInfo(String key);
}