/*
 * JPAContainer
 * Copyright (C) 2009 Oy IT Mill Ltd
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.vaadin.addons.jpacontainer.metadata;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * This class represents the metadata of a property. If the property
 * is transient, this is an ordinary JavaBean property consisting of a getter method
 * and optionally a setter method. If the property is persistent, additional
 * information is provided by the {@link PersistentPropertyMetadata} interface.
 *
 * @see ClassMetadata
 * @author Petter Holmström (IT Mill)
 * @since 1.0
 */
public class PropertyMetadata implements Serializable {

    private final String name;
    private final Class<?> type;
    final Method getter;
    final Method setter;

    /**
     * Creates a new instance of <code>PropertyMetadata</code>. 
     * 
     * @param name the name of the property (must not be null).
     * @param type the type of the property (must not be null).
     * @param getter the getter method that can be used to read the value of the property (must not be null).
     * @param setter the setter method that can be used to set the value of the property (must not be null).
     */
    PropertyMetadata(String name, Class<?> type, Method getter, Method setter) {
        assert name != null : "name must not be null";
        assert type != null : "type must not^ be null";
        /*
         * If we assert that getter and setter != null, PersistentPropertyMetadata
         * will not work.
         */
        this.name = name;
        this.type = type;
        this.getter = getter;
        this.setter = setter;
    }

    /**
     * The name of the property.
     */
    public String getName() {
        return name;
    }

    /**
     * The type of the property.
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * The annotations of the property, if any.
     *
     * @see #getAnnotation(java.lang.Class) 
     */
    public Annotation[] getAnnotations() {
        return getter.getAnnotations();
    }

    /**
     * Gets the annotation of the specified annotation class, if available.
     *
     * @see #getAnnotations()
     * @see Class#getAnnotation(java.lang.Class) 
     * @param annotationClass the annotation class.
     * @return the annotation, or null if not found.
     */
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return getter.getAnnotation(annotationClass);
    }

    /**
     * Returns whether the property is writable or not. Transient properties (i.e. JavaBean properties) are
     * only writable if they have a setter method.
     *
     * @return true if the property is writable, false if it is not.
     */
    public boolean isWritable() {
        return setter != null;
    }
}