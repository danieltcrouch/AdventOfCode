package day9;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class MarbleService
{
    //Puzzle 1 & 2
    //400493
    //3338341690
    public static BigInteger findScore( List<String> values )
    {
        String value = values.get( 0 );
        int playerCount = Integer.parseInt( value.split( ";" )[0].replaceAll( "[\\D]", "" ) );
        int lastMarbleValue = Integer.parseInt( value.split( ";" )[1].replaceAll( "[\\D]", "" ) );
        lastMarbleValue *= 100; //Puzzle 2

        CircleList<Integer> marbles = new CircleList<>();
        marbles.add( 0 );

        BigInteger[] players = new BigInteger[playerCount];
        Arrays.fill( players, BigInteger.ZERO );

        for ( int i = 1; i <= lastMarbleValue; i++ )
        {
            if ( i % 23 != 0 )
            {
                marbles.rotate( 2 );
                marbles.add( i );
            }
            else
            {
                marbles.rotate( -7 );
                players[i % playerCount] = players[i % playerCount].add( BigInteger.valueOf( i + marbles.pop() ) );
            }
        }

        return Arrays.stream( players ).max( BigInteger::compareTo ).get();
    }
}
