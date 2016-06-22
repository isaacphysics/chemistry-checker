package org.isaacphysics.labs.chemistry.checker;

/**
 * An instance of a positron.
 * Created by Ryan on 20/06/2016.
 */
public class Positron extends SpecialNuclear
{
    public Positron()
    {
        super(0, 1, 1, "Positron", "positron", "e&zwj;&zwj;+&zwnj;");
    }

    @Override
    public boolean equals(Object o) { return (o instanceof Positron); }
}
