package org.kdepo.games.tetris.bot.search;

import org.nd4j.linalg.activations.Activation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BotTrainer {

    private static void trainFigureOrientation() throws IOException, InterruptedException {
        DeepSearch deepSearch = new DeepSearch();

        String fileName = "orientation_data.csv";
        int inputNeurons = 12;
        int outputNeurons = 4;

        List<Activation> activationInputList = new ArrayList<>();
        activationInputList.add(Activation.RELU);
        activationInputList.add(Activation.SIGMOID);
//        activationInputList.add(Activation.SOFTMAX);
        activationInputList.add(Activation.TANH);

        List<Activation> activationOutputList = new ArrayList<>();
//        activationOutputList.add(Activation.RELU);
//        activationOutputList.add(Activation.SIGMOID);
        activationOutputList.add(Activation.SOFTMAX);
//        activationOutputList.add(Activation.TANH);

        // Hidden 1
        List<Activation> activation1List = new ArrayList<>();
        activation1List.add(Activation.RELU);
        activation1List.add(Activation.SIGMOID);
//        activation1List.add(Activation.SOFTMAX);
        activation1List.add(Activation.TANH);

        LayerSettings hiddenLayer1 = new LayerSettings();
        hiddenLayer1.setMinNeurons(49);
        hiddenLayer1.setMaxNeurons(49);
        hiddenLayer1.setActivationList(activation1List);

        // Hidden 2
        List<Activation> activation2List = new ArrayList<>();
        activation2List.add(Activation.RELU);
        activation2List.add(Activation.SIGMOID);
//        activation2List.add(Activation.SOFTMAX);
        activation2List.add(Activation.TANH);

        LayerSettings hiddenLayer2 = new LayerSettings();
        hiddenLayer2.setMinNeurons(7);
        hiddenLayer2.setMaxNeurons(7);
        hiddenLayer2.setActivationList(activation2List);

        // Hidden 3
        List<Activation> activation3List = new ArrayList<>();
        activation3List.add(Activation.RELU);
        activation3List.add(Activation.SIGMOID);
//        activation3List.add(Activation.SOFTMAX);
        activation3List.add(Activation.TANH);

        LayerSettings hiddenLayer3 = new LayerSettings();
        hiddenLayer3.setMinNeurons(8);
        hiddenLayer3.setMaxNeurons(8);
        hiddenLayer3.setActivationList(activation3List);

        // Hidden 4
        List<Activation> activation4List = new ArrayList<>();
        activation4List.add(Activation.RELU);
        activation4List.add(Activation.SIGMOID);
//        activation4List.add(Activation.SOFTMAX);
        activation4List.add(Activation.TANH);

        LayerSettings hiddenLayer4 = new LayerSettings();
        hiddenLayer4.setMinNeurons(6);
        hiddenLayer4.setMaxNeurons(6);
        hiddenLayer4.setActivationList(activation4List);

        // Hidden 5
        List<Activation> activation5List = new ArrayList<>();
        activation5List.add(Activation.RELU);
        activation5List.add(Activation.SIGMOID);
//        activation5List.add(Activation.SOFTMAX);
        activation5List.add(Activation.TANH);

        LayerSettings hiddenLayer5 = new LayerSettings();
        hiddenLayer5.setMinNeurons(6);
        hiddenLayer5.setMaxNeurons(6);
        hiddenLayer5.setActivationList(activation5List);

        // Hidden 6
        List<Activation> activation6List = new ArrayList<>();
        activation6List.add(Activation.RELU);
        activation6List.add(Activation.SIGMOID);
//        activation6List.add(Activation.SOFTMAX);
        activation6List.add(Activation.TANH);

        LayerSettings hiddenLayer6 = new LayerSettings();
        hiddenLayer6.setMinNeurons(4);
        hiddenLayer6.setMaxNeurons(4);
        hiddenLayer6.setActivationList(activation6List);

        // Hidden 7
        List<Activation> activation7List = new ArrayList<>();
//        activation7List.add(Activation.RELU);
//        activation7List.add(Activation.SIGMOID);
        activation7List.add(Activation.SOFTMAX);
//        activation7List.add(Activation.TANH);

        LayerSettings hiddenLayer7 = new LayerSettings();
        hiddenLayer7.setMinNeurons(4);
        hiddenLayer7.setMaxNeurons(4);
        hiddenLayer7.setActivationList(activation7List);

        deepSearch.searchStructure(
                fileName,
                inputNeurons,
                activationInputList,
                outputNeurons,
                activationOutputList,
                hiddenLayer1
                , hiddenLayer2
//                , hiddenLayer3
//                , hiddenLayer4
//                , hiddenLayer5
//                , hiddenLayer6
//                , hiddenLayer7
        );
    }

    private static void trainFigurePosition() throws IOException, InterruptedException {
        DeepSearch deepSearch = new DeepSearch();

        String fileName = "position_data.csv";
        int inputNeurons = 13;
        int outputNeurons = 10;

        List<Activation> activationInputList = new ArrayList<>();
        activationInputList.add(Activation.RELU);
        activationInputList.add(Activation.SIGMOID);
//        activationInputList.add(Activation.SOFTMAX);
        activationInputList.add(Activation.TANH);

        List<Activation> activationOutputList = new ArrayList<>();
//        activationOutputList.add(Activation.RELU);
//        activationOutputList.add(Activation.SIGMOID);
        activationOutputList.add(Activation.SOFTMAX);
//        activationOutputList.add(Activation.TANH);

        // Hidden 1
        List<Activation> activation1List = new ArrayList<>();
        activation1List.add(Activation.RELU);
        activation1List.add(Activation.SIGMOID);
//        activation1List.add(Activation.SOFTMAX);
        activation1List.add(Activation.TANH);

        LayerSettings hiddenLayer1 = new LayerSettings();
        hiddenLayer1.setMinNeurons(40);
        hiddenLayer1.setMaxNeurons(40);
        hiddenLayer1.setActivationList(activation1List);

        // Hidden 2
        List<Activation> activation2List = new ArrayList<>();
        activation2List.add(Activation.RELU);
        activation2List.add(Activation.SIGMOID);
//        activation2List.add(Activation.SOFTMAX);
        activation2List.add(Activation.TANH);

        LayerSettings hiddenLayer2 = new LayerSettings();
        hiddenLayer2.setMinNeurons(10);
        hiddenLayer2.setMaxNeurons(40);
        hiddenLayer2.setActivationList(activation2List);

        // Hidden 3
        List<Activation> activation3List = new ArrayList<>();
        activation3List.add(Activation.RELU);
        activation3List.add(Activation.SIGMOID);
//        activation3List.add(Activation.SOFTMAX);
        activation3List.add(Activation.TANH);

        LayerSettings hiddenLayer3 = new LayerSettings();
        hiddenLayer3.setMinNeurons(10);
        hiddenLayer3.setMaxNeurons(40);
        hiddenLayer3.setActivationList(activation3List);

        // Hidden 4
        List<Activation> activation4List = new ArrayList<>();
        activation4List.add(Activation.RELU);
        activation4List.add(Activation.SIGMOID);
//        activation4List.add(Activation.SOFTMAX);
        activation4List.add(Activation.TANH);

        LayerSettings hiddenLayer4 = new LayerSettings();
        hiddenLayer4.setMinNeurons(10);
        hiddenLayer4.setMaxNeurons(40);
        hiddenLayer4.setActivationList(activation4List);

        // Hidden 5
        List<Activation> activation5List = new ArrayList<>();
//        activation5List.add(Activation.RELU);
//        activation5List.add(Activation.SIGMOID);
//        activation5List.add(Activation.SOFTMAX);
        activation5List.add(Activation.TANH);

        LayerSettings hiddenLayer5 = new LayerSettings();
        hiddenLayer5.setMinNeurons(12);
        hiddenLayer5.setMaxNeurons(24);
        hiddenLayer5.setActivationList(activation5List);

        // Hidden 6
        List<Activation> activation6List = new ArrayList<>();
//        activation6List.add(Activation.RELU);
//        activation6List.add(Activation.SIGMOID);
//        activation6List.add(Activation.SOFTMAX);
        activation6List.add(Activation.TANH);

        LayerSettings hiddenLayer6 = new LayerSettings();
        hiddenLayer6.setMinNeurons(12);
        hiddenLayer6.setMaxNeurons(24);
        hiddenLayer6.setActivationList(activation6List);

        // Hidden 7
        List<Activation> activation7List = new ArrayList<>();
//        activation7List.add(Activation.RELU);
//        activation7List.add(Activation.SIGMOID);
//        activation7List.add(Activation.SOFTMAX);
        activation7List.add(Activation.TANH);

        LayerSettings hiddenLayer7 = new LayerSettings();
        hiddenLayer7.setMinNeurons(12);
        hiddenLayer7.setMaxNeurons(24);
        hiddenLayer7.setActivationList(activation7List);

        deepSearch.searchStructure(
                fileName,
                inputNeurons,
                activationInputList,
                outputNeurons,
                activationOutputList,
                hiddenLayer1
                , hiddenLayer2
                , hiddenLayer3
                , hiddenLayer4
//                , hiddenLayer5
//                , hiddenLayer6
//                , hiddenLayer7
        );
    }

    public static void main(String[] args) throws IOException, InterruptedException {

//        trainFigureOrientation();
        trainFigurePosition();

    }
}