import java.util.ArrayList;
import java.util.Collections;

/**
 * Repräsentiert logischen Teil der Spielkarte.
 */
public class Karte {
    private final int id;
    private final int paarid; // Zum Identifizieren von Paaren
    private boolean paarGefunden; // ist Karte noch im Spiel?
    private boolean umgedreht; // ist Karte aktuell umgedreht?

    /**
     * Konstruktor
     * @param id 
     */
    public Karte(int id, int paarid) {
        this.id = id;
        this.paarid = paarid;
        this.paarGefunden = false; // Karte ist bei Initialisierung noch nicht gefunden worden
        this.umgedreht = false; // Karte ist bei Initialisierung nicht umgedreht
    }

    // einfacher Zugriff auf Attribute und aktuellem Kartenzustand -> andere Klassen sollen nicht direkt auf die Attribute zugreifen (Datenkapselung)
    public int getId() { return id; }
    public int getpaarId() { return paarid; }
    public boolean isGefunden() { return paarGefunden; }
    public boolean isUmgedreht() { return umgedreht; }

    // Setter für Kartenzustand
    public void setUmgedreht(boolean umgedreht) { this.umgedreht = umgedreht; }
    public void setGefunden(boolean paarGefunden) { this.paarGefunden = paarGefunden; }

    /**
     * Erstellt ein Deck aus 42 Karten.
     * @return ArrayList
     */
    public static ArrayList<Karte> erstelleKartendeck(){
        ArrayList<Karte> kartendeck = new ArrayList<Karte>();

        int id = 0;
        // jede ID muss doppelt für ein Paar vorhanden sein, deshalb die 21 Varianten durchgehen und doppelt hinzufügen
        for (int i = 0; i<21; i++){
            kartendeck.add(new Karte(id++, i));
            kartendeck.add(new Karte(id++, i));
        }

        Collections.shuffle(kartendeck); // Deck mischen, damit Karten immer in verschiedener Reihenfolge auf dem Spielfeld "platziert" werden
        return kartendeck;
    }
}