/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.address.internal;

/**
 * Токен для поддержки выделения улиц
 */
public class StreetItemToken extends com.pullenti.ner.MetaToken {

    private StreetItemToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    public StreetItemType typ = StreetItemType.NOUN;

    public com.pullenti.ner.core.Termin termin;

    public com.pullenti.ner.core.Termin altTermin;

    public com.pullenti.ner.address.StreetReferent existStreet;

    public com.pullenti.ner.NumberToken number;

    public boolean numberHasPrefix;

    public boolean isNumberKm;

    public String value;

    public String altValue;

    public String altValue2;

    /**
     * Признак сокращения
     */
    public boolean isAbridge;

    public boolean isInDictionary;

    public boolean isInBrackets;

    public boolean hasStdSuffix;

    public int nounIsDoubtCoef;

    public boolean isRoad() {
        if (termin == null) 
            return false;
        if ((com.pullenti.n2j.Utils.stringsEq(termin.getCanonicText(), "АВТОДОРОГА") || com.pullenti.n2j.Utils.stringsEq(termin.getCanonicText(), "ШОССЕ") || com.pullenti.n2j.Utils.stringsEq(termin.getCanonicText(), "АВТОШЛЯХ")) || com.pullenti.n2j.Utils.stringsEq(termin.getCanonicText(), "ШОСЕ")) 
            return true;
        return false;
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(typ.toString());
        if (value != null) {
            res.append(" ").append(value);
            if (altValue != null) 
                res.append("/").append(altValue);
        }
        if (existStreet != null) 
            res.append(" ").append(existStreet.toString());
        if (termin != null) {
            res.append(" ").append(termin.toString());
            if (altTermin != null) 
                res.append("/").append(altTermin.toString());
        }
        else if (number != null) 
            res.append(" ").append(number.toString());
        else 
            res.append(" ").append(super.toString());
        if (isAbridge) 
            res.append(" (?)");
        return res.toString();
    }

