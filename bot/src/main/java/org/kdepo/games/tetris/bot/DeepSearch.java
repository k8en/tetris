package org.kdepo.games.tetris.bot;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.conf.BackpropType;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.io.ClassPathResource;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DeepSearch {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";

    private double train(
            String fileName,
            int inputNeurons,
            Activation inputActivation,
            int outputNeurons,
            Activation outputActivation,
            int layer1neurons,
            Activation layer1Activation
    ) throws IOException, InterruptedException {
        DataSet allData;
        try (RecordReader recordReader = new CSVRecordReader(0, ',')) {
            recordReader.initialize(new FileSplit(new ClassPathResource(fileName).getFile()));

            DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, 50, inputNeurons, outputNeurons);
            allData = iterator.next();
        }

        allData.shuffle(42);

        DataNormalization normalizer = new NormalizerStandardize();
        normalizer.fit(allData);
        normalizer.transform(allData);

        SplitTestAndTrain testAndTrain = allData.splitTestAndTrain(0.65);
        DataSet trainingData = testAndTrain.getTrain();
        DataSet testData = testAndTrain.getTest();

        MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
                .iterations(1000)
                //.activation(Activation.TANH)
                .weightInit(WeightInit.XAVIER)
                .regularization(true)
                .learningRate(0.1).l2(0.0001)
                .list()
                .layer(0,
                        new DenseLayer
                                .Builder()
                                .activation(inputActivation)
                                .nIn(inputNeurons)
                                .nOut(layer1neurons)
                                .build()
                )
                .layer(1,
                        new DenseLayer
                                .Builder()
                                .activation(layer1Activation)
                                .nIn(layer1neurons)
                                .nOut(outputNeurons)
                                .build()
                )
                .layer(2,
                        new OutputLayer
                                .Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                                .activation(outputActivation)
                                .nIn(outputNeurons)
                                .nOut(outputNeurons)
                                .build()
                )
                .backpropType(BackpropType.Standard)
                .pretrain(false)
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(configuration);
        model.init();
        model.fit(trainingData);

        INDArray output = model.output(testData.getFeatures());

        Evaluation eval = new Evaluation(outputNeurons);
        eval.eval(testData.getLabels(), output);
        return eval.accuracy();
    }

    private double train(
            String fileName,
            int inputNeurons,
            Activation inputActivation,
            int outputNeurons,
            Activation outputActivation,
            int layer1neurons,
            Activation layer1Activation,
            int layer2neurons,
            Activation layer2Activation
    ) throws IOException, InterruptedException {
        DataSet allData;
        try (RecordReader recordReader = new CSVRecordReader(0, ',')) {
            recordReader.initialize(new FileSplit(new ClassPathResource(fileName).getFile()));

            DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, 50, inputNeurons, outputNeurons);
            allData = iterator.next();
        }

        allData.shuffle(42);

        DataNormalization normalizer = new NormalizerStandardize();
        normalizer.fit(allData);
        normalizer.transform(allData);

        SplitTestAndTrain testAndTrain = allData.splitTestAndTrain(0.65);
        DataSet trainingData = testAndTrain.getTrain();
        DataSet testData = testAndTrain.getTest();

        MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
                .iterations(1000)
                //.activation(Activation.TANH)
                .weightInit(WeightInit.XAVIER)
                .regularization(true)
                .learningRate(0.1).l2(0.0001)
                .list()
                .layer(0,
                        new DenseLayer
                                .Builder()
                                .activation(inputActivation)
                                .nIn(inputNeurons)
                                .nOut(layer1neurons)
                                .build()
                )
                .layer(1,
                        new DenseLayer
                                .Builder()
                                .activation(layer1Activation)
                                .nIn(layer1neurons)
                                .nOut(layer2neurons)
                                .build()
                )
                .layer(2,
                        new DenseLayer
                                .Builder()
                                .activation(layer2Activation)
                                .nIn(layer2neurons)
                                .nOut(outputNeurons)
                                .build()
                )
                .layer(3,
                        new OutputLayer
                                .Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                                .activation(outputActivation)
                                .nIn(outputNeurons)
                                .nOut(outputNeurons)
                                .build()
                )
                .backpropType(BackpropType.Standard)
                .pretrain(false)
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(configuration);
        model.init();
        model.fit(trainingData);

        INDArray output = model.output(testData.getFeatures());

        Evaluation eval = new Evaluation(outputNeurons);
        eval.eval(testData.getLabels(), output);
        return eval.accuracy();
    }

    public void searchStructure(String fileName,
                                int inputNeurons, List<Activation> activationInputList,
                                int outputNeurons, List<Activation> activationOutputList,
                                LayerSettings hiddenLayer1
    ) throws IOException, InterruptedException {

        double bestAccuracy = 0;
        String bestConfiguration = null;

        double totalIterations = inputNeurons * activationInputList.size() * outputNeurons * activationOutputList.size()
                * (hiddenLayer1.getMaxNeurons() - hiddenLayer1.getMinNeurons()) * hiddenLayer1.getActivationList().size();
        double currentIteration = 0;

        System.out.println(YELLOW + "Start time: " + new Date() + RESET);
        System.out.println(YELLOW + "Iterations: " + totalIterations + RESET);

        for (Activation inputActivation : activationInputList) {

            // Hidden layers section
            for (int layer1neurons = hiddenLayer1.getMinNeurons(); layer1neurons <= hiddenLayer1.getMaxNeurons(); layer1neurons++) {
                for (Activation layer1Activation : hiddenLayer1.getActivationList()) {

                    for (Activation outputActivation : activationOutputList) {

                        currentIteration = currentIteration + 1;

                        double currentAccuracy = train(
                                fileName,
                                inputNeurons,
                                inputActivation,
                                outputNeurons,
                                outputActivation,
                                layer1neurons,
                                layer1Activation
                        );

                        String currentConfiguration = DATE_FORMAT.format(new Date())
                                + " [" + inputNeurons + "-" + inputActivation + "]"
                                + " (" + layer1neurons + "-" + layer1Activation + ")"
                                + " [" + outputNeurons + "-" + outputActivation + "]"
                                + ": accuracy=" + currentAccuracy;

                        if (currentAccuracy > bestAccuracy) {
                            bestAccuracy = currentAccuracy;
                            bestConfiguration = currentConfiguration;

                            System.out.println(GREEN + bestConfiguration + " <--- BEST" + RESET);
                        } else {
                            double percent = (currentIteration * 100.0 / totalIterations);
                            System.out.println(currentConfiguration + " , completed=" + String.format("%.2f%%", percent));
                        }
                    }
                }
            }
        }

        System.out.println("End time: " + new Date());
        System.out.println(bestConfiguration);
    }

    public void searchStructure(String fileName,
                                int inputNeurons, List<Activation> activationInputList,
                                int outputNeurons, List<Activation> activationOutputList,
                                LayerSettings hiddenLayer1,
                                LayerSettings hiddenLayer2
    ) throws IOException, InterruptedException {

        double bestAccuracy = 0;
        String bestConfiguration = null;

        double totalIterations = inputNeurons * activationInputList.size() * outputNeurons * activationOutputList.size()
                * (hiddenLayer1.getMaxNeurons() - hiddenLayer1.getMinNeurons()) * hiddenLayer1.getActivationList().size()
                * (hiddenLayer2.getMaxNeurons() - hiddenLayer2.getMinNeurons()) * hiddenLayer2.getActivationList().size();
        double currentIteration = 0;

        System.out.println(YELLOW + "Start time: " + new Date() + RESET);
        System.out.println(YELLOW + "Iterations: " + totalIterations + RESET);

        for (Activation inputActivation : activationInputList) {

            // Hidden layers section
            for (int layer1neurons = hiddenLayer1.getMinNeurons(); layer1neurons <= hiddenLayer1.getMaxNeurons(); layer1neurons++) {
                for (Activation layer1Activation : hiddenLayer1.getActivationList()) {

                    for (int layer2neurons = hiddenLayer2.getMinNeurons(); layer2neurons <= hiddenLayer2.getMaxNeurons(); layer2neurons++) {
                        for (Activation layer2Activation : hiddenLayer2.getActivationList()) {

                            for (Activation outputActivation : activationOutputList) {

                                currentIteration = currentIteration + 1;

                                double currentAccuracy = train(
                                        fileName,
                                        inputNeurons,
                                        inputActivation,
                                        outputNeurons,
                                        outputActivation,
                                        layer1neurons,
                                        layer1Activation,
                                        layer2neurons,
                                        layer2Activation
                                );

                                String currentConfiguration = DATE_FORMAT.format(new Date())
                                        + " [" + inputNeurons + "-" + inputActivation + "]"
                                        + " (" + layer1neurons + "-" + layer1Activation + ")"
                                        + " (" + layer2neurons + "-" + layer2Activation + ")"
                                        + " [" + outputNeurons + "-" + outputActivation + "]"
                                        + ": accuracy=" + currentAccuracy;

                                if (currentAccuracy > bestAccuracy) {
                                    bestAccuracy = currentAccuracy;
                                    bestConfiguration = currentConfiguration;

                                    System.out.println(GREEN + bestConfiguration + " <--- BEST" + RESET);
                                } else {
                                    double percent = (currentIteration * 100.0 / totalIterations);
                                    System.out.println(currentConfiguration + " , completed=" + String.format("%.2f%%", percent));
                                }
                            }

                        }
                    }

                }
            }
        }

        System.out.println("End time: " + new Date());
        System.out.println(bestConfiguration);
    }
}
