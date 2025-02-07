package org.kdepo.games.tetris.bot.search;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.conf.BackpropType;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.common.io.ClassPathResource;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
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

    private DeepSearchResult train(
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
                .seed(42)
                .activation(Activation.TANH)
                .weightInit(WeightInit.XAVIER)
                .updater(new Sgd(0.1))
                .l2(1e-4)
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
                                .Builder(LossFunctions.LossFunction.MSE)
                                .activation(outputActivation)
                                .nIn(outputNeurons)
                                .nOut(outputNeurons)
                                .build()
                )
                .backpropType(BackpropType.Standard)
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(configuration);
        model.init();
        model.fit(trainingData);

        INDArray output = model.output(testData.getFeatures());

        Evaluation eval = new Evaluation(outputNeurons);
        eval.eval(testData.getLabels(), output);

        return new DeepSearchResult(eval, model);
    }

    private DeepSearchResult train(
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
                .seed(6)
                .activation(Activation.TANH)
                .weightInit(WeightInit.XAVIER)
                .updater(new Sgd(0.1))
                .l2(1e-4)
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
                                .Builder(LossFunctions.LossFunction.MSE)
                                .activation(outputActivation)
                                .nIn(outputNeurons)
                                .nOut(outputNeurons)
                                .build()
                )
                .backpropType(BackpropType.Standard)
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(configuration);
        model.init();
        model.fit(trainingData);

        INDArray output = model.output(testData.getFeatures());

        Evaluation eval = new Evaluation(outputNeurons);
        eval.eval(testData.getLabels(), output);

        return new DeepSearchResult(eval, model);
    }

    private DeepSearchResult train(
            String fileName,
            int inputNeurons,
            Activation inputActivation,
            int outputNeurons,
            Activation outputActivation,
            int layer1neurons,
            Activation layer1Activation,
            int layer2neurons,
            Activation layer2Activation,
            int layer3neurons,
            Activation layer3Activation
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
                .seed(6)
                .activation(Activation.TANH)
                .weightInit(WeightInit.XAVIER)
                .updater(new Sgd(0.1))
                .l2(1e-4)
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
                                .nOut(layer3neurons)
                                .build()
                )
                .layer(3,
                        new DenseLayer
                                .Builder()
                                .activation(layer3Activation)
                                .nIn(layer3neurons)
                                .nOut(outputNeurons)
                                .build()
                )
                .layer(4,
                        new OutputLayer
                                .Builder(LossFunctions.LossFunction.MSE)
                                .activation(outputActivation)
                                .nIn(outputNeurons)
                                .nOut(outputNeurons)
                                .build()
                )
                .backpropType(BackpropType.Standard)
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(configuration);
        model.init();
        model.fit(trainingData);

        INDArray output = model.output(testData.getFeatures());

        Evaluation eval = new Evaluation(outputNeurons);
        eval.eval(testData.getLabels(), output);

        return new DeepSearchResult(eval, model);
    }

    private DeepSearchResult train(
            String fileName,
            int inputNeurons,
            Activation inputActivation,
            int outputNeurons,
            Activation outputActivation,
            int layer1neurons,
            Activation layer1Activation,
            int layer2neurons,
            Activation layer2Activation,
            int layer3neurons,
            Activation layer3Activation,
            int layer4neurons,
            Activation layer4Activation
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
                //.seed(6) // ???
                .activation(Activation.TANH)
                .weightInit(WeightInit.XAVIER)
                .updater(new Sgd(0.1))
                .l2(1e-4)
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
                                .nOut(layer3neurons)
                                .build()
                )
                .layer(3,
                        new DenseLayer
                                .Builder()
                                .activation(layer3Activation)
                                .nIn(layer3neurons)
                                .nOut(layer4neurons)
                                .build()
                )
                .layer(4,
                        new DenseLayer
                                .Builder()
                                .activation(layer4Activation)
                                .nIn(layer4neurons)
                                .nOut(outputNeurons)
                                .build()
                )
                .layer(5,
                        new OutputLayer
                                .Builder(LossFunctions.LossFunction.MSE)
                                .activation(outputActivation)
                                .nIn(outputNeurons)
                                .nOut(outputNeurons)
                                .build()
                )
                .backpropType(BackpropType.Standard)
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(configuration);
        model.init();
        model.fit(trainingData);

        INDArray output = model.output(testData.getFeatures());

        Evaluation eval = new Evaluation(outputNeurons);
        eval.eval(testData.getLabels(), output);

        return new DeepSearchResult(eval, model);
    }

    private DeepSearchResult train(
            String fileName,
            int inputNeurons,
            Activation inputActivation,
            int outputNeurons,
            Activation outputActivation,
            int layer1neurons,
            Activation layer1Activation,
            int layer2neurons,
            Activation layer2Activation,
            int layer3neurons,
            Activation layer3Activation,
            int layer4neurons,
            Activation layer4Activation,
            int layer5neurons,
            Activation layer5Activation
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
                .seed(6)
                .activation(Activation.TANH)
                .weightInit(WeightInit.XAVIER)
                .updater(new Sgd(0.1))
                .l2(1e-4)
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
                                .nOut(layer3neurons)
                                .build()
                )
                .layer(3,
                        new DenseLayer
                                .Builder()
                                .activation(layer3Activation)
                                .nIn(layer3neurons)
                                .nOut(layer4neurons)
                                .build()
                )
                .layer(4,
                        new DenseLayer
                                .Builder()
                                .activation(layer4Activation)
                                .nIn(layer4neurons)
                                .nOut(layer5neurons)
                                .build()
                )
                .layer(5,
                        new DenseLayer
                                .Builder()
                                .activation(layer5Activation)
                                .nIn(layer5neurons)
                                .nOut(outputNeurons)
                                .build()
                )
                .layer(6,
                        new OutputLayer
                                .Builder(LossFunctions.LossFunction.MSE)
                                .activation(outputActivation)
                                .nIn(outputNeurons)
                                .nOut(outputNeurons)
                                .build()
                )
                .backpropType(BackpropType.Standard)
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(configuration);
        model.init();
        model.fit(trainingData);

        INDArray output = model.output(testData.getFeatures());

        Evaluation eval = new Evaluation(outputNeurons);
        eval.eval(testData.getLabels(), output);

        return new DeepSearchResult(eval, model);
    }

    private DeepSearchResult train(
            String fileName,
            int inputNeurons,
            Activation inputActivation,
            int outputNeurons,
            Activation outputActivation,
            int layer1neurons,
            Activation layer1Activation,
            int layer2neurons,
            Activation layer2Activation,
            int layer3neurons,
            Activation layer3Activation,
            int layer4neurons,
            Activation layer4Activation,
            int layer5neurons,
            Activation layer5Activation,
            int layer6neurons,
            Activation layer6Activation
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
                .seed(6)
                .activation(Activation.TANH)
                .weightInit(WeightInit.XAVIER)
                .updater(new Sgd(0.1))
                .l2(1e-4)
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
                                .nOut(layer3neurons)
                                .build()
                )
                .layer(3,
                        new DenseLayer
                                .Builder()
                                .activation(layer3Activation)
                                .nIn(layer3neurons)
                                .nOut(layer4neurons)
                                .build()
                )
                .layer(4,
                        new DenseLayer
                                .Builder()
                                .activation(layer4Activation)
                                .nIn(layer4neurons)
                                .nOut(layer5neurons)
                                .build()
                )
                .layer(5,
                        new DenseLayer
                                .Builder()
                                .activation(layer5Activation)
                                .nIn(layer5neurons)
                                .nOut(layer6neurons)
                                .build()
                )
                .layer(6,
                        new DenseLayer
                                .Builder()
                                .activation(layer6Activation)
                                .nIn(layer6neurons)
                                .nOut(outputNeurons)
                                .build()
                )
                .layer(7,
                        new OutputLayer
                                .Builder(LossFunctions.LossFunction.MSE)
                                .activation(outputActivation)
                                .nIn(outputNeurons)
                                .nOut(outputNeurons)
                                .build()
                )
                .backpropType(BackpropType.Standard)
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(configuration);
        model.init();
        model.fit(trainingData);

        INDArray output = model.output(testData.getFeatures());

        Evaluation eval = new Evaluation(outputNeurons);
        eval.eval(testData.getLabels(), output);

        return new DeepSearchResult(eval, model);
    }

    private DeepSearchResult train(
            String fileName,
            int inputNeurons,
            Activation inputActivation,
            int outputNeurons,
            Activation outputActivation,
            int layer1neurons,
            Activation layer1Activation,
            int layer2neurons,
            Activation layer2Activation,
            int layer3neurons,
            Activation layer3Activation,
            int layer4neurons,
            Activation layer4Activation,
            int layer5neurons,
            Activation layer5Activation,
            int layer6neurons,
            Activation layer6Activation,
            int layer7neurons,
            Activation layer7Activation
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
                .seed(6)
                .activation(Activation.TANH)
                .weightInit(WeightInit.XAVIER)
                .updater(new Sgd(0.1))
                .l2(1e-4)
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
                                .nOut(layer3neurons)
                                .build()
                )
                .layer(3,
                        new DenseLayer
                                .Builder()
                                .activation(layer3Activation)
                                .nIn(layer3neurons)
                                .nOut(layer4neurons)
                                .build()
                )
                .layer(4,
                        new DenseLayer
                                .Builder()
                                .activation(layer4Activation)
                                .nIn(layer4neurons)
                                .nOut(layer5neurons)
                                .build()
                )
                .layer(5,
                        new DenseLayer
                                .Builder()
                                .activation(layer5Activation)
                                .nIn(layer5neurons)
                                .nOut(layer6neurons)
                                .build()
                )
                .layer(6,
                        new DenseLayer
                                .Builder()
                                .activation(layer6Activation)
                                .nIn(layer6neurons)
                                .nOut(layer7neurons)
                                .build()
                )
                .layer(7,
                        new DenseLayer
                                .Builder()
                                .activation(layer7Activation)
                                .nIn(layer7neurons)
                                .nOut(outputNeurons)
                                .build()
                )
                .layer(8,
                        new OutputLayer
                                .Builder(LossFunctions.LossFunction.MSE)
                                .activation(outputActivation)
                                .nIn(outputNeurons)
                                .nOut(outputNeurons)
                                .build()
                )
                .backpropType(BackpropType.Standard)
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(configuration);
        model.init();
        model.fit(trainingData);

        INDArray output = model.output(testData.getFeatures());

        Evaluation eval = new Evaluation(outputNeurons);
        eval.eval(testData.getLabels(), output);

        return new DeepSearchResult(eval, model);
    }

    // 1 hidden layer
    public void searchStructure(String fileName,
                                int inputNeurons, List<Activation> activationInputList,
                                int outputNeurons, List<Activation> activationOutputList,
                                LayerSettings hiddenLayer1
    ) throws IOException, InterruptedException {

        double bestAccuracy = 0;
        String bestConfiguration = null;

        double totalIterations = 0;
        for (Activation inputActivation : activationInputList) {
            for (int layer1neurons = hiddenLayer1.getMinNeurons(); layer1neurons <= hiddenLayer1.getMaxNeurons(); layer1neurons++) {
                for (Activation layer1Activation : hiddenLayer1.getActivationList()) {
                    for (Activation outputActivation : activationOutputList) {
                        totalIterations++;
                    }
                }
            }
        }

        double currentIteration = 0;
        double onePercentIterations = totalIterations / 100;
        double iterationNotification = onePercentIterations;

        System.out.println(YELLOW + "Start time: " + new Date() + RESET);
        System.out.println(YELLOW + "Iterations: " + totalIterations + RESET);

        for (Activation inputActivation : activationInputList) {

            // Hidden layers section
            for (int layer1neurons = hiddenLayer1.getMinNeurons(); layer1neurons <= hiddenLayer1.getMaxNeurons(); layer1neurons++) {
                for (Activation layer1Activation : hiddenLayer1.getActivationList()) {

                    for (Activation outputActivation : activationOutputList) {

                        currentIteration = currentIteration + 1;

                        DeepSearchResult deepSearchResult = train(
                                fileName,
                                inputNeurons,
                                inputActivation,
                                outputNeurons,
                                outputActivation,
                                layer1neurons,
                                layer1Activation
                        );

                        if (currentIteration >= iterationNotification) {
                            System.out.println("Completed " + currentIteration + " of " + totalIterations);
                            iterationNotification = iterationNotification + onePercentIterations;
                        }

                        String currentConfiguration = DATE_FORMAT.format(new Date())
                                + " [" + inputNeurons + "-" + inputActivation + "]"
                                + " (" + layer1neurons + "-" + layer1Activation + ")"
                                + " [" + outputNeurons + "-" + outputActivation + "]"
                                + ": accuracy=" + deepSearchResult.getEvaluation().accuracy();

                        if (deepSearchResult.getEvaluation().accuracy() > bestAccuracy) {
                            bestAccuracy = deepSearchResult.getEvaluation().accuracy();
                            bestConfiguration = currentConfiguration;

                            String saveFileName = "C:\\Data\\tmp\\" + inputNeurons + inputActivation
                                    + "_" + layer1neurons + layer1Activation
                                    + "_" + outputNeurons + outputActivation;
                            deepSearchResult.getModel().save(new File(saveFileName));

                            System.out.println(YELLOW + bestConfiguration + RESET);
                            System.out.println(YELLOW + "Saved at " + saveFileName + RESET);
                            System.out.println(GREEN + deepSearchResult.getEvaluation().stats() + RESET);
                            System.out.println();
                        }
                    }
                }
            }
        }

        System.out.println("End time: " + new Date());
        System.out.println(bestConfiguration);
    }

    // 2 hidden layers
    public void searchStructure(String fileName,
                                int inputNeurons, List<Activation> activationInputList,
                                int outputNeurons, List<Activation> activationOutputList,
                                LayerSettings hiddenLayer1,
                                LayerSettings hiddenLayer2
    ) throws IOException, InterruptedException {

        double bestAccuracy = 0;
        String bestConfiguration = null;

        double totalIterations = 0;
        for (Activation inputActivation : activationInputList) {
            for (int layer1neurons = hiddenLayer1.getMinNeurons(); layer1neurons <= hiddenLayer1.getMaxNeurons(); layer1neurons++) {
                for (Activation layer1Activation : hiddenLayer1.getActivationList()) {
                    for (int layer2neurons = hiddenLayer2.getMinNeurons(); layer2neurons <= hiddenLayer2.getMaxNeurons(); layer2neurons++) {
                        for (Activation layer2Activation : hiddenLayer2.getActivationList()) {
                            for (Activation outputActivation : activationOutputList) {
                                totalIterations++;
                            }
                        }
                    }
                }
            }
        }

        double currentIteration = 0;
        double onePercentIterations = totalIterations / 100;
        double iterationNotification = onePercentIterations;

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

                                DeepSearchResult deepSearchResult = train(
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

                                if (currentIteration >= iterationNotification) {
                                    System.out.println("Completed " + currentIteration + " of " + totalIterations);
                                    iterationNotification = iterationNotification + onePercentIterations;
                                }

                                String currentConfiguration = DATE_FORMAT.format(new Date())
                                        + " [" + inputNeurons + "-" + inputActivation + "]"
                                        + " (" + layer1neurons + "-" + layer1Activation + ")"
                                        + " (" + layer2neurons + "-" + layer2Activation + ")"
                                        + " [" + outputNeurons + "-" + outputActivation + "]"
                                        + ": accuracy=" + deepSearchResult.getEvaluation().accuracy();

                                if (deepSearchResult.getEvaluation().accuracy() > bestAccuracy) {
                                    bestAccuracy = deepSearchResult.getEvaluation().accuracy();
                                    bestConfiguration = currentConfiguration;

                                    String saveFileName = "C:\\Data\\tmp\\" + inputNeurons + inputActivation
                                            + "_" + layer1neurons + layer1Activation
                                            + "_" + layer2neurons + layer2Activation
                                            + "_" + outputNeurons + outputActivation;
                                    deepSearchResult.getModel().save(new File(saveFileName));

                                    System.out.println(YELLOW + bestConfiguration + RESET);
                                    System.out.println(YELLOW + "Saved at " + saveFileName + RESET);
                                    System.out.println(GREEN + deepSearchResult.getEvaluation().stats() + RESET);
                                    System.out.println();
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

    // 3 hidden layers
    public void searchStructure(String fileName,
                                int inputNeurons, List<Activation> activationInputList,
                                int outputNeurons, List<Activation> activationOutputList,
                                LayerSettings hiddenLayer1,
                                LayerSettings hiddenLayer2,
                                LayerSettings hiddenLayer3
    ) throws IOException, InterruptedException {

        double bestAccuracy = 0;
        String bestConfiguration = null;

        double totalIterations = 0;
        for (Activation inputActivation : activationInputList) {
            for (int layer1neurons = hiddenLayer1.getMinNeurons(); layer1neurons <= hiddenLayer1.getMaxNeurons(); layer1neurons++) {
                for (Activation layer1Activation : hiddenLayer1.getActivationList()) {
                    for (int layer2neurons = hiddenLayer2.getMinNeurons(); layer2neurons <= hiddenLayer2.getMaxNeurons(); layer2neurons++) {
                        for (Activation layer2Activation : hiddenLayer2.getActivationList()) {
                            for (int layer3neurons = hiddenLayer3.getMinNeurons(); layer3neurons <= hiddenLayer3.getMaxNeurons(); layer3neurons++) {
                                for (Activation layer3Activation : hiddenLayer3.getActivationList()) {
                                    for (Activation outputActivation : activationOutputList) {
                                        totalIterations++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        double currentIteration = 0;
        double onePercentIterations = totalIterations / 100;
        double iterationNotification = onePercentIterations;

        System.out.println(YELLOW + "Start time: " + new Date() + RESET);
        System.out.println(YELLOW + "Iterations: " + totalIterations + RESET);

        for (Activation inputActivation : activationInputList) {

            // Hidden layers section
            for (int layer1neurons = hiddenLayer1.getMinNeurons(); layer1neurons <= hiddenLayer1.getMaxNeurons(); layer1neurons++) {
                for (Activation layer1Activation : hiddenLayer1.getActivationList()) {

                    for (int layer2neurons = hiddenLayer2.getMinNeurons(); layer2neurons <= hiddenLayer2.getMaxNeurons(); layer2neurons++) {
                        for (Activation layer2Activation : hiddenLayer2.getActivationList()) {

                            for (int layer3neurons = hiddenLayer3.getMinNeurons(); layer3neurons <= hiddenLayer3.getMaxNeurons(); layer3neurons++) {
                                for (Activation layer3Activation : hiddenLayer3.getActivationList()) {

                                    for (Activation outputActivation : activationOutputList) {

                                        currentIteration = currentIteration + 1;

                                        DeepSearchResult deepSearchResult = train(
                                                fileName,
                                                inputNeurons,
                                                inputActivation,
                                                outputNeurons,
                                                outputActivation,
                                                layer1neurons,
                                                layer1Activation,
                                                layer2neurons,
                                                layer2Activation,
                                                layer3neurons,
                                                layer3Activation
                                        );

                                        if (currentIteration >= iterationNotification) {
                                            System.out.println("Completed " + currentIteration + " of " + totalIterations);
                                            iterationNotification = iterationNotification + onePercentIterations;
                                        }

                                        String currentConfiguration = DATE_FORMAT.format(new Date())
                                                + " [" + inputNeurons + "-" + inputActivation + "]"
                                                + " (" + layer1neurons + "-" + layer1Activation + ")"
                                                + " (" + layer2neurons + "-" + layer2Activation + ")"
                                                + " (" + layer3neurons + "-" + layer3Activation + ")"
                                                + " [" + outputNeurons + "-" + outputActivation + "]"
                                                + ": accuracy=" + deepSearchResult.getEvaluation().accuracy();

                                        if (deepSearchResult.getEvaluation().accuracy() > bestAccuracy) {
                                            bestAccuracy = deepSearchResult.getEvaluation().accuracy();
                                            bestConfiguration = currentConfiguration;

                                            String saveFileName = "C:\\Data\\tmp\\" + inputNeurons + inputActivation
                                                    + "_" + layer1neurons + layer1Activation
                                                    + "_" + layer2neurons + layer2Activation
                                                    + "_" + layer3neurons + layer3Activation
                                                    + "_" + outputNeurons + outputActivation;
                                            deepSearchResult.getModel().save(new File(saveFileName));

                                            System.out.println(YELLOW + bestConfiguration + RESET);
                                            System.out.println(YELLOW + "Saved at " + saveFileName + RESET);
                                            System.out.println(GREEN + deepSearchResult.getEvaluation().stats() + RESET);
                                            System.out.println();
                                        }
                                    }
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

    // 4 hidden layers
    public void searchStructure(String fileName,
                                int inputNeurons, List<Activation> activationInputList,
                                int outputNeurons, List<Activation> activationOutputList,
                                LayerSettings hiddenLayer1,
                                LayerSettings hiddenLayer2,
                                LayerSettings hiddenLayer3,
                                LayerSettings hiddenLayer4
    ) throws IOException, InterruptedException {

        double bestAccuracy = 0;
        String bestConfiguration = null;

        double totalIterations = 0;
        for (Activation inputActivation : activationInputList) {
            for (int layer1neurons = hiddenLayer1.getMinNeurons(); layer1neurons <= hiddenLayer1.getMaxNeurons(); layer1neurons++) {
                for (Activation layer1Activation : hiddenLayer1.getActivationList()) {
                    for (int layer2neurons = hiddenLayer2.getMinNeurons(); layer2neurons <= hiddenLayer2.getMaxNeurons(); layer2neurons++) {
                        for (Activation layer2Activation : hiddenLayer2.getActivationList()) {
                            for (int layer3neurons = hiddenLayer3.getMinNeurons(); layer3neurons <= hiddenLayer3.getMaxNeurons(); layer3neurons++) {
                                for (Activation layer3Activation : hiddenLayer3.getActivationList()) {
                                    for (int layer4neurons = hiddenLayer4.getMinNeurons(); layer4neurons <= hiddenLayer4.getMaxNeurons(); layer4neurons++) {
                                        for (Activation layer4Activation : hiddenLayer4.getActivationList()) {
                                            for (Activation outputActivation : activationOutputList) {
                                                totalIterations++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        double currentIteration = 0;
        double onePercentIterations = totalIterations / 100;
        double iterationNotification = onePercentIterations;

        System.out.println(YELLOW + "Start time: " + new Date() + RESET);
        System.out.println(YELLOW + "Iterations: " + totalIterations + RESET);

        for (Activation inputActivation : activationInputList) {

            // Hidden layers section
            for (int layer1neurons = hiddenLayer1.getMinNeurons(); layer1neurons <= hiddenLayer1.getMaxNeurons(); layer1neurons++) {
                for (Activation layer1Activation : hiddenLayer1.getActivationList()) {

                    for (int layer2neurons = hiddenLayer2.getMinNeurons(); layer2neurons <= hiddenLayer2.getMaxNeurons(); layer2neurons++) {
                        for (Activation layer2Activation : hiddenLayer2.getActivationList()) {

                            for (int layer3neurons = hiddenLayer3.getMinNeurons(); layer3neurons <= hiddenLayer3.getMaxNeurons(); layer3neurons++) {
                                for (Activation layer3Activation : hiddenLayer3.getActivationList()) {

                                    for (int layer4neurons = hiddenLayer4.getMinNeurons(); layer4neurons <= hiddenLayer4.getMaxNeurons(); layer4neurons++) {
                                        for (Activation layer4Activation : hiddenLayer4.getActivationList()) {

                                            for (Activation outputActivation : activationOutputList) {

                                                currentIteration = currentIteration + 1;

                                                DeepSearchResult deepSearchResult = train(
                                                        fileName,
                                                        inputNeurons,
                                                        inputActivation,
                                                        outputNeurons,
                                                        outputActivation,
                                                        layer1neurons,
                                                        layer1Activation,
                                                        layer2neurons,
                                                        layer2Activation,
                                                        layer3neurons,
                                                        layer3Activation,
                                                        layer4neurons,
                                                        layer4Activation
                                                );

                                                if (currentIteration >= iterationNotification) {
                                                    System.out.println("Completed " + currentIteration + " of " + totalIterations);
                                                    iterationNotification = iterationNotification + onePercentIterations;
                                                }

                                                String currentConfiguration = DATE_FORMAT.format(new Date())
                                                        + " [" + inputNeurons + "-" + inputActivation + "]"
                                                        + " (" + layer1neurons + "-" + layer1Activation + ")"
                                                        + " (" + layer2neurons + "-" + layer2Activation + ")"
                                                        + " (" + layer3neurons + "-" + layer3Activation + ")"
                                                        + " (" + layer4neurons + "-" + layer4Activation + ")"
                                                        + " [" + outputNeurons + "-" + outputActivation + "]"
                                                        + ": accuracy=" + deepSearchResult.getEvaluation().accuracy();

                                                if (deepSearchResult.getEvaluation().accuracy() > bestAccuracy) {
                                                    bestAccuracy = deepSearchResult.getEvaluation().accuracy();
                                                    bestConfiguration = currentConfiguration;

                                                    String saveFileName = "C:\\Data\\tmp\\" + inputNeurons + inputActivation
                                                            + "_" + layer1neurons + layer1Activation
                                                            + "_" + layer2neurons + layer2Activation
                                                            + "_" + layer3neurons + layer3Activation
                                                            + "_" + layer4neurons + layer4Activation
                                                            + "_" + outputNeurons + outputActivation;
                                                    deepSearchResult.getModel().save(new File(saveFileName));

                                                    System.out.println(YELLOW + bestConfiguration + RESET);
                                                    System.out.println(YELLOW + "Saved at " + saveFileName + RESET);
                                                    System.out.println(GREEN + deepSearchResult.getEvaluation().stats() + RESET);
                                                    System.out.println();
                                                }
                                            }
                                        }
                                    }
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

    // 5 hidden layers
    public void searchStructure(String fileName,
                                int inputNeurons, List<Activation> activationInputList,
                                int outputNeurons, List<Activation> activationOutputList,
                                LayerSettings hiddenLayer1,
                                LayerSettings hiddenLayer2,
                                LayerSettings hiddenLayer3,
                                LayerSettings hiddenLayer4,
                                LayerSettings hiddenLayer5
    ) throws IOException, InterruptedException {

        double bestAccuracy = 0;
        String bestConfiguration = null;

        double totalIterations = 0;
        for (Activation inputActivation : activationInputList) {
            for (int layer1neurons = hiddenLayer1.getMinNeurons(); layer1neurons <= hiddenLayer1.getMaxNeurons(); layer1neurons++) {
                for (Activation layer1Activation : hiddenLayer1.getActivationList()) {
                    for (int layer2neurons = hiddenLayer2.getMinNeurons(); layer2neurons <= hiddenLayer2.getMaxNeurons(); layer2neurons++) {
                        for (Activation layer2Activation : hiddenLayer2.getActivationList()) {
                            for (int layer3neurons = hiddenLayer3.getMinNeurons(); layer3neurons <= hiddenLayer3.getMaxNeurons(); layer3neurons++) {
                                for (Activation layer3Activation : hiddenLayer3.getActivationList()) {
                                    for (int layer4neurons = hiddenLayer4.getMinNeurons(); layer4neurons <= hiddenLayer4.getMaxNeurons(); layer4neurons++) {
                                        for (Activation layer4Activation : hiddenLayer4.getActivationList()) {
                                            for (int layer5neurons = hiddenLayer5.getMinNeurons(); layer5neurons <= hiddenLayer5.getMaxNeurons(); layer5neurons++) {
                                                for (Activation layer5Activation : hiddenLayer5.getActivationList()) {
                                                    for (Activation outputActivation : activationOutputList) {
                                                        totalIterations++;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        double currentIteration = 0;
        double onePercentIterations = totalIterations / 100;
        double iterationNotification = onePercentIterations;

        System.out.println(YELLOW + "Start time: " + new Date() + RESET);
        System.out.println(YELLOW + "Iterations: " + totalIterations + RESET);

        for (Activation inputActivation : activationInputList) {

            // Hidden layers section
            for (int layer1neurons = hiddenLayer1.getMinNeurons(); layer1neurons <= hiddenLayer1.getMaxNeurons(); layer1neurons++) {
                for (Activation layer1Activation : hiddenLayer1.getActivationList()) {

                    for (int layer2neurons = hiddenLayer2.getMinNeurons(); layer2neurons <= hiddenLayer2.getMaxNeurons(); layer2neurons++) {
                        for (Activation layer2Activation : hiddenLayer2.getActivationList()) {

                            for (int layer3neurons = hiddenLayer3.getMinNeurons(); layer3neurons <= hiddenLayer3.getMaxNeurons(); layer3neurons++) {
                                for (Activation layer3Activation : hiddenLayer3.getActivationList()) {

                                    for (int layer4neurons = hiddenLayer4.getMinNeurons(); layer4neurons <= hiddenLayer4.getMaxNeurons(); layer4neurons++) {
                                        for (Activation layer4Activation : hiddenLayer4.getActivationList()) {

                                            for (int layer5neurons = hiddenLayer5.getMinNeurons(); layer5neurons <= hiddenLayer5.getMaxNeurons(); layer5neurons++) {
                                                for (Activation layer5Activation : hiddenLayer5.getActivationList()) {

                                                    for (Activation outputActivation : activationOutputList) {

                                                        currentIteration = currentIteration + 1;

                                                        DeepSearchResult deepSearchResult = train(
                                                                fileName,
                                                                inputNeurons,
                                                                inputActivation,
                                                                outputNeurons,
                                                                outputActivation,
                                                                layer1neurons,
                                                                layer1Activation,
                                                                layer2neurons,
                                                                layer2Activation,
                                                                layer3neurons,
                                                                layer3Activation,
                                                                layer4neurons,
                                                                layer4Activation,
                                                                layer5neurons,
                                                                layer5Activation
                                                        );

                                                        if (currentIteration >= iterationNotification) {
                                                            System.out.println("Completed " + currentIteration + " of " + totalIterations);
                                                            iterationNotification = iterationNotification + onePercentIterations;
                                                        }

                                                        String currentConfiguration = DATE_FORMAT.format(new Date())
                                                                + " [" + inputNeurons + "-" + inputActivation + "]"
                                                                + " (" + layer1neurons + "-" + layer1Activation + ")"
                                                                + " (" + layer2neurons + "-" + layer2Activation + ")"
                                                                + " (" + layer3neurons + "-" + layer3Activation + ")"
                                                                + " (" + layer4neurons + "-" + layer4Activation + ")"
                                                                + " (" + layer5neurons + "-" + layer5Activation + ")"
                                                                + " [" + outputNeurons + "-" + outputActivation + "]"
                                                                + ": accuracy=" + deepSearchResult.getEvaluation().accuracy();

                                                        if (deepSearchResult.getEvaluation().accuracy() > bestAccuracy) {
                                                            bestAccuracy = deepSearchResult.getEvaluation().accuracy();
                                                            bestConfiguration = currentConfiguration;

                                                            String saveFileName = "C:\\Data\\tmp\\" + inputNeurons + inputActivation
                                                                    + "_" + layer1neurons + layer1Activation
                                                                    + "_" + layer2neurons + layer2Activation
                                                                    + "_" + layer3neurons + layer3Activation
                                                                    + "_" + layer4neurons + layer4Activation
                                                                    + "_" + layer5neurons + layer5Activation
                                                                    + "_" + outputNeurons + outputActivation;
                                                            deepSearchResult.getModel().save(new File(saveFileName));

                                                            System.out.println(YELLOW + bestConfiguration + RESET);
                                                            System.out.println(YELLOW + "Saved at " + saveFileName + RESET);
                                                            System.out.println(GREEN + deepSearchResult.getEvaluation().stats() + RESET);
                                                            System.out.println();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
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

    // 6 hidden layers
    public void searchStructure(String fileName,
                                int inputNeurons, List<Activation> activationInputList,
                                int outputNeurons, List<Activation> activationOutputList,
                                LayerSettings hiddenLayer1,
                                LayerSettings hiddenLayer2,
                                LayerSettings hiddenLayer3,
                                LayerSettings hiddenLayer4,
                                LayerSettings hiddenLayer5,
                                LayerSettings hiddenLayer6
    ) throws IOException, InterruptedException {

        double bestAccuracy = 0;
        String bestConfiguration = null;

        double totalIterations = 0;
        for (Activation inputActivation : activationInputList) {
            for (int layer1neurons = hiddenLayer1.getMinNeurons(); layer1neurons <= hiddenLayer1.getMaxNeurons(); layer1neurons++) {
                for (Activation layer1Activation : hiddenLayer1.getActivationList()) {
                    for (int layer2neurons = hiddenLayer2.getMinNeurons(); layer2neurons <= hiddenLayer2.getMaxNeurons(); layer2neurons++) {
                        for (Activation layer2Activation : hiddenLayer2.getActivationList()) {
                            for (int layer3neurons = hiddenLayer3.getMinNeurons(); layer3neurons <= hiddenLayer3.getMaxNeurons(); layer3neurons++) {
                                for (Activation layer3Activation : hiddenLayer3.getActivationList()) {
                                    for (int layer4neurons = hiddenLayer4.getMinNeurons(); layer4neurons <= hiddenLayer4.getMaxNeurons(); layer4neurons++) {
                                        for (Activation layer4Activation : hiddenLayer4.getActivationList()) {
                                            for (int layer5neurons = hiddenLayer5.getMinNeurons(); layer5neurons <= hiddenLayer5.getMaxNeurons(); layer5neurons++) {
                                                for (Activation layer5Activation : hiddenLayer5.getActivationList()) {
                                                    for (int layer6neurons = hiddenLayer6.getMinNeurons(); layer6neurons <= hiddenLayer6.getMaxNeurons(); layer6neurons++) {
                                                        for (Activation layer6Activation : hiddenLayer6.getActivationList()) {
                                                            for (Activation outputActivation : activationOutputList) {
                                                                totalIterations++;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        double currentIteration = 0;
        double onePercentIterations = totalIterations / 100;
        double iterationNotification = onePercentIterations;

        System.out.println(YELLOW + "Start time: " + new Date() + RESET);
        System.out.println(YELLOW + "Iterations: " + totalIterations + RESET);

        for (Activation inputActivation : activationInputList) {

            // Hidden layers section
            for (int layer1neurons = hiddenLayer1.getMinNeurons(); layer1neurons <= hiddenLayer1.getMaxNeurons(); layer1neurons++) {
                for (Activation layer1Activation : hiddenLayer1.getActivationList()) {

                    for (int layer2neurons = hiddenLayer2.getMinNeurons(); layer2neurons <= hiddenLayer2.getMaxNeurons(); layer2neurons++) {
                        for (Activation layer2Activation : hiddenLayer2.getActivationList()) {

                            for (int layer3neurons = hiddenLayer3.getMinNeurons(); layer3neurons <= hiddenLayer3.getMaxNeurons(); layer3neurons++) {
                                for (Activation layer3Activation : hiddenLayer3.getActivationList()) {

                                    for (int layer4neurons = hiddenLayer4.getMinNeurons(); layer4neurons <= hiddenLayer4.getMaxNeurons(); layer4neurons++) {
                                        for (Activation layer4Activation : hiddenLayer4.getActivationList()) {

                                            for (int layer5neurons = hiddenLayer5.getMinNeurons(); layer5neurons <= hiddenLayer5.getMaxNeurons(); layer5neurons++) {
                                                for (Activation layer5Activation : hiddenLayer5.getActivationList()) {

                                                    for (int layer6neurons = hiddenLayer6.getMinNeurons(); layer6neurons <= hiddenLayer6.getMaxNeurons(); layer6neurons++) {
                                                        for (Activation layer6Activation : hiddenLayer6.getActivationList()) {

                                                            for (Activation outputActivation : activationOutputList) {

                                                                currentIteration = currentIteration + 1;

                                                                DeepSearchResult deepSearchResult = train(
                                                                        fileName,
                                                                        inputNeurons,
                                                                        inputActivation,
                                                                        outputNeurons,
                                                                        outputActivation,
                                                                        layer1neurons,
                                                                        layer1Activation,
                                                                        layer2neurons,
                                                                        layer2Activation,
                                                                        layer3neurons,
                                                                        layer3Activation,
                                                                        layer4neurons,
                                                                        layer4Activation,
                                                                        layer5neurons,
                                                                        layer5Activation,
                                                                        layer6neurons,
                                                                        layer6Activation
                                                                );

                                                                if (currentIteration >= iterationNotification) {
                                                                    System.out.println("Completed " + currentIteration + " of " + totalIterations);
                                                                    iterationNotification = iterationNotification + onePercentIterations;
                                                                }

                                                                String currentConfiguration = DATE_FORMAT.format(new Date())
                                                                        + " [" + inputNeurons + "-" + inputActivation + "]"
                                                                        + " (" + layer1neurons + "-" + layer1Activation + ")"
                                                                        + " (" + layer2neurons + "-" + layer2Activation + ")"
                                                                        + " (" + layer3neurons + "-" + layer3Activation + ")"
                                                                        + " (" + layer4neurons + "-" + layer4Activation + ")"
                                                                        + " (" + layer5neurons + "-" + layer5Activation + ")"
                                                                        + " (" + layer6neurons + "-" + layer6Activation + ")"
                                                                        + " [" + outputNeurons + "-" + outputActivation + "]"
                                                                        + ": accuracy=" + deepSearchResult.getEvaluation().accuracy();

                                                                if (deepSearchResult.getEvaluation().accuracy() > bestAccuracy) {
                                                                    bestAccuracy = deepSearchResult.getEvaluation().accuracy();
                                                                    bestConfiguration = currentConfiguration;

                                                                    String saveFileName = "C:\\Data\\tmp\\" + inputNeurons + inputActivation
                                                                            + "_" + layer1neurons + layer1Activation
                                                                            + "_" + layer2neurons + layer2Activation
                                                                            + "_" + layer3neurons + layer3Activation
                                                                            + "_" + layer4neurons + layer4Activation
                                                                            + "_" + layer5neurons + layer5Activation
                                                                            + "_" + layer6neurons + layer6Activation
                                                                            + "_" + outputNeurons + outputActivation;
                                                                    deepSearchResult.getModel().save(new File(saveFileName));

                                                                    System.out.println(YELLOW + bestConfiguration + RESET);
                                                                    System.out.println(YELLOW + "Saved at " + saveFileName + RESET);
                                                                    System.out.println(GREEN + deepSearchResult.getEvaluation().stats() + RESET);
                                                                    System.out.println();
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
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

    // 7 hidden layers
    public void searchStructure(String fileName,
                                int inputNeurons, List<Activation> activationInputList,
                                int outputNeurons, List<Activation> activationOutputList,
                                LayerSettings hiddenLayer1,
                                LayerSettings hiddenLayer2,
                                LayerSettings hiddenLayer3,
                                LayerSettings hiddenLayer4,
                                LayerSettings hiddenLayer5,
                                LayerSettings hiddenLayer6,
                                LayerSettings hiddenLayer7
    ) throws IOException, InterruptedException {

        double bestAccuracy = 0;
        String bestConfiguration = null;

        double totalIterations = 0;
        for (Activation inputActivation : activationInputList) {
            for (int layer1neurons = hiddenLayer1.getMinNeurons(); layer1neurons <= hiddenLayer1.getMaxNeurons(); layer1neurons++) {
                for (Activation layer1Activation : hiddenLayer1.getActivationList()) {
                    for (int layer2neurons = hiddenLayer2.getMinNeurons(); layer2neurons <= hiddenLayer2.getMaxNeurons(); layer2neurons++) {
                        for (Activation layer2Activation : hiddenLayer2.getActivationList()) {
                            for (int layer3neurons = hiddenLayer3.getMinNeurons(); layer3neurons <= hiddenLayer3.getMaxNeurons(); layer3neurons++) {
                                for (Activation layer3Activation : hiddenLayer3.getActivationList()) {
                                    for (int layer4neurons = hiddenLayer4.getMinNeurons(); layer4neurons <= hiddenLayer4.getMaxNeurons(); layer4neurons++) {
                                        for (Activation layer4Activation : hiddenLayer4.getActivationList()) {
                                            for (int layer5neurons = hiddenLayer5.getMinNeurons(); layer5neurons <= hiddenLayer5.getMaxNeurons(); layer5neurons++) {
                                                for (Activation layer5Activation : hiddenLayer5.getActivationList()) {
                                                    for (int layer6neurons = hiddenLayer6.getMinNeurons(); layer6neurons <= hiddenLayer6.getMaxNeurons(); layer6neurons++) {
                                                        for (Activation layer6Activation : hiddenLayer6.getActivationList()) {
                                                            for (int layer7neurons = hiddenLayer7.getMinNeurons(); layer7neurons <= hiddenLayer7.getMaxNeurons(); layer7neurons++) {
                                                                for (Activation layer7Activation : hiddenLayer7.getActivationList()) {
                                                                    for (Activation outputActivation : activationOutputList) {
                                                                        totalIterations++;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        double currentIteration = 0;
        double onePercentIterations = totalIterations / 100;
        double iterationNotification = onePercentIterations;

        System.out.println(YELLOW + "Start time: " + new Date() + RESET);
        System.out.println(YELLOW + "Iterations: " + totalIterations + RESET);

        for (Activation inputActivation : activationInputList) {

            // Hidden layers section
            for (int layer1neurons = hiddenLayer1.getMinNeurons(); layer1neurons <= hiddenLayer1.getMaxNeurons(); layer1neurons++) {
                for (Activation layer1Activation : hiddenLayer1.getActivationList()) {

                    for (int layer2neurons = hiddenLayer2.getMinNeurons(); layer2neurons <= hiddenLayer2.getMaxNeurons(); layer2neurons++) {
                        for (Activation layer2Activation : hiddenLayer2.getActivationList()) {

                            for (int layer3neurons = hiddenLayer3.getMinNeurons(); layer3neurons <= hiddenLayer3.getMaxNeurons(); layer3neurons++) {
                                for (Activation layer3Activation : hiddenLayer3.getActivationList()) {

                                    for (int layer4neurons = hiddenLayer4.getMinNeurons(); layer4neurons <= hiddenLayer4.getMaxNeurons(); layer4neurons++) {
                                        for (Activation layer4Activation : hiddenLayer4.getActivationList()) {

                                            for (int layer5neurons = hiddenLayer5.getMinNeurons(); layer5neurons <= hiddenLayer5.getMaxNeurons(); layer5neurons++) {
                                                for (Activation layer5Activation : hiddenLayer5.getActivationList()) {

                                                    for (int layer6neurons = hiddenLayer6.getMinNeurons(); layer6neurons <= hiddenLayer6.getMaxNeurons(); layer6neurons++) {
                                                        for (Activation layer6Activation : hiddenLayer6.getActivationList()) {

                                                            for (int layer7neurons = hiddenLayer7.getMinNeurons(); layer7neurons <= hiddenLayer7.getMaxNeurons(); layer7neurons++) {
                                                                for (Activation layer7Activation : hiddenLayer7.getActivationList()) {

                                                                    for (Activation outputActivation : activationOutputList) {

                                                                        currentIteration = currentIteration + 1;

                                                                        DeepSearchResult deepSearchResult = train(
                                                                                fileName,
                                                                                inputNeurons,
                                                                                inputActivation,
                                                                                outputNeurons,
                                                                                outputActivation,
                                                                                layer1neurons,
                                                                                layer1Activation,
                                                                                layer2neurons,
                                                                                layer2Activation,
                                                                                layer3neurons,
                                                                                layer3Activation,
                                                                                layer4neurons,
                                                                                layer4Activation,
                                                                                layer5neurons,
                                                                                layer5Activation,
                                                                                layer6neurons,
                                                                                layer6Activation,
                                                                                layer7neurons,
                                                                                layer7Activation
                                                                        );

                                                                        if (currentIteration >= iterationNotification) {
                                                                            System.out.println("Completed " + currentIteration + " of " + totalIterations);
                                                                            iterationNotification = iterationNotification + onePercentIterations;
                                                                        }

                                                                        String currentConfiguration = DATE_FORMAT.format(new Date())
                                                                                + " [" + inputNeurons + "-" + inputActivation + "]"
                                                                                + " (" + layer1neurons + "-" + layer1Activation + ")"
                                                                                + " (" + layer2neurons + "-" + layer2Activation + ")"
                                                                                + " (" + layer3neurons + "-" + layer3Activation + ")"
                                                                                + " (" + layer4neurons + "-" + layer4Activation + ")"
                                                                                + " (" + layer5neurons + "-" + layer5Activation + ")"
                                                                                + " (" + layer6neurons + "-" + layer6Activation + ")"
                                                                                + " (" + layer7neurons + "-" + layer7Activation + ")"
                                                                                + " [" + outputNeurons + "-" + outputActivation + "]"
                                                                                + ": accuracy=" + deepSearchResult.getEvaluation().accuracy();

                                                                        if (deepSearchResult.getEvaluation().accuracy() > bestAccuracy) {
                                                                            bestAccuracy = deepSearchResult.getEvaluation().accuracy();
                                                                            bestConfiguration = currentConfiguration;

                                                                            String saveFileName = "C:\\Data\\tmp\\" + inputNeurons + inputActivation
                                                                                    + "_" + layer1neurons + layer1Activation
                                                                                    + "_" + layer2neurons + layer2Activation
                                                                                    + "_" + layer3neurons + layer3Activation
                                                                                    + "_" + layer4neurons + layer4Activation
                                                                                    + "_" + layer5neurons + layer5Activation
                                                                                    + "_" + layer6neurons + layer6Activation
                                                                                    + "_" + layer7neurons + layer7Activation
                                                                                    + "_" + outputNeurons + outputActivation;
                                                                            deepSearchResult.getModel().save(new File(saveFileName));

                                                                            System.out.println(YELLOW + bestConfiguration + RESET);
                                                                            System.out.println(YELLOW + "Saved at " + saveFileName + RESET);
                                                                            System.out.println(GREEN + deepSearchResult.getEvaluation().stats() + RESET);
                                                                            System.out.println();
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
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
