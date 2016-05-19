package thresholdk;

import algorithms.littles.Solver;
import algorithms.littles.WeightMatrix;
import algorithms.littles.exceptions.NoNumberDataException;
import algorithms.littles.exceptions.NoSolutionException;
import distribution_estimate.Statistics;
import distributions.*;
import distributions.interfaces.Distribution;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.distribution.TriangularDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Alexander on 16.05.2016.
 */
public class ThresholdKDetermine{
    private float[][] experimentalK = new float[9][6];

    public ThresholdKDetermine() throws IOException,  NoNumberDataException {
        for(int i = 0; i < 6; i++){
            WeightMatrix wm = new WeightMatrix();
            wm.fillMatrixWithRandomData(new File("src\\main\\java\\thresholdk\\resources\\uniformData" + (10 +i*2) + ".txt"));
            ArrayList<Float> data = wm.asList();

            experimentalK[0][i] = kDetermine(new UniformRealDistribution(), data);
            experimentalK[1][i] = kDetermine(new TriangularDistribution(0, 0.5, 1), data);
            experimentalK[2][i] = kDetermine(new NormalDistribution(), data);
            experimentalK[3][i] = kDetermine(new VDistribution(), data);
            experimentalK[4][i] = kDetermine(new BackSlashDistribution(), data);
            experimentalK[5][i] = kDetermine(new SlashDistribution(), data);
            experimentalK[6][i] = kDetermine(new QuadraticDistribution(), data);
            experimentalK[7][i] = kDetermine(new ExponentialDistribution(), data);
            experimentalK[8][i] = kDetermine(new SqrtDistribution(), data);
        }
    }

    private float kDetermine(AbstractRealDistribution distribution, ArrayList<Float> data) throws NoNumberDataException {
        ArrayList<Float> cloneData = null;
        cloneData = (ArrayList<Float>) data.clone();
        cloneData = Statistics.generateNumbersFromUniform(distribution, cloneData);
        WeightMatrix wm = new WeightMatrix();
        wm.fillMatrixWithRandomData(cloneData);

        Solver solver = null;
        try {
            solver = new Solver(wm);
        }catch(NoSolutionException e){
            System.out.println("нет решений для этих данных");
        }

        float optimal = solver.weightOfOptimalTour();

        int i = cloneData.size();
        try {
            for (i = cloneData.size() - 1; i >= 0; i--) {
                wm.setMaxElementToInfinity();
                Solver solver1 = new Solver(wm);

                if (optimal / solver1.weightOfOptimalTour() < 0.9524f) {
                    break;
                }
            }
        }catch (NoSolutionException e){
            return ((float) i)/cloneData.size();
        }

        return ((float) i)/cloneData.size();
    }

    private float kDetermine(Distribution distribution, ArrayList<Float> data) throws NoNumberDataException {
        ArrayList<Float> cloneData = null;
        cloneData = (ArrayList<Float>) data.clone();
        cloneData = Statistics.generateNumbersFromUniform(distribution, cloneData);
        WeightMatrix wm = new WeightMatrix();
        wm.fillMatrixWithRandomData(cloneData);

        Solver solver = null;
        try {
            solver = new Solver(wm);
        }catch(NoSolutionException e){
            System.out.println("нет решений для этих данных");
        }

        float optimal = solver.weightOfOptimalTour();

        int i = cloneData.size();
        try {
            for (i = cloneData.size() - 1; i >= 0; i--) {
                wm.setMaxElementToInfinity();
                Solver solver1 = new Solver(wm);

                if (optimal / solver1.weightOfOptimalTour() < 0.95) {
                    break;
                }
            }
        }catch (NoSolutionException e){
            return ((float) i + 1)/cloneData.size();
        }

        return ((float) i + 1)/cloneData.size();
    }

    public float[][] getExperimentalK() {
        return experimentalK;
    }

    public void outputArrayOfK(){
        for(int i = 0; i < 9; i++){
            System.out.println(Arrays.toString(experimentalK[i]));
        }
    }

    public static void main(String args[]){
        ThresholdKDetermine experiment = null;

        try {
            experiment = new ThresholdKDetermine();
        } catch (IOException e) {
            System.out.println("Что-то пошло не так");
            e.printStackTrace();
        }catch(NoNumberDataException e){
            System.out.println("Недостаточно данных");
        }

        experiment.outputArrayOfK();
    }
}
