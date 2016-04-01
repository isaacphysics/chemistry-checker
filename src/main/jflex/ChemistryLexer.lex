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

import java_cup.runtime.Symbol;

/**
 * The ChemistryLexer class is a scanner automatically generated
 * from the specification file 'src/main/jflex/lexer.lex'
 */
%%
%class ChemistryLexer
%cup
%%

    ";"                     { return new Symbol(sym.END); }
    "->"                    { return new Symbol(sym.TO); }
    \^\{[0-9]*[+-]\}        { return new Symbol(sym.CHARGE, new String(yytext())); }
    [0-9]+                  { return new Symbol(sym.NUMBER, new Integer(yytext())); }
    \((s|l|g|aq)\)          { return new Symbol(sym.STATE, new String(yytext().replace("(", "").replace(")", ""))); }
    [BCFHIKNOPSUVWY]|[ISZ][nr]|[ACELP][ru]|A[cglmst]|B[aehikr]|C[adefl-os]|D[bsy]|Es|F[elmr]|G[ade]|H[efgos]|Kr|L[aiv]|M[dgnot]|N[abdeiop]|Os|P[abdmot]|R[abe-hnu]|S[bcegim]|T[abcehilm]|Uu[opst]|Xe|Yb|e        { return new Symbol(sym.ELEMENT, new String(yytext())); }
    "+"                     { return new Symbol(sym.PLUS); }
    "("                     { return new Symbol(sym.LPAREN); }
    ")"                     { return new Symbol(sym.RPAREN); }
    [\s]+                   { /* Ignore all whitespace */ }
    [a-z]+                  { /* System.err.println("Illegal character or element part: "+yytext()); */ }
    .                       { /* System.err.println("Illegal character or element part: "+yytext()); */ }
