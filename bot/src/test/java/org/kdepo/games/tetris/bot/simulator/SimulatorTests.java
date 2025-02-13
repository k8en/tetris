package org.kdepo.games.tetris.bot.simulator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kdepo.games.tetris.bot.AdvancedBot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SimulatorTests {

    private static final Random RND = new Random(System.currentTimeMillis());

    @BeforeAll
    static void setup() {
        System.out.println("SimpleBotTests - Tests started");
    }

    @BeforeEach
    void init() {

    }

    @Test
    void testSimulate() {
        Simulator simulator = new Simulator();

        AdvancedBot bot = new AdvancedBot();
        SimulationResult simulationResult = simulator.simulate(bot);

        if (simulationResult.isGameOver()) {
            System.out.println("Status: GAME OVER");
        } else {
            System.out.println("Status: SUCCESS");
        }

        System.out.println("Score: " + simulationResult.getScoreTotal());
        System.out.println("Lines: " + (simulationResult.getLinesCount1() + simulationResult.getLinesCount2() + simulationResult.getLinesCount3() + simulationResult.getLinesCount4()));
        System.out.println(" - 1 : " + simulationResult.getLinesCount1());
        System.out.println(" - 2 : " + simulationResult.getLinesCount2());
        System.out.println(" - 3 : " + simulationResult.getLinesCount3());
        System.out.println(" - 4 : " + simulationResult.getLinesCount4());
    }

    @Test
    void testSimulate2() {

        SimulationResult bestResult = null;

        for (int coveredBlocksDelta = 0; coveredBlocksDelta < 100; coveredBlocksDelta++) {
            for (int pitsPeaksDelta = 0; pitsPeaksDelta < 100; pitsPeaksDelta++) {
                for (int densityDelta = 0; densityDelta < 100; densityDelta++) {
                    for (int overheightDelta = 0; overheightDelta < 100; overheightDelta++) {
                        AdvancedBot bot = new AdvancedBot();
                        bot.setCoveredBlocksWeight(0 + coveredBlocksDelta);
                        bot.setPitsPeaksWeight(0 + pitsPeaksDelta);
                        bot.setDensityWeight(0 + densityDelta);
                        bot.setOverheightWeight(0 + overheightDelta);

                        Simulator simulator = new Simulator();
                        SimulationResult currentResult = simulator.simulate(bot);

                        if (bestResult == null) {
                            bestResult = currentResult;
                            System.out.println(bestResult.getScoreTotal() + "(" + bestResult.getIterationsCompleted() + ") " + bot);

                        } else if (currentResult.getScoreTotal() > bestResult.getScoreTotal()) {
                            bestResult = currentResult;
                            System.out.println(bestResult.getScoreTotal() + "(" + bestResult.getIterationsCompleted() + ") " + bot);
                        }
                    }
                }
            }
            System.out.println("Percents completed: " + coveredBlocksDelta + " at " + new Date());
        }

        if (bestResult.isGameOver()) {
            System.out.println("Status: GAME OVER");
        } else {
            System.out.println("Status: SUCCESS");
        }

        System.out.println("Score: " + bestResult.getScoreTotal());
        System.out.println("Lines: " + (bestResult.getLinesCount1() + bestResult.getLinesCount2() + bestResult.getLinesCount3() + bestResult.getLinesCount4()));
        System.out.println(" - 1 : " + bestResult.getLinesCount1());
        System.out.println(" - 2 : " + bestResult.getLinesCount2());
        System.out.println(" - 3 : " + bestResult.getLinesCount3());
        System.out.println(" - 4 : " + bestResult.getLinesCount4());
    }

    @Test
    void testSimulate3() {
        Simulator simulator = new Simulator();

        int populationSize = 100;
        int coveredBlocksWeightMax = 100;
        int pitsPeaksWeightMax = 100;
        int densityWeightMax = 100;
        int overheightWeightMax = 100;

        // Generate initial population with random parameters
        List<AdvancedBot> population = simulator.generateInitialPopulation(populationSize, coveredBlocksWeightMax, pitsPeaksWeightMax, densityWeightMax, overheightWeightMax);

        AdvancedBot adam = new AdvancedBot();
        adam.setCoveredBlocksWeight(166);
        adam.setPitsPeaksWeight(23);
        adam.setDensityWeight(24);
        adam.setOverheightWeight(34);
        population.add(0, adam);

        while (true) {
            List<SimulationResult> resultsList = new ArrayList<>();
            for (AdvancedBot bot : population) {
                SimulationResult result = simulator.simulate(bot);
                result.setCoveredBlocksWeight(bot.getCoveredBlocksWeight());
                result.setPitsPeaksWeight(bot.getPitsPeaksWeight());
                result.setDensityWeight(bot.getDensityWeight());
                result.setOverheightWeight(bot.getOverheightWeight());
                resultsList.add(result);
            }

            // Sort to use top 10 records
            resultsList.sort((o1, o2) -> {
                Integer o1Score = o1.getScoreTotal();
                Integer o2Score = o2.getScoreTotal();
                return o2Score.compareTo(o1Score);
            });
            System.out.println(resultsList.get(0));

            // Check if we found good configuration
            if (resultsList.get(0).getScoreTotal() > 60000) {
                break;
            }

            // Mutate to new generation
            population.clear();
            AdvancedBot bestBot = new AdvancedBot();
            bestBot.setCoveredBlocksWeight(resultsList.get(0).getCoveredBlocksWeight());
            bestBot.setPitsPeaksWeight(resultsList.get(0).getPitsPeaksWeight());
            bestBot.setDensityWeight(resultsList.get(0).getDensityWeight());
            bestBot.setOverheightWeight(resultsList.get(0).getOverheightWeight());
            population.add(bestBot);
            for (int i = 0; i < 10; i++) {
                SimulationResult simulationResult = resultsList.get(i);
                for (int n = 0; n < 10; n++) {
                    AdvancedBot newbornBot = new AdvancedBot();
                    // Copy from parent
                    int coveredBlocksWeight = simulationResult.getCoveredBlocksWeight() + RND.nextInt(-10, 10);
                    newbornBot.setCoveredBlocksWeight(coveredBlocksWeight);

                    int pitsPeaksWeight = simulationResult.getPitsPeaksWeight() + RND.nextInt(-10, 10);
                    newbornBot.setPitsPeaksWeight(pitsPeaksWeight);

                    int densityWeight = simulationResult.getDensityWeight() + RND.nextInt(-10, 10);
                    newbornBot.setDensityWeight(densityWeight);

                    int overheightWeight = simulationResult.getOverheightWeight() + RND.nextInt(-10, 10);
                    newbornBot.setOverheightWeight(overheightWeight);

                    population.add(newbornBot);
                }
            }

            Collections.shuffle(population);
        }

        System.out.println("Completed");
    }

    @AfterEach
    void tearDown() {

    }

    @AfterAll
    static void done() {
        System.out.println("SimpleBotTests - Tests completed");
    }
}
