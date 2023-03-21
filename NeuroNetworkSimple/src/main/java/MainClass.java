import net.*;
import org.jblas.DoubleMatrix;
import parse.ParsingText;

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
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //String binary1 = String.format("%8s", Integer.toBinaryString(256)).replace(' ', '0');
        //System.out.println(binary1);

        //DecimalToBinaryExt decimalToBinaryExt = new DecimalToBinaryExt();
        //decimalToBinaryExt.Learning();
        //decimalToBinaryExt.Test();

        //ParsingText parsingText = new ParsingText();
        //parsingText.Test();

        //SemanticByCharNet net = new SemanticByCharNet();
        //net.PreviewLearningFile();
        //net.Learning();
        //net.Test();
        //DoubleMatrix convertResult = net.ConvertToInputBit("фываа");

        //System.out.println(convertResult);

        //SemanticByCharNetV2 net = new SemanticByCharNetV2();
        //net.Learning();
        //net.Test();

        //DoubleMatrix[] testMatrix = new DoubleMatrix[10];
        //DoubleMatrix testMatrix = new DoubleMatrix(10);
        //System.out.println(testMatrix);

        //DoubleMatrix[] testMatrix = new DoubleMatrix[10];
        //double[] dTest = new double[10];
        //dTest[0] = 1;
        //for (int i = 0; i < testMatrix.length; i++) {
        //    testMatrix[i] = new DoubleMatrix(dTest);
        //}

        //for (int i = 0; i < testMatrix.length; i++) {
        //    System.out.println(testMatrix[i]);
        //}
        //System.out.println(testMatrix.toString());
        //int[][] testArray

        /*SemanticByCharRecurrentNet net = new SemanticByCharRecurrentNet();

        DoubleMatrix[] tmpTrack = new DoubleMatrix[5];
        double[] dTrack = new double[10];
        dTrack[0] = 2;
        for (int i = 0; i < tmpTrack.length; i++) {
            tmpTrack[i] = new DoubleMatrix(dTrack);
        }

        //double[] dtest1 =
        DoubleMatrix[] tmpPushValue = new DoubleMatrix[10];
        double[] dPush = new double[1];
        dPush[0] = 1;
        for (int i = 0; i < tmpPushValue.length; i++) {
            tmpPushValue[i] = new DoubleMatrix(dPush);
        }

        DoubleMatrix[] tmpResultPush = net.PushRecurrentValue(tmpTrack, tmpPushValue);

        for (int i = 0; i < tmpResultPush.length; i++) {
            System.out.println(tmpResultPush[i]);
        }
        //System.out.println(dTrack.toString());
        //System.out.println(tmpTrack.toString());*/

        SemanticByCharRecurrentNet net = new SemanticByCharRecurrentNet();
        net.Learning();
    }
}
