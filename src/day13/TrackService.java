package day13;

import Common.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TrackService
{
    //Puzzle 2
    //2,84
    public static String findFinalCart( List<String> values )
    {
        int trackMaxHeight = values.size();
        int trackMaxWidth = values.stream().mapToInt( String::length ).max().getAsInt();

        List<Cart> carts = new ArrayList<>();
        char[][] grid = new char[trackMaxHeight][trackMaxWidth];
        initializeTracks( values, trackMaxHeight, trackMaxWidth, carts, grid );

        int tick = 0;
        do
        {
            //printTracks( trackMaxHeight, trackMaxWidth, carts, grid, tick );
            List<Cart> cartsToRemove = new ArrayList<>();
            Collections.sort( carts );
            for ( Cart cart : carts )
            {
                cart.nextMove( grid );
                if ( Cart.hasCrashed( carts, cart ) )
                {
                    cartsToRemove.addAll( carts.stream().filter( c -> c.getLocation().equals( cart.getLocation() ) ).collect( Collectors.toList() ) );
                }
            }
            carts.removeAll( cartsToRemove );
            tick++;
        } while ( carts.size() > 1 );

        //printTracks( trackMaxHeight, trackMaxWidth, carts, grid, tick );
        return carts.get( 0 ).getLocation() + " - " + tick;
    }

    private static void initializeTracks( List<String> values, int trackMaxHeight, int trackMaxWidth, List<Cart> carts, char[][] grid )
    {
        for ( int i = 0; i < trackMaxHeight; i++ )
        {
            for ( int j = 0; j < trackMaxWidth; j++ )
            {
                String temp = values.get( i );
                grid[i][j] = temp.length() > j ? temp.charAt( j ) : ' ';

                if ( isCart( grid[i][j] ) )
                {
                    carts.add( new Cart( grid[i][j], i, j ) );
                    grid[i][j] = carts.get( carts.size() - 1 ).getDirection().isVertical() ? '|' : '-';
                }
            }
        }
    }

    //Puzzle 1
    //76,108
    public static String findCrash( List<String> values )
    {
        int trackMaxHeight = values.size();
        int trackMaxWidth = values.stream().mapToInt( String::length ).max().getAsInt();

        List<Cart> carts = new ArrayList<>();
        char[][] grid = new char[trackMaxHeight][trackMaxWidth];
        initializeTracks( values, trackMaxHeight, trackMaxWidth, carts, grid );

        int tick = 0;
        boolean hasCrashed = false;
        do
        {
            //printTracks( trackMaxHeight, trackMaxWidth, carts, grid, tick );
            Collections.sort( carts );
            for ( Cart cart : carts )
            {
                cart.nextMove( grid );
                if ( Cart.hasCrashed( carts ) )
                {
                    hasCrashed = true;
                    break;
                }
            }
            tick++;
        } while ( !hasCrashed );

        //printTracks( trackMaxHeight, trackMaxWidth, carts, grid, tick );
        return Cart.getCrashed( carts ) + " - " + tick;
    }

    private static void printTracks( int trackMaxHeight, int trackMaxWidth, List<Cart> carts, char[][] grid, int tick )
    {
        Point crash = Cart.getCrashed( carts );

        for ( int i = 0; i < trackMaxHeight; i++ )
        {
            for ( int j = 0; j < trackMaxWidth; j++ )
            {
                final Point currentPoint = new Point( j, i );
                Character toPrint = grid[i][j];
                Cart cart = carts.stream().filter( c -> c.getLocation().equals( currentPoint ) ).findFirst().orElse( null );
                toPrint = cart != null ? cart.getDisplay() : toPrint;
                toPrint = currentPoint.equals( crash ) ? 'X' : toPrint;
                System.out.print( toPrint );
            }
            System.out.println();
        }

        System.out.println();
        System.out.println( "********************************************************" + tick + "***************************************************" );
        System.out.println();
    }

    private static boolean isCart( char c )
    {
        return c == '<' || c == '>' || c == '^' || c == 'v';
    }
}
