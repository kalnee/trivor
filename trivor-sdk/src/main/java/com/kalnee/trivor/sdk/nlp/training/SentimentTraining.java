package com.kalnee.trivor.sdk.nlp.training;

import opennlp.tools.doccat.*;
import opennlp.tools.util.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SentimentTraining {

    private static final Logger LOGGER = LoggerFactory.getLogger(SentimentTraining.class);
    private static final String TRAIN_INPUT = "nlp/training/en-sentiment.train";
    private static final String MODEL_OUTPUT = "trivor-sdk/src/main/resources/nlp/models/en-sentiment.bin";

    public DoccatModel train() {
        try {
            InputStreamFactory modelStream = new MarkableFileInputStreamFactory(
                    new File(getClass().getClassLoader().getResource(TRAIN_INPUT).getFile())
            );
            final ObjectStream<String> lineStream = new PlainTextByLineStream(modelStream, "UTF-8");
            final ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

            final TrainingParameters mlParams = new TrainingParameters();
            mlParams.put(TrainingParameters.ITERATIONS_PARAM, 5000);
            mlParams.put(TrainingParameters.CUTOFF_PARAM, 2);

            final DoccatModel model = DocumentCategorizerME.train("en", sampleStream, mlParams, new DoccatFactory());
            final Path path = Paths.get(MODEL_OUTPUT);
            FileUtils.touch(path.toFile());
            try (OutputStream modelOut = new BufferedOutputStream(new FileOutputStream(path.toString()))) {
                model.serialize(modelOut);
            }

            return model;
        } catch (Exception e) {
            LOGGER.error("an error occurred while training the sentiment analysis model", e);
            throw new IllegalStateException(e);
        }
    }

    public static void main(String[] args) {
        DoccatModel m = new SentimentTraining().train();
        DocumentCategorizerME myCategorizer = new DocumentCategorizerME(m);

        test(myCategorizer, "i 'm glad you came");
        test(myCategorizer, "that 's bad");
        test(myCategorizer, "this is my new idea");

        test(myCategorizer, "i love you so much that i can 't even wait until tomorrow !");
        test(myCategorizer, "that 's the worst thing you 've ever done to me");
        test(myCategorizer, "my new idea took me a while to come up");

        test(myCategorizer, "i love you and hate you at the same time");
    }

    private static void test(DocumentCategorizerME myCategorizer, String sentence) {
        double[] outcomes = myCategorizer.categorize(sentence.split(" "));
        String category = myCategorizer.getBestCategory(outcomes);
        String results = myCategorizer.getAllResults(outcomes);

        System.out.println("\nSentence: " + sentence);
        System.out.println("Category: " + category + " (" + outcomes[myCategorizer.getIndex(category)] + ")");
        System.out.println("All: " + results);
    }
}
