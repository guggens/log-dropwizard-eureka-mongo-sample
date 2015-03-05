package com.yammer.breakerbox.service.views;

import com.yammer.breakerbox.service.core.Instances;
import io.dropwizard.views.View;

public abstract class NavbarView extends View {
    protected NavbarView(String templateName) {
        super(templateName);
    }

    public Iterable<String> getClusters() {
        return Instances.clusters();
    }

    public Iterable<String> getNoMetaClusters() {
        return Instances.noMetaClusters();
    }
}