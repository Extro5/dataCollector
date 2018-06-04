/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic.internal;

public class Anafor implements Comparable<Anafor> {

    public SynToken source;

    public SynToken target;

    public float coef = (float)0;

    @Override
    public String toString() {
        return ((Float)coef).toString() + ": " + target.toString() + " <= " + source.toString();
    }

    private static boolean _CanBeAnafor(SynToken sy) {
        if (sy.typ == Types.PRONOUNOBJ) 
            return true;
        if (sy.anaforRef0 != null) {
            if (!sy.anaforRef0.isValue("САМ", null)) 
                return true;
        }
        return false;
    }

    private static Anafor _TryCreate(SynToken src, SynToken tgt, CreateTyp typ) {
        if (!tgt.isActant()) 
            return null;
        if (tgt.getMorph().getNumber() == com.pullenti.morph.MorphNumber.UNDEFINED && tgt.ref == null) 
            return null;
        if (src.endChar <= tgt.endChar) 
            return null;
        float _coef = (float)0;
        com.pullenti.ner.MorphCollection _morph = null;
        if (src.typ == Types.PRONOUNOBJ) {
            if (tgt.typ == Types.ACTANT) {
                for(SynToken ch : tgt.children) {
                    if (ch.typ == Types.PRONOUNOBJ) 
                        return null;
                }
            }
            if (tgt.typ == Types.PRONOUNOBJ) {
                for(com.pullenti.morph.MorphBaseInfo it : src.getMorph().getItems()) {
                    boolean eq = false;
                    for(com.pullenti.morph.MorphBaseInfo it2 : tgt.getMorph().getItems()) {
                        if (com.pullenti.n2j.Utils.stringsEq((((com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(it, com.pullenti.morph.MorphWordForm.class))).normalCase, (((com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(it2, com.pullenti.morph.MorphWordForm.class))).normalCase)) {
                            eq = true;
                            break;
                        }
                    }
                    if (eq) {
                        _coef = (float)2;
                        break;
                    }
                }
            }
            else {
                _morph = src.getMorph();
                _coef = (float)_morphCoef(src.getMorph(), tgt);
                if (_coef <= 0 && src.isValue("СВОЙ", "СВІЙ")) {
                    if ((((tgt.getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.SINGULAR.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                        _coef = (float)2;
                }
            }
        }
        else if (src.anaforRef0 != null) {
            if (src.ref != null) 
                return null;
            _morph = src.anaforRef0.getMorph();
            _coef = (float)_morphCoef(src.anaforRef0.getMorph(), tgt);
            if (_coef > 0 && src.getBase() != null && src.npt != null) {
                if (com.pullenti.n2j.Utils.stringsNe(tgt.getBase(), src.getBase())) 
                    _coef = (float)0;
            }
            if (tgt.ref != null) {
                String bas = src.getBase();
                if (com.pullenti.n2j.Utils.stringsEq(tgt.ref.getTypeName(), "ORGANIZATION")) {
                    if ((com.pullenti.n2j.Utils.stringsEq(bas, "КОМПАНИЯ") || com.pullenti.n2j.Utils.stringsEq(bas, "ФИРМА") || com.pullenti.n2j.Utils.stringsEq(bas, "КОМПАНІЯ")) || com.pullenti.n2j.Utils.stringsEq(bas, "ФІРМА")) 
                        _coef = (float)3;
                    else if (src.typ == Types.WHAT && src.getMorph().getNumber() != com.pullenti.morph.MorphNumber.PLURAL) 
                        _coef = (float)3;
                }
                else if (com.pullenti.n2j.Utils.stringsEq(tgt.ref.getTypeName(), "PERSON") || com.pullenti.n2j.Utils.stringsEq(tgt.ref.getTypeName(), "PERSONPROPERTY")) {
                    if (src.typ == Types.WHAT && src.getMorph().getNumber() != com.pullenti.morph.MorphNumber.PLURAL) 
                        _coef = (float)3;
                }
            }
        }
        if (_coef > 0) {
            if (tgt.ref != null) {
                if (com.pullenti.n2j.Utils.stringsEq(tgt.ref.getTypeName(), "PERSON")) 
                    _coef += ((float)1);
                else if (com.pullenti.n2j.Utils.stringsEq(tgt.ref.getTypeName(), "ORGANIZATION")) 
                    _coef += ((float)1);
                else if (com.pullenti.n2j.Utils.stringsEq(tgt.ref.getTypeName(), "PERSONPROPERTY")) 
                    _coef += ((float)1);
            }
            else if (tgt.typ == Types.PROPERNAME) 
                _coef++;
            else if (tgt.getMorph().containsAttr("одуш.", new com.pullenti.morph.MorphClass(null))) 
                _coef++;
            else if (tgt.getEndToken().getMorphClassInDictionary().isNoun()) {
                if (tgt.isAnimatedOrNamed()) 
                    _coef++;
            }
            int cou = 0;
            for(com.pullenti.morph.MorphBaseInfo it : tgt.getMorph().getItems()) {
                if (it._getClass().isPronoun() || it._getClass().isPersonalPronoun()) 
                    cou++;
            }
            if (cou > 1) 
                _coef /= ((float)cou);
            if (tgt.typ == Types.ACTANT) {
                if ((((tgt.getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) {
                    if (_morph != null && _morph.getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                        _coef += ((float)0.5);
                    else 
                        _coef += ((float)0.1);
                }
                else 
                    _coef -= ((float)0.1);
            }
        }
        if (_coef > 0) 
            return _new2475(src, tgt, _coef);
        return null;
    }

    private static int _morphCoef(com.pullenti.ner.MorphCollection sm, SynToken tgt) {
        if ((((sm.getNumber().value()) & (tgt.getMorph().getNumber().value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) {
            if ((((sm.getNumber().value()) & (com.pullenti.morph.MorphNumber.SINGULAR.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value()) && tgt.typ != Types.ACTANT) {
                if (sm.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                    for(com.pullenti.morph.MorphBaseInfo it : tgt.getMorph().getItems()) {
                        if (it.getNumber() == com.pullenti.morph.MorphNumber.SINGULAR && (((it.getGender().value()) & (sm.getGender().value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                            return (sm.getNumber() == com.pullenti.morph.MorphNumber.SINGULAR ? 2 : 1);
                    }
                }
            }
            if ((((sm.getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) {
                for(com.pullenti.morph.MorphBaseInfo it : tgt.getMorph().getItems()) {
                    if (it.getNumber() == com.pullenti.morph.MorphNumber.PLURAL) {
                        if (sm.findItem(sm.getCase(), com.pullenti.morph.MorphNumber.PLURAL, com.pullenti.morph.MorphGender.UNDEFINED) != null) 
                            return 2;
                        return 1;
                    }
                }
                if (tgt.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                    return 2;
                else 
                    return 1;
            }
            else if (tgt.typ == Types.ACTANT) 
                return -1;
            return 1;
        }
        return 0;
    }

    public static void process(java.util.ArrayList<SynToken> li, java.util.ArrayList<SynToken> prev, java.util.ArrayList<SynToken> sup) {
        for(int i = 0; i < li.size(); i++) {
            if (_CanBeAnafor(li.get(i))) {
                SynToken an = li.get(i);
                java.util.ArrayList<Anafor> aa = null;
                for(int j = i - 1; j >= 0; j--) {
                    for(SynToken ob = li.get(j); ob != null; ob = (ob.typ == Types.OBJ && ob.children.size() > 0 ? ob.children.get(0) : null)) {
                        Anafor a = _TryCreate(an, ob, CreateTyp.LOCAL);
                        if (a != null) {
                            a.coef++;
                            a.coef += (((float)((j))) / ((float)i));
                            if (aa == null) 
                                aa = new java.util.ArrayList< >();
                            aa.add(a);
                        }
                    }
                }
                if (sup != null) {
                    for(int j = sup.size() - 1; j >= 0; j--) {
                        Anafor a = _TryCreate(an, sup.get(j), CreateTyp.ORTO);
                        if (a != null) {
                            a.coef += ((float)2);
                            if (aa == null) 
                                aa = new java.util.ArrayList< >();
                            aa.add(a);
                        }
                    }
                }
                if (prev != null) {
                    for(int j = prev.size() - 1; j >= 0; j--) {
                        Anafor a = _TryCreate(an, prev.get(j), CreateTyp.HISTORY);
                        if (a != null) {
                            if (aa == null) 
                                aa = new java.util.ArrayList< >();
                            aa.add(a);
                            a.coef += (((float)((j))) / ((float)(int)prev.size()));
                        }
                    }
                }
                if (aa == null) 
                    continue;
                java.util.Collections.sort(aa);
                li.get(i).setAnaforRef(aa.get(0).target);
            }
        }
    }

    @Override
    public int compareTo(Anafor other) {
        if (coef > other.coef) 
            return -1;
        if (coef < other.coef) 
            return 1;
        return 0;
    }

    public static class CreateTyp implements Comparable<CreateTyp> {
    
        public static final CreateTyp LOCAL; // 0
    
        public static final CreateTyp ORTO; // 1
    
        public static final CreateTyp HISTORY; // 2
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private CreateTyp(int val, String str) { m_val = val; m_str = str; }
        @Override
        public String toString() {
            if(m_str != null) return m_str;
            return ((Integer)m_val).toString();
        }
        @Override
        public int hashCode() {
            return (int)m_val;
        }
        @Override
        public int compareTo(CreateTyp v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, CreateTyp> mapIntToEnum; 
        private static java.util.HashMap<String, CreateTyp> mapStringToEnum; 
        public static CreateTyp of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            CreateTyp item = new CreateTyp(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static CreateTyp of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        static {
            mapIntToEnum = new java.util.HashMap< >();
            mapStringToEnum = new java.util.HashMap< >();
            LOCAL = new CreateTyp(0, "LOCAL");
            mapIntToEnum.put(LOCAL.value(), LOCAL);
            mapStringToEnum.put(LOCAL.m_str.toUpperCase(), LOCAL);
            ORTO = new CreateTyp(1, "ORTO");
            mapIntToEnum.put(ORTO.value(), ORTO);
            mapStringToEnum.put(ORTO.m_str.toUpperCase(), ORTO);
            HISTORY = new CreateTyp(2, "HISTORY");
            mapIntToEnum.put(HISTORY.value(), HISTORY);
            mapStringToEnum.put(HISTORY.m_str.toUpperCase(), HISTORY);
        }
    }


    public static Anafor _new2475(SynToken _arg1, SynToken _arg2, float _arg3) {
        Anafor res = new Anafor();
        res.source = _arg1;
        res.target = _arg2;
        res.coef = _arg3;
        return res;
    }
    public Anafor() {
    }
    public static Anafor _globalInstance;
    static {
        _globalInstance = new Anafor(); 
    }
}
