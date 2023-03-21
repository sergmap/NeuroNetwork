package net;

import org.jblas.DoubleMatrix;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class SemanticByCharRecurrentNet {
    DoubleMatrix recurrentTrack = new DoubleMatrix(10);

    private SigmoidNetworkExt net;
    private String filename_learn = "d:\\Sergwork\\Java\\NeuroNetwork_Материалы для обучения\\Подготовка\\numeric.txt";

    private ArrayList<Character> charArray = new ArrayList<Character>(Arrays.asList('а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й',
            'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф',
            'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            ' '));

    // Создать массив символов
    // Считывать файл, посимвольно запихивать в нейронку, добавляя исходящий символ Push в массив рекуррентных последовательностей
    // Создать нейронку
    public void Learning() throws IOException {
        File file = new File(filename_learn);
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);

        List<double[][]> inputsOutputs = new ArrayList<>();

        // Create an track DoubleMatrix
        DoubleMatrix[] tmpTrack = new DoubleMatrix[charArray.size()];
        double[] dTrack = new double[10];
        for (int i = 0; i < tmpTrack.length; i++) {
            tmpTrack[i] = new DoubleMatrix(dTrack);
        }

        int symbol = reader.read();

        char prevInputChar = (char)0;

        while (symbol != -1) {
            char inputChar = (char) symbol;
            inputChar = Character.toLowerCase(inputChar);
            int inputCharIdx = charArray.indexOf(inputChar);

            if (inputCharIdx != -1) {

                double[][] io = new double[2][];
                double[] x = ConvertCharToBit(inputChar).toArray();
                double[] y = ConvertCharToBit(prevInputChar).toArray();

                DoubleMatrix[] tmpPushValueMatrix = new DoubleMatrix[charArray.size()];
                for (int a = 0; a < x.length; a++) {
                    double[] dPush = new double[1];
                    dPush[0] = x[a];
                    tmpPushValueMatrix[a] = new DoubleMatrix(dPush);
                }
                tmpTrack = this.PushRecurrentValue(tmpTrack, tmpPushValueMatrix);
                x = CollapseTrackToArray(tmpTrack);

                io[0] = x;
                io[1] = y;
                inputsOutputs.add(io);

                prevInputChar = inputChar;
            }
            symbol = reader.read();
        }
        net = new SigmoidNetworkExt(charArray.size(), 32, charArray.size());
        net.SGD(inputsOutputs, 100, 10, 4, inputsOutputs.subList(0, 10));
    }

    

    public DoubleMatrix[] PushRecurrentValue(DoubleMatrix[] aTrack, DoubleMatrix[] aPushValue) {
        int tmpTrackSize = aTrack.length;

        for (int i = 0; i < tmpTrackSize; i++) {
            int tmpStackSize = aTrack[i].length;

            double[] tmpTrackOneDouble = aTrack[i].toArray();

            int tmpTrackOneDoubleSize = tmpTrackOneDouble.length;
            /*for (int j = 0; j < tmpTrackOneDoubleSize-1; j++) {
                double tmpValue = tmpTrackOneDouble[j]/10;
                tmpTrackOneDouble[j+1] = tmpValue;
                tmpTrackOneDouble[j] = 0;
            }*/

            for (int j = tmpTrackOneDoubleSize-2; j >= 0 ; j--) {
                double tmpValue = tmpTrackOneDouble[j]/10;
                tmpTrackOneDouble[j+1] = tmpValue;
            }

            double[] tmpPushDouble = aPushValue[i].toArray();
            tmpTrackOneDouble[0] = tmpPushDouble[0];

            DoubleMatrix tmpTrackOneMatrix = new DoubleMatrix(tmpTrackOneDouble);
            aTrack[i] = tmpTrackOneMatrix;
        }

        return aTrack;
    }

    public double[] CollapseTrackToArray(DoubleMatrix[] aTrackMatrix) {
        double[] tmpResultArray = new double[aTrackMatrix.length];


        for (int i = 0; i < aTrackMatrix.length; i++) {
            double[] tmpArray = aTrackMatrix[i].toArray();
            double tmpMaxValue = 0;
            for (int j = 0; j < tmpArray.length; j++) {
                if (tmpArray[j] > tmpMaxValue) {tmpMaxValue = tmpArray[j];}
            }
            tmpResultArray[i] = tmpMaxValue;
        }
        return tmpResultArray;
    }

    public DoubleMatrix ConvertCharToBit(char inputChar) {
        int charArraySize = charArray.size();
        double[] temp = new double[charArraySize];
        int inputCharIdx = charArray.indexOf(inputChar);
        if (inputCharIdx != -1) {
            temp[inputCharIdx] = 1;
        }
        DoubleMatrix tmpResultMatrix = new DoubleMatrix(temp);
        return tmpResultMatrix;
    }
}
