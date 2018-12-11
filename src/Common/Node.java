package Common;

import java.util.List;

public abstract class Node<T>
{
    T          content;
    List<Node> children;

    public Node()
    {
    }

    public Node( T content, List<Node> children )
    {
        this.content = content;
        this.children = children;
    }

    public T getContent()
    {
        return content;
    }

    public void setContent( T content )
    {
        this.content = content;
    }

    public List<Node> getChildren()
    {
        return children;
    }

    public void setChildren( List<Node> children )
    {
        this.children = children;
    }
}
