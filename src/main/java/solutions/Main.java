package solutions;

import algorithms.littles.Solver;
import algorithms.littles.WeightMatrix;
import algorithms.littles.exceptions.NoNumberDataException;
import algorithms.littles.exceptions.NoSolutionException;
import distributions.*;
import distributions.interfaces.Distribution;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.distribution.TriangularDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;

import java.awt.geom.Arc2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Alexander on 19.05.2016.
 */
public class Main{
    public static final int COUNT = 100;
    ArrayList<ArrayList<Data>> result;
    public Main() throws  IOException{
        result = new ArrayList<>();

        for(int i = 0; i < 1; i++){
            result.add(new ArrayList<Data>());
        }

        for(int i = 0; i < 1; i++){
            ArrayList<Float> dataFromFile = getDataFromFile(new File("src\\main\\java\\solutions\\resources\\uniform" + (10 +i*2) + ".txt"), (10 +i*2));

            this.result.get(0).add(solve(new UniformRealDistribution(0, 1), dataFromFile, (10 + i * 2)));
        }
    }

    public Data solve(Distribution dist, ArrayList<Float> data, int size){
        Data result = new Data();

        for(int i = 0; i < COUNT; i++){
            ArrayList<Float> current = new ArrayList();
            for(int j = 0; j < (size*size - size); j++){
                current.add(data.get(size*i + j));
            }

            WeightMatrix wm = new WeightMatrix();
            try {
                wm.fillMatrixWithRandomData(current);
                Solver solver = new Solver(wm);
                result.add(solver.weightOfOptimalTour());
            } catch (NoNumberDataException e) {
                System.out.println("Some errors");
            }catch(NoSolutionException e){
                System.out.println("No solutions");
            }
        }

        return result;
    }

    public Data solve(AbstractRealDistribution dist, ArrayList<Float> data, int size){
        Data result = new Data();

        int countOfElements = size*size - size;
        for(int i = 0; i < COUNT; i++){
            ArrayList<Float> current = new ArrayList();
            for(int j = 0; j < countOfElements; j++){
                current.add(data.get(countOfElements*i + j));
            }

            WeightMatrix wm = new WeightMatrix();
            try {
                wm.fillMatrixWithRandomData(current);
                Solver solver = new Solver(wm);
                result.add(solver.weightOfOptimalTour());
            } catch (NoNumberDataException e) {
                System.out.println("Some errors");
            }catch(NoSolutionException e){
                System.out.println("No solutions");
            }
        }

        return result;
    }

    public ArrayList<Float> getDataFromFile(File file, int size) throws IOException{
        ArrayList<Float> temp = new ArrayList<Float>(COUNT*(size*size - size));
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
            String[] str = br.readLine().split(" ");

            for(int i = 0; i < str.length; i++){
                temp.add(Float.valueOf(str[i]));
            }
        }

        return temp;
    }

    public void output(){
        for(ArrayList<Data> array : this.result){
            for(Data data : array){
                System.out.println(data.getData());
            }
        }
    }

    public class Data{
        ArrayList<Float> data;

        public Data() {
            data = new ArrayList<>();
        }

        public void add(Float item){
            this.data.add(item);
        }

        public ArrayList<Float> getData() {
            return data;
        }

        private void setData(ArrayList<Float> data) {
            this.data = data;
        }
    }

    public static void main(String args[]) throws IOException{
        Main main = new Main();

        main.output();
    }
}
