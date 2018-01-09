<!--- Alex Thomas @Cawfree, 2018. --->

<!--- A single component which can be used to generate a Java-side invocation and also receive one. --->
<!--- Note that the invocation transmission and reception sources can be separate classes altogether. --->
<cfcomponent>

  <!--- The return method of invocation; this is the method we specify for Java to call back to. --->
  <cffunction name="onInvoked" returntype="struct">
    <!--- Define that we'll accept an argument from the callback. --->
    <cfargument name="msg" required="Yes">
      <!--- Here, just for demonstration we'll write the data returned to us by the callback to a file in the working directory. --->
      <cffile action = "write" file = "C:\ColdFusion2016\cfusion\wwwroot\cf-callbacks\success-from-cf.txt" output = "#msg#">
      <!--- Return data. --->
      <cfreturn { Key1 = "#msg#" }>
   </cffunction> 
 </cfcomponent>