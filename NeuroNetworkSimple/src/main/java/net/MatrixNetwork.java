package net;

import org.jblas.DoubleMatrix;

import java.util.Arrays;

public class MatrixNetwork {

    //private final double BIAS = 0;
    //private final double WEIGHTS = 1;

    private DoubleMatrix[] weights;
    private DoubleMatrix[] biases;

    private int numLayers;
    private int[] sizes;

    public MatrixNetwork(int... sizes) {
        this.sizes = sizes;
        this.numLayers = sizes.length;

        this.biases = new DoubleMatrix[sizes.length - 1];
        this.weights = new DoubleMatrix[sizes.length - 1];

        // Storing biases
        for(int i = 1; i < sizes.length; i++) {
            double[][] temp = new double[sizes[i]][];
            for (int j = 0; j < sizes[i]; j++) {
                double[] b = new double[] { 1 }; // Set constant value
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
                    w[k] = 0; // Set constant value
                }
                temp[j] = w;
            }
            weights[i - 1] = new DoubleMatrix(temp);
        }
    }

//    public double[] feedForward(double[] inputs) {
//        double[] outputs = null;
//        for (int i = 1; i < numLayers; i++) {
//            int size = sizes[i];
//            double[] z = new double[size];
//            outputs = new double[size];
//
//            for(int j = 0; j < size; j++) {
//                for (int k = 0; k < inputs.length; k++) {
//                    z[j] += WEIGHTS * inputs[k];
//                }
//                z[j] += BIAS;
//                //outputs[j] = z[j] > 0 ? 1 : 0; // Perceptron output
//                outputs[j] = 1 / (1 + Math.exp(-z[j])); //Sigmoid output
//                //outputs[j] = z[j] >= 0.5 ? 1 : 0; // Perceptron output
//            }
//            inputs = outputs;
//            //System.out.println(size);
//        }
//
//        System.out.println("test");
//        return outputs;
//    }

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
