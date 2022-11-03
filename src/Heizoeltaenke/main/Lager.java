package Heizoeltaenke.main;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Calendar;

public class Lager {
    private static final Scanner myObj = new Scanner(System.in); // Scanner Object for input

    private int LieferungIndex;

    public List<IStorageItem> items = new ArrayList<>();

    //#region helperMethods

    //helper Method to check if any Tank actually exists 
    private void checkIfNoTankExists() throws InexistantItemException{
        if(items.size() == 0){
            throw new InexistantItemException("Exception: Es existieren keine Tänke!");
        }
        return;
    }

    //helper Method to print tank names
    private void helpPrintNames()
    {
        for (int index = 0; index < items.size(); index++) {
            System.out.println((index + 1) + ": " + items.get(index).getInfo("Name"));
        }
        System.out.println("\n" + (items.size() + 1) + ": " + "Zurück");
    }

    //#region getCorrectInput()
    /**
     * This Method checks if the corrected Input (Console) is in form with the given min, max values and returns it as Integer
     * @param print : String value to print to the console
     * @param minValue : min value to check for
     * @param maxValue : max value to check for
     * @return Integer : Returns the checked value
     */
    private Integer getCorrectInput(String print, Integer minValue, Integer maxValue) {
        System.out.print(print + ": ");
        Integer value = myObj.nextInt(); //read user input

        boolean inputIsCorrect = (value >= minValue && value <= maxValue); //if input is correct set boolean to true
        //If input is not correct loop through until the input is correct
        while (!(inputIsCorrect)) {
            System.out.println("ungültiges " + print + "\n" + "bitte versuchen Sie erneut\n" );
            System.out.print(print + ": ");
            value = myObj.nextInt();
            inputIsCorrect = (value >= minValue && value <= maxValue);
        }
        return value;
    }
    //#endregion getCorrectInput
    
    //#endregion helper Methods

    public void TankErstellen() {
        System.out.print("Tankname eingeben: ");
        String Name = myObj.next(); // Read user input
        System.out.print("Tankid eingeben: ");
        int Id = myObj.nextInt();
        System.out.print("Tankgrösse eingeben: ");
        double Kapazität = myObj.nextDouble();
        System.out.println("Erbaudatum eingeben");
        
        int Year = getCorrectInput("Jahr", 1800, 2022);
        int Month = getCorrectInput("Monat", 1, 12);
        int Day = getCorrectInput("Tag", 1, 31);

        Heizöltank neuerHeizöltank = new Heizöltank(Name, Id, Kapazität);
        //setting the data to the curent instance of the object
        neuerHeizöltank.ErbauDatum.set(Calendar.DATE, Day);
        neuerHeizöltank.ErbauDatum.set(Calendar.MONTH, Month);
        neuerHeizöltank.ErbauDatum.set(Calendar.YEAR, Year);

        neuerHeizöltank.printInfo();
        items.add(neuerHeizöltank);
    }

    public void Infobericht() {
        try {
            checkIfNoTankExists();
        } catch (InexistantItemException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        System.out.println("Wählen Sie der Tank aus: ");
        helpPrintNames(); //print all Tanks and an option to return to the menu
        
        int wahl = myObj.nextInt();
        
        //loop untill an actual existing tank is chosen
        while(wahl > items.size() || wahl <= 0)
        {
            if (wahl == items.size() + 1) return;//if choice is the last printed option: "Zurück", return out of the method
            System.out.println("Ungültige Auswahl");
            helpPrintNames(); //print all options again
            wahl = myObj.nextInt();
        }
        items.get(wahl - 1).printInfo();
        return;
    }

    public void Anlieferung() throws InvalidDeliveryException, InexistantItemException{  
        checkIfNoTankExists();

        boolean everyTankIsFull = (items.get(items.size() - 1).<Double>getInfo("VerfügbaresPlatz") == 0);
        if(everyTankIsFull) { 
            throw new InvalidDeliveryException("Exception: Alle Tänke sind voll");
        }
        System.out.println("Wieviel wollen Sie anliefern? (liter)");
        double num = myObj.nextDouble();

        IStorageItem item = items.get(LieferungIndex);
        item.Anlieferung(num);
        Double verfügbaresPlatz = item.getInfo("VerfügbaresPlatz");

        boolean tankIsFull = false;
        if(LieferungIndex < items.size() - 1) {
            tankIsFull = (verfügbaresPlatz == 0);
        }
        if (!(tankIsFull)) return;

        double restToInput = item.getInfo("Rest");
        items.get(LieferungIndex + 1).Anlieferung(restToInput);
        LieferungIndex++;
    }

    public void Auslieferung() throws InvalidDeliveryException, InexistantItemException{
        checkIfNoTankExists();

        boolean everyTankIsEmpty = (items.get(0).<Double>getInfo("Füllstand") == 0);
        if(everyTankIsEmpty) {
            throw new InvalidDeliveryException("Exception: Alle Tänke sind leer");
        }
        System.out.println("Wieviel wollen Sie ausliefern? (liter)");
        double num = myObj.nextDouble();

        IStorageItem item = items.get(LieferungIndex);
        item.Auslieferung(num);
        Double füllstand = item.getInfo("Füllstand");

        boolean tankIsEmpty = false;
        if(LieferungIndex > 0) {
            tankIsEmpty = (füllstand == 0);
        }
        if(!(tankIsEmpty)) return;

        double restToInput = item.getInfo("Rest");
        items.get(LieferungIndex - 1).Auslieferung(restToInput);
        LieferungIndex--;
    }

    public void Entfernen(){
        try {
            checkIfNoTankExists();
        } catch (InexistantItemException ex) {
            System.out.println(ex.getMessage());
            return;
        }
        helpPrintNames();

        int wahl = myObj.nextInt();

        if (wahl == items.size() + 1) return; //if choice is the last printed option: "Zurück", return out of the method
        while(wahl > items.size() || wahl <= 0)
        {
            System.out.println("Dieser Tank exisitiert nicht.");
            helpPrintNames(); //print all options again
            wahl = myObj.nextInt();
        }

        if (items.get(wahl - 1).<Double>getInfo("Füllstand") != 0)
        {
            System.out.println("Tank ist nicht leer!");
            return;
        }

        items.remove(wahl - 1);
        System.out.println("Tank wurde erfolgreich enfernt");
        return;
    }
}