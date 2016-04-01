/**
 * Copyright 2016 James Sharkey
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

import org.jscience.mathematics.number.LargeInteger;
import org.jscience.mathematics.number.Rational;
import org.jscience.mathematics.structure.VectorSpace;
import org.jscience.mathematics.vector.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class EquationBalancer {

    private static void printMatrix(VectorSpace m) {
        System.out.println(m.toString());
    }

    private static long gcd(long a, long b)
    {
        while (b > 0)
        {
            long temp = b;
            b = a % b; // % is remainder
            a = temp;
        }
        return a;
    }

    private static long lcm(long a, long b)
    {
        return a * (b / gcd(a, b));
    }

    private static long lcm(Vector<Rational> input) {
        long result = input.get(0).getDivisor().longValue();
        for(int i = 1; i < input.getDimension(); i++) result = lcm(result, input.get(i).getDivisor().longValue());
        return result;
    }


    public static ArrayList<Integer> returnCoefficients(EquationStatement eqn) throws Exception {
        if (eqn.containsError()) {
            throw new Exception("Error in equation!");
        }
        // TODO: make this better!
        ArrayList<Integer> coefficients = new ArrayList<Integer>();

        Set<String> keysLeft = eqn.getLeftExpression().getAtomCount().keySet();
        Set<String> keysRight = eqn.getRightExpression().getAtomCount().keySet();
        if (!keysLeft.containsAll(keysRight)) {
            throw new Exception("Mismatched elements!");
        }
        ArrayList<String> keys = new ArrayList<String>();
        keys.addAll(keysLeft);
        System.out.println("Keys; " + keys.toString());

        int rows = keys.size() + 1;
        int cols = eqn.getLeftExpression().getTerms().size() + eqn.getRightExpression().getTerms().size();
        // Want to solve the equation M x = z for x
        Rational[][] mArray = new Rational[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mArray[i][j] = Rational.ZERO;
            }
        }


        SparseVector<Rational> z = SparseVector.valueOf(rows, Rational.ZERO , rows - 1, Rational.ONE);
        mArray[rows - 1][0] = Rational.ONE;

        System.out.println("Rows: " + rows + " Cols: " + cols);

        System.out.println("Terms:");
        int offset = eqn.getLeftExpression().getTerms().size();
        for (int j = 0; j < offset; j++) {
            AbstractTerm t = eqn.getLeftExpression().getTerms().get(j);
            System.out.println("\t" + t.toString());
            if (t instanceof Term) {
                Term term = (Term) t;
                int N = 1;//term.getNumber();
                HashMap<String, Integer> atomCount = term.getAtomCount();

                for (String k : keys) {
                    if (atomCount.containsKey(k)) {
                        int i = keys.indexOf(k);
                        int n = atomCount.get(k);

                        mArray[i][j] = Rational.valueOf(N*n, 1);
                    }
                }
            }
        }

        // TODO: ways to avoid multiple for loops?
        for (int j = offset; j < cols; j++) {
            AbstractTerm t = eqn.getRightExpression().getTerms().get(j - offset);
            System.out.println("\t" + t.toString());
            if (t instanceof Term) {
                Term term = (Term) t;
                int N = -1;// * term.getNumber();
                HashMap<String, Integer> atomCount = term.getAtomCount();

                for (String k : keys) {
                    if (atomCount.containsKey(k)) {
                        int i = keys.indexOf(k);
                        int n = atomCount.get(k);

                        mArray[i][j] = Rational.valueOf(N*n, 1);
                    }
                }
            }
        }

        Matrix M = DenseMatrix.valueOf(mArray);
        System.out.println("Matrix:");
        printMatrix(M);
        System.out.println("Zeros:");
        printMatrix(z);
        System.out.println("Solution:");
        Vector<Rational> solution = M.solve(z);
        long factor = lcm(solution);
        solution = solution.times(Rational.valueOf(factor, 1));
        printMatrix(solution);

        for (int i = 0; i < solution.getDimension(); i++) {
            // TODO: is there a better way to cast down to int?
            coefficients.add((int) solution.get(i).longValue());
        }
        return coefficients;
    }
}
