package net;

import org.jblas.DoubleMatrix;
import org.jblas.util.Random;


import java.util.*;

public class SigmoidNetworkExt {


    public DoubleMatrix[] weights;
    public DoubleMatrix[] biases;

    private int numLayers;
    private int[] sizes;

    public SigmoidNetworkExt(int... sizes) {
        this.sizes = sizes;
        this.numLayers = sizes.length;

        this.biases = new DoubleMatrix[sizes.length - 1];
        this.weights = new DoubleMatrix[sizes.length - 1];

        // Storing biases
        for(int i = 1; i < sizes.length; i++) {
            double[][] temp = new double[sizes[i]][];
            for (int j = 0; j < sizes[i]; j++) {
                //double[] b = new double[] { 1 }; // Set constant value
                double[] b = new double[] {Random.nextGaussian()};
                temp[j] = b;
            }
            biases[i-1] = new DoubleMatrix(temp);
        }

        // Storing weights
        for (int i = 1; i < sizes.length; i++) {
            double[][] temp = new double[sizes[i]][];
            for (int j = 0; j < sizes[i]; j++) {
                double[] w = new double[sizes[i-1]];
                for (int k = 0; k < sizes[i - 1]; k++) {
                    //w[k] = 1; // Set constant value
                    w[k] = Random.nextGaussian();
                }
                temp[j] = w;
            }
            weights[i - 1] = new DoubleMatrix(temp);
        }

//        final double WEIGHT = 2;
//        double[][] customWeights = new double[][] {
//                {0, 0, 0, 0, 0, 0, 0, 0, WEIGHT, WEIGHT},
//                {0, 0, 0, 0, WEIGHT, WEIGHT, WEIGHT, WEIGHT, 0, 0},
//                {0, 0, WEIGHT, WEIGHT, 0, 0, WEIGHT, WEIGHT, 0, 0},
//                {0, WEIGHT, 0, WEIGHT, 0, WEIGHT, 0, WEIGHT, 0, WEIGHT}
//
//        } ;
//        weights[0] = new DoubleMatrix(customWeights);
    }

    public DoubleMatrix feedForward(DoubleMatrix a) {
        for (int i = 0; i < numLayers - 1; i++) {
            double[] z = new double[weights[i].rows];
            for (int j = 0; j < weights[i].rows; j++) {
                z[j] = weights[i].getRow(j).dot(a) + biases[i].get(j);
            }
            DoubleMatrix output = new DoubleMatrix(z);
            a = sigmoid(output);
        }
        return a;
    }

    /**
     *
     * @param z - input vector created by finding dot product of weights and inputs
     *          and added a bias of a neuron
     * @return output vector - inputs for the next layer
     */
    private DoubleMatrix sigmoid(DoubleMatrix z) {
        double[] output = new double[z.length];
        for (int i = 0; i < output.length; i++) {
            output[i] = 1 / (1 + Math.exp(-z.get(i)));
        }
        return new DoubleMatrix(output);
    }

    private int evaluate(List<double[][]> testData) {
        int sum = 0;

        for (double[][] inputOutput : testData) {
            DoubleMatrix x = new DoubleMatrix(inputOutput[0]);
            DoubleMatrix y = new DoubleMatrix(inputOutput[1]);
            DoubleMatrix netOutput = feedForward(x);
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            for (double d : netOutput.toArray()) {
                sb.append(d >= 0.5 ? 1 : 0);
            }
            for (double d : y.toArray()) {
                sb2.append(d >= 0.5 ? 1 : 0);
            }
            //if (Integer.parseInt(sb.toString(), 2) == Integer.parseInt(sb2.toString(), 2))
            if (sb.toString().equals(sb2.toString()))
            {
                //System.out.println("sb.toString()=" + sb.toString());
                //System.out.println("sb2.toString()=" + sb2.toString());
                sum++;
            }
        }
        return sum;
    }

    public void SGD(List<double[][]> trainingData, int epochs, int miniBatchSize, double eta, List<double[][]> testData) {

        int nTest = 0;

        int n = trainingData.size();

        if (testData != null) {
            nTest = testData.size();
        }

        for (int j = 0; j < epochs; j++) {
            Collections.shuffle(trainingData);
            List<List<double[][]>> miniBatches = new ArrayList<>();
            for (int k = 0; k < n; k += miniBatchSize) {
                if (k + miniBatchSize < n)
                {
                    miniBatches.add(trainingData.subList(k, k + miniBatchSize));
                }
                else {miniBatches.add(trainingData.subList(k, k + (n - k)));}

            }
            for (List<double[][]> miniBatch : miniBatches) {
                updateMiniBatch(miniBatch, eta);
            }

            if (testData != null) {
                int e = evaluate(testData);
                System.out.println(String.format("Epoch %d: %d / %d", j, e, nTest));
            } else {
                System.out.println(String.format("Epoch %d complete", j));
            }
        }
    }

