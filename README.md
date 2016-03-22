# chemistry-checker
Sister project to "equality-checker" for checking chemical formula.

## Installation / Setup Instructions

It needs Maven, which should find the required JFlex and CUP dependencies.

`mvn jflex:generate cup:generate`

Then run the `main(...)` method of the resulting `target\generated-sources\cup\chemistry_checker\parser.java` file. For now.
