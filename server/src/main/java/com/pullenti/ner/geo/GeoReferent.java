/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.geo;

/**
 * Сущность, описывающая территорию как административную единицу. 
 *  Это страны, автономные образования, области, административные районы и пр.
 */
public class GeoReferent extends com.pullenti.ner.Referent {

    public GeoReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.geo.internal.MetaGeo.globalMeta);
    }

    public static final String OBJ_TYPENAME = "GEO";

    public static final String ATTR_NAME = "NAME";

    public static final String ATTR_TYPE = "TYPE";

    public static final String ATTR_ALPHA2 = "ALPHA2";

    public static final String ATTR_HIGHER = "HIGHER";

    public static final String ATTR_REF = "REF";

    public static final String ATTR_FIAS = "FIAS";

    public static final String ATTR_BTI = "BTI";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        return _ToString(shortVariant, lang, true, lev);
    }

    private String _ToString(boolean shortVariant, com.pullenti.morph.MorphLang lang, boolean outCladr, int lev) {
        if (isUnion()) {
            StringBuilder res = new StringBuilder();
            res.append(getStringValue(ATTR_TYPE));
            for(com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_REF) && (s.getValue() instanceof com.pullenti.ner.Referent)) 
                    res.append("; ").append((((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class))).toString(true, lang, 0));
            }
            return res.toString();
        }
        String name = com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(_getName(lang != null && lang.isEn()));
        if (!shortVariant) {
            if (!isState()) {
                if (isCity() && isRegion()) {
                }
                else {
                    String typ = getStringValue(ATTR_TYPE);
                    if (typ != null) {
                        if (!isCity()) {
                            int i = typ.lastIndexOf(' ');
                            if (i > 0) 
                                typ = typ.substring(i + 1);
                        }
                        name = typ + " " + name;
                    }
                }
            }
        }
        if (!shortVariant && outCladr) {
            Object kladr = getValue(ATTR_FIAS);
            if (kladr instanceof com.pullenti.ner.Referent) 
                name = name + " (ФИАС: " + ((String)com.pullenti.n2j.Utils.notnull((((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(kladr, com.pullenti.ner.Referent.class))).getStringValue("GUID"), "?")) + ")";
            String bti = getStringValue(ATTR_BTI);
            if (bti != null) 
                name = name + " (БТИ " + bti + ")";
        }
        if (!shortVariant && getHigher() != null && (lev < 10)) {
            if (((getHigher().isCity() && isRegion())) || ((findSlot(ATTR_TYPE, "город", true) == null && findSlot(ATTR_TYPE, "місто", true) == null && isCity()))) 
                return name + "; " + getHigher()._ToString(false, lang, false, lev + 1);
        }
        return name;
    }

    private String _getName(boolean cyr) {
        String name = null;
        for(int i = 0; i < 2; i++) {
            for(com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) {
                    String v = s.getValue().toString();
                    if (com.pullenti.n2j.Utils.isNullOrEmpty(v)) 
                        continue;
                    if (i == 0) {
                        if (!com.pullenti.morph.LanguageHelper.isCyrillicChar(v.charAt(0))) {
                            if (cyr) 
                                continue;
                        }
                        else if (!cyr) 
                            continue;
                    }
                    if (name == null) 
                        name = v;
                    else if (name.length() > v.length()) {
                        if ((v.length() < 4) && (name.length() < 10)) {
                        }
                        else if (name.charAt(name.length() - 1) == 'В') {
                        }
                        else 
                            name = v;
                    }
                    else if ((name.length() < 4) && v.length() >= 4 && (v.length() < 10)) 
                        name = v;
                }
            }
            if (name != null) 
                break;
        }
        if (com.pullenti.n2j.Utils.stringsEq(name, "МОЛДОВА")) 
            name = "МОЛДАВИЯ";
        else if (com.pullenti.n2j.Utils.stringsEq(name, "БЕЛАРУСЬ")) 
            name = "БЕЛОРУССИЯ";
        return (String)com.pullenti.n2j.Utils.notnull(name, "?");
    }

    @Override
    public String toSortString() {
        String typ = "GEO4";
        if (isState()) 
            typ = "GEO1";
        else if (isRegion()) 
            typ = "GEO2";
        else if (isCity()) 
            typ = "GEO3";
        return typ + _getName(false);
    }

    @Override
    public java.util.ArrayList<String> getCompareStrings() {
        java.util.ArrayList<String> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) 
                res.add(s.getValue().toString());
        }
        if (res.size() > 0) 
            return res;
        else 
            return super.getCompareStrings();
    }

    public void addName(String v) {
        if (v != null) {
            if (v.indexOf('-') > 0) 
                v = v.replace(" - ", "-");
            addSlot(ATTR_NAME, v.toUpperCase(), false, 0);
        }
    }

    public void addTyp(String v) {
        if (v != null) {
            if (com.pullenti.n2j.Utils.stringsEq(v, "ТЕРРИТОРИЯ") && isState()) 
                return;
            addSlot(ATTR_TYPE, v.toLowerCase(), false, 0);
        }
    }

    public void addTypCity(com.pullenti.morph.MorphLang lang) {
        if (lang.isEn()) 
            addSlot(ATTR_TYPE, "city", false, 0);
        else if (lang.isUa()) 
            addSlot(ATTR_TYPE, "місто", false, 0);
        else 
            addSlot(ATTR_TYPE, "город", false, 0);
    }

    public void addTypReg(com.pullenti.morph.MorphLang lang) {
        if (lang.isEn()) 
            addSlot(ATTR_TYPE, "region", false, 0);
        else if (lang.isUa()) 
            addSlot(ATTR_TYPE, "регіон", false, 0);
        else 
            addSlot(ATTR_TYPE, "регион", false, 0);
    }

    public void addTypState(com.pullenti.morph.MorphLang lang) {
        if (lang.isEn()) 
            addSlot(ATTR_TYPE, "country", false, 0);
        else if (lang.isUa()) 
            addSlot(ATTR_TYPE, "держава", false, 0);
        else 
            addSlot(ATTR_TYPE, "государство", false, 0);
    }

    public void addTypUnion(com.pullenti.morph.MorphLang lang) {
        if (lang.isEn()) 
            addSlot(ATTR_TYPE, "union", false, 0);
        else if (lang.isUa()) 
            addSlot(ATTR_TYPE, "союз", false, 0);
        else 
            addSlot(ATTR_TYPE, "союз", false, 0);
    }

    public void addTypTer(com.pullenti.morph.MorphLang lang) {
        if (lang.isEn()) 
            addSlot(ATTR_TYPE, "territory", false, 0);
        else if (lang.isUa()) 
            addSlot(ATTR_TYPE, "територія", false, 0);
        else 
            addSlot(ATTR_TYPE, "территория", false, 0);
    }

    @Override
    public com.pullenti.ner.Slot addSlot(String attrName, Object attrValue, boolean clearOldValue, int statCount) {
        m_TmpBits = (short)0;
        return super.addSlot(attrName, attrValue, clearOldValue, statCount);
    }

    @Override
    public void uploadSlot(com.pullenti.ner.Slot slot, Object newVal) {
        m_TmpBits = (short)0;
        super.uploadSlot(slot, newVal);
    }

    private static final int bIT_ISCITY = 2;

    private static final int bIT_ISREGION = 4;

    private static final int bIT_ISSTATE = 8;

    private static final int bIT_ISBIGCITY = 0x10;

    private static final int bIT_ISTERRITORY = 0x20;

    private short m_TmpBits = (short)0;

    private void _recalcTmpBits() {
        m_TmpBits = (short)1;
        m_Higher = null;
        GeoReferent hi = (GeoReferent)com.pullenti.n2j.Utils.cast(getValue(ATTR_HIGHER), GeoReferent.class);
        if (hi == this || hi == null) {
        }
        else {
            java.util.ArrayList<com.pullenti.ner.Referent> li = null;
            boolean err = false;
            for(com.pullenti.ner.Referent r = (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(hi.getValue(ATTR_HIGHER), com.pullenti.ner.Referent.class); r != null; r = (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(r.getValue(ATTR_HIGHER), com.pullenti.ner.Referent.class)) {
                if (r == hi || r == this) {
                    err = true;
                    break;
                }
                if (li == null) 
                    li = new java.util.ArrayList< >();
                else if (li.contains(r)) {
                    err = true;
                    break;
                }
                li.add(r);
            }
            if (!err) 
                m_Higher = hi;
        }
        int _isState = -1;
        int isReg = -1;
        for(com.pullenti.ner.Slot t : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(t.getTypeName(), ATTR_TYPE)) {
                String val = (String)com.pullenti.n2j.Utils.cast(t.getValue(), String.class);
                if (com.pullenti.n2j.Utils.stringsEq(val, "территория") || com.pullenti.n2j.Utils.stringsEq(val, "територія") || com.pullenti.n2j.Utils.stringsEq(val, "territory")) {
                    m_TmpBits = (short)(1 | bIT_ISTERRITORY);
                    return;
                }
                if (_isCity(val)) {
                    m_TmpBits |= ((short)bIT_ISCITY);
                    if ((com.pullenti.n2j.Utils.stringsEq(val, "город") || com.pullenti.n2j.Utils.stringsEq(val, "місто") || com.pullenti.n2j.Utils.stringsEq(val, "city")) || com.pullenti.n2j.Utils.stringsEq(val, "town")) 
                        m_TmpBits |= ((short)bIT_ISBIGCITY);
                    continue;
                }
                if ((com.pullenti.n2j.Utils.stringsEq(val, "государство") || com.pullenti.n2j.Utils.stringsEq(val, "держава") || com.pullenti.n2j.Utils.stringsEq(val, "империя")) || com.pullenti.n2j.Utils.stringsEq(val, "імперія") || com.pullenti.n2j.Utils.stringsEq(val, "country")) {
                    m_TmpBits |= ((short)bIT_ISSTATE);
                    isReg = 0;
                    continue;
                }
                if (_isRegion(val)) {
                    if (_isState < 0) 
                        _isState = 0;
                    if (isReg < 0) 
                        isReg = 1;
                }
            }
            else if (com.pullenti.n2j.Utils.stringsEq(t.getTypeName(), ATTR_ALPHA2)) {
                m_TmpBits = (short)(1 | bIT_ISSTATE);
                if (findSlot(ATTR_TYPE, "город", true) != null || findSlot(ATTR_TYPE, "місто", true) != null || findSlot(ATTR_TYPE, "city", true) != null) 
                    m_TmpBits |= ((short)(bIT_ISBIGCITY | bIT_ISCITY));
                return;
            }
        }
        if (_isState != 0) {
            if ((_isState < 0) && ((((int)m_TmpBits) & bIT_ISCITY)) != 0) {
            }
            else 
                m_TmpBits |= ((short)bIT_ISSTATE);
        }
        if (isReg != 0) {
            if ((_isState < 0) && ((((int)m_TmpBits) & bIT_ISCITY)) != 0) {
            }
            else 
                m_TmpBits |= ((short)bIT_ISREGION);
        }
    }

    /**
     * Тип(ы)
     */
    public java.util.ArrayList<String> getTyps() {
        java.util.ArrayList<String> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_TYPE)) 
                res.add(s.getValue().toString());
        }
        return res;
    }


    /**
     * Это может быть населенным пунктом
     */
    public boolean isCity() {
        if (((((int)m_TmpBits) & 1)) == 0) 
            _recalcTmpBits();
        return ((((int)m_TmpBits) & bIT_ISCITY)) != 0;
    }


    /**
     * Это именно город, а не деревня или поселок
     */
    public boolean isBigCity() {
        if (((((int)m_TmpBits) & 1)) == 0) 
            _recalcTmpBits();
        return ((((int)m_TmpBits) & bIT_ISBIGCITY)) != 0;
    }


    /**
     * Это может быть отдельным государством
     */
    public boolean isState() {
        if (((((int)m_TmpBits) & 1)) == 0) 
            _recalcTmpBits();
        return ((((int)m_TmpBits) & bIT_ISSTATE)) != 0;
    }


    /**
     * Это может быть регионом в составе другого образования
     */
    public boolean isRegion() {
        if (((((int)m_TmpBits) & 1)) == 0) 
            _recalcTmpBits();
        return ((((int)m_TmpBits) & bIT_ISREGION)) != 0;
    }


    /**
     * Просто территория (например, территория аэропорта Шереметьево)
     */
    public boolean isTerritory() {
        if (((((int)m_TmpBits) & 1)) == 0) 
            _recalcTmpBits();
        return ((((int)m_TmpBits) & bIT_ISTERRITORY)) != 0;
    }


    /**
     * Союз России и Белоруссии
     */
    public boolean isUnion() {
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_TYPE)) {
                String v = (String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class);
                if (v.endsWith("союз")) 
                    return true;
            }
        }
        return false;
    }


    private static boolean _isCity(String v) {
        if ((((((((((((v.indexOf("поселок") >= 0) || (v.indexOf("селение") >= 0) || (v.indexOf("село") >= 0)) || (v.indexOf("деревня") >= 0) || (v.indexOf("станица") >= 0)) || (v.indexOf("пункт") >= 0) || (v.indexOf("станция") >= 0)) || (v.indexOf("аул") >= 0) || (v.indexOf("хутор") >= 0)) || (v.indexOf("местечко") >= 0) || (v.indexOf("урочище") >= 0)) || (v.indexOf("усадьба") >= 0) || (v.indexOf("аал") >= 0)) || (v.indexOf("выселки") >= 0) || (v.indexOf("арбан") >= 0)) || (v.indexOf("місто") >= 0) || (v.indexOf("селище") >= 0)) || (v.indexOf("сіло") >= 0) || (v.indexOf("станиця") >= 0)) || (v.indexOf("станція") >= 0) || (v.indexOf("city") >= 0)) || (v.indexOf("municipality") >= 0) || (v.indexOf("town") >= 0)) 
            return true;
        if ((v.indexOf("город") >= 0) || (v.indexOf("місто") >= 0)) {
            if (!_isRegion(v)) 
                return true;
        }
        return false;
    }

    private static boolean _isRegion(String v) {
        if (((((((((((((v.indexOf("район") >= 0) || (v.indexOf("штат") >= 0) || (v.indexOf("область") >= 0)) || (v.indexOf("волость") >= 0) || (v.indexOf("провинция") >= 0)) || (v.indexOf("регион") >= 0) || (v.indexOf("округ") >= 0)) || (v.indexOf("край") >= 0) || (v.indexOf("префектура") >= 0)) || (v.indexOf("улус") >= 0) || (v.indexOf("провінція") >= 0)) || (v.indexOf("регіон") >= 0) || (v.indexOf("образование") >= 0)) || (v.indexOf("утворення") >= 0) || (v.indexOf("автономия") >= 0)) || (v.indexOf("автономія") >= 0) || (v.indexOf("district") >= 0)) || (v.indexOf("county") >= 0) || (v.indexOf("state") >= 0)) || (v.indexOf("area") >= 0) || (v.indexOf("borough") >= 0)) || (v.indexOf("parish") >= 0) || (v.indexOf("region") >= 0)) || (v.indexOf("province") >= 0) || (v.indexOf("prefecture") >= 0)) 
            return true;
        if ((v.indexOf("городск") >= 0) || (v.indexOf("міськ") >= 0)) {
            if ((v.indexOf("образование") >= 0) || (v.indexOf("освіта") >= 0)) 
                return true;
        }
        return false;
    }

    /**
     * 2-х символьный идентификатор страны (ISO 3166)
     */
    public String getAlpha2() {
        return getStringValue(ATTR_ALPHA2);
    }

    /**
     * 2-х символьный идентификатор страны (ISO 3166)
     */
    public String setAlpha2(String value) {
        addSlot(ATTR_ALPHA2, value, true, 0);
        return value;
    }


    private GeoReferent m_Higher;

    /**
     * Вышестоящий объект
     */
    public GeoReferent getHigher() {
        if (((((int)m_TmpBits) & 1)) == 0) 
            _recalcTmpBits();
        return m_Higher;
    }

    /**
     * Вышестоящий объект
     */
    public GeoReferent setHigher(GeoReferent value) {
        if (value == this) 
            return value;
        if (value != null) {
            GeoReferent d = value;
            java.util.ArrayList<GeoReferent> li = new java.util.ArrayList< >();
            for(; d != null; d = d.getHigher()) {
                if (d == this) 
                    return value;
                else if (com.pullenti.n2j.Utils.stringsEq(d.toString(), this.toString())) 
                    return value;
                if (li.contains(d)) 
                    return value;
                li.add(d);
            }
        }
        addSlot(ATTR_HIGHER, null, true, 0);
        if (value != null) 
            addSlot(ATTR_HIGHER, value, true, 0);
        return value;
    }


    private static boolean _checkRoundDep(GeoReferent d) {
        if (d == null) 
            return true;
        GeoReferent d0 = d;
        java.util.ArrayList<GeoReferent> li = new java.util.ArrayList< >();
        for(d = d.getHigher(); d != null; d = d.getHigher()) {
            if (d == d0) 
                return true;
            if (li.contains(d)) 
                return true;
            li.add(d);
        }
        return false;
    }

    public GeoReferent getTopHigher() {
        if (_checkRoundDep(this)) 
            return this;
        for(GeoReferent hi = this; hi != null; hi = hi.getHigher()) {
            if (hi.getHigher() == null) 
                return hi;
        }
        return this;
    }


    @Override
    public com.pullenti.ner.Referent getParentReferent() {
        return getHigher();
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        GeoReferent _geo = (GeoReferent)com.pullenti.n2j.Utils.cast(obj, GeoReferent.class);
        if (_geo == null) 
            return false;
        if (_geo.getAlpha2() != null && com.pullenti.n2j.Utils.stringsEq(_geo.getAlpha2(), getAlpha2())) 
            return true;
        if (isCity() != _geo.isCity()) 
            return false;
        if (isUnion() != _geo.isUnion()) 
            return false;
        if (isUnion()) {
            for(com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_REF)) {
                    if (obj.findSlot(ATTR_REF, s.getValue(), true) == null) 
                        return false;
                }
            }
            for(com.pullenti.ner.Slot s : obj.getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_REF)) {
                    if (findSlot(ATTR_REF, s.getValue(), true) == null) 
                        return false;
                }
            }
            return true;
        }
        com.pullenti.ner.Referent ref1 = (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(getValue(ATTR_REF), com.pullenti.ner.Referent.class);
        com.pullenti.ner.Referent ref2 = (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(_geo.getValue(ATTR_REF), com.pullenti.ner.Referent.class);
        if (ref1 != null && ref2 != null) {
            if (ref1 != ref2) 
                return false;
        }
        boolean r = isRegion() || isState();
        boolean r1 = _geo.isRegion() || _geo.isState();
        if (r != r1) {
            if (isTerritory() != _geo.isTerritory()) 
                return false;
            return false;
        }
        boolean eqNames = false;
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) {
                if (_geo.findSlot(s.getTypeName(), s.getValue(), true) != null) {
                    eqNames = true;
                    break;
                }
            }
        }
        if (!eqNames) 
            return false;
        if (isRegion() && _geo.isRegion()) {
            java.util.ArrayList<String> typs1 = getTyps();
            java.util.ArrayList<String> typs2 = _geo.getTyps();
            boolean ok = false;
            for(String t : typs1) {
                if (typs2.contains(t)) 
                    ok = true;
                else 
                    for(String tt : typs2) {
                        if (com.pullenti.morph.LanguageHelper.endsWith(tt, t) || com.pullenti.morph.LanguageHelper.endsWith(t, tt)) 
                            ok = true;
                    }
            }
            if (!ok) 
                return false;
        }
        if (getHigher() != null && _geo.getHigher() != null) {
            if (_checkRoundDep(this) || _checkRoundDep(_geo)) 
                return false;
            if (getHigher().canBeEquals(_geo.getHigher(), typ)) {
            }
            else if (_geo.getHigher().getHigher() != null && getHigher().canBeEquals(_geo.getHigher().getHigher(), typ)) {
            }
            else if (getHigher().getHigher() != null && getHigher().getHigher().canBeEquals(_geo.getHigher(), typ)) {
            }
            else 
                return false;
        }
        return true;
    }

    public void mergeSlots2(com.pullenti.ner.Referent obj, com.pullenti.morph.MorphLang lang) {
        boolean mergeStatistic = true;
        for(com.pullenti.ner.Slot s : obj.getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), GeoReferent.ATTR_NAME) || com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), GeoReferent.ATTR_TYPE)) {
                String nam = s.getValue().toString();
                if (com.pullenti.morph.LanguageHelper.isLatinChar(nam.charAt(0))) {
                    if (!lang.isEn()) 
                        continue;
                }
                else if (lang.isEn()) 
                    continue;
                if (com.pullenti.morph.LanguageHelper.endsWith(nam, " ССР")) 
                    continue;
            }
            addSlot(s.getTypeName(), s.getValue(), false, (mergeStatistic ? s.getCount() : 0));
        }
        if (findSlot(GeoReferent.ATTR_NAME, null, true) == null && obj.findSlot(GeoReferent.ATTR_NAME, null, true) != null) {
            for(com.pullenti.ner.Slot s : obj.getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), GeoReferent.ATTR_NAME)) 
                    addSlot(s.getTypeName(), s.getValue(), false, (mergeStatistic ? s.getCount() : 0));
            }
        }
        if (findSlot(GeoReferent.ATTR_TYPE, null, true) == null && obj.findSlot(GeoReferent.ATTR_TYPE, null, true) != null) {
            for(com.pullenti.ner.Slot s : obj.getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), GeoReferent.ATTR_TYPE)) 
                    addSlot(s.getTypeName(), s.getValue(), false, (mergeStatistic ? s.getCount() : 0));
            }
        }
        if (isTerritory()) {
            if (((getAlpha2() != null || findSlot(ATTR_TYPE, "государство", true) != null || findSlot(ATTR_TYPE, "держава", true) != null) || findSlot(ATTR_TYPE, "империя", true) != null || findSlot(ATTR_TYPE, "імперія", true) != null) || findSlot(ATTR_TYPE, "state", true) != null) {
                com.pullenti.ner.Slot s = findSlot(ATTR_TYPE, "территория", true);
                if (s != null) 
                    getSlots().remove(s);
            }
        }
        if (isState()) {
            for(com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_TYPE) && ((com.pullenti.n2j.Utils.stringsEq(s.getValue().toString(), "регион") || com.pullenti.n2j.Utils.stringsEq(s.getValue().toString(), "регіон") || com.pullenti.n2j.Utils.stringsEq(s.getValue().toString(), "region")))) {
                    getSlots().remove(s);
                    break;
                }
            }
        }
        if (isCity()) {
            com.pullenti.ner.Slot s = (com.pullenti.ner.Slot)com.pullenti.n2j.Utils.notnull(findSlot(ATTR_TYPE, "город", true), com.pullenti.n2j.Utils.notnull(findSlot(ATTR_TYPE, "місто", true), findSlot(ATTR_TYPE, "city", true)));
            if (s != null) {
                for(com.pullenti.ner.Slot ss : getSlots()) {
                    if (com.pullenti.n2j.Utils.stringsEq(ss.getTypeName(), ATTR_TYPE) && ss != s && _isCity(ss.getValue().toString())) {
                        getSlots().remove(s);
                        break;
                    }
                }
            }
        }
        boolean has = false;
        for(int i = 0; i < getSlots().size(); i++) {
            if (com.pullenti.n2j.Utils.stringsEq(getSlots().get(i).getTypeName(), ATTR_HIGHER)) {
                if (!has) 
                    has = true;
                else {
                    getSlots().remove(i);
                    i--;
                }
            }
        }
        _mergeExtReferents(obj);
    }

    @Override
    public com.pullenti.ner.core.IntOntologyItem createOntologyItem() {
        boolean __isCity = isCity();
        com.pullenti.ner.core.IntOntologyItem oi = new com.pullenti.ner.core.IntOntologyItem(this);
        for(com.pullenti.ner.Slot a : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(a.getTypeName(), ATTR_NAME)) {
                String s = a.getValue().toString();
                com.pullenti.ner.core.Termin t = new com.pullenti.ner.core.Termin(null, new com.pullenti.morph.MorphLang(null), false);
                t.initByNormalText(s, new com.pullenti.morph.MorphLang(null));
                if (__isCity) 
                    t.addStdAbridges();
                oi.termins.add(t);
            }
        }
        return oi;
    }

    public boolean checkAbbr(String abbr) {
        if (abbr.length() != 2) 
            return false;
        boolean nameq = false;
        boolean typeq = false;
        boolean nameq2 = false;
        boolean typeq2 = false;
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) {
                String val = (String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class);
                char ch = val.charAt(0);
                if (ch == abbr.charAt(0)) {
                    nameq = true;
                    int ii = val.indexOf(' ');
                    if (ii > 0) {
                        if (abbr.charAt(1) == val.charAt(ii + 1)) {
                            if (val.indexOf(' ', ii + 1) < 0) 
                                return true;
                        }
                    }
                }
                if (ch == abbr.charAt(1)) 
                    nameq2 = true;
            }
            else if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_TYPE)) {
                String ty = s.getValue().toString();
                if (com.pullenti.n2j.Utils.stringsEq(ty, "государство") || com.pullenti.n2j.Utils.stringsEq(ty, "держава") || com.pullenti.n2j.Utils.stringsEq(ty, "country")) 
                    continue;
                char ch = Character.toUpperCase(ty.charAt(0));
                if (ch == abbr.charAt(1)) 
                    typeq = true;
                if (ch == abbr.charAt(0)) 
                    typeq2 = true;
            }
        }
        if (typeq && nameq) 
            return true;
        if (typeq2 && nameq2) 
            return true;
        return false;
    }

    /**
     * Добавляем ссылку на организацию, также добавляем имена
     * @param org 
     */
    public void addOrgReferent(com.pullenti.ner.Referent org) {
        if (org == null) 
            return;
        boolean nam = false;
        addSlot(ATTR_REF, org, false, 0);
        GeoReferent _geo = null;
        String specTyp = null;
        String num = org.getStringValue("NUMBER");
        for(com.pullenti.ner.Slot s : org.getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), "NAME")) {
                if (num == null) 
                    addName((String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class));
                else 
                    addName(s.getValue().toString() + "-" + num);
                nam = true;
            }
            else if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), "TYPE")) {
                String v = (String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class);
                if (com.pullenti.n2j.Utils.stringsEq(v, "СЕЛЬСКИЙ СОВЕТ")) 
                    addTyp("сельский округ");
                else if (com.pullenti.n2j.Utils.stringsEq(v, "ГОРОДСКОЙ СОВЕТ")) 
                    addTyp("городской округ");
                else if (com.pullenti.n2j.Utils.stringsEq(v, "ПОСЕЛКОВЫЙ СОВЕТ")) 
                    addTyp("поселковый округ");
                else if (com.pullenti.n2j.Utils.stringsEq(v, "аэропорт")) 
                    specTyp = v.toUpperCase();
            }
            else if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), "GEO") && (s.getValue() instanceof GeoReferent)) 
                _geo = (GeoReferent)com.pullenti.n2j.Utils.cast(s.getValue(), GeoReferent.class);
        }
        if (!nam) {
            for(com.pullenti.ner.Slot s : org.getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), "EPONYM")) {
                    if (num == null) 
                        addName((((String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class))).toUpperCase());
                    else 
                        addName((((String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class))).toUpperCase() + "-" + num);
                    nam = true;
                }
            }
        }
        if (!nam && num != null) {
            for(com.pullenti.ner.Slot s : org.getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), "TYPE")) {
                    addName((((String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class))).toUpperCase() + "-" + num);
                    nam = true;
                }
            }
        }
        if (_geo != null && !nam) {
            for(String n : _geo.getStringValues(GeoReferent.ATTR_NAME)) {
                addName(n);
                if (specTyp != null) {
                    addName(n + " " + specTyp);
                    addName(specTyp + " " + n);
                }
                nam = true;
            }
        }
        if (!nam) 
            addName(org.toString(true, com.pullenti.morph.MorphLang.UNKNOWN, 0).toUpperCase());
    }
}
