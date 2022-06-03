package data;

public class Coordinates {
    private final long x;
    private final long y;

    public Coordinates(long xCoordinate, long yCoordinate){
        x=xCoordinate;
        y=yCoordinate;
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
}