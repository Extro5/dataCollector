/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic.internal;

public class PredicateHelper {

    public static void processPredicates(java.util.ArrayList<SynToken> li) {
        _clearAdverbs(li);
        _prepareVerbs(li);
        _correctObjs(li);
        java.util.ArrayList<Fragment> ff = Fragment.createFragraph(li);
        if (ff == null || (ff.size() < 1)) 
            return;
        for(Fragment f : ff) {
            f.doActants(null);
        }
        for(int i = 0; i < (li.size() - 1); i++) {
            if ((li.get(i).typ == Types.ACT && li.get(i + 1).typ == Types.ACT && li.get(i).isPredicateBe()) && ((li.get(i + 1).isPredicateInfinitive() || !li.get(i + 1).isPredicateBe()))) {
                li.get(i).setEndToken(li.get(i + 1).getEndToken());
                li.get(i).delVals(ValTypes.BASE);
                li.get(i).addVals(li.get(i + 1));
                li.get(i).secMorph = li.get(i + 1).getMorph();
                for(int j = 0; j < li.get(i).children.size(); j++) {
                    SynToken ch = li.get(i).children.get(j);
                    if (ch.children.contains(li.get(i + 1))) {
                        li.get(i).children.remove(j);
                        j--;
                        continue;
                    }
                    if (ch.typ == Types.ACTANT) {
                        if (li.get(i + 1).getMorph().getVoice() == com.pullenti.morph.MorphVoice.PASSIVE) {
                            if (ch.rol == com.pullenti.ner.semantic.ActantRole.AGENT) 
                                ch.rol = com.pullenti.ner.semantic.ActantRole.PATIENT;
                            else if (ch.rol == com.pullenti.ner.semantic.ActantRole.PATIENT) 
                                ch.rol = com.pullenti.ner.semantic.ActantRole.AGENT;
                        }
                    }
                }
                for(SynToken ch : li.get(i + 1).children) {
                    if (li.get(i).children.contains(ch)) 
                        continue;
                    boolean has = false;
                    if (ch.typ == Types.ACTANT && ch.children.size() > 0) {
                        for(SynToken chh : li.get(i).children) {
                            if (chh.typ == Types.ACTANT && chh.children.contains(ch.children.get(0))) {
                                has = true;
                                break;
                            }
                        }
                    }
                    if (!has) 
                        li.get(i).addChild(ch);
                }
                li.get(i + 1)._UseThisToken = li.get(i);
                li.remove(i + 1);
                i--;
            }
        }
    }

