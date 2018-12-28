package TPN;

import processing.core.PApplet;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Evolution implements Runnable {

    private List<DNA> population = new CopyOnWriteArrayList<DNA>();

    private DNA bestFitnessScoreDNA;

    private Random selection = new Random();
    private Random random = new Random();

    private static int max = 128;
    private static int min = 32;
    private int textsize = 20;


    private PApplet app;

    private int numberOfGenes;

    private boolean isDone = false;

    public Evolution() {
        populateDNA(Main.getTarget());

    }

    public static int getMax() {
        return max;
    }

    public static int getMin() {
        return min;
    }

    public DNA getBestFitnessScoreDNA() {
        return bestFitnessScoreDNA;
    }

    public void popultatingMatingPool() {

        for (DNA dna : population) {
            if (bestFitnessScoreDNA == null) {
                bestFitnessScoreDNA = dna;
            } else if (dna.fitness() > bestFitnessScoreDNA.fitness()) {
                bestFitnessScoreDNA = dna;

            }
            int n = (int) (dna.fitness() * 100);

            for (int i = 0; i < n; i++) {
                Main.getMatingPool().add(dna);
            }
        }
    }

    public void evolving() {

        for (int i = 0; i < population.size(); i++) {

            int a = selection.nextInt(Main.getMatingPool().size());
            int b = selection.nextInt(Main.getMatingPool().size());
            DNA partnerA = Main.getMatingPool().get(a);
            DNA partnerB = Main.getMatingPool().get(b);


            DNA child = partnerA.crossover(partnerB);
            child.mutate(Main.getMutationRate());
            population.set(i, child);
        }

    }

    public void populateDNA(String target) {
        char[] genes;
        numberOfGenes = target.toCharArray().length;
        genes = new char[numberOfGenes];
        for (int i = 0; i < Main.getTotalPopultation(); i++) {
            for (int j = 0; j < numberOfGenes; j++) {
                int randomNum = random.nextInt((max - min) + 1) + min;
                genes[j] = (char) randomNum;
            }

            StringBuilder result = new StringBuilder();
            for (int j = 0; j < genes.length; j++) {
                result.append(genes[j]);
            }


            population.add(new DNA(result.toString()));
        }
    }

    public void run() {
        popultatingMatingPool();
    }

    public List<DNA> getPopulation() {
        return population;
    }


}
