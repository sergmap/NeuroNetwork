import net.*;
import org.jblas.DoubleMatrix;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class MainClass {
    public static void main(String[] args) throws IOException {
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

        //String userName = Console.ReadLine();
        //Scanner scanner = new Scanner(System.in);
        //String name = scanner.nextLine();
        //System.out.println("Hello " + name);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //String binary1 = String.format("%8s", Integer.toBinaryString(256)).replace(' ', '0');
        //System.out.println(binary1);


        DecimalToBinaryExt decimalToBinaryExt = new DecimalToBinaryExt();
        decimalToBinaryExt.Learning();
        decimalToBinaryExt.Test();
    }
}
