# Events4J

http://www.object-zoo.net/events4j  
[events4j@object-zoo.net](mailto:events4j@object-zoo.net)

Events4J is a simple and generic Java API that mimics the delegates (Action, Func)
and events known from C# and .NET in Java to facilitate the development of loosely
coupled functional units.

It is open source software released under the terms of the MIT License found in
the file "LICENSE.txt"

## Download
Events4J can be downloaded form the [Releases Page](http://object-zoo.net/events4j/releases).

## Motivation
#### Problems solved:
* Introduce delegates and events as Java types, that are not available in the SDK
* Have minimal dependencies between event provider and consumer unlike traditional interface based Java event listeners
* Provide convenient way to call Java methods asynchronously

#### Design goals:
* Minimum number of interfaces that are still suitable for most situations
* Generic implementation adapts to most use cases
* Allow flexible use through adapters while sticking to the Single Responsibility Principle
* Usable in [GWT](http://code.google.com/intl/en-US/webtoolkit/) applications
* __Zero__ dependencies

#### Non-goals:
* Provide a publish subscribe event bus (maybe [EventBus](http://www.eventbus.org) is something for you)
* Provide automatic wiring through annotations and parameter types

## How To Use It
* Learn by example in the [Tutorial](http://doc.object-zoo.net/events4j/tutorial)
* Browse the [JavaDoc](http://doc.object-zoo.net/events4j/api/)

## Development Tools

* [Eclipse](http://www.eclipse.org/)
* [FindBugs](http://findbugs.sourceforge.net/) (Static code analysis)
* [EclEmma](http://www.eclemma.org/) (Code Coverage)
* [Infinitest](http://infinitest.github.com/) (Continuous Testing)
* [Ant](http://ant.apache.org/) (Automated build)

## Release History
* __1.2.1__  Fixes
    - Fixed GWT support
* __1.2__  Extensions
    - Added asynchronous action calling
    - Fixes in build script
* __1.1__  Repackaged, Extensions
    - Added GWT support
    - Splitted JARs into standard, asynchronous and GWT
    - Added Event support for events with single subscribers only
* __1.0__  First final release
    - Completed [JavaDoc](http://doc.object-zoo.net/events4j/api/)  
    - Added Executor selection for asynchronous event delegates
* __0.9__  First preview release
