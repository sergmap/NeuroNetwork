package net;

import org.jblas.DoubleMatrix;

public class SemanticByCharRecurrentNet {
    DoubleMatrix recurrentTrack = new DoubleMatrix(10);
    // Создать массив символов
    // Считывать файл, посимвольно запихивать в нейронку, добавляя исходящий символ Push в массив рекуррентных последовательностей
    // Создать нейронку

    public DoubleMatrix[] PushRecurrentValue(DoubleMatrix[] aTrack, DoubleMatrix[] aPushValue) {
        int tmpTrackSize = aTrack.length;

        for (int i = 0; i < tmpTrackSize; i++) {
            int tmpStackSize = aTrack[i].length;

            double[] tmpTrackOneDouble = aTrack[i].toArray();

            int tmpTrackOneDoubleSize = tmpTrackOneDouble.length;
            for (int j = 0; j < tmpTrackOneDoubleSize-1; j++) {
                double tmpValue = tmpTrackOneDouble[j]/10;
                tmpTrackOneDouble[j+1] = tmpValue;
                tmpTrackOneDouble[j] = 0;
            }

            double[] tmpPushDouble = aPushValue[i].toArray();
            tmpTrackOneDouble[0] = tmpPushDouble[0];

            DoubleMatrix tmpTrackOneMatrix = new DoubleMatrix(tmpTrackOneDouble);
            aTrack[i] = tmpTrackOneMatrix;
        }

        return aTrack;
    }
}
