/**
 * Created by Alexander on 02.05.2016.
 */
import com.sun.org.glassfish.external.statistics.Statistic;
import distribution_estimate.Statistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(BlockJUnit4ClassRunner.class)
public class StatisticsTest {

    public Float[] input = {1f, 1f, 1f, 1f, 2f, 3f, 3f, 3f, 4f};
    @Test
    public void calculateEDF(){
        ArrayList<Float> list = new ArrayList<>(Arrays.asList(input));
        System.out.println(Statistics.calculateEDF(list));
    }
}
