import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * Repräsentiert Gerüst des Spieles.
 */
public class Game extends JFrame {

    private boolean spielerAmZug = true;
    private int punkteSpieler = 0;
    private int punkteComputer = 0;

    ArrayList<Karte> kartendeck = new ArrayList<Karte>();
    ArrayList<KarteButton> alleButtons = new ArrayList<>();
    private KarteButton ersteKarte = null;
    private Timer umdrehTimer;
    private Gegenspieler gegenspieler;

    private JPanel spielfeldPanel;
    private SpielerPanel spielerPanel;
    private SpielerPanel computerPanel;

    /**
     * Startet das Spiel.
     */
    public static void main(String[] args){
        Game g = new Game();
        g.setExtendedState(JFrame.MAXIMIZED_BOTH);
        g.spielStarten();
    }

    /**
     * Konstruktor
     */
    public Game(){
        setTitle("Memory");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        erstelleSpielfeldPanel();
        erstelleSpielerpanels();

        setVisible(true);
    }

    /**
     * Erstellt das Spielfeld, auf dem die Karten ausgelegt werden.
     * */
    public void erstelleSpielfeldPanel(){
        spielfeldPanel = new JPanel(new GridLayout(6,7,5,5));
        add(spielfeldPanel, BorderLayout.CENTER);
    }

    /**
     * Erstellt Seiten-Panel für den Spieler mit Punktestand und Zuginformation.
     * */
    private void erstelleSpielerpanels(){
        spielerPanel = new SpielerPanel("Spieler");
        computerPanel = new SpielerPanel("Computer");
        add(spielerPanel, BorderLayout.WEST);
        add(computerPanel, BorderLayout.EAST);
    }

    /**
     * Erstellt das Kartendeck und initialisiert das Spielfeld
     */
    private void spielStarten(){
        // zu Beginn eines Spieles immer alles zurücksetzen falls nicht erste Runde
        punkteSpieler = 0; punkteComputer = 0; spielfeldPanel.removeAll();

        kartendeck = Karte.erstelleKartendeck();

        Icon rueckseite = new ImageIcon("bilder/rueckseite.png");
        for (Karte k:kartendeck) {
            Icon vorderseite = new ImageIcon("bilder/"+k.getpaarId()+".png");
            KarteButton spielkarte = new KarteButton(k, vorderseite, rueckseite);
            alleButtons.add(spielkarte);

            spielkarte.addActionListener(e -> {
                karteUmdrehen(spielkarte);
            });
            spielfeldPanel.add(spielkarte);
        }
        spielfeldPanel.revalidate();
        spielfeldPanel.repaint();

        gegenspieler = new Gegenspieler(alleButtons);
    }

    private void spielBeenden(){
        String gewinner = (punkteSpieler > punkteComputer) ? "Spieler hat gewonnen! :)" : (punkteComputer > punkteSpieler) ? "Computer hat gewonnen. :(" : "Unentschieden!";
        JOptionPane.showMessageDialog(this, gewinner, "Spielergebnis", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Setzt Kartenstatus und prüft bei zweiter umgedrehter Karte, ob Paar oder Zugwechsel
     * */
    private void karteUmdrehen(KarteButton button){
        Karte k = button.getKarte();
        if (!spielerAmZug || k.isGefunden() || k.isUmgedreht()) return;

        k.setUmgedreht(true);
        button.ansichtAktualisieren();
        gegenspieler.kartenMerken(button);

        if (ersteKarte == null) {
            ersteKarte = button;
        } else {
            paarPruefen(button);
        }
    }

    /**
     * Aktualisiert Kartenstatus und Punktestand, wenn Paar gefunden, beendet beim letzten gefundenen Paar das Spiel
     * */
    private void paarPruefen(KarteButton zweiteKarte){
        Karte k1 = ersteKarte.getKarte();
        Karte k2 = zweiteKarte.getKarte();

        if (k1.getpaarId() == k2.getpaarId()) {
            k1.setGefunden(true);
            ersteKarte.ansichtAktualisieren();
            k2.setGefunden(true);
            zweiteKarte.ansichtAktualisieren();
            punkteSpieler++;
            updateLabels();
            auswahlZuruecksetzen(); // ersteKarte muss für nächsten Zug wieder leer sein
            if (allePaareGefunden()) spielBeenden();
        } else {
            umdrehTimer = new Timer(1000, e -> {
                k1.setUmgedreht(false);
                k2.setUmgedreht(false);
                ersteKarte.ansichtAktualisieren();
                zweiteKarte.ansichtAktualisieren();
                auswahlZuruecksetzen();
                switchZug();
            });
            umdrehTimer.setRepeats(false);
            umdrehTimer.start();
        }
    }

    private void switchZug() {
        spielerAmZug = !spielerAmZug;
        if(spielerAmZug) { setKartenAktiv(true); spielerPanel.setZug(true); computerPanel.setZug(false); }
        else { spielerPanel.setZug(false); computerPanel.setZug(true); }

        if (!spielerAmZug) { setKartenAktiv(false); computerZug(); }
    }

    private void computerZug(){
        Timer t = new Timer(1200, e -> {
            boolean success = gegenspieler.zugSpielen();

            if (success) {
                punkteComputer++;
                updateLabels();
                if (allePaareGefunden()) {
                    spielBeenden();
                } else {
                    computerZug();
                }
            } else {
                switchZug();
            }
        });
        t.setRepeats(false);
        t.start();
    }

    /**
     * Prüft, ob alle Karten im Spiel den gefunden-Status haben
     * */
    private Boolean allePaareGefunden(){
        for (Karte k : kartendeck) {
            if(!k.isGefunden()) return false;
        }
        return true;
    }

    private void auswahlZuruecksetzen() {
        ersteKarte = null;
    }

    private void updateLabels() {
        spielerPanel.setPunkte(punkteSpieler);
        computerPanel.setPunkte(punkteComputer);
    }

    /**
     * Setzt, ob Spieler aktuell Karten umdrehen darf oder nicht.
     * */
    private void setKartenAktiv(boolean aktiv) {
        for (KarteButton k : alleButtons) {
            if (!k.getKarte().isGefunden()) {
                k.setEnabled(aktiv);
            }
        }
    }
}