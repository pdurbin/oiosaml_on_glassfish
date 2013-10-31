package com.github.gist.pdurbin.oiosaml_on_glassfish;

import com.sun.org.apache.xalan.internal.xslt.EnvironmentCheck;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Logger;
import javax.inject.Named;

@Named("index")
public class Index {

    private static final Logger logger = Logger.getLogger(Index.class.getCanonicalName());
    String title = "OIOSAML on GlassFish";

    public String getTitle() {
        return title;
    }

    public Index() {
        EnvironmentCheck env = new EnvironmentCheck();
        File out = new File("/tmp/oiosaml_on_glassfish.xalan_envcheck");
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(out);
        } catch (FileNotFoundException ex) {
            logger.info("Error: " + ex.getMessage());
        }
        env.checkEnvironment(printWriter);
    }
}
