# chemistry-checker

Historic project for checking equivalence of chemical formula on Isaac Physics.

This project has been superseded by a new Node-based project: [`chemistry-checker-js`](https://github.com/isaacphysics/chemistry-checker-js).


## Installation / Setup Instructions

It needs Maven, which should find the required JFlex and CUP dependencies.

`mvn jflex:generate cup:generate`

Then run the `main(...)` method of the resulting `target\generated-sources\cup\chemistry_checker\parser.java` file.
