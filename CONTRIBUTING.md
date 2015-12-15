# Contributing to nfscan

:+1::tada: First off, thanks for taking the time to contribute! :tada::+1:

The following is a set of guidelines for contributing to nfscan and its packages,
which are hosted in the [nfscan Organization](https://github.com/nfscan) on GitHub.
These are just guidelines, not rules, use your best judgment and feel free to
propose changes to this document in a pull request.

Contributing Code
=================

* A good patch:

  * is clear.
  * works across all supported versions of Java (1.7 >).
  * has comments included as needed.

* A test case that demonstrates the previous flaw that now passes
  with the included patch.
* If it adds/changes a public API, it must also include documentation
  for those changes.
* Must be appropriately licensed (MIT).


Reporting An Issue/Feature
==========================

* Check to see if there's an existing issue/pull request for the
  bug/feature. All issues are at https://github.com/nfscan/nfscan/issues
  and pull reqs are at https://github.com/nfscan/nfscan/pulls.
* If there isn't an existing issue there, please file an issue. The ideal
  report includes:

  * A description of the problem/suggestion.
  * How to recreate the bug.
  * If relevant, including the versions of your:

    * Java version
    * Operational System
    * AWS region
    * Optionally of the other dependencies involved

  * If possible, create a pull request with a (failing) test case demonstrating
    what's wrong. This makes the process for fixing bugs quicker & gets issues
    resolved sooner.
