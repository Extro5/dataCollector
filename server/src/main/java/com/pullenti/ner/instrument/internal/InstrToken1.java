/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.instrument.internal;

public class InstrToken1 extends com.pullenti.ner.MetaToken {

    public InstrToken1(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
        if(_globalInstance == null) return;
        typ = Types.LINE;
    }

    public com.pullenti.ner.instrument.InstrumentReferent iRef;

    public boolean isExpired;

    public java.util.ArrayList<String> numbers = new java.util.ArrayList< >();

    /**
     * Это используется, когда задаются диапазоны ...
     */
    public String minNumber;

    public NumberTypes numTyp = NumberTypes.UNDEFINED;

    public String numSuffix;

    public com.pullenti.ner.Token numBeginToken;

    public com.pullenti.ner.Token numEndToken;

    public boolean isNumDoubt = false;

    public int getLastNumber() {
        if (numbers.size() < 1) 
            return 0;
        return com.pullenti.ner.decree.internal.PartToken.getNumber(numbers.get(numbers.size() - 1));
    }


    public int getFirstNumber() {
        if (numbers.size() < 1) 
            return 0;
        return com.pullenti.ner.decree.internal.PartToken.getNumber(numbers.get(0));
    }


    public int getMiddleNumber() {
        if (numbers.size() < 2) 
            return 0;
        return com.pullenti.ner.decree.internal.PartToken.getNumber(numbers.get(1));
    }


    public int getLastMinNumber() {
        if (minNumber == null) 
            return 0;
        return com.pullenti.ner.decree.internal.PartToken.getNumber(minNumber);
    }


    public boolean getHasChanges() {
        for(com.pullenti.ner.Token t = (com.pullenti.ner.Token)com.pullenti.n2j.Utils.notnull(numEndToken, getBeginToken()); t != null; t = t.getNext()) {
            if (t.getReferent() instanceof com.pullenti.ner.decree.DecreeChangeReferent) 
                return true;
            if (t.endChar > endChar) 
                break;
        }
        return false;
    }


    public Types typ = Types.LINE;

    public java.util.ArrayList<com.pullenti.ner.decree.internal.DecreeToken> signValues = new java.util.ArrayList< >();

    public String value;

    public boolean allUpper;

    public boolean hasVerb;

    public boolean hasManySpecChars;

