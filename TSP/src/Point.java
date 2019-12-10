public class Point <E> {
    private E xValue;
    private E yValue;

    public Point(){
        xValue = null;
        yValue = null;
    }

    public Point(E x, E y){
        xValue = x;
        yValue = y;
    }

    public E getxValue() { return this.xValue; }

    public E getyValue() { return this.yValue; }

    public void setX(E x){
        xValue = x;
    }

    public void setY(E y){
        yValue = y;
    }

}
