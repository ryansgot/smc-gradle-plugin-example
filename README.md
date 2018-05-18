# SMC Gradle Plugin Examples
The [SMC Gradle Plugin](https://github.com/ryansgot/smc-gradle-plugin) integrates the [State Machine Compiler](http://smc.sourceforge.net/) with the Gradle build system. This project contains examples of an [Android-Java project](https://github.com/ryansgot/smc-gradle-plugin-example/tree/master/androidjava) and a [Java project](https://github.com/ryansgot/smc-gradle-plugin-example/tree/master/javaonly). Both projects have gradle tasks which generate the following State Machine:
![Turnstile State Diagram](/img/turnstile?raw=true "Turnstile")

## androidjava project
Code generation works in one command:
`
$ ./gradlew :androidjava:clean :androidjava:compileDebugJava
`
This will generate the `TurnstileContext` class in the `build/generated/source/sm/debug/com/fsryan/example/smcgradleplugin/android` directory because:
- The compileDebugJava command was run (the smc gradle plugin establishes the correct task dependencies for you)
- The `Turnstile.sm` file is located in the `src/main/sm/com/fsryan/example/smcgradleplugin/android` directory.

So much like [AIDL](https://developer.android.com/guide/components/aidl), the directory structure of the state machine description file (.sm file) determines the output of the generated .java file. The source set for the variant is appropriately updated with this generated directory.

## java only project
You need two separate commands:
`
$ ./gradlew :javaonly:clean :javaonly:generateStateMachineSources; ./gradlew compileJava
`
This will generate the `TurnstileContext` class in the `build/generated-src/sm/com/fsryan/example/smcgradleplugin` directory because:
- The `Turnstile.sm` file is located in the `src/main/sm/com/fsryan/example/smcgradleplugin` directory--the directory structure of the state machine description file (.sm file) determines the output of the generated .java file.
- The plugin will only output the generated source to your projects build directory

The generated TurnstileContext source is added to the main source set for you by the plugin.

## Auto download+extract SMC necessary components
Typically, using SMC, you would have to download a zip file containing the `Smc.jar` file (the one containing the java appliation which generates code) and `statemap.jar` file (containing the class dependencies of the generated code). You would then extract these binaries, add the statemap.jar library to your project's classpath, and run the Smc.jar wth the correct arguments to generate your state machine into the correct directory. The Plugin, however, does all of this for you when configured to the needs of your project. See [an example smc extension configuration](https://github.com/ryansgot/smc-gradle-plugin-example/blob/master/javaonly/build.gradle#L29-L40) for more details.

So, supposing you did not have the `Smc.jar` file or the `statemap.jar` file, then you could still use the smc gradle plugin. This project has both of those binaries stored in source control, but you can force the download+extract behavior in this project by adding the `-PdownloadSmc` and `-PdownloadStatemap` options to the command line as below:
`
$ ./gradlew :androidjava:clean :androidjava:compileDebugJava -PdownloadSmc -PdownloadStatemap
` 