    private boolean _isSurname() {
        if (typ != StreetItemType.NAME) 
            return false;
        if (!((getEndToken() instanceof com.pullenti.ner.TextToken))) 
            return false;
        String nam = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(getEndToken(), com.pullenti.ner.TextToken.class))).term;
        if (nam.length() > 4) {
            if (com.pullenti.morph.LanguageHelper.endsWithEx(nam, "А", "Я", "КО", "ЧУКА")) {
                if (!com.pullenti.morph.LanguageHelper.endsWithEx(nam, "АЯ", "ЯЯ", null, null)) 
                    return true;
            }
        }
        return false;
    }

    public static StreetItemToken tryParse(com.pullenti.ner.Token t, com.pullenti.ner.core.IntOntologyCollection locStreets, boolean recurse, StreetItemToken prev, boolean ignoreOnto) {
        if (t == null) 
            return null;
        if (t.kit.isRecurceOverflow()) 
            return null;
        t.kit.recurseLevel++;
        StreetItemToken res = _tryParse(t, locStreets, recurse, prev, ignoreOnto);
        t.kit.recurseLevel--;
        return res;
    }

    public static StreetItemToken _tryParse(com.pullenti.ner.Token t, com.pullenti.ner.core.IntOntologyCollection locStreets, boolean recurse, StreetItemToken prev, boolean ignoreOnto) {
        if (t == null) 
            return null;
        com.pullenti.ner.Token tn = null;
        if (t.isValue("ИМЕНИ", null) || t.isValue("ІМЕНІ", null)) 
            tn = t;
        else if (t.isValue("ИМ", null) || t.isValue("ІМ", null)) {
            tn = t;
            if (tn.getNext() != null && tn.getNext().isChar('.')) 
                tn = tn.getNext();
        }
        if (tn != null) {
            if (tn.getNext() == null || tn.getWhitespacesAfterCount() > 2) 
                return null;
            t = tn.getNext();
        }
        com.pullenti.ner.NumberToken nt = com.pullenti.ner.core.NumberHelper.tryParseAge(t);
        if (nt != null) 
            return _new218(nt.getBeginToken(), nt.getEndToken(), StreetItemType.AGE, nt);
        if ((((nt = (com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class)))) != null) {
            if ((((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(nt, com.pullenti.ner.NumberToken.class))).value == ((long)0)) 
                return null;
            StreetItemToken res = _new219(nt, nt, StreetItemType.NUMBER, nt, nt.getMorph());
            if ((t.getNext() != null && t.getNext().isHiphen() && t.getNext().getNext() != null) && t.getNext().getNext().isValue("Я", null)) 
                res.setEndToken(t.getNext().getNext());
            com.pullenti.ner.core.NumberExToken nex = com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(t);
            if (nex != null) {
                if (nex.exTyp == com.pullenti.ner.core.NumberExType.KILOMETER) {
                    res.isNumberKm = true;
                    res.setEndToken(nex.getEndToken());
                }
                else 
                    return null;
            }
            AddressItemToken aaa = AddressItemToken.tryParse(t, null, false, true, null);
            if (aaa != null && aaa.typ == AddressItemToken.ItemType.NUMBER && aaa.endChar > t.endChar) {
                if (prev != null && prev.typ == StreetItemType.NOUN && com.pullenti.n2j.Utils.stringsEq(prev.termin.getCanonicText(), "КВАРТАЛ")) {
                    res.setEndToken(aaa.getEndToken());
                    res.value = aaa.value;
                    res.number = null;
                }
                else 
                    return null;
            }
            if (nt.typ == com.pullenti.ner.NumberSpellingType.WORDS && nt.getMorph()._getClass().isAdjective()) {
                com.pullenti.ner.core.NounPhraseToken npt2 = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                if (npt2 != null && npt2.endChar > t.endChar && npt2.getMorph().getNumber() != com.pullenti.morph.MorphNumber.SINGULAR) {
                    if (t.getNext() != null && !t.getNext().chars.isAllLower()) {
                    }
                    else 
                        return null;
                }
            }
            return res;
        }
        com.pullenti.ner.Token ntt = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t);
        if (ntt != null && (ntt instanceof com.pullenti.ner.NumberToken) && prev != null) 
            return _new220(t, ntt, StreetItemType.NUMBER, (com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(ntt, com.pullenti.ner.NumberToken.class), true);
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt != null && tt.getMorph()._getClass().isAdjective()) {
            if (tt.chars.isCapitalUpper() || ((prev != null && prev.typ == StreetItemType.NUMBER && tt.isValue("ТРАНСПОРТНЫЙ", null)))) {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                if (npt != null && (com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(npt.noun, com.pullenti.ner.core.GetTextAttr.NO).indexOf("-") >= 0)) 
                    npt = null;
                com.pullenti.ner.Token tte = tt.getNext();
                if (npt != null && npt.adjectives.size() == 1) 
                    tte = npt.getEndToken();
                if (tte != null) {
                    if ((((((((((tte.isValue("ВАЛ", null) || tte.isValue("ТРАКТ", null) || tte.isValue("ПОЛЕ", null)) || tte.isValue("МАГИСТРАЛЬ", null) || tte.isValue("СПУСК", null)) || tte.isValue("ВЗВОЗ", null) || tte.isValue("РЯД", null)) || tte.isValue("СЛОБОДА", null) || tte.isValue("РОЩА", null)) || tte.isValue("ПРУД", null) || tte.isValue("СЪЕЗД", null)) || tte.isValue("КОЛЬЦО", null) || tte.isValue("МАГІСТРАЛЬ", null)) || tte.isValue("УЗВІЗ", null) || tte.isValue("ЛІНІЯ", null)) || tte.isValue("УЗВІЗ", null) || tte.isValue("ГАЙ", null)) || tte.isValue("СТАВОК", null) || tte.isValue("ЗЇЗД", null)) || tte.isValue("КІЛЬЦЕ", null)) {
                        StreetItemToken sit = _new221(tt, tte, true);
                        sit.typ = StreetItemType.NAME;
                        if (npt == null || npt.adjectives.size() == 0) 
                            sit.value = com.pullenti.ner.core.MiscHelper.getTextValue(tt, tte, com.pullenti.ner.core.GetTextAttr.NO);
                        else 
                            sit.value = npt.getNormalCaseText(new com.pullenti.morph.MorphClass(null), false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                        com.pullenti.ner.core.TerminToken tok2 = m_Ontology.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
                        if (tok2 != null && tok2.termin != null && tok2.getEndToken() == tte) 
                            sit.termin = tok2.termin;
                        return sit;
                    }
                }
                if (npt != null && npt.getBeginToken() != npt.getEndToken() && npt.adjectives.size() <= 1) {
                    com.pullenti.ner.Token tt1 = npt.getEndToken().getNext();
                    if (tt1 != null && tt1.isComma()) 
                        tt1 = tt1.getNext();
                    boolean ok = false;
                    StreetItemToken sti1 = (recurse ? null : tryParse(tt1, locStreets, true, null, false));
                    if (sti1 != null && sti1.typ == StreetItemType.NOUN) 
                        ok = true;
                    else {
                        AddressItemToken ait = AddressItemToken.tryParse(tt1, locStreets, false, true, null);
                        if (ait != null) {
                            if (ait.typ == AddressItemToken.ItemType.HOUSE) 
                                ok = true;
                            else if (ait.typ == AddressItemToken.ItemType.NUMBER) {
                                AddressItemToken ait2 = AddressItemToken.tryParse(npt.getEndToken(), locStreets, false, true, null);
                                if (ait2 == null) 
                                    ok = true;
                            }
                        }
                    }
                    if (ok) {
                        sti1 = tryParse(npt.getEndToken(), locStreets, false, null, false);
                        if (sti1 != null && sti1.typ == StreetItemType.NOUN) 
                            ok = false;
                    }
                    if (ok) {
                        StreetItemToken sit = new StreetItemToken(tt, npt.getEndToken());
                        sit.typ = StreetItemType.NAME;
                        sit.value = com.pullenti.ner.core.MiscHelper.getTextValue(tt, npt.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                        sit.altValue = npt.getNormalCaseText(new com.pullenti.morph.MorphClass(null), false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                        return sit;
                    }
                }
            }
        }
        if ((tt != null && (tt.getNext() instanceof com.pullenti.ner.TextToken) && tt.getNext().chars.isCapitalUpper()) && !recurse) {
            if ((tt.isValue("ВАЛ", null) || tt.isValue("ТРАКТ", null) || tt.isValue("ПОЛЕ", null)) || tt.isValue("КОЛЬЦО", null) || tt.isValue("КІЛЬЦЕ", null)) {
                StreetItemToken sit = tryParse(tt.getNext(), locStreets, true, null, false);
                if (sit != null && sit.typ == StreetItemType.NAME) {
                    if (sit.value != null) 
                        sit.value = sit.value + " " + tt.getNormalCaseText(new com.pullenti.morph.MorphClass(null), false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                    else 
                        sit.value = sit.getSourceText().toUpperCase() + " " + tt.getNormalCaseText(new com.pullenti.morph.MorphClass(null), false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                    if (sit.altValue != null) 
                        sit.altValue = sit.altValue + " " + tt.getNormalCaseText(new com.pullenti.morph.MorphClass(null), false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                    sit.setBeginToken(tt);
                    return sit;
                }
            }
        }
        if (((tt != null && tt.getLengthChar() == 1 && tt.chars.isAllLower()) && tt.getNext() != null && tt.getNext().isChar('.')) && tt.kit.baseLanguage.isRu()) {
            if (tt.isValue("М", null) || tt.isValue("M", null)) {
                if (prev != null && prev.typ == StreetItemType.NOUN) {
                }
                else 
                    return _new222(tt, tt.getNext(), m_Metro, StreetItemType.NOUN, true);
            }
        }
        com.pullenti.ner.core.IntOntologyToken ot = null;
        if (locStreets != null) {
            java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> ots = locStreets.tryAttach(t, null, false);
            if (ots != null) 
                ot = ots.get(0);
        }
        if (t.kit.ontology != null && ot == null) {
            java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> ots = t.kit.ontology.attachToken(com.pullenti.ner.address.AddressReferent.OBJ_TYPENAME, t);
            if (ots != null) 
                ot = ots.get(0);
        }
        if (ot != null && ot.getBeginToken() == ot.getEndToken() && ot.getMorph()._getClass().isAdjective()) {
            com.pullenti.ner.core.TerminToken tok0 = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok0 != null) {
                if (((StreetItemType)tok0.termin.tag) == StreetItemType.STDADJECTIVE) 
                    ot = null;
            }
        }
        if (ot != null) {
            StreetItemToken res0 = _new223(ot.getBeginToken(), ot.getEndToken(), StreetItemType.NAME, (com.pullenti.ner.address.StreetReferent)com.pullenti.n2j.Utils.cast(ot.item.referent, com.pullenti.ner.address.StreetReferent.class), ot.getMorph(), true);
            return res0;
        }
        com.pullenti.ner.core.TerminToken tok = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok != null && com.pullenti.n2j.Utils.stringsEq(tok.termin.getCanonicText(), "НАБЕРЕЖНАЯ") && !tok.chars.isAllLower()) {
            StreetItemToken nex = tryParse(tok.getEndToken().getNext(), null, false, null, false);
            if (nex != null && ((nex.typ == StreetItemType.NOUN || nex.typ == StreetItemType.STDADJECTIVE))) 
                tok = null;
            else if ((((t.getMorph().getGender().value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value()) && t.getLengthChar() > 7) 
                tok = null;
        }
        if (((tok != null && t.getLengthChar() == 1 && t.isValue("Б", null)) && prev != null && prev.number != null) && prev.number.value == ((long)26)) 
            tok = null;
        if (tok != null && !ignoreOnto) {
            if (((StreetItemType)tok.termin.tag) == StreetItemType.NUMBER) {
                if (tok.getEndToken().getNext() instanceof com.pullenti.ner.NumberToken) 
                    return _new224(t, tok.getEndToken().getNext(), StreetItemType.NUMBER, (com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(tok.getEndToken().getNext(), com.pullenti.ner.NumberToken.class), true, tok.getMorph());
                return null;
            }
            if (tt == null) 
                return null;
            boolean abr = true;
            switch(((StreetItemType)tok.termin.tag).value()) { 
            case 3:
                if (tt.chars.isAllLower() && prev == null) 
                    return null;
                else if (tt.isValue(tok.termin.getCanonicText(), null)) 
                    abr = false;
                else if (tt.getLengthChar() == 1) {
                    if (!tt.isWhitespaceBefore() && !tt.getPrevious().isCharOf(":,.")) 
                        break;
                    if (!tok.getEndToken().isChar('.')) {
                        if (!tt.chars.isAllUpper()) 
                            break;
                        boolean oo2 = false;
                        if (tok.getEndToken().isNewlineAfter() && prev != null) 
                            oo2 = true;
                        else {
                            StreetItemToken _next = tryParse(tok.getEndToken().getNext(), null, false, null, false);
                            if (_next != null && ((_next.typ == StreetItemType.NAME || _next.typ == StreetItemType.NOUN))) 
                                oo2 = true;
                            else if (AddressItemToken.checkHouseAfter(tok.getEndToken().getNext(), false, true) && prev != null) 
                                oo2 = true;
                        }
                        if (oo2) 
                            return _new225(tok.getBeginToken(), tok.getEndToken(), StreetItemType.STDADJECTIVE, tok.termin, abr, tok.getMorph());
                        break;
                    }
                    com.pullenti.ner.Token tt2 = tok.getEndToken().getNext();
                    if (tt2 != null && tt2.isHiphen()) 
                        tt2 = tt2.getNext();
                    if (tt2 instanceof com.pullenti.ner.TextToken) {
                        if (tt2.getLengthChar() == 1 && tt2.chars.isAllUpper()) 
                            break;
                        if (tt2.chars.isCapitalUpper()) {
                            boolean isSur = false;
                            String txt = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt2, com.pullenti.ner.TextToken.class))).term;
                            if (txt.endsWith("ОГО")) 
                                isSur = true;
                            else 
                                for(com.pullenti.morph.MorphBaseInfo wf : tt2.getMorph().getItems()) {
                                    if (wf._getClass().isProperSurname() && (((com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class))).isInDictionary()) {
                                        if (wf.getCase().isGenitive()) {
                                            isSur = true;
                                            break;
                                        }
                                    }
                                }
                            if (isSur) 
                                break;
                        }
                    }
                }
                return _new225(tok.getBeginToken(), tok.getEndToken(), StreetItemType.STDADJECTIVE, tok.termin, abr, tok.getMorph());
            case 0:
                if (tt.isValue(tok.termin.getCanonicText(), null) || tok.getEndToken().isValue(tok.termin.getCanonicText(), null) || tt.isValue("УЛ", null)) 
                    abr = false;
                else if (tok.getBeginToken() != tok.getEndToken() && ((tok.getBeginToken().getNext().isHiphen() || tok.getBeginToken().getNext().isCharOf("/\\")))) {
                }
                else if (!tt.chars.isAllLower() && tt.getLengthChar() == 1) 
                    break;
                else if (tt.getLengthChar() == 1) {
                    if (!tt.isWhitespaceBefore()) {
                        if (tt.getPrevious() != null && tt.getPrevious().isCharOf(",")) {
                        }
                        else 
                            return null;
                    }
                    if (tok.getEndToken().isChar('.')) {
                    }
                    else if (tok.getBeginToken() != tok.getEndToken() && tok.getBeginToken().getNext() != null && ((tok.getBeginToken().getNext().isHiphen() || tok.getBeginToken().getNext().isCharOf("/\\")))) {
                    }
                    else if (tok.getLengthChar() > 5) {
                    }
                    else if (tok.getBeginToken() == tok.getEndToken() && tt.isValue("Ш", null) && tt.chars.isAllLower()) {
                        if (prev != null && ((prev.typ == StreetItemType.NAME || prev.typ == StreetItemType.STDNAME || prev.typ == StreetItemType.STDPARTOFNAME))) {
                        }
                        else {
                            StreetItemToken sii = tryParse(tt.getNext(), null, false, null, false);
                            if (sii != null && (((sii.typ == StreetItemType.NAME || sii.typ == StreetItemType.STDNAME || sii.typ == StreetItemType.STDPARTOFNAME) || sii.typ == StreetItemType.AGE))) {
                            }
                            else 
                                return null;
                        }
                    }
                    else 
                        return null;
                }
                else if (((com.pullenti.n2j.Utils.stringsEq(tt.term, "КВ") || com.pullenti.n2j.Utils.stringsEq(tt.term, "КВАРТ"))) && !tok.getEndToken().isValue("Л", null)) {
                }
                if (!t.chars.isAllLower() && t.getMorph()._getClass().isProperSurname() && t.chars.isCyrillicLetter()) {
                    if ((((t.getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                        return null;
                }
                if (com.pullenti.n2j.Utils.stringsEq(tt.term, "ДОРОГОЙ")) 
                    return null;
                com.pullenti.ner.core.Termin alt = null;
                if (tok.getBeginToken().isValue("ПР", null) && ((tok.getBeginToken() == tok.getEndToken() || tok.getBeginToken().getNext().isChar('.')))) 
                    alt = m_Prospect;
                return _new227(tok.getBeginToken(), tok.getEndToken(), StreetItemType.NOUN, tok.termin, alt, abr, tok.getMorph(), (tok.termin.tag2 instanceof Integer ? (int)tok.termin.tag2 : 0));
            case 4:
                boolean isPostOff = com.pullenti.n2j.Utils.stringsEq(tok.termin.getCanonicText(), "ПОЧТОВОЕ ОТДЕЛЕНИЕ");
                if (tok.getBeginToken().chars.isAllLower() && !isPostOff && tok.getEndToken().chars.isAllLower()) 
                    return null;
                StreetItemToken sits = _new228(tok.getBeginToken(), tok.getEndToken(), StreetItemType.STDNAME, tok.getMorph(), tok.termin.getCanonicText());
                if (tok.getBeginToken() != tok.getEndToken() && !isPostOff) {
                    String vv = com.pullenti.ner.core.MiscHelper.getTextValue(tok.getBeginToken(), tok.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                    if (com.pullenti.n2j.Utils.stringsNe(vv, sits.value)) {
                        if (vv.length() < sits.value.length()) 
                            sits.altValue = vv;
                        else {
                            sits.altValue = sits.value;
                            sits.value = vv;
                        }
                    }
                    if (((m_StdOntMisc.tryParse(tok.getBeginToken(), com.pullenti.ner.core.TerminParseAttr.NO) != null || tok.getBeginToken().getMorphClassInDictionary().isProperName() || (tok.getBeginToken().getLengthChar() < 4))) && ((tok.getEndToken().getMorph()._getClass().isProperSurname() || !tok.getEndToken().getMorphClassInDictionary().isProperName()))) 
                        sits.altValue2 = com.pullenti.ner.core.MiscHelper.getTextValue(tok.getEndToken(), tok.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                    else if (((tok.getEndToken().getMorphClassInDictionary().isProperName() || m_StdOntMisc.tryParse(tok.getEndToken(), com.pullenti.ner.core.TerminParseAttr.NO) != null)) && ((tok.getBeginToken().getMorph()._getClass().isProperSurname() || !tok.getBeginToken().getMorphClassInDictionary().isProperName()))) 
                        sits.altValue2 = com.pullenti.ner.core.MiscHelper.getTextValue(tok.getBeginToken(), tok.getBeginToken(), com.pullenti.ner.core.GetTextAttr.NO);
                }
                return sits;
            case 5:
                if (prev != null && prev.typ == StreetItemType.NAME) {
                    String nam = (String)com.pullenti.n2j.Utils.notnull(prev.value, com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(prev, com.pullenti.ner.core.GetTextAttr.NO));
                    if (prev.altValue == null) 
                        prev.altValue = tok.termin.getCanonicText() + " " + nam;
                    else 
                        prev.altValue = tok.termin.getCanonicText() + " " + prev.altValue;
                    prev.setEndToken(tok.getEndToken());
                    prev.value = nam;
                    return tryParse(tok.getEndToken().getNext(), locStreets, recurse, prev, false);
                }
                StreetItemToken sit = tryParse(tok.getEndToken().getNext(), locStreets, false, null, false);
                if (sit == null || ((sit.typ != StreetItemType.NAME && sit.typ != StreetItemType.NOUN))) 
                    return null;
                if (sit.typ == StreetItemType.NOUN) {
                    if (tok.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                        return _new228(tok.getBeginToken(), tok.getEndToken(), StreetItemType.NAME, tok.getMorph(), com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(tok, com.pullenti.ner.core.GetTextAttr.NO));
                    else 
                        return _new230(tok.getBeginToken(), tok.getEndToken(), StreetItemType.NAME, tok.getMorph(), tok.termin);
                }
                if (sit.value != null) {
                    if (sit.altValue == null) 
                        sit.altValue = tok.termin.getCanonicText() + " " + sit.value;
                    else 
                        sit.value = tok.termin.getCanonicText() + " " + sit.value;
                }
                else if (sit.existStreet == null) {
                    sit.altValue = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(sit.getBeginToken(), com.pullenti.ner.TextToken.class))).term;
                    sit.value = tok.termin.getCanonicText() + " " + (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(sit.getBeginToken(), com.pullenti.ner.TextToken.class))).term;
                }
                sit.setBeginToken(tok.getBeginToken());
                return sit;
            case 1:
                if (tok.getBeginToken().chars.isAllLower()) {
                    if (prev != null && prev.typ == StreetItemType.STDADJECTIVE) {
                    }
                    else if (prev != null && prev.typ == StreetItemType.NOUN && AddressItemToken.checkHouseAfter(tok.getEndToken().getNext(), true, false)) {
                    }
                    else if (t.isValue("ПРОЕКТИРУЕМЫЙ", null) || t.isValue("МИРА", null)) {
                    }
                    else {
                        StreetItemToken nex = tryParse(tok.getEndToken().getNext(), null, true, null, false);
                        if (nex != null && nex.typ == StreetItemType.NOUN) {
                            com.pullenti.ner.Token tt2 = nex.getEndToken().getNext();
                            while(tt2 != null && tt2.isCharOf(",.")) {
                                tt2 = tt2.getNext();
                            }
                            if (tt2 == null || tt2.getWhitespacesBeforeCount() > 1) 
                                return null;
                            if (AddressItemToken.checkHouseAfter(tt2, false, true)) {
                            }
                            else 
                                return null;
                        }
                        else 
                            return null;
                    }
                }
                StreetItemToken sit0 = tryParse(tok.getBeginToken(), null, false, prev, true);
                if (sit0 != null && sit0.typ == StreetItemType.NAME && sit0.endChar > tok.endChar) {
                    sit0.isInDictionary = true;
                    return sit0;
                }
                StreetItemToken sit1 = _new231(tok.getBeginToken(), tok.getEndToken(), StreetItemType.NAME, tok.getMorph(), true);
                if ((!tok.isWhitespaceAfter() && tok.getEndToken().getNext() != null && tok.getEndToken().getNext().isHiphen()) && !tok.getEndToken().getNext().isWhitespaceAfter()) {
                    StreetItemToken sit2 = tryParse(tok.getEndToken().getNext().getNext(), locStreets, false, null, false);
                    if (sit2 != null && ((sit2.typ == StreetItemType.NAME || sit2.typ == StreetItemType.STDPARTOFNAME || sit2.typ == StreetItemType.STDNAME))) 
                        sit1.setEndToken(sit2.getEndToken());
                }
                return sit1;
            case 7:
                return _new232(tok.getBeginToken(), tok.getEndToken(), StreetItemType.FIX, tok.getMorph(), true, tok.termin);
            }
        }
        if (tt != null) {
            if ((prev != null && prev.typ == StreetItemType.NUMBER && prev.number != null) && prev.number.value == ((long)26)) {
                if (tt.isValue("БАКИНСКИЙ", null) || "БАКИНСК".startsWith((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term)) {
                    com.pullenti.ner.Token tt2 = tt;
                    if (tt2.getNext() != null && tt2.getNext().isChar('.')) 
                        tt2 = tt2.getNext();
                    if (tt2.getNext() instanceof com.pullenti.ner.TextToken) {
                        tt2 = tt2.getNext();
                        if (tt2.isValue("КОМИССАР", null) || tt2.isValue("КОММИССАР", null) || "КОМИС".startsWith((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt2, com.pullenti.ner.TextToken.class))).term)) {
                            if (tt2.getNext() != null && tt2.getNext().isChar('.')) 
                                tt2 = tt2.getNext();
                            StreetItemToken sit = _new233(tt, tt2, StreetItemType.STDNAME, true, "БАКИНСКИХ КОМИССАРОВ", tt2.getMorph());
                            return sit;
                        }
                    }
                }
            }
            if ((tt.getNext() != null && tt.getNext().isChar('.') && !tt.chars.isAllLower()) && (tt.getNext().getWhitespacesAfterCount() < 3) && (tt.getNext().getNext() instanceof com.pullenti.ner.TextToken)) {
                com.pullenti.ner.Token tt1 = tt.getNext().getNext();
                if (tt1 != null && tt1.isHiphen()) 
                    tt1 = tt1.getNext();
                if (tt.getLengthChar() == 1 && tt1.getLengthChar() == 1 && (tt1.getNext() instanceof com.pullenti.ner.TextToken)) {
                    if (tt1.isAnd() && tt1.getNext().chars.isAllUpper() && tt1.getNext().getLengthChar() == 1) 
                        tt1 = tt1.getNext();
                    if ((tt1.chars.isAllUpper() && tt1.getNext().isChar('.') && (tt1.getNext().getWhitespacesAfterCount() < 3)) && (tt1.getNext().getNext() instanceof com.pullenti.ner.TextToken)) 
                        tt1 = tt1.getNext().getNext();
                }
                StreetItemToken sit = StreetItemToken.tryParse(tt1, locStreets, false, null, false);
                if (sit != null && (tt1 instanceof com.pullenti.ner.TextToken)) {
                    String str = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt1, com.pullenti.ner.TextToken.class))).term;
                    boolean ok = false;
                    com.pullenti.morph.MorphClass cla = tt.getNext().getNext().getMorphClassInDictionary();
                    if (sit.isInDictionary) 
                        ok = true;
                    else if (sit._isSurname() || cla.isProperSurname()) 
                        ok = true;
                    else if (com.pullenti.morph.LanguageHelper.endsWith(str, "ОЙ") && ((cla.isProperSurname() || ((sit.typ == StreetItemType.NAME && sit.isInDictionary))))) 
                        ok = true;
                    else if (com.pullenti.morph.LanguageHelper.endsWithEx(str, "ГО", "ИХ", null, null)) 
                        ok = true;
                    else if (tt1.isWhitespaceBefore() && !tt1.getMorphClassInDictionary().isUndefined()) {
                    }
                    else if (prev != null && prev.typ == StreetItemType.NOUN && ((!prev.isAbridge || prev.getLengthChar() > 2))) 
                        ok = true;
                    else if ((prev != null && prev.typ == StreetItemType.NAME && sit.typ == StreetItemType.NOUN) && AddressItemToken.checkHouseAfter(sit.getEndToken().getNext(), false, true)) 
                        ok = true;
                    else if (sit.typ == StreetItemType.NAME && AddressItemToken.checkHouseAfter(sit.getEndToken().getNext(), false, true)) {
                        if (com.pullenti.ner.geo.internal.MiscLocationHelper.checkGeoObjectBefore(tt)) 
                            ok = true;
                    }
                    if (ok) {
                        sit.setBeginToken(tt);
                        sit.value = str;
                        sit.altValue = com.pullenti.ner.core.MiscHelper.getTextValue(tt, sit.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                        if (sit.altValue != null) 
                            sit.altValue = sit.altValue.replace("-", "");
                        return sit;
                    }
                }
            }
            if (tt.chars.isCyrillicLetter() && tt.getLengthChar() > 1 && !tt.getMorph()._getClass().isPreposition()) {
                if (tt.isValue("ГЕРОЙ", null) || tt.isValue("ЗАЩИТНИК", "ЗАХИСНИК")) {
                    if ((tt.getNext() instanceof com.pullenti.ner.ReferentToken) && (tt.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                        StreetItemToken re = _new234(tt, tt.getNext(), StreetItemType.STDPARTOFNAME, com.pullenti.ner.core.MiscHelper.getTextValue(tt, tt.getNext(), com.pullenti.ner.core.GetTextAttr.NO));
                        StreetItemToken sit = tryParse(tt.getNext().getNext(), locStreets, false, null, false);
                        if (sit == null || sit.typ != StreetItemType.NAME) {
                            boolean ok2 = false;
                            if (sit != null && ((sit.typ == StreetItemType.STDADJECTIVE || sit.typ == StreetItemType.NOUN))) 
                                ok2 = true;
                            else if (AddressItemToken.checkHouseAfter(tt.getNext().getNext(), false, true)) 
                                ok2 = true;
                            else if (tt.getNext().isNewlineAfter()) 
                                ok2 = true;
                            if (ok2) {
                                sit = _new235(tt, tt.getNext(), StreetItemType.NAME);
                                sit.value = com.pullenti.ner.core.MiscHelper.getTextValue(tt, tt.getNext(), com.pullenti.ner.core.GetTextAttr.NO);
                                return sit;
                            }
                            return re;
                        }
                        if (sit.value == null) 
                            sit.value = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(sit, com.pullenti.ner.core.GetTextAttr.NO);
                        if (sit.altValue == null) {
                            sit.altValue = sit.value;
                            sit.value = re.value + " " + sit.value;
                        }
                        else 
                            sit.value = re.value + " " + sit.value;
                        sit.setBeginToken(tt);
                        return sit;
                    }
                }
                com.pullenti.ner.NumberToken ani = com.pullenti.ner.core.NumberHelper.tryParseAnniversary(t);
                if (ani != null) 
                    return _new236(t, ani.getEndToken(), StreetItemType.AGE, ani, ((Long)ani.value).toString());
                boolean ok1 = false;
                if (!tt.chars.isAllLower()) {
                    AddressItemToken ait = AddressItemToken.tryParse(tt, null, false, true, null);
                    if (ait != null) {
                    }
                    else 
                        ok1 = true;
                }
                else if (prev != null && prev.typ == StreetItemType.NOUN) {
                    com.pullenti.ner.Token tt1 = prev.getBeginToken().getPrevious();
                    if (tt1 != null && tt1.isComma()) 
                        tt1 = tt1.getPrevious();
                    if (tt1 != null && (tt1.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) 
                        ok1 = true;
                    else if (AddressItemToken.checkHouseAfter(tt.getNext(), false, false)) {
                        if (!AddressItemToken.checkHouseAfter(tt, false, false)) 
                            ok1 = true;
                    }
                }
                else if (tt.getWhitespacesAfterCount() < 2) {
                    StreetItemToken nex = tryParse(tt.getNext(), null, true, null, false);
                    if (nex != null && nex.typ == StreetItemType.NOUN) {
                        if (com.pullenti.n2j.Utils.stringsEq(nex.termin.getCanonicText(), "ПЛОЩАДЬ")) {
                            if (tt.isValue("ОБЩИЙ", null)) 
                                return null;
                        }
                        com.pullenti.ner.Token tt1 = tt.getPrevious();
                        if (tt1 != null && tt1.isComma()) 
                            tt1 = tt1.getPrevious();
                        if (tt1 != null && (tt1.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) 
                            ok1 = true;
                        else if (AddressItemToken.checkHouseAfter(nex.getEndToken().getNext(), false, false)) 
                            ok1 = true;
                    }
                }
                if (ok1) {
                    com.pullenti.morph.MorphClass dc = tt.getMorphClassInDictionary();
                    if (dc.isAdverb()) {
                        if (!((dc.isProper()))) 
                            return null;
                    }
                    StreetItemToken res = _new237(tt, tt, StreetItemType.NAME, tt.getMorph());
                    if ((tt.getNext() != null && ((tt.getNext().isHiphen() || tt.getNext().isCharOf("\\/"))) && (tt.getNext().getNext() instanceof com.pullenti.ner.TextToken)) && !tt.isWhitespaceAfter() && !tt.getNext().isWhitespaceAfter()) {
                        boolean ok2 = AddressItemToken.checkHouseAfter(tt.getNext().getNext().getNext(), false, false) || tt.getNext().getNext().isNewlineAfter();
                        if (!ok2) {
                            StreetItemToken te2 = tryParse(tt.getNext().getNext().getNext(), null, false, null, false);
                            if (te2 != null && te2.typ == StreetItemType.NOUN) 
                                ok2 = true;
                        }
                        if (ok2) {
                            res.setEndToken(tt.getNext().getNext());
                            res.value = com.pullenti.ner.core.MiscHelper.getTextValue(res.getBeginToken(), res.getBeginToken(), com.pullenti.ner.core.GetTextAttr.NO) + " " + com.pullenti.ner.core.MiscHelper.getTextValue(res.getEndToken(), res.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                        }
                    }
                    else if ((tt.getWhitespacesAfterCount() < 2) && (tt.getNext() instanceof com.pullenti.ner.TextToken) && tt.getNext().chars.isLetter()) {
                        if (!AddressItemToken.checkHouseAfter(tt.getNext(), false, false) || tt.getNext().isNewlineAfter()) {
                            com.pullenti.ner.Token tt1 = tt.getNext();
                            boolean isPref = false;
                            if ((tt1 instanceof com.pullenti.ner.TextToken) && tt1.chars.isAllLower()) {
                                if (tt1.isValue("ДЕ", null) || tt1.isValue("ЛА", null)) {
                                    tt1 = tt1.getNext();
                                    isPref = true;
                                }
                            }
                            StreetItemToken nn = tryParse(tt1, locStreets, false, null, false);
                            if (nn == null || nn.typ == StreetItemType.NAME) {
                                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                                if (npt != null) {
                                    if (npt.getBeginToken() == npt.getEndToken()) 
                                        npt = null;
                                }
                                if (npt != null && ((npt.isNewlineAfter() || AddressItemToken.checkHouseAfter(npt.getEndToken().getNext(), false, false)))) {
                                    res.setEndToken(npt.getEndToken());
                                    if (npt.getMorph().getCase().isGenitive()) {
                                        res.value = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(npt, com.pullenti.ner.core.GetTextAttr.NO);
                                        res.altValue = npt.getNormalCaseText(new com.pullenti.morph.MorphClass(null), false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                                    }
                                    else {
                                        res.value = npt.getNormalCaseText(new com.pullenti.morph.MorphClass(null), false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                                        res.altValue = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(npt, com.pullenti.ner.core.GetTextAttr.NO);
                                    }
                                }
                                else if (AddressItemToken.checkHouseAfter(tt1.getNext(), false, false) && tt1.chars.isCyrillicLetter() == tt.chars.isCyrillicLetter() && (t.getWhitespacesAfterCount() < 2)) {
                                    if (tt1.getMorph()._getClass().isVerb() && !tt1.isValue("ДАЛИ", null)) {
                                    }
                                    else if (npt == null && !tt1.chars.isAllLower() && !isPref) {
                                    }
                                    else {
                                        res.setEndToken(tt1);
                                        res.value = com.pullenti.ner.core.MiscHelper.getTextValue(res.getBeginToken(), res.getBeginToken(), com.pullenti.ner.core.GetTextAttr.NO) + " " + com.pullenti.ner.core.MiscHelper.getTextValue(res.getEndToken(), res.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                                    }
                                }
                            }
                            else if (nn.typ == StreetItemType.NOUN) {
                                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                                if (npt != null && npt.getEndToken() == nn.getEndToken()) {
                                    res.value = com.pullenti.ner.core.MiscHelper.getTextValue(res.getBeginToken(), res.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                                    String var = com.pullenti.morph.Morphology.getWordform(res.value, com.pullenti.morph.MorphBaseInfo._new238(com.pullenti.morph.MorphCase.NOMINATIVE, com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphNumber.SINGULAR, npt.getMorph().getGender()));
                                    if (var != null && com.pullenti.n2j.Utils.stringsNe(var, res.value)) {
                                        res.altValue = res.value;
                                        res.value = var;
                                    }
                                }
                            }
                        }
                    }
                    return res;
                }
            }
            if (((tt.isValue("РЕКА", null) || tt.isValue("РІЧКА", null))) && tt.getNext() != null && tt.getNext().chars.isCapitalUpper()) 
                return _new239(tt, tt.getNext(), StreetItemType.NAME, tt.getMorph(), tt.getNext().getSourceText().toUpperCase());
            if (tt.isValue("№", null) || tt.isValue("НОМЕР", null) || tt.isValue("НОМ", null)) {
                com.pullenti.ner.Token tt1 = tt.getNext();
                if (tt1 != null && tt1.isChar('.')) 
                    tt1 = tt1.getNext();
                if (tt1 instanceof com.pullenti.ner.NumberToken) 
                    return _new220(tt, tt1, StreetItemType.NUMBER, (com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(tt1, com.pullenti.ner.NumberToken.class), true);
            }
            if (tt.isHiphen() && (tt.getNext() instanceof com.pullenti.ner.NumberToken)) {
                if (prev != null && prev.typ == StreetItemType.NOUN) {
                    if (com.pullenti.n2j.Utils.stringsEq(prev.termin.getCanonicText(), "МИКРОРАЙОН") || com.pullenti.morph.LanguageHelper.endsWith(prev.termin.getCanonicText(), "ГОРОДОК")) 
                        return _new220(tt, tt.getNext(), StreetItemType.NUMBER, (com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(tt.getNext(), com.pullenti.ner.NumberToken.class), true);
                }
            }
        }
        com.pullenti.ner.Referent r = (t == null ? null : t.getReferent());
        if (r instanceof com.pullenti.ner.geo.GeoReferent) {
            com.pullenti.ner.geo.GeoReferent geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(r, com.pullenti.ner.geo.GeoReferent.class);
            if (prev != null && prev.typ == StreetItemType.NOUN) {
                if (AddressItemToken.checkHouseAfter(t.getNext(), false, false)) 
                    return _new234(t, t, StreetItemType.NAME, com.pullenti.ner.core.MiscHelper.getTextValue(t, t, com.pullenti.ner.core.GetTextAttr.NO));
            }
        }
        if (((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isCapitalUpper() && tt.chars.isLatinLetter()) && (tt.getWhitespacesAfterCount() < 2)) {
            if (com.pullenti.ner.core.MiscHelper.isEngArticle(tt)) 
                return null;
            com.pullenti.ner.Token tt2 = tt.getNext();
            if (com.pullenti.ner.core.MiscHelper.isEngAdjSuffix(tt2)) 
                tt2 = tt2.getNext().getNext();
            com.pullenti.ner.core.TerminToken tok1 = m_Ontology.tryParse(tt2, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok1 != null) 
                return _new228(tt, tt2.getPrevious(), StreetItemType.NAME, tt.getMorph(), (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term);
        }
        return null;
    }

    public static java.util.ArrayList<StreetItemToken> tryParseSpec(com.pullenti.ner.Token t, StreetItemToken prev) {
        if (t == null) 
            return null;
        java.util.ArrayList<StreetItemToken> res = null;
        StreetItemToken sit;
        if (t.getReferent() instanceof com.pullenti.ner.date.DateReferent) {
            com.pullenti.ner.date.DateReferent dr = (com.pullenti.ner.date.DateReferent)com.pullenti.n2j.Utils.cast(t.getReferent(), com.pullenti.ner.date.DateReferent.class);
            if (!(((((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class))).getBeginToken() instanceof com.pullenti.ner.NumberToken))) 
                return null;
            if (dr.getYear() == 0 && dr.getDay() > 0 && dr.getMonth() > 0) {
                res = new java.util.ArrayList< >();
                res.add(_new218(t, t, StreetItemType.NUMBER, new com.pullenti.ner.NumberToken(t, t, (long)dr.getDay(), com.pullenti.ner.NumberSpellingType.DIGIT, null)));
                String tmp = dr.toString(false, t.getMorph().getLanguage(), 0);
                int i = tmp.indexOf(' ');
                res.add((sit = _new234(t, t, StreetItemType.STDNAME, tmp.substring(i + 1).toUpperCase())));
                sit.chars.setCapitalUpper(true);
                return res;
            }
            if (dr.getYear() > 0 && dr.getMonth() == 0) {
                res = new java.util.ArrayList< >();
                res.add(_new218(t, t, StreetItemType.NUMBER, new com.pullenti.ner.NumberToken(t, t, (long)dr.getYear(), com.pullenti.ner.NumberSpellingType.DIGIT, null)));
                res.add((sit = _new234(t, t, StreetItemType.STDNAME, (t.getMorph().getLanguage().isUa() ? "РОКУ" : "ГОДА"))));
                sit.chars.setCapitalUpper(true);
                return res;
            }
            return null;
        }
        if (prev != null && prev.typ == StreetItemType.AGE) {
            res = new java.util.ArrayList< >();
            if (t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                res.add((sit = _new248(t, t, StreetItemType.NAME, t.getSourceText().toUpperCase(), t.getReferent().toString(true, t.kit.baseLanguage, 0).toUpperCase())));
            else if (t.isValue("ГОРОД", null) || t.isValue("МІСТО", null)) 
                res.add((sit = _new234(t, t, StreetItemType.NAME, "ГОРОДА")));
            else 
                return null;
            return res;
        }
        if (prev != null && prev.typ == StreetItemType.NOUN) {
            com.pullenti.ner.NumberToken num = com.pullenti.ner.core.NumberHelper.tryParseRoman(t);
            if (num != null) {
                res = new java.util.ArrayList< >();
                res.add((sit = _new218(num.getBeginToken(), num.getEndToken(), StreetItemType.NUMBER, num)));
                t = num.getEndToken().getNext();
                if ((num.typ == com.pullenti.ner.NumberSpellingType.DIGIT && (t instanceof com.pullenti.ner.TextToken) && !t.isWhitespaceBefore()) && t.getLengthChar() == 1) {
                    sit.setEndToken(t);
                    sit.value = ((Long)num.value).toString() + (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
                    sit.number = null;
                }
                return res;
            }
        }
        return null;
    }

    public static java.util.ArrayList<StreetItemToken> tryParseList(com.pullenti.ner.Token t, com.pullenti.ner.core.IntOntologyCollection locStreets, int maxCount) {
        java.util.ArrayList<StreetItemToken> res = null;
        StreetItemToken sit = tryParse(t, locStreets, false, null, false);
        if (sit != null) {
            res = new java.util.ArrayList< >();
            res.add(sit);
            t = sit.getEndToken().getNext();
        }
        else {
            res = tryParseSpec(t, null);
            if (res == null) 
                return null;
            sit = res.get(res.size() - 1);
            t = sit.getEndToken().getNext();
            StreetItemToken sit2 = tryParse(t, locStreets, false, null, false);
            if (sit2 != null && sit2.typ == StreetItemType.NOUN) {
            }
            else if (AddressItemToken.checkHouseAfter(t, false, true)) {
            }
            else 
                return null;
        }
        for(; t != null; t = t.getNext()) {
            if (maxCount > 0 && res.size() >= maxCount) 
                break;
            if (t.isNewlineBefore()) {
                if (t.getNewlinesBeforeCount() > 1) 
                    break;
                if (((t.getWhitespacesAfterCount() < 15) && sit != null && sit.typ == StreetItemType.NOUN) && t.chars.isCapitalUpper()) {
                }
                else 
                    break;
            }
            if (t.isHiphen() && sit != null && ((sit.typ == StreetItemType.NAME || ((sit.typ == StreetItemType.STDADJECTIVE && !sit.isAbridge))))) {
                StreetItemToken sit1 = tryParse(t.getNext(), locStreets, false, sit, false);
                if (sit1 == null) 
                    break;
                if (sit1.typ == StreetItemType.NUMBER) {
                    com.pullenti.ner.Token tt = sit1.getEndToken().getNext();
                    if (tt != null && tt.isComma()) 
                        tt = tt.getNext();
                    boolean ok = false;
                    AddressItemToken ait = AddressItemToken.tryParse(tt, locStreets, false, true, null);
                    if (ait != null) {
                        if (ait.typ == AddressItemToken.ItemType.HOUSE) 
                            ok = true;
                    }
                    if (!ok) {
                        if (res.size() == 2 && res.get(0).typ == StreetItemType.NOUN) {
                            if (com.pullenti.n2j.Utils.stringsEq(res.get(0).termin.getCanonicText(), "МИКРОРАЙОН")) 
                                ok = true;
                        }
                    }
                    if (ok) {
                        sit = sit1;
                        res.add(sit);
                        t = sit.getEndToken();
                        sit.numberHasPrefix = true;
                        continue;
                    }
                }
                if (sit1.typ != StreetItemType.NAME && sit1.typ != StreetItemType.NAME) 
                    break;
                if (t.isWhitespaceBefore() && t.isWhitespaceAfter()) 
                    break;
                if (res.get(0).getBeginToken().getPrevious() != null) {
                    AddressItemToken aaa = AddressItemToken.tryParse(res.get(0).getBeginToken().getPrevious(), null, false, true, null);
                    if (aaa != null && aaa.typ == AddressItemToken.ItemType.DETAIL && aaa.detailType == com.pullenti.ner.address.AddressDetailType.CROSS) 
                        break;
                }
                sit = sit1;
                res.add(sit);
                t = sit.getEndToken();
                continue;
            }
            else if (t.isHiphen() && sit != null && sit.typ == StreetItemType.NUMBER) {
                StreetItemToken sit1 = tryParse(t.getNext(), locStreets, false, null, false);
                if (sit1 != null && ((sit1.typ == StreetItemType.STDADJECTIVE || sit1.typ == StreetItemType.STDNAME || sit1.typ == StreetItemType.NAME))) {
                    sit.numberHasPrefix = true;
                    sit = sit1;
                    res.add(sit);
                    t = sit.getEndToken();
                    continue;
                }
            }
            if (t.isChar('.') && sit != null && sit.typ == StreetItemType.NOUN) {
                if (t.getWhitespacesAfterCount() > 1) 
                    break;
                sit = tryParse(t.getNext(), locStreets, false, null, false);
                if (sit == null) 
                    break;
                if (sit.typ == StreetItemType.NUMBER || sit.typ == StreetItemType.STDADJECTIVE) {
                    StreetItemToken sit1 = tryParse(sit.getEndToken().getNext(), null, false, null, false);
                    if (sit1 != null && ((sit1.typ == StreetItemType.STDADJECTIVE || sit1.typ == StreetItemType.STDNAME || sit1.typ == StreetItemType.NAME))) {
                    }
                    else 
                        break;
                }
                else if (sit.typ != StreetItemType.NAME && sit.typ != StreetItemType.STDNAME && sit.typ != StreetItemType.AGE) 
                    break;
                if (t.getPrevious().getMorphClassInDictionary().isNoun()) {
                    if (!sit.isInDictionary) {
                        com.pullenti.ner.Token tt = sit.getEndToken().getNext();
                        boolean hasHouse = false;
                        for(; tt != null; tt = tt.getNext()) {
                            if (tt.isNewlineBefore()) 
                                break;
                            if (tt.isComma()) 
                                continue;
                            AddressItemToken ai = AddressItemToken.tryParse(tt, null, false, true, null);
                            if (ai != null && ((ai.typ == AddressItemToken.ItemType.HOUSE || ai.typ == AddressItemToken.ItemType.BUILDING || ai.typ == AddressItemToken.ItemType.CORPUS))) {
                                hasHouse = true;
                                break;
                            }
                            StreetItemToken vv = tryParse(tt, null, false, null, false);
                            if (vv == null || vv.typ == StreetItemType.NOUN) 
                                break;
                            tt = vv.getEndToken();
                        }
                        if (!hasHouse) 
                            break;
                    }
                    if (t.getPrevious().getPrevious() != null) {
                        com.pullenti.ner.core.NounPhraseToken npt11 = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getPrevious().getPrevious(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                        if (npt11 != null && npt11.getEndToken() == t.getPrevious()) 
                            break;
                    }
                }
                res.add(sit);
            }
            else {
                sit = tryParse(t, locStreets, false, res.get(res.size() - 1), false);
                if (sit == null) {
                    java.util.ArrayList<StreetItemToken> spli = tryParseSpec(t, res.get(res.size() - 1));
                    if (spli != null && spli.size() > 0) {
                        res.addAll(spli);
                        t = spli.get(spli.size() - 1).getEndToken();
                        continue;
                    }
                    if (((t instanceof com.pullenti.ner.TextToken) && ((res.size() == 2 || res.size() == 3)) && res.get(0).typ == StreetItemType.NOUN) && res.get(1).typ == StreetItemType.NUMBER && (((com.pullenti.n2j.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term, "ГОДА") || com.pullenti.n2j.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term, "МАЯ") || com.pullenti.n2j.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term, "МАРТА")) || com.pullenti.n2j.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term, "СЪЕЗДА")))) {
                        res.add((sit = _new234(t, t, StreetItemType.STDNAME, (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term)));
                        continue;
                    }
                    sit = res.get(res.size() - 1);
                    if (t == null) 
                        break;
                    if (sit.typ == StreetItemType.NOUN && ((com.pullenti.n2j.Utils.stringsEq(sit.termin.getCanonicText(), "МИКРОРАЙОН") || com.pullenti.n2j.Utils.stringsEq(sit.termin.getCanonicText(), "МІКРОРАЙОН"))) && (t.getWhitespacesBeforeCount() < 2)) {
                        com.pullenti.ner.Token tt1 = t;
                        if (tt1.isHiphen() && tt1.getNext() != null) 
                            tt1 = tt1.getNext();
                        if (com.pullenti.ner.core.BracketHelper.isBracket(tt1, true) && tt1.getNext() != null) 
                            tt1 = tt1.getNext();
                        com.pullenti.ner.Token tt2 = tt1.getNext();
                        boolean br = false;
                        if (com.pullenti.ner.core.BracketHelper.isBracket(tt2, true)) {
                            tt2 = tt2.getNext();
                            br = true;
                        }
                        if (((tt1 instanceof com.pullenti.ner.TextToken) && tt1.getLengthChar() == 1 && tt1.chars.isLetter()) && ((AddressItemToken.checkHouseAfter(tt2, false, true) || tt2 == null))) {
                            sit = _new234(t, (br ? tt1.getNext() : tt1), StreetItemType.NAME, (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt1, com.pullenti.ner.TextToken.class))).term);
                            char ch1 = AddressItemToken.correctChar(sit.value.charAt(0));
                            if (((int)ch1) != 0 && ch1 != sit.value.charAt(0)) 
                                sit.altValue = String.valueOf(ch1);
                            res.add(sit);
                            break;
                        }
                    }
                    if (t.isComma() && (((sit.typ == StreetItemType.NAME || sit.typ == StreetItemType.STDNAME || sit.typ == StreetItemType.STDPARTOFNAME) || sit.typ == StreetItemType.STDADJECTIVE || ((sit.typ == StreetItemType.NUMBER && res.size() > 1 && (((res.get(res.size() - 2).typ == StreetItemType.NAME || res.get(res.size() - 2).typ == StreetItemType.STDNAME || res.get(res.size() - 2).typ == StreetItemType.STDADJECTIVE) || res.get(res.size() - 2).typ == StreetItemType.STDPARTOFNAME))))))) {
                        sit = tryParse(t.getNext(), null, false, null, false);
                        if (sit != null && sit.typ == StreetItemType.NOUN) {
                            com.pullenti.ner.Token ttt = sit.getEndToken().getNext();
                            if (ttt != null && ttt.isComma()) 
                                ttt = ttt.getNext();
                            AddressItemToken add = AddressItemToken.tryParse(ttt, null, false, true, null);
                            if (add != null && ((add.typ == AddressItemToken.ItemType.HOUSE || add.typ == AddressItemToken.ItemType.CORPUS || add.typ == AddressItemToken.ItemType.BUILDING))) {
                                res.add(sit);
                                t = sit.getEndToken();
                                continue;
                            }
                        }
                    }
                    if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
                        StreetItemToken sit1 = res.get(res.size() - 1);
                        if (sit1.typ == StreetItemType.NOUN && ((sit1.nounIsDoubtCoef == 0 || (((t.getNext() instanceof com.pullenti.ner.TextToken) && !t.getNext().chars.isAllLower()))))) {
                            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                            if (br != null && (br.getLengthChar() < 50)) {
                                StreetItemToken sit2 = tryParse(t.getNext(), locStreets, false, null, false);
                                if (sit2 != null && sit2.getEndToken().getNext() == br.getEndToken()) {
                                    if (sit2.value == null && sit2.typ == StreetItemType.NAME) 
                                        sit2.value = com.pullenti.ner.core.MiscHelper.getTextValue(sit2.getBeginToken(), sit2.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                                    sit2.setBeginToken(t);
                                    sit2.isInBrackets = true;
                                    t = sit2.setEndToken(br.getEndToken());
                                    res.add(sit2);
                                    continue;
                                }
                                res.add(_new253(t, br.getEndToken(), StreetItemType.NAME, com.pullenti.ner.core.MiscHelper.getTextValue(t, br.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO), true));
                                t = br.getEndToken();
                                continue;
                            }
                        }
                    }
                    if (t.isHiphen() && (t.getNext() instanceof com.pullenti.ner.NumberToken)) {
                        sit = res.get(res.size() - 1);
                        if (sit.typ == StreetItemType.NOUN && (((com.pullenti.n2j.Utils.stringsEq(sit.termin.getCanonicText(), "КВАРТАЛ") || com.pullenti.n2j.Utils.stringsEq(sit.termin.getCanonicText(), "МИКРОРАЙОН") || com.pullenti.n2j.Utils.stringsEq(sit.termin.getCanonicText(), "ГОРОДОК")) || com.pullenti.n2j.Utils.stringsEq(sit.termin.getCanonicText(), "МІКРОРАЙОН")))) {
                            sit = _new220(t, t.getNext(), StreetItemType.NUMBER, (com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class), true);
                            res.add(sit);
                            t = t.getNext();
                            continue;
                        }
                    }
                    break;
                }
                res.add(sit);
                if (sit.typ == StreetItemType.NAME) {
                    int cou = 0;
                    int jj;
                    for(jj = res.size() - 1; jj >= 0; jj--) {
                        if (sit.typ == StreetItemType.NAME) 
                            cou++;
                        else 
                            break;
                    }
                    if (cou > 4) {
                        if (jj < 0) 
                            return null;
                        for(int indRemoveRange = jj + res.size() - jj - 1, indMinIndex = jj; indRemoveRange >= indMinIndex; indRemoveRange--) res.remove(indRemoveRange);
                        break;
                    }
                }
            }
            t = sit.getEndToken();
        }
        for(int i = 0; i < (res.size() - 1); i++) {
            if (res.get(i).typ == StreetItemType.NAME && res.get(i + 1).typ == StreetItemType.NAME && (res.get(i).getWhitespacesAfterCount() < 3)) {
                boolean isProp = false;
                boolean isPers = false;
                if (res.get(i).getBeginToken().getMorph()._getClass().isNoun()) {
                    com.pullenti.ner.ReferentToken rt = res.get(i).kit.processReferent("PERSON", res.get(i).getBeginToken());
                    if (rt != null) {
                        if (com.pullenti.n2j.Utils.stringsEq(rt.referent.getTypeName(), "PERSONPROPERTY")) 
                            isProp = true;
                        else if (rt.getEndToken() == res.get(i + 1).getEndToken()) 
                            isPers = true;
                    }
                }
                if ((i == 0 && ((!isProp && !isPers)) && ((i + 2) < res.size())) && res.get(i + 2).typ == StreetItemType.NOUN && !res.get(i).getBeginToken().getMorph()._getClass().isAdjective()) {
                    if (com.pullenti.ner.geo.internal.MiscLocationHelper.checkGeoObjectBefore(res.get(0).getBeginToken()) && res.get(0).getEndToken().getNext() == res.get(1).getBeginToken() && (res.get(0).getWhitespacesAfterCount() < 2)) {
                    }
                    else {
                        res.remove(i);
                        i--;
                        continue;
                    }
                }
                if (res.get(i).getMorph()._getClass().isAdjective() && res.get(i + 1).getMorph()._getClass().isAdjective()) {
                    if (res.get(i).getEndToken().getNext().isHiphen()) {
                    }
                    else if (i == 1 && res.get(0).typ == StreetItemType.NOUN && res.size() == 3) {
                    }
                    else if (i == 0 && res.size() == 3 && res.get(2).typ == StreetItemType.NOUN) {
                    }
                    else 
                        continue;
                }
                res.get(i).value = com.pullenti.ner.core.MiscHelper.getTextValue(res.get(i).getBeginToken(), res.get(i + 1).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                if ((res.get(i).value.indexOf("-") >= 0)) 
                    res.get(i).value = res.get(i).value.replace('-', ' ');
                if (!res.get(i + 1).getBeginToken().getPrevious().isHiphen() && ((!res.get(i).getBeginToken().getMorph()._getClass().isAdjective() || isProp || isPers))) {
                    if (isPers && res.get(i + 1).getEndToken().getMorphClassInDictionary().isProperName()) 
                        res.get(i).altValue = com.pullenti.ner.core.MiscHelper.getTextValue(res.get(i).getBeginToken(), res.get(i).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                    else 
                        res.get(i).altValue = com.pullenti.ner.core.MiscHelper.getTextValue(res.get(i + 1).getBeginToken(), res.get(i + 1).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                    if ((res.get(i).altValue.indexOf("-") >= 0)) 
                        res.get(i).altValue = res.get(i).altValue.replace('-', ' ');
                }
                res.get(i).setEndToken(res.get(i + 1).getEndToken());
                res.get(i).existStreet = null;
                res.get(i).isInDictionary = res.get(i + 1).isInDictionary || res.get(i).isInDictionary;
                res.remove(i + 1);
                i--;
            }
        }
        for(int i = 0; i < (res.size() - 1); i++) {
            if (res.get(i).typ == StreetItemType.STDADJECTIVE && res.get(i).getEndToken().isChar('.') && res.get(i + 1)._isSurname()) {
                res.get(i + 1).value = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(res.get(i + 1).getBeginToken(), com.pullenti.ner.TextToken.class))).term;
                res.get(i + 1).altValue = com.pullenti.ner.core.MiscHelper.getTextValue(res.get(i).getBeginToken(), res.get(i + 1).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                res.get(i + 1).setBeginToken(res.get(i).getBeginToken());
                res.remove(i);
                break;
            }
        }
        for(int i = 0; i < (res.size() - 1); i++) {
            if ((res.get(i + 1).typ == StreetItemType.STDADJECTIVE && res.get(i + 1).getEndToken().isChar('.') && res.get(i + 1).getBeginToken().getLengthChar() == 1) && !res.get(i).getBeginToken().chars.isAllLower()) {
                if (res.get(i)._isSurname()) {
                    if (i == (res.size() - 2) || res.get(i + 2).typ != StreetItemType.NOUN) {
                        res.get(i).setEndToken(res.get(i + 1).getEndToken());
                        res.remove(i + 1);
                        break;
                    }
                }
            }
        }
        for(int i = 0; i < (res.size() - 1); i++) {
            if (res.get(i).typ == StreetItemType.NAME || res.get(i).typ == StreetItemType.STDNAME || res.get(i).typ == StreetItemType.STDADJECTIVE) {
                if (res.get(i + 1).typ == StreetItemType.NOUN && !res.get(i + 1).isAbridge) {
                    int i0 = -1;
                    if (i == 1 && res.get(0).typ == StreetItemType.NOUN && res.size() == 3) 
                        i0 = 0;
                    else if (i == 0 && res.size() == 3 && res.get(2).typ == StreetItemType.NOUN) 
                        i0 = 2;
                    if (i0 < 0) 
                        continue;
                    if (res.get(i0).termin == res.get(i + 1).termin) 
                        continue;
                    res.get(i).altValue = (String)com.pullenti.n2j.Utils.notnull(res.get(i).value, com.pullenti.ner.core.MiscHelper.getTextValue(res.get(i).getBeginToken(), res.get(i).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO));
                    if (res.get(i).typ == StreetItemType.STDADJECTIVE) {
                        java.util.ArrayList<String> adjs = com.pullenti.ner.geo.internal.MiscLocationHelper.getStdAdjFull(res.get(i).getBeginToken(), res.get(i + 1).getMorph().getGender(), res.get(i + 1).getMorph().getNumber(), true);
                        if (adjs.size() > 0) 
                            res.get(i).altValue = adjs.get(0);
                    }
                    res.get(i).value = res.get(i).altValue + " " + res.get(i + 1).termin.getCanonicText();
                    res.get(i).typ = StreetItemType.STDNAME;
                    res.get(i0).altTermin = res.get(i + 1).termin;
                    res.get(i).setEndToken(res.get(i + 1).getEndToken());
                    res.remove(i + 1);
                    i--;
                }
            }
        }
        if ((res.size() >= 3 && res.get(0).typ == StreetItemType.NOUN && com.pullenti.n2j.Utils.stringsEq(res.get(0).termin.getCanonicText(), "КВАРТАЛ")) && ((res.get(1).typ == StreetItemType.NAME || res.get(1).typ == StreetItemType.STDNAME)) && res.get(2).typ == StreetItemType.NOUN) {
            if (res.size() == 3 || res.get(3).typ == StreetItemType.NUMBER) {
                res.get(1).value = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(res.get(1), com.pullenti.ner.core.GetTextAttr.NO) + " " + res.get(2).termin.getCanonicText();
                res.get(1).setEndToken(res.get(2).getEndToken());
                res.remove(2);
            }
        }
        if ((res.size() >= 3 && res.get(0).typ == StreetItemType.NOUN && com.pullenti.n2j.Utils.stringsEq(res.get(0).termin.getCanonicText(), "КВАРТАЛ")) && ((res.get(2).typ == StreetItemType.NAME || res.get(2).typ == StreetItemType.STDNAME)) && res.get(1).typ == StreetItemType.NOUN) {
            if (res.size() == 3 || res.get(3).typ == StreetItemType.NUMBER) {
                res.get(1).value = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(res.get(2), com.pullenti.ner.core.GetTextAttr.NO) + " " + res.get(1).termin.getCanonicText();
                res.get(1).setEndToken(res.get(2).getEndToken());
                res.get(1).typ = StreetItemType.NAME;
                res.remove(2);
            }
        }
        if (res.size() >= 3 && res.get(0).typ == StreetItemType.NUMBER && res.get(1).typ == StreetItemType.NOUN) {
            com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(res.get(0).getBeginToken(), com.pullenti.ner.NumberToken.class);
            if (nt != null && nt.typ == com.pullenti.ner.NumberSpellingType.DIGIT && nt.getMorph()._getClass().isUndefined()) 
                return null;
        }
        int ii0 = -1;
        int ii1 = -1;
        if (res.get(0).typ == StreetItemType.NOUN && res.get(0).isRoad()) {
            ii0 = (ii1 = 0);
            if (((ii0 + 1) < res.size()) && res.get(ii0 + 1).typ == StreetItemType.NUMBER && res.get(ii0 + 1).isNumberKm) 
                ii0++;
        }
        else if ((res.size() > 1 && res.get(0).typ == StreetItemType.NUMBER && res.get(0).isNumberKm) && res.get(1).typ == StreetItemType.NOUN && res.get(1).isRoad()) 
            ii0 = (ii1 = 1);
        if (ii0 >= 0) {
            if (res.size() == (ii0 + 1)) {
                com.pullenti.ner.Token tt = res.get(ii0).getEndToken().getNext();
                StreetItemToken num = _tryAttachRoadNum(tt);
                if (num != null) {
                    res.add(num);
                    tt = num.getEndToken().getNext();
                    res.get(0).isAbridge = false;
                }
                if (tt != null && (tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                    com.pullenti.ner.geo.GeoReferent g1 = (com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(tt.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                    tt = tt.getNext();
                    if (tt != null && tt.isHiphen()) 
                        tt = tt.getNext();
                    com.pullenti.ner.geo.GeoReferent g2 = (tt == null ? null : (com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(tt.getReferent(), com.pullenti.ner.geo.GeoReferent.class));
                    if (g2 != null) {
                        if (g1.isCity() && g2.isCity()) {
                            StreetItemToken nam = _new235(res.get(0).getEndToken().getNext(), tt, StreetItemType.NAME);
                            nam.value = (g1.toString(true, tt.kit.baseLanguage, 0) + " - " + g2.toString(true, tt.kit.baseLanguage, 0)).toUpperCase();
                            nam.altValue = (g2.toString(true, tt.kit.baseLanguage, 0) + " - " + g1.toString(true, tt.kit.baseLanguage, 0)).toUpperCase();
                            res.add(nam);
                        }
                    }
                }
                else if (com.pullenti.ner.core.BracketHelper.isBracket(tt, false)) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null) {
                        StreetItemToken nam = _new256(tt, br.getEndToken(), StreetItemType.NAME, true);
                        nam.value = com.pullenti.ner.core.MiscHelper.getTextValue(tt.getNext(), br.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                        res.add(nam);
                    }
                }
            }
            else if ((res.size() == (ii0 + 2) && res.get(ii0 + 1).typ == StreetItemType.NAME && res.get(ii0 + 1).getEndToken().getNext() != null) && res.get(ii0 + 1).getEndToken().getNext().isHiphen()) {
                com.pullenti.ner.Token tt = res.get(ii0 + 1).getEndToken().getNext().getNext();
                com.pullenti.ner.geo.GeoReferent g2 = (tt == null ? null : (com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(tt.getReferent(), com.pullenti.ner.geo.GeoReferent.class));
                com.pullenti.ner.Token te = null;
                String name2 = null;
                if (g2 == null && tt != null) {
                    com.pullenti.ner.ReferentToken rt = tt.kit.processReferent("GEO", tt);
                    if (rt != null) {
                        te = rt.getEndToken();
                        name2 = rt.referent.toString(true, te.kit.baseLanguage, 0);
                    }
                    else {
                        java.util.ArrayList<com.pullenti.ner.geo.internal.CityItemToken> cits2 = com.pullenti.ner.geo.internal.CityItemToken.tryParseList(tt, null, 2);
                        if (cits2 != null) {
                            if (cits2.size() == 1 && cits2.get(0).typ == com.pullenti.ner.geo.internal.CityItemToken.ItemType.PROPERNAME) {
                                name2 = cits2.get(0).value;
                                te = cits2.get(0).getEndToken();
                            }
                        }
                    }
                }
                else {
                    te = tt;
                    name2 = g2.toString(true, te.kit.baseLanguage, 0);
                }
                if (((g2 != null && g2.isCity())) || ((g2 == null && name2 != null))) {
                    res.get(ii0 + 1).altValue = (name2 + " - " + ((String)com.pullenti.n2j.Utils.notnull(res.get(ii0 + 1).value, res.get(ii0 + 1).getSourceText()))).toUpperCase();
                    res.get(ii0 + 1).value = (((String)com.pullenti.n2j.Utils.notnull(res.get(ii0 + 1).value, res.get(ii0 + 1).getSourceText())) + " - " + name2).toUpperCase();
                    res.get(ii0 + 1).setEndToken(te);
                }
            }
            StreetItemToken nn = _tryAttachRoadNum(res.get(res.size() - 1).getEndToken().getNext());
            if (nn != null) {
                res.add(nn);
                res.get(ii1).isAbridge = false;
            }
            if (res.size() > (ii0 + 1) && res.get(ii0 + 1).typ == StreetItemType.NAME && com.pullenti.n2j.Utils.stringsEq(res.get(ii1).termin.getCanonicText(), "АВТОДОРОГА")) {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(res.get(ii0 + 1).getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                if (npt != null && npt.adjectives.size() > 0) 
                    return null;
            }
        }
        if (res.size() > 0) {
            StreetItemToken it = res.get(res.size() - 1);
            StreetItemToken it0 = (res.size() > 1 ? res.get(res.size() - 2) : null);
            if (it.typ == StreetItemType.NUMBER && !it.numberHasPrefix) {
                if (it.getBeginToken() instanceof com.pullenti.ner.NumberToken) {
                    if (!it.getBeginToken().getMorph()._getClass().isAdjective() || it.getBeginToken().getMorph()._getClass().isNoun()) {
                        if (AddressItemToken.checkHouseAfter(it.getEndToken().getNext(), false, true)) 
                            it.numberHasPrefix = true;
                        else if (it0 != null && it0.typ == StreetItemType.NOUN && (((com.pullenti.n2j.Utils.stringsEq(it0.termin.getCanonicText(), "МИКРОРАЙОН") || com.pullenti.n2j.Utils.stringsEq(it0.termin.getCanonicText(), "МІКРОРАЙОН") || com.pullenti.n2j.Utils.stringsEq(it0.termin.getCanonicText(), "КВАРТАЛ")) || com.pullenti.n2j.Utils.stringsEq(it0.termin.getCanonicText(), "ГОРОДОК")))) {
                            AddressItemToken ait = AddressItemToken.tryParse(it.getBeginToken(), locStreets, false, true, null);
                            if (ait != null && ait.typ == AddressItemToken.ItemType.NUMBER && ait.endChar > it.endChar) {
                                it.number = null;
                                it.value = ait.value;
                                it.setEndToken(ait.getEndToken());
                                it.typ = StreetItemType.NAME;
                            }
                        }
                        else if (it0 != null && it0.termin != null && com.pullenti.n2j.Utils.stringsEq(it0.termin.getCanonicText(), "ПОЧТОВОЕ ОТДЕЛЕНИЕ")) 
                            it.numberHasPrefix = true;
                        else if (res.size() == 2 && res.get(0).typ == StreetItemType.NOUN && (res.get(0).getWhitespacesAfterCount() < 2)) {
                        }
                        else 
                            res.remove(res.size() - 1);
                    }
                    else 
                        it.numberHasPrefix = true;
                }
            }
        }
        if (res.size() == 0) 
            return null;
        for(int i = 0; i < res.size(); i++) {
            if ((res.get(i).typ == StreetItemType.NOUN && res.get(i).chars.isCapitalUpper() && (((com.pullenti.n2j.Utils.stringsEq(res.get(i).termin.getCanonicText(), "НАБЕРЕЖНАЯ") || com.pullenti.n2j.Utils.stringsEq(res.get(i).termin.getCanonicText(), "МИКРОРАЙОН") || com.pullenti.n2j.Utils.stringsEq(res.get(i).termin.getCanonicText(), "НАБЕРЕЖНА")) || com.pullenti.n2j.Utils.stringsEq(res.get(i).termin.getCanonicText(), "МІКРОРАЙОН") || com.pullenti.n2j.Utils.stringsEq(res.get(i).termin.getCanonicText(), "ГОРОДОК")))) && res.get(i).getBeginToken().isValue(res.get(i).termin.getCanonicText(), null)) {
                boolean ok = false;
                if (i > 0 && ((res.get(i - 1).typ == StreetItemType.NOUN || res.get(i - 1).typ == StreetItemType.STDADJECTIVE))) 
                    ok = true;
                else if (i > 1 && ((res.get(i - 1).typ == StreetItemType.STDADJECTIVE || res.get(i - 1).typ == StreetItemType.NUMBER)) && res.get(i - 2).typ == StreetItemType.NOUN) 
                    ok = true;
                if (ok) {
                    res.get(i).termin = null;
                    res.get(i).typ = StreetItemType.NAME;
                }
            }
        }
        StreetItemToken last = res.get(res.size() - 1);
        for(int kk = 0; kk < 2; kk++) {
            com.pullenti.ner.Token ttt = last.getEndToken().getNext();
            if (((last.typ == StreetItemType.NAME && ttt != null && ttt.getLengthChar() == 1) && ttt.chars.isAllUpper() && (ttt.getWhitespacesBeforeCount() < 2)) && ttt.getNext() != null && ttt.getNext().isChar('.')) 
                last.setEndToken(ttt.getNext());
        }
        return res;
    }

    private static StreetItemToken _tryAttachRoadNum(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        if (!t.chars.isLetter() || t.getLengthChar() != 1) 
            return null;
        com.pullenti.ner.Token tt = t.getNext();
        if (tt != null && tt.isHiphen()) 
            tt = tt.getNext();
        if (!((tt instanceof com.pullenti.ner.NumberToken))) 
            return null;
        StreetItemToken res = _new235(t, tt, StreetItemType.NAME);
        res.value = t.getSourceText().toUpperCase() + (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.NumberToken.class))).value;
        return res;
    }

    public static void initialize() throws Exception, java.io.IOException {
        if (m_Ontology != null) 
            return;
        m_Ontology = new com.pullenti.ner.core.TerminCollection();
        m_StdOntMisc = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t;
        t = com.pullenti.ner.core.Termin._new258("УЛИЦА", StreetItemType.NOUN, com.pullenti.morph.MorphGender.FEMINIE);
        t.addAbridge("УЛ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new259("ВУЛИЦЯ", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, com.pullenti.morph.MorphGender.FEMINIE);
        t.addAbridge("ВУЛ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("STREET", StreetItemType.NOUN);
        t.addAbridge("ST.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("ПЛОЩАДЬ", StreetItemType.NOUN, 1, com.pullenti.morph.MorphGender.FEMINIE);
        t.addAbridge("ПЛ.");
        t.addAbridge("ПЛОЩ.");
        t.addAbridge("ПЛ-ДЬ");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new262("ПЛОЩА", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 1, com.pullenti.morph.MorphGender.FEMINIE);
        t.addAbridge("ПЛ.");
        t.addAbridge("ПЛОЩ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("МАЙДАН", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.MASCULINE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("SQUARE", StreetItemType.NOUN);
        t.addAbridge("SQ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("ПРОЕЗД", StreetItemType.NOUN, 1, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("ПР.");
        t.addAbridge("П-Д");
        t.addAbridge("ПР-Д");
        t.addAbridge("ПР-ЗД");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("ЛИНИЯ", StreetItemType.NOUN, 2, com.pullenti.morph.MorphGender.FEMINIE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new262("ЛІНІЯ", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 2, com.pullenti.morph.MorphGender.FEMINIE);
        m_Ontology.add(t);
        m_Prospect = (t = com.pullenti.ner.core.Termin._new261("ПРОСПЕКТ", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.MASCULINE));
        t.addAbridge("ПРОС.");
        t.addAbridge("ПРКТ");
        t.addAbridge("ПРОСП.");
        t.addAbridge("ПР-Т");
        t.addAbridge("ПР-КТ");
        t.addAbridge("П-Т");
        t.addAbridge("П-КТ");
        t.addAbridge("ПР-ТЕ");
        t.addAbridge("ПР-КТЕ");
        t.addAbridge("П-ТЕ");
        t.addAbridge("П-КТЕ");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("ПЕРЕУЛОК", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("ПЕР.");
        t.addAbridge("ПЕР-К");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("ПРОУЛОК", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("ПРОУЛ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new262("ПРОВУЛОК", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 0, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("ПРОВ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new120("LANE", StreetItemType.NOUN, 0);
        t.addAbridge("LN.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("ТУПИК", StreetItemType.NOUN, 1, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("ТУП.");
        t.addAbridge("Т.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("БУЛЬВАР", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("БУЛЬВ.");
        t.addAbridge("БУЛ.");
        t.addAbridge("Б-Р");
        t.addAbridge("Б-РЕ");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new120("BOULEVARD", StreetItemType.NOUN, 0);
        t.addAbridge("BLVD");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new120("СКВЕР", StreetItemType.NOUN, 1);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("НАБЕРЕЖНАЯ", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.FEMINIE);
        t.addAbridge("НАБ.");
        t.addAbridge("НАБЕР.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new262("НАБЕРЕЖНА", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 0, com.pullenti.morph.MorphGender.FEMINIE);
        t.addAbridge("НАБ.");
        t.addAbridge("НАБЕР.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("АЛЛЕЯ", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.FEMINIE);
        t.addAbridge("АЛ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new262("АЛЕЯ", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 0, com.pullenti.morph.MorphGender.FEMINIE);
        t.addAbridge("АЛ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new120("ALLEY", StreetItemType.NOUN, 0);
        t.addAbridge("ALY.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("ПРОСЕКА", StreetItemType.NOUN, 1, com.pullenti.morph.MorphGender.FEMINIE);
        t.addVariant("ПРОСЕК", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new262("ПРОСІКА", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 1, com.pullenti.morph.MorphGender.FEMINIE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("ШОССЕ", StreetItemType.NOUN, 1, com.pullenti.morph.MorphGender.NEUTER);
        t.addAbridge("Ш.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new262("ШОСЕ", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 1, com.pullenti.morph.MorphGender.NEUTER);
        t.addAbridge("Ш.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new120("ROAD", StreetItemType.NOUN, 1);
        t.addAbridge("RD.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("МИКРОРАЙОН", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("МКР.");
        t.addAbridge("МИКР-Н");
        t.addAbridge("МКР-Н");
        t.addAbridge("МКРН.");
        t.addAbridge("М-Н");
        t.addAbridge("М-ОН");
        t.addAbridge("М/Р");
        t.addVariant("МІКРОРАЙОН", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("КВАРТАЛ", StreetItemType.NOUN, 2, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("КВАРТ.");
        t.addAbridge("КВ-Л");
        t.addAbridge("КВ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new289("ЖИЛОЙ КОМПЛЕКС", StreetItemType.NOUN, "ЖК", 0, com.pullenti.morph.MorphGender.MASCULINE);
        t.addVariant("ЖИЛКОМПЛЕКС", false);
        t.addAbridge("ЖИЛ.К.");
        t.addAbridge("Ж/К");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("ГОРОДОК", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.MASCULINE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new262("МІСТЕЧКО", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 0, com.pullenti.morph.MorphGender.NEUTER);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new120("HILL", StreetItemType.NOUN, 0);
        t.addAbridge("HL.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("ВОЕННЫЙ ГОРОДОК", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.MASCULINE);
        t.addAbridge("В.ГОРОДОК");
        t.addAbridge("В/Г");
        t.addAbridge("В/ГОРОДОК");
        t.addAbridge("В/ГОР");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("ПРОМЗОНА", StreetItemType.NOUN, 1, com.pullenti.morph.MorphGender.FEMINIE);
        t.addVariant("ПРОМЫШЛЕННАЯ ЗОНА", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("ЖИЛАЯ ЗОНА", StreetItemType.NOUN, 1, com.pullenti.morph.MorphGender.FEMINIE);
        t.addVariant("ЖИЛЗОНА", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("КОММУНАЛЬНАЯ ЗОНА", StreetItemType.NOUN, 1, com.pullenti.morph.MorphGender.FEMINIE);
        t.addVariant("КОМЗОНА", false);
        t.addAbridge("КОММУН. ЗОНА");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("МАССИВ", StreetItemType.NOUN, 2, com.pullenti.morph.MorphGender.MASCULINE);
        t.addVariant("ЖИЛОЙ МАССИВ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("МОСТ", StreetItemType.NOUN, 2, com.pullenti.morph.MorphGender.MASCULINE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new262("МІСТ", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 2, com.pullenti.morph.MorphGender.MASCULINE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("ПАРК", StreetItemType.NOUN, 2, com.pullenti.morph.MorphGender.MASCULINE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new120("PLAZA", StreetItemType.NOUN, 1);
        t.addAbridge("PLZ");
        m_Ontology.add(t);
        m_Metro = (t = com.pullenti.ner.core.Termin._new302("СТАНЦИЯ МЕТРО", "МЕТРО", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.FEMINIE));
        t.addVariant("СТАНЦІЯ МЕТРО", false);
        t.addAbridge("СТ.МЕТРО");
        t.addAbridge("СТ.М.");
        t.addAbridge("МЕТРО");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new261("АВТОДОРОГА", StreetItemType.NOUN, 0, com.pullenti.morph.MorphGender.FEMINIE);
        t.addVariant("ФЕДЕРАЛЬНАЯ АВТОДОРОГА", false);
        t.addVariant("АВТОМОБИЛЬНАЯ ДОРОГА", false);
        t.addVariant("АВТОТРАССА", false);
        t.addVariant("ФЕДЕРАЛЬНАЯ ТРАССА", false);
        t.addVariant("АВТОМАГИСТРАЛЬ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new302("ДОРОГА", "АВТОДОРОГА", StreetItemType.NOUN, 1, com.pullenti.morph.MorphGender.FEMINIE);
        t.addVariant("ТРАССА", false);
        t.addVariant("МАГИСТРАЛЬ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new262("АВТОДОРОГА", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 0, com.pullenti.morph.MorphGender.FEMINIE);
        t.addVariant("ФЕДЕРАЛЬНА АВТОДОРОГА", false);
        t.addVariant("АВТОМОБІЛЬНА ДОРОГА", false);
        t.addVariant("АВТОТРАСА", false);
        t.addVariant("ФЕДЕРАЛЬНА ТРАСА", false);
        t.addVariant("АВТОМАГІСТРАЛЬ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new306("ДОРОГА", "АВТОДОРОГА", StreetItemType.NOUN, com.pullenti.morph.MorphLang.UA, 1, com.pullenti.morph.MorphGender.FEMINIE);
        t.addVariant("ТРАСА", false);
        t.addVariant("МАГІСТРАЛЬ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new307("МОСКОВСКАЯ КОЛЬЦЕВАЯ АВТОМОБИЛЬНАЯ ДОРОГА", "МКАД", StreetItemType.FIX, com.pullenti.morph.MorphGender.FEMINIE);
        t.addVariant("МОСКОВСКАЯ КОЛЬЦЕВАЯ АВТОДОРОГА", false);
        m_Ontology.add(t);
        m_Ontology.add(com.pullenti.ner.core.Termin._new118("САДОВОЕ КОЛЬЦО", StreetItemType.FIX));
        m_Ontology.add(com.pullenti.ner.core.Termin._new118("БУЛЬВАРНОЕ КОЛЬЦО", StreetItemType.FIX));
        m_Ontology.add(com.pullenti.ner.core.Termin._new118("ТРАНСПОРТНОЕ КОЛЬЦО", StreetItemType.FIX));
        t = com.pullenti.ner.core.Termin._new258("ПОЧТОВОЕ ОТДЕЛЕНИЕ", StreetItemType.STDNAME, com.pullenti.morph.MorphGender.NEUTER);
        t.addAbridge("П.О.");
        t.addAbridge("ПОЧТ.ОТД.");
        t.addAbridge("ПОЧТОВ.ОТД.");
        t.addAbridge("ПОЧТОВОЕ ОТД.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("БОЛЬШОЙ", StreetItemType.STDADJECTIVE);
        t.addAbridge("БОЛ.");
        t.addAbridge("Б.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new119("ВЕЛИКИЙ", StreetItemType.STDADJECTIVE, com.pullenti.morph.MorphLang.UA);
        t.addAbridge("ВЕЛ.");
        t.addAbridge("В.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("МАЛЫЙ", StreetItemType.STDADJECTIVE);
        t.addAbridge("МАЛ.");
        t.addAbridge("М.");
        t.addVariant("МАЛИЙ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("СРЕДНИЙ", StreetItemType.STDADJECTIVE);
        t.addAbridge("СРЕД.");
        t.addAbridge("СР.");
        t.addAbridge("С.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new119("СЕРЕДНІЙ", StreetItemType.STDADJECTIVE, com.pullenti.morph.MorphLang.UA);
        t.addAbridge("СЕРЕД.");
        t.addAbridge("СЕР.");
        t.addAbridge("С.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("ВЕРХНИЙ", StreetItemType.STDADJECTIVE);
        t.addAbridge("ВЕРХН.");
        t.addAbridge("ВЕРХ.");
        t.addAbridge("ВЕР.");
        t.addAbridge("В.");
        t.addVariant("ВЕРХНІЙ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("НИЖНИЙ", StreetItemType.STDADJECTIVE);
        t.addAbridge("НИЖН.");
        t.addAbridge("НИЖ.");
        t.addAbridge("Н.");
        t.addVariant("НИЖНІЙ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("СТАРЫЙ", StreetItemType.STDADJECTIVE);
        t.addAbridge("СТАР.");
        t.addAbridge("СТ.");
        t.addVariant("СТАРИЙ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("НОВЫЙ", StreetItemType.STDADJECTIVE);
        t.addAbridge("НОВ.");
        t.addVariant("НОВИЙ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("НОМЕР", StreetItemType.STDADJECTIVE);
        t.addAbridge("N");
        t.addAbridge("№");
        t.addAbridge("НОМ.");
        m_Ontology.add(t);
        for(String s : new String[] {"ФРИДРИХА ЭНГЕЛЬСА", "КАРЛА МАРКСА", "РОЗЫ ЛЮКСЕМБУРГ"}) {
            t = com.pullenti.ner.core.Termin._new118(s, StreetItemType.STDNAME);
            t.addAllAbridges(0, 0, 0);
            m_Ontology.add(t);
        }
        for(String s : new String[] {"МАРТА", "МАЯ", "ОКТЯБРЯ", "НОЯБРЯ", "БЕРЕЗНЯ", "ТРАВНЯ", "ЖОВТНЯ", "ЛИСТОПАДА"}) {
            m_Ontology.add(com.pullenti.ner.core.Termin._new118(s, StreetItemType.STDNAME));
        }
        for(String s : new String[] {"МАРШАЛА", "ГЕНЕРАЛА", "АДМИРАЛА", "КОСМОНАВТА", "ЛЕТЧИКА", "АВИАКОНСТРУКТОРА", "АРХИТЕКТОРА", "СКУЛЬПТОРА", "ХУДОЖНИКА", "КОНСТРУКТОРА", "АКАДЕМИКА", "ПРОФЕССОРА", "ЛЕЙТЕНАНТА", "КАПИТАНА", "МАЙОРА", "ПОДПОЛКОВНИКА", "ПОЛКОВНИКА", "ПОЛИЦИИ", "МИЛИЦИИ"}) {
            m_StdOntMisc.add(new com.pullenti.ner.core.Termin(s, new com.pullenti.morph.MorphLang(null), false));
            t = com.pullenti.ner.core.Termin._new118(s, StreetItemType.STDPARTOFNAME);
            t.addAllAbridges(0, 0, 2);
            t.addAllAbridges(2, 5, 0);
            t.addAbridge("ГЛ." + s);
            t.addAbridge("ГЛАВ." + s);
            m_Ontology.add(t);
        }
        for(String s : new String[] {"МАРШАЛА", "ГЕНЕРАЛА", "АДМІРАЛА", "КОСМОНАВТА", "ЛЬОТЧИКА", "АВІАКОНСТРУКТОРА", "АРХІТЕКТОРА", "СКУЛЬПТОРА", "ХУДОЖНИКА", "КОНСТРУКТОРА", "АКАДЕМІКА", "ПРОФЕСОРА", "ЛЕЙТЕНАНТА", "КАПІТАН", "МАЙОР", "ПІДПОЛКОВНИК", "ПОЛКОВНИК", "ПОЛІЦІЇ", "МІЛІЦІЇ"}) {
            m_StdOntMisc.add(new com.pullenti.ner.core.Termin(s, new com.pullenti.morph.MorphLang(null), false));
            t = com.pullenti.ner.core.Termin._new119(s, StreetItemType.STDPARTOFNAME, com.pullenti.morph.MorphLang.UA);
            t.addAllAbridges(0, 0, 2);
            t.addAllAbridges(2, 5, 0);
            t.addAbridge("ГЛ." + s);
            t.addAbridge("ГЛАВ." + s);
            m_Ontology.add(t);
        }
        t = com.pullenti.ner.core.Termin._new118("ВАСИЛЬЕВСКОГО ОСТРОВА", StreetItemType.STDNAME);
        t.addAbridge("В.О.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("ПЕТРОГРАДСКОЙ СТОРОНЫ", StreetItemType.STDNAME);
        t.addAbridge("П.С.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("ОЛИМПИЙСКАЯ ДЕРЕВНЯ", StreetItemType.FIX);
        t.addAbridge("ОЛИМП. ДЕРЕВНЯ");
        t.addAbridge("ОЛИМП. ДЕР.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new118("ЛЕНИНСКИЕ ГОРЫ", StreetItemType.FIX);
        m_Ontology.add(t);
        byte[] obj = ResourceHelper.getBytes("s.dat");
        if (obj == null) 
            throw new Exception("Can't file resource file s.dat in Location analyzer");
        String streets = com.pullenti.n2j.Utils.decodeCharset(java.nio.charset.Charset.forName("UTF-8"), com.pullenti.ner.geo.internal.MiscLocationHelper.deflate(obj), 0, -1);
        java.util.HashMap<String, Boolean> names = new java.util.HashMap< >();
        try (java.io.BufferedReader tr = new java.io.BufferedReader(new java.io.StringReader(streets))) {
            StringBuilder name = new StringBuilder();
            while(true) {
                String line = tr.readLine();
                if (line == null) 
                    break;
                line = line.trim();
                if (com.pullenti.n2j.Utils.isNullOrEmpty(line)) 
                    continue;
                if (line.indexOf(';') >= 0) {
                    String[] parts = com.pullenti.n2j.Utils.split(line, String.valueOf(';'), false);
                    t = com.pullenti.ner.core.Termin._new330(StreetItemType.NAME, true);
                    t.initByNormalText(parts[0], new com.pullenti.morph.MorphLang(null));
                    for(int j = 1; j < parts.length; j++) {
                        t.addVariant(parts[j], true);
                    }
                }
                else {
                    t = com.pullenti.ner.core.Termin._new330(StreetItemType.NAME, true);
                    t.initByNormalText(line, new com.pullenti.morph.MorphLang(null));
                }
                if (t.terms.size() > 1) 
                    t.tag = StreetItemType.STDNAME;
                m_Ontology.add(t);
            }
        }
    }

    private static com.pullenti.ner.core.TerminCollection m_Ontology;

    private static com.pullenti.ner.core.TerminCollection m_StdOntMisc;

    private static com.pullenti.ner.core.Termin m_Prospect;

    private static com.pullenti.ner.core.Termin m_Metro;

    public static StreetItemToken _new218(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.NumberToken _arg4) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.number = _arg4;
        return res;
    }
    public static StreetItemToken _new219(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.NumberToken _arg4, com.pullenti.ner.MorphCollection _arg5) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.number = _arg4;
        res.setMorph(_arg5);
        return res;
    }
    public static StreetItemToken _new220(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.NumberToken _arg4, boolean _arg5) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.number = _arg4;
        res.numberHasPrefix = _arg5;
        return res;
    }
    public static StreetItemToken _new221(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.hasStdSuffix = _arg3;
        return res;
    }
    public static StreetItemToken _new222(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.core.Termin _arg3, StreetItemType _arg4, boolean _arg5) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.termin = _arg3;
        res.typ = _arg4;
        res.isAbridge = _arg5;
        return res;
    }
    public static StreetItemToken _new223(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.address.StreetReferent _arg4, com.pullenti.ner.MorphCollection _arg5, boolean _arg6) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.existStreet = _arg4;
        res.setMorph(_arg5);
        res.isInDictionary = _arg6;
        return res;
    }
    public static StreetItemToken _new224(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.NumberToken _arg4, boolean _arg5, com.pullenti.ner.MorphCollection _arg6) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.number = _arg4;
        res.numberHasPrefix = _arg5;
        res.setMorph(_arg6);
        return res;
    }
    public static StreetItemToken _new225(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.core.Termin _arg4, boolean _arg5, com.pullenti.ner.MorphCollection _arg6) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.termin = _arg4;
        res.isAbridge = _arg5;
        res.setMorph(_arg6);
        return res;
    }
    public static StreetItemToken _new227(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.core.Termin _arg4, com.pullenti.ner.core.Termin _arg5, boolean _arg6, com.pullenti.ner.MorphCollection _arg7, int _arg8) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.termin = _arg4;
        res.altTermin = _arg5;
        res.isAbridge = _arg6;
        res.setMorph(_arg7);
        res.nounIsDoubtCoef = _arg8;
        return res;
    }
    public static StreetItemToken _new228(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.MorphCollection _arg4, String _arg5) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setMorph(_arg4);
        res.value = _arg5;
        return res;
    }
    public static StreetItemToken _new230(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.MorphCollection _arg4, com.pullenti.ner.core.Termin _arg5) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setMorph(_arg4);
        res.termin = _arg5;
        return res;
    }
    public static StreetItemToken _new231(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.MorphCollection _arg4, boolean _arg5) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setMorph(_arg4);
        res.isInDictionary = _arg5;
        return res;
    }
    public static StreetItemToken _new232(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.MorphCollection _arg4, boolean _arg5, com.pullenti.ner.core.Termin _arg6) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setMorph(_arg4);
        res.isInDictionary = _arg5;
        res.termin = _arg6;
        return res;
    }
    public static StreetItemToken _new233(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, boolean _arg4, String _arg5, com.pullenti.ner.MorphCollection _arg6) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.isInDictionary = _arg4;
        res.value = _arg5;
        res.setMorph(_arg6);
        return res;
    }
    public static StreetItemToken _new234(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, String _arg4) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        return res;
    }
    public static StreetItemToken _new235(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }
    public static StreetItemToken _new236(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.NumberToken _arg4, String _arg5) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.number = _arg4;
        res.value = _arg5;
        return res;
    }
    public static StreetItemToken _new237(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.MorphCollection _arg4) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setMorph(_arg4);
        return res;
    }
    public static StreetItemToken _new239(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, com.pullenti.ner.MorphCollection _arg4, String _arg5) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setMorph(_arg4);
        res.altValue = _arg5;
        return res;
    }
    public static StreetItemToken _new248(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, String _arg4, String _arg5) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        res.altValue = _arg5;
        return res;
    }
    public static StreetItemToken _new253(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, String _arg4, boolean _arg5) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        res.isInBrackets = _arg5;
        return res;
    }
    public static StreetItemToken _new256(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, StreetItemType _arg3, boolean _arg4) {
        StreetItemToken res = new StreetItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.isInBrackets = _arg4;
        return res;
    }
    public StreetItemToken() {
        super();
    }
}
