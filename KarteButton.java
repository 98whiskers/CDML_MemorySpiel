
import javax.swing.Icon;
import javax.swing.JButton;

/***
 * Repräsentiert grafischen Teil der Spielkarte.
 */
public class KarteButton extends JButton {
    private final Karte karte;
    private final Icon iconVorderseite;
    private final Icon iconRueckseite;

    /**
     * Konstruktor
     * @param karte Die logische Karte
     * @param iconVorderseite Icon auf der Seite, die beim Umdrehen gezeigt wird
     * @param iconRueckseite Icon auf der Seite, die standardmäßig gezeigt wird
     */
    public KarteButton(Karte karte, Icon iconVorderseite, Icon iconRueckseite) {
        this.karte = karte;
        this.iconVorderseite = iconVorderseite;
        this.iconRueckseite = iconRueckseite;
        setIcon(iconRueckseite); // Anfangs Rückseite anzeigen
    }

    public void ansichtAktualisieren() {
        if(karte.isGefunden()) {
            setEnabled(false); // Karte ist nicht mehr im Spiel, Button deaktivieren
        } else if (karte.isUmgedreht()) { 
            setIcon(iconVorderseite); // Karte ist umgedreht, Vorderseite anzeigen
        } else {
            setIcon(iconRueckseite); // Karte ist nicht umgedreht, Rückseite anzeigen
        }
    }
    
    /**
     * Getter für die logische Karte, die der Button repräsentiert. Bietet Zugriff auf ID, ob Karte noch aktiv ist....
     * @return
     */
    public Karte getKarte() {
        return karte;
    }

}
