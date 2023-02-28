package net;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SemanticByCharNet {
    private String filename_learn = "d:\\Sergwork\\Java\\NeuroNetwork_Материалы для обучения\\Подготовка\\Огниво.txt";
    private SigmoidNetworkExt net;

    public void Learning() throws IOException {
        // Собрать массив уникальных символов из файла
        ArrayList<Character> charArray = new ArrayList<Character>();
        charArray.add('e');
        charArray.add('w');
        charArray.add('d');

        System.out.println(charArray);

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

        for (int i = 0; i < charArray.size(); i++) {
            System.out.print(charArray.get(i));
            System.out.println("");
        }

        // Готовим данные для обучения
        // Считываем файл посимвольно, организуем очередь битов,
        // в каждой из которых будет включаться бит, соответствующий индексу из массива символов
        FileReader fr1 = new FileReader(file);

        BufferedReader reader1 = new BufferedReader(fr1);

        // preparing learning data
        List<double[][]> inputsOutputs = new ArrayList<>();
        char char0 = (char) 0;
        char char1 = (char) 0;
        char char2 = (char) 0;
        char char3 = (char) 0;
        char char4 = (char) 0;
        char char5 = (char) 0;

        symbol = reader1.read();

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
            if (tmpIndexChar2 != -1) {x[tmpIndexChar2+50*1] = 1;}

            tmpIndexChar3 = charArray.indexOf(char3);
            if (tmpIndexChar3 != -1) {x[tmpIndexChar3+50*2] = 1;}

            tmpIndexChar4 = charArray.indexOf(char4);
            if (tmpIndexChar4 != -1) {x[tmpIndexChar4+50*3] = 1;}

            tmpIndexChar5 = charArray.indexOf(char5);
            if (tmpIndexChar5 != -1) {x[tmpIndexChar5+50*4] = 1;}

            y = Stream.iterate(0, n -> 0).limit(charArray.size()).mapToDouble(Double::new).toArray();
            tmpIndexChar0 = charArray.indexOf(char0);
            if (tmpIndexChar0 != -1) {y[tmpIndexChar0] = 1;}

            io[0] = x;
            io[1] = y;
            inputsOutputs.add(io);
            //System.out.println(inputsOutputs.size());

            symbol = reader1.read();
        }
        System.out.println("charArray.size = " + charArray.size());
        net = new SigmoidNetworkExt(charArray.size()*5, 32, charArray.size());
        //net.SGD(inputsOutputs, 1000, 10, 15, inputsOutputs.subList(0, 10));
        net.SGD(inputsOutputs, 1000, 10, 15, inputsOutputs.subList(0, 10));

        return;
    }
}
