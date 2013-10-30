package com.github.gist.pdurbin.oiosaml_on_glassfish;

import javax.inject.Named;

@Named("index")
public class Index {

    String title = "OIOSAML on GlassFish";

    public String getTitle() {
        return title;
    }
}
