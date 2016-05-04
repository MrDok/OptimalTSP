import distribution_estimate.Distribution;
import distribution_estimate.KSFitGoodness;
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
public class DistributionTest{

    @Test
    public void EDF(){
        ArrayList<Float> temp = new ArrayList<>();
        Float[] array = {1F, 1F, 2F, 2F, 2F, 3F, 4F, 4F, 5F};
        temp.addAll(Arrays.asList(array));

        Distribution distribution = new Distribution(temp, false);

        System.out.println(distribution.getEDF().getEDF());
        assertEquals("Получение значения эмпирической функции", 0F, distribution.getEDF().getValue(0.1F), 0);
    }

    @Test
    public void isUniformDistribution(){
        ArrayList<Float> temp = new ArrayList<>();
        Float[] array = {0.939041f, 0.485299f, 0.351732f, 0.290623f, 0.35019f, 0.994545f, 0.424981f, 0.267586f, 0.637879f, 0.904572f};
        temp.addAll(Arrays.asList(array));
        Distribution distribution = new Distribution(temp, false);

        KSFitGoodness distributions = new KSFitGoodness(distribution);

        System.out.println(distributions.isUniformDistribution(distribution));
        System.out.println(distribution.getEDF().getEDF());
    }
}
