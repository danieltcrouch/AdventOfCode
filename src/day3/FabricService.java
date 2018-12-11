package day3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FabricService
{
    //Puzzle 2
    //445
    public static String findNonOverlappingClaim( List<String> values )
    {
        List<Claim> claims = values.stream().map( Claim::new ).collect( Collectors.toList() );
        Map<Claim, Boolean> searchedClaims = new HashMap<>();
        char[][] claimUnits = new char[1000][1000];

        for ( Claim claim : claims )
        {
            boolean containedOverlap = false;
            for ( Claim.ClaimUnit claimUnit : claim.initializeClaimUnits() )
            {
                if ( claimUnits[claimUnit.getArrayX()][claimUnit.getArrayY()] != 0 )
                {
                    containedOverlap = true;

                    if ( claimUnits[claimUnit.getArrayX()][claimUnit.getArrayY()] == '0' )
                    {
                        claimUnits[claimUnit.getArrayX()][claimUnit.getArrayY()] = 'X';
                        searchedClaims.keySet()
                            .stream()
                            .filter( searchedClaim -> !searchedClaims.get( searchedClaim ) )
                            .filter( searchedClaim -> searchedClaim.claimUnits.contains( claimUnit ) )
                            .forEach( searchedClaim -> searchedClaims.put( searchedClaim, true ) );
                    }
                }
                else
                {
                    claimUnits[claimUnit.getArrayX()][claimUnit.getArrayY()] = '0';
                }
            }

            searchedClaims.put( claim, containedOverlap );
        }

//        for ( char[] row : claimUnits )
//        {
//            for ( char c : row )
//            {
//                System.out.print( c == 0 ? '-' : c );
//            }
//            System.out.println();
//        }

        Claim result = searchedClaims.keySet().stream().filter( claim -> !searchedClaims.get( claim ) ).findFirst().get();
        System.out.println( " (" + result.leftIndex + ", " + result.topIndex + ")" );
        return result.id;
    }

    //Puzzle 1
    //120419
    public static Integer findOverlaps( List<String> values )
    {
        int result = 0;
        List<Claim> claims = values.stream().map( Claim::new ).collect( Collectors.toList() );
        char[][] claimUnits = new char[1000][1000];

        for ( Claim claim : claims )
        {
            for ( Claim.ClaimUnit claimUnit : claim.initializeClaimUnits() )
            {
                if ( claimUnits[claimUnit.getArrayX()][claimUnit.getArrayY()] == '0' ) //if ( claimUnits.get( claimUnit ) != null && !claimUnits.get( claimUnit ) )
                {
                    result++;
                    claimUnits[claimUnit.getArrayX()][claimUnit.getArrayY()] = 'X';
                }
                else if ( claimUnits[claimUnit.getArrayX()][claimUnit.getArrayY()] != 'X' ) //else if ( claimUnits.get( claimUnit ) == null )
                {
                    claimUnits[claimUnit.getArrayX()][claimUnit.getArrayY()] = '0';
                }
            }
        }

        return result;
    }
}
