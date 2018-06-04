/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature.internal;

public class PeaceToken extends com.pullenti.ner.MetaToken {

    public PeaceToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public boolean isText;

    public com.pullenti.ner.literature.TextPeaceType typ = com.pullenti.ner.literature.TextPeaceType.UNDEFINED;

    public String number;

    public String name;

    public boolean hasKeyword;

    private com.pullenti.ner.literature.TextPeaceReferent ref;

    private com.pullenti.ner.titlepage.TitlePageReferent titleRef;

    public int getLevel() {
        if (typ == com.pullenti.ner.literature.TextPeaceType.INTRO || typ == com.pullenti.ner.literature.TextPeaceType.CONCLUSION || typ == com.pullenti.ner.literature.TextPeaceType.REMARKS) 
            return 0;
        if (typ == com.pullenti.ner.literature.TextPeaceType.BOOK) 
            return 1;
        if (typ == com.pullenti.ner.literature.TextPeaceType.VOLUME) 
            return 2;
        if (typ == com.pullenti.ner.literature.TextPeaceType.PART) 
            return 3;
        if (typ == com.pullenti.ner.literature.TextPeaceType.CHAPTER) 
            return 4;
        return -1;
    }


    @Override
    public String toString() {
        if (isText) 
            return "Text";
        StringBuilder tmp = new StringBuilder();
        tmp.append(getLevel()).append(":");
        if (typ != com.pullenti.ner.literature.TextPeaceType.UNDEFINED) 
            tmp.append(" ").append(typ.toString());
        if (number != null) 
            tmp.append(" N=").append(number);
        if (name != null) 
            tmp.append(" \"").append(name).append("\"");
        return tmp.toString();
    }

    /**
     * Получить часть, которой принадлежит токен
     * @param t 
     * @return 
     */
    public static PeaceToken getPeace(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        if (t.tag instanceof PeaceToken) 
            return (PeaceToken)com.pullenti.n2j.Utils.cast(t.tag, PeaceToken.class);
        if ((t.tag instanceof com.pullenti.ner.Token) && ((((com.pullenti.ner.Token)com.pullenti.n2j.Utils.cast(t.tag, com.pullenti.ner.Token.class))).tag instanceof PeaceToken)) 
            return (PeaceToken)com.pullenti.n2j.Utils.cast((((com.pullenti.ner.Token)com.pullenti.n2j.Utils.cast(t.tag, com.pullenti.ner.Token.class))).tag, PeaceToken.class);
        if (t instanceof com.pullenti.ner.MetaToken) {
            for(com.pullenti.ner.Token tt = (((com.pullenti.ner.MetaToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.MetaToken.class))).getBeginToken(); tt != null && tt.endChar <= t.endChar; tt = tt.getNext()) {
                PeaceToken res = getPeace(tt);
                if (res != null) 
                    return res;
            }
        }
        return null;
    }

    /**
     * Проверка, что оба токена из одной части
     * @param t1 
     * @param t2 
     * @return 
     */
    public static boolean isInOnePeace(com.pullenti.ner.Token t1, com.pullenti.ner.Token t2) {
        PeaceToken p1 = getPeace(t1);
        PeaceToken p2 = getPeace(t2);
        return p1 != null && p1 == p2;
    }

    private void _markTermsTag() {
        for(com.pullenti.ner.Token t = getBeginToken(); t != null && t.endChar <= endChar; t = t.getNext()) {
            t.tag = this;
        }
    }

    private static com.pullenti.ner.Token _gotoEndOfLine(com.pullenti.ner.Token t) {
        for(com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            if (tt.isNewlineAfter()) 
                return tt;
        }
        return null;
    }

