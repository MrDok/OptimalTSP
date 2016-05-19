package algorithms.littles;

import algorithms.littles.exceptions.NoNumberDataException;
import algorithms.littles.exceptions.NoSolutionException;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Alexander on 1.04.2016.
 */

/**
 * This class is the matrix of weights
 * Also this class store reserve matrix to calculate the weight of optimal decision
 */
public class WeightMatrix implements Cloneable{
    private ArrayList<ArrayList<Element>> matrix;
    private ArrayList<ArrayList<Element>> reserveMatrix;

    public WeightMatrix(){
        this.matrix = new ArrayList<>();
        this.reserveMatrix = new ArrayList<>();
    }

    /**
     *
     * @param file file which contains square matrix of weights, split by ' '
     * @throws IOException
     */
    public void fillMatrix(File file) throws IOException{
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
            int j = -1;
            String line;
            while ((line = br.readLine()) != null){
                String[] values = line.split(" ");

                ArrayList<Element> tempList = new ArrayList<>(values.length);
                ArrayList<Element> tempReserveMatrixList = new ArrayList<>(values.length);
                ++j;

                for (int i = 0; i < values.length; i++){
                    if(i != j){
                        tempList.add(new Element(Float.valueOf(values[i]), j, i));
                        tempReserveMatrixList.add(new Element(Float.valueOf(values[i]), j, i));
                    }
                    else{
                        tempList.add(new Element(Float.POSITIVE_INFINITY, j, i));
                        tempReserveMatrixList.add(new Element(Float.POSITIVE_INFINITY, j, i));
                    }
                }

                matrix.add(tempList);
                reserveMatrix.add(tempReserveMatrixList);
            }
        }
    }

    /**
     * fill matrix with data, split by ','
     * @param file input file
     * @throws IOException
     * @throws Exception
     */
    public void fillMatrixWithRandomData(File file) throws NoNumberDataException, IOException{
        ArrayList<String> data = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
            String line;
            while ((line = br.readLine()) != null){
                data.addAll(Arrays.asList(line.split(",")));
            }
        }

        double matrixSize = Math.sqrt((double) data.size() + 0.25) + 0.5;

        if(matrixSize % 1 <= 0) {
            for (int i = 0; i < matrixSize; i++) {
                ArrayList<Element> tempMatrixList = new ArrayList<>((int) matrixSize);
                ArrayList<Element> tempReserveMatrixList = new ArrayList<>((int) matrixSize);
                for (int j = 0; j < matrixSize - 1; j++) {
                    tempMatrixList.add(new Element(Float.valueOf(data.get((int) (i * (matrixSize - 1)) + j)), i, j));
                    tempReserveMatrixList.add(new Element(Float.valueOf(data.get((int) (i * (matrixSize - 1)) + j)), i, j));
                }
                tempMatrixList.add(i, new Element(Float.POSITIVE_INFINITY, i, i));
                tempReserveMatrixList.add(i, new Element(Float.POSITIVE_INFINITY, i, i));

                matrix.add(tempMatrixList);
                reserveMatrix.add(tempReserveMatrixList);
            }
        }else{
            throw new NoNumberDataException();
        }
    }

    public void fillMatrixWithRandomData(ArrayList<Float> data) throws NoNumberDataException{
        double matrixSize = Math.sqrt((double) data.size() + 0.25) + 0.5;

        if(matrixSize % 1 <= 0) {
            for (int i = 0; i < matrixSize; i++) {
                ArrayList<Element> tempMatrixList = new ArrayList<>();
                ArrayList<Element> tempReserveMatrixList = new ArrayList<>();
                for (int j = 0; j < i; j++) {
                    tempMatrixList.add(new Element(data.get((int) (i * (matrixSize - 1)) + j), i, j));
                    tempReserveMatrixList.add(new Element(data.get((int) (i * (matrixSize - 1)) + j), i, j));
                }

                tempMatrixList.add(i, new Element(Float.POSITIVE_INFINITY, i, i));
                tempReserveMatrixList.add(i, new Element(Float.POSITIVE_INFINITY, i, i));

                for (int j = i+1; j < matrixSize; j++) {
                    tempMatrixList.add(new Element(data.get((int) (i * (matrixSize - 1)) + j - 1), i, j));
                    tempReserveMatrixList.add(new Element(data.get((int) (i * (matrixSize - 1)) + j - 1), i, j));
                }

                matrix.add(tempMatrixList);
                reserveMatrix.add(tempReserveMatrixList);
            }
        }else{
            throw new NoNumberDataException();
        }
    }

    public ArrayList<Float> asList(){
        ArrayList<Float> result = new ArrayList<>(matrix.size()*(matrix.size() - 1));

        for(ArrayList<Element> list: matrix){
            for(Element element: list){
                if(!element.getValue().equals(Float.POSITIVE_INFINITY)){
                    result.add(element.getValue());
                }
            }
        }

        return result;
    }

    public void output(){
        for (ArrayList<Element> list: matrix){
            System.out.println(list);
        }
    }

    /**
     * convert matrix
     * @return low bound of optimal decision
     */
    public float convert() throws NoSolutionException{
        float estimation = 0;
        for(int i = 0; i < matrix.size(); i++){
            float min = Collections.min(matrix.get(i)).getValue();

            if(min == Float.POSITIVE_INFINITY)
                throw new NoSolutionException();

            for(int j = 0; j < matrix.get(i).size(); j++){
                matrix.get(i).get(j).setValue(matrix.get(i).get(j).getValue() - min);
            }
            estimation += min;
        }

        transpose();

        for(int i = 0; i < matrix.size(); i++){
            float min = Collections.min(matrix.get(i)).getValue();

            if(min == Float.POSITIVE_INFINITY)
                throw new NoSolutionException();

            if(min > 0){
                for (int j = 0; j < matrix.get(i).size(); j++){
                    matrix.get(i).get(j).setValue(matrix.get(i).get(j).getValue() - min);
                }
            }

            estimation += min;
        }

        transpose();
        return estimation;
    }

    public void transpose(){
        for(int i = 0; i < matrix.size(); i++){
            for(int j = i; j < matrix.get(i).size(); j++){
                Element temp = matrix.get(i).get(j);
                matrix.get(i).set(j, matrix.get(j).get(i));
                matrix.get(j).set(i, temp);
            }
        }
    }

    /**
     *
     * @param o element in matrix which contains original coordinates even some rows or columns were deleted
     * @return real coordinates in current state of matrix
     */
    public Edge getRealPosition(Element o){
        for(int i = 0; i < matrix.size(); i++){
            ArrayList<Element> currentArray = matrix.get(i);
            for(int j = 0; j < currentArray.size(); j++){
                if(currentArray.get(j).equals(o)){
                    return new Edge(i, j);
                }
            }
        }
        return null;
    }

    /**
     * Finds row which not contains Infinity value
     * @return row of matrix. If return == -1 then matrix doesn't contains row without Infinity
     */
    public int getRowWithoutInfinity(){
        boolean flag;
        for (int i = 0; i < matrix.size(); i++){
            ArrayList<Element> currentArray = matrix.get(i);

            flag = true;
            for (int j = 0; j < currentArray.size(); j++){
                if (currentArray.get(j).getValue().equals(Float.POSITIVE_INFINITY)){
                    flag = false;
                    break;
                }
            }
            if (flag)
                return i;
        }
        return -1;
    }

    /**
     * Set Infinity value in place where row and column doesn't contains Infinity
     */
    public void setNeedInfinity(){
        int row;
        int column;

        row = getRowWithoutInfinity();
        transpose();
        column = getRowWithoutInfinity();
        transpose();

        if(row != -1 && column != -1)
            matrix.get(row).get(column).setValue(Float.POSITIVE_INFINITY);
    }

    public void setNeedInfinity(Element element){
        Edge real = getRealPosition(element);
        matrix.get(real.getEnd()).get(real.getBegin()).setValue(Float.POSITIVE_INFINITY);
    }

    public void setMaxElementToInfinity(){
        Element max = new Element(0f, 0, 0);

        for(int i = 0; i < matrix.size(); i++){
            for(int j = 0; j < matrix.get(i).size(); j++){
                if(!matrix.get(i).get(j).getValue().equals(Float.POSITIVE_INFINITY) && !matrix.get(i).get(j).getValue().equals(9999f)
                        && matrix.get(i).get(j).getValue() > max.getValue()){
                    max = matrix.get(i).get(j);
                }
            }
        }

        this.matrix.get(max.getCoordinates().getBegin()).get(max.getCoordinates().getEnd()).setValue(9999f);
        //this.setInfinity(max);
    }

    /**
     * Set infinity to the Element
     * @param element input Element of the matrix
     */
    public void setInfinity(Element element){
        Edge real = this.getRealPosition(element);
        matrix.get(real.getBegin()).get(real.getEnd()).setValue(Float.POSITIVE_INFINITY);
    }

    /**
     * Delete row and column with center of coord
     * @param coord input Edge
     */
    public void deleteRowAndColumn(Edge coord){
        matrix.remove(coord.getBegin());

        for (int i = 0; i < matrix.size(); i++){
            matrix.get(i).remove(coord.getEnd());
        }
    }

    /**
     * Delete row and column with center of element
     * @param element input Element
     */
    public void deleteRowAndColumn(Element element){
        Edge real = this.getRealPosition(element);
        matrix.remove(real.getBegin());

        for (int i = 0; i < matrix.size(); i++){
            matrix.get(i).remove(real.getEnd());
        }

        setNeedInfinity();
    }

    /**
     *
     * @return rang of the matrix
     */
    public int rang(){
        return matrix.size();
    }

    /**
     * get minimum of the row elements which contains Element
     * @param element input Element
     * @return float minimum
     */
    public float getMinInRow(Element element){
        Edge real = getRealPosition(element);

        ArrayList<Element> list = matrix.get(real.getBegin());
        float min = Collections.max(list).getValue();

        for(int i = 0; i < real.getEnd(); i++){
            if(list.get(i).getValue() < min)
                min = list.get(i).getValue();
        }

        for(int i = real.getEnd() + 1; i < list.size(); i++){
            if(list.get(i).getValue() < min)
                min = list.get(i).getValue();
        }

        return min;
    }

    /**
     * get minimum of the column elements which contains Element
     * @param element input Element
     * @return float minimum
     */
    public float getMinInColumn(Element element){
        Edge real = getRealPosition(element);

        float min = Float.POSITIVE_INFINITY;

        for(int i = 0; i < real.getBegin(); i++){
            if(matrix.get(i).get(real.getEnd()).getValue() < min){
                min = matrix.get(i).get(real.getEnd()).getValue();
            }
        }

        for(int i = real.getBegin() + 1; i < matrix.size(); i++){
            if(matrix.get(i).get(real.getEnd()).getValue() < min){
                min = matrix.get(i).get(real.getEnd()).getValue();
            }
        }

        return  min;
    }

    /**
     * Calculate mark of the element
     * @param element input Element
     * @return sum of minimum elements of row and column
     */
    public float calculateMark(Element element){
        float minInRow = getMinInRow(element);
        float minInColumn = getMinInColumn(element);
/*        minInRow = (minInRow == Float.POSITIVE_INFINITY) ? 0f : minInRow;
        minInColumn = (minInColumn == Float.POSITIVE_INFINITY) ? 0f : minInColumn;*/

        return minInRow + minInColumn;
    }

    /**
     * Calculate estimation of zero element in matrix;
     * @return maximum estimation zero element
     */
    public Element estimation(){
        ArrayList<Element> estimationList = new ArrayList<>();

        for(int i = 0; i < matrix.size(); i++){
            ArrayList<Element> list = matrix.get(i);
            for(int j = 0; j < list.size(); j++){
                if(list.get(j).getValue().equals(0F)){
                    list.get(j).setMark(calculateMark(list.get(j)));
                    estimationList.add(list.get(j));
                }
            }
        }

        Element max = estimationList.get(0);

        for(int i = 0; i < estimationList.size(); i++){
            if(estimationList.get(i).getMark() > max.getMark())
                max = estimationList.get(i);
        }

        return max;
    }

    /**
     * solve for 2 rang matrix
     * @return ArrayList of two last elements which belongs to the optimal decision
     */
    public ArrayList<Element> solveForTwoRangMatrix(){
        ArrayList<Element> result = new ArrayList<>(2);
        if(matrix.size() != 2)
            throw new RuntimeException("Размер матрицы больше двух");
        else{
            for(int i = 0; i < 2; i++){
                for(int j = 0; j < matrix.get(i).size(); j++){
                    if(!matrix.get(i).get(j).getValue().equals(Float.POSITIVE_INFINITY))
                        result.add(matrix.get(i).get(j));
                }
            }
            return result;
        }
    }

    /**
     *
     * @param tour ArrayList of Element
     * @return weight of input tour
     */
    public float calculateWeightOfTour(ArrayList<Element> tour){
        float sum = 0f;
        for(Element element: tour){
            sum += reserveMatrix.get(element.getCoordinates().getBegin()).get(element.getCoordinates().getEnd()).getValue();
        }

        return sum;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        WeightMatrix clone = new WeightMatrix();

        for(ArrayList<Element> arrayList: matrix){
            ArrayList<Element> cloneArrayList = new ArrayList<>();

            for(Element element: arrayList){
                cloneArrayList.add((Element) element.clone());
            }

            clone.matrix.add(cloneArrayList);
        }

        for(ArrayList<Element> arrayList: reserveMatrix){
            ArrayList<Element> cloneArrayList = new ArrayList<>();

            for(Element element: arrayList){
                cloneArrayList.add((Element) element.clone());
            }

            clone.reserveMatrix.add(cloneArrayList);
        }

        return clone;
    }

}
