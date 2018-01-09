package com.zonal.coldfusion.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import coldfusion.bootstrap.BootstrapClassLoader;

/**
 * Author: Alexander Thomas (@Cawfree), 2018.
 *
 * Defines a basic asynchronous communication route between Coldfusion and Java, and back again!
 * Building this program will generate an executable 'zonal.jar' in the lib/build/libs directory.
 * Migrate the project into your wwwroot directory and access via http://localhost:8500/cf-callbacks/cfm/callback-example.cfm.
 * Note that for Coldfusion to detect and load the generated jar, the process must be stopped and started again.
 */
public final class ColdfusionHandler {

    /* Static Declarations. */
    private static final String HANDLE_METHOD_INVOCATION_TEMPLATEPROXY = "invoke";
    private static final String HANDLE_METHOD_INVOCATION_STRUCTWRAPPER = "setMap";
    private static final String RESPONSE_SUCCESS                       = "success";
    private static final String RESPONSE_ERRONEOUS                     = "error";
    private static final String FIELD_RESPONSE                         = "response";
    private static final String FIELD_CAUSE                            = "cause";
    private static final String FIELD_TARGET                           = "target";

    /* Dynamic Reflection Classes. (Treat as final!) */
    private static Class CLASS_DYNAMIC_TEMPLATEPROXY = null;
    private static Class CLASS_DYNAMIC_PAGECONTEXT   = null;
    private static Class CLASS_DYNAMIC_STRUCTWRAPPER = null;
    private static Class CLASS_DYNAMIC_STRUCT        = null;

    /* Prepare Static Dependencies. */
    static {
        try {
            // Initialize reflection classes. (Although we don't package these within the compiled .jar, these should all be made available by the Coldfusion runtime.)
            ColdfusionHandler.CLASS_DYNAMIC_TEMPLATEPROXY = BootstrapClassLoader.instance().loadClass("coldfusion.runtime.TemplateProxy");
            ColdfusionHandler.CLASS_DYNAMIC_PAGECONTEXT   = BootstrapClassLoader.instance().loadClass("javax.servlet.jsp.PageContext");
            ColdfusionHandler.CLASS_DYNAMIC_STRUCTWRAPPER = BootstrapClassLoader.instance().loadClass("coldfusion.runtime.StructWrapper");
            ColdfusionHandler.CLASS_DYNAMIC_STRUCT        = BootstrapClassLoader.instance().loadClass("coldfusion.runtime.Struct");
        }
        catch(final ClassNotFoundException pClassNotFoundException) {
            // Print the Stack Trace.
            pClassNotFoundException.printStackTrace();
            /** TODO: Log a warning somewhere to show that the configuration was in error. */
        }
    }

    /** Invokable method; can be used to route a callback method. */
    @SuppressWarnings("unused") public  final Object invoke(final Object pTemplateProxy, final Object pPageContext, final Object pCallbackMethod, final Object pDataIn) {
        // Declare the ClassLoader.
        Thread.currentThread().setContextClassLoader(BootstrapClassLoader.instance());
        // Declare Response Map.
        final Map <String, Object> lResponseMap = new HashMap<>();
        // Enter error-susceptible body.
        try {
            // Cast the dependencies.
            final Object   lTemplateProxy = ColdfusionHandler.CLASS_DYNAMIC_TEMPLATEPROXY.cast(pTemplateProxy);
            final Object   lPageContext   = CLASS_DYNAMIC_PAGECONTEXT.cast(pPageContext);
            // Declare response for callback.
            final Object[] lResponse      = new Object[]{ pDataIn + " " + "world!"};
            // Allocate a StructWrapper.
            final Object   lStructWrapper = CLASS_DYNAMIC_STRUCTWRAPPER.newInstance();
            // Fetch the 'setMap' method.
            final Method   lSetMap        = lStructWrapper.getClass().getMethod(ColdfusionHandler.HANDLE_METHOD_INVOCATION_STRUCTWRAPPER, Map.class);
            // TODO why does this throw an exception?
            try {
                // Fetch the invocation method for TemplateProxys (used to communicate back to ColdFusion).
                final Method  lMethod        = ColdfusionHandler.CLASS_DYNAMIC_TEMPLATEPROXY.getMethod(ColdfusionHandler.HANDLE_METHOD_INVOCATION_TEMPLATEPROXY, String.class, Object[].class, ColdfusionHandler.CLASS_DYNAMIC_PAGECONTEXT);
                // Invoke the callback.
                lMethod.invoke(lTemplateProxy, pCallbackMethod, lResponse, lPageContext);
                // Define that we succeeded in the invocation.
                lResponseMap.put(ColdfusionHandler.FIELD_RESPONSE, ColdfusionHandler.RESPONSE_SUCCESS);
                // Print our reference to the callback method.
                lResponseMap.put(ColdfusionHandler.FIELD_TARGET,   pCallbackMethod);
            }
            catch(final Throwable pThrowable) {
                // Print the Stack Trace.
                pThrowable.printStackTrace();
                // Declare the response.
                lResponseMap.put(ColdfusionHandler.FIELD_RESPONSE, ColdfusionHandler.RESPONSE_ERRONEOUS);
                lResponseMap.put(ColdfusionHandler.FIELD_CAUSE, pThrowable.getCause().toString());
            }
            // Define the data to encapsulate within the StructWrapper.
            lSetMap.invoke(lStructWrapper, lResponseMap);
            // Return the Struct.
            return ColdfusionHandler.CLASS_DYNAMIC_STRUCT.getConstructor(ColdfusionHandler.CLASS_DYNAMIC_STRUCTWRAPPER).newInstance(lStructWrapper);
        }
        catch(final Throwable pThrowable) {
            // Print the Stack Trace.
            pThrowable.printStackTrace();
        }
        // Return the failure case; i.e, there is nothing we could return.
        return null;
    }

}