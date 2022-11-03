package Heizoeltaenke.main;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Heizöltank implements IStorageItem {
    
    private double Kapazität;
    
    public Calendar ErbauDatum = Calendar.getInstance();

    private String Name;

    public String getName() {
        return Name;
    }
    private int Id;

    private double Füllstand = 0;
    public Double getFüllstand() {
        return Füllstand;
    }

    private double VerfügbaresPlatz;
    public Double getVerfügbaresPlatz(){
        return  VerfügbaresPlatz;
    }

    //var to determine the leftover value on Anlieferung and Auslieferung Methods --> used as Input for those same Methods on the next or previous object instance
    private double restLiter;
    public double getRestLiter() {
        return restLiter;
    }

    @SuppressWarnings("unchecked")
    public <T> T getInfo(String key) {
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("Füllstand", getFüllstand());
        info.put("VerfügbaresPlatz", getVerfügbaresPlatz());
        info.put("Rest", getRestLiter());
        info.put("Name", getName());
        return (T)info.get(key);
    }

    //#region Anlieferung
    /**
     * Method used to fill up the tanks.
     * If inputed number is larger than the tanks leftover capacity, fills the leftover value in the next Tank.
     * @param Liter : number to fill up the Tank with
     * @return void: Prints information to the console --> name, id, used capacity, remaining capacity
     */

    public void Anlieferung(double Liter) throws InvalidDeliveryException, InexistantItemException {   
        VerfügbaresPlatz = Kapazität - Füllstand;
        //inputed number larger than remaining capacity
        if(Liter > VerfügbaresPlatz){
            throw new InvalidDeliveryException("Exception: Verfügbares Platz: " + VerfügbaresPlatz + "   Anlieferungsversuch: " + Liter + 
                                                "\nVerfügbares Platz ungenügend");
        }
        //add inuted number to currently used capacity
        Füllstand += Liter;
        //calculate new remaining capacity
        VerfügbaresPlatz = Kapazität - Füllstand;
        //print Tank instance info
        printOilInfo();
    }
    //#endregion

    //#region Auslieferung
    /**
     * Method used to empty the tanks
     * If inputed number is larger than the tanks currently fuel level, empties the leftover value of the previous Tank.    
     * @param double Liter: number to empty the Tank with
     * @return void: Prints information to the console
     */

    public void Auslieferung(double Liter) throws InexistantItemException{
        VerfügbaresPlatz = Kapazität - Füllstand;
        //inputed number larger than currently used capacity
        if(Liter > Füllstand){
            //calculate leftover
            restLiter = Liter - Füllstand;
            //set inputed number to currently used capacity
            Liter = Füllstand;
        }
        //subtract inputed number from currently used capacity
        Füllstand -= Liter;
        //calculate new remaining capacity
        VerfügbaresPlatz = Kapazität - Füllstand;
        //print Tank instance info
        printOilInfo();
    }
    //#endregion

    //Constructor
    public Heizöltank(String Name, int Id, double Kapazität) {
        this.Name = Name;
        this.Id = Id;
        this.Kapazität = Kapazität;
        this.VerfügbaresPlatz = Kapazität - Füllstand;
    }

    //this Method prints all information of the Tank instance
    public void printInfo() {
        System.out.println("Name des Tanks: " + Name + "\n" +
                            "Id des Tanks: " + Id + "\n" +
                            "Tankgrösse: " + Kapazität + "l\n" +
                            "Füllstand: " + Füllstand + "l\n" +
                            "VerfügbaresPlatz: " + VerfügbaresPlatz + "l\n" +
                            "Der Erbaudatum ist " + ErbauDatum.get(Calendar.DATE) + "/"
                            + ErbauDatum.get(Calendar.MONTH) + "/"
                            + ErbauDatum.get(Calendar.YEAR));
    }

    //this Method prints information regarding the oil status of the tank instance
    private void printOilInfo() {
        System.out.println("Name: " + Name + "    Id: " + Id);
        System.out.println("Füllstand: " + Füllstand + "/" + Kapazität + "l" + "\n" +
                        "Verfügbares Platz: " + VerfügbaresPlatz + "l");
        System.out.println();
    }
}