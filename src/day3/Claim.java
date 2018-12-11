package day3;

import Common.Point;

import java.util.ArrayList;
import java.util.List;

public class Claim
{
    public class ClaimUnit extends Point
    {
        public ClaimUnit( int x, int y )
        {
            super( x, y );
        }
    }

    String id;
    int leftIndex;
    int topIndex;
    int width;
    int height;
    List<ClaimUnit> claimUnits;

    public Claim( String value )
    {
        //#1 @ 520,746: 4x20
        value = value.replace( " ", "" );
        value = value.replace( "#", "" );
        value = value.replace( "@", "," );
        value = value.replace( ",", "," );
        value = value.replace( ":", "," );
        value = value.replace( "x", "," );

        String[] values = value.split( "," );
        id          = values[0];
        leftIndex   = Integer.parseInt( values[1] );
        topIndex    = Integer.parseInt( values[2] );
        width       = Integer.parseInt( values[3] );
        height      = Integer.parseInt( values[4] );

        claimUnits = new ArrayList<>();
    }

    public List<ClaimUnit> initializeClaimUnits()
    {
        List<ClaimUnit> result = new ArrayList<>();
        for ( int x = leftIndex; x < leftIndex + width; x++ )
        {
            for ( int y = topIndex; y < topIndex + height; y++ )
            {
                result.add( new ClaimUnit( x, y ) );
            }
        }

        claimUnits = result;
        return result;
    }
}
