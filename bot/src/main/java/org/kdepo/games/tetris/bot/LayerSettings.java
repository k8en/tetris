package org.kdepo.games.tetris.bot;

import org.nd4j.linalg.activations.Activation;

import java.util.List;

public class LayerSettings {

    private int minNeurons;

    private int maxNeurons;

    private List<Activation> activationList;

    public int getMinNeurons() {
        return minNeurons;
    }

    public void setMinNeurons(int minNeurons) {
        this.minNeurons = minNeurons;
    }

    public int getMaxNeurons() {
        return maxNeurons;
    }

    public void setMaxNeurons(int maxNeurons) {
        this.maxNeurons = maxNeurons;
    }

    public List<Activation> getActivationList() {
        return activationList;
    }

    public void setActivationList(List<Activation> activationList) {
        this.activationList = activationList;
    }
}
