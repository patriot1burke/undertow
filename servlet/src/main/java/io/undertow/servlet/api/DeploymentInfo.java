/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.undertow.servlet.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.undertow.servlet.UndertowServletMessages;

/**
 * Represents a servlet deployment.
 *
 * @author Stuart Douglas
 */
public class DeploymentInfo implements Cloneable {

    private volatile String deploymentName;
    private volatile String contextPath;
    private volatile ClassLoader classLoader;
    private volatile ResourceLoader resourceLoader;
    private volatile ClassIntrospecter classIntrospecter;
    private volatile int majorVersion = 3;
    private volatile int minorVersion;
    private final Map<String, ServletInfo> servlets = new HashMap<String, ServletInfo>();
    private final Map<String, FilterInfo> filters = new HashMap<String, FilterInfo>();
    private final List<ListenerInfo> listeners = new ArrayList<ListenerInfo>();
    private final List<ServletContainerInitializerInfo> servletContainerInitializers = new ArrayList<ServletContainerInitializerInfo>();
    private final List<ThreadSetupAction> threadSetupActions = new ArrayList<ThreadSetupAction>();


    public void validate() {
        if (deploymentName == null) {
            throw UndertowServletMessages.MESSAGES.paramCannotBeNull("deploymentName");
        }
        if (contextPath == null) {
            throw UndertowServletMessages.MESSAGES.paramCannotBeNull("contextName");
        }
        if (classLoader == null) {
            throw UndertowServletMessages.MESSAGES.paramCannotBeNull("classLoader");
        }
        if (resourceLoader == null) {
            throw UndertowServletMessages.MESSAGES.paramCannotBeNull("resourceLoader");
        }
        if (classIntrospecter == null) {
            throw UndertowServletMessages.MESSAGES.paramCannotBeNull("classIntrospecter");
        }

        for (final ServletInfo servlet : this.servlets.values()) {
            servlet.validate();
        }
        for (final FilterInfo filter : this.filters.values()) {
            filter.validate();
        }
    }

    public String getDeploymentName() {
        return deploymentName;
    }

    public DeploymentInfo setDeploymentName(final String deploymentName) {
        this.deploymentName = deploymentName;
        return this;
    }

    public String getContextPath() {
        return contextPath;
    }

    public DeploymentInfo setContextPath(final String contextPath) {
        this.contextPath = contextPath;
        return this;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public DeploymentInfo setClassLoader(final ClassLoader classLoader) {
        this.classLoader = classLoader;
        return this;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public DeploymentInfo setResourceLoader(final ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        return this;
    }

    public ClassIntrospecter getClassIntrospecter() {
        return classIntrospecter;
    }

    public DeploymentInfo setClassIntrospecter(final ClassIntrospecter classIntrospecter) {
        this.classIntrospecter = classIntrospecter;
        return this;
    }

    public DeploymentInfo addServlet(final ServletInfo servlet) {
        servlets.put(servlet.getName(), servlet);
        return this;
    }

    public DeploymentInfo addServlets(final ServletInfo... servlets) {
        for (final ServletInfo servlet : servlets) {
            this.servlets.put(servlet.getName(), servlet);
        }
        return this;
    }

    public DeploymentInfo addServlets(final Collection<ServletInfo> servlets) {
        for (final ServletInfo servlet : servlets) {
            this.servlets.put(servlet.getName(), servlet);
        }
        return this;
    }

    public Map<String, ServletInfo> getServlets() {
        return Collections.unmodifiableMap(servlets);
    }


    public DeploymentInfo addFilter(final FilterInfo filter) {
        filters.put(filter.getName(), filter);
        return this;
    }

    public DeploymentInfo addFilters(final FilterInfo... filters) {
        for (final FilterInfo filter : filters) {
            this.filters.put(filter.getName(), filter);
        }
        return this;
    }

    public DeploymentInfo addFilters(final Collection<FilterInfo> filters) {
        for (final FilterInfo filter : filters) {
            this.filters.put(filter.getName(), filter);
        }
        return this;
    }

    public Map<String, FilterInfo> getFilters() {
        return Collections.unmodifiableMap(filters);
    }

    public DeploymentInfo addListener(final ListenerInfo listener) {
        listeners.add(listener);
        return this;
    }

    public DeploymentInfo addListeners(final ListenerInfo... listeners) {
        this.listeners.addAll(Arrays.asList(listeners));
        return this;
    }

    public DeploymentInfo addListeners(final Collection<ListenerInfo> listeners) {
        this.listeners.addAll(listeners);
        return this;
    }

    public List<ListenerInfo> getListeners() {
        return listeners;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public DeploymentInfo setMajorVersion(final int majorVersion) {
        this.majorVersion = majorVersion;
        return this;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public DeploymentInfo setMinorVersion(final int minorVersion) {
        this.minorVersion = minorVersion;
        return this;
    }

    public DeploymentInfo addServletContainerInitalizer(final ServletContainerInitializerInfo servletContainerInitializer) {
        servletContainerInitializers.add(servletContainerInitializer);
        return this;
    }

    public DeploymentInfo addServletContainerInitalizers(final ServletContainerInitializerInfo... servletContainerInitializer) {
        servletContainerInitializers.addAll(Arrays.asList(servletContainerInitializer));
        return this;
    }

    public DeploymentInfo addServletContainerInitalizers(final List<ServletContainerInitializerInfo> servletContainerInitializer) {
        servletContainerInitializers.addAll(servletContainerInitializer);
        return this;
    }

    public List<ServletContainerInitializerInfo> getServletContainerInitializers() {
        return servletContainerInitializers;
    }

    public DeploymentInfo addThreadSetupAction(final ThreadSetupAction action) {
        threadSetupActions.add(action);
        return this;
    }

    public List<ThreadSetupAction> getThreadSetupActions() {
        return threadSetupActions;
    }

    @Override
    public DeploymentInfo clone() {
        final DeploymentInfo info = new DeploymentInfo()
                .setClassLoader(classLoader)
                .setContextPath(contextPath)
                .setResourceLoader(resourceLoader)
                .setMajorVersion(majorVersion)
                .setMinorVersion(minorVersion)
                .setDeploymentName(deploymentName);

        for (Map.Entry<String, ServletInfo> e : servlets.entrySet()) {
            info.addServlet(e.getValue().clone());
        }

        for (Map.Entry<String, FilterInfo> e : filters.entrySet()) {
            info.addFilter(e.getValue().clone());
        }
        info.listeners.addAll(listeners);
        info.servletContainerInitializers.addAll(servletContainerInitializers);
        info.threadSetupActions.addAll(threadSetupActions);
        return info;
    }


}