/**
 * Repräsentiert logischen Teil der Spielkarte.
 */
public class Karte {
    private final int id; // Zum identifizieren von Paaren
    private boolean paarGefunden; // ist Karte noch im Spiel?
    private boolean umgedreht; // ist Karte aktuell umgedreht?

    /**
     * Konstruktor
     * @param id 
     */
    public Karte(int id) {
        this.id = id;
        this.paarGefunden = false; // Karte ist bei Initialisierung noch nicht gefunden worden
        this.umgedreht = false; // Karte ist bei Initalisierung nicht umgedreht
    }

    // einfacher Zugriff auf Attribute und aktuellem Kartenzustand -> andere Klassen sollen nicht direkt auf die Attribute zugreifen (Datenkapselung)
    public int getId() { return id; }
    public boolean isGefunden() { return paarGefunden; }
    public boolean isUmgedreht() { return umgedreht; }

    // Setter für Kartenzustand
    public void setUmgedreht(boolean umgedreht) { this.umgedreht = umgedreht; }
    public void setGefunden(boolean paarGefunden) { this.paarGefunden = paarGefunden; }
}