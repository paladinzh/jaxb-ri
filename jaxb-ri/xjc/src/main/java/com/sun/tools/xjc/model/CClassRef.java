/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.xjc.model;

import javax.xml.namespace.QName;

import com.sun.codemodel.JClass;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.Aspect;
import com.sun.tools.xjc.outline.Outline;
import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIClass;
import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIEnum;
import com.sun.xml.xsom.XSComponent;

/**
 * Reference to an existing class.
 *
 * @author Kohsuke Kawaguchi
 */
public final class CClassRef extends AbstractCElement implements NClass, CClass {

    private final String fullyQualifiedClassName;

    /**
     * Cached for both performance and single identity.
     */
    private JClass clazz;

    /**
     *
     * @param decl
     *      The {@link BIClass} declaration that has {@link BIClass#getExistingClassRef()}
     */
    public CClassRef(Model model, XSComponent source, BIClass decl, CCustomizations customizations) {
        super(model, source, decl.getLocation(), customizations);
        fullyQualifiedClassName = decl.getExistingClassRef();
        assert fullyQualifiedClassName!=null;
    }

    /**
     *
     * @param decl
     *      The {@link BIClass} declaration that has {@link BIEnum#ref}
     */
    public CClassRef(Model model, XSComponent source, BIEnum decl, CCustomizations customizations) {
        super(model, source, decl.getLocation(), customizations);
        fullyQualifiedClassName = decl.ref;
        assert fullyQualifiedClassName!=null;
    }

    public void setAbstract() {
        // assume that the referenced class is marked as abstract to begin with.
    }

    public boolean isAbstract() {
        // no way to find out for sure
        return false;
    }

    public NType getType() {
        return this;
    }

    public JClass toType(Outline o, Aspect aspect) {
        if(clazz==null)
            clazz = o.getCodeModel().ref(fullyQualifiedClassName);
        return clazz;
    }

    public String fullName() {
        return fullyQualifiedClassName;
    }

    public QName getTypeName() {
        return null;
    }

    /**
     * Guaranteed to return this.
     */
    @Deprecated
    public CNonElement getInfo() {
        return this;
    }
    
// are these going to bite us?
//    we can compute some of them, but not all.

    public CElement getSubstitutionHead() {
        return null;
    }

    public CClassInfo getScope() {
        return null;
    }

    public QName getElementName() {
        return null;
    }

    public boolean isBoxedType() {
        return false;
    }

    public boolean isSimpleType() {
        return false;
    }


}
