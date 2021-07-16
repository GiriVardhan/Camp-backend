package com.jbent.peoplecentral.velocity;
import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.velocity.VelocityToolboxView;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

/**
 * Velocty view resolver with support for velocity toolbox configuration.
 */
public class VelocityToolboxViewResolver extends VelocityViewResolver {
    private String toolboxConfigLocation;

  	/**
	 * Sets default viewClass to VelocityToolboxView.
	 * @see #setViewClass
	 */
	public VelocityToolboxViewResolver() {
		setViewClass(VelocityToolboxView.class);
	}

    /**
     * Set a "toolboxConfigLocation", for example "/WEB-INF/toolbox.xml",
     * to automatically load a Velocity Tools toolbox definition file and expose
     * all defined tools in the specified scopes to the resolved view. If no config
     * location is specified, no toolbox will be loaded and exposed.
     *
     * The specfied location string needs to refer to a ServletContext
     * resources, as expected by ServletToolboxManager which is part of
     * the view package of Velocity Tools.
     *
     * @see org.apache.velocity.tools.view.servlet.ServletToolboxManager#getInstance
     */
    public void setToolboxConfigLocation(String toolboxConfigLocation) {
        this.toolboxConfigLocation = toolboxConfigLocation;
    }

    @Override
    protected View loadView(String viewName, Locale locale) throws Exception {
        VelocityToolboxView view = null;
		view = (VelocityToolboxView)super.loadView(viewName, locale);
        view.setToolboxConfigLocation(toolboxConfigLocation);
        return view;
	}
}