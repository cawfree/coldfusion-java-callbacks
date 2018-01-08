<!--- Alex Thomas @Cawfree, 2018. --->

<!--- A single component which can be used to generate a Java-side invocation and also receive one. --->
<!--- Note that the invocation transmission and reception sources can be separate classes altogether. --->
<cfcomponent>

  <!--- A function exposed to the parent .cfm, which serves as the entry point for executing the .jar.  --->
  <cffunction name="doInvoke" returntype="struct">
    <!--- Accepts a PageContext as an argument, which can be utilized back on the Java-side. --->
    <cfargument name="msg" required="Yes">
    <!--- Allocate an instance of the ColdfusionHandler; this is the Java-side implementation of the --->
  	<cfscript>
  		CFCInvokerObj = createObject("java","com.zonal.coldfusion.core.ColdfusionHandler");
  	</cfscript>

  	<cfoutput>
     
     Transmitted 'hello,' to the ColdfusionHandler! <br/> :)

    </cfoutput>

    <!--- Calls the 'invoke' method on the Java library, passing the data 'hello,' and specifying a return call to 'onInvoked'  --->
  	<cfdump var="#CFCInvokerObj.invoke(this, msg, 'onInvoked', 'hello,')#"/>

   </cffunction> 

  <!--- The return method of invocation; this is the method we specify for Java to call back to. --->
  <cffunction name="onInvoked" returntype="struct">
    <!--- Define that we'll accept an argument from the callback. --->
    <cfargument name="msg" required="Yes">
      <!--- Here, just for demonstration we'll write the data returned to us by the callback to a file in the working directory. --->
	    <cffile action = "write" file = "C:\ColdFusion2016\cfusion\wwwroot\cf-callbacks\success-from-cf.txt" output = "#msg#">
   </cffunction> 

 </cfcomponent>