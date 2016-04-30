package distributions.interfaces;

/**
 * Created by Alexander on 30.04.2016.
 */
public interface Distribution{

    double cumulativeProbability(float x);

    double inverseCumulativeProbability(float x);

    double density(float x);
}
