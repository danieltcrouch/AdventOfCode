package day8;

import Common.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TreeService
{
    //Puzzle 2
    //37067
    public static int findMetadataSpecialSum( List<String> values )
    {
        String input = values.get( 0 );
        List<Integer> data = Arrays.stream( input.split( " " ) ).map( Integer::valueOf ).collect( Collectors.toList() );
        List<Node> nodes = new ArrayList<>( getChildren( data, 1 )  );
        return getSpecialTreeSum( nodes.get( 0 ) );
    }

    private static int getSpecialTreeSum( Node node )
    {
        MetaData data = (MetaData) node;
        if ( data.getChildren().size() == 0 )
        {
            return data.getContent().stream().mapToInt( Integer::intValue ).sum();
        }
        else
        {
            int result = 0;
            for ( Integer index : data.getContent() )
            {
                result += node.getChildren().size() >= index && index != 0 ? getSpecialTreeSum( data.getChildren().get( index - 1 ) ) : 0;
            }
            return result;
        }
    }

    //Puzzle 1
    //40984
    public static int findMetadataTreeSum( List<String> values )
    {
        String input = values.get( 0 );
        List<Integer> data = Arrays.stream( input.split( " " ) ).map( Integer::valueOf ).collect( Collectors.toList() );
        return getTreeSum( getChildren( data, 1 ) );
    }

    private static int getTreeSum( List<Node> nodes )
    {
        int result = 0;
        for ( MetaData node : nodes.stream().filter( obj -> obj instanceof MetaData ).map( obj -> (MetaData) obj ).collect( Collectors.toList() ) )
        {
            result += node.getContent().stream().mapToInt( Integer::intValue ).sum();
            result += getTreeSum( node.getChildren() );
        }
        return result;
    }

    private static List<Node> getChildren( List<Integer> data, int count )
    {
        List<Node> result = new ArrayList<>();

        for ( int i = 0; i < count; i++ )
        {
            int childrenCount = data.get( 0 );
            data.remove( 0 );
            int metaDataCount = data.get( 0 );
            data.remove( 0 );

            List<Node> children = getChildren( data, childrenCount );

            List<Integer> metaData = new ArrayList<>( data.subList( 0, metaDataCount ) );
            IntStream.range( 0, metaDataCount ).forEach( x -> data.remove( 0 ) );

            MetaData node = new MetaData( metaData, children );
            result.add( node );
        }

        return result;
    }

    private static class MetaData extends Node<List<Integer>>
    {
        public MetaData( List<Integer> content, List<Node> children )
        {
            super( content, children );
        }
    }
}
