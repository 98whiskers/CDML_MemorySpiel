import java.awt.BorderLayout;
import javax.swing.*;

/**
 * Repräsentiert Gerüst des Spieles.
 */
public class Game extends JFrame {

    private int punkteSpieler = 0;
    private int punkteComputer = 0;

    private JPanel spielfeldPanel; 

    /**
     * Startet das Spiel.
     * @param args
     */
    public static void main(String[] args){

    }

    /**
     * Konstruktor
     */
    public Game(){
        setTitle("Memory");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Timer
        // Spieler -> Punktestand, ob aktuell am Zug
        // Computer -> Punktestand, ob aktuell am Zug
    }

    /**
     * 
     */
    private void spielInitalisieren(){
        // TODO: Karten erstellen und shufflen
    }
}