    public static void _clearAdverbs(java.util.ArrayList<SynToken> li) {
        for(int i = 0; i < li.size(); i++) {
            if ((li.get(i).typ == Types.OBJ && li.get(i).getBeginToken() == li.get(i).getEndToken() && li.get(i).getBeginToken().getMorphClassInDictionary().isAdjective()) && li.get(i).children.size() == 0) {
                int co = 0;
                if (i > 0 && li.get(i - 1).isActant()) 
                    co = ObjectHelper._calcMorphAccordCoef(li.get(i - 1), li.get(i), false);
                else if (i > 1 && li.get(i - 1).typ == Types.COMMA && li.get(i - 2).isActant()) 
                    co = ObjectHelper._calcMorphAccordCoef(li.get(i - 2), li.get(i), false);
                if (co < 1) 
                    continue;
                boolean ok = false;
                for(int j = i + 1; j < li.size(); j++) {
                    SynToken lj = li.get(j);
                    if (lj.typ == Types.ADVERB || lj.typ == Types.WHAT) 
                        continue;
                    if (lj.isConjOrComma() && ((j + 1) < li.size()) && li.get(j + 1).typ == Types.WHAT) 
                        continue;
                    if (lj.isPredicateInfinitive()) {
                        ok = true;
                        break;
                    }
                    if (lj.isPredicate()) 
                        break;
                    if (lj.isActant()) {
                        ok = true;
                        break;
                    }
                }
                if (ok) {
                    li.get(i).typ = Types.ACTPRICH;
                    String norm = li.get(i).getBeginToken().getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, true, com.pullenti.morph.MorphGender.MASCULINE, false);
                    if (com.pullenti.morph.LanguageHelper.endsWithEx(norm, "ЫЙ", "ИЙ", null, null)) 
                        norm = norm.substring(0, 0+(norm.length() - 2)) + "О";
                    li.get(i).addVal(norm, ValTypes.PROP);
                    li.get(i).setBase((li.get(i).kit.baseLanguage.isUa() ? "БУЛИ" : "БЫТЬ"));
                    li.get(i).setExplainInfo(new java.util.ArrayList< >());
                    if (((i + 1) < li.size()) && li.get(i + 1).typ == Types.WHAT) {
                        li.get(i).setEndToken(li.get(i + 1).getEndToken());
                        li.remove(i + 1);
                    }
                    else if (((i + 2) < li.size()) && li.get(i + 1).isConjOrComma() && li.get(i + 2).typ == Types.WHAT) {
                        li.get(i).setEndToken(li.get(i + 2).getEndToken());
                        if (li.get(i + 1).getBeginToken() == li.get(i + 1).getEndToken() && li.get(i + 1).getEndToken().getMorphClassInDictionary().isAdverb()) 
                            li.get(i).addVal(li.get(i + 1).getEndToken().getNormalCaseText(new com.pullenti.morph.MorphClass(null), false, com.pullenti.morph.MorphGender.UNDEFINED, false), ValTypes.PROP);
                        li.remove(i + 1);
                        li.remove(i + 1);
                    }
                }
            }
        }
        for(int i = 0; i < li.size(); i++) {
            if (li.get(i).typ == Types.ADVERB) {
                int beforeCoef = 0;
                SynToken before = null;
                for(int j = i - 1; j >= 0; j--) {
                    if (li.get(j).isPredicate()) {
                        before = li.get(j);
                        break;
                    }
                    else if (li.get(j).isConjOrComma()) {
                        if (j > 0 && li.get(j - 1).typ == Types.ADVERB) 
                            beforeCoef++;
                        else 
                            beforeCoef += 5;
                    }
                    else if (li.get(j).typ == Types.WHAT) 
                        beforeCoef += 10;
                    else if (li.get(j).typ != Types.ADVERB) 
                        break;
                }
                int afterCoef = 0;
                SynToken after = null;
                for(int j = i + 1; j < li.size(); j++) {
                    if (li.get(j).isPredicate() || ((li.get(j).isActant() && j == (i + 1)))) {
                        after = li.get(j);
                        break;
                    }
                    else if (li.get(j).isConjOrComma()) {
                        if (((j + 1) < li.size()) && li.get(j + 1).typ == Types.ADVERB) 
                            afterCoef++;
                        else 
                            afterCoef += 5;
                    }
                    else if (li.get(j).typ == Types.WHAT) 
                        afterCoef += 10;
                    else if (li.get(j).typ != Types.ADVERB) 
                        break;
                }
                if (beforeCoef > 5) 
                    before = null;
                if (afterCoef > 5) 
                    after = null;
                if (before != null && after != null) {
                    if (beforeCoef < afterCoef) 
                        after = null;
                    else if (beforeCoef == afterCoef && ((com.pullenti.n2j.Utils.stringsEq(li.get(i).getBase(), "БЫ") || com.pullenti.n2j.Utils.stringsEq(li.get(i).getBase(), "Б")))) 
                        after = null;
                    else 
                        before = null;
                }
                if (after == null && before == null) 
                    continue;
                String nam = li.get(i).getBase();
                if (before != null) {
                    before.addVal(nam, ValTypes.PROP);
                    before.setEndToken(li.get(i).getEndToken());
                    li.remove(i);
                    i--;
                    while(i >= 0 && li.get(i) != before && li.get(i).isConjOrComma()) {
                        li.remove(i);
                        i--;
                    }
                }
                else {
                    if (after.beginChar > li.get(i).beginChar) 
                        after.setBeginToken(li.get(i).getBeginToken());
                    if (after.isActant()) {
                        after.addVal(nam, ValTypes.ACTANTPROP);
                        li.remove(i);
                        i--;
                    }
                    else {
                        after.addVal(nam, ValTypes.PROP);
                        li.remove(i);
                        i--;
                        while(((i + 1) < li.size()) && li.get(i + 1) != after && li.get(i + 1).isConjOrComma()) {
                            li.remove(i + 1);
                        }
                    }
                }
            }
        }
    }

    private static void _prepareVerbs(java.util.ArrayList<SynToken> li) {
        for(int i = 0; i < (li.size() - 1); i++) {
            if ((li.get(i).typ == Types.ACT && li.get(i + 1).typ == Types.ACT && li.get(i).isPredicateBe()) && com.pullenti.n2j.Utils.stringsEq(li.get(i + 1).getBase(), li.get(i).getBase())) {
                li.get(i).setEndToken(li.get(i + 1).getEndToken());
                li.get(i).addVals(li.get(i + 1));
                li.get(i).not ^= li.get(i + 1).not;
                li.remove(i + 1);
                i--;
            }
        }
    }

    private static void _correctObjs(java.util.ArrayList<SynToken> li) {
        for(SynToken o : li) {
            if ((o.typ == Types.OBJ && o.getBeginToken() == o.getEndToken() && o.getBeginToken().getMorphClassInDictionary().isUndefined()) && o.getMorph().getItemsCount() > 1) {
                for(int ii = o.getMorph().getItemsCount() - 1; ii > 0; ii--) {
                    o.getMorph().removeItem(ii);
                }
            }
        }
    }
    public PredicateHelper() {
    }
}
