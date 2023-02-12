package net;

import org.jblas.DoubleMatrix;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.io.BufferedReader;

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
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String numStr = null;

        while (1==1) {
            System.out.print("Enter number between 0 and 256: ");
            try {
                numStr = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (numStr.equals("exit")) {
                return;
            }

            int numValue = Integer.parseInt(numStr);
            System.out.println("You entered number: " + numValue);

            // Prepare right answer
            String binaryRightAnswer = String.format("%8s", Integer.toBinaryString(numValue)).replace(' ', '0');
            System.out.println("Right answer:    " + binaryRightAnswer);

            // Feeding Data
            double[] x = new double[256];
            x = Stream.iterate(0, n -> 0).limit(256).mapToDouble(Double::new).toArray();
            x[numValue] = 1;
            DoubleMatrix mx = new DoubleMatrix(x);

            DoubleMatrix mresult = net.feedForward(mx);
            double[] aresult = mresult.toArray();

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < aresult.length; i++) {
                sb.append(aresult[i] >= 0.5 ? 1 : 0);
            }

            System.out.println("Learning answer: " + sb);
            System.out.println("Source result:");
            System.out.println(mresult.toString());
        }
        //return;
    }
}
