package day13;

import Common.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TrackService
{
    public static String findCrash( List<String> values )
    {
        int trackMaxHeight = values.size();
        int trackMaxWidth = values.stream().mapToInt( String::length ).max().getAsInt();

        List<Cart> carts = new ArrayList<>();
        char[][] grid = new char[trackMaxHeight][trackMaxWidth];
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

        printTracks( trackMaxHeight, trackMaxWidth, carts, grid );

        while ( !Cart.hasCrashed( carts ) )
        {
            carts.forEach( cart -> cart.nextMove( grid ) );
            printTracks( trackMaxHeight, trackMaxWidth, carts, grid );
        }

        return Cart.getCrashed( carts ).getLocation().toString();
    }

    private static void printTracks( int trackMaxHeight, int trackMaxWidth, List<Cart> carts, char[][] grid )
    {
        for ( int i = 0; i < trackMaxHeight; i++ )
        {
            for ( int j = 0; j < trackMaxWidth; j++ )
            {
                final Point currentPoint = new Point( j, i );
                Character toPrint = grid[i][j];
                Cart cart = carts.stream().filter( c -> c.getLocation().equals( currentPoint ) ).findFirst().orElse( null );
                toPrint = cart != null ? cart.getDisplay() : toPrint;
                System.out.print( toPrint );
            }
            System.out.println();
        }

        System.out.println();
        System.out.println( "***********************************************************************************************************" );
        System.out.println();
    }

    private static boolean isCart( char c )
    {
        return c == '<' || c == '>' || c == '^' || c == 'v';
    }
}
