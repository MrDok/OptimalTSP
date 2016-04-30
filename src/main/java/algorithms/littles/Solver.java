package algorithms.littles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Alexander on 27.04.2016.
 */
public class Solver{
    WeightMatrix matrix;
    float lowBound;
    ArrayList<Element> result;

    Solver(File file) throws IOException{
        matrix = new WeightMatrix();
        result = new ArrayList<>();
        matrix.fillMatrix(file);
        lowBound = 0f;

        while(matrix.rang() > 2){
            lowBound += matrix.convert();
            Element dot = matrix.estimation();
            result.add(dot);
            matrix.deleteRowAndColumn(dot);
        }

        solveForTwoRangMatrix();
        System.out.println(lowBound);
        System.out.println(weightOfOptimalDecision());
        System.out.println(result);
        //matrix.output();
    }

    public void solveForTwoRangMatrix(){
        lowBound += matrix.convert();
        result.addAll(matrix.solveForTwoRangMatrix());
    }

    public float weightOfOptimalDecision(){
        return matrix.calculateWeightOfTour(result);
    }

    public static void main(String args[]){
        try{
            Solver solver = new Solver(new File("C:\\Users\\Alexander\\IdeaProjects\\OptimalTSP\\src\\main\\java\\algorithms\\littles\\resources\\test.txt"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
