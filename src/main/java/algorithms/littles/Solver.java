package algorithms.littles;

import algorithms.littles.exceptions.NoSolutionException;
import distribution_estimate.Statistics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Alexander on 27.04.2016.
 */
public class Solver{
    private WeightMatrix matrix;
    private float resultLowBound;
    private ArrayList<Element> result;

    public Solver(WeightMatrix matrix) throws NoSolutionException{
        try {
            this.matrix = (WeightMatrix) matrix.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        result = new ArrayList<>();
        resultLowBound = 0f;
        ArrayList<Element> list = new ArrayList<>();

        float lowBound = 0f;
        lowBound += this.matrix.convert();

        solve(this.matrix, lowBound, list);
    }

    public Solver(File file) throws IOException, NoSolutionException{
        matrix = new WeightMatrix();
        result = new ArrayList<>();
        matrix.fillMatrix(file);
        resultLowBound = 0f;
        ArrayList<Element> list = new ArrayList<>();

        float lowBound = 0f;

        lowBound += matrix.convert();

        solve(matrix, lowBound, list);
    }

    public boolean solve(WeightMatrix wm, float lowBound, ArrayList<Element> list) throws NoSolutionException{
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

            /*float lowBound1 = Statistics.round(wm1.convert(), Statistics.SCALE);
            float lowBound2 = Statistics.round(wm2.convert(), Statistics.SCALE);*/

            float lowBound1 = wm1.convert();
            float lowBound2 = wm2.convert();

            boolean check = false;
            if(lowBound1 <= lowBound2){
                ArrayList<Element> cloneList = (ArrayList<Element>)list.clone();
                cloneList.add(new Element(maxEstimate));
                check = solve(wm1, lowBound + lowBound1, cloneList);
            }
            if(!check){
                check = solve(wm2, lowBound + lowBound2, list);
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
            lowBound = Statistics.round(lowBound, Statistics.SCALE);
            listClone.addAll(wmClone.solveForTwoRangMatrix());

            if(Statistics.round(weightOfOptimalDecision(listClone), Statistics.SCALE) <= lowBound) {
                this.resultLowBound = lowBound;
                this.result.addAll(listClone);
                return true;
            }
            else{
                return false;
            }
        }
    }

    private float weightOfOptimalDecision(ArrayList<Element> list){
        return matrix.calculateWeightOfTour(list);
    }

    public float weightOfOptimalTour(){
        return matrix.calculateWeightOfTour(result);
    }

    public WeightMatrix getMatrix() {
        return matrix;
    }

    private void setMatrix(WeightMatrix matrix) {
        this.matrix = matrix;
    }

    public float getResultLowBound() {
        return resultLowBound;
    }

    private void setResultLowBound(float resultLowBound) {
        this.resultLowBound = resultLowBound;
    }

    public ArrayList<Element> getResult() {
        return result;
    }

    private void setResult(ArrayList<Element> result) {
        this.result = result;
    }

    public static void main(String args[]){
        try{
            Solver solver = new Solver(new File("src\\main\\java\\algorithms\\littles\\resources\\test2.txt"));
            System.out.println(solver.result);
            System.out.println(solver.resultLowBound);
        }catch (IOException e){
            e.printStackTrace();
        }catch (NoSolutionException e){
            System.out.println("some error");
        }
    }

}
