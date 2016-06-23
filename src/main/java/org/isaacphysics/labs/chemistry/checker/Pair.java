package org.isaacphysics.labs.chemistry.checker;

/**
 * A tuple class compatible with hash tables.
 * Created by Ryan on 23/06/2016.
 */
public class Pair<A, B>
{
    /**
     * First element of tuple.
     */
    private A first;

    /**
     * Second element of tuple.
     */
    private B second;

    /**
     * Constructor function of Pair.
     * @param first First element of pair.
     * @param second Second element of pair.
     */
    public Pair(A first, B second)
    {
        super();
        this.first = first;
        this.second = second;
    }

    @Override
    public int hashCode()
    {
        int hashFirst = first != null ? first.hashCode() : 0;
        int hashSecond = second != null ? second.hashCode() : 0;

        return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other instanceof Pair)
        {
            Pair otherPair = (Pair) other;

            return
                    ((  this.first == otherPair.first ||
                            ( this.first != null && otherPair.first != null &&
                                    this.first.equals(otherPair.first))) &&
                            (	this.second == otherPair.second ||
                                    ( this.second != null && otherPair.second != null &&
                                            this.second.equals(otherPair.second))) );
        }

        return false;
    }

    @Override
    public String toString()
    {
        return "(" + first + ", " + second + ")";
    }

    /**
     * Getter method. Returns first element of pair.
     * @return First element of pair.
     */
    public A getFirst()
    {
        return first;
    }

    /**
     * Setter method. Sets first element of pair.
     * @param first Value that you want first element of pair to be set to.
     */
    public void setFirst(A first)
    {
        this.first = first;
    }

    /**
     * Getter method. Returns second element of pair.
     * @return Second element of pair.
     */
    public B getSecond()
    {
        return second;
    }

    /**
     * Setter method. Sets second element of pair.
     * @param second Value that you want second element of pair to be set to.
     */
    public void setSecond(B second)
    {
        this.second = second;
    }
}
