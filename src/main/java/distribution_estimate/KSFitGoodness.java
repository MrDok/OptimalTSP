package distribution_estimate;

import distributions.*;
import distributions.NormalDistribution;
import org.apache.commons.math3.special.Erf;
import org.apache.commons.math3.distribution.*;
import java.awt.geom.Arc2D;
import java.util.*;

/**
 * Created by Alexander on 20.04.2016.
 */
public class KSFitGoodness{

    public static final int UNIFORM = 0;
    public static final int TRIANGULAR = 1;
    public static final int NORMAL = 2;
    public static final int V = 3;
    public static final int BACKSLASH = 4;
    public static final int SLASH = 5;
    public static final int QUADRATIC = 6;
    public static final int EXPONENTIAL = 7;
    public static final int SQRT = 8;

    private ArrayList<Pair> criteria = new ArrayList<>(9);

    public KSFitGoodness(Distribution distribution) {
        isUniformDistribution(distribution);
        isTriangularDistribution(distribution);
        isNormalDistribution(distribution);
        isVDistribution(distribution);
        isBackSlashDistribution(distribution);
        isSlashDistribution(distribution);
        isQuadraticDistribution(distribution);
        isExponentialDistribution(distribution);
        isSqrtDistribution(distribution);
    }

    public String getBestMatch(){
        Collections.sort(criteria);

        System.out.println(criteria);

        switch(criteria.get(0).getDistribution()){
            case UNIFORM:
                return "UNIFORM";
            case TRIANGULAR:
                return "TRIANGULAR";
            case NORMAL:
                return "NORMAL";
            case V:
                return "V";
            case BACKSLASH:
                return "BACKSLASH";
            case SLASH:
                return "SLASH";
            case QUADRATIC:
                return "QUADRATIC";
            case EXPONENTIAL:
                return "EXPONENTIAL";
            case SQRT:
                return "SQRT";
            default:
                return "can't solve";
        }
    }

    public float isUniformDistribution(Distribution distribution){
        UniformRealDistribution uniform = new UniformRealDistribution(0, 1);

        float quantile = calculateQuantile(uniform, distribution);
        this.criteria.add(new Pair(quantile, 0));

        return quantile;
    }

    public float isTriangularDistribution(Distribution distribution){
        TriangularDistribution triangular = new TriangularDistribution(0, 0.5, 1);

        float quantile = calculateQuantile(triangular, distribution);
        this.criteria.add(new Pair(quantile, 1));

        return quantile;
    }

    public float isNormalDistribution(Distribution distribution){
        NormalDistribution normal = new NormalDistribution();

        float quantile = calculateQuantile(normal, distribution);
        this.criteria.add(new Pair(quantile, 2));

        return quantile;
    }

    public float isVDistribution(Distribution distribution){
        VDistribution v = new VDistribution();

        float quantile = calculateQuantile(v, distribution);
        this.criteria.add(new Pair(quantile, 3));

        return quantile;
    }

    public float isBackSlashDistribution(Distribution distribution){
        BackSlashDistribution backSlash = new BackSlashDistribution();

        float quantile = calculateQuantile(backSlash, distribution);
        this.criteria.add(new Pair(quantile, 4));

        return quantile;
    }

    public float isSlashDistribution(Distribution distribution){
        SlashDistribution slash = new SlashDistribution();

        float quantile = calculateQuantile(slash, distribution);
        this.criteria.add(new Pair(quantile, 5));

        return quantile;
    }

    public float isQuadraticDistribution(Distribution distribution){
        QuadraticDistribution quadratic = new QuadraticDistribution();

        float quantile = calculateQuantile(quadratic, distribution);
        this.criteria.add(new Pair(quantile, 6));

        return quantile;
    }

    public float isExponentialDistribution(Distribution distribution){
        distributions.ExponentialDistribution exp = new distributions.ExponentialDistribution();

        float quantile = calculateQuantile(exp, distribution);
        this.criteria.add(new Pair(quantile, 7));

        return quantile;
    }

    public float isSqrtDistribution(Distribution distribution){
        SqrtDistribution sqrt = new SqrtDistribution();

        float quantile = calculateQuantile(sqrt, distribution);
        this.criteria.add(new Pair(quantile, 8));

        return quantile;
    }

    private float calculateQuantile(AbstractRealDistribution real, Distribution test){
        float d = -1f;

        Iterator iterator = test.getEDF().getEDF().entrySet().iterator();

        float difference;
        while(iterator.hasNext()){
            Map.Entry<Float, Float> current = (Map.Entry<Float, Float>) iterator.next();
            difference =  Math.abs(current.getValue() - Statistics.round((float) real.cumulativeProbability(current.getKey()), Statistics.SCALE));

            if(difference > d)
                d = difference;
        }

        return (float) (Math.sqrt(test.getNormalData().size()) * d + 1/(6*Math.sqrt(test.getNormalData().size())));
    }

    private float calculateQuantile(distributions.interfaces.Distribution real, Distribution test){
        float d = 0f;

        Iterator iterator = test.getEDF().getEDF().entrySet().iterator();

        float difference;
        while(iterator.hasNext()){
            Map.Entry<Float, Float> current = (Map.Entry<Float, Float>) iterator.next();
            difference =  Statistics.round(Math.abs(current.getValue() - (float) real.cumulativeProbability(current.getKey())), Statistics.SCALE);

            if(difference > d)
                d = difference;
        }

        return (float) (Math.sqrt(test.getNormalData().size()) * d + 1/(6*Math.sqrt(test.getNormalData().size())));
    }



    public class Pair implements Comparable<Pair>{
        private float quantile;
        private int distribution;

        public Pair(float quantile, int distribution) {
            this.quantile = quantile;
            this.distribution = distribution;
        }

        public float getQuantile() {
            return quantile;
        }

        private void setQuantile(float quantile) {
            this.quantile = quantile;
        }

        public int getDistribution() {
            return distribution;
        }

        private void setDistribution(int distribution) {
            this.distribution = distribution;
        }

        @Override
        public int compareTo(Pair o) {
            return ((Float)quantile).compareTo(o.getQuantile());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair pair = (Pair) o;

            if (distribution != pair.distribution) return false;
            if (Float.compare(pair.quantile, quantile) != 0) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = (quantile != +0.0f ? Float.floatToIntBits(quantile) : 0);
            result = 31 * result + distribution;
            return result;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "quantile=" + quantile +
                    ", distribution=" + distribution +
                    '}';
        }
    }
}
