/**
 * Created by Alexander on 01.05.2016.
 */
import com.sun.org.glassfish.external.statistics.Statistic;
import distribution_estimate.Distribution;
import distribution_estimate.KSFitGoodness;
import distribution_estimate.Statistics;
import distributions.*;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.TriangularDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;


import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
/**
 * Created by Alexander on 19.04.2016.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class KSFitGoodnessTest {

    public static final int COUNT = 5;


    @Test
    public void getBestMatchForNormal() {
        NormalDistribution normal = new NormalDistribution(0.5, 0.2);

        ArrayList<Float> temp = new ArrayList<>();
        temp.addAll(Statistics.generateNumbers(normal, Statistics.COUNT));

        Distribution distribution = new Distribution(temp, true);

        KSFitGoodness kolmogorov = new KSFitGoodness(distribution);

        assertEquals("result", "NORMAL", kolmogorov.getBestMatch());
    }

    @Test
    public void getBestMatchForUniform() {
        ArrayList<Float> temp = new ArrayList<>();
        temp.addAll(Statistics.generateNumbers(new UniformRealDistribution(), Statistics.COUNT));

        Distribution distribution = new Distribution(temp, true);

        KSFitGoodness kolmogorov = new KSFitGoodness(distribution);

        assertEquals("result", "UNIFORM", kolmogorov.getBestMatch());
    }

    @Test
    public void getBestMatchForTriangular() {
        TriangularDistribution triangular = new TriangularDistribution(0, 0.5, 1);

        ArrayList<Float> temp = new ArrayList<>();
        temp.addAll(Statistics.generateNumbers(triangular, Statistics.COUNT));

        Distribution distribution = new Distribution(temp, true);

        KSFitGoodness kolmogorov = new KSFitGoodness(distribution);

        assertEquals("result", "TRIANGULAR", kolmogorov.getBestMatch());
    }

    @Test
    public void getBestMatchForSqrt() {
        SqrtDistribution sqrt = new SqrtDistribution();

        ArrayList<Float> temp = new ArrayList<>();
        temp.addAll(Statistics.generateNumbers(sqrt, Statistics.COUNT));

        Distribution distribution = new Distribution(temp, true);

        KSFitGoodness kolmogorov = new KSFitGoodness(distribution);

        assertEquals("result", "SQRT", kolmogorov.getBestMatch());

    }

    @Test
    public void getBestMatchForBackSlash() {
        BackSlashDistribution backSlash = new BackSlashDistribution();

        ArrayList<Float> temp = new ArrayList<>();
        temp.addAll(Statistics.generateNumbers(backSlash, Statistics.COUNT));

        Distribution distribution = new Distribution(temp, true);

        KSFitGoodness kolmogorov = new KSFitGoodness(distribution);

        assertEquals("result", "BACKSLASH", kolmogorov.getBestMatch());
    }

    @Test
    public void getBestMatchForSlash() {
        SlashDistribution slash = new SlashDistribution();

        ArrayList<Float> temp = new ArrayList<>();
        temp.addAll(Statistics.generateNumbers(slash, Statistics.COUNT));

        Distribution distribution = new Distribution(temp, true);

        KSFitGoodness kolmogorov = new KSFitGoodness(distribution);

        assertEquals("result", "SLASH", kolmogorov.getBestMatch());
    }

    @Test
    public void getBestMatchForV() {
        VDistribution v = new VDistribution();

        ArrayList<Float> temp = new ArrayList<>();
        temp.addAll(Statistics.generateNumbers(v, Statistics.COUNT));

        System.out.println(temp);
        Distribution distribution = new Distribution(temp, true);

        KSFitGoodness kolmogorov = new KSFitGoodness(distribution);

        assertEquals("result", "V", kolmogorov.getBestMatch());
    }

    @Test
    public void getBestMatchForQuadratic() {
        QuadraticDistribution quadratic = new QuadraticDistribution();

        ArrayList<Float> temp = new ArrayList<>();
        temp.addAll(Statistics.generateNumbers(quadratic, Statistics.COUNT));

        Distribution distribution = new Distribution(temp, true);

        KSFitGoodness kolmogorov = new KSFitGoodness(distribution);

        assertEquals("result", "QUADRATIC", kolmogorov.getBestMatch());
    }

    @Test
    public void getBestMatchForExponential() {
        ExponentialDistribution exp = new ExponentialDistribution();

        ArrayList<Float> temp = new ArrayList<>();
        temp.addAll(Statistics.generateNumbers(exp, Statistics.COUNT));

        Distribution distribution = new Distribution(temp, true);

        KSFitGoodness kolmogorov = new KSFitGoodness(distribution);

        assertEquals("result", "EXPONENTIAL", kolmogorov.getBestMatch());
    }

    public Float[] doubleFloatConvert(Double[] input){
        Float[] output = new Float[input.length];
        for(int i = 0; i < output.length; i++) {
            output[i] = input[i].floatValue();
        }

        return output;
    }
}
