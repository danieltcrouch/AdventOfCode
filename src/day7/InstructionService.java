package day7;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InstructionService
{
    //Puzzle 2
    //1234
    public static int findStepTime( List<String> values )
    {
        List<Step> masterList = getInstructions( values );

        Step step1 = masterList.stream().filter( s -> s.getPrerequisites().size() == 0 ).findFirst().get();
        List<Step> availableSteps = new ArrayList<>();
        availableSteps.add( step1 );

        int totalTime = 0;
        List<Step.Worker> workers = getWorkers( 5 );
        List<Step> completed = new ArrayList<>();
        do
        {
            assignWork( workers, availableSteps );
            List<Step> temp = workSecond( workers );
            completed.addAll( temp );

//            System.out.print( totalTime + " " );
//            System.out.println( completed.stream().map( day7.Step::getCharacter ).map( String::valueOf ).collect( Collectors.joining() ) );

            availableSteps.addAll( getAvailableSteps( masterList, completed, temp ) );

            totalTime++;
        } while ( completed.size() != masterList.size() );
        return totalTime;
    }

    private static List<Step.Worker> getWorkers( int count )
    {
        List<Step.Worker> result = new ArrayList<>();
        IntStream.range( 0, count ).forEach( i -> result.add( new Step.Worker() ) );
        return result;
    }

    private static void assignWork( List<Step.Worker> workers, List<Step> availableSteps )
    {
        for ( Step.Worker worker : workers  )
        {
            if ( worker.isIdle() )
            {
                worker.activeStep = getNextStep( availableSteps );
            }
        }
    }

    private static List<Step> workSecond( List<Step.Worker> workers )
    {
        List<Step> result = new ArrayList<>();

        for ( Step.Worker worker : workers  )
        {
            if ( !worker.isIdle() )
            {
                worker.work();
                if ( worker.activeStep.timeRemaining == 0 )
                {
                    result.add( worker.activeStep );
                }
            }
        }

        return result;
    }

    private static List<Step> getAvailableSteps( List<Step> masterList, List<Step> completed, List<Step> justCompleted )
    {
        List<Step> availableSteps = new ArrayList<>();
        justCompleted.forEach( s -> availableSteps.addAll( masterList.get( masterList.indexOf( s ) ).nextSteps ) );
        return availableSteps.stream().filter( s -> completed.containsAll( masterList.get( masterList.indexOf( s ) ).prerequisites ) ).collect( Collectors.toList() );
    }

    //Puzzle 1
    //SCLPAMQVUWNHODRTGYKBJEFXZI
    public static String findStepOrder( List<String> values )
    {
        List<Step> masterList = getInstructions( values );

        Step step1 = masterList.stream().filter( s -> s.getPrerequisites().size() == 0 ).findFirst().get();
        List<Step> availableSteps = new ArrayList<>();
        availableSteps.add( step1 );

        List<Step> completed = new ArrayList<>();
        do
        {
            Step step = getNextStep( availableSteps );
            completed.add( step );
            availableSteps.addAll( getAvailableSteps( masterList, completed, step ) );
        } while ( completed.size() != masterList.size() );
        return completed.stream().map( Step::getCharacter ).map( String::valueOf ).collect( Collectors.joining() );
    }

    private static List<Step> getInstructions( List<String> values )
    {
        List<Step> result;

        List<Character> allCharacters = new ArrayList<>();
        Map<Character, List<Character>> instructionList = new HashMap<>();
        for ( String value : values )
        {
            char step1 = value.charAt( 5 );
            char step2 = value.charAt( 36 );
            List<Character> steps = instructionList.getOrDefault( step1, new ArrayList<>() );
            steps.add( step2 );
            instructionList.put( step1, steps );

            allCharacters.add( step1 );
            allCharacters.add( step2 );
        }

        result = allCharacters.stream().distinct().map( Step::new ).collect( Collectors.toList() );
        result.forEach( s -> s.addNextSteps( instructionList.getOrDefault( s.character, new ArrayList<>() ).stream().map( Step::new ).collect( Collectors.toList() ) ) );

        Map<Character, List<Character>> prerequisites = new HashMap<>();
        for ( Map.Entry<Character, List<Character>> steps : instructionList.entrySet() )
        {
            for ( Character step : steps.getValue() )
            {
                List<Character> requisites = prerequisites.getOrDefault( step, new ArrayList<>() );
                requisites.add( steps.getKey() );
                prerequisites.put( step, requisites );
            }
        }

        result.forEach( i -> i.addPrerequisites( prerequisites.getOrDefault( i.character, new ArrayList<>() ).stream().map( Step::new ).collect( Collectors.toList() ) ) );
        return result;
    }

    private static List<Step> getAvailableSteps( List<Step> masterList, List<Step> completed, Step justCompleted )
    {
        List<Step> availableSteps = new ArrayList<>( masterList.get( masterList.indexOf( justCompleted ) ).nextSteps );
        return availableSteps.stream().filter( s -> completed.containsAll( masterList.get( masterList.indexOf( s ) ).prerequisites ) ).collect( Collectors.toList() );
    }

    private static Step getNextStep( List<Step> availableSteps )
    {
        Step result = null;
        availableSteps.sort( Comparator.comparing( Step::getCharacter ) );
        if ( availableSteps.size() > 0 )
        {
            result = availableSteps.get( 0 );
            availableSteps.remove( 0 );
        }
        return result;
    }
}
