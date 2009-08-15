

*** Introduction

This is a work-in-progress for the Vending Machine example, as highlighted by the Lambda Lounge.

This example uses Groovy, but in a fairly straight-up, Java-esque way.
Not a lot of dynamic or functional style in this project.

However, it does use the evaluation of strings (as code), and the testing tools.

*** Requirements

-- assumes Java 5+ is available
-- assumes Groovy and Gant are available

*** Deviations from spec

-- the only input is a file, though this file allows assertions
-- there is no output per se
-- may not return change in the least number of coins?

*** Themes

-- the input lines contain lists and maps that are evaluated by Groovy: only actions are parsed by the example
-- the input can contain assertions of state. extremely useful
-- in theory, a set of input files could be used against other implementations to see if the examples match


