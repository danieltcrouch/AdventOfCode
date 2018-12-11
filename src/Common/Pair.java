package Common;

import java.util.Objects;

public class Pair
{
    Integer a;
    Integer b;

    public Pair() {}

    public Pair( Integer a, Integer b )
    {
        this.a = a;
        this.b = b;
    }

    public Integer getDifference()
    {
        return Math.abs( getA() - getB() );
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }
        Pair pair = (Pair) o;
        return Objects.equals( getA(), pair.getA() ) &&
               Objects.equals( getB(), pair.getB() );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( getA(), getB() );
    }

    public Integer getA()
    {
        return a;
    }

    public void setA( Integer a )
    {
        this.a = a;
    }

    public Integer getB()
    {
        return b;
    }

    public void setB( Integer b )
    {
        this.b = b;
    }
}
