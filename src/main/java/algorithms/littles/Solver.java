package algorithms.littles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Alexander on 27.04.2016.
 */
public class Solver{
    WeightMatrix matrix;
    float resultLowBound;
    ArrayList<Element> result;

    Solver(File file) throws IOException{
        matrix = new WeightMatrix();
        result = new ArrayList<>();
        matrix.fillMatrix(file);
        resultLowBound = 0f;
        ArrayList<Element> list = new ArrayList<>();

        float lowBound = 0f;
        lowBound += matrix.convert();

        solve(matrix, lowBound, list);
    }

    public boolean solve(WeightMatrix wm, float lowBound, ArrayList<Element> list){
        if(wm.rang() != 2){
            WeightMatrix wm1 = null;
            WeightMatrix wm2 = null;

            try {
                wm1 = (WeightMatrix)wm.clone();
                wm2 = (WeightMatrix)wm.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

            Element maxEstimate = wm.estimation();
            wm1.deleteRowAndColumn(maxEstimate);
            wm2.setInfinity(maxEstimate);

            float lowBound1 = wm1.convert();
            float lowBound2 = wm2.convert();

            boolean check = false;
            if(lowBound1 <= lowBound2){
                ArrayList<Element> cloneList = (ArrayList<Element>)list.clone();
                cloneList.add(maxEstimate);
                check = solve(wm1, lowBound + lowBound1, cloneList);
            }
            if(!check){
                solve(wm2, lowBound + lowBound2, list);
            }

            return check;
        }else{
            WeightMatrix wmClone = null;
            ArrayList<Element> listClone = null;
            try {
                wmClone = (WeightMatrix) wm.clone();
                listClone = (ArrayList<Element>) list.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

            lowBound += wmClone.convert();
            listClone.addAll(wmClone.solveForTwoRangMatrix());

            if(weightOfOptimalDecision(list) <= lowBound) {
                this.resultLowBound = lowBound;
                this.result.addAll(listClone);
                return true;
            }
            else{
                return false;
            }
        }
    }

    public void solveForTwoRangMatrix(WeightMatrix wm, float lowBound, ArrayList<Element> list){
        lowBound += matrix.convert();
        result.addAll(matrix.solveForTwoRangMatrix());
    }

    public float weightOfOptimalDecision(ArrayList<Element> list){
        return matrix.calculateWeightOfTour(list);
    }

    public static void main(String args[]){
        try{
            Solver solver = new Solver(new File("src\\main\\java\\algorithms\\littles\\resources\\test.txt"));
            System.out.println(solver.result);
            System.out.println(solver.resultLowBound);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
