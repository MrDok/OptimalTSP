package distribution_estimate;

import java.awt.geom.Arc2D;
import java.util.*;

/**
 * Created by Alexander on 18.04.2016.
 */
public class Statistics{

    private static final int SCALE = 2;

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
        TreeMap<Float, Float> tempCDF = new TreeMap<>();
        Float totalSum = Statistics.sum(input);
        Float tempSum = 0F;
        Float current = null;
        try{
            current = input.get(0);
        }catch(IndexOutOfBoundsException e){
            System.out.println("Array of data is empty");
        }

        if (current != null){
            for (Float item : input){
                if (current.equals(item))
                    tempSum += item;
                else{
                    tempCDF.put(current, round(tempSum/totalSum, SCALE));
                    tempSum += item;
                    current = item;
                }
            }
        }

        tempCDF.put(current, tempSum/totalSum);

        return tempCDF;
    }

    public static void main(String args[]){
        Statistics st = new Statistics();
        ArrayList<Float> temp = new ArrayList<>();
        Float[] array = {1F, 1F, 2F, 2F, 2F, 3F, 4F, 4F, 5F};
        temp.addAll(Arrays.asList(array));

        System.out.println("Sum = " + st.sum(temp));
        System.out.println(st.normalization(temp));
        System.out.println(st.calculateEDF(temp));
    }
}
