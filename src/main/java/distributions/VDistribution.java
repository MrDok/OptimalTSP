package distributions;

import distributions.interfaces.Distribution;

/**
 * Created by Alexander on 27.04.2016.
 */
public class VDistribution implements Distribution{
    @Override
    public double cumulativeProbability(float x){
        if(x >= 1)
            return 1;

        if(x > 0 && x <= 0.5)
            return 2*(x - x*x);

        if(x > 0.5 && x < 1)
            return 1 - 2*x + 2*x*x;

        return 0;
    }

    @Override
    public double inverseCumulativeProbability(float x){
        if(x >= 1)
            return 1;

        if(x > 0 && x <= 0.5)
            return 0.5 - 0.5*Math.sqrt(1 - 2*x);

        if(x > 0.5 && x < 1)
            return 0.5 + 0.5*Math.sqrt(2*x - 1);

        return 0;
    }

    @Override
    public double density(float x){
        if(x >= 1)
            return 0;

        if(x > 0 && x <= 0.5)
            return 2*(1 -2*x);

        if(x > 0.5 && x < 1)
            return 2*(2*x - 1);

        return 0;
    }
}
