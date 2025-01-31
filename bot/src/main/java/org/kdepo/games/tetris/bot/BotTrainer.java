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
import java.util.Date;

public class BotTrainer {

    private static final int CLASSES_COUNT = 4;
    private static final int FEATURES_COUNT = 12;

    private static void trainFigureOrientation(int layer2neurons) throws IOException, InterruptedException {
        DataSet allData;
        try (RecordReader recordReader = new CSVRecordReader(0, ',')) {
            recordReader.initialize(new FileSplit(new ClassPathResource("orientation_data.csv").getFile()));

            DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, 50, FEATURES_COUNT, CLASSES_COUNT);
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
                .activation(Activation.TANH)
                .weightInit(WeightInit.XAVIER)
                .regularization(true)
                .learningRate(0.1).l2(0.0001)
                .list()
                .layer(0,
                        new DenseLayer
                                .Builder()
                                .nIn(FEATURES_COUNT)
                                .nOut(layer2neurons)
                                .build()
                )
                .layer(1,
                        new DenseLayer
                                .Builder()
                                .nIn(layer2neurons)
                                .nOut(4)
                                .build()
                )
//                .layer(2,
//                        new DenseLayer
//                                .Builder()
//                                .nIn(8)
//                                .nOut(4)
//                                .build()
//                )
                .layer(2,
                        new OutputLayer
                                .Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                                .activation(Activation.SOFTMAX)
                                .nIn(4)
                                .nOut(CLASSES_COUNT)
                                .build()
                )
                .backpropType(BackpropType.Standard).pretrain(false)
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(configuration);
        model.init();
        model.fit(trainingData);

        INDArray output = model.output(testData.getFeatures());

        Evaluation eval = new Evaluation(CLASSES_COUNT);
        eval.eval(testData.getLabels(), output);
//        System.out.println(eval.stats());
        System.out.println("layer2neurons=" + layer2neurons + ", accuracy=" + eval.accuracy());
    }

    private static void trainFigureOrientation2(int layer2neurons, int layer3neurons) throws IOException, InterruptedException {
        DataSet allData;
        try (RecordReader recordReader = new CSVRecordReader(0, ',')) {
            recordReader.initialize(new FileSplit(new ClassPathResource("orientation_data.csv").getFile()));

            DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, 50, FEATURES_COUNT, CLASSES_COUNT);
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
                .activation(Activation.TANH)
                .weightInit(WeightInit.XAVIER)
                .regularization(true)
                .learningRate(0.1).l2(0.0001)
                .list()
                .layer(0,
                        new DenseLayer
                                .Builder()
                                .nIn(FEATURES_COUNT)
                                .nOut(layer2neurons)
                                .build()
                )
                .layer(1,
                        new DenseLayer
                                .Builder()
                                .activation(Activation.RELU)
                                .nIn(layer2neurons)
                                .nOut(layer3neurons)
                                .build()
                )
                .layer(2,
                        new DenseLayer
                                .Builder()
                                .activation(Activation.RELU)
                                .nIn(layer3neurons)
                                .nOut(4)
                                .build()
                )
                .layer(3,
                        new OutputLayer
                                .Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                                .activation(Activation.SOFTMAX)
                                .nIn(4)
                                .nOut(CLASSES_COUNT)
                                .build()
                )
                .backpropType(BackpropType.Standard)
                .pretrain(false)
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(configuration);
        model.init();
        model.fit(trainingData);

        INDArray output = model.output(testData.getFeatures());

        Evaluation eval = new Evaluation(CLASSES_COUNT);
        eval.eval(testData.getLabels(), output);
//        System.out.println(eval.stats());
        System.out.println("layer2neurons=" + layer2neurons + ", layer3neurons=" + layer3neurons + ", accuracy=" + eval.accuracy());
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Start time: "+new Date());

//        for (int layer2neurons = 1; layer2neurons <= 24; layer2neurons++) {
//            trainFigureOrientation(layer2neurons);
//        }

        for (int layer2neurons = 1; layer2neurons <= 24; layer2neurons++) {
            for (int layer3neurons = 1; layer3neurons <= 24; layer3neurons++) {
                trainFigureOrientation2(layer2neurons, layer3neurons);
            }
        }

        System.out.println("Start time: "+new Date());
    }
}