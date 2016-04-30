package distribution_estimate;

import java.util.ArrayList;

/**
 * Created by Alexander on 19.04.2016.
 */
public class Distribution{
    private ArrayList<Float> initialData;
    private ArrayList<Float> normalData;
    private EDF EDF;
    private Float expectation;
    private Float variance;


    public Distribution(ArrayList<Float> initialData){
        this.initialData = initialData;
        this.normalData = Statistics.normalization(this.initialData);
        this.expectation = Statistics.calculateExpectation(this.normalData);
        this.variance = Statistics.calculateVariance(this.normalData);
        this.EDF = new EDF(this.normalData);
    }

    public ArrayList<Float> getInitialData(){
        return initialData;
    }

    public void setInitialData(ArrayList<Float> initialData){
        this.initialData = initialData;
    }

    public ArrayList<Float> getNormalData(){
        return normalData;
    }

    private void setNormalData(ArrayList<Float> normalData){
        this.normalData = normalData;
    }

    public Float getVariance(){
        return variance;
    }

    private void setVariance(Float variance){
        this.variance = variance;
    }

    public Float getExpectation(){
        return expectation;
    }

    private void setExpectation(Float expectation){
        this.expectation = expectation;
    }

    public EDF getEDF(){
        return EDF;
    }

    private void setEDF(EDF EDF){
        this.EDF = EDF;
    }
}
