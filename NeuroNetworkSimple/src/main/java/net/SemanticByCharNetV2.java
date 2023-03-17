package net;

import org.jblas.DoubleMatrix;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class SemanticByCharNetV2 {
    //Модифицированная типа версия
    //первое что нужно сделать
    //Считываем файл посимвольно, если
    private SigmoidNetworkExt net;

    //private String filename_learn = "d:\\Sergwork\\Java\\NeuroNetwork_Материалы для обучения\\Подготовка\\Огниво.txt";
    private String filename_learn = "d:\\Sergwork\\Java\\NeuroNetwork_Материалы для обучения\\Подготовка\\numeric.txt";

    private ArrayList<Character> charArray = new ArrayList<Character>(Arrays.asList('а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й',
            'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф',
            'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            ' '));

    int inputStringSize = 5;

    public void TestingReadFile() throws IOException {
        File file = new File(filename_learn);
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);

        int symbol = reader.read();

        while (symbol != -1) {
            char c = (char) symbol;
            c = Character.toLowerCase(c);

            if (symbol == 10 || symbol == 13) {
                System.out.print(c);
            }
            else {
                int tmpCharArrarIdx = charArray.indexOf(c);
                if (tmpCharArrarIdx != -1) {
                    System.out.print(c);

                }
            }
            symbol = reader.read();
        }

    }

    public String StringCharShiftLeft(String aString, char additionalChar) {
        // Получаем строку, сдвигаем влево, первый символ выкидываем, последний добавляем
        StringBuilder sb = new StringBuilder("");
        for (int i = 1; i < aString.length(); i++) {
            char tmpChar = aString.charAt(i);
            sb.append(tmpChar);
        }
        sb.append(additionalChar);
        String returnString = sb.toString();

        return returnString;
    }

    public void Learning() throws IOException {

        StringBuilder inputSb = new StringBuilder("");
        for (int i = 0; i < inputStringSize; i++) {
            inputSb.append((char) 0);
        }

        String inputString = inputSb.toString();
        List<double[][]> inputsOutputs = new ArrayList<>();

        File file = new File(filename_learn);
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);

        int symbol = reader.read();

        while (symbol != -1) {
            char inputChar = (char) symbol;
            inputChar = Character.toLowerCase(inputChar);
            int inputCharIdx = charArray.indexOf(inputChar);

            if (inputCharIdx != -1) {
                double[][] io = new double[2][];
                double[] x = ConvertStrToBit(inputString).toArray();
                double[] y = ConvertCharToBit(inputChar).toArray();

                io[0] = x;
                io[1] = y;
                inputsOutputs.add(io);

                inputString = this.StringCharShiftLeft(inputString, inputChar);
                System.out.println(inputString);
            }
            symbol = reader.read();
        }
        //net = new SigmoidNetworkExt(inputStringSize*charArray.size(), 32, charArray.size());
        net = new SigmoidNetworkExt(inputStringSize*charArray.size(), 32, charArray.size());
        net.SGD(inputsOutputs, 100, 10, 4, inputsOutputs.subList(0, 10));
    }

    public void Test() throws IOException {
        // Считать 5 символов с клавиатуры
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String userStr = "";
        String tmpResultStr = "";
        int charArraySize = charArray.size();

        System.out.print("Введите " + inputStringSize + " символов: ");
        userStr = reader.readLine();
        tmpResultStr = userStr;

        while (userStr.equals("exit") != true) {
            System.out.println("Вы ввели: " + userStr);

            DoubleMatrix inputFeed = this.ConvertStrToBit(userStr);
            DoubleMatrix resultFeed = net.feedForward(inputFeed);

            String feedStr = userStr;
            //System.out.println(feedStr);

            while (tmpResultStr.length() < 130) {
                // Преобразовать текущую строку для входа в битовый массив
                DoubleMatrix tmpInput = this.ConvertStrToBit(feedStr);
                DoubleMatrix tmpOutput = null;//new DoubleMatrix(charArraySize);
                // Подать массив на вход нейронки
                tmpOutput = net.feedForward(tmpInput);
                // Получить ответ из нейронки и определить, какой символ в нем зашит
                char tmpOutputChat = this.ConvertBitToChar(tmpOutput);
                // сдвинуть символы текущей строки вход влево, добавить символ, полученный из нейронки
                StringBuilder tempSb = new StringBuilder("");
                for (int i = 1; i < feedStr.length(); i++) {
                    tempSb.append(feedStr.charAt(i));
                }
                tempSb.append(tmpOutputChat);
                feedStr = tempSb.toString();
                //System.out.println(feedStr);
                tmpResultStr = tmpResultStr + tmpOutputChat;
            }

            //double[] inputFeed = net.ConvertToInputBit("qwerty");
            // Создать процедуры:
            //      предварительное чтение файла (заполнение charArray)
            //      подготовка входа (250) из 5 передаваемых символов ConvertToInputBit
            System.out.println("tmpResultStr=" + tmpResultStr);
            System.out.print("Введите " + inputStringSize + " символов: ");
            userStr = reader.readLine();
            tmpResultStr = userStr;
        }
        return;
    }

    public DoubleMatrix ConvertStrToBit(String inputString) {
        int charArraySize = charArray.size();
        double[] temp = new double[charArraySize*inputStringSize];

        for (int i = 0; i < inputStringSize; i++) {
            char inputChar = inputString.charAt(i);
            int inputCharIdx = charArray.indexOf(inputChar);
            if (inputCharIdx != -1) {
                int tmpCharArrayIdx = (i * charArraySize) + inputCharIdx;
                temp[tmpCharArrayIdx] = 1;
            }
        }
        DoubleMatrix tmpResultMatrix = new DoubleMatrix(temp);
        return tmpResultMatrix;
    }

    public DoubleMatrix ConvertCharToBit(char inputChar) {
        int charArraySize = charArray.size();
        double[] temp = new double[charArraySize];
        int inputCharIdx = charArray.indexOf(inputChar);

        temp[inputCharIdx] = 1;
        DoubleMatrix tmpResultMatrix = new DoubleMatrix(temp);
        return tmpResultMatrix;
    }

    public char ConvertBitToChar(DoubleMatrix aBit) {

        // Найти первое значение в массиве с значением 1

        double test = aBit.get(0);

        double[] tmpBitArray = aBit.toArray();
        int tmpCharIndex = -1;
        int charArraySize = charArray.size();
        //System.out.println("aBit:" + aBit.toString());

        //System.out.print("tmpBitArray:");
        for (int i = 0; i < tmpBitArray.length; i++) {
            double d = tmpBitArray[i] >= 0.5 ? 1 : 0;
            //tmpBitArray[i] = d;
            if (d == 1) {
                tmpCharIndex = i;
            }
            //System.out.print(tmpBitArray[i] + " ");
        }
        //System.out.println("");

        char resultChar = '?';
        if (tmpCharIndex != -1) {
            resultChar = charArray.get(tmpCharIndex);
            System.out.println("resultChar=" + resultChar + " код=" + (int)resultChar);
        }
        else {
            double tmpCharValueMax = 0;
            int tmpCharIndexMax = 0;
            // Получить индекс наибольшего значения из Массива
            for (int j = 0; j < tmpBitArray.length; j++) {

                if (tmpBitArray[j] > tmpCharValueMax) {
                    tmpCharIndexMax = j;
                }
            }
            resultChar = charArray.get(tmpCharIndexMax);
            System.out.println("resultCharMax=" + resultChar + " код=" + (int)resultChar);
        }

        return resultChar;
    }
}
