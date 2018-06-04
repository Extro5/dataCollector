/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic;

/**
 * Предикат (действие, глагол)
 */
public class PredicateReferent extends com.pullenti.ner.Referent {

    public PredicateReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.semantic.internal.MetaPredicate.globalMeta);
    }

    public static final String OBJ_TYPENAME = "PREDICATE";

    public static final String ATTR_BASE = "BASE";

    public static final String ATTR_PROP = "PROP";

    public static final String ATTR_ACTANT = "ACTANT";

    public static final String ATTR_ATTR = "ATTR";

    /**
     * Морфологические атрибуты
     */
    public java.util.ArrayList<PredicateAttr> getAttrs() {
        java.util.ArrayList<PredicateAttr> _attrs = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_ATTR)) {
                try {
                    Object a = PredicateAttr.of((String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class));
                    if (a instanceof PredicateAttr) 
                        _attrs.add((PredicateAttr)a);
                } catch(Exception ex2573) {
                }
            }
        }
        return _attrs;
    }


    public boolean isAttr(PredicateAttr a) {
        return findSlot(ATTR_ATTR, a.toString(), true) != null;
    }

    public void addAttr(PredicateAttr a) {
        addSlot(ATTR_ATTR, a.toString(), false, 0);
    }

    /**
     * Глагол(ы)
     */
    public java.util.ArrayList<String> getBases() {
        java.util.ArrayList<String> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_BASE)) 
                res.add((String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class));
        }
        return res;
    }


    /**
     * Наречия
     */
    public java.util.ArrayList<String> getProps() {
        java.util.ArrayList<String> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_PROP)) 
                res.add((String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class));
        }
        return res;
    }


    /**
     * Актанты
     */
    public java.util.ArrayList<ActantReferent> getActants() {
        java.util.ArrayList<ActantReferent> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_ACTANT) && (s.getValue() instanceof ActantReferent)) 
                res.add((ActantReferent)com.pullenti.n2j.Utils.cast(s.getValue(), ActantReferent.class));
        }
        return res;
    }


    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        if (isAttr(PredicateAttr.NOT)) 
            res.append("НЕ");
        for(String b : getBases()) {
            if (res.length() > 0) 
                res.append(' ');
            res.append(b);
            if (isAttr(PredicateAttr.REFLEXIVE) && !b.endsWith("СЯ")) 
                res.append("(СЯ)");
        }
        for(String p : getProps()) {
            if (res.length() > 0) 
                res.append(' ');
            res.append(p);
        }
        if (!shortVariant) {
            for(ActantReferent a : getActants()) {
                res.append(" <").append(a.toString(true, lang, lev + 1)).append(">");
            }
        }
        return res.toString();
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        PredicateReferent ar = (PredicateReferent)com.pullenti.n2j.Utils.cast(obj, PredicateReferent.class);
        if (ar == null) 
            return false;
        return false;
    }
}
