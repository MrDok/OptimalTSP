package distribution_estimate;

import algorithms.littles.Element;
import org.apache.commons.math3.distribution.AbstractRealDistribution;

import java.awt.geom.Arc2D;
import java.io.*;
import java.util.*;

/**
 * Created by Alexander on 18.04.2016.
 */
public class Statistics{
    public static final int SCALE = 2;
    public static final int COUNT = 5;

    public Statistics(){
    }

    public static Float calculateExpectation(ArrayList<Float> input){
        Float sum = 0F;
        for(Float item : input){
            sum +=item;
        }
        return sum/input.size();
    }

    public static Float calculateVariance(ArrayList<Float> input){
        double tempDX = 0D;
        for(Float item: input){
            tempDX += Math.sqrt(Math.pow(item - calculateExpectation(input), 2));
        }

        return (float) tempDX/input.size();
    }

    public static Float sum(List<Float> list){
        if (list.size() == 0)
            return -1F;

        Float sum = 0F;
        for(Float item: list){
            sum += item;
        }
        return sum;
    }

    public static ArrayList<Float> normalization(ArrayList<Float> input){
        ArrayList<Float> result = (ArrayList<Float>) input.clone();
        Collections.sort(result);
        try{
            Float max = input.get(result.size() - 1);
            Float min = input.get(0);
            for (int i = 0; i< result.size(); i++){
                result.set(i, (result.get(i) - min)/(max - min));
            }

            return result;
        }catch(IndexOutOfBoundsException e){
            System.out.println("List is empty");

            return input;
        }
    }

    public static float round(float value, int scale) {
        return (float) (Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale));
    }

    public static TreeMap<Float, Float> calculateEDF(ArrayList<Float> input){
        Collections.sort(input);
        TreeMap<Float, Float> tempCDF = new TreeMap<>();
        Float totalCount = (float)input.size();
        Float tempCount = 0F;
        Float current = null;
        try{
            current = input.get(0);
        }catch(IndexOutOfBoundsException e){
            System.out.println("Array of data is empty");
        }

        if (current != null){
            //tempCDF.put(round(current, SCALE), 0.0F);
            for (Float item : input){
                if (current.equals(item))
                    tempCount ++;
                else{
                    tempCDF.put(round(current, SCALE), round(tempCount/totalCount, SCALE));
                    tempCount ++;
                    current = item;
                }
            }
            tempCDF.put(round(current, SCALE), round(tempCount/totalCount, SCALE));
        }
        return tempCDF;
    }

    public static ArrayList<Float> getUniformData(File file) throws IOException {
        ArrayList<Float> tempList = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
            String line;
            while ((line = br.readLine()) != null){
                String[] values = line.split(",");

                for(int i = 0; i < values.length; i++) {
                    tempList.add(Float.valueOf(values[i]));
                }
            }
        }

        return tempList;
    }

    public static ArrayList<Float> generateNumbers(distributions.interfaces.Distribution distribution, int count){
        ArrayList<Float> distrArray = null;
        try {
            distrArray = getUniformData(new File("src\\main\\java\\distribution_estimate\\resources\\uniform_distribution_numbers.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < distrArray.size(); i++){
            distrArray.set(i, round((float) distribution.inverseCumulativeProbability(distrArray.get(i)), Statistics.SCALE));
        }

        return distrArray;
    }

    public static ArrayList<Float> generateNumbers(AbstractRealDistribution distribution, int count){
        ArrayList<Float> distrArray = null;
        try {
            distrArray = getUniformData(new File("src\\main\\java\\distribution_estimate\\resources\\uniform_distribution_numbers.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < distrArray.size(); i++){
            distrArray.set(i, round((float) distribution.inverseCumulativeProbability(distrArray.get(i)), Statistics.SCALE));
        }

        return distrArray;
    }

    public static Float[] doubleFloatConvert(Double[] input){
        Float[] output = new Float[input.length];
        for(int i = 0; i < output.length; i++) {
            output[i] = input[i].floatValue();
        }

        return output;
    }
}
