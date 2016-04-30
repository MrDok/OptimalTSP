package distribution_estimate;

import org.apache.commons.math3.special.Erf;
import org.apache.commons.math3.distribution.*;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Alexander on 20.04.2016.
 */
public class Distributions{

    public static float uniformDistribution(float a, float b, float value){

        return (value - a)/(b-a);
    }

    public static float normalDistribution(float ex, float dx, float value){
        return (float) (0.5D*(1D + Erf.erf((value - ex)/(Math.sqrt(2*dx)))));
    }

    public static float inverseNormalDistribution(float ex, float dx, float value){
        return (float) (1/((0.5D*(1D + Erf.erf((value - ex)/(Math.sqrt(2*dx)))))));
    }

    public float isUniformDistribution(EDF edf){

        Iterator iterator = edf.getEDF().entrySet().iterator();
        float aUniform = edf.getEDF().firstKey();
        float bUniform = edf.getEDF().lastKey();

        ArrayList<Float> criteria = new ArrayList<>(5);
        criteria.add(0F);
        float difference;
        while(iterator.hasNext()){
            Map.Entry<Float, Float> current = (Map.Entry<Float, Float>) iterator.next();
            difference = Math.abs(current.getValue() - uniformDistribution(aUniform, bUniform, current.getKey()));
            if (difference > criteria.get(0))
                criteria.set(0, difference);
        }

        criteria.set(0, (float) Math.sqrt(edf.getEDF().size()) * criteria.get(0));

        return criteria.get(0);
    }
}
