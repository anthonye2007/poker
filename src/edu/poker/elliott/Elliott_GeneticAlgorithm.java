package edu.poker.elliott;

import java.util.*;

import static java.lang.Thread.*;
import static java.util.Map.Entry;

/**
 * Runs a genetic algorithm to determine an optimal poker agent.
 * Created by anthonyelliott on 3/12/14.
 */
public class Elliott_GeneticAlgorithm {

    private final int numGenomes = 100;

    public static void main(String[] args) {
        Elliott_GeneticAlgorithm runner = new Elliott_GeneticAlgorithm();
        List<Genome> initialPopulation = runner.initializePopulation();
        runner.runGeneration(initialPopulation);
    }

    private void runGeneration(List<Genome> population) {
        Map<Genome, Integer> genomes = new HashMap<>(numGenomes);

        for (Genome genome : population) {
            Elliott player = new Elliott(genome);
            int score = fitness(player);
            genomes.put(genome, score);
        }

        // sort entries by score
        Map<Genome, Integer> sortedGenomes = sortGenomes(genomes);

        for (Entry<Genome, Integer> entry : sortedGenomes.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    private List<Genome> initializePopulation() {
        List<Genome> genomes = new LinkedList<>();

        for (int i = 0; i < numGenomes; i++) {
            Genome genome = new Genome();
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            genomes.add(genome);
        }

        return genomes;
    }

    private Map<Genome, Integer> sortGenomes(Map<Genome, Integer> unsortedMap) {

        List<Entry<Genome, Integer>> list = new LinkedList<>(unsortedMap.entrySet());

        // sort list based on comparator
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Entry) (o1)).getValue())
                        .compareTo(((Entry) (o2)).getValue());
            }
        });

        // put sorted list into map again
        //LinkedHashMap make sure order in which keys were inserted
        Map<Genome, Integer> sortedMap = new LinkedHashMap<>();
        for (Entry<Genome, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    private int fitness(Player player) {
        Elliott_Runner runner = new Elliott_Runner(player);
        runner.setNumTournaments(5);

        return runner.play();
    }
}
