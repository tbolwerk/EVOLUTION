package TPN;

import processing.core.PApplet;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main extends PApplet {

    private static String target = "To be or not to be that is a really really good question";
    private static int totalPopultation = 40;
    private static float mutationRate = 0.01f;
    private static int numberOfThreads = 4;
    private static List<DNA> matingPool = new CopyOnWriteArrayList<DNA>();
    private Evolution evolution;
    private int textsize = 20;

    public static void main(String[] args) {
        PApplet.main("TPN.Main");

    }

    public static String getTarget() {
        return target;
    }

    public static List<DNA> getMatingPool() {
        return matingPool;
    }

    public static float getMutationRate() {
        return mutationRate;
    }

    public static int getTotalPopultation() {
        return totalPopultation;
    }

    public void settings() {
        size(1000, 800);

    }

    public void setup() {
        frameRate(120);
        evolution = new Evolution();
    }

    public void draw() {
        long millis = millis() % 1000;
        long second = (millis() / 1000) % 60;
        long minute = (millis() / (1000 * 60)) % 60;
        long hour = (millis() / (1000 * 60 * 60)) % 24;

        background(0);
        new Thread(evolution).start();
        for (int i = 0; i < numberOfThreads; i++) {
            new Thread(new Evolution()).start();
        }
        if (matingPool.size() > 0)
            evolution.evolving();
        if (matingPool.size() >= evolution.getPopulation().size() + evolution.getPopulation().size() * numberOfThreads) {
            matingPool.clear();
        }
        System.out.println(matingPool.size());

        text("| Mutation:" + Main.getMutationRate() * 100 + "% | Threads: " + numberOfThreads + " | Time Elapsed: " + hour + ":" + minute + ":" + second + " | Target: " + getTarget(), 0, 0 + textsize * 1);
        text("| Target: " + getTarget(), 0, 0 + textsize * 2);
        if (evolution.getBestFitnessScoreDNA() != null)
            evolution.getBestFitnessScoreDNA().show(this, 0, textsize, textsize);
        if (evolution.getBestFitnessScoreDNA().fitness() == 1.0f) {
            stop();
        }
        int counter = 0;
        for (DNA dna : evolution.getPopulation()) {
            dna.show(this, 0, 80 + textsize * counter, textsize);
            counter++;
        }
//        evolution.evolving();

    }
}
