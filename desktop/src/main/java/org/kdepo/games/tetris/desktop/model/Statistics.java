package org.kdepo.games.tetris.desktop.model;

public class Statistics {

    private int lines;

    private int score;

    private int figuresType1;

    private int figuresType2;

    private int figuresType3;

    private int figuresType4;

    private int figuresType5;

    private int figuresType6;

    public Statistics() {
        lines = 0;
        score = 0;
        figuresType1 = 0;
        figuresType2 = 0;
        figuresType3 = 0;
        figuresType4 = 0;
        figuresType5 = 0;
        figuresType6 = 0;
    }

    public void addLines(int lines) {
        this.lines = this.lines + lines;
    }

    public int getLines() {
        return lines;
    }

    public void addScore(int score) {
        this.score = this.score + score;
    }

    public int getScore() {
        return score;
    }

    public int getFigures() {
        return figuresType1 + figuresType2 + figuresType3 + figuresType4 + figuresType5 + figuresType6;
    }

    public void addFiguresType1(int figuresType1) {
        this.figuresType1 = this.figuresType1 + figuresType1;
    }

    public int getFiguresType1() {
        return figuresType1;
    }

    public void addFiguresType2(int figuresType2) {
        this.figuresType2 = this.figuresType2 + figuresType2;
    }

    public int getFiguresType2() {
        return figuresType2;
    }

    public void addFiguresType3(int figuresType3) {
        this.figuresType3 = this.figuresType3 + figuresType3;
    }

    public int getFiguresType3() {
        return figuresType3;
    }

    public void addFiguresType4(int figuresType4) {
        this.figuresType4 = this.figuresType4 + figuresType4;
    }

    public int getFiguresType4() {
        return figuresType4;
    }

    public void addFiguresType5(int figuresType5) {
        this.figuresType5 = this.figuresType5 + figuresType5;
    }

    public int getFiguresType5() {
        return figuresType5;
    }

    public void addFiguresType6(int figuresType6) {
        this.figuresType6 = this.figuresType6 + figuresType6;
    }

    public int getFiguresType6() {
        return figuresType6;
    }
}
