/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.uri;

/**
 * URI, а также ISBN, УДК, ББК, ICQ и пр.
 */
public class UriReferent extends com.pullenti.ner.Referent {

    public UriReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.uri.internal.MetaUri.globalMeta);
    }

    public static final String OBJ_TYPENAME = "URI";

    public static final String ATTR_VALUE = "VALUE";

    public static final String ATTR_DETAIL = "DETAIL";

    public static final String ATTR_SCHEME = "SCHEME";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        if (getScheme() != null) {
            String split = ":";
            if (com.pullenti.n2j.Utils.stringsEq(getScheme(), "ISBN") || com.pullenti.n2j.Utils.stringsEq(getScheme(), "ББК") || com.pullenti.n2j.Utils.stringsEq(getScheme(), "УДК")) 
                split = " ";
            else if (com.pullenti.n2j.Utils.stringsEq(getScheme(), "http") || com.pullenti.n2j.Utils.stringsEq(getScheme(), "ftp") || com.pullenti.n2j.Utils.stringsEq(getScheme(), "https")) 
                split = "://";
            return getScheme() + split + ((String)com.pullenti.n2j.Utils.notnull(getValue(), "?"));
        }
        else 
            return getValue();
    }

    /**
     * Значение
     */
    public String getValue() {
        return getStringValue(ATTR_VALUE);
    }

    /**
     * Значение
     */
    public String setValue(String _value) {
        String val = _value;
        addSlot(ATTR_VALUE, val, true, 0);
        return _value;
    }


    /**
     * Схема
     */
    public String getScheme() {
        return getStringValue(ATTR_SCHEME);
    }

    /**
     * Схема
     */
    public String setScheme(String _value) {
        addSlot(ATTR_SCHEME, _value, true, 0);
        return _value;
    }


    /**
     * Детализация кода (если есть)
     */
    public String getDetail() {
        return getStringValue(ATTR_DETAIL);
    }

    /**
     * Детализация кода (если есть)
     */
    public String setDetail(String _value) {
        addSlot(ATTR_DETAIL, _value, true, 0);
        return _value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        UriReferent _uri = (UriReferent)com.pullenti.n2j.Utils.cast(obj, UriReferent.class);
        if (_uri == null) 
            return false;
        return com.pullenti.n2j.Utils.stringsCompare(getValue(), _uri.getValue(), true) == 0;
    }

    public static UriReferent _new2649(String _arg1, String _arg2) {
        UriReferent res = new UriReferent();
        res.setScheme(_arg1);
        res.setValue(_arg2);
        return res;
    }
    public static UriReferent _new2652(String _arg1, String _arg2) {
        UriReferent res = new UriReferent();
        res.setValue(_arg1);
        res.setScheme(_arg2);
        return res;
    }
}