    public static java.util.ArrayList<ChapterTextToken> createReferents(com.pullenti.ner.core.AnalyzerData dat, com.pullenti.ner.Token t0) {
        java.util.ArrayList<ChapterTextToken> res = new java.util.ArrayList< >();
        java.util.ArrayList<PeaceToken> ptts = tryParseAll(t0);
        ChapterTextToken ctt;
        com.pullenti.ner.Token t1 = null;
        for(com.pullenti.ner.Token tt = t0; tt != null; tt = tt.getNext()) {
            if (ptts.size() > 0) {
                if (tt.beginChar >= ptts.get(0).beginChar) 
                    break;
            }
            com.pullenti.ner.Token tt1 = _gotoEndOfLine(tt);
            if (tt1 == null) 
                break;
            if ((tt1.endChar - tt.endChar) > 100) 
                break;
            t1 = tt1;
            tt = tt1;
        }
        boolean hasHead = false;
        if (t1 != null && t1.getNext() != null) {
            PeaceToken ptt0 = _new1547(t0, t1, false, com.pullenti.ner.literature.TextPeaceType.UNDEFINED);
            ptt0._markTermsTag();
            com.pullenti.ner.literature.TextPeaceReferent r = com.pullenti.ner.literature.TextPeaceReferent._new1548(com.pullenti.ner.literature.TextPeaceKind.HEAD);
            r.addOccurence(new com.pullenti.ner.TextAnnotation(t0, t1, r));
            dat.registerReferent(r);
            ptt0.ref = r;
            hasHead = true;
            _analyzeHead(t0, t1, r);
            t0 = t1.getNext();
        }
        if (ptts.size() == 0) {
            PeaceToken ptt = _new1549(t0, t0, true);
            for(com.pullenti.ner.Token tt = t0; tt != null; tt = tt.getNext()) {
                tt.tag = ptt;
                ptt.setEndToken(tt);
            }
            ctt = ChapterTextToken._new1550(ptt.getBeginToken(), ptt.getEndToken(), "Весь текст");
            res.add(ctt);
            if (hasHead) {
                com.pullenti.ner.literature.TextPeaceReferent r = com.pullenti.ner.literature.TextPeaceReferent._new1548(com.pullenti.ner.literature.TextPeaceKind.TEXT);
                r.addOccurence(new com.pullenti.ner.TextAnnotation(ptt.getBeginToken(), ptt.getEndToken(), r));
                dat.registerReferent(r);
                ptt.ref = r;
            }
            return res;
        }
        if (ptts.get(0).getBeginToken().getPrevious() != null) {
            if (t0.beginChar < ptts.get(0).getBeginToken().getPrevious().beginChar) {
                com.pullenti.ner.literature.TextPeaceReferent r = com.pullenti.ner.literature.TextPeaceReferent._new1548(com.pullenti.ner.literature.TextPeaceKind.TEXT);
                if (!hasHead) {
                    if ((((ptts.get(0).getBeginToken().getPrevious().endChar - t0.beginChar)) < 1000) || (ptts.get(0).getBeginToken().getPrevious().endChar < ((dat.kit.getSofa().getText().length() / 10)))) 
                        r.setKind(com.pullenti.ner.literature.TextPeaceKind.HEAD);
                }
                r.addOccurence(new com.pullenti.ner.TextAnnotation(t0, ptts.get(0).getBeginToken().getPrevious(), r));
                dat.registerReferent(r);
                PeaceToken ptt = new PeaceToken(t0, ptts.get(0).getBeginToken().getPrevious());
                if (r.getKind() != com.pullenti.ner.literature.TextPeaceKind.HEAD) {
                    ptt.isText = true;
                    res.add(ChapterTextToken._new1550(ptt.getBeginToken(), ptt.getEndToken(), "Безымянный"));
                }
                else 
                    ptt.isText = false;
                ptt._markTermsTag();
            }
        }
        if ((ptts.size() > 2 && ptts.get(0).number == null && ptts.get(0).getLevel() > ptts.get(1).getLevel()) && com.pullenti.n2j.Utils.stringsEq(ptts.get(1).number, "1")) 
            ptts.get(0).typ = com.pullenti.ner.literature.TextPeaceType.INTRO;
        for(int i = 1; (i < ptts.size()) && (i < 4); i++) {
            if (ptts.get(i).hasKeyword && (ptts.get(i).getLevel() < ptts.get(i - 1).getLevel()) && !ptts.get(i - 1).hasKeyword) {
                if (i > 1) {
                    ptts.remove(i - 1);
                    i--;
                }
            }
        }
        java.util.ArrayList<PeaceToken> stack = new java.util.ArrayList< >();
        for(int i = 0; i < ptts.size(); i++) {
            com.pullenti.ner.literature.TextPeaceReferent r = com.pullenti.ner.literature.TextPeaceReferent._new1554(com.pullenti.ner.literature.TextPeaceKind.TITLE, ptts.get(i).typ);
            if (ptts.get(i).number != null) 
                r.setNumber(ptts.get(i).number);
            if (ptts.get(i).name != null) 
                r.setName(ptts.get(i).name);
            r.addOccurence(new com.pullenti.ner.TextAnnotation(ptts.get(i).getBeginToken(), ptts.get(i).getEndToken(), r));
            dat.registerReferent(r);
            ptts.get(i).ref = r;
            ptts.get(i)._markTermsTag();
            if (ptts.get(i).getLevel() == 0) 
                stack.clear();
            else {
                int j;
                boolean ok = false;
                for(j = 0; j < stack.size(); j++) {
                    if (stack.get(j).getLevel() == ptts.get(i).getLevel()) {
                        com.pullenti.n2j.Utils.putArrayValue(stack, j, ptts.get(i));
                        ok = true;
                        if (j > 0) 
                            r.setOwner(stack.get(j - 1).ref);
                        if ((j + 1) > stack.size()) 
                            for(int indRemoveRange = j + 1 + stack.size() - j - 1 - 1, indMinIndex = j + 1; indRemoveRange >= indMinIndex; indRemoveRange--) stack.remove(indRemoveRange);
                        break;
                    }
                    else if (stack.get(j).getLevel() > ptts.get(i).getLevel()) {
                        for(int indRemoveRange = j + stack.size() - 1 - 1, indMinIndex = j; indRemoveRange >= indMinIndex; indRemoveRange--) stack.remove(indRemoveRange);
                        break;
                    }
                }
                if (!ok) {
                    if (stack.size() > 0) 
                        r.setOwner(stack.get(stack.size() - 1).ref);
                    stack.add(ptts.get(i));
                }
            }
            t1 = null;
            if ((i + 1) < ptts.size()) 
                t1 = ptts.get(i + 1).getBeginToken().getPrevious();
            else 
                for(com.pullenti.ner.Token tt = ptts.get(i).getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                    t1 = tt;
                }
            if (t1 == null || ptts.get(i).getEndToken() == t1) 
                continue;
            com.pullenti.ner.literature.TextPeaceReferent rr = com.pullenti.ner.literature.TextPeaceReferent._new1555(com.pullenti.ner.literature.TextPeaceKind.TEXT, r);
            rr.addOccurence(new com.pullenti.ner.TextAnnotation(ptts.get(i).getEndToken().getNext(), t1, rr));
            dat.registerReferent(rr);
            PeaceToken ptt = _new1549(ptts.get(i).getEndToken().getNext(), t1, true);
            ptt._markTermsTag();
            res.add(ChapterTextToken._new1550(ptt.getBeginToken(), ptt.getEndToken(), r.toString()));
        }
        return res;
    }

