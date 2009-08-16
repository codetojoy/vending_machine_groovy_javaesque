

*** Introduction

This is a work-in-progress for the Vending Machine example, as highlighted by the Lambda Lounge.

This example uses Groovy, but in a fairly straight-up, Java-esque way.
Not a lot of dynamic or functional style in this project.

However, it does use the evaluation of strings (as code), and the testing tools.

*** Requirements

-- assumes Java 5+ is available
-- assumes Groovy and Gant are available

http://groovy.codehaus.org/

http://gant.codehaus.org/

*** Deviations from spec

-- the only input is a file, though this file allows assertions
-- there is no output per se

*** Themes

-- the input lines contain lists and maps that are evaluated by Groovy: only actions are parsed by the example
-- the input can contain assertions of state. extremely useful
-- in theory, a set of input files could be used against other implementations to see if the examples match
-- MoneyState is immutable, and intended to allow the developer to visualize "slots" 
   e.g. new MoneyState(1,2,3,4) is 1 dollar, 2 quarters, 3 dimes, and 4 nickels

*** lessons learned

-- testing is essential here
-- you really have to trust the tests (see above). they become intrinsic to the experience
-- very old idea, but evaluating data as code is extremely powerful
-- it takes time to set up Gant, JUnit, etc, for the first time, but vital