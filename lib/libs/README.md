You must ensure that your ColdFusion installation's `cfmx_bootstrap.jar` is placed into this directory as a compilation dependency. Note tha this `.jar` will _not_ be included in the compiled build; it will only be referenced to via the ColdFusion's installation at runtime, as a means to load other in-memory dependencies.