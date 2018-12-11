package Common;

import java.util.Objects;
import java.util.stream.IntStream;

public class Point
{
    private Integer x;
    private Integer y;

    public Point() { }

    public Point( Integer x, Integer y )
    {
        this.x = x;
        this.y = y;
    }

    public static Point get( Integer x, Integer y )
    {
        return new Point( x, y );
    }

    public static int getManhattanDistance( Point origin, Point point )
    {
        return Math.abs( origin.getX() - point.getX() ) + Math.abs( origin.getY() - point.getY() );
    }

    public Integer getArrayX()
    {
        return getY();
    }

    public Integer getArrayY()
    {
        return getX();
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }
        Point point = (Point) o;
        return Objects.equals( getX(), point.getX() ) &&
               Objects.equals( getY(), point.getY() );
    }

    @Override
    public int hashCode()
    {

        return Objects.hash( getX(), getY() );
    }

    public Integer getX()
    {
        return x;
    }

    public void setX( Integer x )
    {
        this.x = x;
    }

    public Integer getY()
    {
        return y;
    }

    public void setY( Integer y )
    {
        this.y = y;
    }
}
