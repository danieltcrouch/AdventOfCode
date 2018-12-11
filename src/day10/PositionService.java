package day10;

import Common.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PositionService
{
    public static String displaySeconds( List<String> values )
    {
        List<Star> stars =
            values.stream()
            .map( s -> Arrays.asList( Integer.parseInt( s.split( "velocity" )[0].split( "," )[0].replaceAll( "[^\\d.-]", "" ) ),
                                      Integer.parseInt( s.split( "velocity" )[0].split( "," )[1].replaceAll( "[^\\d.-]", "" ) ),
                                      Integer.parseInt( s.split( "velocity" )[1].split( "," )[0].replaceAll( "[^\\d.-]", "" ) ),
                                      Integer.parseInt( s.split( "velocity" )[1].split( "," )[1].replaceAll( "[^\\d.-]", "" ) ) ) )
            .map( list -> new Star( list.get( 0 ), list.get( 1 ), list.get( 2 ), list.get( 3 ) ) )
            .collect( Collectors.toList() );

        int second = 0;

        int previousSpan;
        int span = Integer.MAX_VALUE;
        boolean isSpanDecreasing = true;
        List<Point> currentStarsPositions = new ArrayList<>();
        do
        {
            second++;
            for ( Star star : stars )
            {
                currentStarsPositions.add( star.getPositionAtSecond( second ) );
            }

            previousSpan = span;
            span = currentStarsPositions.stream().mapToInt( Point::getX ).max().getAsInt() -
                   currentStarsPositions.stream().mapToInt( Point::getX ).min().getAsInt();

            currentStarsPositions.clear();
            if ( span >= previousSpan )
            {
                isSpanDecreasing = false;
            }
        } while ( isSpanDecreasing );

        int finalTime = second - 1;
        for ( Star star : stars )
        {
            currentStarsPositions.add( star.getPositionAtSecond( finalTime ) );
        }
        printStars( currentStarsPositions );

        return "Check for message - Time: " + finalTime;
    }

    private static void printStars( List<Point> stars )
    {
        int horizontal = stars.stream().mapToInt( Point::getX ).max().getAsInt() -
                         stars.stream().mapToInt( Point::getX ).min().getAsInt();
        int vertical =   stars.stream().mapToInt( Point::getY ).max().getAsInt() -
                         stars.stream().mapToInt( Point::getY ).min().getAsInt();
        char[][] grid = new char[vertical + 1][horizontal + 1];

        int xOffset = stars.stream().mapToInt( Point::getY ).min().getAsInt() * -1;
        int yOffset = stars.stream().mapToInt( Point::getX ).min().getAsInt() * -1;
        for ( Point point : stars )
        {
            grid[point.getArrayX() + xOffset][point.getArrayY() + yOffset] = '#';
        }

        for ( char[] row : grid )
        {
            for ( char c : row )
            {
                System.out.print( c == 0 ? '.' : c );
            }
            System.out.println();
        }
    }
}
