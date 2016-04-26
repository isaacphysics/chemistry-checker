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

import java.util.HashMap;

public class ExpressionStatement extends Statement implements Countable {

    private Expression expr;

    public ExpressionStatement(Expression e) {
        expr = e;
    }

    @Override
    public String toString() {
        return expr.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ExpressionStatement) {
            ExpressionStatement other = (ExpressionStatement) o;
            return this.expr.equals(other.expr);
        }
        return false;
    }

    public boolean containsError() {
        return expr.containsError();
    }

    public HashMap<String, Integer> getAtomCount() {
        return expr.getAtomCount();
    }

    public Integer getCharge() {
        return expr.getCharge();
    }

    public boolean sameMolecules(Statement statement) {
        if (statement instanceof ExpressionStatement) {
            ExpressionStatement exprStatement = (ExpressionStatement) statement;
            return expr.containsAll(exprStatement.expr) && exprStatement.expr.containsAll(expr);
        } else {
            return false;
        }
    }

    public Expression getExpression() {
        return this.expr;
    }

    public String getDotCode() {
        StringBuilder result = new StringBuilder();
        result.append("digraph chemical_syntax_tree {\n");
        result.append("\tnode [shape=record,penwidth=2,splines=ortho];\n\n");
        result.append(expr.getDotCode());
        result.append("}\n");
        return result.toString();
    }

}
