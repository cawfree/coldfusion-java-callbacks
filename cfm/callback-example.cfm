<!--- Alex Thomas @Cawfree, 2018. --->
<!--- You *must* define a mapping for 'callbacks' in your Adiministrator Panel. --->

<cfset inv = createObject("component", "callbacks/cfc/invokable").doInvoke(GetPageContext()) /> 

<cfdump var="#inv#"/>