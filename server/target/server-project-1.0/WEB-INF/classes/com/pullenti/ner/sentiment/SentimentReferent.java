/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.sentiment;

/**
 * Фрагмент, соответсвующий сентиментной оценке
 */
public class SentimentReferent extends com.pullenti.ner.Referent {

    public SentimentReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.sentiment.internal.MetaSentiment.globalMeta);
    }

    public static final String OBJ_TYPENAME = "SENTIMENT";

    public static final String ATTR_KIND = "KIND";

    public static final String ATTR_COEF = "COEF";

    public static final String ATTR_REF = "REF";

    public static final String ATTR_SPELLING = "SPELLING";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        res.append(com.pullenti.ner.sentiment.internal.MetaSentiment.FTYP.convertInnerValueToOuterValue(getStringValue(ATTR_KIND), lang));
        res.append(" ").append(((String)com.pullenti.n2j.Utils.notnull(getSpelling(), "")));
        if (getCoef() > 0) 
            res.append(" (coef=").append(getCoef()).append(")");
        Object r = getValue(ATTR_REF);
        if (r != null && !shortVariant) 
            res.append(" -> ").append(r);
        return res.toString();
    }

    public SentimentKind getKind() {
        String s = getStringValue(ATTR_KIND);
        if (s == null) 
            return SentimentKind.UNDEFINED;
        try {
            Object res = SentimentKind.of(s);
            if (res instanceof SentimentKind) 
                return (SentimentKind)res;
        } catch(Exception ex2589) {
        }
        return SentimentKind.UNDEFINED;
    }

    public SentimentKind setKind(SentimentKind value) {
        if (value != SentimentKind.UNDEFINED) 
            addSlot(ATTR_KIND, value.toString(), true, 0);
        return value;
    }


    public String getSpelling() {
        return getStringValue(ATTR_SPELLING);
    }

    public String setSpelling(String value) {
        addSlot(ATTR_SPELLING, value, true, 0);
        return value;
    }


    public int getCoef() {
        String val = getStringValue(ATTR_COEF);
        if (val == null) 
            return 0;
        int i;
        com.pullenti.n2j.Outargwrapper<Integer> inoutarg2590 = new com.pullenti.n2j.Outargwrapper< >();
        boolean inoutres2591 = com.pullenti.n2j.Utils.parseInteger(val, inoutarg2590);
        i = (inoutarg2590.value != null ? inoutarg2590.value : 0);
        if (!inoutres2591) 
            return 0;
        return i;
    }

    public int setCoef(int value) {
        addSlot(ATTR_COEF, ((Integer)value).toString(), true, 0);
        return value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        SentimentReferent sr = (SentimentReferent)com.pullenti.n2j.Utils.cast(obj, SentimentReferent.class);
        if (sr == null) 
            return false;
        if (sr.getKind() != getKind()) 
            return false;
        if (com.pullenti.n2j.Utils.stringsNe(sr.getSpelling(), getSpelling())) 
            return false;
        return true;
    }

    @Override
    public boolean canBeGeneralFor(com.pullenti.ner.Referent obj) {
        return false;
    }

    @Override
    public com.pullenti.ner.core.IntOntologyItem createOntologyItem() {
        com.pullenti.ner.core.IntOntologyItem oi = new com.pullenti.ner.core.IntOntologyItem(this);
        oi.termins.add(new com.pullenti.ner.core.Termin(getSpelling(), new com.pullenti.morph.MorphLang(null), false));
        return oi;
    }
}
