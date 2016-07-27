package org.isaacphysics.labs.chemistry.checker;

/**
 * Instance of a neutron.
 *
 * Created by hhrl2 on 08/07/2016.
 */
public final class Neutron extends SpecialNuclear {

    private static final int RealMass = 1;
    private static final int RealAtom = 0;

    /**
     * Constructor method of Neutron.
     * @param mass Mass number inputted by user.
     * @param atom Atom number inputted by user.
     */
    public Neutron(final Integer mass, final Integer atom) {
        super(RealMass, RealAtom, mass, atom, 0, "Neutron", "neutron", "n");
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Neutron)) {
            return false;
        }

        Neutron p = (Neutron) o;
        return p.getAtomicNumber().equals(getAtomicNumber()) && p.getMassNumber().equals(getMassNumber());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
