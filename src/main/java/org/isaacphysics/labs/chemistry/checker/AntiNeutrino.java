package org.isaacphysics.labs.chemistry.checker;

/**
 * An instance of anti-neutrino (in nuclear equations).
 *
 * Created by hhrl2 on 25/07/2016.
 */
public class AntiNeutrino extends SpecialNuclear {

    private static final int RealMass = 0;
    private static final int RealAtom = 0;

    /**
     * Constructor method of PhysicalElectron.
     *
     * @param mass Mass number inputted by user.
     * @param atom Atom number inputted by user.
     */
    public AntiNeutrino(final Integer mass, final Integer atom) {
        super(RealMass, RealAtom, mass, atom, 0, "Antineutrino", "antineutrino", "&#957;&zwj;e&zwnj;");
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof AntiNeutrino)) {
            return false;
        }

        AntiNeutrino p = (AntiNeutrino) o;
        return p.getAtomicNumber().equals(getAtomicNumber()) && p.getMassNumber().equals(getMassNumber());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
