### coldfusion-java-callbacks
A simple project setup demonstrating how to route data from ColdFusion through to a custom `.jar`, resulting in a returned response data and the asynchronous invocation of another ColdFusion function.

#### Configuration
  - Clone this repo into `<Coldfusion Directory>/cfusion/wwwroot/`
  - Make a copy of your `cfmx_bootstrap.jar` file from your Coldfusion installation directory into the `./lib/libs` folder of the project directory.
  - Build the project. `./gradlew build`
  - Copy the resulting `.jar` from the `lib/build/libs` directory into `<Coldfusion Directory>/cfusion/wwwroot/WEB-INF/lib`
  - Register this project as a valid **mapping**, using the **Logical Path** `callbacks` and **Directory Path** as the project directory, via the Coldfusion [Administrator Panel](http://localhost:8500/CFIDE/administrator/). (This is used for the _relative_ `.cfc` references made by the `.cfm`.)
  - Stop the ColdFusion Application Server and Start it again. (_This forces ColdFusion to yield the newly added binaries._)
