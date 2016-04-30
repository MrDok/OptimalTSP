package algorithms.littles;

/**
 * Created by Alexander on 25.04.2016.
 */

public class Element implements Comparable<Element>{

    private Float value;

    private Edge coordinates;

    private Float mark;

    public Element(Float value, Edge coordinates){
        this.value = value;
        this.coordinates = coordinates;
        this.mark = -1F;
    }

    public Element(Element element){
        this.value = element.getValue();
        this.coordinates = new Edge(element.getCoordinates());
        this.mark = element.getMark();
    }

    public Element(Float value, int x, int y){
        this.value = value;
        this.coordinates = new Edge(x, y);
        this.mark = -1F;
    }

    public Float getValue(){
        return value;
    }

    public void setValue(Float value){
        this.value = value;
    }

    public Edge getCoordinates(){
        return coordinates;
    }

    private void setCoordinates(Edge coordinates){
        this.coordinates = coordinates;
    }

    public Float getMark(){
        return mark;
    }

    public void setMark(Float mark){
        this.mark = mark;
    }

    public void markZeroing(){
        this.mark = -1F;
    }

    @Override
    public String toString(){
        return "value=" + this.value + " coordinates=" + this.coordinates;
    }

    @Override
    public int compareTo(Element o){
        return value.compareTo(o.getValue());
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Element object = (Element) o;
        return ((this.value.equals(object.getValue()) && this.coordinates.equals(object.getCoordinates())));
    }
}
