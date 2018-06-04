/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic.internal;

public class ObjectHelper {

    public static void processObjects(java.util.ArrayList<SynToken> li, History hist) {
        PredicateHelper._clearAdverbs(li);
        _ruleProperNames(li, hist);
        java.util.ArrayList<SynToken> tmp = new java.util.ArrayList< >();
        for(int i = 0; i < li.size(); i++) {
            if (li.get(i).isActant() || ((li.get(i).isPredicateInfinitive() && li.get(i).canTransformToTyp(Types.OBJ)))) {
                tmp.clear();
                int j = i;
                for(; j < li.size(); j++) {
                    if ((li.get(j).isActant() || ((li.get(j).isPredicateInfinitive() && li.get(j).canTransformToTyp(Types.OBJ))) || li.get(j).typ == Types.CONJ) || li.get(j).typ == Types.COMMA) {
                        if (j > i && i > 0 && ((li.get(i - 1).typ == Types.ACTPRICH && !li.get(i - 1).isPredicateBe()))) {
                            Actant a = Actant.tryCreate(li.get(j), li.get(i - 1));
                            if (a != null && a.agentCoef > 0) {
                                if (li.get(j - 1).typ == Types.NUMBER && cancNextCoef(li.get(j - 1), li.get(j)) > 0) {
                                }
                                else {
                                    Actant aa = Actant.tryCreate(li.get(i), li.get(i - 1));
                                    if (aa != null && aa.agentCoef > 0) {
                                    }
                                    else 
                                        break;
                                }
                            }
                        }
                        if (li.get(j).isPredicateInfinitive() && ((j + 1) < li.size())) {
                            Actant a = Actant.tryCreate(li.get(j + 1), li.get(j));
                            if (a != null) 
                                break;
                        }
                        if (tmp.size() >= 7) {
                            if (!li.get(j).isActant()) 
                                break;
                            if (li.size() >= 10) 
                                break;
                        }
                        tmp.add(li.get(j));
                    }
                    else 
                        break;
                }
                for(int kk = tmp.size() - 1; kk >= 0; kk--) {
                    if (tmp.get(kk).isActant()) 
                        break;
                    else {
                        tmp.remove(kk);
                        j--;
                    }
                }
                if (tmp.size() < 2) 
                    continue;
                java.util.ArrayList<SynToken> gvars = ObjectsLinks.createLinks(tmp);
                if (gvars != null) {
                    for(SynToken ch : gvars) {
                        if (ch.typ == Types.NUMBER && ch.children.size() > 0) {
                            gvars = (java.util.ArrayList<SynToken>)com.pullenti.n2j.Utils.notnull(ObjectsLinks.createLinks(gvars), gvars);
                            break;
                        }
                    }
                    for(int indRemoveRange = i + j - i - 1, indMinIndex = i; indRemoveRange >= indMinIndex; indRemoveRange--) li.remove(indRemoveRange);
                    li.addAll(i, gvars);
                    j = (i + gvars.size()) - 1;
                }
                i = j - 1;
            }
        }
        for(SynToken c : li) {
            _correctAfter(c, 0);
        }
    }

    public static int cancAndCoef(SynToken prev, SynToken next) {
        int co = ObjectHelper._calcMorphAccordCoef(prev, next, false);
        if (co <= 0) 
            return co;
        int pcou = 0;
        for(com.pullenti.ner.Token t = prev.getBeginToken(); t != null && t.endChar <= prev.endChar; t = t.getNext()) {
            if (t.getMorph()._getClass().isPreposition()) 
                continue;
            pcou++;
        }
        int ncou = 0;
        for(com.pullenti.ner.Token t = next.getBeginToken(); t != null && t.endChar <= next.endChar; t = t.getNext()) {
            if (t.getMorph()._getClass().isPreposition()) 
                continue;
            ncou++;
        }
        if (pcou == ncou) 
            co++;
        return co;
    }

