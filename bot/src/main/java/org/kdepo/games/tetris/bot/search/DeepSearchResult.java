package org.kdepo.games.tetris.bot.search;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.evaluation.classification.Evaluation;

public class DeepSearchResult {

    private final Evaluation evaluation;
    private final MultiLayerNetwork model;

    public DeepSearchResult(Evaluation evaluation, MultiLayerNetwork model) {
        this.evaluation = evaluation;
        this.model = model;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public MultiLayerNetwork getModel() {
        return model;
    }
}
