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
public abstract class SpecialNuclear extends Nuclear
{
    /**
     * Mass number
     */
    private Integer mass;

    /**
     * Atomic number
     */
    private Integer atom;

    /**
     * Charge
     */
    private Integer charge;

    /**
     * Name of subparticle
     */
    private String name;

    /**
     * Syntax of subparticle
     */
    private String syntax;

    /**
     * Abbreviation of subparticle
     */
    private String abbr;

    /**
     * Constructor method of SpecialNuclear.
     * @param mass Mass number
     * @param atom Atomic number
     * @param charge Charge
     * @param name Name of nuclear particle
     * @param abbr Abbreviation of nuclear particle
     */
    public SpecialNuclear(Integer mass, Integer atom, Integer charge, String name, String syntax, String abbr)
    {
        this.mass = mass;
        this.atom = atom;
        this.charge = charge;
        this.name = name;
        this.syntax = syntax;
        this.abbr = abbr;
    }

    @Override
    public Integer getMassNumber() { return mass; }

    @Override
    public Integer getAtomicNumber() { return atom; }

    @Override
    public Integer getCharge() { return charge; }

    @Override
    public HashMap<String, Integer> getAtomCount() { return new HashMap<>(); }

    @Override
    abstract public boolean equals(Object o);

    @Override
    public String toString() { return "\\" + syntax; }

    @Override
    public String getDotId() { return syntax + "_" + dotId; }

    @Override
    public String getDotCode()
    {
        StringBuilder result = new StringBuilder();

        result.append("\t");
        result.append(getDotId());

        result.append(" [label=\"{&zwj;&zwj;&zwj;&zwj;" + name + "&zwnj;|\\n");
        result.append(getDotString());

        result.append("\\n\\n|&zwj;&zwj;&zwj;atomic #&zwnj;: " + atom);

        result.append("\\n|&zwj;&zwj;&zwj;mass #&zwnj;: " + mass);

        result.append("}\",color=\"#4c7fbe\"];\n");
        return result.toString();
    }

    @Override
    public String getDotString()
    {
        return "&zwj;&zwj;" + mass + "&zwnj;&zwj;" + atom + "&zwnj;" + abbr;
    }
}
