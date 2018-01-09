<!--- Alex Thomas @Cawfree, 2018. --->

<!--- A single component which can be used to generate a Java-side invocation and also receive one. --->
<!--- Note that the invocation transmission and reception sources can be separate classes altogether. --->
<cfcomponent>

  <!--- A function exposed to the parent .cfm, which serves as the entry point for executing the .jar.  --->
  <cffunction name="doInvoke" returntype="struct">
    <!--- Accepts a PageContext as an argument, which can be utilized back on the Java-side. --->
    <cfargument name="ctx" required="Yes">
    <cfargument name="lis" required="Yes">
    <!--- Allocate an instance of the ColdfusionHandler; this is the Java-side implementation of the --->
  	<cfscript>
  		CFCInvokerObj = createObject("java","com.zonal.coldfusion.core.ColdfusionHandler");
  	</cfscript>

    <!--- Calls the 'invoke' method on the Java library, passing the data 'hello,' and specifying a return call to 'onInvoked'  --->
  	<cfreturn "#CFCInvokerObj.invoke(lis, ctx, 'onInvoked', 'hello,')#"/>

   </cffunction> 

 </cfcomponent>