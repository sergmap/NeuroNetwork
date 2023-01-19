import net.MatrixNetwork;
import net.PerceptronNetwork;
import net.SigmoidNetwork;
import org.jblas.DoubleMatrix;

public class MainClass {
    public static void main(String[] args) {
        System.out.println("Hello Serg");

        //SigmoidNetwork perceptronNetwork = new SigmoidNetwork(3, 3, 2);
        //SigmoidNetwork perceptronNetwork = new SigmoidNetwork(3, 1);
        //double[] inputs = {0.5, 0.5, 0.5};
        //double[] outputs = perceptronNetwork.feedForward(inputs);

        //double[] inputs = {0.5, 0.5, 0.5};
        double[] inputs = {1, 1};
        MatrixNetwork matrixNetwork = new MatrixNetwork(2,3,2);
        DoubleMatrix outputs  = matrixNetwork.feedForward(new DoubleMatrix(inputs));

        //System.out.println(outputs.toString("%.02f"));
        System.out.println(outputs.toString("%.04f"));
        //for (int i = 0; i < outputs.length; i++) {
        //    System.out.println(outputs[i]);
        //}
        //System.out.println(perceptronNetwork);

    }
}
