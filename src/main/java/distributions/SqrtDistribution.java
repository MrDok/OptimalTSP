package distributions;

import distributions.interfaces.Distribution;

/**
 * Created by Alexander on 27.04.2016.
 */
public class SqrtDistribution implements Distribution{

    @Override
    public double cumulativeProbability(float x){
        if(x >= 1)
            return 1;

        if(x > 0 && x < 1)
            return Math.pow(x, 1.5);

        return 0;
    }

    @Override
    public double inverseCumulativeProbability(float x){
        if(x >= 1)
            return 1;

        if(x > 0 && x < 1)
            return Math.pow(x, 2/3D);

        return 0;
    }

    @Override
    public double density(float x){
        if(x > 0 && x < 1)
            return 1.5*Math.sqrt(x);

        return 0;
    }
}
