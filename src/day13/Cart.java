package day13;

import Common.Point;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Cart implements Comparable
{
    public enum Direction
    {
        up, down, left, right;

        public Direction getNextDirection( Turn turn )
        {
            Direction result = null;
            if ( turn == Turn.straight )
            {
                result = this;
            }
            else if ( turn == Turn.left && this == up || turn == Turn.right && this == down )
            {
                result = left;
            }
            else if ( turn == Turn.left && this == down || turn == Turn.right && this == up )
            {
                result = right;
            }
            else if ( turn == Turn.left && this == right || turn == Turn.right && this == left )
            {
                result = up;
            }
            else if ( turn == Turn.left && this == left || turn == Turn.right && this == right )
            {
                result = down;
            }
            return result;
        }

        public boolean isVertical()
        {
            return this == up || this == down;
        }

        public boolean isHorizontal()
        {
            return this == left || this == right;
        }
    }

    public enum Turn
    {
        left, straight, right;

        public static Turn getNextTurn( Turn turn )
        {
            Turn result = null;
            turn = turn == null ? right : turn;
            switch ( turn )
            {
            case left:
                result = straight;
                break;
            case straight:
                result = right;
                break;
            case right:
                result = left;
                break;
            }
            return result;
        }
    }

    private Point location;
    private Direction direction;
    private Turn lastTurn;

    public Cart()
    {
        this.location = null;
        this.direction = null;
        this.lastTurn = null;
    }

    public Cart( Point location, Direction direction, Turn lastTurn )
    {
        this.location = location;
        this.direction = direction;
        this.lastTurn = lastTurn;
    }

    public Cart( char c, int i, int j )
    {
        this( new Point( j, i ), getDirection( c ), null );
    }

    public void nextMove( char[][] grid )
    {
        Character currentTrack = grid[getLocation().getArrayX()][getLocation().getArrayY()];
        int x = getLocation().getX();
        int y = getLocation().getY();

        if ( currentTrack == '+' )
        {
            Turn turn = Turn.getNextTurn( getLastTurn() );
            setDirection( getDirection().getNextDirection( turn ) );
            setLastTurn( turn );
        }
        else if ( currentTrack == '\\' || currentTrack == '/' )
        {
            Turn turn = ( currentTrack == '\\' && getDirection().isVertical() || currentTrack == '/' && getDirection().isHorizontal() ) ? Turn.left : Turn.right;
            setDirection( getDirection().getNextDirection( turn ) );
        }

        if ( getDirection() == Direction.up )
        {
            y--;
        }
        else if ( getDirection() == Direction.down )
        {
            y++;
        }
        else if ( getDirection() == Direction.left )
        {
            x--;
        }
        else
        {
            x++;
        }

        getLocation().setX( x );
        getLocation().setY( y );
    }

    public static Point getCrashed( List<Cart> carts )
    {
        List<Point> locations = carts.stream().map( Cart::getLocation ).collect( Collectors.toList() );
        return carts.stream().filter( c -> Collections.frequency( locations, c.getLocation() ) > 1 ).findAny().orElse( new Cart() ).getLocation();
    }

    public static boolean hasCrashed( List<Cart> carts )
    {
        List<Point> locations = carts.stream().map( Cart::getLocation ).collect( Collectors.toList() );
        return carts.stream().anyMatch( c -> Collections.frequency( locations, c.getLocation() ) > 1 );
    }

    public static boolean hasCrashed( List<Cart> carts, Cart cart )
    {
        List<Point> locations = carts.stream().map( Cart::getLocation ).collect( Collectors.toList() );
        return Collections.frequency( locations, cart.getLocation() ) > 1;
    }

    private static Direction getDirection( char c )
    {
        Direction result = null;
        switch ( c )
        {
        case '^':
            result = Direction.up;
            break;
        case 'v':
            result = Direction.down;
            break;
        case '<':
            result = Direction.left;
            break;
        case '>':
            result = Direction.right;
            break;
        }
        return result;
    }

    public Character getDisplay()
    {
        Character result = null;
        switch ( getDirection() )
        {
        case up:
            result = '^';
            break;
        case down:
            result = 'v';
            break;
        case left:
            result = '<';
            break;
        case right:
            result = '>';
            break;
        }
        return result;
    }

    @Override
    public int compareTo( Object obj )
    {
        int result;
        Cart c2 = (Cart)obj;
        if ( getLocation().equals( c2.getLocation() ) )
        {
            result = 0;
        }
        else if ( !getLocation().getY().equals( c2.getLocation().getY() ) )
        {
            result = getLocation().getY() - c2.getLocation().getY();
        }
        else
        {
            result = getLocation().getX() - c2.getLocation().getX();
        }
        return result;
    }

    public Point getLocation()
    {
        return location;
    }

    public void setLocation( Point location )
    {
        this.location = location;
    }

    public Direction getDirection()
    {
        return direction;
    }

    public void setDirection( Direction direction )
    {
        this.direction = direction;
    }

    public Turn getLastTurn()
    {
        return lastTurn;
    }

    public void setLastTurn( Turn lastTurn )
    {
        this.lastTurn = lastTurn;
    }
}
