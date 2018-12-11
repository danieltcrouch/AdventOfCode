package day10;

import Common.Pair;
import Common.Point;

public class Star
{
    private Point position;
    private Pair velocity;

    public Star( Point position, Pair velocity )
    {
        this.position = position;
        this.velocity = velocity;
    }

    public Star( int pointX, int pointY, int velocityRight, int velocityDown )
    {
        this.position = new Point( pointX, pointY );
        this.velocity = new Pair( velocityRight, velocityDown );
    }

    public Point getPositionAtSecond( int second )
    {
        Point result = new Point( position.getX(), position.getY() );
        result.setX( result.getX() + ( second * velocity.getA() ) );
        result.setY( result.getY() + ( second * velocity.getB() ) );
        return result;
    }

    public Point getPosition()
    {
        return position;
    }

    public void setPosition( Point position )
    {
        this.position = position;
    }

    public Pair getVelocity()
    {
        return velocity;
    }

    public void setVelocity( Pair velocity )
    {
        this.velocity = velocity;
    }
}
