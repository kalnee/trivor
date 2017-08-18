/* Copyright (c) 2016 Kalnee
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.kalnee.trivor.nlp.nlp.training;

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
    private static final String MODEL_OUTPUT = "trivor-nlp/src/main/resources/nlp/models/en-sentiment.bin";

    public void train() {
        try {
            InputStreamFactory modelStream = new MarkableFileInputStreamFactory(
                    new File(getClass().getClassLoader().getResource(TRAIN_INPUT).getFile())
            );
            final ObjectStream<String> lineStream = new PlainTextByLineStream(modelStream, "UTF-8");
            final ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

            final TrainingParameters mlParams = new TrainingParameters();
            mlParams.put(TrainingParameters.ITERATIONS_PARAM, 5000);
            mlParams.put(TrainingParameters.CUTOFF_PARAM, 5);

            final DoccatModel model = DocumentCategorizerME.train("en", sampleStream, mlParams, new DoccatFactory());
            final Path path = Paths.get(MODEL_OUTPUT);
            FileUtils.touch(path.toFile());
            try (OutputStream modelOut = new BufferedOutputStream(new FileOutputStream(path.toString()))) {
                model.serialize(modelOut);
            }
        } catch (Exception e) {
            LOGGER.error("an error occurred while training the sentiment analysis model", e);
            throw new IllegalStateException(e);
        }
    }

    public static void main(String[] args) {
        new SentimentTraining().train();
    }
}
