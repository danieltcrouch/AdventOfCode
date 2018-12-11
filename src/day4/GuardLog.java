package day4;

import Common.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GuardLog
{
    public static class GuardLogNight
    {
        String id;
        LocalDate date;
        List<Pair> sleepSpans;

        public GuardLogNight( String id, LocalDate date )
        {
            this.id = id;
            this.date = date;
            sleepSpans = new ArrayList<>();
        }

        public void addSleepSpan( LocalDateTime start, LocalDateTime end )
        {
            int length = end.getMinute() - start.getMinute();
            sleepSpans.add( new Pair( start.getMinute(), length ) );
        }

        public String getId()
        {
            return id;
        }

        public LocalDate getDate()
        {
            return date;
        }

        public List<Pair> getSleepSpans()
        {
            return sleepSpans;
        }
    }

    public enum Action
    {
        begin, sleep, wake;

        public static Action getAction( String s )
        {
            Action result = null;

            if ( s.contains( "begins shift" ) )
            {
                result = begin;
            }
            else if ( s.contains( "falls asleep" ) )
            {
                result = sleep;
            }
            else if ( s.contains( "wakes up" ) )
            {
                result = wake;
            }

            return result;
        }
    }

    String id;
    Action action;
    LocalDateTime time;

    public GuardLog( String value )
    {
        //[1518-11-01 00:00] Guard #10 begins shift
        //[1518-11-01 00:05] falls asleep
        //[1518-11-01 00:25] wakes up
        value = value.replace( "[", "" );
        value = value.replace( "] ", "," );
        String[] values = value.split( "," );

        time = LocalDateTime.parse( values[0], DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm") );
        LocalDate today = time.toLocalDate();
        LocalDateTime tomorrowMidnight = today.plusDays( 1 ).atTime( LocalTime.MIDNIGHT );
        time = ( time.isBefore( tomorrowMidnight ) && time.isAfter( LocalDateTime.of( today, LocalTime.NOON ) ) ) ? tomorrowMidnight : time;

        action = Action.getAction( values[1] );
        id = action == Action.begin ? getId( values[1] ) : null;
    }

    public static GuardLogNight getGuardLogNight( List<GuardLog> logs )
    {
        GuardLog begin = logs.stream().filter( guardLog -> guardLog.action == Action.begin ).findFirst().get();

        String id = begin.id;
        LocalDate date = begin.time.toLocalDate();
        logs.forEach( guardLog -> guardLog.id = id );
        logs.remove( begin );

        GuardLogNight night = new GuardLogNight( id, date );
        LocalDateTime start = null;
        for ( GuardLog log : logs )
        {
            if ( log.action == Action.sleep )
            {
                start = log.time;
            }
            else
            {
                night.addSleepSpan( start, log.time );
            }
        }

        return night;
    }

    private static String getId( String value )
    {
        return value.replaceAll( "[\\D]", "" );
    }

    public String getId()
    {
        return id;
    }

    public Action getAction()
    {
        return action;
    }

    public LocalDateTime getTime()
    {
        return time;
    }
}
