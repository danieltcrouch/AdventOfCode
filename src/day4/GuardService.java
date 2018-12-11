package day4;

import Common.Pair;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GuardService
{
    //Puzzle 2
    //65854
    public static Integer findGuardByMinute( List<String> values )
    {
        List<GuardLog> guardLogs = values.stream().map( GuardLog::new ).collect( Collectors.toList() );
        guardLogs.sort( Comparator.comparing( GuardLog::getTime ) );
        List<GuardLog.GuardLogNight> guardLogNights = getGuardLogNights( guardLogs );

        int highestCount = 0;
        int commonMinute = 0;
        String guardId = "";
        List<String> guardIds = guardLogNights.stream().map( guardLogNight -> guardLogNight.id ).distinct().collect( Collectors.toList() );
        for ( String id : guardIds )
        {
            Map.Entry<Integer,Integer> minuteCount = findMostCommonMinuteCount( guardLogNights.stream().filter( night -> night.id.equals( id ) ).collect( Collectors.toList() ) );
            if ( minuteCount != null && minuteCount.getValue() > highestCount )
            {
                highestCount = minuteCount.getValue();
                commonMinute = minuteCount.getKey();
                guardId = id;
            }
        }

//        char[][] logs = getLogArray( guardLogNights );
//        System.out.println( "           000000000011111111112222222222333333333344444444445555555555" );
//        System.out.println( "           012345678901234567890123456789012345678901234567890123456789" );
//        for ( int i = 0; i < logs.length; i++ )
//        {
//            GuardLog.GuardLogNight night = guardLogNights.get( i );
//            System.out.print( night.date.format( DateTimeFormatter.ofPattern( "MM-dd" ) ) + " " );
//            System.out.print( String.format( "%1$" + 4 + "s", night.id ) + " " );
//            for ( char c : logs[ i ] )
//            {
//                System.out.print( c == 0 ? '.' : c );
//            }
//            System.out.println();
//        }

        System.out.println( guardId + ", " + commonMinute );
        return Integer.parseInt( guardId ) * commonMinute;
    }

    private static Map.Entry<Integer,Integer> findMostCommonMinuteCount( List<GuardLog.GuardLogNight> nights )
    {
        Map<Integer,Integer> timeByCount = new HashMap<>();
        for ( GuardLog.GuardLogNight night : nights )
        {
            for ( Pair span : night.sleepSpans )
            {
                for ( int i = span.getA(); i < span.getA() + span.getB(); i++ )
                {
                    timeByCount.put( i, timeByCount.getOrDefault( i, 0 ) + 1 );
                }
            }
        }

        return timeByCount.entrySet().stream().max( Comparator.comparingInt( Map.Entry::getValue ) ).orElse( null );
    }

    private static char[][] getLogArray( List<GuardLog.GuardLogNight> guardLogNights )
    {
        char[][] result = new char[guardLogNights.size()][60];
        for ( GuardLog.GuardLogNight night : guardLogNights )
        {
            char[] row = new char[60];
            for ( Pair span : night.sleepSpans )
            {
                for ( int i = span.getA(); i < span.getA() + span.getB(); i++ )
                {
                    row[i] = '#';
                }
            }
            result[ guardLogNights.indexOf( night ) ] = row;
        }
        return result;
    }

    //Puzzle 4
    //99911
    public static Integer findMinuteByGuard( List<String> values )
    {
        List<GuardLog> guardLogs = values.stream().map( GuardLog::new ).collect( Collectors.toList() );
        guardLogs.sort( Comparator.comparing( GuardLog::getTime ) );
        List<GuardLog.GuardLogNight> guardLogNights = getGuardLogNights( guardLogs );

        String guardId = findSleepiestGuard( guardLogNights );
        int commonMinute = findMostCommonMinute( guardLogNights.stream().filter( night -> night.id.equals( guardId ) ).collect( Collectors.toList() ) );

        System.out.println( guardId + ", " + commonMinute );
        return Integer.parseInt( guardId ) * commonMinute;
    }

    private static List<GuardLog.GuardLogNight> getGuardLogNights( List<GuardLog> guardLogs )
    {
        List<GuardLog.GuardLogNight> result = new ArrayList<>();
        List<GuardLog> guardLogsOnDate = new ArrayList<>();
        LocalDate date = guardLogs.get( 0 ).time.toLocalDate();

        for ( GuardLog log : guardLogs )
        {
            if ( !log.time.toLocalDate().equals( date ) )
            {
                result.add( GuardLog.getGuardLogNight( guardLogsOnDate ) );
                guardLogsOnDate.clear();
                date = log.time.toLocalDate();
            }

            guardLogsOnDate.add( log );
        }

        result.sort( Comparator.comparing( GuardLog.GuardLogNight::getDate ) );
        return result;
    }

    private static String findSleepiestGuard( List<GuardLog.GuardLogNight> nights )
    {
        Map<String,Integer> guardByMinutes = new HashMap<>();
        for ( GuardLog.GuardLogNight night : nights )
        {
            int minutesSlept = night.sleepSpans.stream().mapToInt( Pair::getB ).sum();
            guardByMinutes.put( night.id, guardByMinutes.getOrDefault( night.id, 0 ) + minutesSlept );
        }

        return guardByMinutes.entrySet().stream().max( Comparator.comparingInt( Map.Entry::getValue ) ).get().getKey();
    }

    private static Integer findMostCommonMinute( List<GuardLog.GuardLogNight> nights )
    {
        Map<Integer,Integer> timeByCount = new HashMap<>();
        for ( GuardLog.GuardLogNight night : nights )
        {
            for ( Pair span : night.sleepSpans )
            {
                for ( int i = span.getA(); i < span.getA() + span.getB(); i++ )
                {
                    timeByCount.put( i, timeByCount.getOrDefault( i, 0 ) + 1 );
                }
            }
        }

        return timeByCount.entrySet().stream().max( Comparator.comparingInt( Map.Entry::getValue ) ).get().getKey();
    }
}
