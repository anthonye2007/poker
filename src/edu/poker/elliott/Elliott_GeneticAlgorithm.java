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
        runGeneration(initialPopulation, new ArrayList<Integer>(minNumGenerations));
        System.out.println("\nFinished!");
    }

    private List<Genome> runGeneration(List<Genome> population, List<Integer> topScores) {
        if (topScores.size() > minNumGenerations && notChanging(topScores) && topScores.get(0) > 0) {
            return population;
        }

        int generationNum = topScores.size() + 1;
        System.out.println("Generation " + generationNum);

        Map<Genome, Integer> genomes = new HashMap<>(numGenomes);

        for (Genome genome : population) {
            Elliott player = new Elliott(genome);
            int score = fitness(player);
            genomes.put(genome, score);
        }

        Map<Genome, Integer> sortedGenomes = sortGenomesByScore(genomes);

        // keep top 5%
        List<Genome> elites = keepElites(sortedGenomes);

        // keep top score
        Genome topGenome = elites.get(0);
        Integer topScore = genomes.get(topGenome);
        topScores.add(topScore);

        // tweak 20%
        List<Genome> tweaks = tweakElites(elites);

        // about 50%
        List<Genome> crossovers = crossoverAll(genomes.keySet());

        List<Genome> possibleDuplicates = new ArrayList<>(elites);
        possibleDuplicates.addAll(tweaks);
        possibleDuplicates.addAll(crossovers);
        List<Genome> newPopulation = removeDuplicates(possibleDuplicates);

        // fill rest of next population with randomly generated genomes
        int numGenomesStillNeeded = numGenomes - newPopulation.size();
        List<Genome> randoms = randomGenomes(numGenomesStillNeeded);
        newPopulation.addAll(randoms);

        return runGeneration(newPopulation, topScores);
    }

    private List<Genome> removeDuplicates(List<Genome> newPopulation) {
        List<Genome> toAdd = new ArrayList<>();

        while (newPopulation.size() > 0) {
            Genome genome = newPopulation.remove(0);
            if (!isDuplicate(newPopulation, genome)) {
                toAdd.add(genome);
            }
        }

        return toAdd;
    }

    private boolean isDuplicate(List<Genome> list, Genome genome) {
        for(Genome possibleDuplicate : list) {
            if (genome.equals(possibleDuplicate))
                return true;
        }
        return false;
    }

    /**
     * Returns true 50% of the time.
     */
    private boolean shouldAdd() {
        Random rand = new Random();
        int num = rand.nextInt(2);

        return num == 1;
    }


    private List<Genome> crossoverAll(Set<Genome> genomes) {
        List<Genome> crossedGenomes = new ArrayList<>(genomes.size());

        List<Genome> toCross = new ArrayList<>();
        for(Genome genome : genomes) {
            if (shouldAdd())
                toCross.add(genome);
        }

        Random rand = new Random();

        while (toCross.size() > 2) {
            // cross two random genomes
            Genome first = toCross.remove(rand.nextInt(toCross.size()));
            Genome second = toCross.remove(rand.nextInt(toCross.size()));

            Genome crossed = cross(first, second);
            crossedGenomes.add(crossed);
        }

        return crossedGenomes;
    }

    private Genome cross(Genome first, Genome second) {
        String firstStr = first.toString();
        int minLength = 2;

        int pivot = randomIntFromTo(minLength, firstStr.length() - minLength);

        String subFirst = firstStr.substring(0, pivot);
        String subSecond = second.toString().substring(pivot);

        String crossedGenome = subFirst + subSecond;

        return new Genome(crossedGenome);
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

    private List<Genome> tweakElites(List<Genome> elites) {
        List<Genome> tweaks = new ArrayList<>();
        Random rand = new Random();

        for (Genome elite : elites) {
            // tweak random number of genes
            for (int i = 0; i < rand.nextInt(3); i++) {
                int indexToTweak = rand.nextInt(Genome.numGenes());
                char c = Action.randomAction();
                StringBuilder str = new StringBuilder(elite.toString());
                str.setCharAt(indexToTweak, c);

                Genome tweaked = new Genome(str.toString());
                tweaks.add(tweaked);
            }
        }
        return tweaks;
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

    private int randomIntFromTo(int from, int to) {
        Random rand = new Random();

        return rand.nextInt(to - from) + from;
    }
}
