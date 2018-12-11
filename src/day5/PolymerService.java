package day5;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PolymerService
{
    //Puzzle 2
    //5726
    public static int findPolymerUnit( List<String> values )
    {
        String polymer = values.get( 0 );
        Map<Character, Integer> charsByLength = polymer.chars().mapToObj( e -> Character.toLowerCase( (char)e) ).distinct().collect( Collectors.toMap( Function.identity(), s->0 ) );

        for ( Character c : charsByLength.keySet() )
        {
            String tempPolymer = polymer.replaceAll( c.toString(), "" ).replaceAll( c.toString().toUpperCase(), "" );
            charsByLength.put( c, findPolymerLength( Arrays.asList( tempPolymer ) ) );
        }

        return charsByLength.values().stream().min( Comparator.comparingInt( Integer::intValue ) ).get();
    }

    //Puzzle 1
    //9822
    public static int findPolymerLength( List<String> values )
    {
        String polymer = values.get( 0 );

        int changes;
        do
        {
            changes = 0;
            for ( int i = 0; i < polymer.length() - 1; i++ )
            {
                char c1 = polymer.charAt( i );
                char c2 = polymer.charAt( i + 1 );
                if ( Character.toLowerCase( c1 ) == Character.toLowerCase( c2 ) && c1 != c2 )
                {
                    polymer = new StringBuilder(polymer).deleteCharAt(i).toString();
                    polymer = new StringBuilder(polymer).deleteCharAt(i).toString();
                    changes++;
                }
            }
        } while ( changes > 0 );

        return polymer.length();
    }
}
