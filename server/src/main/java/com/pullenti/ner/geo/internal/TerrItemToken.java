/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.geo.internal;

public class TerrItemToken extends com.pullenti.ner.MetaToken {

    public TerrItemToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    /**
     * Ссылка на существующий объект
     */
    public com.pullenti.ner.core.IntOntologyItem ontoItem;

    /**
     * Это термин для существительного и прилагательного
     */
    public TerrTermin terminItem;

    /**
     * Прилагательное (существующих объектов, для терминов или для собственного имени)
     */
    public boolean isAdjective;

    public boolean isDistrictName;

    /**
     * Это ссылка на страну для "китайская провинция"
     */
    public com.pullenti.ner.ReferentToken adjectiveRef;

    /**
     * Ссылка на организацию-РЖД
     */
    public com.pullenti.ner.ReferentToken rzd;

    public String rzdDir;

    /**
     * Это если есть такой же город
     */
    public boolean canBeCity;

    public boolean canBeSurname;

    /**
     * Прилагательное находится в словаре
     */
    public boolean isAdjInDictionary;

    public boolean isGeoInDictionary;

    /**
     * Сомнительность...
     */
    public boolean isDoubt;

    public boolean isCityRegion() {
        if (terminItem == null) 
            return false;
        return ((terminItem.getCanonicText().indexOf("ГОРОДС") >= 0) || (terminItem.getCanonicText().indexOf("МІСЬК") >= 0) || (terminItem.getCanonicText().indexOf("МУНИЦИПАЛ") >= 0)) || (terminItem.getCanonicText().indexOf("МУНІЦИПАЛ") >= 0);
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (ontoItem != null) 
            res.append(ontoItem.getCanonicText()).append(" ");
        else if (terminItem != null) 
            res.append(terminItem.getCanonicText()).append(" ");
        else 
            res.append(super.toString()).append(" ");
        if (adjectiveRef != null) 
            res.append(" (Adj: ").append(adjectiveRef.referent.toString()).append(")");
        return res.toString().trim();
    }

