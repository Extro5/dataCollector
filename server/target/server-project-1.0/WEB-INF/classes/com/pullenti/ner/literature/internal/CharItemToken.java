/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature.internal;

public class CharItemToken extends com.pullenti.ner.MetaToken {

    public CharItemToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public CharItemType typ = CharItemType.NAME;

    public CharItemAttrSubtype subtyp = CharItemAttrSubtype.UNDEFINED;

    public com.pullenti.ner.literature.CharacterType charTyp = com.pullenti.ner.literature.CharacterType.UNDEFINED;

    public java.util.ArrayList<String> values = new java.util.ArrayList< >();

    /**
     * Полное имя для сокращённого
     */
    public java.util.ArrayList<String> fullValues = new java.util.ArrayList< >();

    public com.pullenti.morph.MorphGender gender = com.pullenti.morph.MorphGender.UNDEFINED;

    public boolean isUniWithPrev;

    public boolean isUniWithNext;

    public boolean canBeFirstName;

    public boolean canBeMiddleName;

    public boolean canBeLastName;

    public boolean isAfterByName;

    public CharacterAge age = CharacterAge.UNDEFINED;

    public com.pullenti.ner.person.PersonPropertyReferent persProp;

    public com.pullenti.ner.Token anaforRef;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(typ.toString()).append(" ").append((subtyp == CharItemAttrSubtype.UNDEFINED ? "" : subtyp.toString())).append(": ");
        for(String v : values) {
            if (com.pullenti.n2j.Utils.stringsNe(v, values.get(0))) 
                res.append('/');
            res.append(v);
        }
        return res.toString();
    }

    public static java.util.ArrayList<CharItemToken> tryParseList(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        PeaceToken peace = PeaceToken.getPeace(t);
        if (peace != null) {
            if (!peace.isText) 
                return null;
        }
        if ((t instanceof com.pullenti.ner.TextToken) && t.getMorph()._getClass().isPersonalPronoun()) {
            if (com.pullenti.n2j.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term, "Я")) {
                if (DialogItemToken.isInDialog(t)) 
                    return null;
                CharItemToken cit0 = _new1499(t, t, CharItemType.PROPER);
                cit0.values.add("Я");
                java.util.ArrayList<CharItemToken> res0 = new java.util.ArrayList< >();
                res0.add(cit0);
                return res0;
            }
        }
        if (!t.isWhitespaceBefore() && t.getPrevious() != null) {
            if (t.getPrevious().isHiphen()) 
                return null;
        }
        if (t.isValue("ЛЭНГДОН", null) && t.getNext().isValue("НОВЫЙ", null)) {
        }
        if (t.beginChar == 40417) {
        }
        com.pullenti.morph.MorphGender gen;
        CharItemToken cit = tryParse(t, null);
        if (cit == null) {
            com.pullenti.n2j.Outargwrapper<com.pullenti.morph.MorphGender> inoutarg1500 = new com.pullenti.n2j.Outargwrapper< >();
            com.pullenti.ner.Token tt = LitHelper.findByName(t, inoutarg1500);
            gen = inoutarg1500.value;
            if (tt == null) 
                return null;
            cit = tryParse(tt, null);
            if (cit == null) 
                return null;
            cit.isAfterByName = true;
        }
        java.util.ArrayList<CharItemToken> res = new java.util.ArrayList< >();
        res.add(cit);
        for(com.pullenti.ner.Token tt = cit.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
            if (tt.isNewlineBefore()) 
                break;
            if (PeaceToken.getPeace(tt) != peace) 
                break;
            if (res.size() > 10) 
                return null;
            cit = tryParse(tt, res.get(res.size() - 1));
            if (cit == null && res.get(res.size() - 1).typ == CharItemType.ATTR) {
            }
            if ((cit == null && !tt.isWhitespaceAfter() && !tt.isWhitespaceBefore()) && tt.isHiphen() && res.get(res.size() - 1).typ != CharItemType.ATTR) {
                cit = tryParse(tt.getNext(), res.get(res.size() - 1));
                if (cit != null) {
                    res.get(res.size() - 1).isUniWithNext = true;
                    cit.isUniWithPrev = true;
                    if (cit.typ == CharItemType.ATTR) 
                        cit.typ = CharItemType.PROPER;
                }
            }
            if (cit == null) 
                break;
            res.add(cit);
            tt = cit.getEndToken();
        }
        for(int i = 1; i < res.size(); i++) {
            if (res.get(i).typ == CharItemType.ATTR) {
                if (res.get(i - 1).typ == CharItemType.ATTR) {
                    for(int indRemoveRange = i + res.size() - i - 1, indMinIndex = i; indRemoveRange >= indMinIndex; indRemoveRange--) res.remove(indRemoveRange);
                    break;
                }
                if (res.get(i - 1).isUniWithNext) 
                    continue;
                if (com.pullenti.morph.CharsInfo.ooEq(res.get(i - 1).chars, res.get(i).chars) && (i + 1) == res.size()) {
                    res.get(i).typ = CharItemType.PROPER;
                    i--;
                    continue;
                }
                for(int indRemoveRange = i + res.size() - i - 1, indMinIndex = i; indRemoveRange >= indMinIndex; indRemoveRange--) res.remove(indRemoveRange);
                break;
            }
            else if (res.get(i).typ == CharItemType.NAME || res.get(i).typ == CharItemType.PROPER) {
                if (res.get(0).isAfterByName) 
                    continue;
                if (res.get(i).getBeginToken().getPrevious() != null && res.get(i).getBeginToken().getPrevious().isHiphen()) 
                    continue;
                if (res.get(i).isUniWithPrev || res.get(i - 1).isUniWithNext) 
                    continue;
                if (res.get(i - 1).typ != CharItemType.ATTR) {
                    if (com.pullenti.morph.CharsInfo.ooNoteq(res.get(i - 1).chars, res.get(i).chars)) {
                        for(int indRemoveRange = i + res.size() - i - 1, indMinIndex = i; indRemoveRange >= indMinIndex; indRemoveRange--) res.remove(indRemoveRange);
                        break;
                    }
                }
                boolean err = false;
                if (res.get(i - 1).typ == CharItemType.PROPER) {
                    if (res.get(i - 1).chars.isAllUpper()) 
                        err = true;
                }
                if (err) {
                    for(int indRemoveRange = i + res.size() - i - 1, indMinIndex = i; indRemoveRange >= indMinIndex; indRemoveRange--) res.remove(indRemoveRange);
                    break;
                }
            }
        }
        if (res.size() == 0) 
            return null;
        if (res.size() == 1 && res.get(0).typ == CharItemType.ATTR && !res.get(0).isAfterByName) {
            if (DialogItemToken.isInDialog(res.get(0).getBeginToken())) 
                return null;
        }
        if ((res.size() == 1 && res.get(0).typ == CharItemType.PROPER && res.get(0).subtyp == CharItemAttrSubtype.NOUNINDICT) && !res.get(0).isAfterByName) {
            com.pullenti.ner.core.StatisticCollection.WordInfo stat = t.kit.statistics.getWordInfo(t);
            if (stat != null && ((stat.lowerCount * 10) < stat.totalCount) && stat.totalCount > 10) {
            }
            else 
                return null;
        }
        if (res.get(0).subtyp == CharItemAttrSubtype.PERSONAFTER || res.get(0).charTyp == com.pullenti.ner.literature.CharacterType.ANIMAL) {
            if (_isAnaforToken(res.get(0).getBeginToken().getPrevious())) {
                if (res.get(0).values.contains("БОГАЧ")) {
                }
                res.get(0).anaforRef = res.get(0).setBeginToken(res.get(0).getBeginToken().getPrevious());
            }
        }
        if (res.get(0).subtyp == CharItemAttrSubtype.PERSONAFTER && res.size() > 1 && !res.get(0).isAfterByName) {
            if (res.get(1)._checkMorphError(res.get(0).getMorph())) 
                for(int indRemoveRange = 1 + res.size() - 1 - 1, indMinIndex = 1; indRemoveRange >= indMinIndex; indRemoveRange--) res.remove(indRemoveRange);
        }
        for(CharItemToken r : res) {
            r._createAltValue();
        }
        if (com.pullenti.n2j.Utils.stringsEq(res.get(0).values.get(0), "БИ")) {
        }
        return res;
    }

    private boolean _checkMorphError(com.pullenti.morph.MorphBaseInfo mbi) {
        boolean okNom = false;
        boolean okGen = false;
        for(com.pullenti.morph.MorphBaseInfo it : getMorph().getItems()) {
            if (it.getNumber() != com.pullenti.morph.MorphNumber.PLURAL && (it instanceof com.pullenti.morph.MorphWordForm) && (((com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(it, com.pullenti.morph.MorphWordForm.class))).isInDictionary()) {
                if (it.getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                    continue;
                if (it.getGender() != com.pullenti.morph.MorphGender.UNDEFINED && mbi.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                    if ((((it.getGender().value()) & (mbi.getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                        continue;
                }
                if (((it.getCase().isGenitive() || it.getCase().isAccusative() || it.getCase().isDative())) && !it.getCase().isNominative()) {
                    if (((com.pullenti.morph.MorphCase.ooBitand(mbi.getCase(), it.getCase()))).isUndefined()) 
                        okGen = true;
                }
                else if (it.getCase().isNominative()) {
                    if (mbi.getCase().isNominative()) 
                        okNom = true;
                }
            }
        }
        if (okGen && !okNom) 
            return true;
        return false;
    }

    private void _createAltValue() {
        if (getBeginToken() != getEndToken()) 
            return;
        if (typ == CharItemType.ATTR) 
            return;
        for(String v : values) {
            java.util.ArrayList<com.pullenti.ner.person.internal.ShortNameHelper.ShortnameVar> names = com.pullenti.ner.person.internal.ShortNameHelper.getNamesForShortname(v);
            if (names != null) {
                canBeFirstName = true;
                for(com.pullenti.ner.person.internal.ShortNameHelper.ShortnameVar kp : names) {
                    if ((((getMorph().getGender().value()) & (kp.gender.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                        if (!fullValues.contains(kp.name)) 
                            fullValues.add(kp.name);
                    }
                }
                break;
            }
        }
        for(String v : values) {
            String alt = null;
            if (v.length() > 6) {
                if (v.endsWith("ОВИЧ") || v.endsWith("ЕВИЧ")) {
                    canBeMiddleName = true;
                    char ch = v.charAt(v.length() - 5);
                    if (ch == 'Ь') 
                        alt = v.substring(0, 0+(v.length() - 5)) + "ИЧ";
                    else if (ch == 'И') 
                        alt = v.substring(0, 0+(v.length() - 4)) + "Ч";
                    else if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(ch)) 
                        alt = v.substring(0, 0+(v.length() - 4)) + "ИЧ";
                    else 
                        alt = v.substring(0, 0+(v.length() - 4)) + "ЫЧ";
                }
                else if (v.endsWith("ЬИЧ")) {
                    canBeMiddleName = true;
                    alt = v.substring(0, 0+(v.length() - 3)) + "ИЧ";
                }
                else if (v.endsWith("ШНА")) {
                    canBeMiddleName = true;
                    alt = v.substring(0, 0+(v.length() - 3)) + "ЧНА";
                }
            }
            if (alt != null) {
                canBeMiddleName = true;
                if (!values.contains(alt)) {
                    values.add(alt);
                    break;
                }
            }
        }
    }

    public static CharItemToken tryParse(com.pullenti.ner.Token t, CharItemToken prev) {
        if (t == null) 
            return null;
        if (prev != null && prev.typ != CharItemType.ATTR) {
            if (t instanceof com.pullenti.ner.NumberToken) {
                if (t.getMorph()._getClass().isAdjective() && t.chars.isCapitalUpper()) {
                    CharItemToken num = _new1501(t, t, CharItemType.NAME, t.getMorph(), true);
                    num.values.add(((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value).toString());
                    return num;
                }
            }
            else if (!t.chars.isAllLower()) {
                com.pullenti.ner.NumberToken rim = com.pullenti.ner.core.NumberHelper.tryParseRoman(t);
                if (rim != null) {
                    CharItemToken num = _new1502(t, rim.getEndToken(), CharItemType.NAME, true);
                    num.values.add(((Long)rim.value).toString());
                    return num;
                }
            }
        }
        if (!((t instanceof com.pullenti.ner.TextToken))) 
            return null;
        if (!t.chars.isLetter()) 
            return null;
        if (t.chars.isLatinLetter()) {
            if (!t.kit.baseLanguage.isEn()) 
                return null;
        }
        String te = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
        if ((t.getLengthChar() == 1 && !t.isWhitespaceAfter() && com.pullenti.ner.core.BracketHelper.isBracket(t.getNext(), true)) && !t.getNext().isWhitespaceAfter()) {
            if (com.pullenti.n2j.Utils.stringsEq(te, "Д") || com.pullenti.n2j.Utils.stringsEq(te, "О")) {
                CharItemToken res = tryParse(t.getNext().getNext(), prev);
                if (res != null && res.typ != CharItemType.ATTR) {
                    res.setBeginToken(t);
                    res.canBeLastName = true;
                    for(int ii = 0; ii < res.values.size(); ii++) {
                        com.pullenti.n2j.Utils.putArrayValue(res.values, ii, te + res.values.get(ii));
                    }
                    return res;
                }
            }
        }
        if (com.pullenti.ner.person.internal.PersonItemToken.M_SURPREFIXES.contains(te)) {
            com.pullenti.ner.Token tt = t.getNext();
            if (tt != null && tt.isHiphen()) 
                tt = tt.getNext();
            CharItemToken _next = tryParse(tt, prev);
            if (_next != null && _next.typ != CharItemType.ATTR) {
                CharItemToken pr = _new1503(t, t, CharItemType.NAME, true);
                pr.values.add(te);
                pr.canBeLastName = true;
                return pr;
            }
        }
        if (prev != null && prev.typ != CharItemType.ATTR) {
            if (com.pullenti.ner.person.internal.PersonItemToken.M_ARABPOSTFIX.contains(te)) {
                CharItemToken pr = _new1504(t, t, CharItemType.NAME, true, com.pullenti.morph.MorphGender.MASCULINE);
                pr.values.add(te);
                pr.canBeLastName = true;
                return pr;
            }
            if (com.pullenti.ner.person.internal.PersonItemToken.M_ARABPOSTFIXFEM.contains(te)) {
                CharItemToken pr = _new1504(t, t, CharItemType.NAME, true, com.pullenti.morph.MorphGender.FEMINIE);
                pr.values.add(te);
                pr.canBeLastName = true;
                return pr;
            }
            com.pullenti.ner.core.TerminToken tok2 = m_StdAdjs.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok2 != null) {
                if (!t.getMorph().containsAttr("сравн.", new com.pullenti.morph.MorphClass(null))) {
                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                    if (npt == null || npt.endChar == t.endChar) {
                        CharItemToken pr = _new1506(t, tok2.getEndToken(), CharItemType.NAME, true, tok2.getMorph());
                        pr.values.add(tok2.termin.getCanonicText());
                        pr.canBeLastName = true;
                        return pr;
                    }
                }
            }
        }
        if (M_EMPTYWORDS.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO) != null) 
            return null;
        if (prev == null && _isAnaforToken(t) && !t.isNewlineAfter()) {
            CharItemToken res = tryParse(t.getNext(), prev);
            if (res != null && res.typ == CharItemType.ATTR) {
                if (res.subtyp == CharItemAttrSubtype.PERSONAFTER || res.charTyp == com.pullenti.ner.literature.CharacterType.ANIMAL) {
                    res.anaforRef = t;
                    return res;
                }
            }
        }
        com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
        if (prev == null) {
            if (mc.isPreposition() || mc.isAdverb()) 
                return null;
            if (mc.isMisc()) 
                return null;
        }
        if (mc.isPronoun() || mc.isPersonalPronoun()) {
            if (!t.isValue("ТОМ", null)) 
                return null;
        }
        com.pullenti.morph.MorphCase cas = new com.pullenti.morph.MorphCase(null);
        if (prev == null && (t.getPrevious() instanceof com.pullenti.ner.TextToken) && !t.isNewlineBefore()) {
            if (t.getPrevious().getMorph()._getClass().isPreposition()) 
                cas = com.pullenti.morph.LanguageHelper.getCaseAfterPreposition(t.getPrevious().getNormalCaseText(com.pullenti.morph.MorphClass.PREPOSITION, false, com.pullenti.morph.MorphGender.UNDEFINED, false));
            else {
            }
            if (!cas.isUndefined() && !t.getMorph().getCase().isUndefined()) {
                if (((com.pullenti.morph.MorphCase.ooBitand(cas, t.getMorph().getCase()))).isUndefined()) 
                    cas = com.pullenti.morph.MorphCase.UNDEFINED;
            }
        }
        if (t.isValue("ВОЛЬКА", null) && t.chars.isCapitalUpper()) {
        }
        com.pullenti.ner.core.TerminToken tok1 = m_MisterWords.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok1 != null) {
            CharItemToken res = _new1507(t, tok1.getEndToken(), tok1.getMorph());
            res.typ = CharItemType.ATTR;
            res.charTyp = com.pullenti.ner.literature.CharacterType.MAN;
            res.subtyp = CharItemAttrSubtype.MISTER;
            res.values.add(tok1.termin.getCanonicText());
            if (tok1.termin.tag instanceof com.pullenti.morph.MorphGender) 
                res.gender = (com.pullenti.morph.MorphGender)tok1.termin.tag;
            if (tok1.termin.tag2 instanceof CharacterAge) 
                res.age = (CharacterAge)tok1.termin.tag2;
            return res;
        }
        if (prev == null) {
            com.pullenti.ner.person.internal.PersonAttrToken attr = com.pullenti.ner.person.internal.PersonAttrToken.tryAttach(t, null, com.pullenti.ner.person.internal.PersonAttrToken.PersonAttrAttachAttrs.NO);
            if (attr != null) {
                if (attr.typ == com.pullenti.ner.person.internal.PersonAttrTerminType.PREFIX && attr.value != null && attr.gender != com.pullenti.morph.MorphGender.UNDEFINED) {
                    CharItemToken res0 = _new1508(attr.getBeginToken(), attr.getEndToken(), CharItemType.ATTR, attr.getMorph());
                    res0.gender = attr.gender;
                    res0.subtyp = CharItemAttrSubtype.MISTER;
                    String str = com.pullenti.morph.Morphology.getWordform(attr.value, com.pullenti.morph.MorphBaseInfo._new1509(com.pullenti.morph.MorphCase.NOMINATIVE, com.pullenti.morph.MorphClass.NOUN));
                    if (str != null) 
                        res0.values.add(str);
                    else 
                        res0.values.add(attr.value);
                    return res0;
                }
            }
            if (attr != null && ((attr.typ == com.pullenti.ner.person.internal.PersonAttrTerminType.POSITION || attr.typ == com.pullenti.ner.person.internal.PersonAttrTerminType.KING))) {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                if (npt != null && npt.endChar <= attr.endChar) {
                    CharItemToken res0 = _new1508(attr.getBeginToken(), npt.getEndToken(), CharItemType.ATTR, attr.getMorph());
                    res0.typ = CharItemType.ATTR;
                    res0.persProp = attr.getPropRef();
                    res0.anaforRef = attr.anafor;
                    if (attr.canHasPersonAfter > 0) 
                        res0.subtyp = CharItemAttrSubtype.PERSONAFTER;
                    String str = npt.noun.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, true, com.pullenti.morph.MorphGender.UNDEFINED, false);
                    if (com.pullenti.n2j.Utils.stringsEq(str, "ОТЕЦ") && com.pullenti.n2j.Utils.stringsEq(attr.getPropRef().getName(), "священник")) 
                        attr.getPropRef().setName("отец");
                    java.util.ArrayList<com.pullenti.morph.DerivateWord> inf = com.pullenti.morph.Explanatory.findWords(str, new com.pullenti.morph.MorphLang());
                    if (inf != null) {
                        for(com.pullenti.morph.DerivateWord i : inf) {
                            if (i.attrs.isCanPersonAfter()) 
                                res0.subtyp = CharItemAttrSubtype.PERSONAFTER;
                            else if (i.attrs.isEmoNegative() || i.attrs.isEmoPositive()) 
                                res0.subtyp = CharItemAttrSubtype.EMOTION;
                        }
                    }
                    if (npt.adjectives.size() > 0 && m_StdAdjs.tryParse(npt.adjectives.get(0).getBeginToken(), com.pullenti.ner.core.TerminParseAttr.NO) != null) 
                        str = npt.getNormalCaseText(new com.pullenti.morph.MorphClass(null), true, com.pullenti.morph.MorphGender.UNDEFINED, false);
                    res0.values.add(str);
                    for(com.pullenti.ner.Token tt = npt.getEndToken().getNext(); tt != null && tt.endChar <= attr.endChar; tt = tt.getNext()) {
                        if ((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isLetter() && tt.chars.isCapitalUpper()) {
                            attr.getPropRef().setName(com.pullenti.ner.core.MiscHelper.getTextValue(t, tt.getPrevious(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE).toLowerCase());
                            break;
                        }
                        res0.setEndToken(tt);
                    }
                    if (com.pullenti.n2j.Utils.stringsCompare(str, attr.getPropRef().getName(), true) == 0) 
                        res0.persProp = null;
                    else if (res0.subtyp == CharItemAttrSubtype.EMOTION) 
                        res0.persProp = null;
                    if (res0.subtyp == CharItemAttrSubtype.PERSONAFTER && _isAnaforToken(res0.getEndToken().getNext())) {
                        res0.setEndToken(res0.getEndToken().getNext());
                        res0.anaforRef = res0.getEndToken();
                    }
                    return res0;
                }
            }
        }
        if (!mc.isProperSurname() && t.chars.isCapitalUpper()) {
            for(com.pullenti.morph.MorphBaseInfo it : t.getMorph().getItems()) {
                if (it._getClass().isProperSurname()) {
                    if (it.getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                        continue;
                    if (!cas.isUndefined() && !it.getCase().isUndefined()) {
                        if (((com.pullenti.morph.MorphCase.ooBitand(cas, it.getCase()))).isUndefined()) 
                            continue;
                    }
                    if (it instanceof com.pullenti.morph.MorphWordForm) {
                        if ((((com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(it, com.pullenti.morph.MorphWordForm.class))).undefCoef > ((short)100)) {
                            mc = com.pullenti.morph.MorphClass.ooBitor(mc, com.pullenti.morph.MorphClass._new1511(true));
                            break;
                        }
                    }
                }
            }
        }
        if (((mc.isProperName() || mc.isProperSurname() || mc.isProperSecname())) && !t.chars.isAllLower()) {
            if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) {
                if (mc.isVerb() || mc.isAdverb()) 
                    return null;
            }
            CharItemToken res = _new1499(t, t, CharItemType.NAME);
            res.setMorph(new com.pullenti.ner.MorphCollection(null));
            for(com.pullenti.morph.MorphBaseInfo it : t.getMorph().getItems()) {
                if (it._getClass().isProperName() || it._getClass().isProperSurname() || it._getClass().isProperSecname()) {
                    if (it.getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                        continue;
                    if (!cas.isUndefined() && !it.getCase().isUndefined()) {
                        if (((com.pullenti.morph.MorphCase.ooBitand(cas, it.getCase()))).isUndefined()) 
                            continue;
                    }
                    res.getMorph().addItem(it);
                    if (it.containsAttr("неизм.", new com.pullenti.morph.MorphClass()) && it.getCase().isUndefined()) 
                        it.setCase(com.pullenti.morph.MorphCase.ALLCASES);
                    if (it instanceof com.pullenti.morph.MorphWordForm) {
                        if (!res.values.contains((((com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(it, com.pullenti.morph.MorphWordForm.class))).normalCase)) 
                            res.values.add((((com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(it, com.pullenti.morph.MorphWordForm.class))).normalCase);
                        if ((((com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(it, com.pullenti.morph.MorphWordForm.class))).isInDictionary() || (((com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(it, com.pullenti.morph.MorphWordForm.class))).undefCoef > ((short)100)) {
                            if (it._getClass().isProperName()) 
                                res.canBeFirstName = true;
                            if (it._getClass().isProperSurname()) 
                                res.canBeLastName = true;
                            if (it._getClass().isProperSecname()) 
                                res.canBeMiddleName = true;
                        }
                    }
                }
            }
            if (mc.isNoun() && com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) {
                if (res.canBeFirstName) 
                    res.subtyp = CharItemAttrSubtype.NOUNINDICT;
                else {
                    com.pullenti.ner.core.StatisticCollection.WordInfo stat = t.kit.statistics.getWordInfo(t);
                    if (stat.lowerCount > 0) 
                        res.subtyp = CharItemAttrSubtype.NOUNINDICT;
                }
            }
            if (res.values.size() > 0) {
                res._createAltValue();
                return res;
            }
        }
        com.pullenti.ner.core.TerminToken tokS = m_StdAdjs.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tokS != null) {
            if (prev == null) {
                CharItemToken _next = tryParse(tokS.getEndToken().getNext(), null);
                if (_next != null && _next.typ == CharItemType.ATTR) {
                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                    if (npt != null && npt.getEndToken() == _next.getEndToken() && npt.getMorph().getNumber() != com.pullenti.morph.MorphNumber.PLURAL) {
                        _next.setBeginToken(t);
                        _next.values.clear();
                        String str = npt.getNormalCaseText(new com.pullenti.morph.MorphClass(null), true, com.pullenti.morph.MorphGender.UNDEFINED, false);
                        _next.values.add(str);
                        return _next;
                    }
                }
            }
        }
        if (!mc.isNoun()) {
            if (t.chars.isAllLower() || (t.getLengthChar() < 2)) {
                if (tokS != null && prev != null) {
                    if (prev.typ != CharItemType.ATTR) 
                        return null;
                    if (t.getMorph().containsAttr("сравн.", new com.pullenti.morph.MorphClass(null))) 
                        return null;
                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                    if (npt != null && npt.endChar > t.endChar) 
                        return null;
                }
                else if ((prev != null && t.getPrevious() != null && t.getPrevious().isHiphen()) && !t.isWhitespaceBefore()) {
                }
                else 
                    return null;
            }
            if (mc.isAdjective() || tokS != null) {
                if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) 
                    return null;
                com.pullenti.morph.MorphGender gen = com.pullenti.morph.MorphGender.UNDEFINED;
                com.pullenti.morph.MorphCase cas1 = new com.pullenti.morph.MorphCase(null);
                com.pullenti.ner.core.NounPhraseToken np = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                if (np != null && np.adjectives.size() >= 1 && t.chars.isCapitalUpper()) {
                    CharItemToken res2 = _new1508(t, np.getEndToken(), CharItemType.PROPER, np.getMorph());
                    res2.values.add(np.getNormalCaseText(new com.pullenti.morph.MorphClass(null), false, com.pullenti.morph.MorphGender.UNDEFINED, false));
                    return res2;
                }
                if (prev == null) {
                    if (np != null && np.adjectives.size() == 1) {
                    }
                    else 
                        return null;
                    if (np.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                        return null;
                    gen = np.getMorph().getGender();
                    cas1 = np.getMorph().getCase();
                }
                else if (prev.gender != com.pullenti.morph.MorphGender.UNDEFINED) {
                    gen = com.pullenti.morph.MorphGender.of((prev.gender.value()) & (t.getMorph().getGender().value()));
                    if (gen == com.pullenti.morph.MorphGender.UNDEFINED) 
                        return null;
                }
                CharItemToken res = _new1499(t, t, CharItemType.PROPER);
                if (prev != null) 
                    res.isUniWithPrev = true;
                res.setMorph(new com.pullenti.ner.MorphCollection(t.getMorph()));
                if (gen != com.pullenti.morph.MorphGender.UNDEFINED) 
                    res.getMorph().removeItems(gen);
                if (!cas.isUndefined()) 
                    res.getMorph().removeItems(cas);
                res.values.add(t.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, true, gen, false));
                return res;
            }
            if (!mc.isUndefined() && !mc.isProperGeo()) 
                return null;
            if (t.getMorph().containsAttr("прдктв.", new com.pullenti.morph.MorphClass(null))) 
                return null;
            boolean hasVov = false;
            boolean hasNotVov = false;
            for(char ch : (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term.toCharArray()) {
                if (com.pullenti.morph.LanguageHelper.isCyrillicChar(ch)) {
                    if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(ch)) 
                        hasVov = true;
                    else 
                        hasNotVov = true;
                }
                else if (com.pullenti.morph.LanguageHelper.isLatinVowel(ch)) 
                    hasVov = true;
                else 
                    hasNotVov = true;
            }
            if (!hasNotVov || !hasVov) 
                return null;
            if (t.chars.isAllLower()) {
                if ((prev != null && t.getPrevious() != null && t.getPrevious().isHiphen()) && !t.isWhitespaceBefore()) {
                }
                else 
                    return null;
            }
            CharItemToken res11 = _new1499(t, t, CharItemType.PROPER);
            int coef = 0;
            for(com.pullenti.morph.MorphBaseInfo it : t.getMorph().getItems()) {
                com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(it, com.pullenti.morph.MorphWordForm.class);
                if (wf == null) 
                    continue;
                if (wf.getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                    continue;
                if (wf._getClass().isProperSurname() || ((wf._getClass().isNoun() && wf.undefCoef > ((short)10)))) {
                    res11.getMorph().addItem(it);
                    if (coef < wf.undefCoef) 
                        res11.values.add(0, wf.normalCase);
                    else 
                        res11.values.add(wf.normalCase);
                    coef = (int)wf.undefCoef;
                    if (wf.undefCoef > ((short)100)) 
                        res11.canBeLastName = true;
                }
            }
            if (res11.values.size() == 0) {
                String vall = t.getNormalCaseText(com.pullenti.morph.MorphClass._new1516(true), false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                res11.values.add(vall);
            }
            if (!res11.values.contains((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term)) 
                res11.values.add((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term);
            for(int i = res11.values.size() - 1; i >= 0; i--) {
                if (res11.values.indexOf(res11.values.get(i)) < i) 
                    res11.values.remove(i);
            }
            return res11;
        }
        String val = t.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, false, com.pullenti.morph.MorphGender.UNDEFINED, false);
        boolean ok = false;
        boolean prop = false;
        boolean nounInDic = false;
        if (t.getMorph().containsAttr("одуш.", new com.pullenti.morph.MorphClass(null))) 
            ok = true;
        else if (com.pullenti.morph.Explanatory.isAnimated(val, new com.pullenti.morph.MorphLang())) 
            ok = true;
        else if (!t.chars.isAllLower() && !com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t) && !com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t.getPrevious(), false, false)) {
            ok = true;
            prop = true;
            nounInDic = true;
        }
        else if ((prev != null && prev.typ != CharItemType.ATTR && t.getPrevious() != null) && t.getPrevious().isHiphen() && !t.isWhitespaceBefore()) {
            ok = true;
            prop = true;
        }
        if (!ok) 
            return null;
        if (!prop && t.chars.isCapitalUpper()) {
            com.pullenti.ner.core.StatisticCollection.WordInfo stat = t.kit.statistics.getWordInfo(t);
            if (stat.totalCount > (stat.lowerCount * 10)) 
                prop = true;
            else if (prev != null && prev.chars.isCapitalUpper()) 
                prop = true;
        }
        CharItemToken res1 = _new1499(t, t, (prop ? CharItemType.PROPER : CharItemType.ATTR));
        res1.values.add(val);
        res1.setMorph(new com.pullenti.ner.MorphCollection(t.getMorph()));
        if (!cas.isUndefined()) 
            res1.getMorph().removeItems(cas);
        if ((res1.typ == CharItemType.ATTR && prev != null && prev.typ == CharItemType.ATTR) && res1.chars.isCapitalUpper()) {
            if (prev.chars.isAllLower()) 
                res1.typ = CharItemType.PROPER;
            else if (prev.chars.isCapitalUpper() && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(prev.getBeginToken(), false, false)) 
                res1.typ = CharItemType.PROPER;
        }
        if (res1.values.contains("ДОКТОР") || res1.values.contains("МАСТЕР") || res1.subtyp == CharItemAttrSubtype.MISTER) {
            res1.charTyp = com.pullenti.ner.literature.CharacterType.MAN;
            res1.subtyp = CharItemAttrSubtype.MISTER;
        }
        else 
            for(String v : res1.values) {
                java.util.ArrayList<com.pullenti.morph.DerivateWord> inf = com.pullenti.morph.Explanatory.findWords(v, new com.pullenti.morph.MorphLang());
                if (inf != null) {
                    for(com.pullenti.morph.DerivateWord i : inf) {
                        if (i.attrs.isAnimal()) 
                            res1.charTyp = com.pullenti.ner.literature.CharacterType.ANIMAL;
                        else if (i.attrs.isAnimated()) 
                            res1.charTyp = com.pullenti.ner.literature.CharacterType.MAN;
                        if (i.attrs.isCanPersonAfter()) 
                            res1.subtyp = CharItemAttrSubtype.PERSONAFTER;
                        if (i.attrs.isEmoNegative() || i.attrs.isEmoPositive()) 
                            res1.subtyp = CharItemAttrSubtype.EMOTION;
                    }
                }
            }
        if (nounInDic && res1.subtyp == CharItemAttrSubtype.UNDEFINED) 
            res1.subtyp = CharItemAttrSubtype.NOUNINDICT;
        if (((res1.subtyp == CharItemAttrSubtype.PERSONAFTER || res1.charTyp == com.pullenti.ner.literature.CharacterType.ANIMAL)) && _isAnaforToken(res1.getEndToken().getNext())) {
            res1.setEndToken(res1.getEndToken().getNext());
            res1.anaforRef = res1.getEndToken();
        }
        return res1;
    }

    public String getTermValue() {
        if (getBeginToken() == getEndToken() && (getBeginToken() instanceof com.pullenti.ner.TextToken)) 
            return (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(getBeginToken(), com.pullenti.ner.TextToken.class))).term;
        StringBuilder tmp = new StringBuilder();
        for(com.pullenti.ner.Token tt = getBeginToken(); tt != null && tt.endChar <= endChar; tt = tt.getNext()) {
            if (tt.isHiphen()) 
                tmp.append('-');
            else if (tt instanceof com.pullenti.ner.TextToken) 
                tmp.append((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term);
        }
        if (tmp.length() > 0) 
            return tmp.toString();
        return null;
    }

    private static boolean _isAnaforToken(com.pullenti.ner.Token t) {
        if (!((t instanceof com.pullenti.ner.TextToken))) 
            return false;
        if (!t.getMorph()._getClass().isPronoun()) 
            return false;
        if (t.isValue("ЕГО", null) || t.isValue("ЕЕ", null) || t.isValue("СВОЙ", null)) 
            return true;
        return false;
    }

    public static com.pullenti.ner.core.TerminCollection M_EMPTYWORDS;

    private static com.pullenti.ner.core.TerminCollection m_MisterWords;

    private static com.pullenti.ner.core.TerminCollection m_StdAdjs;

    public static CharItemToken _new1499(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, CharItemType _arg3) {
        CharItemToken res = new CharItemToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }
    public static CharItemToken _new1501(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, CharItemType _arg3, com.pullenti.ner.MorphCollection _arg4, boolean _arg5) {
        CharItemToken res = new CharItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setMorph(_arg4);
        res.isUniWithPrev = _arg5;
        return res;
    }
    public static CharItemToken _new1502(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, CharItemType _arg3, boolean _arg4) {
        CharItemToken res = new CharItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.isUniWithPrev = _arg4;
        return res;
    }
    public static CharItemToken _new1503(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, CharItemType _arg3, boolean _arg4) {
        CharItemToken res = new CharItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.isUniWithNext = _arg4;
        return res;
    }
    public static CharItemToken _new1504(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, CharItemType _arg3, boolean _arg4, com.pullenti.morph.MorphGender _arg5) {
        CharItemToken res = new CharItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.isUniWithPrev = _arg4;
        res.gender = _arg5;
        return res;
    }
    public static CharItemToken _new1506(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, CharItemType _arg3, boolean _arg4, com.pullenti.ner.MorphCollection _arg5) {
        CharItemToken res = new CharItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.isUniWithPrev = _arg4;
        res.setMorph(_arg5);
        return res;
    }
    public static CharItemToken _new1507(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.MorphCollection _arg3) {
        CharItemToken res = new CharItemToken(_arg1, _arg2);
        res.setMorph(_arg3);
        return res;
    }
    public static CharItemToken _new1508(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, CharItemType _arg3, com.pullenti.ner.MorphCollection _arg4) {
        CharItemToken res = new CharItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setMorph(_arg4);
        return res;
    }
    public CharItemToken() {
        super();
    }
    static {
        M_EMPTYWORDS = new com.pullenti.ner.core.TerminCollection();
        for(String s : new String[] {"ДОРОГОЙ", "УВАЖАЕМЫЙ", "МИЛЕЙШИЙ", "МИЛЫЙ", "ДРАЖАЙШИЙ", "ЛЮБЕЗНЫЙ", "ЛЮБЕЗНЕЙШИЙ"}) {
            M_EMPTYWORDS.add(com.pullenti.ner.core.Termin._new118(s, s));
        }
        for(String s : new String[] {"СПАСИБО", "ПОЖАЛУЙСТА", "ЗДРАВСТВУЙ", "ЗДРАВСТВУЙТЕ", "ЧЕЛОВЕК", "БОГ", "БОЖЕ", "ГОСПОДЬ", "ГОСПОДИ"}) {
            M_EMPTYWORDS.add(new com.pullenti.ner.core.Termin(s, new com.pullenti.morph.MorphLang(null), false));
        }
        m_MisterWords = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t;
        t = com.pullenti.ner.core.Termin._new120("МАЛЬЧИК", com.pullenti.morph.MorphGender.MASCULINE, CharacterAge.YOUNG);
        t.addVariant("МАЛЬЧИШКА", false);
        t.addVariant("МАЛЕНЬКИЙ МАЛЬЧИК", false);
        t.addVariant("МАЛЬЧУГАН", false);
        m_MisterWords.add(t);
        t = com.pullenti.ner.core.Termin._new120("ПАРЕНЬ", com.pullenti.morph.MorphGender.MASCULINE, CharacterAge.of((CharacterAge.YOUNGTOP.value()) | (CharacterAge.MIDDLEAGEMIDDLE.value())));
        t.addVariant("ЮНОША", false);
        t.addVariant("ХЛОПЕЦ", false);
        t.addVariant("МОЛОДОЙ ЧЕЛОВЕК", false);
        m_MisterWords.add(t);
        t = com.pullenti.ner.core.Termin._new120("МУЖЧИНА", com.pullenti.morph.MorphGender.MASCULINE, CharacterAge.MIDDLE);
        t.addVariant("МУЖИК", false);
        t.addVariant("МУЖИЧОК", false);
        m_MisterWords.add(t);
        t = com.pullenti.ner.core.Termin._new120("СТАРИК", com.pullenti.morph.MorphGender.MASCULINE, CharacterAge.OLD);
        t.addVariant("СТАРИЧОК", false);
        t.addVariant("СТАРИКАШКА", false);
        m_MisterWords.add(t);
        t = com.pullenti.ner.core.Termin._new120("ДЕВОЧКА", com.pullenti.morph.MorphGender.FEMINIE, CharacterAge.YOUNG);
        t.addVariant("ДЕВЧОНКА", false);
        t.addVariant("МАЛЕНЬКАЯ ДЕВОЧКА", false);
        m_MisterWords.add(t);
        t = com.pullenti.ner.core.Termin._new120("ДЕВУШКА", com.pullenti.morph.MorphGender.FEMINIE, CharacterAge.of((CharacterAge.YOUNGTOP.value()) | (CharacterAge.MIDDLEAGESMALL.value())));
        t.addVariant("ДЕВИЦА", false);
        m_MisterWords.add(t);
        t = com.pullenti.ner.core.Termin._new120("ЖЕНЩИНА", com.pullenti.morph.MorphGender.FEMINIE, CharacterAge.MIDDLE);
        m_MisterWords.add(t);
        t = com.pullenti.ner.core.Termin._new120("СТАРУХА", com.pullenti.morph.MorphGender.FEMINIE, CharacterAge.OLD);
        t.addVariant("СТАРУШКА", false);
        m_MisterWords.add(t);
        for(String s : new String[] {"ДЕВОЧКА", "ДЕВУШКА", "ЖЕНЩИНА", "СТАРУХА", "СТАРУШКА", "ДЕВИЦА", "ДЕВЧОНКА"}) {
            m_MisterWords.add(com.pullenti.ner.core.Termin._new118(s, com.pullenti.morph.MorphGender.FEMINIE));
        }
        m_StdAdjs = new com.pullenti.ner.core.TerminCollection();
        t = new com.pullenti.ner.core.Termin("СТАРШИЙ", new com.pullenti.morph.MorphLang(null), false);
        t.addAbridge("СТ.");
        m_StdAdjs.add(t);
        t = new com.pullenti.ner.core.Termin("СРЕДНИЙ", new com.pullenti.morph.MorphLang(null), false);
        t.addAbridge("СР.");
        t.addVariant("СЕРЕДНИЙ", false);
        m_StdAdjs.add(t);
        t = new com.pullenti.ner.core.Termin("МЛАДШИЙ", new com.pullenti.morph.MorphLang(null), false);
        t.addAbridge("МЛ.");
        t.addVariant("МЕНЬШИЙ", false);
        m_StdAdjs.add(t);
        t = new com.pullenti.ner.core.Termin("СТАРЫЙ", new com.pullenti.morph.MorphLang(null), false);
        t.addVariant("ПРЕДЫДУЩИЙ", false);
        m_StdAdjs.add(t);
        t = new com.pullenti.ner.core.Termin("НОВЫЙ", new com.pullenti.morph.MorphLang(null), false);
        t.addVariant("НЫНЕШНИЙ", false);
        m_StdAdjs.add(t);
    }
}