    /**
     * Признак того, что это стандартный заголовок (н-р, РЕКВИЗИТЫ И ПОДПИСИ СТОРОН)
     */
    public boolean isStandardTitle;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(typ.toString()).append(" ").append(numTyp.toString()).append(" ");
        if (isNumDoubt) 
            res.append("(?) ");
        if (isExpired) 
            res.append("(Expired) ");
        if (getHasChanges()) 
            res.append("(HasChanges) ");
        for(int i = 0; i < numbers.size(); i++) {
            res.append((i > 0 ? "." : "")).append(numbers.get(i));
        }
        if (numSuffix != null) 
            res.append(" Suf='").append(numSuffix).append("'");
        if (value != null) 
            res.append(" '").append(value).append("'");
        for(com.pullenti.ner.decree.internal.DecreeToken s : signValues) {
            res.append(" [").append(s.toString()).append("]");
        }
        if (allUpper) 
            res.append(" AllUpper");
        if (hasVerb) 
            res.append(" HasVerb");
        if (hasManySpecChars) 
            res.append(" HasManySpecChars");
        if (isStandardTitle) 
            res.append(" IsStandardTitle");
        if (value == null) 
            res.append(": ").append(getSourceText());
        return res.toString();
    }

    public static InstrToken1 parse(com.pullenti.ner.Token t, boolean ignoreDirectives, FragToken cur, int lev, InstrToken1 prev, boolean isCitat, int maxChar, boolean canBeTableCell) {
        if (t == null) 
            return null;
        if (t.isChar('(')) {
            InstrToken1 edt = null;
            FragToken fr = FragToken._createEditions(t);
            if (fr != null) 
                edt = _new1381(fr.getBeginToken(), fr.getEndToken(), Types.EDITIONS);
            else {
                com.pullenti.ner.Token t2 = _createEdition(t);
                if (t2 != null) 
                    edt = _new1381(t, t2, Types.EDITIONS);
            }
            if (edt != null) {
                if (edt.getEndToken().getNext() != null && edt.getEndToken().getNext().isChar('.')) 
                    edt.setEndToken(edt.getEndToken().getNext());
                return edt;
            }
        }
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.Token t00 = null;
        InstrToken1 res = _new1383(t0, t, true);
        for(; t != null; t = (t == null ? null : t.getNext())) {
            if (!t.isTableControlChar()) 
                break;
            else {
                if (t.isChar((char)0x1E)) {
                    boolean isTable = false;
                    java.util.ArrayList<com.pullenti.ner.core.internal.TableRowToken> rows = com.pullenti.ner.core.internal.TableHelper.tryParseRows(t, 0, true);
                    if (rows != null && rows.size() > 0) {
                        isTable = true;
                        if (rows.get(0).cells.size() > 2 || rows.get(0).cells.size() == 0) {
                        }
                        else if (lev >= 10) 
                            isTable = false;
                        else {
                            InstrToken1 it11 = parse(rows.get(0).getBeginToken(), true, null, 10, null, false, maxChar, canBeTableCell);
                            if (canBeTableCell) {
                                if (it11 != null) 
                                    return it11;
                            }
                            if (it11 != null && it11.numbers.size() > 0) {
                                if (it11.getTypContainerRank() > 0 || it11.getLastNumber() == 1 || it11.isStandardTitle) 
                                    isTable = false;
                            }
                        }
                    }
                    if (isTable) {
                        int le = 1;
                        for(t = t.getNext(); t != null; t = t.getNext()) {
                            if (t.isChar((char)0x1E)) 
                                le++;
                            else if (t.isChar((char)0x1F)) {
                                if ((--le) == 0) {
                                    res.setEndToken(t);
                                    res.hasVerb = true;
                                    return res;
                                }
                            }
                        }
                    }
                }
                if (t != null) 
                    res.setEndToken(t);
            }
        }
        if (t == null) {
            if (t0 instanceof com.pullenti.ner.TextToken) 
                return null;
            t = res.getEndToken();
        }
        com.pullenti.ner.decree.internal.DecreeToken dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t, null, false);
        if (dt == null && (((t.getReferent() instanceof com.pullenti.ner.person.PersonReferent) || (t.getReferent() instanceof com.pullenti.ner.instrument.InstrumentParticipant)))) {
            dt = com.pullenti.ner.decree.internal.DecreeToken._new805(t, t, com.pullenti.ner.decree.internal.DecreeToken.ItemType.OWNER);
            dt.ref = (com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
        }
        if (dt != null && dt.getEndToken().isNewlineAfter()) {
            if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.DATE || dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER || dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.OWNER) {
                res.typ = Types.SIGNS;
                res.signValues.add(dt);
                res.setEndToken(dt.getEndToken());
                return res;
            }
        }
        if (t.isValue("ПРИЛОЖЕНИЕ", "ДОДАТОК")) {
            if (t.getNext() != null && ((t.getNext().isValue("В", null) || t.getNext().isChar(':')))) {
            }
            else {
                res.typ = Types.APPENDIX;
                if (t.getReferent() instanceof com.pullenti.ner.decree.DecreePartReferent) 
                    t = t.kit.debedToken(t);
                for(t = t.getNext(); t != null; t = t.getNext()) {
                    if (res.numEndToken == null) {
                        com.pullenti.ner.Token ttt = (com.pullenti.ner.Token)com.pullenti.n2j.Utils.notnull(com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t), t);
                        NumberingHelper.parseNumber(ttt, res, prev);
                        if (res.numEndToken != null) {
                            res.setEndToken((t = res.numEndToken));
                            continue;
                        }
                    }
                    dt = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(t, null, false);
                    if (dt != null) {
                        if (dt.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NUMBER) {
                            res.numBeginToken = dt.getBeginToken();
                            res.numEndToken = dt.getEndToken();
                            if (dt.value != null) 
                                res.numbers.add(dt.value.toUpperCase());
                        }
                        t = res.setEndToken(dt.getEndToken());
                        continue;
                    }
                    if ((t instanceof com.pullenti.ner.NumberToken) && ((t.isNewlineAfter() || ((t.getNext() != null && t.getNext().isChar('.') && t.getNext().isNewlineAfter()))))) {
                        res.numBeginToken = t;
                        res.numbers.add(((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value).toString());
                        if (t.getNext() != null && t.getNext().isChar('.')) 
                            t = t.getNext();
                        res.numEndToken = t;
                        res.setEndToken(t);
                        continue;
                    }
                    if (((t instanceof com.pullenti.ner.NumberToken) && (t.getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().getLengthChar() == 1) && ((t.getNext().isNewlineAfter() || ((t.getNext().getNext() != null && t.getNext().getNext().isChar('.')))))) {
                        res.numBeginToken = t;
                        res.numbers.add(((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value).toString());
                        res.numbers.add((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class))).term);
                        res.numTyp = NumberTypes.COMBO;
                        t = t.getNext();
                        if (t.getNext() != null && t.getNext().isChar('.')) 
                            t = t.getNext();
                        res.numEndToken = t;
                        res.setEndToken(t);
                        continue;
                    }
                    if (res.numEndToken == null) {
                        NumberingHelper.parseNumber(t, res, prev);
                        if (res.numEndToken != null) {
                            res.setEndToken((t = res.numEndToken));
                            continue;
                        }
                    }
                    if (t.isValue("К", "ДО") && t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.decree.DecreeReferent)) 
                        break;
                    if (t.chars.isLetter()) {
                        com.pullenti.ner.NumberToken lat = com.pullenti.ner.core.NumberHelper.tryParseRoman(t);
                        if (lat != null && !t.isValue("C", null) && !t.isValue("С", null)) {
                            res.numBeginToken = t;
                            res.numbers.add(((Long)lat.value).toString());
                            res.numTyp = NumberTypes.ROMAN;
                            t = lat.getEndToken();
                            if (t.getNext() != null && ((t.getNext().isChar('.') || t.getNext().isChar(')')))) 
                                t = t.getNext();
                            res.numEndToken = t;
                            res.setEndToken(t);
                            continue;
                        }
                        if (t.getLengthChar() == 1 && t.chars.isAllUpper()) {
                            res.numBeginToken = t;
                            res.numbers.add((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term);
                            res.numTyp = NumberTypes.LETTER;
                            if (t.getNext() != null && ((t.getNext().isChar('.') || t.getNext().isChar(')')))) 
                                t = t.getNext();
                            res.numEndToken = t;
                            res.setEndToken(t);
                            continue;
                        }
                    }
                    if (InstrToken._checkEntered(t) != null) 
                        break;
                    if (t instanceof com.pullenti.ner.TextToken) {
                        if ((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).isPureVerb()) {
                            res.typ = Types.LINE;
                            break;
                        }
                    }
                    break;
                }
                if (res.typ != Types.LINE) 
                    return res;
            }
        }
        if (t.isNewlineBefore()) {
            if (t.isValue("МНЕНИЕ", "ДУМКА") || ((t.isValue("ОСОБОЕ", "ОСОБЛИВА") && t.getNext() != null && t.getNext().isValue("МНЕНИЕ", "ДУМКА")))) {
                com.pullenti.ner.Token t1 = t.getNext();
                if (t1 != null && t1.isValue("МНЕНИЕ", "ДУМКА")) 
                    t1 = t1.getNext();
                boolean ok = false;
                if (t1 != null) {
                    if (t1.isNewlineBefore() || (t1.getReferent() instanceof com.pullenti.ner.person.PersonReferent)) 
                        ok = true;
                }
                if (ok) {
                    res.typ = Types.APPENDIX;
                    res.setEndToken(t1.getPrevious());
                    return res;
                }
            }
            if ((t.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) && (((com.pullenti.ner.decree.DecreeReferent)com.pullenti.n2j.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreeReferent.class))).getKind() == com.pullenti.ner.decree.DecreeKind.PUBLISHER) 
                res.typ = Types.APPROVED;
        }
        if (t.isValue("КОНСУЛЬТАНТПЛЮС", null) || t.isValue("ГАРАНТ", null)) {
            com.pullenti.ner.Token t1 = t.getNext();
            boolean ok = false;
            if (t1 != null && t1.isChar(':')) {
                t1 = t1.getNext();
                ok = true;
            }
            if (t1 != null && ((t1.isValue("ПРИМЕЧАНИЕ", null) || ok))) {
                if (t1.getNext() != null && t1.getNext().isChar('.')) 
                    t1 = t1.getNext();
                InstrToken1 re = _new1381(t, t1, Types.COMMENT);
                for(t1 = t1.getNext(); t1 != null; t1 = t1.getNext()) {
                    re.setEndToken(t1);
                    if (t1.isNewlineAfter()) 
                        break;
                }
                return re;
            }
        }
        int checkComment = 0;
        for(com.pullenti.ner.Token ttt = t; ttt != null; ttt = ttt.getNext()) {
            if (ttt.isNewlineBefore() && ttt != t) 
                break;
            if (ttt.getMorph()._getClass().isPreposition()) 
                continue;
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(ttt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (npt == null) 
                break;
            if (npt.noun.isValue("ПРИМЕНЕНИЕ", "ЗАСТОСУВАННЯ") || npt.noun.isValue("ВОПРОС", "ПИТАННЯ")) {
                checkComment++;
                ttt = npt.getEndToken();
            }
            else 
                break;
        }
        if (checkComment > 0 || t.isValue("О", "ПРО")) {
            com.pullenti.ner.Token t1 = null;
            boolean ok = false;
            com.pullenti.ner.decree.DecreeReferent dref = null;
            for(com.pullenti.ner.Token ttt = t.getNext(); ttt != null; ttt = ttt.getNext()) {
                t1 = ttt;
                if (t1.isValue("СМ", null) && t1.getNext() != null && t1.getNext().isChar('.')) {
                    if (checkComment > 0) 
                        ok = true;
                    if ((t1.getNext().getNext() instanceof com.pullenti.ner.ReferentToken) && (((t1.getNext().getNext().getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) || (t1.getNext().getNext().getReferent() instanceof com.pullenti.ner.decree.DecreePartReferent)))) {
                        ok = true;
                        dref = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.n2j.Utils.cast(t1.getNext().getNext().getReferent(), com.pullenti.ner.decree.DecreeReferent.class);
                    }
                }
                if (ttt.isNewlineAfter()) 
                    break;
            }
            if (ok) {
                InstrToken1 cmt = _new1381(t, t1, Types.COMMENT);
                if (dref != null && t1.getNext() != null && t1.getNext().getReferent() == dref) {
                    if (t1.getNext().getNext() != null && t1.getNext().getNext().isValue("УТРАТИТЬ", "ВТРАТИТИ")) {
                        for(com.pullenti.ner.Token ttt = t1.getNext().getNext(); ttt != null; ttt = ttt.getNext()) {
                            if (ttt.isNewlineBefore()) 
                                break;
                            cmt.setEndToken(ttt);
                        }
                    }
                }
                return cmt;
            }
        }
        com.pullenti.ner.Token tt = InstrToken._checkApproved(t);
        if (tt != null) {
            res.setEndToken(tt);
            if (tt.getNext() != null && (tt.getNext().getReferent() instanceof com.pullenti.ner.decree.DecreeReferent)) {
                res.typ = Types.APPROVED;
                res.setEndToken(tt.getNext());
                return res;
            }
            com.pullenti.ner.Token tt1 = tt;
            if (tt1.isChar(':') && tt1.getNext() != null) 
                tt1 = tt1.getNext();
            if ((tt1.getReferent() instanceof com.pullenti.ner.person.PersonReferent) || (tt1.getReferent() instanceof com.pullenti.ner.instrument.InstrumentParticipant)) {
                res.typ = Types.APPROVED;
                res.setEndToken(tt1);
                return res;
            }
            com.pullenti.ner.decree.internal.DecreeToken dt1 = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(tt.getNext(), null, false);
            if (dt1 != null && dt1.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP) {
                res.typ = Types.APPROVED;
                int err = 0;
                for(com.pullenti.ner.Token ttt = dt1.getEndToken().getNext(); ttt != null; ttt = ttt.getNext()) {
                    if (com.pullenti.ner.decree.internal.DecreeToken.isKeyword(ttt, false) != null) 
                        break;
                    dt1 = com.pullenti.ner.decree.internal.DecreeToken.tryAttach(ttt, null, false);
                    if (dt1 != null) {
                        if (dt1.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.TYP || dt1.typ == com.pullenti.ner.decree.internal.DecreeToken.ItemType.NAME) 
                            break;
                        res.setEndToken((ttt = dt1.getEndToken()));
                        continue;
                    }
                    if (ttt.getMorph()._getClass().isPreposition() || ttt.getMorph()._getClass().isConjunction()) 
                        continue;
                    if (ttt.getWhitespacesBeforeCount() > 15) 
                        break;
                    if ((++err) > 10) 
                        break;
                }
                return res;
            }
        }
        String val = null;
        com.pullenti.n2j.Outargwrapper<String> inoutarg1392 = new com.pullenti.n2j.Outargwrapper< >();
        com.pullenti.ner.Token tt2 = _checkDirective(t, inoutarg1392);
        val = inoutarg1392.value;
        if (tt2 != null) {
            if (tt2.isNewlineAfter() || ((tt2.getNext() != null && ((tt2.getNext().isCharOf(":") || ((tt2.getNext().isChar('.') && tt2 != t)))) && ((tt2.getNext().isNewlineAfter() || t.chars.isAllUpper()))))) 
                return _new1387(t, (tt2.isNewlineAfter() ? tt2 : tt2.getNext()), Types.DIRECTIVE, val);
        }
        if ((lev < 3) && t != null) {
            if (t.isValue("СОДЕРЖИМОЕ", "ВМІСТ") || t.isValue("СОДЕРЖАНИЕ", "ЗМІСТ") || t.isValue("ОГЛАВЛЕНИЕ", "ЗМІСТ")) {
                boolean ok = false;
                if (t.isNewlineAfter()) 
                    ok = true;
                else if (t.getNext() != null && ((t.getNext().isCharOf(":.;") || t.getNext().getMorph().getCase().isGenitive())) && t.getNext().isNewlineAfter()) {
                    t = t.getNext();
                    ok = true;
                }
                if (ok && t.getNext() != null) {
                    InstrToken1 first = parse(t.getNext(), ignoreDirectives, null, lev + 1, null, false, 0, false);
                    if (first != null) {
                        int cou = 0;
                        for(t = first.getEndToken().getNext(); t != null; t = t.getNext()) {
                            if (t.isNewlineBefore()) {
                                if ((++cou) > 200) 
                                    break;
                            }
                            InstrToken1 it = parse(t, ignoreDirectives, null, lev + 1, null, false, 0, false);
                            if (it == null) 
                                break;
                            ok = false;
                            if (first.numbers.size() == 1 && it.numbers.size() == 1) {
                                if (com.pullenti.n2j.Utils.stringsEq(first.numbers.get(0), it.numbers.get(0))) 
                                    ok = true;
                            }
                            else if (first.value != null && it.value != null && first.value.startsWith(it.value)) 
                                ok = true;
                            if (ok) {
                                if (t.getPrevious() == null) 
                                    return null;
                                res.setEndToken(t.getPrevious());
                                res.typ = Types.INDEX;
                                return res;
                            }
                            t = it.getEndToken();
                        }
                    }
                }
            }
        }
        java.util.ArrayList<com.pullenti.ner.decree.internal.PartToken> pts = (t == null ? null : com.pullenti.ner.decree.internal.PartToken.tryAttachList((t.isValue("ПОЛОЖЕНИЕ", "ПОЛОЖЕННЯ") ? t.getNext() : t), false, 40));
        if ((pts != null && pts.size() > 0 && pts.get(0).typ != com.pullenti.ner.decree.internal.PartToken.ItemType.PREFIX) && pts.get(0).values.size() > 0 && !pts.get(0).isNewlineAfter()) {
            boolean ok = false;
            tt = pts.get(pts.size() - 1).getEndToken().getNext();
            if (tt != null && tt.isCharOf(".)]")) {
            }
            else 
                for(; tt != null; tt = tt.getNext()) {
                    if (tt.isValue("ПРИМЕНЯТЬСЯ", "ЗАСТОСОВУВАТИСЯ")) 
                        ok = true;
                    if ((tt.isValue("ВСТУПАТЬ", "ВСТУПАТИ") && tt.getNext() != null && tt.getNext().getNext() != null) && tt.getNext().getNext().isValue("СИЛА", "ЧИННІСТЬ")) 
                        ok = true;
                    if (tt.isNewlineAfter()) {
                        if (ok) 
                            return _new1381(t, tt, Types.COMMENT);
                        break;
                    }
                }
        }
        if (t != null && ((t.isNewlineBefore() || isCitat || ((t.getPrevious() != null && t.getPrevious().isTableControlChar())))) && !t.isTableControlChar()) {
            boolean ok = true;
            if (t.getNext() != null && t.chars.isAllLower()) {
                if (!t.getMorph().getCase().isNominative()) 
                    ok = false;
                else if (t.getNext() != null && t.getNext().isCharOf(",:;.")) 
                    ok = false;
                else {
                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getPrevious(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                    if (npt != null && npt.getEndToken() == t) 
                        ok = false;
                }
            }
            if (ok && (t instanceof com.pullenti.ner.TextToken)) {
                ok = false;
                String s = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
                if (com.pullenti.n2j.Utils.stringsEq(s, "ГЛАВА") || com.pullenti.n2j.Utils.stringsEq(s, "ГОЛОВА")) {
                    res.typ = Types.CHAPTER;
                    t = t.getNext();
                    ok = true;
                }
                else if (com.pullenti.n2j.Utils.stringsEq(s, "СТАТЬЯ") || com.pullenti.n2j.Utils.stringsEq(s, "СТАТТЯ")) {
                    res.typ = Types.CLAUSE;
                    t = t.getNext();
                    ok = true;
                    if ((t.getNext() instanceof com.pullenti.ner.NumberToken) && (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class))).value == ((long)19)) {
                    }
                }
                else if (com.pullenti.n2j.Utils.stringsEq(s, "РАЗДЕЛ") || com.pullenti.n2j.Utils.stringsEq(s, "РОЗДІЛ")) {
                    res.typ = Types.SECTION;
                    t = t.getNext();
                    ok = true;
                }
                else if (com.pullenti.n2j.Utils.stringsEq(s, "ЧАСТЬ") || com.pullenti.n2j.Utils.stringsEq(s, "ЧАСТИНА")) {
                    res.typ = Types.DOCPART;
                    t = t.getNext();
                    ok = true;
                }
                else if (com.pullenti.n2j.Utils.stringsEq(s, "ПОДРАЗДЕЛ") || com.pullenti.n2j.Utils.stringsEq(s, "ПІДРОЗДІЛ")) {
                    res.typ = Types.SUBSECTION;
                    t = t.getNext();
                    ok = true;
                }
                else if ((com.pullenti.n2j.Utils.stringsEq(s, "ПРИМЕЧАНИЕ") || com.pullenti.n2j.Utils.stringsEq(s, "ПРИМІТКА") || com.pullenti.n2j.Utils.stringsEq(s, "ПРИМЕЧАНИЯ")) || com.pullenti.n2j.Utils.stringsEq(s, "ПРИМІТКИ")) {
                    res.typ = Types.NOTICE;
                    t = t.getNext();
                    if (t != null && t.isCharOf(".:")) 
                        t = t.getNext();
                    ok = true;
                }
                else if (com.pullenti.n2j.Utils.stringsEq(s, "§") || com.pullenti.n2j.Utils.stringsEq(s, "ПАРАГРАФ")) {
                    res.typ = Types.PARAGRAPH;
                    t = t.getNext();
                    ok = true;
                }
                if (ok) {
                    com.pullenti.ner.Token ttt = t;
                    if (ttt != null && (ttt instanceof com.pullenti.ner.NumberToken)) 
                        ttt = ttt.getNext();
                    if (ttt != null && !ttt.isNewlineBefore()) {
                        if (com.pullenti.ner.decree.internal.PartToken.tryAttach(ttt, null, false, false) != null) 
                            res.typ = Types.LINE;
                        else if (InstrToken._checkEntered(ttt) != null) {
                            res.typ = Types.EDITIONS;
                            t00 = res.getBeginToken();
                        }
                        else if (res.getBeginToken().chars.isAllLower()) 
                            res.typ = Types.LINE;
                    }
                }
            }
        }
        boolean num = res.typ != Types.EDITIONS;
        boolean hasLetters = false;
        boolean isApp = cur != null && ((cur.kind == com.pullenti.ner.instrument.InstrumentKind.APPENDIX || cur.kind == com.pullenti.ner.instrument.InstrumentKind.INTERNALDOCUMENT));
        for(; t != null; t = t.getNext()) {
            if (maxChar > 0 && t.beginChar > maxChar) 
                break;
            if (t.isNewlineBefore() && t != res.getBeginToken()) {
                if (res.numbers.size() == 2) {
                    if (com.pullenti.n2j.Utils.stringsEq(res.numbers.get(0), "3") && com.pullenti.n2j.Utils.stringsEq(res.numbers.get(1), "4")) {
                    }
                }
                boolean isNewLine = true;
                if (t.getNewlinesBeforeCount() == 1 && t.getPrevious() != null && t.getPrevious().chars.isLetter()) {
                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getPrevious(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                    if (npt != null && npt.endChar > t.beginChar) 
                        isNewLine = false;
                    else if (t.getPrevious().getMorphClassInDictionary().isAdjective()) {
                        npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                        if (npt != null && npt.getMorph().checkAccord(t.getPrevious().getMorph(), false)) 
                            isNewLine = false;
                    }
                }
                if (isNewLine && t.chars.isLetter()) {
                    if (!com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) {
                        if (t.getPrevious() != null && t.getPrevious().isCharOf(":;.")) {
                        }
                        else if (t.getPrevious() != null && ((t.getPrevious().isValue("ИЛИ", null) || t.getPrevious().isCommaAnd())) && res.numbers.size() > 0) {
                            InstrToken1 vvv = parse(t, true, null, 0, null, false, 0, false);
                            if (vvv != null && vvv.numbers.size() > 0) 
                                isNewLine = true;
                        }
                        else 
                            isNewLine = false;
                    }
                }
                if (isNewLine) 
                    break;
            }
            if (t.isTableControlChar() && t != res.getBeginToken()) {
                if (canBeTableCell || t.isChar((char)0x1E) || t.isChar((char)0x1F)) 
                    break;
                if (num && res.numbers.size() > 0) 
                    num = false;
                else if (t.getPrevious() == res.numEndToken) {
                }
                else if (!t.isNewlineAfter()) 
                    continue;
                else 
                    break;
            }
            if ((t.isChar('[') && t == t0 && (t.getNext() instanceof com.pullenti.ner.NumberToken)) && t.getNext().getNext() != null && t.getNext().getNext().isChar(']')) {
                num = false;
                res.numbers.add(((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class))).value).toString());
                res.numTyp = NumberTypes.DIGIT;
                res.numSuffix = "]";
                res.numBeginToken = t;
                res.numEndToken = t.getNext().getNext();
                t = res.numEndToken;
                continue;
            }
            if (t.isChar('(')) {
                num = false;
                if (FragToken._createEditions(t) != null) 
                    break;
                if (_createEdition(t) != null) 
                    break;
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null) {
                    if (t == res.getBeginToken()) {
                        com.pullenti.ner.NumberToken lat = com.pullenti.ner.core.NumberHelper.tryParseRoman(t.getNext());
                        if (lat != null && lat.getEndToken().getNext() == br.getEndToken()) {
                            res.numbers.add(((Long)lat.value).toString());
                            res.numSuffix = ")";
                            res.numBeginToken = t;
                            res.numEndToken = br.getEndToken();
                            res.numTyp = (lat.typ == com.pullenti.ner.NumberSpellingType.ROMAN ? NumberTypes.ROMAN : NumberTypes.DIGIT);
                        }
                        else if (((t == t0 && t.isNewlineBefore() && br.getLengthChar() == 3) && br.getEndToken() == t.getNext().getNext() && (t.getNext() instanceof com.pullenti.ner.TextToken)) && t.getNext().chars.isLatinLetter()) {
                            res.numBeginToken = t;
                            res.numTyp = NumberTypes.LETTER;
                            res.numbers.add((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class))).term);
                            res.setEndToken((res.numEndToken = t.getNext().getNext()));
                        }
                    }
                    t = res.setEndToken(br.getEndToken());
                    continue;
                }
            }
            if (num) {
                NumberingHelper.parseNumber(t, res, prev);
                num = false;
                if (res.numbers.size() > 0) {
                }
                if (res.numEndToken != null && res.numEndToken.endChar >= t.endChar) {
                    t = res.numEndToken;
                    continue;
                }
            }
            if (res.numbers.size() == 0) 
                num = false;
            if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter()) {
                hasLetters = true;
                if (t00 == null) 
                    t00 = t;
                num = false;
                if (!t.chars.isAllUpper()) 
                    res.allUpper = false;
                if ((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).isPureVerb()) {
                    if (t.chars.isCyrillicLetter()) {
                        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse((t.getMorph()._getClass().isPreposition() ? t.getNext() : t), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                        if (npt != null) {
                        }
                        else 
                            res.hasVerb = true;
                    }
                }
            }
            else if (t instanceof com.pullenti.ner.ReferentToken) {
                hasLetters = true;
                if (t00 == null) 
                    t00 = t;
                num = false;
                if (t.getReferent() instanceof com.pullenti.ner.decree.DecreeChangeReferent) {
                    res.hasVerb = true;
                    res.allUpper = false;
                }
            }
            if (t != res.getBeginToken() && _isFirstLine(t)) 
                break;
            String tmp;
            com.pullenti.n2j.Outargwrapper<String> inoutarg1389 = new com.pullenti.n2j.Outargwrapper< >();
            tt2 = _checkDirective(t, inoutarg1389);
            tmp = inoutarg1389.value;
            if (tt2 != null) {
                if (tt2.getNext() != null && tt2.getNext().isCharOf(":.") && tt2.getNext().isNewlineAfter()) {
                    if (ignoreDirectives && !t.isNewlineBefore()) 
                        t = tt2;
                    else 
                        break;
                }
            }
            res.setEndToken(t);
        }
        if (res.getTypContainerRank() > 0 && t00 != null) {
            if (t00.chars.isAllLower()) {
                res.typ = Types.LINE;
                res.numbers.clear();
                res.numTyp = NumberTypes.UNDEFINED;
            }
        }
        if (t00 != null) {
            int len = res.endChar - t00.beginChar;
            if (len < 1000) {
                res.value = com.pullenti.ner.core.MiscHelper.getTextValue(t00, res.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                if (com.pullenti.morph.LanguageHelper.endsWith(res.value, ".")) 
                    res.value = res.value.substring(0, 0+(res.value.length() - 1));
            }
        }
        if (!hasLetters) 
            res.allUpper = false;
        if (res.numTyp != NumberTypes.UNDEFINED && res.getBeginToken() == res.numBeginToken && res.getEndToken() == res.numEndToken) {
            boolean ok = false;
            if (prev != null) {
                if (NumberingHelper.calcDelta(prev, res, true) == 1) 
                    ok = true;
            }
            if (!ok) {
                InstrToken1 res1 = parse(res.getEndToken().getNext(), true, null, 0, null, false, 0, false);
                if (res1 != null) {
                    if (NumberingHelper.calcDelta(res, res1, true) == 1) 
                        ok = true;
                }
            }
            if (!ok) {
                res.numTyp = NumberTypes.UNDEFINED;
                res.numbers.clear();
            }
        }
        if (res.typ == Types.APPENDIX || res.getTypContainerRank() > 0) {
            if (res.typ == Types.CLAUSE && res.getLastNumber() == 17) {
            }
            tt = (((com.pullenti.ner.Token)com.pullenti.n2j.Utils.notnull(res.numEndToken, res.getBeginToken()))).getNext();
            if (tt != null) {
                com.pullenti.ner.Token ttt = InstrToken._checkEntered(tt);
                if (ttt != null) {
                    if (tt.isValue("УТРАТИТЬ", null) && tt.getPrevious() != null && tt.getPrevious().isChar('.')) {
                        res.value = null;
                        res.setEndToken(tt.getPrevious());
                        res.isExpired = true;
                    }
                    else {
                        res.typ = Types.EDITIONS;
                        res.numbers.clear();
                        res.numTyp = NumberTypes.UNDEFINED;
                        res.value = null;
                    }
                }
            }
        }
        if (res.typ == Types.DOCPART) {
        }
        boolean badNumber = false;
        if ((res.getTypContainerRank() > 0 && res.numTyp != NumberTypes.UNDEFINED && res.numEndToken != null) && !res.numEndToken.isNewlineAfter() && res.numEndToken.getNext() != null) {
            com.pullenti.ner.Token t1 = res.numEndToken.getNext();
            boolean bad = false;
            if (t1.chars.isAllLower()) 
                bad = true;
            if (bad) 
                badNumber = true;
        }
        if (res.numTyp != NumberTypes.UNDEFINED && !isCitat) {
            if (res.isNewlineBefore()) {
            }
            else if (res.getBeginToken().getPrevious() != null && res.getBeginToken().getPrevious().isTableControlChar()) {
            }
            else 
                badNumber = true;
            if (com.pullenti.n2j.Utils.stringsEq(res.numSuffix, "-")) 
                badNumber = true;
        }
        if (res.typ == Types.LINE && res.numbers.size() > 0 && isCitat) {
            com.pullenti.ner.Token tt0 = res.getBeginToken().getPrevious();
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt0, true, true)) 
                tt0 = tt0.getPrevious();
            if (tt0 != null) 
                tt0 = tt0.getPrevious();
            for(; tt0 != null; tt0 = tt0.getPrevious()) {
                if (tt0.isValue("ГЛАВА", "ГОЛОВА")) 
                    res.typ = Types.CHAPTER;
                else if (tt0.isValue("СТАТЬЯ", "СТАТТЯ")) 
                    res.typ = Types.CLAUSE;
                else if (tt0.isValue("РАЗДЕЛ", "РОЗДІЛ")) 
                    res.typ = Types.SECTION;
                else if (tt0.isValue("ЧАСТЬ", "ЧАСТИНА")) 
                    res.typ = Types.DOCPART;
                else if (tt0.isValue("ПОДРАЗДЕЛ", "ПІДРОЗДІЛ")) 
                    res.typ = Types.SUBSECTION;
                else if (tt0.isValue("ПАРАГРАФ", null)) 
                    res.typ = Types.PARAGRAPH;
                else if (tt0.isValue("ПРИМЕЧАНИЕ", "ПРИМІТКА")) 
                    res.typ = Types.NOTICE;
                if (tt0.isNewlineBefore()) 
                    break;
            }
        }
        if (badNumber) {
            res.typ = Types.LINE;
            res.numTyp = NumberTypes.UNDEFINED;
            res.value = null;
            res.numbers.clear();
            res.numBeginToken = (res.numEndToken = null);
        }
        if ((res.typ == Types.SECTION || res.typ == Types.PARAGRAPH || res.typ == Types.CHAPTER) || res.typ == Types.CLAUSE) {
            if (res.numbers.size() == 0) 
                res.typ = Types.LINE;
        }
        if (res.getEndToken().isChar('>') && res.getBeginToken().isValue("ПУТЕВОДИТЕЛЬ", null)) {
            res.typ = Types.COMMENT;
            for(com.pullenti.ner.Token ttt = res.getEndToken().getNext(); ttt != null; ttt = ttt.getNext()) {
                InstrToken1 li2 = parse(ttt, true, null, 0, null, false, 0, false);
                if (li2 != null && li2.getEndToken().isChar('>')) {
                    res.setEndToken((ttt = li2.getEndToken()));
                    continue;
                }
                break;
            }
            return res;
        }
        if (res.typ == Types.LINE) {
            if (res.numTyp != NumberTypes.UNDEFINED) {
                com.pullenti.ner.Token ttt = res.getBeginToken().getPrevious();
                if (ttt != null) {
                    if (ttt.isValue("ПУНКТ", null)) {
                        res.numTyp = NumberTypes.UNDEFINED;
                        res.value = null;
                        res.numbers.clear();
                    }
                }
                for(String nn : res.numbers) {
                    int vv;
                    com.pullenti.n2j.Outargwrapper<Integer> inoutarg1390 = new com.pullenti.n2j.Outargwrapper< >();
                    boolean inoutres1391 = com.pullenti.n2j.Utils.parseInteger(nn, inoutarg1390);
                    vv = (inoutarg1390.value != null ? inoutarg1390.value : 0);
                    if (inoutres1391) {
                        if (vv > 1000 && res.numBeginToken == res.getBeginToken()) {
                            res.numTyp = NumberTypes.UNDEFINED;
                            res.value = null;
                            res.numbers.clear();
                            break;
                        }
                    }
                }
            }
            if (_isFirstLine(res.getBeginToken())) 
                res.typ = Types.FIRSTLINE;
            if (res.numTyp == NumberTypes.DIGIT) {
                if (res.numSuffix == null) 
                    res.isNumDoubt = true;
            }
            if (res.numbers.size() == 0) {
                com.pullenti.ner.decree.internal.PartToken pt = com.pullenti.ner.decree.internal.PartToken.tryAttach(res.getBeginToken(), null, false, false);
                if (pt != null && pt.typ != com.pullenti.ner.decree.internal.PartToken.ItemType.PREFIX) {
                    tt = pt.getEndToken().getNext();
                    if (tt != null && ((tt.isCharOf(".") || tt.isHiphen()))) 
                        tt = tt.getNext();
                    tt = InstrToken._checkEntered(tt);
                    if (tt != null) {
                        res.typ = Types.EDITIONS;
                        res.isExpired = tt.isValue("УТРАТИТЬ", "ВТРАТИТИ");
                    }
                }
                else {
                    tt = InstrToken._checkEntered(res.getBeginToken());
                    if (tt != null && tt.getNext() != null && (tt.getNext().getReferent() instanceof com.pullenti.ner.decree.DecreeReferent)) 
                        res.typ = Types.EDITIONS;
                    else if (res.getBeginToken().isValue("АБЗАЦ", null) && res.getBeginToken().getNext() != null && res.getBeginToken().getNext().isValue("УТРАТИТЬ", "ВТРАТИТИ")) 
                        res.isExpired = true;
                }
            }
        }
        if (res.typ == Types.LINE && res.numTyp == NumberTypes.ROMAN) {
            InstrToken1 res1 = parse(res.getEndToken().getNext(), true, cur, lev + 1, null, false, 0, false);
            if (res1 != null && res1.typ == Types.CLAUSE) 
                res.typ = Types.CHAPTER;
        }
        int specs = 0;
        int _chars = 0;
        if (res.numbers.size() == 2 && com.pullenti.n2j.Utils.stringsEq(res.numbers.get(0), "2") && com.pullenti.n2j.Utils.stringsEq(res.numbers.get(1), "3")) {
        }
        for(tt = (res.numEndToken == null ? res.getBeginToken() : res.numEndToken.getNext()); tt != null; tt = tt.getNext()) {
            if (tt.endChar > res.getEndToken().endChar) 
                break;
            com.pullenti.ner.TextToken tto = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.TextToken.class);
            if (tto == null) 
                continue;
            if (!tto.chars.isLetter()) {
                if (!tto.isCharOf(",;.():") && !com.pullenti.ner.core.BracketHelper.isBracket(tto, false)) 
                    specs += tto.getLengthChar();
            }
            else 
                _chars += tto.getLengthChar();
        }
        if ((specs + _chars) > 0) {
            if ((((specs * 100) / ((specs + _chars)))) > 10) 
                res.hasManySpecChars = true;
        }
        res.isStandardTitle = false;
        int words = 0;
        for(tt = (res.numBeginToken == null ? res.getBeginToken() : res.numBeginToken.getNext()); tt != null && tt.endChar <= res.endChar; tt = tt.getNext()) {
            if (!((tt instanceof com.pullenti.ner.TextToken)) || tt.isChar('_')) {
                res.isStandardTitle = false;
                break;
            }
            if (!tt.chars.isLetter() || tt.getMorph()._getClass().isConjunction() || tt.getMorph()._getClass().isPreposition()) 
                continue;
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (npt != null) {
                words++;
                int ii;
                for(ii = 0; ii < m_StdTitleWords.size(); ii++) {
                    if (npt.noun.isValue(m_StdTitleWords.get(ii), null)) 
                        break;
                }
                if (ii < m_StdTitleWords.size()) {
                    if (com.pullenti.n2j.Utils.stringsEq(m_StdTitleWords.get(ii), "ВВЕДЕНИЕ") || com.pullenti.n2j.Utils.stringsEq(m_StdTitleWords.get(ii), "ВВЕДЕННЯ")) 
                        words++;
                    tt = npt.getEndToken();
                    res.isStandardTitle = true;
                    continue;
                }
                if ((npt.noun.isValue("МОМЕНТ", null) || npt.noun.isValue("ЗАКЛЮЧЕНИЕ", "ВИСНОВОК") || npt.noun.isValue("ДАННЫЕ", null)) || npt.isValue("ДОГОВОР", "ДОГОВІР")) {
                    tt = npt.getEndToken();
                    continue;
                }
            }
            ParticipantToken pp = ParticipantToken.tryAttach(tt, null, null, false);
            if (pp != null && pp.kind == ParticipantToken.Kinds.PURE) {
                tt = pp.getEndToken();
                continue;
            }
            res.isStandardTitle = false;
            break;
        }
        if (words < 2) 
            res.isStandardTitle = false;
        else if ((res.numbers.size() == 0 && !res.isNewlineBefore() && res.getBeginToken().getPrevious() != null) && res.getBeginToken().getPrevious().isTableControlChar()) 
            res.isStandardTitle = false;
        for(t = res.getEndToken().getNext(); t != null; t = t.getNext()) {
            if (!t.isTableControlChar()) 
                break;
            else if (t.isChar((char)0x1E)) 
                break;
            else 
                res.setEndToken(t);
        }
        return res;
    }

    private static java.util.ArrayList<String> m_StdTitleWords = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"РЕКВИЗИТ", "ПОДПИСЬ", "СТОРОНА", "АДРЕС", "ВВЕДЕНИЕ", "ПОЛОЖЕНИЕ", "ТЕЛЕФОН", "МЕСТО", "НАХОЖДЕНИЕ", "МЕСТОНАХОЖДЕНИЕ", "ТЕРМИН", "ОПРЕДЕЛЕНИЕ", "СЧЕТ", "РЕКВІЗИТ", "ПІДПИС", "СТОРОНА", "АДРЕСА", "ВСТУП", "ПОЛОЖЕННЯ", "МІСЦЕ", "ЗНАХОДЖЕННЯ", "МІСЦЕЗНАХОДЖЕННЯ", "ТЕРМІН", "ВИЗНАЧЕННЯ", "РАХУНОК"}));

    private static boolean _isFirstLine(com.pullenti.ner.Token t) {
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return false;
        String v = tt.term;
        if ((((com.pullenti.n2j.Utils.stringsEq(v, "ИСХОДЯ") || com.pullenti.n2j.Utils.stringsEq(v, "ВИХОДЯЧИ"))) && t.getNext() != null && t.getNext().isValue("ИЗ", "З")) && t.getNext().getNext() != null && t.getNext().getNext().isValue("ИЗЛОЖЕННОЕ", "ВИКЛАДЕНЕ")) 
            return true;
        if ((((com.pullenti.n2j.Utils.stringsEq(v, "НА") || com.pullenti.n2j.Utils.stringsEq(v, "HA"))) && t.getNext() != null && t.getNext().isValue("ОСНОВАНИЕ", "ПІДСТАВА")) && t.getNext().getNext() != null && t.getNext().getNext().isValue("ИЗЛОЖЕННОЕ", "ВИКЛАДЕНЕ")) 
            return true;
        if (((com.pullenti.n2j.Utils.stringsEq(v, "УЧИТЫВАЯ") || com.pullenti.n2j.Utils.stringsEq(v, "ВРАХОВУЮЧИ"))) && t.getNext() != null && t.getNext().isValue("ИЗЛОЖЕННОЕ", "ВИКЛАДЕНЕ")) 
            return true;
        if ((com.pullenti.n2j.Utils.stringsEq(v, "ЗАСЛУШАВ") || com.pullenti.n2j.Utils.stringsEq(v, "РАССМОТРЕВ") || com.pullenti.n2j.Utils.stringsEq(v, "ЗАСЛУХАВШИ")) || com.pullenti.n2j.Utils.stringsEq(v, "РОЗГЛЯНУВШИ")) 
            return true;
        if (com.pullenti.n2j.Utils.stringsEq(v, "РУКОВОДСТВУЯСЬ") || com.pullenti.n2j.Utils.stringsEq(v, "КЕРУЮЧИСЬ")) 
            return tt.isNewlineBefore();
        return false;
    }

    public static com.pullenti.ner.Token _createEdition(com.pullenti.ner.Token t) {
        if (t == null || t.getNext() == null) 
            return null;
        boolean ok = false;
        com.pullenti.ner.Token t1 = t;
        int br = 0;
        if (t.isChar('(') && t.isNewlineBefore()) {
            ok = true;
            br = 1;
            t1 = t.getNext();
        }
        if (!ok || t1 == null) 
            return null;
        ok = false;
        java.util.ArrayList<com.pullenti.ner.decree.internal.PartToken> dts = com.pullenti.ner.decree.internal.PartToken.tryAttachList(t1, true, 40);
        if (dts != null && dts.size() > 0) 
            t1 = dts.get(dts.size() - 1).getEndToken().getNext();
        com.pullenti.ner.Token t2 = InstrToken._checkEntered(t1);
        if (t2 == null && t1 != null) 
            t2 = InstrToken._checkEntered(t1.getNext());
        if (t2 != null) 
            ok = true;
        if (!ok) 
            return null;
        for(t1 = t2; t1 != null; t1 = t1.getNext()) {
            if (t1.isChar(')')) {
                if ((--br) == 0) 
                    return t1;
            }
            else if (t1.isChar('(')) 
                br++;
            else if (t1.isNewlineAfter()) 
                break;
        }
        return null;
    }

    public static com.pullenti.ner.Token _checkDirective(com.pullenti.ner.Token t, com.pullenti.n2j.Outargwrapper<String> val) {
        val.value = null;
        if (t == null || t.getMorph()._getClass().isAdjective()) 
            return null;
        for(int ii = 0; ii < InstrToken.m_Directives.size(); ii++) {
            if (t.isValue(InstrToken.m_Directives.get(ii), null)) {
                val.value = InstrToken.m_DirectivesNorm.get(ii);
                if (t.getWhitespacesBeforeCount() < 7) {
                    if (((((com.pullenti.n2j.Utils.stringsNe(val.value, "ПРИКАЗ") && com.pullenti.n2j.Utils.stringsNe(val.value, "ПОСТАНОВЛЕНИЕ") && com.pullenti.n2j.Utils.stringsNe(val.value, "УСТАНОВЛЕНИЕ")) && com.pullenti.n2j.Utils.stringsNe(val.value, "РЕШЕНИЕ") && com.pullenti.n2j.Utils.stringsNe(val.value, "ЗАЯВЛЕНИЕ")) && com.pullenti.n2j.Utils.stringsNe(val.value, "НАКАЗ") && com.pullenti.n2j.Utils.stringsNe(val.value, "ПОСТАНОВА")) && com.pullenti.n2j.Utils.stringsNe(val.value, "ВСТАНОВЛЕННЯ") && com.pullenti.n2j.Utils.stringsNe(val.value, "РІШЕННЯ")) && com.pullenti.n2j.Utils.stringsNe(val.value, "ЗАЯВУ")) {
                        if ((t.getNext() != null && t.getNext().isChar(':') && t.getNext().isNewlineAfter()) && t.chars.isAllUpper()) {
                        }
                        else 
                            break;
                    }
                }
                if (t.getNext() != null && t.getNext().isValue("СЛЕДУЮЩЕЕ", "НАСТУПНЕ")) 
                    return t.getNext();
                if (((com.pullenti.n2j.Utils.stringsEq(val.value, "ЗАЯВЛЕНИЕ") || com.pullenti.n2j.Utils.stringsEq(val.value, "ЗАЯВА"))) && t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.org.OrganizationReferent)) 
                    t = t.getNext();
                return t;
            }
        }
        if (t.chars.isLetter() && t.getLengthChar() == 1) {
            if (t.isNewlineBefore() || ((t.getNext() != null && t.getNext().chars.isLetter() && t.getNext().getLengthChar() == 1))) {
                for(int ii = 0; ii < InstrToken.m_Directives.size(); ii++) {
                    com.pullenti.ner.Token res = com.pullenti.ner.core.MiscHelper.tryAttachWordByLetters(InstrToken.m_Directives.get(ii), t, true);
                    if (res != null) {
                        val.value = InstrToken.m_DirectivesNorm.get(ii);
                        return res;
                    }
                }
            }
        }
        return null;
    }

    public int getTypContainerRank() {
        int res = _calcRank(typ);
        return res;
    }


    public static int _calcRank(Types ty) {
        if (ty == Types.DOCPART) 
            return 1;
        if (ty == Types.SECTION) 
            return 2;
        if (ty == Types.SUBSECTION) 
            return 3;
        if (ty == Types.CHAPTER) 
            return 4;
        if (ty == Types.PARAGRAPH) 
            return 5;
        if (ty == Types.SUBPARAGRAPH) 
            return 6;
        if (ty == Types.CLAUSE) 
            return 7;
        return 0;
    }

    public boolean canBeContainerFor(InstrToken1 lt) {
        int r = _calcRank(typ);
        int r1 = _calcRank(lt.typ);
        if (r > 0 && r1 > 0) 
            return r < r1;
        return false;
    }

    public static class Types implements Comparable<Types> {
    
        public static final Types LINE; // 0
    
        public static final Types FIRSTLINE; // 1
    
        public static final Types SIGNS; // 2
    
        public static final Types APPENDIX; // 3
    
        public static final Types APPROVED; // 4
    
        public static final Types BASE; // 5
    
        public static final Types INDEX; // 6
    
        public static final Types TITLE; // 7
    
        public static final Types DIRECTIVE; // 8
    
        public static final Types CHAPTER; // 9
    
        public static final Types CLAUSE; // 10
    
        public static final Types DOCPART; // 11
    
        public static final Types SECTION; // 12
    
        public static final Types SUBSECTION; // 13
    
        public static final Types PARAGRAPH; // 14
    
        public static final Types SUBPARAGRAPH; // 15
    
        public static final Types CLAUSEPART; // 16
    
        public static final Types EDITIONS; // 17
    
        public static final Types COMMENT; // 18
    
        public static final Types NOTICE; // 19
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private Types(int val, String str) { m_val = val; m_str = str; }
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
        public int compareTo(Types v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, Types> mapIntToEnum; 
        private static java.util.HashMap<String, Types> mapStringToEnum; 
        public static Types of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            Types item = new Types(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static Types of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        static {
            mapIntToEnum = new java.util.HashMap< >();
            mapStringToEnum = new java.util.HashMap< >();
            LINE = new Types(0, "LINE");
            mapIntToEnum.put(LINE.value(), LINE);
            mapStringToEnum.put(LINE.m_str.toUpperCase(), LINE);
            FIRSTLINE = new Types(1, "FIRSTLINE");
            mapIntToEnum.put(FIRSTLINE.value(), FIRSTLINE);
            mapStringToEnum.put(FIRSTLINE.m_str.toUpperCase(), FIRSTLINE);
            SIGNS = new Types(2, "SIGNS");
            mapIntToEnum.put(SIGNS.value(), SIGNS);
            mapStringToEnum.put(SIGNS.m_str.toUpperCase(), SIGNS);
            APPENDIX = new Types(3, "APPENDIX");
            mapIntToEnum.put(APPENDIX.value(), APPENDIX);
            mapStringToEnum.put(APPENDIX.m_str.toUpperCase(), APPENDIX);
            APPROVED = new Types(4, "APPROVED");
            mapIntToEnum.put(APPROVED.value(), APPROVED);
            mapStringToEnum.put(APPROVED.m_str.toUpperCase(), APPROVED);
            BASE = new Types(5, "BASE");
            mapIntToEnum.put(BASE.value(), BASE);
            mapStringToEnum.put(BASE.m_str.toUpperCase(), BASE);
            INDEX = new Types(6, "INDEX");
            mapIntToEnum.put(INDEX.value(), INDEX);
            mapStringToEnum.put(INDEX.m_str.toUpperCase(), INDEX);
            TITLE = new Types(7, "TITLE");
            mapIntToEnum.put(TITLE.value(), TITLE);
            mapStringToEnum.put(TITLE.m_str.toUpperCase(), TITLE);
            DIRECTIVE = new Types(8, "DIRECTIVE");
            mapIntToEnum.put(DIRECTIVE.value(), DIRECTIVE);
            mapStringToEnum.put(DIRECTIVE.m_str.toUpperCase(), DIRECTIVE);
            CHAPTER = new Types(9, "CHAPTER");
            mapIntToEnum.put(CHAPTER.value(), CHAPTER);
            mapStringToEnum.put(CHAPTER.m_str.toUpperCase(), CHAPTER);
            CLAUSE = new Types(10, "CLAUSE");
            mapIntToEnum.put(CLAUSE.value(), CLAUSE);
            mapStringToEnum.put(CLAUSE.m_str.toUpperCase(), CLAUSE);
            DOCPART = new Types(11, "DOCPART");
            mapIntToEnum.put(DOCPART.value(), DOCPART);
            mapStringToEnum.put(DOCPART.m_str.toUpperCase(), DOCPART);
            SECTION = new Types(12, "SECTION");
            mapIntToEnum.put(SECTION.value(), SECTION);
            mapStringToEnum.put(SECTION.m_str.toUpperCase(), SECTION);
            SUBSECTION = new Types(13, "SUBSECTION");
            mapIntToEnum.put(SUBSECTION.value(), SUBSECTION);
            mapStringToEnum.put(SUBSECTION.m_str.toUpperCase(), SUBSECTION);
            PARAGRAPH = new Types(14, "PARAGRAPH");
            mapIntToEnum.put(PARAGRAPH.value(), PARAGRAPH);
            mapStringToEnum.put(PARAGRAPH.m_str.toUpperCase(), PARAGRAPH);
            SUBPARAGRAPH = new Types(15, "SUBPARAGRAPH");
            mapIntToEnum.put(SUBPARAGRAPH.value(), SUBPARAGRAPH);
            mapStringToEnum.put(SUBPARAGRAPH.m_str.toUpperCase(), SUBPARAGRAPH);
            CLAUSEPART = new Types(16, "CLAUSEPART");
            mapIntToEnum.put(CLAUSEPART.value(), CLAUSEPART);
            mapStringToEnum.put(CLAUSEPART.m_str.toUpperCase(), CLAUSEPART);
            EDITIONS = new Types(17, "EDITIONS");
            mapIntToEnum.put(EDITIONS.value(), EDITIONS);
            mapStringToEnum.put(EDITIONS.m_str.toUpperCase(), EDITIONS);
            COMMENT = new Types(18, "COMMENT");
            mapIntToEnum.put(COMMENT.value(), COMMENT);
            mapStringToEnum.put(COMMENT.m_str.toUpperCase(), COMMENT);
            NOTICE = new Types(19, "NOTICE");
            mapIntToEnum.put(NOTICE.value(), NOTICE);
            mapStringToEnum.put(NOTICE.m_str.toUpperCase(), NOTICE);
        }
    }


    public static InstrToken1 _new1381(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Types _arg3) {
        InstrToken1 res = new InstrToken1(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }
    public static InstrToken1 _new1383(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3) {
        InstrToken1 res = new InstrToken1(_arg1, _arg2);
        res.allUpper = _arg3;
        return res;
    }
    public static InstrToken1 _new1387(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Types _arg3, String _arg4) {
        InstrToken1 res = new InstrToken1(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        return res;
    }
    public InstrToken1() {
        super();
        if(_globalInstance == null) return;
        typ = Types.LINE;
    }
    public static InstrToken1 _globalInstance;
    static {
        _globalInstance = new InstrToken1(); 
    }
}
