/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic;

/**
 * Актант - связь предиката с объектами или объекта с объектами
 */
public class ActantReferent extends com.pullenti.ner.Referent {

    public ActantReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.semantic.internal.MetaActant.globalMeta);
    }

    public static final String OBJ_TYPENAME = "ACTANT";

    public static final String ATTR_ROLE = "ROLE";

    public static final String ATTR_REF = "REF";

    public static final String ATTR_VALUE = "VALUE";

    public static final String ATTR_COUNT = "COUNT";

    public static final String ATTR_PREPOSITION = "PREP";

    public static final String ATTR_PROP = "PROP";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        ActantRole r = getRole();
        String cou = getCount();
        if (r != ActantRole.UNDEFINED && !shortVariant) 
            res.append(com.pullenti.ner.semantic.internal.MetaActant.ROLEFEATURE.convertInnerValueToOuterValue(r.toString(), lang));
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_PROP)) {
                if (res.length() > 0) 
                    res.append(' ');
                res.append(s.getValue());
            }
        }
        if (getPrep() != null) {
            if (res.length() > 0) 
                res.append(' ');
            res.append(getPrep());
        }
        if (cou != null) {
            if (res.length() > 0) 
                res.append(' ');
            res.append(cou);
        }
        if (res.length() > 0) 
            res.append(": ");
        java.util.ArrayList<com.pullenti.ner.Referent> prs = getRefs();
        if (getValue() != null) 
            res.append(getValue());
        else 
            for(com.pullenti.ner.Referent p : prs) {
                if (p != prs.get(0)) 
                    res.append((r == ActantRole.SENTACTANT ? " + " : " & "));
                res.append(p.toString(true, lang, lev + 1));
            }
        return res.toString();
    }

    /**
     * Роль актанта
     */
    public ActantRole getRole() {
        String s = getStringValue(ATTR_ROLE);
        if (s == null) 
            return ActantRole.UNDEFINED;
        try {
            Object res = ActantRole.of(s);
            if (res instanceof ActantRole) 
                return (ActantRole)res;
        } catch(Exception ex2571) {
        }
        return ActantRole.UNDEFINED;
    }

    /**
     * Роль актанта
     */
    public ActantRole setRole(ActantRole _value) {
        if (_value != ActantRole.UNDEFINED) 
            addSlot(ATTR_ROLE, _value.toString(), true, 0);
        return _value;
    }


    /**
     * Ссылки на объекты, предикаты или другие актанты
     */
    public java.util.ArrayList<com.pullenti.ner.Referent> getRefs() {
        java.util.ArrayList<com.pullenti.ner.Referent> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_REF) && (s.getValue() instanceof com.pullenti.ner.Referent)) 
                res.add((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class));
        }
        return res;
    }


    public java.util.ArrayList<com.pullenti.ner.Referent> getAllRefs() {
        java.util.ArrayList<com.pullenti.ner.Referent> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_REF) && (s.getValue() instanceof ActantReferent)) 
                res.addAll((((ActantReferent)com.pullenti.n2j.Utils.cast(s.getValue(), ActantReferent.class))).getRefs());
            else if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_REF) && (s.getValue() instanceof com.pullenti.ner.Referent)) 
                res.add((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class));
        }
        return res;
    }


    /**
     * Исходное значение из текста
     */
    public String getValue() {
        return getStringValue(ATTR_VALUE);
    }

    /**
     * Исходное значение из текста
     */
    public String setValue(String _value) {
        addSlot(ATTR_VALUE, _value, true, 0);
        return _value;
    }


    /**
     * Количественная характеристика
     */
    public String getCount() {
        return getStringValue(ATTR_COUNT);
    }

    /**
     * Количественная характеристика
     */
    public String setCount(String _value) {
        addSlot(ATTR_COUNT, _value, true, 0);
        return _value;
    }


    /**
     * Предлог (если есть)
     */
    public String getPrep() {
        return getStringValue(ATTR_PREPOSITION);
    }

    /**
     * Предлог (если есть)
     */
    public String setPrep(String _value) {
        addSlot(ATTR_PREPOSITION, _value, true, 0);
        return _value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        ActantReferent ar = (ActantReferent)com.pullenti.n2j.Utils.cast(obj, ActantReferent.class);
        if (ar == null) 
            return false;
        if (ar.getRole() != getRole()) 
            return false;
        if (com.pullenti.n2j.Utils.stringsCompare((String)com.pullenti.n2j.Utils.notnull(ar.getValue(), ""), (String)com.pullenti.n2j.Utils.notnull(getValue(), ""), true) != 0) 
            return false;
        if (com.pullenti.n2j.Utils.stringsNe(getCount(), ar.getCount())) 
            return false;
        if (com.pullenti.n2j.Utils.stringsNe(getPrep(), ar.getPrep())) 
            return false;
        java.util.ArrayList<com.pullenti.ner.Referent> refs1 = getRefs();
        java.util.ArrayList<com.pullenti.ner.Referent> refs2 = ar.getRefs();
        if (refs1.size() != refs2.size()) 
            return false;
        for(com.pullenti.ner.Referent r : refs1) {
            boolean has = false;
            for(com.pullenti.ner.Referent r2 : refs2) {
                if (r2.canBeEquals(r, typ)) {
                    has = true;
                    break;
                }
            }
            if (!has) 
                return false;
        }
        return true;
    }

    @Override
    public java.util.ArrayList<String> getCompareStrings() {
        java.util.ArrayList<String> strs = null;
        for(com.pullenti.ner.Slot r : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), ActantReferent.ATTR_REF) && (r.getValue() instanceof ObjectReferent)) {
                java.util.ArrayList<String> ss = (((ObjectReferent)com.pullenti.n2j.Utils.cast(r.getValue(), ObjectReferent.class))).getCompareStrings();
                if (strs == null) 
                    strs = ss;
                else if (ss != null) 
                    strs.addAll(ss);
            }
        }
        if (strs != null && strs.size() > 0) 
            return strs;
        if (getValue() != null) {
            strs = new java.util.ArrayList< >();
            strs.add(getValue());
            return strs;
        }
        return null;
    }

    public static ActantReferent _new2583(ActantRole _arg1) {
        ActantReferent res = new ActantReferent();
        res.setRole(_arg1);
        return res;
    }
}
