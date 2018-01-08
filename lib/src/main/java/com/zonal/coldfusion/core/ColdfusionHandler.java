package com.zonal.coldfusion.core;

import java.lang.reflect.Method;
import java.util.HashMap;

import coldfusion.bootstrap.BootstrapClassLoader;
import coldfusion.runtime.Struct;
import coldfusion.runtime.StructWrapper;

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

    /** Test method to assure the compiled .jar is invoked correctly. (It should be placed in your <ColdFusion-Home>/cfusion/wwwroot/WEB-INF/lib/* directory.)*/
    @SuppressWarnings("unused") public final boolean assertVisibility() {
        // Print to the console.
        System.out.println(this.getClass().getSimpleName() + " detected trigger.");
        // Assert success.
        return true;
    }

    /** Invokable method; can be used to route a callback method. */
    @SuppressWarnings("unused") public  final Object invoke(final Object pTemplateProxy, final Object pPageContext, final Object pCallbackMethod, final Object pDataIn) {
        // Declare the ClassLoader.
        Thread.currentThread().setContextClassLoader(BootstrapClassLoader.instance());
        // Declare a StructMapper.
        final StructWrapper lStructWrapper = new StructWrapper();
        // Enter error-susceptible body.
        try {
            // Attempt to load the TemplateProxy and Struct classes from the runtime implementation of ColdFusion.
            final Class lC = BootstrapClassLoader.instance().loadClass("coldfusion.runtime.TemplateProxy");
            final Class lD = BootstrapClassLoader.instance().loadClass("javax.servlet.jsp.PageContext");
            // Cast the dependencies.
            final Object lTemplateProxy = lC.cast(pTemplateProxy);
            final Object lPageContext   = lD.cast(pPageContext);
            // Declare response for callback.
            final Object[] lResponse     = new Object[]{ pDataIn + " " + "world!"};
            // Fetch the invocation method for TemplateProxys (used to communicate back to ColdFusion).
            final Method  lMethod        = lTemplateProxy.getClass().getMethod(ColdfusionHandler.HANDLE_METHOD_INVOCATION_TEMPLATEPROXY, String.class, Object[].class, lD);
            // Invoke the callback.
            lMethod.invoke(lTemplateProxy, pCallbackMethod, lResponse, lPageContext);
            // Define the mapping.
            lStructWrapper.setMap(new HashMap() { {
                // Buffer response data.
                this.put("response", "success");
                this.put("code", "1");
                this.put("error", "Dispatched to " + pCallbackMethod + "!");
            } });
            // Return the failure case.
            return new Struct(lStructWrapper);
        }
        catch(final Throwable pThrowable) {
            // Define the mapping.
            lStructWrapper.setMap(new HashMap() { {
                // Buffer response data.
                this.put("response", "error");
                this.put("code", "0");
                this.put("error", "Failed to dispatch. ");
            } });
            // Return the failure case.
            return new Struct(lStructWrapper);
        }
    }

}