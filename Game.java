import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * Repräsentiert Gerüst des Spieles.
 */
public class Game extends JFrame {

    private int punkteSpieler = 0;
    private int punkteComputer = 0;

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
        spielfeldPanel = new SpielerPanel("Spieler");
        computerPanel = new SpielerPanel("Computer");
        add(spielerPanel, BorderLayout.WEST);
        add(computerPanel, BorderLayout.EAST);
    }

    /**
     * 
     */
    private void spielStarten(){
        // zu Beginn eines Spieles immer alles zurücksetzen falls nicht erste Runde
        punkteSpieler = 0; punkteComputer = 0; spielfeldPanel.removeAll();

        ArrayList<Karte> kartendeck = Karte.erstelleKartendeck();

        Icon rueckseite = new ImageIcon("bilder/rueckseite.png");
        for (Karte k:kartendeck) {
            Icon vorderseite = new ImageIcon("bilder/"+k.getId()+".png");
            KarteButton spielkarte = new KarteButton(k, vorderseite, rueckseite);

            spielkarte.addActionListener(e -> {
                if (!k.isGefunden() && !k.isUmgedreht()) {
                    k.setUmgedreht(true);
                    spielkarte.ansichtAktualisieren();
                }
            });
            spielfeldPanel.add(spielkarte);
        }
        spielfeldPanel.revalidate();
        spielfeldPanel.repaint();
    }

    // Optische Anpassungen der UI-Elemente
    private void styleName(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 28));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    private void styleScore(JLabel label) {
        label.setFont(new Font("Arial", Font.PLAIN, 24));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    private void styleZug(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(new Color(0,180,0));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setVisible(false);
    }
}