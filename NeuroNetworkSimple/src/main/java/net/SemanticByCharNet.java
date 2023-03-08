package net;

import org.jblas.DoubleMatrix;
import org.jblas.util.Random;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class SemanticByCharNet {
    //private String filename_learn = "d:\\Sergwork\\Java\\NeuroNetwork_Материалы для обучения\\Подготовка\\Огниво_for_learning.txt";
    private String filename_learn = "d:\\Sergwork\\Java\\NeuroNetwork_Материалы для обучения\\Подготовка\\Огниво.txt";
    private SigmoidNetworkExt net;
    private ArrayList<Character> charArray = new ArrayList<Character>(Arrays.asList('а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й',
                                                                                    'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф',
                                                                                    'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я',
                                                                                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
                                                                                    ' '));

    public SemanticByCharNet() {

    }

    //Preparing from source file
    public void PreviewLearningFile1() throws IOException {
        charArray = new ArrayList<Character>(Arrays.asList('а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й',
                                                           'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф',
                                                           'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я',
                                                           '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
                                                           ' '));

        // Собрать массив уникальных символов из файла
        charArray.add('e');
        charArray.add('w');
        charArray.add('d');

        //System.out.println("charArray=" + charArray);

        // Opening the learning file for read
        File file = new File(filename_learn);
        FileReader fr = new FileReader(file);

        BufferedReader reader = new BufferedReader(fr);

        int symbol = reader.read();

        while (symbol != -1) {  // Когда дойдём до конца файла, получим '-1'
            // Что-то делаем с прочитанным символом
            // Преобразовать в char:
            char c = (char) symbol;
            c = Character.toLowerCase(c);
            int charIndex = charArray.indexOf(c);

            //System.out.println("char = " + c + " index = " + charIndex);

            if (charIndex == -1) {
                charArray.add(c);
            }
            symbol = reader.read(); // Читаем символ
        }
        charArray.sort(Character::compareTo);

        System.out.println("charArray=" + charArray);

        for (int i = 0; i < charArray.size(); i++) {

            //System.out.print("charCode=" + );
            System.out.print("charCode=" + (int)charArray.get(i) +  charArray.get(i));
            System.out.println("");
        }
    }

    public void Learning() throws IOException {
        //this.PreviewLearningFile();
        int charArraySize = charArray.size();
        //net = new SigmoidNetworkExt(charArray.size()*5, 32, charArray.size());
        net = new SigmoidNetworkExt(charArraySize*5, 32, charArraySize);

        // Готовим данные для обучения
        // Считываем файл посимвольно, организуем очередь битов,
        // в каждой из которых будет включаться бит, соответствующий индексу из массива символов
        File file = new File(filename_learn);
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);

        // preparing learning data
        List<double[][]> inputsOutputs = new ArrayList<>();
        char char0 = (char) 0;
        char char1 = (char) 0;
        char char2 = (char) 0;
        char char3 = (char) 0;
        char char4 = (char) 0;
        char char5 = (char) 0;

        int symbol = reader.read();
        symbol = reader.read();

        int tmpJ = 0;

        //while ((symbol != -1)
        //      && (tmpJ < 110))
        while (symbol != -1)
        {
            tmpJ++;
            char c = (char) symbol;
            c = Character.toLowerCase(c);

            char0 = char1;
            char1 = char2;
            char2 = char3;
            char3 = char4;
            char4 = char5;
            char5 = c;

            int tmpIndexChar0 = 0;
            int tmpIndexChar1 = 0;
            int tmpIndexChar2;
            int tmpIndexChar3;
            int tmpIndexChar4;
            int tmpIndexChar5;

            double[][] io = new double[2][];
            double[] x = new double[charArray.size()*5];
            double[] y = new double[charArray.size()];

            x = Stream.iterate(0, n -> 0).limit(charArray.size()*5).mapToDouble(Double::new).toArray();

            tmpIndexChar1 = charArray.indexOf(char1);
            if (tmpIndexChar1 != -1) {x[tmpIndexChar1] = 1;}

            tmpIndexChar2 = charArray.indexOf(char2);
            if (tmpIndexChar2 != -1) {x[tmpIndexChar2+charArraySize*1] = 1;}

            tmpIndexChar3 = charArray.indexOf(char3);
            if (tmpIndexChar3 != -1) {x[tmpIndexChar3+charArraySize*2] = 1;}

            tmpIndexChar4 = charArray.indexOf(char4);
            if (tmpIndexChar4 != -1) {x[tmpIndexChar4+charArraySize*3] = 1;}

            tmpIndexChar5 = charArray.indexOf(char5);
            if (tmpIndexChar5 != -1) {x[tmpIndexChar5+charArraySize*4] = 1;}

            y = Stream.iterate(0, n -> 0).limit(charArray.size()).mapToDouble(Double::new).toArray();
            tmpIndexChar0 = charArray.indexOf(char0);
            if (tmpIndexChar0 != -1) {y[tmpIndexChar0] = 1;}

            io[0] = x;
            io[1] = y;
            inputsOutputs.add(io);
            //System.out.println(inputsOutputs.size());

            symbol = reader.read();
        }
        System.out.println("charArray.size = " + charArray.size());
        //net.SGD(inputsOutputs, 1000, 10, 15, inputsOutputs.subList(0, 10));
        //net.SGD(inputsOutputs, 1000, 10, 15, inputsOutputs.subList(0, 10));
        net.SGD(inputsOutputs, 100, 10, 5, inputsOutputs.subList(0, 10));
        //net.SGD(inputsOutputs, 3, 10, 15, inputsOutputs.subList(0, 10));

        return;
    }

    public void Test() throws IOException {
        // Считать 5 символов с клавиатуры
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String userStr = "";
        String tmpResultStr = "";
        int charArraySize = charArray.size();

        System.out.print("Введите 5 символов: ");
        userStr = reader.readLine();
        tmpResultStr = userStr;

        while (userStr.equals("exit") != true) {
            System.out.println("Вы ввели: " + userStr);

            DoubleMatrix inputFeed = this.ConvertStrToBit(userStr);
            DoubleMatrix resultFeed = net.feedForward(inputFeed);

            String feedStr = userStr;
            System.out.println(feedStr);

            while (tmpResultStr.length() < 13) {
                // Преобразовать текущую строку для входа в битовый массив
                DoubleMatrix tmpInput = this.ConvertStrToBit(feedStr);
                DoubleMatrix tmpOutput = null;//new DoubleMatrix(charArraySize);
                // Подать массив на вход нейронки
                tmpOutput = net.feedForward(tmpInput);
                // Получить ответ из нейронки и определить, какой символ в нем зашит
                char tmpOutputChat = this.ConvertBitToChar(tmpOutput);
                //System.out.println("feedStr=" + feedStr + "      tmpOutputChat=" + tmpOutputChat);
                // сдвинуть символы текущей строки вход влево, добавить символ, полученный из нейронки
                StringBuilder tempSb = new StringBuilder("");
                for (int i = 1; i < feedStr.length(); i++) {
                    tempSb.append(feedStr.charAt(i));
                }
                tempSb.append(tmpOutputChat);
                feedStr = tempSb.toString();
                System.out.println(feedStr);
                tmpResultStr = tmpResultStr + tmpOutputChat;
            }

            //double[] inputFeed = net.ConvertToInputBit("qwerty");
            // Создать процедуры:
            //      предварительное чтение файла (заполнение charArray)
            //      подготовка входа (250) из 5 передаваемых символов ConvertToInputBit
            System.out.println("tmpResultStr=" + tmpResultStr);
            System.out.print("Введите 5 символов: ");
            userStr = reader.readLine();
            tmpResultStr = userStr;
        }
        return;
    }

    public DoubleMatrix ConvertStrToBit(String inputString) {
        int charArraySize = charArray.size();
        double[] temp = new double[charArraySize*5];
        //Double[] tmpResultArray = new Double[charArraySize*5];

        for (int i = 0; i < 5; i++) {
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

    public char ConvertBitToChar(DoubleMatrix aBit) {

        // Найти первое значение в массиве с значением 1
        double[] tmpBitArray = aBit.toArray();
        int tmpCharIndex = -1;
        int charArraySize = charArray.size();
        //System.out.println("aBit:" + aBit.toString());

        //System.out.print("tmpBitArray:");
        for (int i = 0; i < tmpBitArray.length; i++) {
            double d = tmpBitArray[i] >= 0.5 ? 1 : 0;
            tmpBitArray[i] = d;
            if (d == 1) {
                tmpCharIndex = i;
            }
            System.out.print(tmpBitArray[i] + " ");
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

                if (tmpCharValueMax > tmpBitArray[j]) {
                    tmpCharIndexMax = j;
                }
            }
            resultChar = charArray.get(tmpCharIndexMax);
            System.out.println("resultCharMax=" + resultChar + " код=" + (int)resultChar);
            //resultChar = charArray.get(Random.nextInt(charArraySize-1));
            //System.out.println("resultCharRandom=" + resultChar);
        }

        //System.out.println("tmpCharIndex=" + tmpCharIndex + " resultChar=" + resultChar);

        return resultChar;
    }
}
