## coldfusion-java-callbacks

This project demonstrates how we may pass arbitrary data from the ColdFusion environment into a custom `.jar`. The method may either return results directly back in the ColdFusion environment, or defer execution to another function in ColdFusion in an event-driven style.

#### Configuration
To test this project, ensure the following:
  - The project exists within the `<ColdFusion-Home>/cfusion/wwwroot/` directory.
  - The compiled `.jar` is copied into `<ColdFusion-Home>/cfusion/wwwroot/WEB-INF/lib/`, where all of ColdFusion's runtime `.jar`s are held.
  - The ColdFusion Application Service is **stopped** and then **restarted**. (This forces ColdFusion to recognize the new binaries.
  - Register the project as a valid _mapping_ in the ColdFusion [administrator panel](http://localhost:8500/CFIDE/administrator/) and add the **Logical Path** 	`/callbacks` 	and **Directory Path** `C:/ColdFusion2016/cfusion/wwwroot/cf-callbacks/`.
 
