/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic.internal;

public class History {

    public java.util.ArrayList<SynToken> prevList = new java.util.ArrayList< >();

    public void add(java.util.ArrayList<SynToken> li) {
        li.sort(m_Comp);
        for(SynToken s : li) {
            if (s.real instanceof com.pullenti.ner.semantic.ObjectReferent) {
                com.pullenti.ner.semantic.ObjectReferent sr = (com.pullenti.ner.semantic.ObjectReferent)com.pullenti.n2j.Utils.cast(s.real, com.pullenti.ner.semantic.ObjectReferent.class);
                prevList.add(s);
                java.util.ArrayList<String> abs = sr._getAbbrs();
                if (abs != null && abs.size() > 0) {
                    for(String a : abs) {
                        if (abbrs.containsKey(a)) 
                            abbrs.put(a, sr);
                        else 
                            abbrs.put(a, sr);
                    }
                }
                for(com.pullenti.ner.Slot v : s.real.getSlots()) {
                    if (com.pullenti.n2j.Utils.stringsEq(v.getTypeName(), com.pullenti.ner.semantic.ObjectReferent.ATTR_NAME)) {
                        String n = (String)com.pullenti.n2j.Utils.cast(v.getValue(), String.class);
                        if (names.containsKey(n)) 
                            names.put(n, sr);
                        else 
                            names.put(n, sr);
                    }
                    else if (com.pullenti.n2j.Utils.stringsEq(v.getTypeName(), com.pullenti.ner.semantic.ObjectReferent.ATTR_ALIAS)) {
                        String n = (String)com.pullenti.n2j.Utils.cast(v.getValue(), String.class);
                        if (aliases.containsKey(n)) 
                            aliases.put(n, sr);
                        else 
                            aliases.put(n, sr);
                    }
                }
            }
        }
        if (prevList.size() > 30) 
            for(int indRemoveRange = 0 + prevList.size() - 30 - 1, indMinIndex = 0; indRemoveRange >= indMinIndex; indRemoveRange--) prevList.remove(indRemoveRange);
    }

    /**
     * Список возможных сокращений
     */
    public java.util.HashMap<String, com.pullenti.ner.semantic.ObjectReferent> abbrs = new java.util.HashMap< >();

    /**
     * Список возможных собственных имён
     */
    public java.util.HashMap<String, com.pullenti.ner.semantic.ObjectReferent> names = new java.util.HashMap< >();

    /**
     * Список возможных псевдонимов
     */
    public java.util.HashMap<String, com.pullenti.ner.semantic.ObjectReferent> aliases = new java.util.HashMap< >();

    private static CompTokens m_Comp;

    public static class CompTokens implements java.util.Comparator<com.pullenti.ner.semantic.internal.SynToken> {
    
        @Override
        public int compare(com.pullenti.ner.semantic.internal.SynToken x, com.pullenti.ner.semantic.internal.SynToken y) {
            if (x.beginChar < y.beginChar) 
                return -1;
            if (x.beginChar > y.beginChar) 
                return 1;
            if (x.endChar < y.endChar) 
                return -1;
            if (x.endChar > y.endChar) 
                return 1;
            return 0;
        }
        public CompTokens() {
        }
    }


