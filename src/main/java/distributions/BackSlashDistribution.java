package distributions;

import distribution_estimate.Statistics;
import distributions.interfaces.Distribution;

/**
 * Created by Alexander on 27.04.2016.
 */
public class BackSlashDistribution implements Distribution{
    @Override
    public double cumulativeProbability(float x){
        if(x >= 1)
            return 1;
        if(x > 0 && x < 1)
            return x*x;

        return 0;
    }

    @Override
    public double inverseCumulativeProbability(float x){
        if(x >= 1)
            return 1;
        if(x > 0 && x < 1)
            return Math.sqrt(x);

        return 0;
    }

    @Override
    public double density(float x){
        if(x > 0 && x < 1)
            return 2*x;

        return 0;
    }
}