    public static int cancNextCoef(SynToken prev, SynToken next) {
        if (prev.typ == Types.PRONOUNOBJ) {
            if (next.preposition != null) 
                return 0;
            for(com.pullenti.morph.MorphBaseInfo it : prev.getMorph().getItems()) {
                if (it._getClass().isPronoun()) {
                    if ((((com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(it, com.pullenti.morph.MorphWordForm.class))).misc.getAttrs().contains("неизм.")) 
                        return 1;
                    for(com.pullenti.morph.MorphBaseInfo it2 : next.getMorph().getItems()) {
                        if (it.checkAccord(it2, false)) 
                            return 2;
                    }
                }
            }
            return 0;
        }
        if (prev.isMeasured() && next.canBeMesure()) 
            return 2;
        if (prev.canBeMesure() && next.isMeasured() && next.preposition == null) 
            return 2;
        if (prev.typ == Types.NUMBER || prev.typ == Types.ACTANT) {
            if (next.typ == Types.ACTANT) 
                return 0;
            int co = _calcMorphAccordCoef(prev, next, false);
            boolean ok = co > 1;
            if (!ok && next.preposition != null) {
                if ((com.pullenti.n2j.Utils.stringsEq(next.preposition, "СРЕДИ") || com.pullenti.n2j.Utils.stringsEq(next.preposition, "ИЗ") || com.pullenti.n2j.Utils.stringsEq(next.preposition, "СЕРЕД")) || com.pullenti.n2j.Utils.stringsEq(next.preposition, "З")) {
                    ok = true;
                    co = 1;
                }
            }
            if (!ok && prev.typ == Types.NUMBER) {
                ok = true;
                co = 1;
            }
            if (ok) 
                return co;
            return 0;
        }
        java.util.ArrayList<com.pullenti.morph.DerivateWord> ews = prev.getExplainInfo();
        if (ews.size() > 0) {
            if (next.typ == Types.NUMBER) {
                for(com.pullenti.morph.DerivateWord ew : ews) {
                    if (ew.attrs.isNumbered()) 
                        return 2;
                }
                return -1;
            }
            String pref = (String)com.pullenti.n2j.Utils.notnull(next.preposition, "");
            for(com.pullenti.morph.DerivateWord ew : ews) {
                if (ew.nexts != null) {
                    com.pullenti.morph.MorphCase cas;
                    com.pullenti.n2j.Outargwrapper<com.pullenti.morph.MorphCase> inoutarg2486 = new com.pullenti.n2j.Outargwrapper< >();
                    Boolean inoutres2487 = com.pullenti.n2j.Utils.tryGetValue(ew.nexts, pref, inoutarg2486);
                    cas = inoutarg2486.value;
                    if (!inoutres2487) 
                        continue;
                    if (pref.length() > 0) 
                        return 3;
                    if (next.isPredicateInfinitive()) {
                        if (cas.isGenitive()) 
                            return 2;
                    }
                    else {
                        if (!((com.pullenti.morph.MorphCase.ooBitand(cas, next.getMorph().getCase()))).isUndefined()) 
                            return 2;
                        if (next.ref != null && com.pullenti.n2j.Utils.stringsEq(next.ref.getTypeName(), "PERSON") && ew.attrs.isAnimated()) 
                            return 1;
                    }
                }
            }
        }
        if (next.typ == Types.NUMBER) 
            return 0;
        if (next.preposition == null && prev.typ == Types.OBJ) {
            if (next.ref != null && com.pullenti.n2j.Utils.stringsEq(next.ref.getTypeName(), "ORGANIZATION")) {
            }
            if (((next.getMorph().getCase().isGenitive() && (next.getMorph().getCase().getCount() < 4))) || next.isPredicateInfinitive()) {
                if (ews.size() == 0) 
                    return 2;
                return 1;
            }
            if (next.getMorph().getCase().isUndefined() || next.getMorph().getCase().getCount() > 3) {
                if (next.canBeProperName() && prev.canHasProperName()) {
                    if (next.ref != null && com.pullenti.n2j.Utils.stringsEq(next.ref.getTypeName(), "ORGANIZATION")) {
                    }
                    else 
                        return -1;
                }
                if (prev.preposition != null) 
                    return -1;
                return 1;
            }
            if (next.ref != null && prev.ref == null) {
                if (next.ref != null && com.pullenti.n2j.Utils.stringsEq(next.ref.getTypeName(), "ORGANIZATION")) 
                    return 1;
            }
        }
        return 0;
    }

    private static void _correctAfter(SynToken s, int lev) {
        if (lev > 30) 
            return;
        if (s.tag == s) 
            return;
        s.tag = s;
        s.manageNpt();
        for(SynToken ch : s.children) {
            _correctAfter(ch, lev + 1);
        }
    }

    public static int _calcMorphAccordCoef(SynToken s1, SynToken s2, boolean ignoreGender) {
        int res = _calcMorphAccordCoef(s1.getMorph(), s2.getMorph(), ignoreGender);
        if (res == 0) {
            if (s1.preposition != null && s1.getBeginToken().isValue("МЕЖДУ", "МІЖ")) {
                if (s2.getBeginToken().getPrevious() != null && s2.getBeginToken().getPrevious().isCommaAnd()) 
                    return res + 1;
            }
            return 0;
        }
        String t1 = s1.getBase();
        String t2 = s2.getBase();
        if (t1 != null && t2 != null) {
            if (com.pullenti.n2j.Utils.stringsEq(t1, t2)) 
                res += 2;
            else {
                int i;
                for(i = 0; (i < t1.length()) && (i < t2.length()); i++) {
                    if (t1.charAt(t1.length() - 1 - i) != t2.charAt(t2.length() - 1 - i)) 
                        break;
                }
                if (i > 1) 
                    res += (i / 2);
            }
        }
        return res;
    }

    public static int _calcMorphAccordCoef(com.pullenti.ner.MorphCollection m1, com.pullenti.ner.MorphCollection m2, boolean ignoreGender) {
        int res = 0;
        for(com.pullenti.morph.MorphBaseInfo i1 : m1.getItems()) {
            if (i1.getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
                for(com.pullenti.morph.MorphBaseInfo i2 : m2.getItems()) {
                    if (i2.getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
                        int val = 0;
                        if (!((com.pullenti.morph.MorphCase.ooBitand(i1.getCase(), i2.getCase()))).isUndefined()) 
                            val++;
                        if (!ignoreGender && (((i1.getGender().value()) & (i2.getGender().value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                            val++;
                        if (val > res) 
                            res = val;
                    }
                }
            }
        }
        if (res > 0) 
            return res;
        for(com.pullenti.morph.MorphBaseInfo i1 : m1.getItems()) {
            if (i1.getNumber() != com.pullenti.morph.MorphNumber.SINGULAR) {
                for(com.pullenti.morph.MorphBaseInfo i2 : m2.getItems()) {
                    if (i2.getNumber() != com.pullenti.morph.MorphNumber.SINGULAR) {
                        int val = 0;
                        if (!((com.pullenti.morph.MorphCase.ooBitand(i1.getCase(), i2.getCase()))).isUndefined()) 
                            val++;
                        if (val > res) 
                            res = val;
                    }
                }
            }
        }
        return res;
    }

    private static boolean _ruleProperNames(java.util.ArrayList<SynToken> li, History hist) {
        boolean ch = false;
        for(int i = 0; i < (li.size() - 1); i++) {
            if (li.get(i).typ == Types.PROPERNAME && li.get(i + 1).typ == Types.PROPERNAME) {
                li.get(i).setEndToken(li.get(i + 1).getEndToken());
                li.get(i).mergeValsBySpace(li.get(i + 1).vals);
                li.remove(i + 1);
                i--;
                ch = true;
            }
        }
        for(int i = 0; i < (li.size() - 3); i++) {
            if (((li.get(i).typ == Types.OBJ && li.get(i + 1).typ == Types.COMMA && li.get(i + 2).typ == Types.ACTPRICH) && ((com.pullenti.n2j.Utils.stringsEq(li.get(i + 2).getBase(), "ПОЛУЧИТЬ") || com.pullenti.n2j.Utils.stringsEq(li.get(i + 2).getBase(), "ОТРИМАТИ"))) && ((li.get(i + 3).typ2 == Types.PROPERNAME || li.get(i + 3).typ == Types.PROPERNAME))) && li.get(i + 3).preposition == null) {
                li.get(i + 3).setBeginToken(li.get(i + 1).getBeginToken());
                for(int indRemoveRange = i + 1 + 2 - 1, indMinIndex = i + 1; indRemoveRange >= indMinIndex; indRemoveRange--) li.remove(indRemoveRange);
                ch = true;
            }
        }
        for(int i = 0; i < (li.size() - 2); i++) {
            if ((li.get(i).typ == Types.OBJ && li.get(i + 1).typ == Types.BRACKETOPEN && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(li.get(i + 1).getBeginToken(), true, false)) && li.get(i).canHasProperName()) {
                int j;
                boolean ok = false;
                java.util.ArrayList<SynToken> tmp = new java.util.ArrayList< >();
                for(j = i + 2; j < li.size(); j++) {
                    if (li.get(j).typ == Types.BRACKETCLOSE && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(li.get(j).getEndToken(), true, li.get(i + 1).getBeginToken(), false)) {
                        ok = true;
                        break;
                    }
                    else 
                        tmp.add(li.get(j));
                }
                if (!ok || (tmp.size() < 2)) 
                    continue;
                SynHelper.analyzeSent(tmp, hist, true);
                SynToken st = SynToken._new2488(li.get(i).getBeginToken(), li.get(j).getEndToken(), Types.OBJ, Types.PROPERNAME, li.get(i).getMorph());
                String nam = com.pullenti.ner.core.MiscHelper.getTextValue(li.get(i + 1).getBeginToken(), li.get(j).getEndToken(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
                st.addVal(nam, ValTypes.NAME);
                String nam2 = com.pullenti.ner.core.MiscHelper.getTextValue(li.get(i + 1).getBeginToken(), li.get(j).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                if (com.pullenti.n2j.Utils.stringsNe(nam2, nam)) 
                    st.addVal(nam2, ValTypes.NAME);
                st.addChild(li.get(i));
                for(SynToken tt : tmp) {
                    if (tt.typ == Types.OBJ || tt.typ == Types.NUMBER) 
                        st.addChild(tt);
                }
                for(int indRemoveRange = i + 1 + j - i - 1, indMinIndex = i + 1; indRemoveRange >= indMinIndex; indRemoveRange--) li.remove(indRemoveRange);
                com.pullenti.n2j.Utils.putArrayValue(li, i, st);
                ch = true;
            }
        }
        for(int i = 0; i < (li.size() - 1); i++) {
            if ((li.get(i).typ == Types.OBJ && li.get(i + 1).canBeProperName() && li.get(i + 1).ref == null) && li.get(i + 1).children.size() == 0 && li.get(i + 1).preposition == null) {
                SynToken sy0 = null;
                sy0 = li.get(i).getAbbrObj(li.get(i + 1));
                if (sy0 != null) {
                    sy0.setEndToken(li.get(i + 1).getEndToken());
                    sy0.typ2 = Types.PROPERNAME;
                    sy0.typ = Types.OBJ;
                    sy0.addVal(li.get(i + 1).getVal(ValTypes.NAME), ValTypes.ALIAS);
                    li.remove(i + 1);
                    ch = true;
                    continue;
                }
                sy0 = li.get(i).getNamedObj();
                if (sy0 == null) 
                    continue;
                if (sy0 != li.get(i)) {
                    if (!li.get(i).embedNamed(sy0, li.get(i + 1))) 
                        continue;
                    li.get(i + 1).typ = Types.PROPERNAME;
                    li.get(i + 1).typ2 = Types.PROPERNAME;
                    li.get(i + 1).setBeginToken(sy0.getBeginToken());
                    li.get(i).setEndToken(li.get(i + 1).getEndToken());
                    li.get(i).typ = Types.OBJ;
                    li.get(i).typ2 = Types.PROPERNAME;
                    li.remove(i + 1);
                    ch = true;
                    continue;
                }
                if (li.get(i + 1).canHasProperName()) {
                    li.get(i + 1).typ = Types.OBJ;
                    li.get(i + 1).typ2 = Types.UNDEFINED;
                    continue;
                }
                sy0.typ = Types.OBJ;
                sy0.typ2 = Types.UNDEFINED;
                li.get(i + 1).embed(sy0, false, 0);
                li.get(i + 1).setBeginToken(sy0.getBeginToken());
                li.get(i + 1).typ = Types.OBJ;
                li.get(i + 1).typ2 = Types.PROPERNAME;
                li.get(i + 1).preposition = sy0.preposition;
                li.remove(i);
                li.get(i).setMorph(sy0.getMorph());
                java.util.ArrayList<SynToken> add = null;
                int j = i;
                while((j + 2) < li.size()) {
                    if (!li.get(j + 1).isConjOrComma()) 
                        break;
                    if (li.get(j + 2).canBeProperName()) {
                        li.get(j + 2).embed(sy0, false, 0);
                        li.get(j + 2).typ = Types.OBJ;
                        li.get(j + 2).typ2 = Types.PROPERNAME;
                        li.get(j + 2).setMorph(sy0.getMorph());
                        li.remove(j + 1);
                        if (add == null) 
                            add = new java.util.ArrayList< >();
                        add.add(li.get(j + 1));
                        j++;
                        continue;
                    }
                    if (li.get(j + 2).typ == Types.BRACKETOPEN) {
                    }
                    break;
                }
                if (add != null) {
                    SynToken cnt = SynToken._new2480(li.get(i).getBeginToken(), add.get(add.size() - 1).getEndToken(), Types.OBJ);
                    cnt.addChild(li.get(i));
                    for(SynToken a : add) {
                        cnt.addChild(a);
                    }
                    cnt.getMorph().setNumber(com.pullenti.morph.MorphNumber.PLURAL);
                    com.pullenti.n2j.Utils.putArrayValue(li, i, cnt);
                }
                ch = true;
                i--;
            }
            else if (((((i + 3) < li.size()) && li.get(i).typ == Types.OBJ && li.get(i + 2).canBeProperName()) && li.get(i + 2).children.size() == 0 && li.get(i + 1).getBeginToken().isChar('(')) && li.get(i + 3).getEndToken().isChar(')')) {
                SynToken sy0 = null;
                sy0 = li.get(i).getAbbrObj(li.get(i + 2));
                if (sy0 != null) {
                    sy0.setEndToken(li.get(i + 3).getEndToken());
                    sy0.addVal(li.get(i + 2).getVal(ValTypes.NAME), ValTypes.ALIAS);
                    sy0.typ2 = Types.PROPERNAME;
                    li.remove(i + 1);
                    li.remove(i + 1);
                    li.remove(i + 1);
                    ch = true;
                }
            }
            else if (((((i + 2) < li.size()) && li.get(i).canBeProperName() && li.get(i + 1).typ == Types.UNDEFINED) && li.get(i + 1).getBeginToken().isHiphen() && li.get(i + 2).typ == Types.OBJ) && !li.get(i + 2).canBeProperName()) {
                if (li.get(i + 2).getNamedObj() == li.get(i + 2)) {
                    li.get(i).embed(li.get(i + 2), false, 0);
                    li.get(i).setEndToken(li.get(i + 2).getEndToken());
                    li.get(i).typ = Types.OBJ;
                    li.get(i).typ2 = Types.PROPERNAME;
                    li.get(i).setMorph(li.get(i + 2).getMorph());
                    li.remove(i + 1);
                    li.remove(i + 1);
                    ch = true;
                }
            }
        }
        for(int i = 0; i < (li.size() - 3); i++) {
            if ((li.get(i).typ == Types.OBJ && li.get(i).typ2 == Types.PROPERNAME && li.get(i + 1).typ == Types.UNDEFINED) && li.get(i + 1).isChar('(')) {
                int j = i + 2;
                boolean ok = false;
                boolean br = false;
                if (li.get(j).typ == Types.BRACKETOPEN) {
                    j++;
                    br = true;
                }
                java.util.ArrayList<SynToken> tmp = new java.util.ArrayList< >();
                for(; j < li.size(); j++) {
                    if (li.get(j).isChar(')')) {
                        ok = true;
                        break;
                    }
                    else 
                        tmp.add(li.get(j));
                }
                if (!ok || (((tmp.size() < 2) && !br))) 
                    continue;
                SynHelper.analyzeSent(tmp, hist, true);
                SynToken st = li.get(i);
                st.setEndToken(li.get(j).getEndToken());
                String nam = com.pullenti.ner.core.MiscHelper.getTextValue(li.get(i + 1).getBeginToken(), li.get(j).getEndToken(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
                st.addVal(nam, ValTypes.NAME);
                String nam2 = com.pullenti.ner.core.MiscHelper.getTextValue(li.get(i + 1).getBeginToken(), li.get(j).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                if (com.pullenti.n2j.Utils.stringsNe(nam2, nam)) 
                    st.addVal(nam2, ValTypes.NAME);
                for(int indRemoveRange = i + 1 + j - i - 1, indMinIndex = i + 1; indRemoveRange >= indMinIndex; indRemoveRange--) li.remove(indRemoveRange);
                com.pullenti.n2j.Utils.putArrayValue(li, i, st);
                ch = true;
            }
        }
        for(int i = 0; i < (li.size() - 2); i++) {
            if ((li.get(i).typ == Types.PROPERNAME && li.get(i + 1).typ == Types.CONJ && li.get(i).children.size() == 0) && li.get(i + 2).typ == Types.OBJ && li.get(i + 2).anaforRef0 != null) {
                if (li.get(i + 2).anaforRef0.isValue("ДРУГОЙ", "ІНШИЙ") || li.get(i + 2).anaforRef0.isValue("ОСТАЛЬНОЙ", "РЕШТІ")) {
                }
                else 
                    continue;
                SynToken sy0 = li.get(i + 2);
                li.get(i).embed(sy0, false, 0);
                for(int j = i - 2; j >= 0; j -= 2) {
                    if (li.get(j).typ != Types.PROPERNAME || li.get(j + 1).typ != Types.COMMA) 
                        break;
                    if (li.get(j).children.size() == 0) 
                        li.get(j).embed(sy0, false, 0);
                }
                ch = true;
            }
        }
        return ch;
    }

    private static boolean _ruleBracket(java.util.ArrayList<SynToken> li) {
        boolean ch = false;
        for(int i = 0; i < (li.size() - 2); i++) {
            if (li.get(i).typ == Types.BRACKETOPEN && li.get(i + 1).typ == Types.OBJ) {
                int j;
                for(j = i + 1; j < li.size(); j++) {
                    if (li.get(j).typ != Types.OBJ && !li.get(j).isConjOrComma()) 
                        break;
                }
                if (j >= li.size() || li.get(j).typ != Types.BRACKETCLOSE) 
                    continue;
                if (i > 0 && li.get(i - 1).typ == Types.OBJ) {
                    li.get(i - 1).setEndToken(li.get(j).getEndToken());
                    for(int kk = i + 1; kk < j; kk++) {
                        if (li.get(kk).typ == Types.OBJ) 
                            li.get(i - 1).embed(li.get(kk), false, 0);
                    }
                    for(int indRemoveRange = i + (j - i) + 1 - 1, indMinIndex = i; indRemoveRange >= indMinIndex; indRemoveRange--) li.remove(indRemoveRange);
                    ch = true;
                    continue;
                }
                if (li.get(i + 1).getBeginToken().chars.isAllLower()) {
                    li.remove(j);
                    li.remove(i);
                    i--;
                    ch = true;
                    continue;
                }
                SynToken sy = new SynToken(li.get(i).getBeginToken(), li.get(j).getEndToken());
                for(int kk = i + 1; kk < j; kk++) {
                    if (li.get(kk).typ == Types.OBJ) 
                        sy.embed(li.get(kk), false, 0);
                }
                for(int indRemoveRange = i + (j - i) + 1 - 1, indMinIndex = i; indRemoveRange >= indMinIndex; indRemoveRange--) li.remove(indRemoveRange);
                ch = true;
                if (sy.children.size() == 1) {
                    sy = sy.getFirstChild();
                    if (!sy.chars.isAllLower() && sy.typ2 == Types.UNDEFINED) 
                        sy.typ2 = Types.PROPERNAME;
                }
                li.add(i, sy);
            }
        }
        return ch;
    }
    public ObjectHelper() {
    }
}
