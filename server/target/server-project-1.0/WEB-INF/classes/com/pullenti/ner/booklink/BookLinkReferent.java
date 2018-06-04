/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.booklink;

/**
 * Ссылка на внешний литературный источник (статью, книгу и пр.)
 */
public class BookLinkReferent extends com.pullenti.ner.Referent {

    public static final String OBJ_TYPENAME = "BOOKLINK";

    public static final String ATTR_AUTHOR = "AUTHOR";

    public static final String ATTR_NAME = "NAME";

    public static final String ATTR_YEAR = "YEAR";

    public static final String ATTR_LANG = "LANG";

    public static final String ATTR_GEO = "GEO";

    public static final String ATTR_URL = "URL";

    public static final String ATTR_MISC = "MISC";

    public static final String ATTR_TYPE = "TYPE";

    public BookLinkReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.booklink.internal.MetaBookLink.globalMeta);
    }

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang _lang, int lev) {
        StringBuilder res = new StringBuilder();
        Object a = getValue(ATTR_AUTHOR);
        if (a != null) {
            for(com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_AUTHOR)) {
                    if (a != s.getValue()) 
                        res.append(", ");
                    if (s.getValue() instanceof com.pullenti.ner.Referent) 
                        res.append((((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class))).toString(true, _lang, lev + 1));
                    else if (s.getValue() instanceof String) 
                        res.append((String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class));
                }
            }
            if (getAuthorsAndOther()) 
                res.append(" и др.");
        }
        String nam = getName();
        if (nam != null) {
            if (res.length() > 0) 
                res.append(' ');
            if (nam.length() > 200) 
                nam = nam.substring(0, 0+200) + "...";
            res.append("\"").append(nam).append("\"");
        }
        com.pullenti.ner.uri.UriReferent uri = (com.pullenti.ner.uri.UriReferent)com.pullenti.n2j.Utils.cast(getValue(ATTR_URL), com.pullenti.ner.uri.UriReferent.class);
        if (uri != null) 
            res.append(" [").append(uri.toString()).append("]");
        if (getYear() > 0) 
            res.append(", ").append(getYear());
        return res.toString();
    }

    public String getName() {
        return getStringValue(ATTR_NAME);
    }

    public String setName(String value) {
        addSlot(ATTR_NAME, value, true, 0);
        return value;
    }


    public String getLang() {
        return getStringValue(ATTR_LANG);
    }

    public String setLang(String value) {
        addSlot(ATTR_LANG, value, true, 0);
        return value;
    }


    public String getTyp() {
        return getStringValue(ATTR_TYPE);
    }

    public String setTyp(String value) {
        addSlot(ATTR_TYPE, value, true, 0);
        return value;
    }


    public com.pullenti.ner.uri.UriReferent getUrl() {
        return (com.pullenti.ner.uri.UriReferent)com.pullenti.n2j.Utils.cast(getValue(ATTR_URL), com.pullenti.ner.uri.UriReferent.class);
    }


    public int getYear() {
        int _year;
        com.pullenti.n2j.Outargwrapper<Integer> inoutarg395 = new com.pullenti.n2j.Outargwrapper< >();
        boolean inoutres396 = com.pullenti.n2j.Utils.parseInteger((String)com.pullenti.n2j.Utils.notnull(getStringValue(ATTR_YEAR), ""), inoutarg395);
        _year = (inoutarg395.value != null ? inoutarg395.value : 0);
        if (inoutres396) 
            return _year;
        else 
            return 0;
    }

    public int setYear(int value) {
        addSlot(ATTR_YEAR, ((Integer)value).toString(), true, 0);
        return value;
    }


    public boolean getAuthorsAndOther() {
        return findSlot(ATTR_MISC, "и др.", true) != null;
    }

    public boolean setAuthorsAndOther(boolean value) {
        addSlot(ATTR_MISC, "и др.", false, 0);
        return value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType _typ) {
        BookLinkReferent br = (BookLinkReferent)com.pullenti.n2j.Utils.cast(obj, BookLinkReferent.class);
        if (br == null) 
            return false;
        int eq = 0;
        if (getYear() > 0 && br.getYear() > 0) {
            if (getYear() == br.getYear()) 
                eq++;
            else 
                return false;
        }
        if (getTyp() != null && br.getTyp() != null) {
            if (com.pullenti.n2j.Utils.stringsNe(getTyp(), br.getTyp())) 
                return false;
        }
        boolean eqAuth = false;
        if (findSlot(ATTR_AUTHOR, null, true) != null && br.findSlot(ATTR_AUTHOR, null, true) != null) {
            boolean ok = false;
            for(com.pullenti.ner.Slot a : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(a.getTypeName(), ATTR_AUTHOR)) {
                    if (br.findSlot(ATTR_AUTHOR, a.getValue(), true) != null) {
                        eq++;
                        ok = true;
                        eqAuth = true;
                    }
                }
            }
            if (!ok) 
                return false;
        }
        if (com.pullenti.n2j.Utils.stringsNe(br.getName(), getName())) {
            if (getName() == null || br.getName() == null) 
                return false;
            if (getName().startsWith(br.getName()) || br.getName().startsWith(getName())) 
                eq += 1;
            else if (eqAuth && com.pullenti.ner.core.MiscHelper.canBeEquals(getName(), br.getName(), false, true, false)) 
                eq += 1;
            else 
                return false;
        }
        else 
            eq += 2;
        return eq > 2;
    }
}
