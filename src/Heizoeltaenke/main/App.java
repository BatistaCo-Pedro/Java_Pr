package Heizoeltaenke.main;
import java.util.Scanner;

public class App {
    //global variables:
    private static final Scanner myObj = new Scanner(System.in); // Scanner Object for input
    //creating a Lager instance 
    private static Lager LagerEins = new Lager();

    public static void main(String[] args) { 
        int wahl;
        while(true) {
            Menu();

            wahl = myObj.nextInt();
    
            switch (wahl) {
                case 1:
                    LagerEins.TankErstellen();
                    break;
                case 2:
                    LagerEins.Infobericht();
                    break;
                case 3:
                    Lieferungen();
                    break;
                case 4:
                    LagerEins.Entfernen();
                    break;
                case 5:
                    return;
            }
        } 
    }

    private static void Lieferungen() {
        if(LagerEins.items.size() == 0){
            System.out.println("Es existieren keine Tänke");
            return;
        }
        
        System.out.println("1: Anlieferung \n2: Auslieferung \n\n3: Zurück" );
        int wahl = myObj.nextInt();
        switch(wahl)
        {
            case 1: 
                try {
                    LagerEins.Anlieferung();       
                } catch (InvalidDeliveryException|InexistantItemException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case 2: 
                try {
                    LagerEins.Auslieferung();
                } catch (InvalidDeliveryException|InexistantItemException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case 3:
                return;
            default: 
                System.out.println(wahl + " ist nicht eine gültige wahl\n");
                Lieferungen();
        }
    }

    private static void Menu() {
        System.out.println("*********************************");
        System.out.println("1: Tank erstellen");
        System.out.println("2: Infobericht");
        System.out.println("3: Lieferungen");
        System.out.println("4: Tank entfernen");
        System.out.println("5: Leave");
        System.out.println("*********************************");
    }
}