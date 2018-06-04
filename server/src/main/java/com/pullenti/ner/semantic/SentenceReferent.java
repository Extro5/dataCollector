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
public class SentenceReferent extends com.pullenti.ner.Referent {

    public SentenceReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.semantic.internal.MetaSentence.globalMeta);
    }

    public static final String OBJ_TYPENAME = "SENTENCE";

    public static final String ATTR_KIND = "KIND";

    public static final String ATTR_CONTENT = "CONTENT";

    public static final String ATTR_REF = "REF";

    /**
     * Тип предложения
     */
    public SentenceKind getKind() {
        String s = getStringValue(ATTR_KIND);
        if (s == null) 
            return SentenceKind.UNDEFINED;
        try {
            Object res = SentenceKind.of(s);
            if (res instanceof SentenceKind) 
                return (SentenceKind)res;
        } catch(Exception ex2586) {
        }
        return SentenceKind.UNDEFINED;
    }

    /**
     * Тип предложения
     */
    public SentenceKind setKind(SentenceKind value) {
        if (value != SentenceKind.UNDEFINED) 
            addSlot(ATTR_KIND, value.toString(), true, 0);
        return value;
    }


    /**
     * Ссылка на семантические объекты
     */
    public java.util.ArrayList<ObjectReferent> getSemRefs() {
        java.util.ArrayList<ObjectReferent> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_REF) && (s.getValue() instanceof ObjectReferent)) 
                res.add((ObjectReferent)com.pullenti.n2j.Utils.cast(s.getValue(), ObjectReferent.class));
        }
        return res;
    }


    /**
     * Ссылка на синтаксические объекты (для аннотации - предложения, входящие в аннотацию)
     */
    public java.util.ArrayList<SentenceReferent> getSynRefs() {
        java.util.ArrayList<SentenceReferent> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_REF) && (s.getValue() instanceof SentenceReferent)) 
                res.add((SentenceReferent)com.pullenti.n2j.Utils.cast(s.getValue(), SentenceReferent.class));
        }
        return res;
    }


    /**
     * Содержимое
     */
    public String getContent() {
        return getStringValue(ATTR_CONTENT);
    }

    /**
     * Содержимое
     */
    public String setContent(String value) {
        if (value == null) 
            return value;
        if (value.indexOf('\n') >= 0 || value.indexOf('\r') >= 0) 
            value = value.replace('\n', ' ').replace('\r', ' ');
        while((value.indexOf("  ") >= 0)) {
            value = value.replace("  ", " ");
        }
        addSlot(ATTR_CONTENT, value.trim(), true, 0);
        return value;
    }


    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        if (getKind() == SentenceKind.ANNOTATION) 
            return "Автореферат";
        String cnt = getContent();
        if (cnt != null) {
            if (cnt.length() > 100) 
                return cnt.substring(0, 0+100) + "...";
            else 
                return cnt;
        }
        return "Предложение";
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        return this == obj;
    }

    @Override
    public String toSortString() {
        if (getOccurrence().size() == 0) 
            return getKind().toString();
        else 
            return getKind().toString() + " " + String.format("%06d", getOccurrence().get(0).beginChar);
    }

    /**
     * Это для аннотаций
     */
    public com.pullenti.ner.TextAnnotation ignoredPart = null;

    public static SentenceReferent _new2477(SentenceKind _arg1) {
        SentenceReferent res = new SentenceReferent();
        res.setKind(_arg1);
        return res;
    }
}
