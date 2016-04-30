import distribution_estimate.Distribution;
import distribution_estimate.Distributions;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
/**
 * Created by Alexander on 19.04.2016.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class DistributionTest{

    @Test
    public void EDF(){
        ArrayList<Float> temp = new ArrayList<>();
        Float[] array = {1F, 1F, 2F, 2F, 2F, 3F, 4F, 4F, 5F};
        temp.addAll(Arrays.asList(array));

        Distribution distribution = new Distribution(temp);

        System.out.println(distribution.getEDF().getEDF());
        assertEquals("Получение значения эмпирической функции", 0F, distribution.getEDF().getValue(0.1F), 0);
    }

    @Test
    public void isUniformDistribution(){
        ArrayList<Float> temp = new ArrayList<>();
        Float[] array = {1F, 1F, 2F, 2F, 2F, 3F, 4F, 4F, 5F};
        temp.addAll(Arrays.asList(array));
        Distribution distribution = new Distribution(temp);

        Distributions distributions = new Distributions();

        System.out.println(Math.pow(8, 1/3D));
        System.out.println(distributions.isUniformDistribution(distribution.getEDF()));
    }
}
