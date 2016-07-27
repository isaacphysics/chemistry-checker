# Use http://www.webgraphviz.com/ to generate the actual SVG files, and then use
# Chrome's DevTools to select the SVG element on the page, edit the SVG node as HTML
# and then copy the SVG code into a text file. This allows preservation of the
# non-printable characters; otherwise they get lost along the way to being saved.
#
# This relies on what I guess is a bug in WebGraphviz, which allows the &zwj; character
# to pass through the generation of the output (doesn't work on desktop) - but whilst
# it does still work it's an amazing way of allowing dot code to be used to add fancy
# text formatting without changing the file visably if the formatting can't be applied.

import sys

if len(sys.argv) < 2:
    print "No input file!"
    raise SystemExit
else:
    fname = str(sys.argv[1])
    print fname

f = open(fname, 'r')
filedata = f.read()
f.close()

newdata = filedata.replace("&zwj;&zwj;&zwj;&zwj;", '<tspan style="font-weight:bold">')
newdata = newdata.replace("&zwj;&zwj;&zwj;", '<tspan style="font-style:italic">')
newdata = newdata.replace("&zwj;&zwj;", '<tspan style="font-size:52%;baseline-shift:super">')
newdata = newdata.replace("&zwj;", '<tspan style="font-size:52%;baseline-shift:sub">')
newdata = newdata.replace("&zwnj;", '</tspan>')

f = open(fname, 'w')
f.write(newdata)
f.close()
