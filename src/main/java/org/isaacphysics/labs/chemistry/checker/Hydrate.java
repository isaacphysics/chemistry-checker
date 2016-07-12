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
 * Class for storing water of crystallization.
 * Created by Ryan on 22/06/2016.
 */
public final class Hydrate extends Formula
{
    /**
     * Formula of the dehydrated salt.
     */
    private Compound compound;

    /**
     * Number of water molecules per formula.
     */
    private Integer water_count;

    /**
     * Constructor function for hydrate.
     * @param compound Formula of associated salt compound.
     * @param count Number of water molecules per formula.
     */
    public Hydrate(Compound compound, Integer count)
    {
        this.compound = compound;
        this.water_count = count;
    }

    @Override
    public Integer getCharge()
    {
        return 0;
    }

    @Override
    public HashMap<String, Integer> getAtomCount()
    {
        HashMap<String, Integer> temp = compound.getAtomCount();

        // Put water atoms...
        if (temp.containsKey("H"))
        {
            temp.put("H", temp.get("H") + 2 * water_count);
        }
        else
        {
            temp.put("H", 2 * water_count);
        }

        if (temp.containsKey("O"))
        {
            temp.put("O", temp.get("O") + water_count);
        }
        else
        {
            temp.put("O", water_count);
        }

        return temp;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Hydrate))
            return false;

        Hydrate other = (Hydrate) o;

        return compound.equals(other.compound) && water_count.equals(other.water_count);
    }

    @Override
    public String toString()
    {
        if (water_count > 1)
            return compound.toString() + "." + water_count.toString() + "H2O";
        else
            return compound.toString() + ".H2O";
    }

    @Override
    public String getDotId()
    {
        return "hydrate_" + dotId;
    }

    @Override
    public String getDotCode()
    {
        StringBuilder result = new StringBuilder();

        result.append("\t");
        result.append(getDotId());

        result.append(" [label=\"{&zwj;&zwj;&zwj;&zwj;Hydrate&zwnj;|\\n");
        result.append(getDotString());

        result.append("\\n\\n|&zwj;&zwj;&zwj;water per formula&zwnj;: ");
        result.append(water_count);

        result.append("}\",color=\"#614126\"];\n");

        result.append("\t");
        result.append(getDotId());
        result.append(":s -> ");
        result.append(compound.getDotId());
        result.append(":n;\n");
        result.append(compound.getDotCode());

        return result.toString();
    }

    @Override
    public String getDotString()
    {
        if (water_count > 1)
            return compound.toString() + "&#183;" + water_count.toString() + "H2O";
        else
            return compound.toString() + "&#183;H2O";
    }
}
