# BaseApp 

A template for multi modular applications written in kotlin with CI integration. 

This app is modularized by functionality. Core, App and Feature. 
While the app is the runnable instance of the project, it depends on feature modules which are in turn 
dependent on core modules and on each other when needed. I have also set up a central dependency management
system, preventing cyclic dependencies.