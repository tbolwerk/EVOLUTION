package TPN;

import processing.core.PApplet;

import java.util.Random;

public class DNA {

    private int numberOfGenes;

    private float fitness;
    private Random random = new Random();
    private char genes[];
    private boolean correct[];
    private PApplet app;


    public DNA(String buildSequence) {
        genes = buildSequence.toCharArray();
        correct = new boolean[genes.length];



    }

    public void show(PApplet app, int x, int y, int size) {
//        System.out.println("Result: " + toString() + " Fitness score: " + fitness());
        app.fill(255);
        app.textSize(size);
//        app.text("Target: " + TPN.Evolution.getTarget(),0,y+size*1);

        app.text("Result:  " + toString() + " Fitness score: " + fitness() * 100 + "%", x, size * 2 + y);

    }

    public float fitness() {
        int score = 0;
        for (int i = 0; i < genes.length; i++) {
            if (genes[i] == Main.getTarget().toCharArray()[i]) {
                correct[i] = true;
                score++;
            }
        }
        return (float) score / Main.getTarget().length();
    }

    public DNA crossover(DNA male) {
        StringBuilder childBuildSequence = new StringBuilder();
        int count = 0;
        for (int i = 0; i < correct.length; i++) {
            if (!correct[i]) {
                count++;
            }
        }
        if (count == 0) {
            count = 1;
        }
        int midpoint = random.nextInt(count);

        for (int i = 0; i < male.getGenes().length; i++) {
            if (!correct[i]) {
                if (i > midpoint) {
                    childBuildSequence.append(male.getGenes()[i]);
                } else {
                    childBuildSequence.append(genes[i]);
                }
            } else {
                childBuildSequence.append(genes[i]);
            }
        }
        DNA child = new DNA(childBuildSequence.toString());
        return child;
    }


    public void mutate(float mutationRate) {
        for (int i = 0; i < genes.length; i++) {
            if (!correct[i]) {
                if (random.nextInt(100) < (mutationRate * 100)) {
                    int randomNum = random.nextInt((Evolution.getMax() - Evolution.getMin()) + 1) + Evolution.getMin();
                    genes[i] = (char) randomNum;
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < genes.length; i++) {
            result.append(genes[i]);
        }

        return result.toString();
    }

    public char[] getGenes() {
        return genes;
    }
}