    public static boolean _checkMorphForRef(com.pullenti.ner.MorphCollection m1, com.pullenti.ner.MorphCollection m2, boolean checkCase) {
        if (m1.getItemsCount() > 0 && m2.getItemsCount() > 0) {
            for(com.pullenti.morph.MorphBaseInfo it1 : m1.getItems()) {
                for(com.pullenti.morph.MorphBaseInfo it2 : m2.getItems()) {
                    if ((((it1.getNumber().value()) & (it2.getNumber().value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value()) || it1.getNumber() == it2.getNumber()) {
                        if (checkCase) {
                            if (((com.pullenti.morph.MorphCase.ooBitand(it1.getCase(), it2.getCase()))).isUndefined()) 
                                continue;
                        }
                        if (it1.getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
                            if ((((it1.getGender().value()) & (it2.getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                                continue;
                        }
                        return true;
                    }
                }
            }
        }
        if ((((m1.getNumber().value()) & (m2.getNumber().value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
            return false;
        if (checkCase) {
            if (((com.pullenti.morph.MorphCase.ooBitand(m1.getCase(), m2.getCase()))).isUndefined()) 
                return false;
        }
        if (m1.getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
            return true;
        if ((((m1.getNumber().value()) & (com.pullenti.morph.MorphNumber.SINGULAR.value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
            return false;
        if ((((m1.getGender().value()) & (m2.getGender().value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
            return true;
        return false;
    }

    public void manageAnaforRefs(java.util.ArrayList<SynToken> li) {
        for(int i = 0; i < li.size(); i++) {
            if ((((li.get(i).typ == Types.OBJ || li.get(i).typ == Types.NUMBER || li.get(i).typ == Types.EMPTY) || li.get(i).typ == Types.WHAT)) && li.get(i).anaforRef0 != null) {
                SynToken sy0 = null;
                com.pullenti.ner.MorphCollection m = li.get(i).anaforRef0.getMorph();
                int j;
                boolean loc = false;
                boolean oo = false;
                if (m._getClass().isPersonalPronoun() || li.get(i).anaforRef0.isValue("КОТОРЫЙ", "КОТРИЙ") || li.get(i).anaforRef0.isValue("КОТОРЫЙ", "ЯКИЙ")) {
                    int max = 0;
                    for(j = i - 1; j >= 0; j--) {
                        if (oo && ((li.get(j).typ == Types.OBJ || li.get(j).typ == Types.NUMBER || li.get(j).typ == Types.PROPERNAME)) && _checkMorphForRef(li.get(j).getMorph(), m, false)) {
                            if ((li.get(j).getMorph().getCase().isGenitive() && li.get(j).preposition == null && j > 0) && li.get(j - 1).typ == Types.OBJ && _checkMorphForRef(li.get(j - 1).getMorph(), m, true)) 
                                continue;
                            int coef = 1;
                            if (j == 0) 
                                coef++;
                            else if (li.get(j - 1).isConjOrComma()) 
                                coef++;
                            if (li.get(j).anaforRef0 != null) 
                                coef--;
                            if (coef > max) {
                                sy0 = li.get(j);
                                loc = true;
                                max = coef;
                            }
                        }
                        else if (li.get(j).typ != Types.OBJ || li.get(j).preposition != null) 
                            oo = true;
                    }
                    for(j = prevList.size() - 1; j >= 0; j--) {
                        if (((prevList.get(j).typ == Types.OBJ || prevList.get(j).typ == Types.NUMBER || prevList.get(j).typ == Types.PROPERNAME)) && _checkMorphForRef(prevList.get(j).getMorph(), m, false) && prevList.get(j).real != null) {
                            int coef = 1;
                            if (j == 0) 
                                coef++;
                            else if (prevList.get(j - 1).typ == Types.SEQEND) 
                                coef++;
                            if (prevList.get(j).getVal(ValTypes.NAME) != null) 
                                coef += 2;
                            if (coef > max) {
                                sy0 = prevList.get(j);
                                loc = false;
                                max = coef;
                            }
                        }
                        else if (prevList.get(j).typ == Types.SEQEND && (j < (prevList.size() - 1)) && sy0 != null) 
                            break;
                    }
                }
                else if (li.get(i).anaforRef0.isValue("СВОЙ", null)) {
                    for(j = i - 1; j >= 0; j--) {
                        if (oo && li.get(j).typ == Types.OBJ) {
                            if ((li.get(j).getMorph().getCase().isGenitive() && li.get(j).preposition == null && j > 0) && li.get(j - 1).typ == Types.OBJ) 
                                continue;
                            sy0 = li.get(j);
                            loc = true;
                            break;
                        }
                        else if (li.get(j).typ == Types.ACT || li.get(j).typ == Types.ACTPRICH) 
                            oo = true;
                    }
                }
                else if (li.get(i).getBeginToken().isValue("ОБ", null) && ((li.get(i).anaforRef0.isValue("ЭТО", null) || li.get(i).anaforRef0.isValue("ТО", null))) && li.get(i).getMorph().getCase().isPrepositional()) {
                    for(j = prevList.size() - 1; j >= 0; j--) {
                        if (prevList.get(j).typ == Types.ACT) 
                            sy0 = prevList.get(j);
                        else if (prevList.get(j).typ == Types.SEQEND && sy0 != null) 
                            break;
                    }
                }
                if (sy0 != null && li.get(i).children.size() == 0) {
                    if (li.get(i).typ == Types.NUMBER) {
                        li.get(i).addChild(sy0);
                        li.get(i).typ = Types.OBJ;
                        continue;
                    }
                    if (li.get(i).typ == Types.EMPTY) {
                        li.get(i).setAnaforRef(sy0);
                        li.get(i).typ = Types.OBJ;
                        continue;
                    }
                    if (li.get(i).typ == Types.WHAT) {
                        li.get(i).setAnaforRef((sy0.getAnaforRef() == null ? sy0 : sy0.getAnaforRef()));
                        continue;
                    }
                    SynToken sy = SynToken._new2480(li.get(i).anaforRef0, li.get(i).anaforRef0, Types.OBJ);
                    if (loc) 
                        sy.setAnaforRef((sy0.getAnaforRef() == null ? sy0 : sy0.getAnaforRef()));
                    else 
                        sy.real = sy0.real;
                    sy.setMorph(m);
                    li.get(i).addChild(sy);
                    if ((((i + 2) < li.size()) && li.get(i + 1).typ == Types.CONJ && li.get(i + 2).typ == Types.OBJ) && ObjectHelper._calcMorphAccordCoef(li.get(i), li.get(i + 2), false) > 0) {
                        if (((i + 3) < li.size()) && li.get(i + 3).typ == Types.OBJ && li.get(i + 3).getMorph().getCase().isGenitive()) {
                        }
                        else 
                            li.get(i + 2).addChild(sy);
                    }
                    continue;
                }
            }
            if ((li.get(i).typ == Types.OBJ && li.get(i).anaforRef0 != null && li.get(i).anaforRef0.getMorph()._getClass().isPronoun()) && li.get(i).getBase() != null) {
                SynToken sy0 = null;
                String str = li.get(i).getBase();
                int j;
                boolean loc = false;
                for(j = i - 1; j >= 0; j--) {
                    if (li.get(j).typ == Types.OBJ && li.get(j).isRootValue(str, null)) {
                        sy0 = li.get(j);
                        loc = true;
                        break;
                    }
                }
                if (sy0 == null) {
                    for(j = prevList.size() - 1; j >= 0; j--) {
                        if (prevList.get(j).canBeProperName() && li.get(i).anaforRef0.isValue("ЭТОТ", null)) {
                            for(SynToken ch : prevList.get(j).children) {
                                if (ch.isRootValue(str, null)) {
                                    sy0 = prevList.get(j);
                                    break;
                                }
                            }
                            if (sy0 != null) 
                                break;
                        }
                    }
                }
                if (sy0 != null) {
                    if (loc) 
                        li.get(i).setAnaforRef((sy0.getAnaforRef() == null ? sy0 : sy0.getAnaforRef()));
                    else 
                        li.get(i).real = sy0.real;
                    li.get(i).clearVals();
                    continue;
                }
            }
        }
    }

    public void manageNames(java.util.ArrayList<SynToken> li) {
        for(SynToken l : li) {
            if (l.typ == Types.OBJ || l.typ == Types.PROPERNAME) {
                if ((l.getBeginToken() == l.getEndToken() && l.getBeginToken().chars.isAllUpper() && l.getLengthChar() > 1) && (l.getBeginToken() instanceof com.pullenti.ner.TextToken)) {
                    String v = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(l.getBeginToken(), com.pullenti.ner.TextToken.class))).term;
                    com.pullenti.ner.semantic.ObjectReferent sr = null;
                    com.pullenti.n2j.Outargwrapper<com.pullenti.ner.semantic.ObjectReferent> inoutarg2484 = new com.pullenti.n2j.Outargwrapper< >();
                    Boolean inoutres2485 = com.pullenti.n2j.Utils.tryGetValue(abbrs, v, inoutarg2484);
                    sr = inoutarg2484.value;
                    if (inoutres2485) {
                        l.ref = sr;
                        l.typ = (sr.getName() == null ? Types.OBJ : Types.PROPERNAME);
                        if (l.typ == Types.OBJ) {
                            sr.addSlot(com.pullenti.ner.semantic.ObjectReferent.ATTR_ALIAS, v, false, 0);
                            l.delVals(ValTypes.NAME);
                        }
                    }
                }
            }
        }
    }
    public History() {
    }
    public static History _globalInstance;
    static {
        _globalInstance = new History(); 
        m_Comp = new CompTokens();
    }
}
