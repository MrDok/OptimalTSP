package distribution_estimate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Alexander on 19.04.2016.
 */
public class EDF{
    TreeMap<Float, Float> EDF;

    public EDF(ArrayList<Float> input){
        EDF = Statistics.calculateEDF(input);
    }

    public TreeMap<Float, Float> getEDF(){
        return EDF;
    }

    public float getValue(Float x){
        Float result = 0F;

        Iterator iterator = this.EDF.entrySet().iterator();

        while(iterator.hasNext()){
            Map.Entry<Float, Float> current = (Map.Entry<Float, Float>) iterator.next();
            if(current.getKey() >= x)
                return result;
            else{
                result = current.getValue();
            }
        }
        return 1F;
    }


}
