import processing.core.PApplet;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Evolution extends PApplet implements Runnable {
    private static int max = 128;
    private static int min = 32;
    private static String target = "Hij doet het heel goed";
    private List<DNA> population = new CopyOnWriteArrayList<DNA>();
    private List<DNA> matingPool = new CopyOnWriteArrayList<DNA>();
    private int numberOfGenes;
    private int textsize = 20;
    private float mutationRate = 0.01f;
    private DNA bestFitnessScoreDNA;
    private int totalPopultation = 30;
    private Random selection = new Random();
    private Random random = new Random();

    public static void main(String[] args) {
        PApplet.main("Evolution");
    }

    public static String getTarget() {
        return target;
    }

    public static int getMax() {
        return max;
    }

    public static int getMin() {
        return min;
    }

    public void settings() {
        size(800, 800);

    }

    public void setup() {

        populateDNA(target);

    }

    public void draw() {
        background(0);
        text("Target: " + Evolution.getTarget() + "| Mutation:" + mutationRate * 100 + "%", 0, 0 + textsize * 1);
        run();

        int counter = 0;
        for (DNA dna : population) {
            dna.show(0, 80 + textsize * counter, textsize);
            counter++;
        }

    }

    public void evolving() {
        for (DNA dna : population) {
            if (bestFitnessScoreDNA == null) {
                bestFitnessScoreDNA = dna;
            } else if (dna.fitness() > bestFitnessScoreDNA.fitness()) {
                bestFitnessScoreDNA = dna;

            }
            int n = (int) (dna.fitness() * 100);

            for (int i = 0; i < n; i++) {
                matingPool.add(dna);
            }
        }
        if (bestFitnessScoreDNA != null)
            bestFitnessScoreDNA.show(0, textsize, textsize);
        for (int i = 0; i < population.size(); i++) {

            int a = selection.nextInt(matingPool.size());
            int b = selection.nextInt(matingPool.size());
            DNA partnerA = matingPool.get(a);
            DNA partnerB = matingPool.get(b);


            DNA child = partnerA.crossover(partnerB);
            child.mutate(mutationRate);
            population.set(i, child);
        }
        matingPool.clear();
    }

    public void populateDNA(String target) {
        char[] genes;
        numberOfGenes = target.toCharArray().length;
        genes = new char[numberOfGenes];
        for (int i = 0; i < totalPopultation; i++) {
            for (int j = 0; j < numberOfGenes; j++) {
                int randomNum = random.nextInt((max - min) + 1) + min;
                genes[j] = (char) randomNum;
            }

            StringBuilder result = new StringBuilder();
            for (int j = 0; j < genes.length; j++) {
                result.append(genes[j]);
            }


            population.add(new DNA(this, result.toString()));
        }
    }

    public void run() {
        evolving();
    }
}
