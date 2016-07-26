package org.isaacphysics.labs.chemistry.checker;

/**
 * Instance of a proton.
 *
 * Created by hhrl2 on 08/07/2016.
 */
public final class Proton extends SpecialNuclear {

    private static final int RealMass = 1;
    private static final int RealAtom = 1;

    /**
     * Constructor method of Proton.
     * @param mass Mass number inputted by user.
     * @param atom Atom number inputted by user.
     */
    public Proton(final Integer mass, final Integer atom) {
        super(RealMass, RealAtom, mass, atom, 1, "Proton", "proton", "p");
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Proton)) {
            return false;
        }

        Proton p = (Proton) o;
        return p.getAtomicNumber().equals(getAtomicNumber()) && p.getMassNumber().equals(getMassNumber());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
