import net.PerceptronNetwork;
import net.SigmoidNetwork;

public class MainClass {
    public static void main(String[] args) {
        System.out.println("Hello Serg");

        //SigmoidNetwork perceptronNetwork = new SigmoidNetwork(3, 3, 2);
        SigmoidNetwork perceptronNetwork = new SigmoidNetwork(3, 1);
        double[] inputs = {0.5, -0.5, -0.5};
        double[] outputs = perceptronNetwork.feedForward(inputs);

        for (int i = 0; i < outputs.length; i++) {
            System.out.println(outputs[i]);
        }
        //System.out.println(perceptronNetwork);

    }
}
