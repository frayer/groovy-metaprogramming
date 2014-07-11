Groovy Metaprogramming
======================

About
-----

This is a project with specifications to show off features of Groovy
Metaprogramming.  Your job is to write to code to get them to pass.

The tag "start-point-v1.1" contains all the specifications with no supporting
implementing code.  The working solution is contained in the tag
"solution-v1.1".

Running
-------
Clone this repository and take a branch of the "start-points-v1.1" tag:

    git co -b mywork start-point-v1.1

You can progress in any way you see fit, but I would recommend the following:

    src/test/groovy/org/frayer/MetaObjectProgrammingFacts.groovy
    src/test/groovy/org/frayer/CategorySpec.groovy
    src/test/groovy/org/frayer/MetaClassInjectionSpec.groovy
    src/test/groovy/org/frayer/MemoizeSpec.groovy

In each of these Spock Specs, you'll find methods annotated with @Ignore.
Remove this annotation to ensure the test will fail.  The test can then be
executed with Gradle 2.0 or above with the command:

    gradle test

At any time you can switch to the tag "solution-v1.1" to see a possible
solution to make the test pass.  It may be beneficial to have two working
copies of the code so you can look at both at the same time.  Or you can view
the "solution-v1.1" tag in Github while you point your code editor at your
working branch.
