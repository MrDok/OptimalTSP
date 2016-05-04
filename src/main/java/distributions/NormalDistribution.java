package distributions;

import distributions.interfaces.Distribution;
import org.apache.commons.math3.special.Erf;

/**
 * Created by Alexander on 04.05.2016.
 */
public class NormalDistribution implements Distribution {

    @Override
    public double cumulativeProbability(float x) {
        if(x >= 1)
            return 1;
        if(x > 0 && x < 1) {
            return 0.5 - 0.506288*Erf.erf(1.76777 - 3.53554*x);
        }

        return 0;
    }

    @Override
    public double inverseCumulativeProbability(float x) {
        if(x >= 1)
            return 1;
        if(x > 0 && x < 1) {
            return 0.5 - 0.282843*Erf.erfInv(0.98758 - 1.97516*x);
        }

        return 0;
    }

    @Override
    public double density(float x) {
        if(x > 0 && x < 1) {
            return 2.01979* Math.exp(-12.5 * Math.pow(x - 0.5, 2));
        }

        return 0;
    }
}
