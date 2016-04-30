package algorithms.littles;

/**
 * Created by Alexander on 24.04.2016.
 */
public class Edge{
    private int begin;
    private int end;

    public Edge(int begin, int end){
        this.begin = begin;
        this.end = end;
    }

    public Edge(Edge edge){
        this.begin = edge.getBegin();
        this.end = edge.getEnd();
    }

    public int getBegin(){
        return begin;
    }

    public void setBegin(int begin){
        this.begin = begin;
    }

    public int getEnd(){
        return end;
    }

    public void setEnd(int end){
        this.end = end;
    }

    /*@Override
    public String toString(){
        return "from " + this.begin + " to " + this.end;
    }*/

    @Override
    public String toString(){
        return "(" + this.begin + ", " + this.end + ")" ;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (begin != edge.begin) return false;
        if (end != edge.end) return false;

        return true;
    }

    @Override
    public int hashCode(){
        int result = begin;
        result = 31 * result + end;
        return result;
    }
}
