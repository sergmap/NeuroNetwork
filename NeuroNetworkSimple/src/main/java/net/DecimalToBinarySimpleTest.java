package net;

import org.jblas.DoubleMatrix;

import java.util.Arrays;

public class DecimalToBinarySimpleTest {

    private DoubleMatrix[] weights;
    private DoubleMatrix[] biases;

    private int numLayers;
    private int[] sizes;

    public DecimalToBinarySimpleTest(int... sizes) {
        this.sizes = sizes;
        this.numLayers = sizes.length;

        this.biases = new DoubleMatrix[sizes.length - 1];
        this.weights = new DoubleMatrix[sizes.length - 1];

        // Storing biases
        for(int i = 1; i < sizes.length; i++) {
            double[][] temp = new double[sizes[i]][];
            for (int j = 0; j < sizes[i]; j++) {
                double[] b = new double[] { -1 }; // Set constant value
                temp[j] = b;
            }
            biases[i-1] = new DoubleMatrix(temp);
        }

        // Storing weights
//        for (int i = 1; i < sizes.length; i++) {
//            double[][] temp = new double[sizes[i]][];
//            for (int j = 0; j < sizes[i]; j++) {
//                double[] w = new double[sizes[i-1]];
//                for (int k = 0; k < sizes[i - 1]; k++) {
//                    w[k] = 0; // Set constant value
//                }
//                temp[j] = w;
//            }
//            weights[i - 1] = new DoubleMatrix(temp);
//        }

        final double WEIGHT = 2;
//        double[][] customWeights = new double[][] {
//                {0, WEIGHT, 0, WEIGHT, 0, WEIGHT, 0, WEIGHT, 0, WEIGHT},
//                {0, 0, WEIGHT, WEIGHT, 0, 0, WEIGHT, WEIGHT, 0, 0},
//                {0, 0, 0, 0, WEIGHT, WEIGHT, WEIGHT, WEIGHT, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, WEIGHT, WEIGHT}
//        } ;
        double[][] customWeights = new double[][] {
                {0, 0, 0, 0, 0, 0, 0, 0, WEIGHT, WEIGHT},
                {0, 0, 0, 0, WEIGHT, WEIGHT, WEIGHT, WEIGHT, 0, 0},
                {0, 0, WEIGHT, WEIGHT, 0, 0, WEIGHT, WEIGHT, 0, 0},
                {0, WEIGHT, 0, WEIGHT, 0, WEIGHT, 0, WEIGHT, 0, WEIGHT}

        } ;
        weights[0] = new DoubleMatrix(customWeights);
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

    private DoubleMatrix sigmoid(DoubleMatrix z) {
        double[] output = new double[z.length];
        for (int i = 0; i < output.length; i++) {
            output[i] = 1 / (1 + Math.exp(-z.get(i)));
        }
        return new DoubleMatrix(output);
    }

    @Override
    public String toString() {
        return "net.PerceptronNetwork{" + "\n" +
//                "                  " + "BIAS=" + BIAS + "\n" +
                "                  " + "sizes=" + Arrays.toString(sizes) + "\n" +
                "                  " + "numLayers=" + numLayers + "\n" +
                "                  " + '}';
    }


}
