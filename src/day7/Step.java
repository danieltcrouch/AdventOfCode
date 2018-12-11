package day7;

import java.util.ArrayList;
import java.util.List;

public class Step
{
    public static class Worker
    {
        Step activeStep;

        public void work()
        {
            activeStep.timeRemaining--;
        }

        public boolean isIdle()
        {
            return activeStep == null || activeStep.timeRemaining == 0;
        }
    }

    Character character;
    Integer timeRemaining;
    List<Step> prerequisites;
    List<Step> nextSteps;

    public Step( Character character, Integer timeRemaining, List<Step> prerequisites, List<Step> nextSteps )
    {
        this.character = character;
        this.timeRemaining = timeRemaining;
        this.prerequisites = prerequisites != null ? prerequisites : new ArrayList<>();
        this.nextSteps = nextSteps != null ? nextSteps : new ArrayList<>();
    }

    public Step( Character character )
    {
        this.character = character;
        this.prerequisites = new ArrayList<>();
        this.nextSteps = new ArrayList<>();
        setTimeRemaining();
    }

    public void addPrerequisites( List<Step> prerequisites )
    {
        this.prerequisites.addAll( prerequisites );
    }

    public void addNextSteps( List<Step> nextSteps )
    {
        this.nextSteps.addAll( nextSteps );
    }

    private void setTimeRemaining()
    {
//        timeRemaining = (int)character - 4;
        timeRemaining = (int)character - 4;
    }

    @Override
    public boolean equals( Object o )
    {
        boolean result = false;
        if ( o == this )
        {
            return true;
        }
        if ( !( o instanceof Step ) )
        {
            return false;
        }

        Step c = (Step) o;
        return c.character == this.character;
    }

    public Character getCharacter()
    {
        return character;
    }

    public Integer getTimeRemaining()
    {
        return timeRemaining;
    }

    public List<Step> getPrerequisites()
    {
        return prerequisites;
    }

    public List<Step> getNextSteps()
    {
        return nextSteps;
    }
}
