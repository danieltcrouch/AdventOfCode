package day12;

import java.util.List;
import java.util.stream.Collectors;

public class PlantService
{
    public static int findPlantPots( List<String> values )
    {
        String initialState = values.get( 0 ).split( ": " )[1];
        values.remove( 0 );
        values.remove( 1 );
        List<String> combinations = values.stream().filter( value -> value.endsWith( "#" ) ).map( value -> value.substring( 0, 5 ) ).collect( Collectors.toList() );

        //

        return 0;
    }
}
