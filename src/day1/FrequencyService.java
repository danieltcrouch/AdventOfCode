package day1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FrequencyService
{
    //Puzzle 2
    //83445
    public static Integer findRepeatedFrequency( List<String> values )
    {
        List<Integer> intValues = values.stream().map( Integer::parseInt ).collect( Collectors.toList() );
        Integer result = null;

        List<Integer> frequencies = new ArrayList<>();
        int frequency = 0;

        do
        {
            for ( Integer value : intValues )
            {
                frequency += value;

                if ( frequencies.indexOf( frequency ) > -1 )
                {
                    result = frequency;
                    break;
                }

                frequencies.add( frequency );
            }
        } while ( result != null );

        return result;
    }

    //Puzzle 1
    //590
    public static int findTotalFrequency( List<String> values )
    {
        List<Integer> intValues = values.stream().map( Integer::parseInt ).collect( Collectors.toList() );
        int frequency = 0;
        for ( Integer value : intValues )
        {
            frequency += value;
        }

        return frequency;
    }
}
