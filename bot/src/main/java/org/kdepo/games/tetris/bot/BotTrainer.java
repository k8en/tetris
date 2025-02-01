package org.kdepo.games.tetris.bot;

import org.nd4j.linalg.activations.Activation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BotTrainer {

    private static void trainFigureOrientation() throws IOException, InterruptedException {
        DeepSearch deepSearch = new DeepSearch();

        String fileName = "orientation_data.csv";

        int inputNeurons = 12;
        List<Activation> activationInputList = new ArrayList<>();
        activationInputList.add(Activation.RELU);
        activationInputList.add(Activation.SIGMOID);
        activationInputList.add(Activation.SOFTMAX);
        activationInputList.add(Activation.TANH);

        int outputNeurons = 4;
        List<Activation> activationOutputList = new ArrayList<>();
        activationOutputList.add(Activation.RELU);
        activationOutputList.add(Activation.SIGMOID);
        activationOutputList.add(Activation.SOFTMAX);
        activationOutputList.add(Activation.TANH);

        // Hidden 1
        List<Activation> activation1List = new ArrayList<>();
        activation1List.add(Activation.RELU);
        activation1List.add(Activation.SIGMOID);
        activation1List.add(Activation.SOFTMAX);
        activation1List.add(Activation.TANH);

        LayerSettings hiddenLayer1 = new LayerSettings();
        hiddenLayer1.setMinNeurons(4);
        hiddenLayer1.setMaxNeurons(12);
        hiddenLayer1.setActivationList(activation1List);

        // Hidden 2
        List<Activation> activation2List = new ArrayList<>();
        activation2List.add(Activation.RELU);
        activation2List.add(Activation.SIGMOID);
        activation2List.add(Activation.SOFTMAX);
        activation2List.add(Activation.TANH);

        LayerSettings hiddenLayer2 = new LayerSettings();
        hiddenLayer2.setMinNeurons(4);
        hiddenLayer2.setMaxNeurons(12);
        hiddenLayer2.setActivationList(activation2List);

        deepSearch.searchStructure(
                fileName,
                inputNeurons,
                activationInputList,
                outputNeurons,
                activationOutputList,
                hiddenLayer1,
                hiddenLayer2
        );
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        trainFigureOrientation();

    }
}