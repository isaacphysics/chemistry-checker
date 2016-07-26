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

/**
 * A tuple class compatible with hash tables.
 * Created by Ryan on 23/06/2016.
 *
 * @param <A> Type of the first element in pair.
 * @param <B> Type of the second element in pair.
 */
final class Pair<A, B> {

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
    Pair(final A first, final B second) {
        super();
        this.first = first;
        this.second = second;
    }

    @Override
    public int hashCode() {

        int hashFirst;
        int hashSecond;

        if (first != null) {
            hashFirst = first.hashCode();
        } else {
            hashFirst = 0;
        }

        if (second != null) {
            hashSecond = second.hashCode();
        } else {
            hashSecond = 0;
        }

        return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    @Override
    public boolean equals(final Object other) {

        if (other instanceof Pair) {

            Pair otherPair = (Pair) other;

            return (this.first == otherPair.first
                    || (this.first != null && otherPair.first != null && this.first.equals(otherPair.first)))

                   && (this.second == otherPair.second
                    || (this.second != null && otherPair.second != null && this.second.equals(otherPair.second)));
        }

        return false;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    /**
     * Getter method. Returns first element of pair.
     *
     * @return First element of pair.
     */
    public A getFirst() {
        return first;
    }

    /**
     * Setter method. Sets first element of pair.
     *
     * @param first Value that you want first element of pair to be set to.
     */
    public void setFirst(final A first) {
        this.first = first;
    }

    /**
     * Getter method. Returns second element of pair.
     *
     * @return Second element of pair.
     */
    public B getSecond() {
        return second;
    }

    /**
     * Setter method. Sets second element of pair.
     *
     * @param second Value that you want second element of pair to be set to.
     */
    public void setSecond(final B second) {
        this.second = second;
    }
}
