package net;

import java.util.Arrays;

public class SigmoidNetwork {

    private final double BIAS = 0;
    private final double WEIGHTS = 1;
    private int numLayers;
    private int[] sizes;

    public SigmoidNetwork(int... sizes) {
        this.sizes = sizes;
        this.numLayers = sizes.length;
    }

    public double[] feedForward(double[] inputs) {
        double[] outputs = null;
        for (int i = 1; i < numLayers; i++) {
            int size = sizes[i];
            double[] z = new double[size];
            outputs = new double[size];

            for(int j = 0; j < size; j++) {
                for (int k = 0; k < inputs.length; k++) {
                    z[j] += WEIGHTS * inputs[k];
                }
                z[j] += BIAS;
                //outputs[j] = z[j] > 0 ? 1 : 0; // Perceptron output
                //outputs[j] = 1 / (1 + Math.exp(-z[j])); //Sigmoid output
                outputs[j] = z[j] >= 0.5 ? 1 : 0; // Perceptron output
            }
            inputs = outputs;
            //System.out.println(size);
        }

        System.out.println("test");
        return outputs;
    }

    @Override
    public String toString() {
        return "net.PerceptronNetwork{" + "\n" +
                "                  " + "BIAS=" + BIAS + "\n" +
                "                  " + "sizes=" + Arrays.toString(sizes) + "\n" +
                "                  " + "numLayers=" + numLayers + "\n" +
                "                  " + '}';
    }

}