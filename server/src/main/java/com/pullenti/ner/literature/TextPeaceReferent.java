/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature;

/**
 * Фрагмент текста
 */
public class TextPeaceReferent extends com.pullenti.ner.Referent {

    public static final String OBJ_TYPENAME = "TEXTPEACE";

    public static final String ATTR_KIND = "KIND";

    public static final String ATTR_TYPE = "TYPE";

    public static final String ATTR_NAME = "NAME";

    public static final String ATTR_NUMBER = "NUMBER";

    public static final String ATTR_OWNER = "OWNER";

    public static final String ATTR_AUTHOR = "AUTHOR";

    public TextPeaceReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.literature.internal.MetaPeace.GLOBALMETA);
    }

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder tmp = new StringBuilder();
        if (getKind() != TextPeaceKind.TITLE) 
            tmp.append(com.pullenti.ner.literature.internal.MetaPeace.GLOBALMETA.kindFi.convertInnerValueToOuterValue((String)com.pullenti.n2j.Utils.notnull(getStringValue(ATTR_KIND), "Text"), new com.pullenti.morph.MorphLang(null)));
        if (getTyp() != TextPeaceType.UNDEFINED) 
            tmp.append(" ").append(com.pullenti.ner.literature.internal.MetaPeace.GLOBALMETA.typeFi.convertInnerValueToOuterValue(getTyp(), new com.pullenti.morph.MorphLang(null)));
        if (getNumber() != null) 
            tmp.append(" № ").append(getNumber());
        if (getName() != null) 
            tmp.append(" \"").append(getName()).append("\"");
        Object auth = getValue(ATTR_AUTHOR);
        if (auth != null) 
            tmp.append(" ").append(auth);
        return tmp.toString().trim();
    }

    public TextPeaceReferent getOwner() {
        return (TextPeaceReferent)com.pullenti.n2j.Utils.cast(getValue(ATTR_OWNER), TextPeaceReferent.class);
    }

    public TextPeaceReferent setOwner(TextPeaceReferent value) {
        addSlot(ATTR_OWNER, value, true, 0);
        return value;
    }


    @Override
    public com.pullenti.ner.Referent getParentReferent() {
        return getOwner();
    }


    public TextPeaceKind getKind() {
        String s = getStringValue(ATTR_KIND);
        if (s == null) 
            return TextPeaceKind.TEXT;
        try {
            Object res = TextPeaceKind.of(s);
            if (res instanceof TextPeaceKind) 
                return (TextPeaceKind)res;
        } catch(Exception ex1571) {
        }
        return TextPeaceKind.TEXT;
    }

    public TextPeaceKind setKind(TextPeaceKind value) {
        if (value == TextPeaceKind.TEXT) 
            addSlot(ATTR_KIND, null, true, 0);
        else 
            addSlot(ATTR_KIND, value.toString(), true, 0);
        return value;
    }


    public TextPeaceType getTyp() {
        String s = getStringValue(ATTR_TYPE);
        if (s == null) 
            return TextPeaceType.UNDEFINED;
        try {
            Object res = TextPeaceType.of(s);
            if (res instanceof TextPeaceType) 
                return (TextPeaceType)res;
        } catch(Exception ex1572) {
        }
        return TextPeaceType.UNDEFINED;
    }

    public TextPeaceType setTyp(TextPeaceType value) {
        if (value == TextPeaceType.UNDEFINED) 
            addSlot(ATTR_TYPE, null, true, 0);
        else 
            addSlot(ATTR_TYPE, value.toString(), true, 0);
        return value;
    }


    public String getName() {
        return getStringValue(ATTR_NAME);
    }

    public String setName(String value) {
        addSlot(ATTR_NAME, value, true, 0);
        return value;
    }


    public String getNumber() {
        return getStringValue(ATTR_NUMBER);
    }

    public String setNumber(String value) {
        addSlot(ATTR_NUMBER, value, true, 0);
        return value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType _typ) {
        return obj == this;
    }

    public void _setTitle(com.pullenti.ner.titlepage.TitlePageReferent title) {
        for(String n : title.getNames()) {
            addSlot(ATTR_NAME, n, false, 0);
        }
        for(com.pullenti.ner.Slot s : title.getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.titlepage.TitlePageReferent.ATTR_AUTHOR)) 
                addSlot(ATTR_AUTHOR, s.getValue(), false, 0);
        }
    }

    public static TextPeaceReferent _new1548(TextPeaceKind _arg1) {
        TextPeaceReferent res = new TextPeaceReferent();
        res.setKind(_arg1);
        return res;
    }
    public static TextPeaceReferent _new1554(TextPeaceKind _arg1, TextPeaceType _arg2) {
        TextPeaceReferent res = new TextPeaceReferent();
        res.setKind(_arg1);
        res.setTyp(_arg2);
        return res;
    }
    public static TextPeaceReferent _new1555(TextPeaceKind _arg1, TextPeaceReferent _arg2) {
        TextPeaceReferent res = new TextPeaceReferent();
        res.setKind(_arg1);
        res.setOwner(_arg2);
        return res;
    }
}
