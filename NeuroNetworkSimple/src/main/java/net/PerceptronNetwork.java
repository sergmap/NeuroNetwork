package net;

import java.awt.im.InputMethodRequests;
import java.util.Arrays;

public class PerceptronNetwork {

    private final int BIAS = 0;
    private final int WEIGHTS = 1;
    private int numLayers;
    private int[] sizes;

    public PerceptronNetwork(int... sizes) {
        this.sizes = sizes;
        this.numLayers = sizes.length;
    }

    public int[] feedForward(int[] inputs) {
        int[] outputs = null;
        for (int i = 1; i < numLayers; i++) {
            int size = sizes[i];
            int[] z = new int[size];
            outputs = new int[size];

            for(int j = 0; j < size; j++) {
                for (int k = 0; k < inputs.length; k++) {
                    z[j] += WEIGHTS * inputs[k];
                }
                z[j] += BIAS;
                outputs[j] = z[j] > 0 ? 1 : 0;
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