    public static java.util.ArrayList<PeaceToken> tryParseAll(com.pullenti.ner.Token first) {
        java.util.ArrayList<PeaceToken> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Token t = first; t != null; t = t.getNext()) {
            if (!t.isNewlineBefore()) 
                continue;
            PeaceToken ptt = tryParse(t, (res.size() > 0 ? res.get(res.size() - 1) : null));
            if (ptt != null) {
                res.add(ptt);
                t = ptt.getEndToken();
            }
        }
        for(int i = 0; i < (res.size() - 1); i++) {
            if (res.get(i).typ == com.pullenti.ner.literature.TextPeaceType.CHAPTER && !res.get(i).hasKeyword && res.get(i + 1).typ == com.pullenti.ner.literature.TextPeaceType.INTRO) {
                res.remove(i);
                i--;
            }
        }
        int notNumChap = 0;
        int numChap = 0;
        for(PeaceToken r : res) {
            if (r.typ == com.pullenti.ner.literature.TextPeaceType.CHAPTER && !r.hasKeyword && r.number == null) 
                notNumChap++;
            else if (r.typ == com.pullenti.ner.literature.TextPeaceType.CHAPTER && r.number != null) 
                numChap++;
        }
        boolean ok = false;
        if (numChap > 0) {
        }
        else if (notNumChap > 3 && (notNumChap < 100)) 
            ok = true;
        if (!ok) {
            for(int i = res.size() - 1; i >= 0; i--) {
                if (res.get(i).typ == com.pullenti.ner.literature.TextPeaceType.CHAPTER && !res.get(i).hasKeyword && res.get(i).number == null) 
                    res.remove(i);
            }
        }
        if (res.size() == 1 && ((res.get(0).typ == com.pullenti.ner.literature.TextPeaceType.INTRO || res.get(0).typ == com.pullenti.ner.literature.TextPeaceType.CONCLUSION))) 
            res.clear();
        return res;
    }

    private static PeaceToken tryParse(com.pullenti.ner.Token t, PeaceToken prev) {
        if (t == null) 
            return null;
        if (!t.isNewlineBefore()) 
            return null;
        PeaceToken res = null;
        com.pullenti.ner.NumberToken num = com.pullenti.ner.core.NumberHelper.tryParseRoman(t);
        if (num != null) {
            com.pullenti.ner.Token tt = num.getEndToken();
            if (tt.getNext() != null && tt.getNext().isChar('.')) 
                tt = tt.getNext();
            if (tt.isNewlineAfter()) {
                if (prev != null && prev.typ == com.pullenti.ner.literature.TextPeaceType.CHAPTER && prev.hasKeyword) 
                    return null;
                res = _new1558(t, tt, com.pullenti.ner.literature.TextPeaceType.CHAPTER, ((Long)num.value).toString());
                return res;
            }
        }
        com.pullenti.ner.core.TerminToken tok = m_Terms.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok == null) {
            if (t.isChar('*')) 
                tok = m_Terms.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
            else if (num != null) 
                tok = m_Terms.tryParse(num.getEndToken().getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
        }
        if (tok != null && !t.chars.isAllLower()) {
            if (t.getNewlinesBeforeCount() == 1) {
                if (prev != null && prev.getEndToken().getNext() == t) {
                }
                else if (tok.getBeginToken().chars.isAllUpper()) {
                }
                else 
                    return null;
            }
            res = _new1559(t, tok.getEndToken(), (com.pullenti.ner.literature.TextPeaceType)tok.termin.tag, true);
            if (num != null) 
                res.number = ((Long)num.value).toString();
        }
        if (res == null) {
            if (t.getNewlinesBeforeCount() > 1) {
                if (t.getPrevious() != null && t.getPrevious().isChar(':')) {
                }
                else {
                    com.pullenti.ner.Token tt = _tryCheckAllTokensUpper(t);
                    if (tt != null && tt.getNewlinesAfterCount() > 1) {
                        if ((prev != null && prev.getEndToken().getNext() == t && prev.typ == com.pullenti.ner.literature.TextPeaceType.CHAPTER) && prev.number == null && !prev.hasKeyword) 
                            return null;
                        res = _new1560(t, tt, com.pullenti.ner.literature.TextPeaceType.CHAPTER);
                        res.name = com.pullenti.ner.core.MiscHelper.getTextValue(t, tt, com.pullenti.ner.core.GetTextAttr.KEEPQUOTES);
                        return res;
                    }
                }
            }
            return null;
        }
        t = res.getEndToken();
        com.pullenti.ner.Token tnam0 = null;
        if (res.typ == com.pullenti.ner.literature.TextPeaceType.INTRO) {
            if (prev != null && prev.typ != com.pullenti.ner.literature.TextPeaceType.INTRO && prev.hasKeyword) 
                return null;
            if (!t.isNewlineAfter()) {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                if (npt != null) 
                    t = res.setEndToken(npt.getEndToken());
            }
        }
        else if (res.typ == com.pullenti.ner.literature.TextPeaceType.CONCLUSION || res.typ == com.pullenti.ner.literature.TextPeaceType.REMARKS) {
            if (prev == null) 
                return null;
        }
        else {
            if (t.getNext() instanceof com.pullenti.ner.NumberToken) {
                res.number = ((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class))).value).toString();
                if (com.pullenti.n2j.Utils.stringsEq(res.number, "15")) {
                }
                t = res.setEndToken(t.getNext());
            }
            else {
                num = com.pullenti.ner.core.NumberHelper.tryParseRoman(t.getNext());
                if (num != null) {
                    res.number = ((Long)num.value).toString();
                    t = res.setEndToken(num.getEndToken());
                }
                else if ((!t.isNewlineAfter() && (t.getNext() instanceof com.pullenti.ner.TextToken) && ((t.getNext().getMorph()._getClass().isAdjective() || t.getNext().getMorph()._getClass().isPronoun()))) && t.getNext().getMorph().getCase().isNominative() && t.getNext().getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE) {
                    res.number = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class))).term;
                    t = res.setEndToken(t.getNext());
                }
            }
            boolean hasPoint = false;
            if (t.getNext() != null && t.getNext().isCharOf(".)")) {
                t = res.setEndToken(t.getNext());
                hasPoint = true;
            }
            if (res.number == null) {
                if (res.typ == com.pullenti.ner.literature.TextPeaceType.BOOK && !res.isNewlineAfter()) {
                }
                else 
                    return null;
            }
            if (!t.isNewlineAfter() && t.getNext() != null) {
                if (t.getNext().chars.isAllLower()) {
                    if (!hasPoint) 
                        return null;
                }
                if (!t.getNext().isComma()) 
                    tnam0 = t.getNext();
            }
            else if (t.getNewlinesAfterCount() == 1 && t.getNext() != null) {
                if (t.getNext().chars.isAllUpper() && t.getNext().getLengthChar() > 2) 
                    tnam0 = t.getNext();
                else if (prev != null && prev.typ == res.typ && prev.name != null) 
                    tnam0 = t.getNext();
                else if (com.pullenti.ner.core.BracketHelper.isBracket(t.getNext(), true) || ((t.getNext().chars.isLetter() && !t.getNext().chars.isAllLower()))) {
                    for(com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                        if (tt.isNewlineAfter()) {
                            if (tt.isCharOf(".:")) {
                            }
                            else if ((tt.endChar - t.getNext().beginChar) < 200) 
                                tnam0 = t.getNext();
                            break;
                        }
                    }
                }
            }
        }
        if (tnam0 == null && t.getNext() != null && t.getNext().isComma()) 
            tnam0 = t.getNext().getNext();
        if (tnam0 != null) {
            com.pullenti.ner.Token tnam1 = tnam0;
            int allUp = 0;
            int total = 0;
            for(com.pullenti.ner.Token tt = tnam0; tt != null; tt = tt.getNext()) {
                if (tt.isNewlineBefore() && tt != tnam0) {
                    if (tt.getNewlinesBeforeCount() > 1) 
                        break;
                    if (((allUp * 2) > total && total > 7 && tt.chars.isAllUpper()) && tt.getLengthChar() > 1) {
                    }
                    else 
                        break;
                }
                tnam1 = tt;
                if ((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isLetter() && tt.getLengthChar() > 1) {
                    total++;
                    if (tt.chars.isAllUpper()) 
                        allUp++;
                }
            }
            if (tnam1 != null) {
                t = res.setEndToken(tnam1);
                res.name = com.pullenti.ner.core.MiscHelper.getTextValue(tnam0, tnam1, com.pullenti.ner.core.GetTextAttr.KEEPQUOTES);
            }
        }
        if (res.typ == com.pullenti.ner.literature.TextPeaceType.INTRO || res.typ == com.pullenti.ner.literature.TextPeaceType.CONCLUSION || res.typ == com.pullenti.ner.literature.TextPeaceType.REMARKS) {
            if (res.name == null) 
                res.name = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(res, com.pullenti.ner.core.GetTextAttr.KEEPQUOTES);
        }
        if (res.name != null && res.name.charAt(res.name.length() - 1) == '*') 
            res.name = res.name.substring(0, 0+(res.name.length() - 1)).trim();
        return res;
    }

    private static com.pullenti.ner.Token _tryCheckAllTokensUpper(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        if (t.isHiphen()) 
            return null;
        int up = 0;
        int lower = 0;
        for(com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            if ((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isLetter()) {
                if (tt.chars.isAllLower()) {
                    if (up == 0) 
                        return null;
                    lower += tt.getLengthChar();
                }
                else 
                    up += tt.getLengthChar();
            }
            if (tt.isNewlineAfter()) {
                if (lower == 0) 
                    return (up > 3 ? tt : null);
                if (tt.chars.isLetter()) 
                    return ((up + lower) < 50 ? tt : null);
                return null;
            }
        }
        return null;
    }

    private static void _analyzeHead(com.pullenti.ner.Token b, com.pullenti.ner.Token e, com.pullenti.ner.literature.TextPeaceReferent head) {
        com.pullenti.ner.Token t0 = b;
        while(true) {
            if (t0.endChar > e.endChar) 
                return;
            java.util.ArrayList<com.pullenti.ner.person.internal.PersonItemToken> pats = com.pullenti.ner.person.internal.PersonItemToken.tryAttachList(t0, null, com.pullenti.ner.person.internal.PersonItemToken.ParseAttr.CANBELATIN, 10);
            if (pats != null) {
                for(int i = 0; i < (pats.size() - 1); i++) {
                    if (pats.get(i).isNewlineAfter()) {
                        for(int indRemoveRange = i + 1 + pats.size() - i - 1 - 1, indMinIndex = i + 1; indRemoveRange >= indMinIndex; indRemoveRange--) pats.remove(indRemoveRange);
                        break;
                    }
                }
            }
            com.pullenti.ner.Token tt = (pats == null ? null : pats.get(pats.size() - 1).getEndToken());
            if (pats != null && ((pats.size() == 2 || pats.size() == 3)) && pats.get(pats.size() - 1).endChar <= e.endChar) {
                tt = pats.get(pats.size() - 1).getEndToken();
                if ((tt != null && !tt.isNewlineAfter() && (pats.size() < 3)) && (tt.getNext() instanceof com.pullenti.ner.TextToken) && com.pullenti.morph.CharsInfo.ooEq(tt.getNext().chars, tt.chars)) 
                    tt = tt.getNext();
                if (tt.isNewlineAfter() || ((tt.getNext() != null && tt.getNext().isCharOf(".,&")))) {
                    String auth = com.pullenti.ner.core.MiscHelper.getTextValue(t0, tt, com.pullenti.ner.core.GetTextAttr.NO);
                    head.addSlot(com.pullenti.ner.literature.TextPeaceReferent.ATTR_AUTHOR, auth, false, 0);
                    t0 = tt.getNext();
                    if (t0 != null && t0.isChar('.')) 
                        t0 = t0.getNext();
                }
            }
            if (t0 == null) 
                return;
            if (t0.isCharOf(",&") && !t0.isNewlineAfter()) {
                t0 = t0.getNext();
                continue;
            }
            break;
        }
        com.pullenti.ner.Token te = _gotoEndOfLine(t0);
        if (te == null || te.endChar > e.endChar) 
            return;
        String _name = com.pullenti.ner.core.MiscHelper.getTextValue(t0, te, com.pullenti.ner.core.GetTextAttr.NO);
        head.setName(_name);
    }

    private static com.pullenti.ner.core.TerminCollection m_Terms;

    public static PeaceToken _new1547(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3, com.pullenti.ner.literature.TextPeaceType _arg4) {
        PeaceToken res = new PeaceToken(_arg1, _arg2);
        res.isText = _arg3;
        res.typ = _arg4;
        return res;
    }
    public static PeaceToken _new1549(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3) {
        PeaceToken res = new PeaceToken(_arg1, _arg2);
        res.isText = _arg3;
        return res;
    }
    public static PeaceToken _new1558(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.literature.TextPeaceType _arg3, String _arg4) {
        PeaceToken res = new PeaceToken(_arg1, _arg2);
        res.typ = _arg3;
        res.number = _arg4;
        return res;
    }
    public static PeaceToken _new1559(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.literature.TextPeaceType _arg3, boolean _arg4) {
        PeaceToken res = new PeaceToken(_arg1, _arg2);
        res.typ = _arg3;
        res.hasKeyword = _arg4;
        return res;
    }
    public static PeaceToken _new1560(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.literature.TextPeaceType _arg3) {
        PeaceToken res = new PeaceToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }
    public PeaceToken() {
        super();
    }
    static {
        m_Terms = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t;
        t = com.pullenti.ner.core.Termin._new118("КНИГА", com.pullenti.ner.literature.TextPeaceType.BOOK);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new118("ТОМ", com.pullenti.ner.literature.TextPeaceType.VOLUME);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new118("ЧАСТЬ", com.pullenti.ner.literature.TextPeaceType.PART);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new118("ГЛАВА", com.pullenti.ner.literature.TextPeaceType.CHAPTER);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new118("ВВЕДЕНИЕ", com.pullenti.ner.literature.TextPeaceType.INTRO);
        t.addVariant("ПРЕДИСЛОВИЕ", false);
        t.addVariant("ПРОЛОГ", false);
        t.addVariant("ОТ АВТОРА", false);
        t.addVariant("АННОТАЦИЯ", false);
        t.addVariant("ANNOTATION", false);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new118("ЗАКЛЮЧЕНИЕ", com.pullenti.ner.literature.TextPeaceType.CONCLUSION);
        t.addVariant("ЭПИЛОГ", false);
        t.addVariant("ПОСЛЕСЛОВИЕ", false);
        m_Terms.add(t);
        t = com.pullenti.ner.core.Termin._new118("ПРИМЕЧАНИЯ", com.pullenti.ner.literature.TextPeaceType.REMARKS);
        t.addVariant("ЗАМЕЧАНИЯ", false);
        m_Terms.add(t);
    }
}
