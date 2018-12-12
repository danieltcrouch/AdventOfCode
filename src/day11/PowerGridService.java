package day11;

import Common.Point;

import java.util.List;

public class PowerGridService
{
    //Puzzle 1 & 2
    //235,63
    //229,251,16
    public static String findPowerBlock( List<String> values )
    {
        String serialNumber = values.get( 0 );
        Integer[][] powerGrid = new Integer[300][300];

        for ( int i = 0; i < powerGrid.length; i++ )
        {
            for ( int j = 0; j < powerGrid[i].length; j++ )
            {
                powerGrid[i][j] = getPowerLevel( j + 1, i + 1, Integer.parseInt( serialNumber ) );
            }
        }

        int dimension = 0;
        int highestLevel = 0;
        Point highestLevelPoint = new Point();
        for ( int s = 1; s <= powerGrid.length; s++ ) //Puzzle 1: s = 3
        {
            for ( int i = 0; i < powerGrid.length - s; i++ )
            {
                for ( int j = 0; j < powerGrid[i].length - s; j++ )
                {
                    int level = getBlockPowerLevel( powerGrid, s, i, j );
                    if ( level > highestLevel )
                    {
                        dimension = s;
                        highestLevel = level;
                        highestLevelPoint.setX( j + 1 );
                        highestLevelPoint.setY( i + 1 );
                    }
                }
            }
            System.out.print( s + ",");
            if ( s % 10 == 0 )
            {
                System.out.println();
            }
        }
        System.out.println();
        System.out.println();


        for ( int i = 0; i < powerGrid.length; i++ )
        {
            for ( int j = 0; j < powerGrid[i].length; j++ )
            {
                System.out.print( String.format("%1$3s", powerGrid[i][j]) );
            }
            System.out.println();
        }


        return highestLevelPoint.toString() + "," + dimension;
    }

    private static int getBlockPowerLevel( Integer[][] powerGrid, int s, int iBase, int jBase )
    {
        int result = 0;
        for ( int i = 0; i < s; i++ )
        {
            for ( int j = 0; j < s; j++ )
            {
                result += powerGrid[iBase + i][jBase + j];
            }
        }
        return result;
    }

    private static int getPowerLevel( int x, int y, int serialNumber )
    {
        int temp = x + 10;
        temp *= y;
        temp += serialNumber;
        temp *= (x + 10);
        temp = ( temp / 100 ) % 10;
        temp -= 5;
        return temp;
    }
}
