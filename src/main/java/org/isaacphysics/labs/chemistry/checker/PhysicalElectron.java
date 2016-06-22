package org.isaacphysics.labs.chemistry.checker;

/**
 * An instance of electron (in nuclear equations).
 * Created by Ryan on 20/06/2016.
 */
public final class PhysicalElectron extends SpecialNuclear
{
    public PhysicalElectron()
    {
        super(0, -1, -1, "Electron", "electron", "e&zwj;&zwj;-&zwnj;");
    }

    @Override
    public boolean equals(Object o) { return (o instanceof PhysicalElectron); }
}
