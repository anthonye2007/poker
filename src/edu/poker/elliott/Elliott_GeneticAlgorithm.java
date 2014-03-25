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
        List<Elliott_Genome> initialPopulation = randomGenomes(numGenomes);
        runGeneration(initialPopulation, new ArrayList<Integer>(minNumGenerations));
        System.out.println("\nFinished!");
    }

    private List<Elliott_Genome> runGeneration(List<Elliott_Genome> population, List<Integer> topScores) {
        if (topScores.size() > minNumGenerations && notChanging(topScores) && topScores.get(0) > 0) {
            return population;
        }

        int generationNum = topScores.size() + 1;
        System.out.println("Generation " + generationNum);

        Map<Elliott_Genome, Integer> genomes = new HashMap<>(numGenomes);

        for (Elliott_Genome genome : population) {
            Elliott player = new Elliott(genome);
            int score = fitness(player);
            genomes.put(genome, score);
        }

        Map<Elliott_Genome, Integer> sortedGenomes = sortGenomesByScore(genomes);

        // keep top 5%
        List<Elliott_Genome> elites = keepElites(sortedGenomes);

        // keep top score
        Elliott_Genome topGenome = elites.get(0);
        Integer topScore = genomes.get(topGenome);
        topScores.add(topScore);

        List<Elliott_Genome> toTweak = getPopulationToTweak(genomes.keySet(), 20);
        List<Elliott_Genome> tweaks = tweakGenomes(toTweak);

        List<Elliott_Genome> crossovers = crossoverGenomes(genomes.keySet(), 50);

        List<Elliott_Genome> possibleDuplicates = new ArrayList<>(elites);
        possibleDuplicates.addAll(tweaks);
        possibleDuplicates.addAll(crossovers);
        List<Elliott_Genome> newPopulation = removeDuplicates(possibleDuplicates);

        int numGenomesStillNeeded = numGenomes - newPopulation.size();
        List<Elliott_Genome> randoms = randomGenomes(numGenomesStillNeeded);
        newPopulation.addAll(randoms);

        return runGeneration(newPopulation, topScores);
    }

    private List<Elliott_Genome> getPopulationToTweak(Set<Elliott_Genome> genomes, int percentageToTweak) {
        List<Elliott_Genome> toTweak = new ArrayList<>();
        for(Elliott_Genome genome : genomes) {
            if (shouldAdd(percentageToTweak))
                toTweak.add(genome);
        }
        return toTweak;
    }

    private List<Elliott_Genome> removeDuplicates(List<Elliott_Genome> newPopulation) {
        List<Elliott_Genome> toAdd = new ArrayList<>();

        while (newPopulation.size() > 0) {
            Elliott_Genome genome = newPopulation.remove(0);
            if (!isDuplicate(newPopulation, genome)) {
                toAdd.add(genome);
            }
        }

        return toAdd;
    }

    private boolean isDuplicate(List<Elliott_Genome> list, Elliott_Genome genome) {
        for(Elliott_Genome possibleDuplicate : list) {
            if (genome.equals(possibleDuplicate))
                return true;
        }
        return false;
    }

    /**
     * Returns true a certain percentage of the time.
     */
    private boolean shouldAdd(int percentage) {
        Random rand = new Random();
        int max = 100;
        int num = rand.nextInt(max - percentage + 1);

        return num == 0;
    }


    private List<Elliott_Genome> crossoverGenomes(Set<Elliott_Genome> genomes, int percentageToCross) {
        List<Elliott_Genome> crossedGenomes = new ArrayList<>(genomes.size());

        List<Elliott_Genome> toCross = new ArrayList<>();
        for(Elliott_Genome genome : genomes) {
            if (shouldAdd(percentageToCross))
                toCross.add(genome);
        }

        Random rand = new Random();

        while (toCross.size() > 2) {
            // cross two random genomes
            Elliott_Genome first = toCross.remove(rand.nextInt(toCross.size()));
            Elliott_Genome second = toCross.remove(rand.nextInt(toCross.size()));

            Elliott_Genome crossed = cross(first, second);
            crossedGenomes.add(crossed);
        }

        return crossedGenomes;
    }

    private Elliott_Genome cross(Elliott_Genome first, Elliott_Genome second) {
        String firstStr = first.toString();
        int minLength = 2;

        int pivot = randomIntFromTo(minLength, firstStr.length() - minLength);

        String subFirst = firstStr.substring(0, pivot);
        String subSecond = second.toString().substring(pivot);

        String crossedGenome = subFirst + subSecond;

        return new Elliott_Genome(crossedGenome);
    }

    private boolean notChanging(List<Integer> topScores) {
        // if last 50 top scores are the same
        for (int i = 1; i < topScores.size() - 1; i++) {
            if (!topScores.get(i).equals(topScores.get(i - 1))) {
                return false;
            }
            if (i > 50) break;
        }

        return true;
    }

    private List<Elliott_Genome> tweakGenomes(List<Elliott_Genome> elites) {
        List<Elliott_Genome> tweaks = new ArrayList<>();
        Random rand = new Random();

        for (Elliott_Genome elite : elites) {
            // tweak small number of genes
            for (int i = 0; i < rand.nextInt(3); i++) {
                int indexToTweak = rand.nextInt(Elliott_Genome.numGenes());
                char c = Action.randomAction();
                StringBuilder str = new StringBuilder(elite.toString());
                str.setCharAt(indexToTweak, c);

                Elliott_Genome tweaked = new Elliott_Genome(str.toString());
                tweaks.add(tweaked);
            }
        }
        return tweaks;
    }

    private List<Elliott_Genome> keepElites(Map<Elliott_Genome, Integer> sortedGenomes) {
        List<Elliott_Genome> elites = new LinkedList<>();
        int i = 0;
        Iterator<Entry<Elliott_Genome, Integer>> iter = sortedGenomes.entrySet().iterator();

        while(iter.hasNext() && i < numGenomes / 20) {
            Entry<Elliott_Genome, Integer> entry = iter.next();
            elites.add(entry.getKey());
            System.out.print(entry.getValue());
            System.out.println(" " + entry.getKey().toString());
            i++;
        }

        System.out.println();

        return elites;
    }

    private List<Elliott_Genome> randomGenomes(int numRandom) {
        List<Elliott_Genome> genomes = new LinkedList<>();

        for (int i = 0; i < numRandom; i++) {
            Elliott_Genome genome = new Elliott_Genome();
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            genomes.add(genome);
        }

        return genomes;
    }

    private Map<Elliott_Genome, Integer> sortGenomesByScore(Map<Elliott_Genome, Integer> unsortedMap) {

        List<Entry<Elliott_Genome, Integer>> list = new LinkedList<>(unsortedMap.entrySet());

        // descending order
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Entry) (o2)).getValue())
                        .compareTo(((Entry) (o1)).getValue());
            }
        });

        // put sorted list into map again
        //LinkedHashMap make sure order in which keys were inserted
        Map<Elliott_Genome, Integer> sortedMap = new LinkedHashMap<>();
        for (Entry<Elliott_Genome, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    private int fitness(Player player) {
        Elliott_Runner runner = new Elliott_Runner(player);
        runner.setNumTournaments(5);

        return runner.play();
    }

    private int randomIntFromTo(int from, int to) {
        Random rand = new Random();

        return rand.nextInt(to - from) + from;
    }
}
