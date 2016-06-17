package org.isaacphysics.labs.chemistry.checker;

/**
 * Base class of arrows in equations.
 * May be extended in the future to include items on top of arrows, e.g. heat, catalyst.
 * Created by Ryan on 16/06/2016.
 */
public abstract class AbstractArrow
{
    @Override
    public abstract String toString();

    /**
     * Returns Graphviz code that generates syntax tree of chemical term.
     * <p>
     *     Special syntax for this modified Graphviz code:
     * <ul>
     *     <li> &zwj;&zwj;&zwj;&zwj;: Makes text to the right bold.
     *     <li> &zwj;&zwj;&zwj;: Makes text to the right italic.
     *     <li> &zwj;&zwj;: Superscripts text to the right.
     *     <li> &zwj;: Subscripts text to the right.
     *     <li> &zwnj;: Delimiter that removes effect of above.
     * </ul>
     *
     * @return Graphviz code that generates the abstract syntax tree
     */
    abstract String getDotCode();

    /**
     * Returns pretty-printed chemical term/equation/expression
     * @return Pretty printed chemical term/equation/expression
     */
    abstract String getDotString();
}
