/**
 * Copyright 2016 Ryan Lau
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.isaacphysics.labs.chemistry.checker;

import java.util.HashMap;

/**
 * Base class for special types of nuclear particles, such as electrons, gamma rays.
 * These particles have very similar behaviours, so base class is made to avoid reinventing the wheel.
 *
 * Created by Ryan on 22/06/2016.
 */
public abstract class SpecialNuclear extends Nuclear {
    /**
     * Mass number (correct answer).
     */
    private Integer mass;

    /**
     * Atomic number (correct answer).
     */
    private Integer atom;

    /**
     * Actual atomic number (user input).
     */
    private Integer actualAtom;

    /**
     * Actual mass number (user input).
     */
    private Integer actualMass;

    /**
     * Name of subparticle.
     */
    private String name;

    /**
     * Syntax of subparticle.
     */
    private String syntax;

    /**
     * Abbreviation of subparticle.
     */
    private String abbr;

    /**
     * Fraction form of charge.
     */
    private Fraction chargeFrac;

    /**
     * Saved atom count.
     */
    private static HashMap<String, Fraction> atomCount = new HashMap<>();

    /**
     * Constructor method of SpecialNuclear.
     * @param mass Actual mass number
     * @param atom Actual atomic number
     * @param actualMass Mass number inputted by student
     * @param actualAtom Atom number inputted by student
     * @param charge Charge
     * @param name Name of nuclear particle
     * @param syntax (mhchem) Syntax of subparticle
     * @param abbr Abbreviation of nuclear particle
     */
    public SpecialNuclear(final Integer mass, final Integer atom, final Integer actualMass, final Integer actualAtom,
                          final Integer charge, final String name, final String syntax, final String abbr) {
        this.mass = mass;
        this.atom = atom;
        this.actualMass = actualMass;
        this.actualAtom = actualAtom;
        this.name = name;
        this.syntax = syntax;
        this.abbr = abbr;
        this.chargeFrac = new Fraction(charge, 1);
    }

    @Override
    public Integer getMassNumber() {
        return actualMass;
    }

    @Override
    public Integer getAtomicNumber() {
        return actualAtom;
    }

    @Override
    public Fraction getCharge() {
        return chargeFrac;
    }

    @Override
    public HashMap<String, Fraction> getAtomCount() {
        return atomCount;
    }

    @Override
    public abstract boolean equals(Object o);

    @Override
    public String toString() {
        return "^{" + actualMass + "}" + "_{" + actualAtom + "}" + "\\" + syntax;
    }

    @Override
    public String getDotId() {
        return syntax + "_" + getdotId();
    }

    @Override
    public String getDotCode() {
        return "\t"
                + getDotId()
                + " [label=\"{&zwj;&zwj;&zwj;&zwj;" + name + "&zwnj;|\\n"
                + getDotString()
                + "\\n\\n|&zwj;&zwj;&zwj;atomic #&zwnj;: " + actualAtom
                + "\\n|&zwj;&zwj;&zwj;mass #&zwnj;: " + actualMass
                + "}\",color=\"#4c7fbe\"];\n";
    }

    @Override
    public String getDotString() {
        return "&zwj;&zwj;" + actualMass + "&zwnj;&zwj;" + actualAtom + "&zwnj;" + abbr;
    }

    @Override
    public boolean isValidAtomicNumber() {
        return actualAtom.equals(atom) && actualMass.equals(mass);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
