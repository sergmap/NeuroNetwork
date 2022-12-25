import net.PerceptronNetwork;

public class MainClass {
    public static void main(String[] args) {
        System.out.println("Hello Serg");

        PerceptronNetwork perceptronNetwork = new PerceptronNetwork(2, 3, 2);
        int[] inputs = {1, 0};
        int[] outputs = perceptronNetwork.feedForward(inputs);

        for (int i = 0; i < outputs.length; i++) {
            System.out.println(outputs[i]);
        }
        //System.out.println(perceptronNetwork);

    }
}
