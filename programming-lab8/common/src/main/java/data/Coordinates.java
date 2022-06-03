package data;

import java.io.Serializable;
import java.lang.Math;

public class Coordinates implements Serializable {
    private final long x;
    private final long y;

    public Coordinates(long xCoordinate, long yCoordinate){
        x=xCoordinate;
        y=yCoordinate;
    }

    public double getDistanceFromZero(){
        return Math.sqrt(x*x + y*y);
    }

    public long getX(){
        return x;
    }

    public long getY(){
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if(!this.getClass().equals(obj.getClass())){
            return false;
        }
        Coordinates cords = (Coordinates)obj;
        return this.x==cords.getX()&&this.y==cords.getY();
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}