package day2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InventoryService
{
    //Puzzle 2
    //xretqmmonskvzupalfiwhcfdb
    public static String findCommonLetters( List<String> values )
    {
        List<String> searchedValues = new ArrayList<>();
        String result = null;

        for ( String value : values )
        {
            for ( String searchedValue : searchedValues )
            {
                int charIndex = getChangedIndex( value, searchedValue );
                if ( charIndex > -1 )
                {
                    result = value.substring( 0, charIndex ) + value.substring( charIndex + 1 );
                    break;
                }
            }

            if ( result != null )
            {
                break;
            }

            searchedValues.add( value );
        }

        return result;
    }

    private static int getChangedIndex( String value1, String value2 )
    {
        int result = -1;

        int changeCount = 0;
        for ( int i = 0; i < value1.length(); i++ )
        {
            if ( value1.charAt( i ) != value2.charAt( i ) )
            {
                changeCount++;
                result = ( changeCount == 1 ) ? i : -1;
            }
        }

        return result;
    }

    //Puzzle 1
    //5976
    public static Integer findChecksum( List<String> values )
    {
        int doubleLetterCount = 0;
        int tripleLetterCount = 0;
        for ( String value : values )
        {
            if ( hasLetterCount( value, 2 ) )
            {
                doubleLetterCount++;
            }

            if ( hasLetterCount( value, 3 ) )
            {
                tripleLetterCount++;
            }
        }

        return doubleLetterCount * tripleLetterCount;
    }

    private static boolean hasLetterCount( String value, int count )
    {
        Map<Character,Long> characterCounts = value.chars().mapToObj( e -> (char) e ).collect( Collectors.groupingBy( Function.identity(), Collectors.counting() ) );
        return characterCounts.containsValue( (long)count );
    }
}
