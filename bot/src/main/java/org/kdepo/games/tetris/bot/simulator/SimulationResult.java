package org.kdepo.games.tetris.bot.simulator;

public class SimulationResult {

    /**
     * Is simulation interrupted due to field overflow
     */
    private boolean isGameOver;

    /**
     * Number of iterations completed during simulation
     */
    private int iterationsCompleted;

    /**
     * Number of points scored during the simulation
     */
    private int scoreTotal;

    /**
     * How many times single line collected
     */
    private int linesCount1;

    /**
     * How many times double lines collected
     */
    private int linesCount2;

    /**
     * How many times triple lines collected
     */
    private int linesCount3;

    /**
     * How many times quadruple lines collected
     */
    private int linesCount4;

    /**
     * Parameter used to run simulation for {@link org.kdepo.games.tetris.bot.HeuristicBot}
     */
    private int coveredBlocksWeight;

    /**
     * Parameter used to run simulation for {@link org.kdepo.games.tetris.bot.HeuristicBot}
     */
    private int pitsPeaksWeight;

    /**
     * Parameter used to run simulation for {@link org.kdepo.games.tetris.bot.HeuristicBot}
     */
    private int densityWeight;

    /**
     * Parameter used to run simulation for {@link org.kdepo.games.tetris.bot.HeuristicBot}
     */
    private int overheightWeight;

    public SimulationResult() {
        isGameOver = false;
        iterationsCompleted = 0;
        scoreTotal = 0;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver() {
        isGameOver = true;
    }

    public int getIterationsCompleted() {
        return iterationsCompleted;
    }

    public void completeIteration() {
        iterationsCompleted++;
    }

    public int getScoreTotal() {
        return scoreTotal;
    }

    public void addScore(int score) {
        scoreTotal = scoreTotal + score;
    }

    public int getLinesCount1() {
        return linesCount1;
    }

    public void addLinesCount1() {
        linesCount1++;
    }

    public int getLinesCount2() {
        return linesCount2;
    }

    public void addLinesCount2() {
        linesCount2++;
    }

    public int getLinesCount3() {
        return linesCount3;
    }

    public void addLinesCount3() {
        linesCount3++;
    }

    public int getLinesCount4() {
        return linesCount4;
    }

    public void addLinesCount4() {
        linesCount4++;
    }

    public int getCoveredBlocksWeight() {
        return coveredBlocksWeight;
    }

    public void setCoveredBlocksWeight(int coveredBlocksWeight) {
        this.coveredBlocksWeight = coveredBlocksWeight;
    }

    public int getPitsPeaksWeight() {
        return pitsPeaksWeight;
    }

    public void setPitsPeaksWeight(int pitsPeaksWeight) {
        this.pitsPeaksWeight = pitsPeaksWeight;
    }

    public int getDensityWeight() {
        return densityWeight;
    }

    public void setDensityWeight(int densityWeight) {
        this.densityWeight = densityWeight;
    }

    public int getOverheightWeight() {
        return overheightWeight;
    }

    public void setOverheightWeight(int overheightWeight) {
        this.overheightWeight = overheightWeight;
    }

    @Override
    public String toString() {
        return "SimulationResult{" +
                "isGameOver=" + isGameOver +
                ", iterationsCompleted=" + iterationsCompleted +
                ", scoreTotal=" + scoreTotal +
                ", linesCount1=" + linesCount1 +
                ", linesCount2=" + linesCount2 +
                ", linesCount3=" + linesCount3 +
                ", linesCount4=" + linesCount4 +
                ", coveredBlocksWeight=" + coveredBlocksWeight +
                ", pitsPeaksWeight=" + pitsPeaksWeight +
                ", densityWeight=" + densityWeight +
                ", overheightWeight=" + overheightWeight +
                '}';
    }
}