    private void updateMiniBatch(List<double[][]> miniBatch, double eta) {
        DoubleMatrix[] nablaB = new DoubleMatrix[biases.length];
        DoubleMatrix[] nablaW = new DoubleMatrix[weights.length];

        for (int i = 0; i < nablaB.length; i++) {
            nablaB[i] = new DoubleMatrix(biases[i].getRows(), biases[i].getColumns());
        }
        for (int i = 0; i < nablaW.length; i++) {
            nablaW[i] = new DoubleMatrix(weights[i].getRows(), weights[i].getColumns());
        }

        for (double[][] inputOutput : miniBatch) {
            DoubleMatrix[][] deltas = backProp(inputOutput);

            DoubleMatrix[] deltaNablaB = deltas[0];
            DoubleMatrix[] deltaNablaW = deltas[1];

            for (int i = 0; i < nablaB.length; i++) {
                nablaB[i] = nablaB[i].add(deltaNablaB[i]);
            }
            for (int i = 0; i < nablaW.length; i++) {
                //System.out.println("nablaW.length = " + nablaW[i].length);
                //System.out.println("deltaNablaW[i].length = " + deltaNablaW[i].length);
                nablaW[i] = nablaW[i].add(deltaNablaW[i]);
            }
        }
        for (int i = 0; i < biases.length; i++) {
            biases[i] = biases[i].sub(nablaB[i].mul(eta / miniBatch.size()));
        }
        for (int i = 0; i < weights.length; i++) {
            weights[i] = weights[i].sub(nablaW[i].mul(eta / miniBatch.size()));
        }
    }

    @Override
    public String toString() {
        return "net.PerceptronNetwork{" + "\n" +
//                "                  " + "BIAS=" + BIAS + "\n" +
                "                  " + "sizes=" + Arrays.toString(sizes) + "\n" +
                "                  " + "numLayers=" + numLayers + "\n" +
                "                  " + '}';
    }

    public DoubleMatrix[][] backProp(double[][] inputsOutputs) {
        DoubleMatrix[] nablaB = new DoubleMatrix[biases.length];
        DoubleMatrix[] nablaW = new DoubleMatrix[weights.length];

        for (int i = 0; i < nablaB.length; i++) {
            nablaB[i] = new DoubleMatrix(biases[i].getRows(), biases[i].getColumns());
        }
        for (int i = 0; i < nablaW.length; i++) {
            nablaW[i] = new DoubleMatrix(weights[i].getRows(), weights[i].getColumns());
        }

        // FeedForward
        DoubleMatrix activation = new DoubleMatrix(inputsOutputs[0]);
        DoubleMatrix[] activations = new DoubleMatrix[numLayers];
        activations[0] = activation;
        DoubleMatrix[] zs = new DoubleMatrix[numLayers - 1];

        for (int i = 0; i < numLayers - 1; i++) {
            double[] scalars = new double[weights[i].rows];
            for (int j = 0; j < weights[i].rows; j++) {
                scalars[j] = weights[i].getRow(j).dot(activation) + biases[i].get(j);
            }
            DoubleMatrix z = new DoubleMatrix(scalars);
            zs[i] = z;
            activation = sigmoid(z);
            activations[i + 1] = activation;
        }

        // Backward pass
        DoubleMatrix output = new DoubleMatrix(inputsOutputs[1]);
        DoubleMatrix delta = costDerivative(activations[activations.length - 1], output)
                .mul(sigmoidPrime(zs[zs.length - 1])); // BP1
        nablaB[nablaB.length - 1] = delta; // BP3
        nablaW[nablaW.length - 1] = delta.mmul(activations[activations.length - 2].transpose()); // BP4
        for (int layer = 2; layer < numLayers; layer++) {
            DoubleMatrix z = zs[zs.length - layer];
            DoubleMatrix sp = sigmoidPrime(z);
            delta = weights[weights.length + 1 - layer].transpose().mmul(delta).mul(sp); // BP2
            nablaB[nablaB.length - layer] = delta; // BP3
            nablaW[nablaW.length - layer] = delta.mmul(activations[activations.length - 1 - layer].transpose()); // BP4
        }
        return new DoubleMatrix[][] { nablaB, nablaW };
    }

    private DoubleMatrix sigmoidPrime(DoubleMatrix z) {
        return sigmoid(z).mul(sigmoid(z).rsub(1));
    }

    private DoubleMatrix costDerivative(DoubleMatrix outputActivations, DoubleMatrix output) {
        return outputActivations.sub(output);
    }

}
