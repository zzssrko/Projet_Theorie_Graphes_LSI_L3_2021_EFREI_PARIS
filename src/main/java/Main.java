import exception.CircuitDetectedException;
import objects.Automate;
import objects.Etat;
import objects.Tools;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Automate automateOG = new Automate();
        Automate automateOG_CPT = new Automate();
        boolean uneEntree, uneSortie;
        String fileName = "Graphe G1.txt";
        
        automateOG.read(fileName);
        automateOG.display();

        try {
            List<List<Etat>> ranks = Tools.getRanks(automateOG);
            uneEntree = ranks.get(0).size() == 1;
            uneSortie = ranks.get(ranks.size() - 1).size() == 1;
            System.out.println();
            Tools.displayRanks(ranks);


            System.out.println("\nUne seule entree? = " + ((uneEntree) ? "Oui" : "Non"));
            System.out.println("Une seule sortie? = " + ((uneSortie) ? "Oui" : "Non"));

            automateOG.read(fileName);
            System.out.println();
            Tools.calculCalendrierAuPlusTot(automateOG);
            
            automateOG_CPT.read(fileName);
            int dateFinPlusTot = automateOG.getSommetFromVal("Omega").getDates().getPlusTot();
            System.out.println();
            Tools.calculCalendrierAuPlusTard(automateOG_CPT, dateFinPlusTot);
            
            /*if (uneEntree && uneSortie) {
                Tools.calculateDatesPlusTot(ranks, automateOG);
                Tools.calculateDatePluTard(ranks, automateOG);
                Tools.displayDates(automateOG);
            } else {
                System.out.println("Impossible de calculer le plus court chemin/calendrier");;
            }*/
            
        } catch (CircuitDetectedException exception) {
            System.out.println("ERROR! Le graphe contient un circuit!");
        }
    }
}


