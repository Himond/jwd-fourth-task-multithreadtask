package by.training.multithreadtunnel.parser;

import by.training.multithreadtunnel.entity.Direction;
import by.training.multithreadtunnel.entity.Train;

import java.util.ArrayList;
import java.util.List;

public class TrainParser {

    private TrainParser() {
    }

    private static final String SPLIT_REGEX = "\\s+";

    public static List<Train> parseTrain(List<String> trains){

        List<Train> arrayTrain = new ArrayList<>();
        for(String line: trains){
            String[] newLine = line.split(SPLIT_REGEX);
            arrayTrain.add(new Train(Integer.parseInt(newLine[0]), Direction.valueOf(newLine[1])));
        }

        return arrayTrain;
    }
}
