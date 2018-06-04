/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.business;

/**
 * Представление бизнес-факта
 */
public class BusinessFactReferent extends com.pullenti.ner.Referent {

    public BusinessFactReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.business.internal.MetaBusinessFact.GLOBALMETA);
    }

    public static final String OBJ_TYPENAME = "BUSINESSFACT";

    public static final String ATTR_KIND = "KIND";

    public static final String ATTR_TYPE = "TYPE";

    public static final String ATTR_WHO = "WHO";

    public static final String ATTR_WHOM = "WHOM";

    public static final String ATTR_WHEN = "WHEN";

    public static final String ATTR_WHAT = "WHAT";

    public static final String ATTR_MISC = "MISC";

    /**
     * Классификатор бизнес-факта
     */
    public BusinessFactKind getKind() {
        String s = getStringValue(ATTR_KIND);
        if (s == null) 
            return BusinessFactKind.UNDEFINED;
        try {
            Object res = BusinessFactKind.of(s);
            if (res instanceof BusinessFactKind) 
                return (BusinessFactKind)res;
        } catch(Exception ex448) {
        }
        return BusinessFactKind.UNDEFINED;
    }

    /**
     * Классификатор бизнес-факта
     */
    public BusinessFactKind setKind(BusinessFactKind value) {
        if (value != BusinessFactKind.UNDEFINED) 
            addSlot(ATTR_KIND, value.toString(), true, 0);
        return value;
    }


    /**
     * Краткое описание факта
     */
    public String getTyp() {
        String _typ = getStringValue(ATTR_TYPE);
        if (_typ != null) 
            return _typ;
        String _kind = getStringValue(ATTR_KIND);
        if (_kind != null) 
            _typ = (String)com.pullenti.n2j.Utils.cast(com.pullenti.ner.business.internal.MetaBusinessFact.GLOBALMETA.kindFeature.convertInnerValueToOuterValue(_kind, new com.pullenti.morph.MorphLang(null)), String.class);
        if (_typ != null) 
            return _typ.toLowerCase();
        return null;
    }

    /**
     * Краткое описание факта
     */
    public String setTyp(String value) {
        addSlot(ATTR_TYPE, value, true, 0);
        return value;
    }


    /**
     * Кто (действительный залог)
     */
    public com.pullenti.ner.Referent getWho() {
        return (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(getValue(ATTR_WHO), com.pullenti.ner.Referent.class);
    }

    /**
     * Кто (действительный залог)
     */
    public com.pullenti.ner.Referent setWho(com.pullenti.ner.Referent value) {
        addSlot(ATTR_WHO, value, true, 0);
        return value;
    }


    /**
     * Второй "Кто" (действительный залог)
     */
    public com.pullenti.ner.Referent getWho2() {
        int i = 2;
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_WHO)) {
                if ((--i) == 0) 
                    return (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class);
            }
        }
        return null;
    }

    /**
     * Второй "Кто" (действительный залог)
     */
    public com.pullenti.ner.Referent setWho2(com.pullenti.ner.Referent value) {
        addSlot(ATTR_WHO, value, false, 0);
        return value;
    }


    /**
     * Кого (страдательный залог)
     */
    public com.pullenti.ner.Referent getWhom() {
        return (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(getValue(ATTR_WHOM), com.pullenti.ner.Referent.class);
    }

    /**
     * Кого (страдательный залог)
     */
    public com.pullenti.ner.Referent setWhom(com.pullenti.ner.Referent value) {
        addSlot(ATTR_WHOM, value, true, 0);
        return value;
    }


    /**
     * Когда (DateReferent или DateRangeReferent)
     */
    public com.pullenti.ner.Referent getWhen() {
        return (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(getValue(ATTR_WHEN), com.pullenti.ner.Referent.class);
    }

    /**
     * Когда (DateReferent или DateRangeReferent)
     */
    public com.pullenti.ner.Referent setWhen(com.pullenti.ner.Referent value) {
        addSlot(ATTR_WHEN, value, true, 0);
        return value;
    }


    /**
     * Что (артефакты события)
     */
    public java.util.ArrayList<com.pullenti.ner.Referent> getWhats() {
        java.util.ArrayList<com.pullenti.ner.Referent> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_WHAT) && (s.getValue() instanceof com.pullenti.ner.Referent)) 
                res.add((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class));
        }
        return res;
    }


    public void addWhat(Object w) {
        if (w instanceof com.pullenti.ner.Referent) 
            addSlot(ATTR_WHAT, w, false, 0);
    }

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        String _typ = (String)com.pullenti.n2j.Utils.notnull(getTyp(), "Бизнес-факт");
        res.append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(_typ));
        Object v;
        if (((v = getValue(ATTR_WHO))) instanceof com.pullenti.ner.Referent) {
            res.append("; Кто: ").append((((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(v, com.pullenti.ner.Referent.class))).toString(true, lang, 0));
            if (getWho2() != null) 
                res.append(" и ").append(getWho2().toString(true, lang, 0));
        }
        if (((v = getValue(ATTR_WHOM))) instanceof com.pullenti.ner.Referent) 
            res.append("; Кого: ").append((((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(v, com.pullenti.ner.Referent.class))).toString(true, lang, 0));
        if (!shortVariant) {
            if ((((v = getValue(ATTR_WHAT)))) != null) 
                res.append("; Что: ").append(v);
            if (((v = getValue(ATTR_WHEN))) instanceof com.pullenti.ner.Referent) 
                res.append("; Когда: ").append((((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(v, com.pullenti.ner.Referent.class))).toString(shortVariant, lang, 0));
            for(com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_MISC)) 
                    res.append("; ").append(s.getValue());
            }
        }
        return res.toString();
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType _typ) {
        BusinessFactReferent br = (BusinessFactReferent)com.pullenti.n2j.Utils.cast(obj, BusinessFactReferent.class);
        if (br == null) 
            return false;
        if (br.getKind() != getKind()) 
            return false;
        if (com.pullenti.n2j.Utils.stringsNe(br.getTyp(), getTyp())) 
            return false;
        if (br.getWho() != getWho() || br.getWhom() != getWhom()) 
            return false;
        if (getWhen() != null && br.getWhen() != null) {
            if (!getWhen().canBeEquals(br.getWhen(), com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) 
                return false;
        }
        com.pullenti.ner.Referent mi1 = (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(getValue(ATTR_WHAT), com.pullenti.ner.Referent.class);
        com.pullenti.ner.Referent mi2 = (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(br.getValue(ATTR_WHAT), com.pullenti.ner.Referent.class);
        if (mi1 != null && mi2 != null) {
            if (!mi1.canBeEquals(mi2, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) 
                return false;
        }
        return true;
    }

    public static BusinessFactReferent _new436(BusinessFactKind _arg1) {
        BusinessFactReferent res = new BusinessFactReferent();
        res.setKind(_arg1);
        return res;
    }
    public static BusinessFactReferent _new447(BusinessFactKind _arg1, String _arg2) {
        BusinessFactReferent res = new BusinessFactReferent();
        res.setKind(_arg1);
        res.setTyp(_arg2);
        return res;
    }
}
