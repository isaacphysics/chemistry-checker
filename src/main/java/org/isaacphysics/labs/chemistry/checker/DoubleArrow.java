package org.isaacphysics.labs.chemistry.checker;

/**
 * Created by Ryan on 17/06/2016.
 * A singleton class for returning an instance of double arrow for chemical equations.
 * Also provides method for pretty printing strings.
 */
public final class DoubleArrow extends AbstractArrow
{
    /**
     * The single class instance for DoubleArrow.
     */
    private static DoubleArrow singleton = null;

    /**
     * Constructor function for DoubleArrow.
     */
    private DoubleArrow() {}

    /**
     * Getter method. Returns the only class instance of DoubleArrow.
     * @return Class instance of DoubleArrow
     */
    public static DoubleArrow getDoubleArrow()
    {
        if (singleton == null)
            singleton = new DoubleArrow();

        return singleton;
    }

    @Override
    public String toString() { return " <--> "; }

    @Override
    String getDotCode()
    {
        return "\tarrow [label=\"{&zwj;&zwj;&zwj;&zwj;DoubleArrow&zwnj;|\\n" +
                "&#8652;\\n\\n" +
                "}\",color=\"#4c7fbe\"];\n";
    }

    @Override
    String getDotString() { return " &#8652; "; }
}
