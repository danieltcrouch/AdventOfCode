package day12;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.toIntExact;

public class PlantService
{
    //Puzzle 2
    //2600000001872
    public static String findPlantPotsConvergence( List<String> values )
    {
        values = values.stream().filter( v -> v != null && v != "" ).collect( Collectors.toList() );
        String currentState = values.get( 0 ).split( ": " )[1];
        values.remove( 0 );
        List<String> combinations = values.stream().filter( value -> value.endsWith( "#" ) ).map( value -> value.substring( 0, 5 ) ).collect( Collectors.toList() );

        int indexOffset = 0;
        boolean convergenceReached = false;
        BigInteger generation = BigInteger.ZERO;
        BigInteger finalGeneration = new BigInteger("50000000000");

        for ( int genX = 0; genX < 50 && !convergenceReached; genX++ )
        {
            for ( int gen = 0; gen < 1000000000 && !convergenceReached; gen++, generation = generation.add( BigInteger.ONE ) )
            {
                String nextState = "";
                currentState = ".." + currentState + "..";
                indexOffset += 2;
                for ( int i = 0; i < currentState.length(); i++ )
                {
                    String combo = getCombo( currentState, i );
                    nextState += (combinations.contains( combo )) ? '#' : '.';
                }

                if ( currentState.substring( currentState.indexOf( '#' ), currentState.lastIndexOf( '#' ) ).equals(
                     nextState.substring( nextState.indexOf( '#' ), nextState.lastIndexOf( '#' ) ) ) )
                {
                    convergenceReached = true;
                }

                currentState = nextState;
            }
        }

        final String currentStateFinal = currentState;
        final int indexOffsetFinal = indexOffset;
        int convergentResult = IntStream.range( 0, currentState.length() ).filter( i -> currentStateFinal.charAt( i ) == '#' ).map( i -> i - indexOffsetFinal ).sum();
        int plantsRemaining = toIntExact( IntStream.range( 0, currentState.length() ).filter( i -> currentStateFinal.charAt( i ) == '#' ).count() );

        return ( finalGeneration.subtract( generation ) )
                .multiply( new BigInteger( String.valueOf( plantsRemaining ) ) )
                .add( new BigInteger( String.valueOf( convergentResult ) ) )
                .toString();
    }

    //Puzzle 1
    //3221
    public static int findPlantPots( List<String> values )
    {
        values = values.stream().filter( v -> v != null && v != "" ).collect( Collectors.toList() );
        String initialState = values.get( 0 ).split( ": " )[1];
        values.remove( 0 );
        List<String> combinations = values.stream().filter( value -> value.endsWith( "#" ) ).map( value -> value.substring( 0, 5 ) ).collect( Collectors.toList() );

        int indexOffset = 0;
        for ( int gen = 0; gen < 20; gen++ )
        {
            String nextState = "";
            initialState = ".." + initialState + "..";
            indexOffset += 2;
            for ( int i = 0; i < initialState.length(); i++ )
            {
                String combo = getCombo( initialState, i );
                nextState += ( combinations.contains( combo ) ) ? '#' : '.';
            }
            initialState = nextState;
        }

        final String result = initialState;
        final int indexOffsetFinal = indexOffset;
        return IntStream.range( 0, initialState.length() ).filter( i -> result.charAt( i ) == '#' ).map( i -> i - indexOffsetFinal ).sum();
    }

    private static String getCombo( String initialState, int i )
    {
        return "" + ( ( i - 2 >= 0 ) ? initialState.charAt( i - 2 ) : '.' ) +
                    ( ( i - 1 >= 0 ) ? initialState.charAt( i - 1 ) : '.' ) +
                    ( initialState.charAt( i ) ) +
                    ( ( i + 1 < initialState.length() ) ? initialState.charAt( i + 1 ) : '.' ) +
                    ( ( i + 2 < initialState.length() ) ? initialState.charAt( i + 2 ) : '.' );
    }
}
