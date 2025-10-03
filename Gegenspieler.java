import java.util.*;

public class Gegenspieler {
    private ArrayList<KarteButton> alleKarten;
    private final Map<Integer, List<KarteButton>> gedaechtnis;
    private ArrayList<KarteButton> bekannteEinzelkarten;
    private Random rng = new Random();

    /**
     * Konstruktor
     * */
    public Gegenspieler(ArrayList<KarteButton> alleKarten) {
        this.alleKarten = alleKarten;
        this.gedaechtnis = new HashMap<>();
        this.bekannteEinzelkarten = new ArrayList<>();
        this.rng = new Random();
    }

    /**
     * Erstellt eine Map mit bereits umgedrehten Karten, sowohl vom Spieler als auch vom Computer
     * */
    public void kartenMerken(KarteButton karte){
        int paarid = karte.getKarte().getpaarId();
        List<KarteButton> bekannteKarten = gedaechtnis.computeIfAbsent(paarid, k -> new ArrayList<>());
        if (bekannteKarten.stream().noneMatch(c -> c.getKarte().getpaarId() == karte.getKarte().getpaarId())) {
            bekannteKarten.add(karte);
        }
    }

    /**
     * Zug des Computers mithilfe seines "Gedächtnis"
     * @return boolean war Zug erfolgreich/darf danach nochmal gezogen werden?
     * */
    public boolean zugSpielen(){
        // Prüfen, ob vollständiges Paar bekannt ist
        KarteButton[] bekanntesPaar = bekanntePaareFinden();

        // wenn Paar bekannt:
        if(bekanntesPaar != null) {
            for (KarteButton b : bekanntesPaar) {
                b.getKarte().setUmgedreht(true);
                b.getKarte().setGefunden(true);
                b.ansichtAktualisieren();
                kartenMerken(b);
            }
            return true;
        } else {
            // kein Paar bekannt, also eine Karte zufällig umdrehen
            KarteButton erste = zufaelligeKarteUmdrehen();
            erste.getKarte().setUmgedreht(true);
            erste.ansichtAktualisieren();
            int paarId = erste.getKarte().getpaarId();
            List<KarteButton> bekannteKarten = gedaechtnis.get(paarId);
            KarteButton zweite = null;

            if (bekannteKarten != null) {
                for (KarteButton b : bekannteKarten) {
                    if (b != erste && !b.getKarte().isGefunden()) {
                        zweite = b;
                        break;
                    }
                }
            }

            if (zweite != null) {
                zweite.getKarte().setUmgedreht(true);
                zweite.ansichtAktualisieren();
                kartenMerken(zweite);
            } else {
                zweite = zufaelligeKarteUmdrehen();
                zweite.getKarte().setUmgedreht(true);
                zweite.ansichtAktualisieren();
            }

            if (erste.getKarte().getpaarId() == zweite.getKarte().getpaarId()) {
                erste.getKarte().setGefunden(true);
                zweite.getKarte().setGefunden(true);
                erste.ansichtAktualisieren();
                zweite.ansichtAktualisieren();

                return true;
            } else {
                final KarteButton zweiteFinal = zweite;
                // Umgedrehte Karten noch kurz zeigen, dann wieder umdrehen.
                javax.swing.Timer timer = new javax.swing.Timer(1500, e -> {
                    erste.getKarte().setUmgedreht(false);
                    zweiteFinal.getKarte().setUmgedreht(false);
                    erste.ansichtAktualisieren();
                    zweiteFinal.ansichtAktualisieren();
                });
                timer.setRepeats(false);
                timer.start();

                return false;
            }
        }
    }

    /**
     * Prüft vor dem eigentlichen Zug, ob durch Spieler aufgedeckte Karten jetzt ein vollständiges Paar bekannt ist
     * */
    private KarteButton[] bekanntePaareFinden(){
        // durch alle bekannte Einträge iterieren
        for (Map.Entry<Integer, List<KarteButton>> eintrag : gedaechtnis.entrySet()) {
            List<KarteButton> liste = eintrag.getValue();
            if(liste.size() == 2){
                ArrayList<KarteButton> ergebnis = new ArrayList<>();
                for (KarteButton k : liste) {
                    if(!k.getKarte().isGefunden()) ergebnis.add(k);
                    if (ergebnis.size() == 2) return new KarteButton[] { ergebnis.get(0), ergebnis.get(1) };
                }
            }
        }
        return null; // aktuell keine Paare bekannt
    }

    /**
     * Sucht alle Karten, die noch kein Paar gefunden haben und aktuell nicht umgedreht sind und dreht dann eine dieser Karten zufällig um
     * @return KarteButton die ausgewählte Karte
     * */
    private KarteButton zufaelligeKarteUmdrehen(){
        ArrayList<KarteButton> moeglicheKarten = new ArrayList<>();
        for (KarteButton k : alleKarten) {
            if (!k.getKarte().isGefunden() && !k.getKarte().isUmgedreht())  {
                moeglicheKarten.add(k);
            }
        }

        KarteButton zufaelligeKarte = moeglicheKarten.get(rng.nextInt(moeglicheKarten.size()));
        zufaelligeKarte.getKarte().setUmgedreht(true);
        zufaelligeKarte.ansichtAktualisieren();
        int reihe = 0;
        int spalte = 0;
        kartenMerken(zufaelligeKarte);
        return zufaelligeKarte;
    }
}
