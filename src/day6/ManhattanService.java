package day6;

import Common.Point;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManhattanService
{
    //Puzzle 2
    //46966
    public static int findLargestManhattanArea( List<String> values )
    {
        List<Point> points = new ArrayList<>();
        char[][] grid = new char[400][400];

        getPoints( values, points, grid );

        for ( int i = 0; i < grid.length; i++ )
        {
            for ( int j = 0; j < grid[i].length; j++ )
            {
                int totalDistance = 0;
                for ( Point point : points )
                {
                    totalDistance += Point.getManhattanDistance( new Point( j, i ), point );
                }

                if ( totalDistance < 10000 )
                {
                    grid[i][j] = '#';
                }
            }
        }

//        for ( char[] row : grid )
//        {
//            for ( char c : row )
//            {
//                System.out.print( c == 0 ? '-' : c );
//            }
//            System.out.println();
//        }

        return getLargestArea( grid, '#' );
    }

    private static int getLargestArea( char[][] grid, char cType )
    {
        int result = 0;

        for ( int i = 0; i < grid.length; i++ )
        {
            for ( int j = 0; j < grid[i].length; j++ )
            {
                char c = grid[i][j];
                if ( c == cType )
                {
                    result++;
                }
            }
        }

        return result;
    }

    //Puzzle 1
    //4829
    public static int findLargestOriginArea( List<String> values )
    {
        List<Point> points = new ArrayList<>();
        char[][] grid = new char[400][400];

        getPoints( values, points, grid );

        for ( int i = 0; i < grid.length; i++ )
        {
            for ( int j = 0; j < grid[i].length; j++ )
            {
                if ( !points.contains( new Point( j, i ) ) )
                {
                    int minDistance = Integer.MAX_VALUE;
                    for ( Point point : points )
                    {
                        int distance = Point.getManhattanDistance( new Point( j, i ), point );
                        if ( distance < minDistance )
                        {
                            minDistance = distance;
                            grid[i][j] = grid[ point.getArrayX() ][ point.getArrayY() ];
                        }
                        else if ( distance == minDistance )
                        {
                            grid[i][j] = '.';
                        }
                    }
                }
            }
        }

//        for ( char[] row : grid )
//        {
//            for ( char c : row )
//            {
//                System.out.print( c == 0 ? '-' : c );
//            }
//            System.out.println();
//        }

        return getLargestArea( grid );
    }

    private static void getPoints( List<String> values, List<Point> points, char[][] grid )
    {
        for ( String value : values )
        {
            String[] pointString = value.split( ", " );
            Point point = new Point( Integer.parseInt( pointString[0] ), Integer.parseInt( pointString[1] ) );
            grid[point.getArrayX()][point.getArrayY()] = (char)(values.indexOf( value ) + 'A');
            points.add( point );
        }
    }

    private static int getLargestArea( char[][] grid )
    {
        Map<Character, Integer> pointBySize = new HashMap<>();
        List<Character> disqualified = new ArrayList<>();

        for ( int i = 0; i < grid.length; i++ )
        {
            for ( int j = 0; j < grid[i].length; j++ )
            {
                char c = grid[i][j];
                if ( !( c == '.' || c == 0 ) )
                {
                    pointBySize.put( c, pointBySize.getOrDefault( c, 0 ) + 1 );
                    if ( (i == 0 || i == grid.length - 1 || j == 0 || j == grid[i].length - 1 ) && !disqualified.contains( c ) )
                    {
                        disqualified.add( c );
                    }
                }
            }
        }

        return pointBySize.entrySet().stream().filter( entry -> !disqualified.contains( entry.getKey() ) ).max( Comparator.comparingInt( Map.Entry::getValue ) ).get().getValue();
    }
}
