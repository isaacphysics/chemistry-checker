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
 * from the specification file 'src/main/jflex/ChemistryLexer.lex'
 */
%%
%class ChemistryLexer
%cup

/* comments */
    LineTerminator = \r|\n|\r\n
    InputCharacter = [^\r\n]

    Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

    TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
    // Comment can be the last line of the file, without line terminator.
    EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
    DocumentationComment = "/**" {CommentContent} "*"+ "/"
    CommentContent       = ( [^*] | \*+ [^/*] )*
%%
    /**
     * Statement/Equation separator
     */
    ";"                             { return new Symbol(sym.END); }

    /* Arrows */
    "<=>"                           { return new Symbol(sym.DTO, DoubleArrow.getDoubleArrow()); }
    "->"                            { return new Symbol(sym.TO, SingleArrow.getSingleArrow()); }

    /**
     * Charges:
     * In form ^{+NUM}, ^{-NUM}, ^{+}, or ^{-}.
     */
    \^\{[1-9][0-9]*[+-]\}           { String s = yytext();
                                        s = s.charAt(s.length() - 2) + s.substring(2, s.length() - 2);
                                        // System.out.println(s); // s = correctly formatted integer
                                        return new Symbol(sym.CHARGE, Integer.parseInt(s)); }
    \^\{"+"\}                       { return new Symbol(sym.CHARGE, 1); }
    \^\{"-"\}                       { return new Symbol(sym.CHARGE, -1); }

    /**
     * Mass numbers:   ^{NUM}.
     * Atomic numbers: _{NUM}.
     */
    \^\{([1-9][0-9]*|0)\}           { String s = yytext();
                                        s = s.substring(2, s.length() - 1);
                                        //System.out.println(s); // s = correctly formatted integer
                                        return new Symbol(sym.SUP, Integer.parseInt(s)); }
    "_{"([1-9][0-9]*|0)\}
                                    { String s = yytext();
                                        s = s.substring(2, s.length() - 1);
                                        //System.out.println(s); // s = correctly formatted integer
                                        return new Symbol(sym.SUB, Integer.parseInt(s)); }

    "_{-"[1-9][0-9]*\}              { String s = yytext();
                                        s = s.substring(2, s.length() - 1);
                                        //System.out.println(s); // s = correctly formatted integer
                                        return new Symbol(sym.SUB, Integer.parseInt(s)); }

    /**
     * Numbers without leading zeroes.
     * Usually used for coefficients in chemical formulas.
     */
    [1-9][0-9]*                     { return new Symbol(sym.NUMBER, Integer.parseInt(yytext())); }

    /**
     * Fractions. Scary things with ugly regex that should not be used as coefficients.
     * But there are physics students.
     */
    \\frac\{[1-9][0-9]*\}\{[1-9][0-9]*\}
                                    { String s = yytext();
                                        s = s.substring(6, s.length() - 1);
                                        String[] twoPart = s.split("\\}\\{");
                                        // System.out.printf("Left: %s\nRight: %s\n", twoPart[0], twoPart[1]);
                                        return new Symbol(sym.FRACTION, new
                                                        FracCoeff(Integer.parseInt(twoPart[0]),
                                                        Integer.parseInt(twoPart[1]))); }

    /**
     * All possible state symbols.
     */
    \((s|l|g|m|aq)\)                { return new Symbol(sym.STATE, yytext().substring(1, yytext().length() - 1)); }

    /**
     * All existing chemical elements.
     */
    [BCFHIKNOPSUVWY]|[ISZ][nr]|
    [ACELP][ru]|A[cglmst]|
    B[aehikr]|C[adefl-os]|
    D[bsy]|Es|F[elmr]|G[ade]|
    H[efgos]|Kr|L[aiv]|M[dgnot]|
    N[abdeiop]|Os|P[abdmot]|
    R[abe-hnu]|S[bcegim]|
    T[abcehilm]|Uu[opst]|Xe|Yb      { return new Symbol(sym.ELEMENT, yytext()); }

    /**
     * Hydrate part (. NUM H2O):
     * The tail part of a hydrated crystal formula.
     */
    "."[\s]*[1-9][0-9]*[\s]*H2O     { String s = yytext(); s = s.replaceAll("\\s+","");
                                      s = s.substring(1, s.length() - 3);
                                      return new Symbol(sym.WATER, Integer.parseInt(s)); }
    "."[\s]*H2O                     { return new Symbol(sym.WATER, 1); }

    /**
     * Plus symbol.
     */
    "+"                             { return new Symbol(sym.PLUS); }

    /**
     * Parenthesis. Commonly used in chemical compounds.
     */
    "("                             { return new Symbol(sym.LPAREN); }
    ")"                             { return new Symbol(sym.RPAREN); }
    "["                             { return new Symbol(sym.LSQUARE); }
    "]"                             { return new Symbol(sym.RSQUARE); }

    /**
     * Syntactic sugar for (chemical) electrons.
     * Physics students seem to like this.
     */
    "e^{-}"                         { return new Symbol(sym.ELECTRON); }
    \\"electron^{-}"                { return new Symbol(sym.ELECTRON); }

    /**
     * Special nuclear particles:
     * Including alpha, beta particles, gamma rays, neutrinoes, electrons and positrons.
     */
    \\"alphaparticle"               { return new Symbol(sym.ALPHA); }
    \\"betaparticle"                { return new Symbol(sym.BETA); }
    \\"gammaray"                    { return new Symbol(sym.GAMMA); }
    \\"neutrino"                    { return new Symbol(sym.NEUTRINO); }
    \\"antineutrino"                { return new Symbol(sym.ANTI_NEUTRINO); }
    \\"electron"                    { return new Symbol(sym.ELECTRON); }
    \\"positron"                    { return new Symbol(sym.POSITRON); }
    \\"neutron"                     { return new Symbol(sym.NEUTRON); }
    \\"proton"                      { return new Symbol(sym.PROTON); }

    /**
     * White space: No special purpose.
     */
    [\s]+                           { /* Ignore all whitespace */ }

    /* Comments */
    {Comment}                       { /* ignore */ }

    /* LaTeX No-Op */
    \{\}                            { /* ignore */ }

    /**
     * Error capturing symbol.
     */
    [^]                             { return new Symbol(sym.ERROR, yytext()); }
