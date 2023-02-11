import net.*;
import org.jblas.DoubleMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MainClass {
    public static void main(String[] args) {
        System.out.println("Hello Serg");

        //SigmoidNetwork perceptronNetwork = new SigmoidNetwork(3, 3, 2);
        //SigmoidNetwork perceptronNetwork = new SigmoidNetwork(3, 1);
        //double[] inputs = {0.5, 0.5, 0.5};
        //double[] outputs = perceptronNetwork.feedForward(inputs);

        //double[] inputs = {0.5, 0.5, 0.5};
        //double[] inputs = {1, 1};
        //MatrixNetwork matrixNetwork = new MatrixNetwork(2,3,2);
        //DoubleMatrix outputs  = matrixNetwork.feedForward(new DoubleMatrix(inputs));

        //System.out.println(outputs.toString("%.02f"));
        //for (int i = 0; i < outputs.length; i++) {
        //    System.out.println(outputs[i]);
        //}
        //System.out.println(perceptronNetwork);

        //DecimalToBinarySimpleTest net = new DecimalToBinarySimpleTest(10, 4);
        //double[] inputs = {0, 0, 0, 0, 0, 0, 0, 0, 1, 0};
        //DoubleMatrix outputs = net.feedForward(new DoubleMatrix(inputs));
        //System.out.println(outputs.toString("%.0f"));

        //        SigmoidNetworkExt net = new SigmoidNetworkExt(1, 1);
        //        double[] inputs = {0};
        //        double[] outputs = {0};
        //        double[][] inputsOutputs = new double[][] {inputs, outputs};
        //        DoubleMatrix[][] deltas = net.backProp(inputsOutputs);
        //        for (int i = 0; i < net.biases.length; i++){
        //            net.biases[i] = net.biases[i].sub(deltas[0][i].mul(4));
        //        }
        //        System.out.println("Complete");

        List<double[][]> inputsOutputs = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            double[][] io = new double[2][];
            double[] x = new double[256];
            double[] y = new double[8];

            String binary = String.format("%8s", Integer.toBinaryString(i)).replace(' ', '0');
            //x = Stream.iterate(0, n -> 0).limit(256).mapToDouble(Double::new).toArray();
            x = Stream.iterate(0, n -> 0).limit(256).mapToDouble(Double::new).toArray();
            y = Arrays.stream(binary.split("")).mapToDouble(Double::parseDouble).toArray();

            x[i] = 1;
            io[0] = x;
            io[1] = y;
            inputsOutputs.add(io);
        }

        SigmoidNetworkExt net = new SigmoidNetworkExt(256, 32, 8);
        net.SGD(inputsOutputs, 1000, 8, 15, inputsOutputs.subList(0, 100));
    }
}
