package objects;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class Automate {
    private List<String> nomsSommets = new ArrayList<>();
    private List<Etat> sommets = new ArrayList<>();


    public void read(String fileName) throws IOException {
        String[] values;
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        int verticesCount = Integer.parseInt(line);

        for (int i = 0; i < verticesCount; i++) {
            nomsSommets.add(String.valueOf(i));
        }

        for (int i = 0; i < verticesCount; i++) {
            sommets.add(new Etat(String.valueOf(i), false, false, new ArrayList<>(), new Dates()));
        }

        line = br.readLine();
        int edgesCount = Integer.parseInt(line);

        for (int i = 0; i < edgesCount; i++) {
            line = br.readLine();
            values = line.split("\\s");

            String depart = values[0];
            int value = Integer.parseInt(String.valueOf(values[1]));
            String arrivee = values[2];

            Etat etatDuDepart = sommets.get(sommets.indexOf(getSommetFromVal(depart)));
            if (!getSuccessors(etatDuDepart).contains(new Transition(value, getSommetFromVal(depart), getSommetFromVal(arrivee))))
                etatDuDepart.getTransitions().add(new Transition(value, getSommetFromVal(depart), getSommetFromVal(arrivee)));
        }
        br.close();
    }


    public void display() {
        String indent = String.format("%-" + (nomsSommets.size()) + "s", "");

        System.out.print("Etats" + indent.substring(0, indent.length() - "Etats".length()));
        for (String a : nomsSommets) {
            System.out.print(a + indent.substring(0, indent.length() - 1));
        }
        
        System.out.println("");

        for (String from : nomsSommets) {
            System.out.print(from + indent.substring(0, indent.length() - 1));
            for (String to : nomsSommets) {
                List<Integer> transitionsFromToTo = getTransitionsFromXtoY(getSommetFromVal(String.valueOf(from)), getSommetFromVal(String.valueOf(to)));
                System.out.print(transitionsFromToTo + indent.substring(0, indent.length() - transitionsFromToTo.toString().length()));
            }
            System.out.println("");
        }
    }


    public Etat getSommetFromVal(String val) {
        for (Etat e : sommets) {
            if (e.value.equals(val))
                return e;
        }
        return null;
    }

    public Set<Etat> getPredecessors(Etat etat) {
        Set<Etat> res = new HashSet<>();
        for (Etat e : sommets) {
            if (getSuccessors(e).contains(etat))
                res.add(e);
        }
        return res;
    }

    public List<Etat> getSuccessors(Etat etat) {
        List<Etat> list = new ArrayList<>();
        for (Transition transition : etat.transitions) {
            Etat arrivee = transition.arrivee;
            if (!list.contains(arrivee))
                list.add(arrivee);
        }
        return list;
    }

    public List<Etat> getNextRanks() {
        List<Etat> tmp = new ArrayList<>();
        for (String c : nomsSommets) {
            Etat etat = getSommetFromVal(String.valueOf(c));
            if (getPredecessors(etat).isEmpty()) {
                tmp.add(getSommetFromVal(String.valueOf(c)));
            }
        }
        return tmp;
    }

    public void removeSommets(List<Etat> toRemove) {
        sommets.removeAll(toRemove);
        for (Etat etat : toRemove) {
            nomsSommets.remove(etat.value);
        }
    }

    public List<Integer> getTransitionsFromXtoY(Etat from, Etat to) {
        List<Integer> res = new ArrayList<>();
        for (Transition transition : from.getTransitions()) {
            if (transition.arrivee.equals(to))
                res.add(transition.value);
        }
        return res;
    }


	public List<Etat> getSommets() {
		return sommets;
	}
}
