<!--- Alex Thomas @Cawfree, 2018. --->

<!--- Print the result of invocation. --->
<cfdump var="#createObject("component", "callbacks/cfc/invokable").doInvoke(GetPageContext(), createObject("component", "callbacks/cfc/listener"))#"/>