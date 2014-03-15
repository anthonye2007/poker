package edu.poker.elliott;

import java.util.*;

import static java.lang.Thread.*;
import static java.util.Map.Entry;

/**
 * Runs a genetic algorithm to determine an optimal poker agent.
 * Created by anthonyelliott on 3/12/14.
 */
public class Elliott_GeneticAlgorithm {

    public static void main(String[] args) {
        new Elliott_GeneticAlgorithm();
    }

    public static final int numGenomes = 100;
    public static final int minNumGenerations = 100;

    public Elliott_GeneticAlgorithm() {
        List<Genome> initialPopulation = randomGenomes(numGenomes);
        List<Genome> endingPopulation = runGeneration(initialPopulation, new ArrayList<Integer>(minNumGenerations));

        for (Genome genome : endingPopulation) {
            Elliott player = new Elliott(genome);
            int score = fitness(player);
            System.out.print(score);
            System.out.println(" " + genome.toString());
        }
    }

    private List<Genome> runGeneration(List<Genome> population, List<Integer> topScores) {
        if (topScores.size() > minNumGenerations) {
            return population;
        }

        Map<Genome, Integer> genomes = new HashMap<>(numGenomes);

        for (Genome genome : population) {
            Elliott player = new Elliott(genome);
            int score = fitness(player);
            genomes.put(genome, score);
        }

        Map<Genome, Integer> sortedGenomes = sortGenomesByScore(genomes);

        // keep top 5%
        List<Genome> newPopulation = keepElites(sortedGenomes);
        Genome topGenome = newPopulation.get(0);
        Integer topScore = genomes.get(topGenome);
        topScores.add(topScore);

        // fill rest of next population with randomly generated genomes
        List<Genome> randoms = randomGenomes((int) (numGenomes * 0.95));

        newPopulation.addAll(randoms);

        return runGeneration(newPopulation, topScores);
    }

    private List<Genome> keepElites(Map<Genome, Integer> sortedGenomes) {
        List<Genome> elites = new LinkedList<>();
        int i = 0;
        Iterator<Entry<Genome, Integer>> iter = sortedGenomes.entrySet().iterator();

        while(iter.hasNext() && i < numGenomes / 20) {
            Entry<Genome, Integer> entry = iter.next();
            elites.add(entry.getKey());
            System.out.print(entry.getValue());
            System.out.println(" " + entry.getKey().toString());
            i++;
        }

        System.out.println();

        return elites;
    }

    private List<Genome> randomGenomes(int numRandom) {
        List<Genome> genomes = new LinkedList<>();

        for (int i = 0; i < numRandom; i++) {
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

    private Map<Genome, Integer> sortGenomesByScore(Map<Genome, Integer> unsortedMap) {

        List<Entry<Genome, Integer>> list = new LinkedList<>(unsortedMap.entrySet());

        // descending order
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Entry) (o2)).getValue())
                        .compareTo(((Entry) (o1)).getValue());
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
