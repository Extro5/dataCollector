/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic;

/**
 * Семантический объект
 */
public class ObjectReferent extends com.pullenti.ner.Referent {

    public ObjectReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.semantic.internal.MetaObject.globalMeta);
    }

    public static final String OBJ_TYPENAME = "OBJECT";

    public static final String ATTR_KIND = "KIND";

    public static final String ATTR_BASE = "BASE";

    public static final String ATTR_PROP = "PROP";

    public static final String ATTR_NAME = "NAME";

    public static final String ATTR_ALIAS = "ALIAS";

    public static final String ATTR_REF = "REF";

    public static final String ATTR_MISCREF = "MISCREF";

    public static final String ATTR_ENTITY = "ENTITY";

    public SemanticKind getKind() {
        String s = getStringValue(ATTR_KIND);
        if (s == null) 
            return SemanticKind.UNDEFINED;
        try {
            Object res = SemanticKind.of(s);
            if (res instanceof SemanticKind) 
                return (SemanticKind)res;
        } catch(Exception ex2572) {
        }
        return SemanticKind.UNDEFINED;
    }

    public SemanticKind setKind(SemanticKind value) {
        if (value != SemanticKind.UNDEFINED) 
            addSlot(ATTR_KIND, value.toString(), true, 0);
        return value;
    }


    public String getBase() {
        return getStringValue(ATTR_BASE);
    }

    public String setBase(String value) {
        addSlot(ATTR_BASE, value, true, 0);
        return value;
    }


    public java.util.ArrayList<String> getProps() {
        java.util.ArrayList<String> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_PROP) && (s.getValue() instanceof String)) 
                res.add((String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class));
        }
        return res;
    }


    public com.pullenti.ner.Referent getProxy() {
        return (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(getValue(ATTR_ENTITY), com.pullenti.ner.Referent.class);
    }

    public com.pullenti.ner.Referent setProxy(com.pullenti.ner.Referent value) {
        addSlot(ATTR_ENTITY, value, true, 0);
        return value;
    }


    public String getName() {
        return getStringValue(ATTR_NAME);
    }


    public void addName(String _name) {
        if (_name != null) 
            addSlot(ATTR_NAME, _name.toUpperCase(), false, 0);
    }

    public String getAlias() {
        return getStringValue(ATTR_ALIAS);
    }


    public void addAlias(String _alias) {
        if (_alias != null) 
            addSlot(ATTR_ALIAS, _alias.toUpperCase(), false, 0);
    }

    /**
     * Все ссылка с другими объектами или предикатами (прямые и через актанты)
     */
    public java.util.ArrayList<com.pullenti.ner.Referent> getPropRefs() {
        java.util.ArrayList<com.pullenti.ner.Referent> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_PROP)) {
                if ((s.getValue() instanceof ObjectReferent) || (s.getValue() instanceof PredicateReferent)) 
                    res.add((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class));
                else if (s.getValue() instanceof ActantReferent) {
                    for(com.pullenti.ner.Slot ss : (((ActantReferent)com.pullenti.n2j.Utils.cast(s.getValue(), ActantReferent.class))).getSlots()) {
                        if (com.pullenti.n2j.Utils.stringsEq(ss.getTypeName(), ActantReferent.ATTR_REF) && (ss.getValue() instanceof com.pullenti.ner.Referent)) 
                            res.add((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(ss.getValue(), com.pullenti.ner.Referent.class));
                    }
                }
            }
        }
        return res;
    }


    @Override
    public com.pullenti.ner.Referent getParentReferent() {
        SemanticKind ki = getKind();
        java.util.ArrayList<com.pullenti.ner.Referent> refs = getPropRefs();
        if (refs.size() == 1) 
            return refs.get(0);
        else 
            return null;
    }


    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        java.util.ArrayList<com.pullenti.ner.Referent> objs = null;
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_PROP)) {
                if (s.getValue() instanceof com.pullenti.ner.Referent) {
                    if (objs == null) 
                        objs = new java.util.ArrayList< >();
                    objs.add((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class));
                }
                else 
                    res.append(s.getValue().toString()).append(" ");
            }
        }
        if (getBase() != null) 
            res.append(getBase());
        else if (getProxy() != null) 
            res.append(getProxy().toString(shortVariant, lang, lev + 1).toUpperCase());
        if (getName() != null) 
            res.append(" \"").append(getName()).append("\"");
        if (getAlias() != null) 
            res.append(" (").append(getAlias()).append(")");
        if (!shortVariant && objs != null && (lev < 30)) {
            for(int i = 0; i < objs.size(); i++) {
                if (i == 0) 
                    res.append("; ");
                else if ((i + 1) < objs.size()) 
                    res.append(", ");
                else 
                    res.append(" и ");
                res.append(objs.get(i).toString(true, lang, lev + 1));
            }
        }
        return res.toString();
    }

    public java.util.ArrayList<String> _getAbbrs() {
        java.util.ArrayList<String> res = new java.util.ArrayList< >();
        if (getKind() != SemanticKind.OBJECT) 
            return res;
        StringBuilder tmp = new StringBuilder();
        ObjectReferent sr = this;
        while(tmp.length() < 20) {
            for(String p : sr.getProps()) {
                tmp.append(p.charAt(0));
            }
            if (com.pullenti.n2j.Utils.isNullOrEmpty(sr.getBase())) 
                break;
            tmp.append(sr.getBase().charAt(0));
            if (tmp.length() > 1) 
                res.add(tmp.toString());
            java.util.ArrayList<com.pullenti.ner.Referent> re = sr.getPropRefs();
            if (re.size() != 1 || !((re.get(0) instanceof ObjectReferent))) 
                break;
            sr = (ObjectReferent)com.pullenti.n2j.Utils.cast(re.get(0), ObjectReferent.class);
        }
        return res;
    }

    public boolean canHasAlias(String _alias) {
        if (_alias == null || (_alias.length() < 2)) 
            return false;
        if (findSlot(ATTR_ALIAS, _alias, true) != null) 
            return true;
        java.util.ArrayList<String> abbrs = _getAbbrs();
        if (abbrs.contains(_alias)) 
            return true;
        return false;
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        ObjectReferent sy = (ObjectReferent)com.pullenti.n2j.Utils.cast(obj, ObjectReferent.class);
        if (sy == null) 
            return false;
        SemanticKind ki = getKind();
        if (ki != sy.getKind()) 
            return false;
        if (sy.getProxy() != null || getProxy() != null) {
            if (getProxy() == null || sy.getProxy() == null) 
                return false;
            if (!getProxy().canBeEquals(sy.getProxy(), typ)) 
                return false;
        }
        int hasName = 0;
        if (getName() == null && sy.getName() == null) {
            if (com.pullenti.n2j.Utils.stringsNe(getBase(), sy.getBase())) 
                return false;
        }
        else {
            hasName = 1;
            if (getName() != null && sy.getName() != null) {
                if (com.pullenti.n2j.Utils.stringsNe(getName(), sy.getName())) 
                    return false;
                hasName = 2;
            }
            else if (getBase() != null && canHasAlias(sy.getName())) {
            }
            else if (sy.getBase() != null && sy.canHasAlias(getName())) {
            }
            else if (com.pullenti.n2j.Utils.stringsNe(getBase(), sy.getBase())) 
                return false;
        }
        java.util.ArrayList<com.pullenti.ner.Referent> re1 = getPropRefs();
        java.util.ArrayList<com.pullenti.ner.Referent> re2 = sy.getPropRefs();
        if (re1.size() > 0 || re2.size() > 0) {
            if (hasName > 1 && ((re1.size() == 0 || re2.size() == 0))) {
            }
            else if (re1.size() != re2.size()) 
                return false;
            else 
                for(com.pullenti.ner.Referent r1 : re1) {
                    boolean ok = false;
                    for(com.pullenti.ner.Referent r2 : re2) {
                        if (r2 == r1 || r2.canBeEquals(r1, typ)) {
                            ActantReferent a1 = _findActant(r1);
                            ActantReferent a2 = sy._findActant(r2);
                            if (a1 != null && a2 != null) {
                                if (!a1.canBeEquals(a2, typ)) 
                                    continue;
                            }
                            ok = true;
                            break;
                        }
                    }
                    if (!ok) 
                        return false;
                }
        }
        java.util.ArrayList<String> pr1 = getProps();
        java.util.ArrayList<String> pr2 = sy.getProps();
        if (((pr1.size() > 0 || pr2.size() > 0)) && hasName == 0) {
            if (pr1.size() != pr2.size()) 
                return false;
            for(String p : pr1) {
                if (!pr2.contains(p)) {
                    if (hasName > 1) {
                    }
                    else 
                        return false;
                }
            }
            for(String p : pr2) {
                if (!pr1.contains(p)) {
                    if (hasName > 1) {
                    }
                    else 
                        return false;
                }
            }
        }
        return true;
    }

    private ActantReferent _findActant(com.pullenti.ner.Referent r) {
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_PROP) && (s.getValue() instanceof ActantReferent)) {
                ActantReferent a = (ActantReferent)com.pullenti.n2j.Utils.cast(s.getValue(), ActantReferent.class);
                if (a.findSlot(ActantReferent.ATTR_REF, r, false) != null) 
                    return a;
            }
        }
        return null;
    }

    @Override
    public void mergeSlots(com.pullenti.ner.Referent obj, boolean mergeStatistic) {
        super.mergeSlots(obj, mergeStatistic);
        if (getName() != null && getBase() != null) {
            if (canHasAlias(getName())) {
                com.pullenti.ner.Slot s = findSlot(ATTR_NAME, getName(), true);
                s.setTypeName(ATTR_ALIAS);
            }
        }
        for(int i = 0; i < (getSlots().size() - 1); i++) {
            int j;
            for(j = i + 1; j < getSlots().size(); j++) {
                if ((getSlots().get(i).getValue() instanceof ObjectReferent) && getSlots().get(j).getValue() == getSlots().get(i).getValue() && com.pullenti.n2j.Utils.stringsNe(getSlots().get(i).getTypeName(), getSlots().get(j).getTypeName())) 
                    break;
            }
            if (j >= getSlots().size()) 
                continue;
            getSlots().remove(i);
            i--;
        }
        for(int i = 0; i < getSlots().size(); i++) {
            if (com.pullenti.n2j.Utils.stringsEq(getSlots().get(i).getTypeName(), ATTR_PROP) && (getSlots().get(i).getValue() instanceof ActantReferent)) {
                ActantReferent act = (ActantReferent)com.pullenti.n2j.Utils.cast(getSlots().get(i).getValue(), ActantReferent.class);
                boolean ch = false;
                for(int j = 0; j < getSlots().size(); j++) {
                    if (i != j && com.pullenti.n2j.Utils.stringsEq(getSlots().get(j).getTypeName(), getSlots().get(i).getTypeName()) && (getSlots().get(j).getValue() instanceof com.pullenti.ner.Referent)) {
                        if (act.findSlot(ActantReferent.ATTR_REF, getSlots().get(j).getValue(), true) != null) {
                            getSlots().remove(j);
                            ch = true;
                            break;
                        }
                    }
                }
                if (ch) 
                    i--;
            }
        }
    }

    @Override
    public boolean canBeGeneralFor(com.pullenti.ner.Referent obj) {
        return false;
    }

    @Override
    public java.util.ArrayList<String> getCompareStrings() {
        java.util.ArrayList<String> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot a : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(a.getTypeName(), ATTR_BASE) || com.pullenti.n2j.Utils.stringsEq(a.getTypeName(), ATTR_NAME) || com.pullenti.n2j.Utils.stringsEq(a.getTypeName(), ATTR_ALIAS)) 
                res.add((String)com.pullenti.n2j.Utils.cast(a.getValue(), String.class));
            else if (com.pullenti.n2j.Utils.stringsEq(a.getTypeName(), ATTR_ENTITY) && (a.getValue() instanceof com.pullenti.ner.Referent)) 
                res.add((((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(a.getValue(), com.pullenti.ner.Referent.class))).toString(true, com.pullenti.morph.MorphLang.UNKNOWN, 0).toUpperCase());
        }
        return res;
    }

    @Override
    public com.pullenti.ner.core.IntOntologyItem createOntologyItem() {
        com.pullenti.ner.core.IntOntologyItem oi = new com.pullenti.ner.core.IntOntologyItem(this);
        for(String s : getCompareStrings()) {
            oi.termins.add(new com.pullenti.ner.core.Termin(s, new com.pullenti.morph.MorphLang(null), false));
        }
        return oi;
    }

    /**
     * Ранг значимости элемента в рамках анализируемого текста
     */
    public float rank;

    /**
     * Уситывается при построении автоаннотаций
     */
    public boolean isSignific = false;

    public static ObjectReferent _new2582(SemanticKind _arg1) {
        ObjectReferent res = new ObjectReferent();
        res.setKind(_arg1);
        return res;
    }
}
