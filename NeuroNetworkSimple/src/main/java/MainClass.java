import net.*;
import org.jblas.DoubleMatrix;

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

        SigmoidNetworkExt net = new SigmoidNetworkExt(1, 1);
        double[] inputs = {0};
        double[] outputs = {0};
        double[][] inputsOutputs = new double[][] {inputs, outputs};
        DoubleMatrix[][] deltas = net.backProp(inputsOutputs);
        for (int i = 0; i < net.biases.length; i++){
            net.biases[i] = net.biases[i].sub(deltas[0][i].mul(4));
        }
        System.out.println("Complete");
    }
}
