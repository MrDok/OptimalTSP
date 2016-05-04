package distributions;

import distributions.interfaces.Distribution;

/**
 * Created by Alexander on 01.05.2016.
 */
public class ExponentialDistribution implements Distribution{
    @Override
    public double cumulativeProbability(float x) {
        if(x >= 1)
            return 1;
        if(x > 0 && x < 1) {
            return (2*Math.pow(Math.E, 2 - x))*Math.sinh(x)/(Math.pow(Math.E, 2) - 1);
        }

        return 0;
    }

    @Override
    public double inverseCumulativeProbability(float x) {
        if(x >= 1)
            return 1;

        double e = Math.E;
        if(x > 0 && x < 1) {
            return Math.log(e/(Math.sqrt(Math.pow(e, 2)*(1 - x) + x)));
        }

        return 0;
    }

    @Override
    public double density(float x) {
        if(x >= 1)
            return 1;
        if(x > 0 && x < 1) {
            double exp = Math.pow(Math.E, 2);
            return (2*exp/(exp-1))* Math.pow(Math.E, -2*x);
        }

        return 0;
    }
}
