# SMC Gradle Plugin Examples
The [SMC Gradle Plugin](https://github.com/ryansgot/smc-gradle-plugin) integrates the [State Machine Compiler](http://smc.sourceforge.net/) with the Gradle build system. This project contains examples of an [Android-Java project](https://github.com/ryansgot/smc-gradle-plugin-example/tree/master/androidjava) and a [Java project](https://github.com/ryansgot/smc-gradle-plugin-example/tree/master/javaonly). The SMC Gradle Plugin will add gradle tasks which generate the following State Machine:

![Turnstile State Diagram](img/turnstile.png?raw=true "Turnstile")

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
`
$ ./gradlew :javaonly:compileJava
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

## Generated HTML Documentation
Both sample projects have chosen to output HTML table documentation. You can do this by setting the `smc` extension property `outputHtmlTable = true`. This will output an HTML file containing like the following to the `build/outputs/sm` directory for a java project and `build/outputs/sm/<variantname>` directory for android:
<dl>
    <table align=center border=3 cellspacing=2 cellpadding=2>
      <caption align="top">
        MainMap Finite State Machine
      </caption>
      <tr>
        <th rowspan=2>
          State
        </th>
        <th colspan=2>
          Actions
        </th>
        <th colspan=3>
          Transition
        </th>
      </tr>
      <tr>
        <th>
          Entry
        </th>
        <th>
         Exit
        </th>
        <th>
          coin
        </th>
        <th>
          pass
        </th>
        <th>
          <b>Default</b>
        </th>
      </tr>
      <tr>
        <td>
          Locked
        </td>
        <td>
        </td>
        <td>
        </td>
        <td>
          <pre>
  Unlocked
  {
    unlock();
  }
          </pre>
        </td>
        <td>
          <pre>
  Locked
  {
    alarm();
  }
          </pre>
        </td>
        <td>
        </td>
      </tr>
      <tr>
        <td>
          Unlocked
        </td>
        <td>
        </td>
        <td>
        </td>
        <td>
          <pre>
  Unlocked
  {
    thankyou();
  }
          </pre>
        </td>
        <td>
          <pre>
  Locked
  {
    lock();
  }
          </pre>
        </td>
        <td>
        </td>
      </tr>
    </table>
</dl>

## Generated Graphviz Graph Documentation
Both sample projects have chosen to output an SM Graphviz (.dot) file for documentation. You can do this by setting the `smc` extension property `graphVizLevel` to 0, 1, or 2, corresponding to the [level of detail that will be put into the graph](http://smc.sourceforge.net/SmcManSec10.htm). This will output a .dot file containing the graph  diagram into the `build/outputs/sm` directory for a java project and `build/outputs/sm/<variantname>` directory for android.

However, this file in and of itself is not the graph. The graph must then be generated. Generating the actual graph requires the [graphviz](https://www.graphviz.org/) software. Once you have graphviz, you can generate to .png (many other formats are possible) by running a command like the following, which I ran to generate the image above:

`
$ dot -Tpng androidjava/build/outputs/sm/debug/Turnstile_sm.dot -o turnstile.png
`