    public static java.util.ArrayList<TerrItemToken> tryParseList(com.pullenti.ner.Token t, com.pullenti.ner.core.IntOntologyCollection intOnt, int maxCount) {
        TerrItemToken ci = TerrItemToken.tryParse(t, intOnt, false, false);
        if (ci == null) 
            return null;
        java.util.ArrayList<TerrItemToken> li = new java.util.ArrayList< >();
        li.add(ci);
        t = ci.getEndToken().getNext();
        if (t == null) 
            return li;
        if (ci.terminItem != null && com.pullenti.n2j.Utils.stringsEq(ci.terminItem.getCanonicText(), "АВТОНОМИЯ")) {
            if (t.getMorph().getCase().isGenitive()) 
                return null;
        }
        for(t = ci.getEndToken().getNext(); t != null; ) {
            ci = TerrItemToken.tryParse(t, intOnt, false, false);
            if (ci == null) {
                if (t.chars.isCapitalUpper() && li.size() == 1 && ((li.get(0).isCityRegion() || ((li.get(0).terminItem != null && li.get(0).terminItem.isSpecificPrefix))))) {
                    CityItemToken cit = CityItemToken.tryParse(t, intOnt, false, null);
                    if (cit != null && cit.typ == CityItemToken.ItemType.PROPERNAME) 
                        ci = new TerrItemToken(cit.getBeginToken(), cit.getEndToken());
                }
                if (t.isChar('(')) {
                    ci = TerrItemToken.tryParse(t.getNext(), intOnt, false, false);
                    if (ci != null && ci.getEndToken().getNext() != null && ci.getEndToken().getNext().isChar(')')) {
                        TerrItemToken ci0 = li.get(li.size() - 1);
                        if (ci0.ontoItem != null && ci.ontoItem == ci0.ontoItem) 
                            ci0.setEndToken(ci.getEndToken().getNext());
                        else {
                            li.add(ci);
                            ci.setEndToken(ci.getEndToken().getNext());
                        }
                        t = ci.getEndToken().getNext();
                        continue;
                    }
                }
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, false, false)) {
                    java.util.ArrayList<TerrItemToken> lii = tryParseList(t.getNext(), intOnt, maxCount);
                    if (lii != null && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(lii.get(lii.size() - 1).getEndToken().getNext(), false, null, false)) {
                        li.addAll(lii);
                        return li;
                    }
                }
                if (li.get(li.size() - 1).rzd != null) 
                    ci = _tryParseRzdDir(t);
                if (ci == null) 
                    break;
            }
            if (ci.isAdjective && li.get(li.size() - 1).rzd != null) {
                TerrItemToken cii = _tryParseRzdDir(t);
                if (cii != null) 
                    ci = cii;
            }
            if (t.isTableControlChar()) 
                break;
            if (t.isNewlineBefore()) {
                if (li.size() > 0 && li.get(li.size() - 1).isAdjective && ci.terminItem != null) {
                }
                else 
                    break;
            }
            li.add(ci);
            t = ci.getEndToken().getNext();
            if (maxCount > 0 && li.size() >= maxCount) 
                break;
        }
        for(TerrItemToken cc : li) {
            if (cc.ontoItem != null && !cc.isAdjective) {
                if (!cc.getBeginToken().chars.isCyrillicLetter()) 
                    continue;
                String alpha2 = null;
                if (cc.ontoItem.referent instanceof com.pullenti.ner.geo.GeoReferent) 
                    alpha2 = (((com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(cc.ontoItem.referent, com.pullenti.ner.geo.GeoReferent.class))).getAlpha2();
                if (com.pullenti.n2j.Utils.stringsEq(alpha2, "TG")) {
                    if (cc.getBeginToken() instanceof com.pullenti.ner.TextToken) {
                        if (com.pullenti.n2j.Utils.stringsNe(cc.getBeginToken().getSourceText(), "Того")) 
                            return null;
                        if (li.size() == 1 && cc.getBeginToken().getPrevious() != null && cc.getBeginToken().getPrevious().isChar('.')) 
                            return null;
                        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(cc.getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.PARSEPRONOUNS, 0);
                        if (npt != null && npt.getEndToken() != cc.getBeginToken()) 
                            return null;
                        if (cc.getBeginToken().getNext() != null) {
                            if (cc.getBeginToken().getNext().getMorph()._getClass().isPersonalPronoun() || cc.getBeginToken().getNext().getMorph()._getClass().isPronoun()) 
                                return null;
                        }
                    }
                    if (li.size() < 2) 
                        return null;
                }
                if (com.pullenti.n2j.Utils.stringsEq(alpha2, "PE")) {
                    if (cc.getBeginToken() instanceof com.pullenti.ner.TextToken) {
                        if (com.pullenti.n2j.Utils.stringsNe((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(cc.getBeginToken(), com.pullenti.ner.TextToken.class))).getSourceText(), "Перу")) 
                            return null;
                        if (li.size() == 1 && cc.getBeginToken().getPrevious() != null && cc.getBeginToken().getPrevious().isChar('.')) 
                            return null;
                    }
                    if (li.size() < 2) 
                        return null;
                }
                if (com.pullenti.n2j.Utils.stringsEq(alpha2, "DM")) {
                    if (cc.getEndToken().getNext() != null) {
                        if (cc.getEndToken().getNext().chars.isCapitalUpper() || cc.getEndToken().getNext().chars.isAllUpper()) 
                            return null;
                    }
                    return null;
                }
                if (com.pullenti.n2j.Utils.stringsEq(alpha2, "JE")) {
                    if (cc.getBeginToken().getPrevious() != null && cc.getBeginToken().getPrevious().isHiphen()) 
                        return null;
                }
                return li;
            }
            else if (cc.ontoItem != null && cc.isAdjective) {
                String alpha2 = null;
                if (cc.ontoItem.referent instanceof com.pullenti.ner.geo.GeoReferent) 
                    alpha2 = (((com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(cc.ontoItem.referent, com.pullenti.ner.geo.GeoReferent.class))).getAlpha2();
                if (com.pullenti.n2j.Utils.stringsEq(alpha2, "SU")) {
                    if (cc.getEndToken().getNext() == null || !cc.getEndToken().getNext().isValue("СОЮЗ", null)) 
                        cc.ontoItem = null;
                }
            }
        }
        for(TerrItemToken cc : li) {
            if (cc.ontoItem != null || ((cc.terminItem != null && !cc.isAdjective)) || cc.rzd != null) 
                return li;
        }
        return null;
    }

    private static TerrItemToken _tryParseRzdDir(com.pullenti.ner.Token t) {
        com.pullenti.ner.Token napr = null;
        com.pullenti.ner.Token tt0 = null;
        com.pullenti.ner.Token tt1 = null;
        String val = null;
        for(com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            if (tt.isCharOf(",.")) 
                continue;
            if (tt.isNewlineBefore()) 
                break;
            if (tt.isValue("НАПРАВЛЕНИЕ", null)) {
                napr = tt;
                continue;
            }
            if (tt.isValue("НАПР", null)) {
                if (tt.getNext() != null && tt.getNext().isChar('.')) 
                    tt = tt.getNext();
                napr = tt;
                continue;
            }
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (npt != null && npt.adjectives.size() > 0 && npt.noun.isValue("КОЛЬЦО", null)) {
                tt0 = tt;
                tt1 = npt.getEndToken();
                val = npt.getNormalCaseText(new com.pullenti.morph.MorphClass(null), true, com.pullenti.morph.MorphGender.UNDEFINED, false);
                break;
            }
            if ((tt instanceof com.pullenti.ner.TextToken) && ((!tt.chars.isAllLower() || napr != null)) && (((tt.getMorph().getGender().value()) & (com.pullenti.morph.MorphGender.NEUTER.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                tt0 = (tt1 = tt);
                continue;
            }
            if ((((tt instanceof com.pullenti.ner.TextToken) && ((!tt.chars.isAllLower() || napr != null)) && tt.getNext() != null) && tt.getNext().isHiphen() && (tt.getNext().getNext() instanceof com.pullenti.ner.TextToken)) && (((tt.getNext().getNext().getMorph().getGender().value()) & (com.pullenti.morph.MorphGender.NEUTER.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                tt0 = tt;
                tt = tt.getNext().getNext();
                tt1 = tt;
                continue;
            }
            break;
        }
        if (tt0 != null) {
            TerrItemToken ci = _new1110(tt0, tt1, true);
            if (val != null) 
                ci.rzdDir = val;
            else {
                ci.rzdDir = tt1.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, true, com.pullenti.morph.MorphGender.NEUTER, false);
                if (tt0 != tt1) 
                    ci.rzdDir = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt0, com.pullenti.ner.TextToken.class))).term + " " + ci.rzdDir;
                ci.rzdDir += " НАПРАВЛЕНИЕ";
            }
            if (napr != null && napr.endChar > ci.endChar) 
                ci.setEndToken(napr);
            return ci;
        }
        return null;
    }

    public static TerrItemToken tryParse(com.pullenti.ner.Token t, com.pullenti.ner.core.IntOntologyCollection intOnt, boolean canBeLowCapital, boolean nounCanBeAdjective) {
        if (t == null) 
            return null;
        if (t.kit.isRecurceOverflow()) 
            return null;
        t.kit.recurseLevel++;
        TerrItemToken res = _TryParse(t, intOnt, canBeLowCapital);
        t.kit.recurseLevel--;
        if (res == null) {
            if (nounCanBeAdjective && t.getMorph()._getClass().isAdjective()) {
                com.pullenti.ner.core.TerminToken tok = m_TerrNounAdjectives.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok != null) 
                    return _new1111(tok.getBeginToken(), tok.getEndToken(), (TerrTermin)com.pullenti.n2j.Utils.cast(tok.termin.tag, TerrTermin.class), false);
            }
            if ((t.chars.isAllUpper() && t.getLengthChar() == 2 && (t instanceof com.pullenti.ner.TextToken)) && intOnt != null) {
                String term = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
                if (((com.pullenti.n2j.Utils.stringsEq(term, "РБ") || com.pullenti.n2j.Utils.stringsEq(term, "РК") || com.pullenti.n2j.Utils.stringsEq(term, "TC")) || com.pullenti.n2j.Utils.stringsEq(term, "ТС") || com.pullenti.n2j.Utils.stringsEq(term, "РТ")) || com.pullenti.n2j.Utils.stringsEq(term, "УР") || com.pullenti.n2j.Utils.stringsEq(term, "РД")) {
                    for(com.pullenti.ner.core.IntOntologyItem it : intOnt.getItems()) {
                        if (it.referent instanceof com.pullenti.ner.geo.GeoReferent) {
                            String alph2 = (((com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(it.referent, com.pullenti.ner.geo.GeoReferent.class))).getAlpha2();
                            if (((com.pullenti.n2j.Utils.stringsEq(alph2, "BY") && com.pullenti.n2j.Utils.stringsEq(term, "РБ"))) || ((com.pullenti.n2j.Utils.stringsEq(alph2, "KZ") && com.pullenti.n2j.Utils.stringsEq(term, "РК")))) 
                                return _new1112(t, t, it);
                            if (com.pullenti.n2j.Utils.stringsEq(term, "РТ")) {
                                if (it.referent.findSlot(null, "ТАТАРСТАН", true) != null) 
                                    return _new1112(t, t, it);
                            }
                            if (com.pullenti.n2j.Utils.stringsEq(term, "РД")) {
                                if (it.referent.findSlot(null, "ДАГЕСТАН", true) != null) 
                                    return _new1112(t, t, it);
                            }
                        }
                    }
                    boolean ok = false;
                    if ((t.getWhitespacesBeforeCount() < 2) && (t.getPrevious() instanceof com.pullenti.ner.TextToken)) {
                        String term2 = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t.getPrevious(), com.pullenti.ner.TextToken.class))).term;
                        if ((t.getPrevious().isValue("КОДЕКС", null) || t.getPrevious().isValue("ЗАКОН", null) || com.pullenti.n2j.Utils.stringsEq(term2, "КОАП")) || com.pullenti.n2j.Utils.stringsEq(term2, "ПДД") || com.pullenti.n2j.Utils.stringsEq(term2, "МЮ")) 
                            ok = true;
                        else if ((t.getPrevious().chars.isAllUpper() && t.getPrevious().getLengthChar() > 1 && (t.getPrevious().getLengthChar() < 4)) && term2.endsWith("К")) 
                            ok = true;
                        else if (com.pullenti.n2j.Utils.stringsEq(term, "РТ") || com.pullenti.n2j.Utils.stringsEq(term, "УР") || com.pullenti.n2j.Utils.stringsEq(term, "РД")) {
                            com.pullenti.ner.Token tt = t.getPrevious();
                            if (tt != null && tt.isComma()) 
                                tt = tt.getPrevious();
                            if (tt != null) {
                                if ((tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && com.pullenti.n2j.Utils.stringsEq((((com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(tt.getReferent(), com.pullenti.ner.geo.GeoReferent.class))).getAlpha2(), "RU")) 
                                    ok = true;
                                else if ((tt instanceof com.pullenti.ner.NumberToken) && tt.getLengthChar() == 6 && (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.NumberToken.class))).typ == com.pullenti.ner.NumberSpellingType.DIGIT) 
                                    ok = true;
                            }
                        }
                    }
                    else if (((t.getWhitespacesBeforeCount() < 2) && (t.getPrevious() instanceof com.pullenti.ner.NumberToken) && t.getPrevious().getLengthChar() == 6) && (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t.getPrevious(), com.pullenti.ner.NumberToken.class))).typ == com.pullenti.ner.NumberSpellingType.DIGIT) 
                        ok = true;
                    if (ok) {
                        if (com.pullenti.n2j.Utils.stringsEq(term, "РК") && m_Kazahstan != null) 
                            return _new1112(t, t, m_Kazahstan);
                        if (com.pullenti.n2j.Utils.stringsEq(term, "РТ") && m_Tatarstan != null) 
                            return _new1112(t, t, m_Tatarstan);
                        if (com.pullenti.n2j.Utils.stringsEq(term, "РД") && m_Dagestan != null) 
                            return _new1112(t, t, m_Dagestan);
                        if (com.pullenti.n2j.Utils.stringsEq(term, "УР") && m_Udmurtia != null) 
                            return _new1112(t, t, m_Udmurtia);
                        if (com.pullenti.n2j.Utils.stringsEq(term, "РБ") && m_Belorussia != null) 
                            return _new1112(t, t, m_Belorussia);
                        if (((com.pullenti.n2j.Utils.stringsEq(term, "ТС") || com.pullenti.n2j.Utils.stringsEq(term, "TC"))) && m_TamogSous != null) 
                            return _new1112(t, t, m_TamogSous);
                    }
                }
            }
            if (((t instanceof com.pullenti.ner.TextToken) && ((t.isValue("Р", null) || t.isValue("P", null))) && t.getNext() != null) && t.getNext().isChar('.') && !t.getNext().isNewlineAfter()) {
                res = tryParse(t.getNext().getNext(), intOnt, false, false);
                if (res != null && res.ontoItem != null) {
                    String str = res.ontoItem.toString().toUpperCase();
                    if ((str.indexOf("РЕСПУБЛИКА") >= 0)) {
                        res.setBeginToken(t);
                        res.isDoubt = false;
                        return res;
                    }
                }
            }
            if ((t instanceof com.pullenti.ner.TextToken) && t.getLengthChar() > 2 && !t.chars.isAllLower()) {
                if (((t.getMorph()._getClass().isAdjective() || t.chars.isAllUpper() || (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term.endsWith("ЖД"))) || ((t.getNext() != null && t.getNext().isHiphen()))) {
                    com.pullenti.ner.ReferentToken rt0 = t.kit.processReferent("ORGANIZATION", t);
                    if (rt0 != null) {
                        if ((((String)com.pullenti.n2j.Utils.notnull(rt0.referent.getStringValue("TYPE"), ""))).endsWith("дорога")) 
                            return _new1121(t, rt0.getEndToken(), rt0, rt0.getMorph());
                    }
                }
                TerrItemToken _rzdDir = _tryParseRzdDir(t);
                if (_rzdDir != null) {
                    com.pullenti.ner.Token tt = _rzdDir.getEndToken().getNext();
                    while(tt != null) {
                        if (tt.isCharOf(",.")) 
                            tt = tt.getNext();
                        else 
                            break;
                    }
                    TerrItemToken chhh = tryParse(tt, intOnt, false, false);
                    if (chhh != null && chhh.rzd != null) 
                        return _rzdDir;
                }
            }
            return tryParseDistrictName(t, intOnt);
        }
        if (res.isAdjective) {
            com.pullenti.ner.ReferentToken rt0 = t.kit.processReferent("ORGANIZATION", t);
            if (rt0 != null) {
                if ((((String)com.pullenti.n2j.Utils.notnull(rt0.referent.getStringValue("TYPE"), ""))).endsWith("дорога")) 
                    return _new1121(t, rt0.getEndToken(), rt0, rt0.getMorph());
            }
            TerrItemToken _rzdDir = _tryParseRzdDir(t);
            if (_rzdDir != null) {
                com.pullenti.ner.Token tt = _rzdDir.getEndToken().getNext();
                while(tt != null) {
                    if (tt.isCharOf(",.")) 
                        tt = tt.getNext();
                    else 
                        break;
                }
                rt0 = t.kit.processReferent("ORGANIZATION", tt);
                if (rt0 != null) {
                    if ((((String)com.pullenti.n2j.Utils.notnull(rt0.referent.getStringValue("TYPE"), ""))).endsWith("дорога")) 
                        return _rzdDir;
                }
            }
        }
        if ((res.getBeginToken().getLengthChar() == 1 && res.getBeginToken().chars.isAllUpper() && res.getBeginToken().getNext() != null) && res.getBeginToken().getNext().isChar('.')) 
            return null;
        if (res.terminItem != null && com.pullenti.n2j.Utils.stringsEq(res.terminItem.getCanonicText(), "ОКРУГ")) {
            if (t.getPrevious() != null && ((t.getPrevious().isValue("ГОРОДСКОЙ", null) || t.getPrevious().isValue("МІСЬКИЙ", null)))) 
                return null;
        }
        if (res.ontoItem != null) {
            CityItemToken cit = CityItemToken.tryParse(res.getBeginToken(), null, canBeLowCapital, null);
            if (cit != null) {
                if (cit.typ == CityItemToken.ItemType.CITY && cit.ontoItem != null && cit.ontoItem.miscAttr != null) {
                    if (cit.getEndToken().isValue("CITY", null)) 
                        return null;
                    if (cit.getEndToken() == res.getEndToken()) {
                        res.canBeCity = true;
                        if (cit.getEndToken().getNext() != null && cit.getEndToken().getNext().isValue("CITY", null)) 
                            return null;
                    }
                }
            }
            cit = CityItemToken.tryParseBack(res.getBeginToken().getPrevious());
            if (cit != null && cit.typ == CityItemToken.ItemType.NOUN && ((res.isAdjective || (cit.getWhitespacesAfterCount() < 1)))) 
                res.canBeCity = true;
        }
        if (res.terminItem != null) {
            res.isDoubt = res.terminItem.isDoubt;
            if (!res.terminItem.isRegion) {
                if (res.terminItem.isMoscowRegion && res.getBeginToken() == res.getEndToken()) 
                    res.isDoubt = true;
                else if (com.pullenti.n2j.Utils.stringsEq(res.terminItem.acronym, "МО") && res.getBeginToken() == res.getEndToken() && res.getLengthChar() == 2) {
                    if (res.getBeginToken().getPrevious() != null && res.getBeginToken().getPrevious().isValue("ВЕТЕРАН", null)) 
                        return null;
                    res.isDoubt = true;
                    if (res.getBeginToken() == res.getEndToken() && res.getLengthChar() == 2) {
                        if (res.getBeginToken().getPrevious() == null || res.getBeginToken().getPrevious().isCharOf(",") || res.getBeginToken().isNewlineBefore()) {
                            if (res.getEndToken().getNext() == null || res.getEndToken().getNext().isCharOf(",") || res.isNewlineAfter()) {
                                res.terminItem = null;
                                res.ontoItem = m_MosRegRU;
                            }
                        }
                    }
                }
                else if (com.pullenti.n2j.Utils.stringsEq(res.terminItem.acronym, "ЛО") && res.getBeginToken() == res.getEndToken() && res.getLengthChar() == 2) {
                    res.isDoubt = true;
                    if (res.getBeginToken().getPrevious() == null || res.getBeginToken().getPrevious().isCommaAnd() || res.getBeginToken().isNewlineBefore()) {
                        res.terminItem = null;
                        res.ontoItem = m_LenRegRU;
                    }
                }
                else if (!res.getMorph().getCase().isNominative() && !res.getMorph().getCase().isAccusative()) 
                    res.isDoubt = true;
                else if (res.getMorph().getNumber() != com.pullenti.morph.MorphNumber.SINGULAR) {
                    if (res.terminItem.isMoscowRegion && res.getMorph().getNumber() != com.pullenti.morph.MorphNumber.PLURAL) {
                    }
                    else 
                        res.isDoubt = true;
                }
            }
            if (((res.terminItem != null && com.pullenti.n2j.Utils.stringsEq(res.terminItem.getCanonicText(), "АО"))) || ((res.ontoItem == m_MosRegRU && res.getLengthChar() == 2))) {
                com.pullenti.ner.Token tt = res.getEndToken().getNext();
                com.pullenti.ner.ReferentToken rt = res.kit.processReferent("ORGANIZATION", res.getBeginToken());
                if (rt == null) 
                    rt = res.kit.processReferent("ORGANIZATION", res.getBeginToken().getNext());
                if (rt != null) {
                    for(com.pullenti.ner.Slot s : rt.referent.getSlots()) {
                        if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), "TYPE")) {
                            String ty = s.getValue().toString();
                            if (res.terminItem != null && com.pullenti.n2j.Utils.stringsNe(ty, res.terminItem.getCanonicText())) 
                                return null;
                        }
                    }
                }
            }
        }
        if (res != null && res.getBeginToken() == res.getEndToken() && res.terminItem == null) {
            if (t instanceof com.pullenti.ner.TextToken) {
                String str = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
                if (com.pullenti.n2j.Utils.stringsEq(str, "ЧАДОВ") || com.pullenti.n2j.Utils.stringsEq(str, "ТОГОВ")) 
                    return null;
            }
            if ((((t.getNext() instanceof com.pullenti.ner.TextToken) && (t.getWhitespacesAfterCount() < 2) && !t.getNext().chars.isAllLower()) && com.pullenti.morph.CharsInfo.ooEq(t.chars, t.getNext().chars) && !t.chars.isLatinLetter()) && ((!t.getMorph().getCase().isGenitive() && !t.getMorph().getCase().isAccusative()))) {
                com.pullenti.morph.MorphClass mc = t.getNext().getMorphClassInDictionary();
                if (mc.isProperSurname() || mc.isProperSecname()) 
                    res.isDoubt = true;
            }
            if ((t.getPrevious() instanceof com.pullenti.ner.TextToken) && (t.getWhitespacesBeforeCount() < 2) && !t.getPrevious().chars.isAllLower()) {
                com.pullenti.morph.MorphClass mc = t.getPrevious().getMorphClassInDictionary();
                if (mc.isProperSurname()) 
                    res.isDoubt = true;
            }
            if (t.getLengthChar() <= 2 && res.ontoItem != null && !t.isValue("РФ", null)) {
                res.isDoubt = true;
                com.pullenti.ner.Token tt = t.getNext();
                if (tt != null && ((tt.isCharOf(":") || tt.isHiphen()))) 
                    tt = tt.getNext();
                if (tt != null && tt.getReferent() != null && com.pullenti.n2j.Utils.stringsEq(tt.getReferent().getTypeName(), "PHONE")) 
                    res.isDoubt = false;
                else if (t.getLengthChar() == 2 && t.chars.isAllUpper() && t.chars.isLatinLetter()) 
                    res.isDoubt = false;
            }
        }
        return res;
    }

    private static TerrItemToken _TryParse(com.pullenti.ner.Token t, com.pullenti.ner.core.IntOntologyCollection intOnt, boolean canBeLowCapital) {
        if (!((t instanceof com.pullenti.ner.TextToken))) 
            return null;
        java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> li = null;
        if (intOnt != null) 
            li = intOnt.tryAttach(t, null, false);
        if (li == null && t.kit.ontology != null) 
            li = t.kit.ontology.attachToken(com.pullenti.ner.geo.GeoReferent.OBJ_TYPENAME, t);
        if (li == null) 
            li = m_TerrOntology.tryAttach(t, null, false);
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (li != null) {
            for(int i = li.size() - 1; i >= 0; i--) {
                if (li.get(i).item != null) {
                    com.pullenti.ner.geo.GeoReferent g = (com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(li.get(i).item.referent, com.pullenti.ner.geo.GeoReferent.class);
                    if (g == null) 
                        continue;
                    if (g.isCity() && !g.isRegion() && !g.isState()) 
                        li.remove(i);
                    else if (g.isState() && t.getLengthChar() == 2 && li.get(i).getLengthChar() == 2) {
                        if (!t.isWhitespaceBefore() && t.getPrevious() != null && t.getPrevious().isChar('.')) 
                            li.remove(i);
                        else if (t.getPrevious() != null && t.getPrevious().isValue("ДОМЕН", null)) 
                            li.remove(i);
                    }
                }
            }
            for(com.pullenti.ner.core.IntOntologyToken nt : li) {
                if (nt.item != null && !((nt.termin.tag instanceof com.pullenti.ner.core.IntOntologyItem))) {
                    if (canBeLowCapital || !com.pullenti.ner.core.MiscHelper.isAllCharactersLower(nt.getBeginToken(), nt.getEndToken(), false) || nt.getBeginToken() != nt.getEndToken()) {
                        TerrItemToken res0 = _new1123(nt.getBeginToken(), nt.getEndToken(), nt.item, nt.getMorph());
                        if (nt.getEndToken().getMorph()._getClass().isAdjective() && nt.getBeginToken() == nt.getEndToken()) {
                            if (nt.getBeginToken().getMorphClassInDictionary().isProperGeo()) {
                            }
                            else 
                                res0.isAdjective = true;
                        }
                        if (nt.getBeginToken() == nt.getEndToken() && nt.chars.isLatinLetter()) {
                            if ((((com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(nt.item.referent, com.pullenti.ner.geo.GeoReferent.class))).isState()) {
                            }
                            else if (nt.item.referent.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, "state", true) != null) {
                            }
                            else 
                                res0.isDoubt = true;
                        }
                        return res0;
                    }
                }
            }
            for(com.pullenti.ner.core.IntOntologyToken nt : li) {
                if (nt.item != null && (nt.termin.tag instanceof com.pullenti.ner.core.IntOntologyItem)) {
                    if (nt.getEndToken().getNext() == null || !nt.getEndToken().getNext().isHiphen()) 
                        return _new1124(nt.getBeginToken(), nt.getEndToken(), nt.item, true, nt.getMorph());
                }
            }
            for(com.pullenti.ner.core.IntOntologyToken nt : li) {
                if (nt.termin != null && nt.item == null) {
                    if (nt.getEndToken().getNext() == null || !nt.getEndToken().getNext().isHiphen() || !(((TerrTermin)com.pullenti.n2j.Utils.cast(nt.termin, TerrTermin.class))).isAdjective) {
                        TerrItemToken res1 = _new1125(nt.getBeginToken(), nt.getEndToken(), (TerrTermin)com.pullenti.n2j.Utils.cast(nt.termin, TerrTermin.class), (((TerrTermin)com.pullenti.n2j.Utils.cast(nt.termin, TerrTermin.class))).isAdjective, nt.getMorph());
                        if (!res1.isAdjective) {
                            if (com.pullenti.n2j.Utils.stringsEq(res1.terminItem.getCanonicText(), "РЕСПУБЛИКА") || com.pullenti.n2j.Utils.stringsEq(res1.terminItem.getCanonicText(), "ШТАТ")) {
                                com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(res1.getBeginToken().getPrevious(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                                if (npt1 != null && npt1.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) {
                                    TerrItemToken res2 = tryParse(res1.getEndToken().getNext(), intOnt, false, false);
                                    if ((res2 != null && res2.ontoItem != null && res2.ontoItem.referent != null) && res2.ontoItem.referent.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, "республика", true) != null) {
                                    }
                                    else 
                                        return null;
                                }
                            }
                            if (com.pullenti.n2j.Utils.stringsEq(res1.terminItem.getCanonicText(), "ГОСУДАРСТВО")) {
                                if (t.getPrevious() != null && t.getPrevious().isValue("СОЮЗНЫЙ", null)) 
                                    return null;
                            }
                        }
                        return res1;
                    }
                }
            }
        }
        if (tt == null) 
            return null;
        if (!tt.chars.isCapitalUpper() && !tt.chars.isAllUpper()) 
            return null;
        if (((tt.getLengthChar() == 2 || tt.getLengthChar() == 3)) && tt.chars.isAllUpper()) {
            if (m_Alpha2State.containsKey(tt.term)) {
                boolean ok = false;
                com.pullenti.ner.Token tt2 = tt.getNext();
                if (tt2 != null && tt2.isChar(':')) 
                    tt2 = tt2.getNext();
                if (tt2 instanceof com.pullenti.ner.ReferentToken) {
                    com.pullenti.ner.Referent r = tt2.getReferent();
                    if (r != null && com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), "PHONE")) 
                        ok = true;
                }
                if (ok) 
                    return _new1112(tt, tt, m_Alpha2State.get(tt.term));
            }
        }
        if (tt.getLengthChar() < 3) 
            return null;
        if (com.pullenti.ner.core.MiscHelper.isEngArticle(tt)) 
            return null;
        if (tt.getLengthChar() < 5) {
            if (tt.getNext() == null || !tt.getNext().isHiphen()) 
                return null;
        }
        com.pullenti.ner.TextToken t0 = tt;
        String prefix = null;
        if (t0.getNext() != null && t0.getNext().isHiphen() && (t0.getNext().getNext() instanceof com.pullenti.ner.TextToken)) {
            tt = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t0.getNext().getNext(), com.pullenti.ner.TextToken.class);
            if (!tt.chars.isAllLower() && ((t0.isWhitespaceAfter() || t0.getNext().isWhitespaceAfter()))) {
                TerrItemToken tit = _TryParse(tt, intOnt, false);
                if (tit != null) {
                    if (tit.ontoItem != null) 
                        return null;
                }
            }
            if (tt.getLengthChar() > 1) {
                if (tt.chars.isCapitalUpper()) 
                    prefix = t0.term;
                else if (!tt.isWhitespaceBefore() && !t0.isWhitespaceAfter()) 
                    prefix = t0.term;
                if (((!tt.isWhitespaceAfter() && tt.getNext() != null && tt.getNext().isHiphen()) && !tt.getNext().isWhitespaceAfter() && (tt.getNext().getNext() instanceof com.pullenti.ner.TextToken)) && com.pullenti.morph.CharsInfo.ooEq(tt.getNext().getNext().chars, t0.chars)) {
                    prefix = prefix + "-" + tt.term;
                    tt = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt.getNext().getNext(), com.pullenti.ner.TextToken.class);
                }
            }
            if (prefix == null) 
                tt = t0;
        }
        if (tt.getMorph()._getClass().isAdverb()) 
            return null;
        CityItemToken cit = CityItemToken.tryParse(t0, null, false, null);
        if (cit != null) {
            if (cit.ontoItem != null || cit.typ == CityItemToken.ItemType.NOUN || cit.typ == CityItemToken.ItemType.CITY) {
                if (!cit.doubtful && !tt.getMorph()._getClass().isAdjective()) 
                    return null;
            }
        }
        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t0, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
        if (npt != null) {
            if (((npt.noun.isValue("ФЕДЕРАЦИЯ", null) || npt.noun.isValue("ФЕДЕРАЦІЯ", null))) && npt.adjectives.size() == 1) {
                if (com.pullenti.ner.core.MiscHelper.isNotMoreThanOneError("РОССИЙСКАЯ", npt.adjectives.get(0)) || com.pullenti.ner.core.MiscHelper.isNotMoreThanOneError("РОСІЙСЬКА", npt.adjectives.get(0))) 
                    return _new1123(npt.getBeginToken(), npt.getEndToken(), (t0.kit.baseLanguage.isUa() ? m_RussiaUA : m_RussiaRU), npt.getMorph());
            }
        }
        if (t0.getMorph()._getClass().isProperName()) {
            if (t0.isWhitespaceAfter() || t0.getNext().isWhitespaceAfter()) 
                return null;
        }
        if (npt != null && npt.getEndToken() == tt.getNext()) {
            boolean adj = false;
            boolean regAfter = false;
            if (npt.adjectives.size() == 1 && !t0.chars.isAllLower()) {
                if (((((tt.getNext().isValue("РАЙОН", null) || tt.getNext().isValue("ОБЛАСТЬ", null) || tt.getNext().isValue("КРАЙ", null)) || tt.getNext().isValue("ВОЛОСТЬ", null) || tt.getNext().isValue("УЛУС", null)) || tt.getNext().isValue("ОКРУГ", null) || tt.getNext().isValue("АВТОНОМИЯ", "АВТОНОМІЯ")) || tt.getNext().isValue("РЕСПУБЛИКА", "РЕСПУБЛІКА") || tt.getNext().isValue("COUNTY", null)) || tt.getNext().isValue("STATE", null) || tt.getNext().isValue("REGION", null)) 
                    regAfter = true;
                else {
                    java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> tok = m_TerrOntology.tryAttach(tt.getNext(), null, false);
                    if (tok != null) {
                        if ((((com.pullenti.n2j.Utils.stringsEq(tok.get(0).termin.getCanonicText(), "РАЙОН") || com.pullenti.n2j.Utils.stringsEq(tok.get(0).termin.getCanonicText(), "ОБЛАСТЬ") || com.pullenti.n2j.Utils.stringsEq(tok.get(0).termin.getCanonicText(), "УЛУС")) || com.pullenti.n2j.Utils.stringsEq(tok.get(0).termin.getCanonicText(), "КРАЙ") || com.pullenti.n2j.Utils.stringsEq(tok.get(0).termin.getCanonicText(), "ВОЛОСТЬ")) || com.pullenti.n2j.Utils.stringsEq(tok.get(0).termin.getCanonicText(), "ОКРУГ") || com.pullenti.n2j.Utils.stringsEq(tok.get(0).termin.getCanonicText(), "АВТОНОМИЯ")) || com.pullenti.n2j.Utils.stringsEq(tok.get(0).termin.getCanonicText(), "АВТОНОМІЯ") || ((tok.get(0).chars.isLatinLetter() && (tok.get(0).termin instanceof TerrTermin) && (((TerrTermin)com.pullenti.n2j.Utils.cast(tok.get(0).termin, TerrTermin.class))).isRegion))) 
                            regAfter = true;
                    }
                }
            }
            if (regAfter) {
                adj = true;
                for(com.pullenti.morph.MorphBaseInfo wff : tt.getMorph().getItems()) {
                    com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(wff, com.pullenti.morph.MorphWordForm.class);
                    if (wf == null) 
                        continue;
                    if (wf._getClass().isVerb() && wf.isInDictionary()) {
                        adj = false;
                        break;
                    }
                    else if (wf.isInDictionary() && !wf._getClass().isAdjective()) {
                    }
                }
                if (!adj && prefix != null) 
                    adj = true;
                if (!adj) {
                    CityItemToken cit1 = CityItemToken.tryParse(tt.getNext().getNext(), null, false, null);
                    if (cit1 != null && cit1.typ != CityItemToken.ItemType.PROPERNAME) 
                        adj = true;
                }
                if (!adj) {
                    if (MiscLocationHelper.checkGeoObjectBefore(npt.getBeginToken())) 
                        adj = true;
                }
                com.pullenti.ner.Token te = tt.getNext().getNext();
                if (te != null && te.isCharOf(",")) 
                    te = te.getNext();
                if (!adj && (te instanceof com.pullenti.ner.ReferentToken)) {
                    if (te.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                        adj = true;
                }
                if (!adj) {
                    te = t0.getPrevious();
                    if (te != null && te.isCharOf(",")) 
                        te = te.getPrevious();
                    if (te instanceof com.pullenti.ner.ReferentToken) {
                        if (te.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                            adj = true;
                    }
                }
                if (adj && npt.adjectives.get(0).getBeginToken() != npt.adjectives.get(0).getEndToken()) {
                    if (com.pullenti.morph.CharsInfo.ooNoteq(npt.adjectives.get(0).getBeginToken().chars, npt.adjectives.get(0).getEndToken().chars)) 
                        return null;
                }
            }
            if (!adj && !t0.chars.isLatinLetter()) 
                return null;
        }
        TerrItemToken res = new TerrItemToken(t0, tt);
        res.isAdjective = tt.getMorph()._getClass().isAdjective();
        res.setMorph(tt.getMorph());
        if (t0 instanceof com.pullenti.ner.TextToken) {
            for(com.pullenti.morph.MorphBaseInfo wf : t0.getMorph().getItems()) {
                com.pullenti.morph.MorphWordForm f = (com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class);
                if (!f.isInDictionary()) 
                    continue;
                if (wf._getClass().isProperSurname() && f.isInDictionary()) 
                    res.canBeSurname = true;
                else if (wf._getClass().isAdjective() && f.isInDictionary()) 
                    res.isAdjInDictionary = true;
                else if (wf._getClass().isProperGeo()) {
                    if (!t0.chars.isAllLower()) 
                        res.isGeoInDictionary = true;
                }
            }
        }
        if ((tt.getWhitespacesAfterCount() < 2) && (tt.getNext() instanceof com.pullenti.ner.TextToken) && tt.getNext().chars.isCapitalUpper()) {
            com.pullenti.ner.MetaToken dir = MiscLocationHelper.tryAttachNordWest(tt.getNext());
            if (dir != null) 
                res.setEndToken(dir.getEndToken());
        }
        return res;
    }

    /**
     * Это пыделение возможного имени для городского района типа Владыкино, Тёплый Стан)
     * @param t 
     * @param intOnt 
     * @param proc 
     * @return 
     */
    public static TerrItemToken tryParseDistrictName(com.pullenti.ner.Token t, com.pullenti.ner.core.IntOntologyCollection intOnt) {
        if (!((t instanceof com.pullenti.ner.TextToken)) || !t.chars.isCapitalUpper() || !t.chars.isCyrillicLetter()) 
            return null;
        if ((t.getNext() != null && t.getNext().isHiphen() && (t.getNext().getNext() instanceof com.pullenti.ner.TextToken)) && com.pullenti.morph.CharsInfo.ooEq(t.getNext().getNext().chars, t.chars)) {
            java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> tok = m_TerrOntology.tryAttach(t, null, false);
            if ((tok != null && tok.get(0).item != null && (tok.get(0).item.referent instanceof com.pullenti.ner.geo.GeoReferent)) && (((com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(tok.get(0).item.referent, com.pullenti.ner.geo.GeoReferent.class))).isState()) 
                return null;
            tok = m_TerrOntology.tryAttach(t.getNext().getNext(), null, false);
            if ((tok != null && tok.get(0).item != null && (tok.get(0).item.referent instanceof com.pullenti.ner.geo.GeoReferent)) && (((com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(tok.get(0).item.referent, com.pullenti.ner.geo.GeoReferent.class))).isState()) 
                return null;
            return new TerrItemToken(t, t.getNext().getNext());
        }
        if ((t.getNext() instanceof com.pullenti.ner.TextToken) && com.pullenti.morph.CharsInfo.ooEq(t.getNext().chars, t.chars)) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (npt != null && npt.getEndToken() == t.getNext() && npt.adjectives.size() == 1) {
                if (!npt.getEndToken().getMorph()._getClass().isAdjective() || ((npt.getEndToken().getMorph().getCase().isNominative() && (npt.getEndToken() instanceof com.pullenti.ner.TextToken) && com.pullenti.morph.LanguageHelper.endsWith((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(npt.getEndToken(), com.pullenti.ner.TextToken.class))).term, "О")))) {
                    TerrItemToken ty = _TryParse(t.getNext(), intOnt, false);
                    if (ty != null && ty.terminItem != null) 
                        return null;
                    return new TerrItemToken(t, t.getNext());
                }
            }
        }
        String str = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
        TerrItemToken res = _new1128(t, t, true);
        if (!com.pullenti.morph.LanguageHelper.endsWith(str, "О")) 
            res.isDoubt = true;
        com.pullenti.ner.MetaToken dir = MiscLocationHelper.tryAttachNordWest(t);
        if (dir != null) {
            res.setEndToken(dir.getEndToken());
            res.isDoubt = false;
            if (res.getEndToken().getWhitespacesAfterCount() < 2) {
                TerrItemToken res2 = tryParseDistrictName(res.getEndToken().getNext(), intOnt);
                if (res2 != null && res2.terminItem == null) 
                    res.setEndToken(res2.getEndToken());
            }
        }
        return res;
    }

    public static void initialize() throws Exception, java.io.IOException, javax.xml.stream.XMLStreamException, org.xml.sax.SAXException {
        if (m_TerrOntology != null) 
            return;
        m_TerrOntology = new com.pullenti.ner.core.IntOntologyCollection();
        m_TerrAdjs = new com.pullenti.ner.core.TerminCollection();
        m_MansByState = new com.pullenti.ner.core.TerminCollection();
        m_UnknownRegions = new com.pullenti.ner.core.TerminCollection();
        m_TerrNounAdjectives = new com.pullenti.ner.core.TerminCollection();
        m_CapitalsByState = new com.pullenti.ner.core.TerminCollection();
        m_GeoAbbrs = new com.pullenti.ner.core.TerminCollection();
        TerrTermin t = new TerrTermin("РЕСПУБЛИКА", new com.pullenti.morph.MorphLang(null));
        t.addAbridge("РЕСП.");
        t.addAbridge("РЕСП-КА");
        t.addAbridge("РЕСПУБ.");
        t.addAbridge("РЕСПУБЛ.");
        t.addAbridge("Р-КА");
        t.addAbridge("РЕСП-КА");
        m_TerrOntology.add(t);
        m_TerrOntology.add(new TerrTermin("РЕСПУБЛІКА", com.pullenti.morph.MorphLang.UA));
        t = TerrTermin._new1129("ГОСУДАРСТВО", true);
        t.addAbridge("ГОС-ВО");
        m_TerrOntology.add(t);
        t = TerrTermin._new1130("ДЕРЖАВА", com.pullenti.morph.MorphLang.UA, true);
        m_TerrOntology.add(t);
        for(String s : new String[] {"СОЮЗ", "СОДРУЖЕСТВО", "ФЕДЕРАЦИЯ", "КОНФЕДЕРАЦИЯ"}) {
            m_TerrOntology.add(TerrTermin._new1131(s, true, true));
        }
        for(String s : new String[] {"СОЮЗ", "СПІВДРУЖНІСТЬ", "ФЕДЕРАЦІЯ", "КОНФЕДЕРАЦІЯ"}) {
            m_TerrOntology.add(TerrTermin._new1132(s, com.pullenti.morph.MorphLang.UA, true, true));
        }
        for(String s : new String[] {"КОРОЛЕВСТВО", "КНЯЖЕСТВО", "ГЕРЦОГСТВО", "ИМПЕРИЯ", "ЦАРСТВО", "KINGDOM", "DUCHY", "EMPIRE"}) {
            m_TerrOntology.add(TerrTermin._new1129(s, true));
        }
        for(String s : new String[] {"КОРОЛІВСТВО", "КНЯЗІВСТВО", "ГЕРЦОГСТВО", "ІМПЕРІЯ"}) {
            m_TerrOntology.add(TerrTermin._new1130(s, com.pullenti.morph.MorphLang.UA, true));
        }
        for(String s : new String[] {"НЕЗАВИСИМЫЙ", "ОБЪЕДИНЕННЫЙ", "СОЕДИНЕННЫЙ", "НАРОДНЫЙ", "НАРОДНО", "ФЕДЕРАТИВНЫЙ", "ДЕМОКРАТИЧЕСКИЙ", "СОВЕТСКИЙ", "СОЦИАЛИСТИЧЕСКИЙ", "КООПЕРАТИВНЫЙ", "ИСЛАМСКИЙ", "АРАБСКИЙ", "МНОГОНАЦИОНАЛЬНЫЙ", "СУВЕРЕННЫЙ", "САМОПРОВОЗГЛАШЕННЫЙ", "НЕПРИЗНАННЫЙ"}) {
            m_TerrOntology.add(TerrTermin._new1135(s, true, true));
        }
        for(String s : new String[] {"НЕЗАЛЕЖНИЙ", "ОБЄДНАНИЙ", "СПОЛУЧЕНИЙ", "НАРОДНИЙ", "ФЕДЕРАЛЬНИЙ", "ДЕМОКРАТИЧНИЙ", "РАДЯНСЬКИЙ", "СОЦІАЛІСТИЧНИЙ", "КООПЕРАТИВНИЙ", "ІСЛАМСЬКИЙ", "АРАБСЬКИЙ", "БАГАТОНАЦІОНАЛЬНИЙ", "СУВЕРЕННИЙ"}) {
            m_TerrOntology.add(TerrTermin._new1136(s, com.pullenti.morph.MorphLang.UA, true, true));
        }
        t = TerrTermin._new1137("ОБЛАСТЬ", true);
        t.addAbridge("ОБЛ.");
        m_TerrNounAdjectives.add(com.pullenti.ner.core.Termin._new118("ОБЛАСТНОЙ", t));
        m_TerrOntology.add(t);
        t = TerrTermin._new1137("REGION", true);
        m_TerrOntology.add(t);
        t = TerrTermin._new1140("ОБЛАСТЬ", com.pullenti.morph.MorphLang.UA, true);
        t.addAbridge("ОБЛ.");
        m_TerrOntology.add(t);
        t = TerrTermin._new1141(null, true, "АО");
        m_TerrOntology.add(t);
        t = TerrTermin._new1142(null, com.pullenti.morph.MorphLang.UA, true, "АО");
        m_TerrOntology.add(t);
        t = TerrTermin._new1137("РАЙОН", true);
        t.addAbridge("Р-Н");
        t.addAbridge("Р-ОН");
        m_TerrNounAdjectives.add(com.pullenti.ner.core.Termin._new118("РАЙОННЫЙ", t));
        m_TerrOntology.add(t);
        t = TerrTermin._new1140("РАЙОН", com.pullenti.morph.MorphLang.UA, true);
        t.addAbridge("Р-Н");
        t.addAbridge("Р-ОН");
        m_TerrOntology.add(t);
        t = TerrTermin._new1137("УЛУС", true);
        m_TerrOntology.add(t);
        t = TerrTermin._new1147("ШТАТ", true, true);
        m_TerrOntology.add(t);
        t = TerrTermin._new1137("STATE", true);
        m_TerrOntology.add(t);
        t = TerrTermin._new1149("ШТАТ", com.pullenti.morph.MorphLang.UA, true, true);
        m_TerrOntology.add(t);
        t = TerrTermin._new1147("ПРОВИНЦИЯ", true, true);
        m_TerrOntology.add(t);
        t = TerrTermin._new1149("ПРОВІНЦІЯ", com.pullenti.morph.MorphLang.UA, true, true);
        m_TerrOntology.add(t);
        t = TerrTermin._new1137("PROVINCE", true);
        t.addVariant("PROVINCIAL", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1137("ПРЕФЕКТУРА", true);
        m_TerrOntology.add(t);
        t = TerrTermin._new1137("PREFECTURE", true);
        m_TerrOntology.add(t);
        t = TerrTermin._new1137("АВТОНОМИЯ", true);
        m_TerrOntology.add(t);
        t = TerrTermin._new1137("AUTONOMY", true);
        m_TerrOntology.add(t);
        t = TerrTermin._new1140("АВТОНОМІЯ", com.pullenti.morph.MorphLang.UA, true);
        m_TerrOntology.add(t);
        for(String s : new String[] {"РЕСПУБЛИКА", "КРАЙ", "ОКРУГ", "ФЕДЕРАЛЬНЫЙ ОКРУГ", "АВТОНОМНЫЙ ОКРУГ", "НАЦИОНАЛЬНЫЙ ОКРУГ", "ВОЛОСТЬ", "ФЕДЕРАЛЬНАЯ ЗЕМЛЯ", "МУНИЦИПАЛЬНЫЙ РАЙОН", "МУНИЦИПАЛЬНЫЙ ОКРУГ", "АДМИНИСТРАТИВНЫЙ ОКРУГ", "ГОРОДСКОЙ РАЙОН", "ВНУТРИГОРОДСКОЙ РАЙОН", "ВНУТРИГОРОДСКОЕ МУНИЦИПАЛЬНОЕ ОБРАЗОВАНИЕ", "REPUBLIC", "COUNTY", "BOROUGH", "PARISH", "MUNICIPALITY", "CENSUS AREA", "AUTONOMOUS REGION", "ADMINISTRATIVE REGION", "SPECIAL ADMINISTRATIVE REGION"}) {
            t = TerrTermin._new1158(s, true, (s.indexOf(" ") >= 0));
            if (com.pullenti.n2j.Utils.stringsEq(s, "КРАЙ")) 
                m_TerrNounAdjectives.add(com.pullenti.ner.core.Termin._new118("КРАЕВОЙ", t));
            else if (com.pullenti.n2j.Utils.stringsEq(s, "ОКРУГ")) 
                m_TerrNounAdjectives.add(com.pullenti.ner.core.Termin._new118("ОКРУЖНОЙ", t));
            else if (com.pullenti.n2j.Utils.stringsEq(s, "ФЕДЕРАЛЬНЫЙ ОКРУГ")) {
                t.acronym = "ФО";
                t.acronymCanBeLower = false;
            }
            if (com.pullenti.morph.LanguageHelper.endsWith(s, "РАЙОН")) 
                t.addAbridge(s.replace("РАЙОН", "Р-Н"));
            m_TerrOntology.add(t);
        }
        for(String s : new String[] {"РЕСПУБЛІКА", "КРАЙ", "ОКРУГ", "ФЕДЕРАЛЬНИЙ ОКРУГ", "АВТОНОМНЫЙ ОКРУГ", "НАЦІОНАЛЬНИЙ ОКРУГ", "ВОЛОСТЬ", "ФЕДЕРАЛЬНА ЗЕМЛЯ", "МУНІЦИПАЛЬНИЙ РАЙОН", "МУНІЦИПАЛЬНИЙ ОКРУГ", "АДМІНІСТРАТИВНИЙ ОКРУГ", "МІСЬКИЙ РАЙОН", "ВНУТРИГОРОДСКОЕ МУНІЦИПАЛЬНЕ УТВОРЕННЯ"}) {
            t = TerrTermin._new1161(s, com.pullenti.morph.MorphLang.UA, true, (s.indexOf(" ") >= 0));
            if (com.pullenti.morph.LanguageHelper.endsWith(s, "РАЙОН")) 
                t.addAbridge(s.replace("РАЙОН", "Р-Н"));
            m_TerrOntology.add(t);
        }
        t = TerrTermin._new1137("СЕЛЬСКИЙ ОКРУГ", true);
        t.addAbridge("С.О.");
        t.addAbridge("C.O.");
        t.addAbridge("ПС С.О.");
        t.addAbridge("С/ОКРУГ");
        t.addAbridge("С/О");
        m_TerrOntology.add(t);
        t = TerrTermin._new1140("СІЛЬСЬКИЙ ОКРУГ", com.pullenti.morph.MorphLang.UA, true);
        t.addAbridge("С.О.");
        t.addAbridge("C.O.");
        t.addAbridge("С/ОКРУГ");
        m_TerrOntology.add(t);
        t = TerrTermin._new1164("СЕЛЬСКИЙ СОВЕТ", "СЕЛЬСКИЙ ОКРУГ", true);
        t.addVariant("СЕЛЬСОВЕТ", false);
        t.addAbridge("С.С.");
        t.addAbridge("С/С");
        t.addVariant("СЕЛЬСКАЯ АДМИНИСТРАЦИЯ", false);
        t.addAbridge("С.А.");
        t.addAbridge("С.АДМ.");
        m_TerrOntology.add(t);
        t = TerrTermin._new1137("ПОСЕЛКОВЫЙ ОКРУГ", true);
        t.addAbridge("П.О.");
        t.addAbridge("П/О");
        t.addVariant("ПОСЕЛКОВАЯ АДМИНИСТРАЦИЯ", false);
        t.addAbridge("П.А.");
        t.addAbridge("П.АДМ.");
        t.addAbridge("П/А");
        m_TerrOntology.add(t);
        t = TerrTermin._new1164("ПОСЕЛКОВЫЙ СОВЕТ", "ПОСЕЛКОВЫЙ ОКРУГ", true);
        t.addAbridge("П.С.");
        m_TerrOntology.add(t);
        m_TerrOntology.add(TerrTermin._new1167("АВТОНОМНЫЙ", true, true));
        m_TerrOntology.add(TerrTermin._new1168("АВТОНОМНИЙ", com.pullenti.morph.MorphLang.UA, true, true));
        m_TerrOntology.add(TerrTermin._new1169("МУНИЦИПАЛЬНОЕ СОБРАНИЕ", true, true, true));
        m_TerrOntology.add(TerrTermin._new1170("МУНІЦИПАЛЬНЕ ЗБОРИ", com.pullenti.morph.MorphLang.UA, true, true, true));
        t = TerrTermin._new1171("МУНИЦИПАЛЬНОЕ ОБРАЗОВАНИЕ", "МО");
        m_TerrOntology.add(t);
        t = new TerrTermin("ТЕРРИТОРИЯ", new com.pullenti.morph.MorphLang(null));
        t.addAbridge("ТЕР.");
        t.addAbridge("ТЕРРИТОР.");
        m_TerrOntology.add(t);
        t = TerrTermin._new1172("ЦЕНТРАЛЬНЫЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("ЦАО");
        t.addVariant("ЦЕНТРАЛЬНЫЙ АО", false);
        t.addVariant("ЦЕНТРАЛЬНЫЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1172("СЕВЕРНЫЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("САО");
        t.addVariant("СЕВЕРНЫЙ АО", false);
        t.addVariant("СЕВЕРНЫЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1172("СЕВЕРО-ВОСТОЧНЫЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("СВАО");
        t.addVariant("СЕВЕРО-ВОСТОЧНЫЙ АО", false);
        t.addVariant("СЕВЕРО-ВОСТОЧНЫЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1172("ВОСТОЧНЫЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("ВАО");
        t.addVariant("ВОСТОЧНЫЙ АО", false);
        t.addVariant("ВОСТОЧНЫЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1172("ЮГО-ВОСТОЧНЫЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("ЮВАО");
        t.addVariant("ЮГО-ВОСТОЧНЫЙ АО", false);
        t.addVariant("ЮГО-ВОСТОЧНЫЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1172("ЮЖНЫЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("ЮАО");
        t.addVariant("ЮЖНЫЙ АО", false);
        t.addVariant("ЮЖНЫЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1172("ЗАПАДНЫЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("ЗАО");
        t.addVariant("ЗАПАДНЫЙ АО", false);
        t.addVariant("ЗАПАДНЫЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1172("СЕВЕРО-ЗАПАДНЫЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("СЗАО");
        t.addVariant("СЕВЕРО-ЗАПАДНЫЙ АО", false);
        t.addVariant("СЕВЕРО-ЗАПАДНЫЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1172("ЗЕЛЕНОГРАДСКИЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("ЗЕЛАО");
        t.addVariant("ЗЕЛЕНОГРАДСКИЙ АО", false);
        t.addVariant("ЗЕЛЕНОГРАДСКИЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1172("ТРОИЦКИЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("ТАО");
        t.addVariant("ТРОИЦКИЙ АО", false);
        t.addVariant("ТРОИЦКИЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1172("НОВОМОСКОВСКИЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("НАО");
        t.addVariant("НОВОМОСКОВСКИЙ АО", false);
        t.addVariant("НОВОМОСКОВСКИЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        t = TerrTermin._new1172("ТРОИЦКИЙ И НОВОМОСКОВСКИЙ АДМИНИСТРАТИВНЫЙ ОКРУГ", true);
        t.addAbridge("ТИНАО");
        t.addAbridge("НИТАО");
        t.addVariant("ТРОИЦКИЙ И НОВОМОСКОВСКИЙ АО", false);
        t.addVariant("ТРОИЦКИЙ И НОВОМОСКОВСКИЙ ОКРУГ", false);
        m_TerrOntology.add(t);
        m_Alpha2State = new java.util.HashMap< >();
        byte[] dat = com.pullenti.ner.address.internal.ResourceHelper.getBytes("t.dat");
        if (dat == null) 
            throw new Exception("Not found resource file t.dat in Analyzer.Location");
        dat = MiscLocationHelper.deflate(dat);
        try (com.pullenti.n2j.MemoryStream tmp = new com.pullenti.n2j.MemoryStream(dat)) {
            tmp.setPosition((long)0);
            com.pullenti.n2j.XmlDocumentWrapper xml = new com.pullenti.n2j.XmlDocumentWrapper();
            xml.load(tmp);
            for(org.w3c.dom.Node x : (new com.pullenti.n2j.XmlNodeListWrapper(xml.doc.getDocumentElement().getChildNodes())).arr) {
                com.pullenti.morph.MorphLang lang = com.pullenti.morph.MorphLang.RU;
                org.w3c.dom.Node a = com.pullenti.n2j.Utils.getXmlAttrByName(x, "l");
                if (a != null) {
                    if (com.pullenti.n2j.Utils.stringsEq(a.getNodeValue(), "en")) 
                        lang = com.pullenti.morph.MorphLang.EN;
                    else if (com.pullenti.n2j.Utils.stringsEq(a.getNodeValue(), "ua")) 
                        lang = com.pullenti.morph.MorphLang.UA;
                }
                if (com.pullenti.n2j.Utils.stringsEq(x.getNodeName(), "state")) 
                    loadState(x, lang);
                else if (com.pullenti.n2j.Utils.stringsEq(x.getNodeName(), "reg")) 
                    loadRegion(x, lang);
                else if (com.pullenti.n2j.Utils.stringsEq(x.getNodeName(), "unknown")) {
                    a = com.pullenti.n2j.Utils.getXmlAttrByName(x, "name");
                    if (a != null && a.getNodeValue() != null) 
                        m_UnknownRegions.add(com.pullenti.ner.core.Termin._new858(a.getNodeValue(), lang));
                }
            }
        }
    }

    /**
     * Словарь стран и некоторых терминов
     */
    public static com.pullenti.ner.core.IntOntologyCollection m_TerrOntology;

    public static com.pullenti.ner.core.TerminCollection m_GeoAbbrs;

    private static com.pullenti.ner.core.IntOntologyItem m_RussiaRU;

    private static com.pullenti.ner.core.IntOntologyItem m_RussiaUA;

    private static com.pullenti.ner.core.IntOntologyItem m_MosRegRU;

    private static com.pullenti.ner.core.IntOntologyItem m_LenRegRU;

    private static com.pullenti.ner.core.IntOntologyItem m_Belorussia;

    private static com.pullenti.ner.core.IntOntologyItem m_Kazahstan;

    private static com.pullenti.ner.core.IntOntologyItem m_TamogSous;

    private static com.pullenti.ner.core.IntOntologyItem m_Tatarstan;

    private static com.pullenti.ner.core.IntOntologyItem m_Udmurtia;

    private static com.pullenti.ner.core.IntOntologyItem m_Dagestan;

    public static com.pullenti.ner.core.TerminCollection m_TerrAdjs;

    public static com.pullenti.ner.core.TerminCollection m_MansByState;

    public static com.pullenti.ner.core.TerminCollection m_UnknownRegions;

    public static com.pullenti.ner.core.TerminCollection m_TerrNounAdjectives;

    public static com.pullenti.ner.core.TerminCollection m_CapitalsByState;

    public static java.util.HashMap<String, com.pullenti.ner.core.IntOntologyItem> m_Alpha2State;

    public static java.util.ArrayList<com.pullenti.ner.Referent> m_AllStates = new java.util.ArrayList< >();

    private static void loadState(org.w3c.dom.Node xml, com.pullenti.morph.MorphLang lang) {
        com.pullenti.ner.geo.GeoReferent state = new com.pullenti.ner.geo.GeoReferent();
        com.pullenti.ner.core.IntOntologyItem c = new com.pullenti.ner.core.IntOntologyItem(state);
        java.util.ArrayList<String> acrs = null;
        for(org.w3c.dom.Node x : (new com.pullenti.n2j.XmlNodeListWrapper(xml.getChildNodes())).arr) {
            if (com.pullenti.n2j.Utils.stringsEq(x.getNodeName(), "n")) {
                com.pullenti.ner.core.Termin te = new com.pullenti.ner.core.Termin(null, new com.pullenti.morph.MorphLang(null), false);
                te.initByNormalText(x.getTextContent(), new com.pullenti.morph.MorphLang(null));
                c.termins.add(te);
                state.addName(x.getTextContent());
            }
            else if (com.pullenti.n2j.Utils.stringsEq(x.getNodeName(), "acr")) {
                c.termins.add(com.pullenti.ner.core.Termin._new1185(x.getTextContent(), lang));
                state.addName(x.getTextContent());
                if (acrs == null) 
                    acrs = new java.util.ArrayList< >();
                acrs.add(x.getTextContent());
            }
            else if (com.pullenti.n2j.Utils.stringsEq(x.getNodeName(), "a")) {
                com.pullenti.ner.core.Termin te = new com.pullenti.ner.core.Termin(null, new com.pullenti.morph.MorphLang(null), false);
                te.initByNormalText(x.getTextContent(), lang);
                te.tag = c;
                c.termins.add(te);
                m_TerrAdjs.add(te);
            }
            else if (com.pullenti.n2j.Utils.stringsEq(x.getNodeName(), "a2")) 
                state.setAlpha2(x.getTextContent());
            else if (com.pullenti.n2j.Utils.stringsEq(x.getNodeName(), "m")) {
                com.pullenti.ner.core.Termin te = new com.pullenti.ner.core.Termin(null, new com.pullenti.morph.MorphLang(null), false);
                te.initByNormalText(x.getTextContent(), lang);
                te.tag = state;
                te.setGender(com.pullenti.morph.MorphGender.MASCULINE);
                m_MansByState.add(te);
            }
            else if (com.pullenti.n2j.Utils.stringsEq(x.getNodeName(), "w")) {
                com.pullenti.ner.core.Termin te = new com.pullenti.ner.core.Termin(null, new com.pullenti.morph.MorphLang(null), false);
                te.initByNormalText(x.getTextContent(), lang);
                te.tag = state;
                te.setGender(com.pullenti.morph.MorphGender.FEMINIE);
                m_MansByState.add(te);
            }
            else if (com.pullenti.n2j.Utils.stringsEq(x.getNodeName(), "cap")) {
                com.pullenti.ner.core.Termin te = new com.pullenti.ner.core.Termin(null, new com.pullenti.morph.MorphLang(null), false);
                te.initByNormalText(x.getTextContent(), lang);
                te.tag = state;
                m_CapitalsByState.add(te);
            }
        }
        c.setShortestCanonicalText(true);
        if (com.pullenti.n2j.Utils.stringsEq(c.getCanonicText(), "ГОЛЛАНДИЯ") || c.getCanonicText().startsWith("КОРОЛЕВСТВО НИДЕР")) 
            c.setCanonicText("НИДЕРЛАНДЫ");
        else if (com.pullenti.n2j.Utils.stringsEq(c.getCanonicText(), "ГОЛЛАНДІЯ") || c.getCanonicText().startsWith("КОРОЛІВСТВО НІДЕР")) 
            c.setCanonicText("НІДЕРЛАНДИ");
        if (com.pullenti.n2j.Utils.stringsEq(state.getAlpha2(), "RU")) {
            if (lang.isUa()) 
                m_RussiaUA = c;
            else 
                m_RussiaRU = c;
        }
        else if (com.pullenti.n2j.Utils.stringsEq(state.getAlpha2(), "BY")) {
            if (!lang.isUa()) 
                m_Belorussia = c;
        }
        else if (com.pullenti.n2j.Utils.stringsEq(state.getAlpha2(), "KZ")) {
            if (!lang.isUa()) 
                m_Kazahstan = c;
        }
        else if (com.pullenti.n2j.Utils.stringsEq(c.getCanonicText(), "ТАМОЖЕННЫЙ СОЮЗ")) {
            if (!lang.isUa()) 
                m_TamogSous = c;
        }
        if (state.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, null, true) == null) {
            if (lang.isUa()) 
                state.addTypState(lang);
            else {
                state.addTypState(com.pullenti.morph.MorphLang.RU);
                state.addTypState(com.pullenti.morph.MorphLang.EN);
            }
        }
        m_TerrOntology.addItem(c);
        if (lang.isRu()) 
            m_AllStates.add(state);
        String a2 = state.getAlpha2();
        if (a2 != null) {
            if (!m_Alpha2State.containsKey(a2)) 
                m_Alpha2State.put(a2, c);
            String a3;
            com.pullenti.n2j.Outargwrapper<String> inoutarg1186 = new com.pullenti.n2j.Outargwrapper< >();
            Boolean inoutres1187 = com.pullenti.n2j.Utils.tryGetValue(MiscLocationHelper.m_Alpha2_3, a2, inoutarg1186);
            a3 = inoutarg1186.value;
            if (inoutres1187) {
                if (!m_Alpha2State.containsKey(a3)) 
                    m_Alpha2State.put(a3, c);
            }
        }
        if (acrs != null) {
            for(String a : acrs) {
                if (!m_Alpha2State.containsKey(a)) 
                    m_Alpha2State.put(a, c);
            }
        }
    }

    private static void loadRegion(org.w3c.dom.Node xml, com.pullenti.morph.MorphLang lang) {
        com.pullenti.ner.geo.GeoReferent reg = new com.pullenti.ner.geo.GeoReferent();
        com.pullenti.ner.core.IntOntologyItem r = new com.pullenti.ner.core.IntOntologyItem(reg);
        com.pullenti.ner.core.Termin aTerm = null;
        for(org.w3c.dom.Node x : (new com.pullenti.n2j.XmlNodeListWrapper(xml.getChildNodes())).arr) {
            if (com.pullenti.n2j.Utils.stringsEq(x.getNodeName(), "n")) {
                String v = x.getTextContent();
                if (v.startsWith("ЦЕНТРАЛ")) {
                }
                com.pullenti.ner.core.Termin te = new com.pullenti.ner.core.Termin(null, new com.pullenti.morph.MorphLang(null), false);
                te.initByNormalText(v, lang);
                if (lang.isRu() && m_MosRegRU == null && com.pullenti.n2j.Utils.stringsEq(v, "ПОДМОСКОВЬЕ")) {
                    m_MosRegRU = r;
                    te.addAbridge("МОС.ОБЛ.");
                }
                else if (lang.isRu() && m_LenRegRU == null && com.pullenti.n2j.Utils.stringsEq(v, "ЛЕНОБЛАСТЬ")) {
                    te.acronym = "ЛО";
                    te.addAbridge("ЛЕН.ОБЛ.");
                    m_LenRegRU = r;
                }
                r.termins.add(te);
                reg.addName(v);
            }
            else if (com.pullenti.n2j.Utils.stringsEq(x.getNodeName(), "t")) 
                reg.addTyp(x.getTextContent());
            else if (com.pullenti.n2j.Utils.stringsEq(x.getNodeName(), "a")) {
                com.pullenti.ner.core.Termin te = new com.pullenti.ner.core.Termin(null, new com.pullenti.morph.MorphLang(null), false);
                te.initByNormalText(x.getTextContent(), lang);
                te.tag = r;
                r.termins.add(te);
            }
            else if (com.pullenti.n2j.Utils.stringsEq(x.getNodeName(), "ab")) {
                if (aTerm == null) 
                    aTerm = com.pullenti.ner.core.Termin._new459(reg.getStringValue(com.pullenti.ner.geo.GeoReferent.ATTR_NAME), lang, reg);
                aTerm.addAbridge(x.getTextContent());
            }
        }
        if (aTerm != null) 
            m_GeoAbbrs.add(aTerm);
        r.setShortestCanonicalText(true);
        if (r.getCanonicText().startsWith("КАРАЧАЕВО")) 
            r.setCanonicText("КАРАЧАЕВО - ЧЕРКЕССИЯ");
        if ((r.getCanonicText().indexOf("ТАТАРСТАН") >= 0)) 
            m_Tatarstan = r;
        else if ((r.getCanonicText().indexOf("УДМУРТ") >= 0)) 
            m_Udmurtia = r;
        else if ((r.getCanonicText().indexOf("ДАГЕСТАН") >= 0)) 
            m_Dagestan = r;
        if (reg.isState() && reg.isRegion()) 
            reg.addTypReg(lang);
        m_TerrOntology.addItem(r);
    }

    public static TerrItemToken _new1110(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3) {
        TerrItemToken res = new TerrItemToken(_arg1, _arg2);
        res.isAdjective = _arg3;
        return res;
    }
    public static TerrItemToken _new1111(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, TerrTermin _arg3, boolean _arg4) {
        TerrItemToken res = new TerrItemToken(_arg1, _arg2);
        res.terminItem = _arg3;
        res.isDoubt = _arg4;
        return res;
    }
    public static TerrItemToken _new1112(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.core.IntOntologyItem _arg3) {
        TerrItemToken res = new TerrItemToken(_arg1, _arg2);
        res.ontoItem = _arg3;
        return res;
    }
    public static TerrItemToken _new1121(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.ReferentToken _arg3, com.pullenti.ner.MorphCollection _arg4) {
        TerrItemToken res = new TerrItemToken(_arg1, _arg2);
        res.rzd = _arg3;
        res.setMorph(_arg4);
        return res;
    }
    public static TerrItemToken _new1123(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.core.IntOntologyItem _arg3, com.pullenti.ner.MorphCollection _arg4) {
        TerrItemToken res = new TerrItemToken(_arg1, _arg2);
        res.ontoItem = _arg3;
        res.setMorph(_arg4);
        return res;
    }
    public static TerrItemToken _new1124(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.core.IntOntologyItem _arg3, boolean _arg4, com.pullenti.ner.MorphCollection _arg5) {
        TerrItemToken res = new TerrItemToken(_arg1, _arg2);
        res.ontoItem = _arg3;
        res.isAdjective = _arg4;
        res.setMorph(_arg5);
        return res;
    }
    public static TerrItemToken _new1125(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, TerrTermin _arg3, boolean _arg4, com.pullenti.ner.MorphCollection _arg5) {
        TerrItemToken res = new TerrItemToken(_arg1, _arg2);
        res.terminItem = _arg3;
        res.isAdjective = _arg4;
        res.setMorph(_arg5);
        return res;
    }
    public static TerrItemToken _new1128(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3) {
        TerrItemToken res = new TerrItemToken(_arg1, _arg2);
        res.isDoubt = _arg3;
        return res;
    }
    public TerrItemToken() {
        super();
    }
}
