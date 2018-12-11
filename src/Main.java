import day9.MarbleService;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    //https://adventofcode.com/2018/
    public static void main( String[] args )
    {
        String answer = MarbleService.findScore( readValues() ) + "";
        System.out.println( answer );
    }

    private static List<String> readValues()
    {
        List<String> list = new ArrayList<>();

        try
        {
            list = Files.readAllLines( Paths.get( "resources/values.txt" ) );
        }
        catch ( Exception e ) {}

        return list;
    }

    public static int findThing( List<String> values )
    {
        return 0;
    }
}


