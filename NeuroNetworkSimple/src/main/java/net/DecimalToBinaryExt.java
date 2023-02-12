package net;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class DecimalToBinaryExt {
    private SigmoidNetworkExt net;

    public DecimalToBinaryExt() {
        net = new SigmoidNetworkExt(256, 32, 8);
    }

    public void Learning() {
        // preparing learning data
        List<double[][]> inputsOutputs = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            double[][] io = new double[2][];
            double[] x = new double[256];
            double[] y = new double[8];

            x = Stream.iterate(0, n -> 0).limit(256).mapToDouble(Double::new).toArray();

            String binary = String.format("%8s", Integer.toBinaryString(i)).replace(' ', '0');
            y = Arrays.stream(binary.split("")).mapToDouble(Double::parseDouble).toArray();

            x[i] = 1;
            io[0] = x;
            io[1] = y;
            inputsOutputs.add(io);
        }

        net.SGD(inputsOutputs, 1000, 8, 15, inputsOutputs.subList(0, 100));

        return;
    }

    public void Test() {

        return;
    }
}
