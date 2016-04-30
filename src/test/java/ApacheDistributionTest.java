import distribution_estimate.Distribution;
import distribution_estimate.Distributions;
import javafx.scene.shape.TriangleMesh;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.TriangularDistribution;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
/**
 * Created by Alexander on 21.04.2016.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class ApacheDistributionTest{

    @Test
    public void normaDistribution(){
        ArrayList<Float> temp = new ArrayList<>();
        Float[] array = {1F, 1F, 2F, 2F, 2F, 3F, 4F, 4F, 5F};
        temp.addAll(Arrays.asList(array));

        Distribution distribution = new Distribution(temp);

        NormalDistribution normal = new NormalDistribution((double)distribution.getExpectation(),
                Math.sqrt(distribution.getVariance()));

        System.out.println(normal.cumulativeProbability(2.896036));
        System.out.println(Distributions.inverseNormalDistribution(distribution.getExpectation(),distribution.getVariance(), 0.2F ));
        assertEquals("Normal Distribution",normal.cumulativeProbability(0.2),
                Distributions.normalDistribution(distribution.getExpectation(),distribution.getVariance(), 0.2F ), 0.000001);


        TriangularDistribution tri = new TriangularDistribution(1, 2, 3);
        ExponentialDistribution exp = new ExponentialDistribution(1);
        exp.sample();
        tri.sample(5);
    }
}
