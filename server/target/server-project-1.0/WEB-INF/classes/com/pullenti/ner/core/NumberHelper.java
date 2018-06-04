/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.core;

/**
 * Работа с числовыми значениями
 */
public class NumberHelper {

    /**
     * Попробовать создать числительное
     * @param token 
     * @return 
     */
    public static com.pullenti.ner.NumberToken _tryParse(com.pullenti.ner.Token token) {
        return _TryParse(token, (long)-1);
    }

    private static com.pullenti.ner.NumberToken _TryParse(com.pullenti.ner.Token token, long prevVal) {
        if (token instanceof com.pullenti.ner.NumberToken) 
            return (com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(token, com.pullenti.ner.NumberToken.class);
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(token, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return null;
        com.pullenti.ner.TextToken et = tt;
        long val = (long)-1;
        com.pullenti.ner.NumberSpellingType typ = com.pullenti.ner.NumberSpellingType.DIGIT;
        String term = tt.term;
        int i;
        int j;
        if (Character.isDigit(term.charAt(0))) {
            com.pullenti.n2j.Outargwrapper<Long> inoutarg555 = new com.pullenti.n2j.Outargwrapper< >();
            boolean inoutres556 = com.pullenti.n2j.Utils.parseLong(term, inoutarg555);
            val = (inoutarg555.value != null ? inoutarg555.value : 0);
            if (!inoutres556) 
                return null;
        }
        if (val >= ((long)0)) {
            boolean hiph = false;
            if ((et.getNext() instanceof com.pullenti.ner.TextToken) && et.getNext().isHiphen()) {
                if ((et.getWhitespacesAfterCount() < 2) && (et.getNext().getWhitespacesAfterCount() < 2)) {
                    et = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class);
                    hiph = true;
                }
            }
            com.pullenti.ner.MorphCollection mc = null;
            if (hiph || !et.isWhitespaceAfter()) {
                com.pullenti.ner.MetaToken rr = analizeNumberTail((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class), val);
                if (rr == null) 
                    et = tt;
                else {
                    mc = rr.getMorph();
                    et = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(rr.getEndToken(), com.pullenti.ner.TextToken.class);
                }
            }
            else 
                et = tt;
            if (et.getNext() != null && et.getNext().isChar('(')) {
                com.pullenti.ner.NumberToken num2 = _tryParse(et.getNext().getNext());
                if ((num2 != null && num2.value == val && num2.getEndToken().getNext() != null) && num2.getEndToken().getNext().isChar(')')) 
                    et = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(num2.getEndToken().getNext(), com.pullenti.ner.TextToken.class);
            }
            while((et.getNext() instanceof com.pullenti.ner.TextToken) && !((et.getPrevious() instanceof com.pullenti.ner.NumberToken)) && et.isWhitespaceBefore()) {
                if (et.getWhitespacesAfterCount() != 1) 
                    break;
                String sss = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class))).term;
                if (com.pullenti.n2j.Utils.stringsEq(sss, "000")) {
                    val *= ((long)1000);
                    et = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class);
                    continue;
                }
                if (Character.isDigit(sss.charAt(0)) && sss.length() == 3) {
                    long val2 = val;
                    for(com.pullenti.ner.Token ttt = et.getNext(); ttt != null; ttt = ttt.getNext()) {
                        String ss = ttt.getSourceText();
                        if (ttt.getWhitespacesBeforeCount() == 1 && ttt.getLengthChar() == 3 && Character.isDigit(ss.charAt(0))) {
                            int ii;
                            com.pullenti.n2j.Outargwrapper<Integer> inoutarg557 = new com.pullenti.n2j.Outargwrapper< >();
                            boolean inoutres558 = com.pullenti.n2j.Utils.parseInteger(ss, inoutarg557);
                            ii = (inoutarg557.value != null ? inoutarg557.value : 0);
                            if (!inoutres558) 
                                break;
                            val2 *= ((long)1000);
                            val2 += ((long)ii);
                            continue;
                        }
                        if ((ttt.isCharOf(".,") && !ttt.isWhitespaceBefore() && !ttt.isWhitespaceAfter()) && ttt.getNext() != null && Character.isDigit(ttt.getNext().getSourceText().charAt(0))) {
                            if (ttt.getNext().isWhitespaceAfter() && (ttt.getPrevious() instanceof com.pullenti.ner.TextToken)) {
                                et = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(ttt.getPrevious(), com.pullenti.ner.TextToken.class);
                                val = val2;
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            for(int k = 0; k < 3; k++) {
                if ((et.getNext() instanceof com.pullenti.ner.TextToken) && et.getNext().chars.isLetter()) {
                    tt = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class);
                    com.pullenti.ner.Token t0 = et;
                    long coef = (long)0;
                    if (k == 0) {
                        coef = (long)1000000000;
                        if (tt.isValue("МИЛЛИАРД", "МІЛЬЯРД") || tt.isValue("BILLION", null) || tt.isValue("BN", null)) {
                            et = tt;
                            val *= coef;
                        }
                        else if (tt.isValue("МЛРД", null)) {
                            et = tt;
                            val *= coef;
                            if ((et.getNext() instanceof com.pullenti.ner.TextToken) && et.getNext().isChar('.')) 
                                et = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class);
                        }
                        else 
                            continue;
                    }
                    else if (k == 1) {
                        coef = (long)1000000;
                        if (tt.isValue("МИЛЛИОН", "МІЛЬЙОН") || tt.isValue("MILLION", null)) {
                            et = tt;
                            val *= coef;
                        }
                        else if (tt.isValue("МЛН", null)) {
                            et = tt;
                            val *= coef;
                            if ((et.getNext() instanceof com.pullenti.ner.TextToken) && et.getNext().isChar('.')) 
                                et = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class);
                        }
                        else if ((tt instanceof com.pullenti.ner.TextToken) && com.pullenti.n2j.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term, "M")) {
                            if (NumberHelper.isMoneyChar(et.getPrevious()) != null) {
                                et = tt;
                                val *= coef;
                            }
                            else 
                                break;
                        }
                        else 
                            continue;
                    }
                    else {
                        coef = (long)1000;
                        if (tt.isValue("ТЫСЯЧА", "ТИСЯЧА") || tt.isValue("THOUSAND", null)) {
                            et = tt;
                            val *= coef;
                        }
                        else if (tt.isValue("ТЫС", null) || tt.isValue("ТИС", null)) {
                            et = tt;
                            val *= coef;
                            if ((et.getNext() instanceof com.pullenti.ner.TextToken) && et.getNext().isChar('.')) 
                                et = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class);
                        }
                        else 
                            break;
                    }
                    if (((t0 == token && t0.getLengthChar() <= 3 && t0.getPrevious() != null) && !t0.isWhitespaceBefore() && t0.getPrevious().isCharOf(",.")) && !t0.getPrevious().isWhitespaceBefore() && (((t0.getPrevious().getPrevious() instanceof com.pullenti.ner.NumberToken) || prevVal >= ((long)0)))) {
                        if (t0.getLengthChar() == 1) 
                            val /= ((long)10);
                        else if (t0.getLengthChar() == 2) 
                            val /= ((long)100);
                        else 
                            val /= ((long)1000);
                        if (t0.getPrevious().getPrevious() instanceof com.pullenti.ner.NumberToken) 
                            val += ((((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t0.getPrevious().getPrevious(), com.pullenti.ner.NumberToken.class))).value * coef);
                        else 
                            val += (prevVal * coef);
                        token = t0.getPrevious().getPrevious();
                    }
                    com.pullenti.ner.NumberToken next = _TryParse(et.getNext(), (long)-1);
                    if (next == null || next.value >= coef) 
                        break;
                    com.pullenti.ner.Token tt1 = next.getEndToken();
                    if (((tt1.getNext() instanceof com.pullenti.ner.TextToken) && !tt1.isWhitespaceAfter() && tt1.getNext().isCharOf(".,")) && !tt1.getNext().isWhitespaceAfter()) {
                        com.pullenti.ner.NumberToken re1 = _TryParse(tt1.getNext().getNext(), next.value);
                        if (re1 != null && re1.getBeginToken() == next.getBeginToken()) 
                            next = re1;
                    }
                    val += next.value;
                    et = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(next.getEndToken(), com.pullenti.ner.TextToken.class);
                    break;
                }
            }
            com.pullenti.ner.NumberToken res = com.pullenti.ner.NumberToken._new559(token, et, val, typ, mc);
            if (et.getNext() != null && (res.value < ((long)1000)) && ((et.getNext().isHiphen() || et.getNext().isValue("ДО", null)))) {
                for(com.pullenti.ner.Token tt1 = et.getNext().getNext(); tt1 != null; tt1 = tt1.getNext()) {
                    if (!((tt1 instanceof com.pullenti.ner.TextToken))) 
                        break;
                    if (Character.isDigit((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt1, com.pullenti.ner.TextToken.class))).term.charAt(0))) 
                        continue;
                    if (tt1.isCharOf(",.") || NumberHelper.isMoneyChar(tt1) != null) 
                        continue;
                    if (tt1.isValue("МИЛЛИОН", "МІЛЬЙОН") || tt1.isValue("МЛН", null) || tt1.isValue("MILLION", null)) 
                        res.value *= ((long)1000000);
                    else if ((tt1.isValue("МИЛЛИАРД", "МІЛЬЯРД") || tt1.isValue("МЛРД", null) || tt1.isValue("BILLION", null)) || tt1.isValue("BN", null)) 
                        res.value *= ((long)1000000000);
                    else if (tt1.isValue("ТЫСЯЧА", "ТИСЯЧА") || tt1.isValue("ТЫС", "ТИС") || tt1.isValue("THOUSAND", null)) 
                        res.value *= ((long)1000);
                    break;
                }
            }
            return res;
        }
        val = (long)0;
        et = null;
        long locValue = (long)0;
        boolean isAdj = false;
        int jPrev = -1;
        for(com.pullenti.ner.TextToken t = tt; t != null; t = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class)) {
            if (t != tt && t.getNewlinesBeforeCount() > 1) 
                break;
            term = t.term;
            if (!Character.isLetter(term.charAt(0))) 
                break;
            TerminToken num = m_Nums.tryParse(t, TerminParseAttr.FULLWORDSONLY);
            if (num == null) 
                break;
            j = (int)num.termin.tag;
            if (jPrev > 0 && (jPrev < 20) && (j < 20)) 
                break;
            isAdj = ((j & prilNumTagBit)) != 0;
            j &= (~prilNumTagBit);
            if (isAdj && t != tt) {
                if ((t.isValue("ДЕСЯТЫЙ", null) || t.isValue("СОТЫЙ", null) || t.isValue("ТЫСЯЧНЫЙ", null)) || t.isValue("ДЕСЯТИТЫСЯЧНЫЙ", null) || t.isValue("МИЛЛИОННЫЙ", null)) 
                    break;
            }
            if (j >= 1000) {
                if (locValue == ((long)0)) 
                    locValue = (long)1;
                val += (locValue * ((long)j));
                locValue = (long)0;
            }
            else {
                if (locValue > ((long)0) && locValue <= j) 
                    break;
                locValue += ((long)j);
            }
            et = t;
            if (j == 1000 || j == 1000000) {
                if ((et.getNext() instanceof com.pullenti.ner.TextToken) && et.getNext().isChar('.')) 
                    t = (et = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class));
            }
            jPrev = j;
        }
        if (locValue > ((long)0)) 
            val += locValue;
        if (val == ((long)0) || et == null) 
            return null;
        com.pullenti.ner.NumberToken nt = new com.pullenti.ner.NumberToken(tt, et, val, com.pullenti.ner.NumberSpellingType.WORDS, null);
        if (et.getMorph() != null) {
            nt.setMorph(new com.pullenti.ner.MorphCollection(et.getMorph()));
            for(com.pullenti.morph.MorphBaseInfo wff : et.getMorph().getItems()) {
                com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(wff, com.pullenti.morph.MorphWordForm.class);
                if (wf != null && wf.misc != null && wf.misc.getAttrs().contains("собир.")) {
                    nt.getMorph()._setClass(com.pullenti.morph.MorphClass.NOUN);
                    break;
                }
            }
            if (!isAdj) {
                nt.getMorph().removeItems(com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphClass.NOUN), false);
                if (nt.getMorph()._getClass().isUndefined()) 
                    nt.getMorph()._setClass(com.pullenti.morph.MorphClass.NOUN);
            }
            if (et.chars.isLatinLetter() && isAdj) 
                nt.getMorph()._setClass(com.pullenti.morph.MorphClass.ADJECTIVE);
        }
        return nt;
    }

    /**
     * Попробовать выделить римскую цифру
     * @param t 
     * @return 
     */
    public static com.pullenti.ner.NumberToken tryParseRoman(com.pullenti.ner.Token t) {
        if (t instanceof com.pullenti.ner.NumberToken) 
            return (com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class);
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null || !t.chars.isLetter()) 
            return null;
        String term = tt.term;
        if (!_isRomVal(term)) 
            return null;
        if (tt.getMorph()._getClass().isPreposition()) {
            if (tt.chars.isAllLower()) 
                return null;
        }
        com.pullenti.ner.NumberToken res = new com.pullenti.ner.NumberToken(t, t, (long)0, com.pullenti.ner.NumberSpellingType.ROMAN, null);
        java.util.ArrayList<Integer> nums = new java.util.ArrayList< >();
        for(; t != null; t = t.getNext()) {
            if (t != res.getBeginToken() && t.isWhitespaceBefore()) 
                break;
            if (!((t instanceof com.pullenti.ner.TextToken))) 
                break;
            term = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
            if (!_isRomVal(term)) 
                break;
            for(char s : term.toCharArray()) {
                int i = _romVal(s);
                if (i > 0) 
                    nums.add(i);
            }
            res.setEndToken(t);
        }
        if (nums.size() == 0) 
            return null;
        for(int i = 0; i < nums.size(); i++) {
            if ((i + 1) < nums.size()) {
                if (nums.get(i) == 1 && nums.get(i + 1) == 5) {
                    res.value += ((long)4);
                    i++;
                }
                else if (nums.get(i) == 1 && nums.get(i + 1) == 10) {
                    res.value += ((long)9);
                    i++;
                }
                else if (nums.get(i) == 10 && nums.get(i + 1) == 50) {
                    res.value += ((long)40);
                    i++;
                }
                else if (nums.get(i) == 10 && nums.get(i + 1) == 100) {
                    res.value += ((long)90);
                    i++;
                }
                else 
                    res.value += ((long)(int)nums.get(i));
            }
            else 
                res.value += ((long)(int)nums.get(i));
        }
        boolean hiph = false;
        com.pullenti.ner.Token et = res.getEndToken().getNext();
        if (et == null) 
            return res;
        if (et.getNext() != null && et.getNext().isHiphen()) {
            et = et.getNext();
            hiph = true;
        }
        if (hiph || !et.isWhitespaceAfter()) {
            com.pullenti.ner.MetaToken mc = analizeNumberTail((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class), res.value);
            if (mc != null) {
                res.setEndToken(mc.getEndToken());
                res.setMorph(mc.getMorph());
            }
        }
        if ((res.getBeginToken() == res.getEndToken() && res.value == ((long)1) && res.getBeginToken().chars.isAllLower()) && res.getBeginToken().getMorph().getLanguage().isUa()) 
            return null;
        return res;
    }

    private static int _romVal(char ch) {
        if (ch == 'Х' || ch == 'X') 
            return 10;
        if (ch == 'І' || ch == 'I') 
            return 1;
        if (ch == 'V') 
            return 5;
        if (ch == 'L') 
            return 50;
        if (ch == 'C' || ch == 'С') 
            return 100;
        return 0;
    }

    private static boolean _isRomVal(String str) {
        for(char ch : str.toCharArray()) {
            if (_romVal(ch) < 1) 
                return false;
        }
        return true;
    }

    /**
     * Выделить римскую цифру с token в обратном порядке
     * @param token 
     * @return 
     */
    public static com.pullenti.ner.NumberToken tryParseRomanBack(com.pullenti.ner.Token token) {
        com.pullenti.ner.Token t = token;
        if (t == null) 
            return null;
        if ((t.chars.isAllLower() && t.getPrevious() != null && t.getPrevious().isHiphen()) && t.getPrevious().getPrevious() != null) 
            t = token.getPrevious().getPrevious();
        com.pullenti.ner.NumberToken res = null;
        for(; t != null; t = t.getPrevious()) {
            com.pullenti.ner.NumberToken nt = tryParseRoman(t);
            if (nt != null) {
                if (nt.getEndToken() == token) 
                    res = nt;
                else 
                    break;
            }
            if (t.isWhitespaceAfter()) 
                break;
        }
        return res;
    }

    /**
     * Это выделение числительных типа 16-летие, 50-летний
     * @param t 
     * @return 
     */
    public static com.pullenti.ner.NumberToken tryParseAge(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class);
        com.pullenti.ner.Token ntNext = null;
        if (nt != null) 
            ntNext = nt.getNext();
        else {
            if (t.isValue("AGED", null) && (t.getNext() instanceof com.pullenti.ner.NumberToken)) 
                return new com.pullenti.ner.NumberToken(t, t.getNext(), (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class))).value, com.pullenti.ner.NumberSpellingType.AGE, null);
            if ((((nt = tryParseRoman(t)))) != null) 
                ntNext = nt.getEndToken().getNext();
        }
        if (nt != null) {
            if (ntNext != null) {
                com.pullenti.ner.Token t1 = ntNext;
                if (t1.isHiphen()) 
                    t1 = t1.getNext();
                if (t1 instanceof com.pullenti.ner.TextToken) {
                    String v = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t1, com.pullenti.ner.TextToken.class))).term;
                    if ((com.pullenti.n2j.Utils.stringsEq(v, "ЛЕТ") || com.pullenti.n2j.Utils.stringsEq(v, "ЛЕТИЯ") || com.pullenti.n2j.Utils.stringsEq(v, "ЛЕТИЕ")) || com.pullenti.n2j.Utils.stringsEq(v, "РІЧЧЯ")) 
                        return com.pullenti.ner.NumberToken._new559(t, t1, nt.value, com.pullenti.ner.NumberSpellingType.AGE, t1.getMorph());
                    if (t1.isValue("ЛЕТНИЙ", "РІЧНИЙ")) 
                        return com.pullenti.ner.NumberToken._new559(t, t1, nt.value, com.pullenti.ner.NumberSpellingType.AGE, t1.getMorph());
                    if (com.pullenti.n2j.Utils.stringsEq(v, "Л") || ((com.pullenti.n2j.Utils.stringsEq(v, "Р") && nt.getMorph().getLanguage().isUa()))) 
                        return new com.pullenti.ner.NumberToken(t, (t1.getNext() != null && t1.getNext().isChar('.') ? t1.getNext() : t1), nt.value, com.pullenti.ner.NumberSpellingType.AGE, null);
                }
            }
            return null;
        }
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return null;
        String s = tt.term;
        if (com.pullenti.morph.LanguageHelper.endsWithEx(s, "ЛЕТИЕ", "ЛЕТИЯ", "РІЧЧЯ", null)) {
            Termin term = m_Nums.find(s.substring(0, 0+(s.length() - 5)));
            if (term != null) 
                return com.pullenti.ner.NumberToken._new559(tt, tt, (long)((int)term.tag), com.pullenti.ner.NumberSpellingType.AGE, tt.getMorph());
        }
        s = tt.lemma;
        if (com.pullenti.morph.LanguageHelper.endsWithEx(s, "ЛЕТНИЙ", "РІЧНИЙ", null, null)) {
            Termin term = m_Nums.find(s.substring(0, 0+(s.length() - 6)));
            if (term != null) 
                return com.pullenti.ner.NumberToken._new559(tt, tt, (long)((int)term.tag), com.pullenti.ner.NumberSpellingType.AGE, tt.getMorph());
        }
        return null;
    }

    /**
     * Выделение годовщин и летий (XX-летие) ...
     */
    public static com.pullenti.ner.NumberToken tryParseAnniversary(com.pullenti.ner.Token t) {
        com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class);
        com.pullenti.ner.Token t1 = null;
        if (nt != null) 
            t1 = nt.getNext();
        else {
            if ((((nt = tryParseRoman(t)))) == null) {
                if (t instanceof com.pullenti.ner.TextToken) {
                    String v = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
                    int num = 0;
                    if (v.endsWith("ЛЕТИЯ") || v.endsWith("ЛЕТИЕ")) {
                        if (v.startsWith("ВОСЕМЬСОТ") || v.startsWith("ВОСЬМИСОТ")) 
                            num = 800;
                    }
                    if (num > 0) 
                        return new com.pullenti.ner.NumberToken(t, t, (long)num, com.pullenti.ner.NumberSpellingType.AGE, null);
                }
                return null;
            }
            t1 = nt.getEndToken().getNext();
        }
        if (t1 == null) 
            return null;
        if (t1.isHiphen()) 
            t1 = t1.getNext();
        if (t1 instanceof com.pullenti.ner.TextToken) {
            String v = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t1, com.pullenti.ner.TextToken.class))).term;
            if ((com.pullenti.n2j.Utils.stringsEq(v, "ЛЕТ") || com.pullenti.n2j.Utils.stringsEq(v, "ЛЕТИЯ") || com.pullenti.n2j.Utils.stringsEq(v, "ЛЕТИЕ")) || t1.isValue("ГОДОВЩИНА", null)) 
                return new com.pullenti.ner.NumberToken(t, t1, nt.value, com.pullenti.ner.NumberSpellingType.AGE, null);
            if (t1.getMorph().getLanguage().isUa()) {
                if (com.pullenti.n2j.Utils.stringsEq(v, "РОКІВ") || com.pullenti.n2j.Utils.stringsEq(v, "РІЧЧЯ") || t1.isValue("РІЧНИЦЯ", null)) 
                    return new com.pullenti.ner.NumberToken(t, t1, nt.value, com.pullenti.ner.NumberSpellingType.AGE, null);
            }
        }
        return null;
    }

    private static String[] m_Samples = new String[] {"ДЕСЯТЫЙ", "ПЕРВЫЙ", "ВТОРОЙ", "ТРЕТИЙ", "ЧЕТВЕРТЫЙ", "ПЯТЫЙ", "ШЕСТОЙ", "СЕДЬМОЙ", "ВОСЬМОЙ", "ДЕВЯТЫЙ"};

    private static com.pullenti.ner.MetaToken analizeNumberTail(com.pullenti.ner.TextToken tt, long val) {
        if (!((tt instanceof com.pullenti.ner.TextToken))) 
            return null;
        String s = tt.term;
        com.pullenti.ner.MorphCollection mc = null;
        if (!tt.chars.isLetter()) {
            if (((com.pullenti.n2j.Utils.stringsEq(s, "<") || com.pullenti.n2j.Utils.stringsEq(s, "("))) && (tt.getNext() instanceof com.pullenti.ner.TextToken)) {
                s = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt.getNext(), com.pullenti.ner.TextToken.class))).term;
                if ((com.pullenti.n2j.Utils.stringsEq(s, "TH") || com.pullenti.n2j.Utils.stringsEq(s, "ST") || com.pullenti.n2j.Utils.stringsEq(s, "RD")) || com.pullenti.n2j.Utils.stringsEq(s, "ND")) {
                    if (tt.getNext().getNext() != null && tt.getNext().getNext().isCharOf(">)")) {
                        mc = new com.pullenti.ner.MorphCollection(null);
                        mc._setClass(com.pullenti.morph.MorphClass.ADJECTIVE);
                        mc.setLanguage(com.pullenti.morph.MorphLang.EN);
                        return com.pullenti.ner.MetaToken._new564(tt, tt.getNext().getNext(), mc);
                    }
                }
            }
            return null;
        }
        if ((com.pullenti.n2j.Utils.stringsEq(s, "TH") || com.pullenti.n2j.Utils.stringsEq(s, "ST") || com.pullenti.n2j.Utils.stringsEq(s, "RD")) || com.pullenti.n2j.Utils.stringsEq(s, "ND")) {
            mc = new com.pullenti.ner.MorphCollection(null);
            mc._setClass(com.pullenti.morph.MorphClass.ADJECTIVE);
            mc.setLanguage(com.pullenti.morph.MorphLang.EN);
            return com.pullenti.ner.MetaToken._new564(tt, tt, mc);
        }
        if (!tt.chars.isCyrillicLetter()) 
            return null;
        if (!tt.isWhitespaceAfter()) {
            if (tt.getNext() != null && tt.getNext().chars.isLetter()) 
                return null;
            if (tt.getLengthChar() == 1 && ((tt.isValue("X", null) || tt.isValue("Х", null)))) 
                return null;
        }
        if (!tt.chars.isAllLower()) {
            String ss = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term;
            if (com.pullenti.n2j.Utils.stringsEq(ss, "Я") || com.pullenti.n2j.Utils.stringsEq(ss, "Й") || com.pullenti.n2j.Utils.stringsEq(ss, "Е")) {
            }
            else if (ss.length() == 2 && ((ss.charAt(1) == 'Я' || ss.charAt(1) == 'Й' || ss.charAt(1) == 'Е'))) {
            }
            else 
                return null;
        }
        if (com.pullenti.n2j.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term, "М")) {
            if (tt.getPrevious() == null || !tt.getPrevious().isHiphen()) 
                return null;
        }
        int dig = (int)((val % ((long)10)));
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> vars = com.pullenti.morph.Morphology.getAllWordforms(m_Samples[dig], new com.pullenti.morph.MorphLang(null));
        if (vars == null || vars.size() == 0) 
            return null;
        for(com.pullenti.morph.MorphWordForm v : vars) {
            if (v._getClass().isAdjective() && com.pullenti.morph.LanguageHelper.endsWith(v.normalCase, s) && v.getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED) {
                if (mc == null) 
                    mc = new com.pullenti.ner.MorphCollection(null);
                boolean ok = false;
                for(com.pullenti.morph.MorphBaseInfo it : mc.getItems()) {
                    if (com.pullenti.morph.MorphClass.ooEq(it._getClass(), v._getClass()) && it.getNumber() == v.getNumber() && ((it.getGender() == v.getGender() || v.getNumber() == com.pullenti.morph.MorphNumber.PLURAL))) {
                        it.setCase(com.pullenti.morph.MorphCase.ooBitor(it.getCase(), v.getCase()));
                        ok = true;
                        break;
                    }
                }
                if (!ok) 
                    mc.addItem(new com.pullenti.morph.MorphBaseInfo(v));
            }
        }
        if (tt.getMorph().getLanguage().isUa() && mc == null && com.pullenti.n2j.Utils.stringsEq(s, "Ї")) {
            mc = new com.pullenti.ner.MorphCollection(null);
            mc.addItem(com.pullenti.morph.MorphBaseInfo._new566(com.pullenti.morph.MorphClass.ADJECTIVE));
        }
        if (mc != null) 
            return com.pullenti.ner.MetaToken._new564(tt, tt, mc);
        if ((((s.length() < 3) && !tt.isWhitespaceBefore() && tt.getPrevious() != null) && tt.getPrevious().isHiphen() && !tt.getPrevious().isWhitespaceBefore()) && tt.getWhitespacesAfterCount() == 1 && com.pullenti.n2j.Utils.stringsNe(s, "А")) 
            return com.pullenti.ner.MetaToken._new564(tt, tt, com.pullenti.ner.MorphCollection._new568(com.pullenti.morph.MorphClass.ADJECTIVE));
        return null;
    }

    /**
     * Преобразовать число в числительное, записанное буквами, в соотв. роде и числе. 
     *  Например, 5 жен.ед. - ПЯТАЯ,  26 мн. - ДВАДЦАТЬ ШЕСТЫЕ
     * @param value значение
     * @param gender род
     * @param num число
     * @return 
     */
    public static String getNumberAdjective(int value, com.pullenti.morph.MorphGender gender, com.pullenti.morph.MorphNumber num) {
        if ((value < 1) || value >= 100) 
            return null;
        String[] words = null;
        if (num == com.pullenti.morph.MorphNumber.PLURAL) 
            words = m_PluralNumberWords;
        else if (gender == com.pullenti.morph.MorphGender.FEMINIE) 
            words = m_WomanNumberWords;
        else if (gender == com.pullenti.morph.MorphGender.NEUTER) 
            words = m_NeutralNumberWords;
        else 
            words = m_ManNumberWords;
        if (value < 20) 
            return words[value - 1];
        int i = value / 10;
        int j = value % 10;
        i -= 2;
        if (i >= m_DecDumberWords.length) 
            return null;
        if (j > 0) 
            return m_DecDumberWords[i] + " " + words[j - 1];
        String[] decs = null;
        if (num == com.pullenti.morph.MorphNumber.PLURAL) 
            decs = m_PluralDecDumberWords;
        else if (gender == com.pullenti.morph.MorphGender.FEMINIE) 
            decs = m_WomanDecDumberWords;
        else if (gender == com.pullenti.morph.MorphGender.NEUTER) 
            decs = m_NeutralDecDumberWords;
        else 
            decs = m_ManDecDumberWords;
        return decs[i];
    }

    private static String[] m_ManNumberWords = new String[] {"ПЕРВЫЙ", "ВТОРОЙ", "ТРЕТИЙ", "ЧЕТВЕРТЫЙ", "ПЯТЫЙ", "ШЕСТОЙ", "СЕДЬМОЙ", "ВОСЬМОЙ", "ДЕВЯТЫЙ", "ДЕСЯТЫЙ", "ОДИННАДЦАТЫЙ", "ДВЕНАДЦАТЫЙ", "ТРИНАДЦАТЫЙ", "ЧЕТЫРНАДЦАТЫЙ", "ПЯТНАДЦАТЫЙ", "ШЕСТНАДЦАТЫЙ", "СЕМНАДЦАТЫЙ", "ВОСЕМНАДЦАТЫЙ", "ДЕВЯТНАДЦАТЫЙ"};

    private static String[] m_NeutralNumberWords = new String[] {"ПЕРВОЕ", "ВТОРОЕ", "ТРЕТЬЕ", "ЧЕТВЕРТОЕ", "ПЯТОЕ", "ШЕСТОЕ", "СЕДЬМОЕ", "ВОСЬМОЕ", "ДЕВЯТОЕ", "ДЕСЯТОЕ", "ОДИННАДЦАТОЕ", "ДВЕНАДЦАТОЕ", "ТРИНАДЦАТОЕ", "ЧЕТЫРНАДЦАТОЕ", "ПЯТНАДЦАТОЕ", "ШЕСТНАДЦАТОЕ", "СЕМНАДЦАТОЕ", "ВОСЕМНАДЦАТОЕ", "ДЕВЯТНАДЦАТОЕ"};

    private static String[] m_WomanNumberWords = new String[] {"ПЕРВАЯ", "ВТОРАЯ", "ТРЕТЬЯ", "ЧЕТВЕРТАЯ", "ПЯТАЯ", "ШЕСТАЯ", "СЕДЬМАЯ", "ВОСЬМАЯ", "ДЕВЯТАЯ", "ДЕСЯТАЯ", "ОДИННАДЦАТАЯ", "ДВЕНАДЦАТАЯ", "ТРИНАДЦАТАЯ", "ЧЕТЫРНАДЦАТАЯ", "ПЯТНАДЦАТАЯ", "ШЕСТНАДЦАТАЯ", "СЕМНАДЦАТАЯ", "ВОСЕМНАДЦАТАЯ", "ДЕВЯТНАДЦАТАЯ"};

    private static String[] m_PluralNumberWords = new String[] {"ПЕРВЫЕ", "ВТОРЫЕ", "ТРЕТЬИ", "ЧЕТВЕРТЫЕ", "ПЯТЫЕ", "ШЕСТЫЕ", "СЕДЬМЫЕ", "ВОСЬМЫЕ", "ДЕВЯТЫЕ", "ДЕСЯТЫЕ", "ОДИННАДЦАТЫЕ", "ДВЕНАДЦАТЫЕ", "ТРИНАДЦАТЫЕ", "ЧЕТЫРНАДЦАТЫЕ", "ПЯТНАДЦАТЫЕ", "ШЕСТНАДЦАТЫЕ", "СЕМНАДЦАТЫЕ", "ВОСЕМНАДЦАТЫЕ", "ДЕВЯТНАДЦАТЫЕ"};

    private static String[] m_DecDumberWords = new String[] {"ДВАДЦАТЬ", "ТРИДЦАТЬ", "СОРОК", "ПЯТЬДЕСЯТ", "ШЕСТЬДЕСЯТ", "СЕМЬДЕСЯТ", "ВОСЕМЬДЕСЯТ", "ДЕВЯНОСТО"};

    private static String[] m_ManDecDumberWords = new String[] {"ДВАДЦАТЫЙ", "ТРИДЦАТЫЙ", "СОРОКОВОЙ", "ПЯТЬДЕСЯТЫЙ", "ШЕСТЬДЕСЯТЫЙ", "СЕМЬДЕСЯТЫЙ", "ВОСЕМЬДЕСЯТЫЙ", "ДЕВЯНОСТЫЙ"};

    private static String[] m_WomanDecDumberWords = new String[] {"ДВАДЦАТАЯ", "ТРИДЦАТАЯ", "СОРОКОВАЯ", "ПЯТЬДЕСЯТАЯ", "ШЕСТЬДЕСЯТАЯ", "СЕМЬДЕСЯТАЯ", "ВОСЕМЬДЕСЯТАЯ", "ДЕВЯНОСТАЯ"};

    private static String[] m_NeutralDecDumberWords = new String[] {"ДВАДЦАТОЕ", "ТРИДЦАТОЕ", "СОРОКОВОЕ", "ПЯТЬДЕСЯТОЕ", "ШЕСТЬДЕСЯТОЕ", "СЕМЬДЕСЯТОЕ", "ВОСЕМЬДЕСЯТОЕ", "ДЕВЯНОСТОЕ"};

    private static String[] m_PluralDecDumberWords = new String[] {"ДВАДЦАТЫЕ", "ТРИДЦАТЫЕ", "СОРОКОВЫЕ", "ПЯТЬДЕСЯТЫЕ", "ШЕСТЬДЕСЯТЫЕ", "СЕМЬДЕСЯТЫЕ", "ВОСЕМЬДЕСЯТЫЕ", "ДЕВЯНОСТЫЕ"};

    public static String[] m_Romans = new String[] {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX", "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX"};

    /**
     * Получить для числа римскую запись
     * @param val 
     * @return 
     */
    public static String getNumberRoman(int val) {
        if (val > 0 && val <= m_Romans.length) 
            return m_Romans[val - 1];
        return ((Integer)val).toString();
    }

    /**
     * Выделить дробное число
     * @param t начальный токен
     * @return 
     */
    public static NumberExToken tryParseFloatNumber(com.pullenti.ner.Token t) {
        return NumberExToken.tryParseFloatNumber(t);
    }

    /**
     * Выделение стандартных мер, типа: 10 кв.м.
     * @param t начальный токен
     * @return 
     */
    public static NumberExToken tryParseNumberWithPostfix(com.pullenti.ner.Token t) {
        return NumberExToken.tryParseNumberWithPostfix(t);
    }

    /**
     * Это попробовать только тип (постфикс) без самого числа. 
     *  Например, куб.м.
     * @param t 
     * @return 
     */
    public static NumberExToken tryAttachPostfixOnly(com.pullenti.ner.Token t) {
        return NumberExToken.tryAttachPostfixOnly(t);
    }

    /**
     * Если этообозначение денежной единицы (н-р, $), то возвращает код валюты
     * @param t 
     * @return 
     */
    public static String isMoneyChar(com.pullenti.ner.Token t) {
        if (!((t instanceof com.pullenti.ner.TextToken)) || t.getLengthChar() != 1) 
            return null;
        char ch = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term.charAt(0);
        if (ch == '$') 
            return "USD";
        if (ch == '£' || ch == ((char)0xA3) || ch == ((char)0x20A4)) 
            return "GBP";
        if (ch == '€') 
            return "EUR";
        if (ch == '¥' || ch == ((char)0xA5)) 
            return "JPY";
        if (ch == ((char)0x20A9)) 
            return "KRW";
        if (ch == ((char)0xFFE5) || ch == 'Ұ' || ch == 'Ұ') 
            return "CNY";
        if (ch == ((char)0x20BD)) 
            return "RUB";
        if (ch == ((char)0x20B4)) 
            return "UAH";
        if (ch == ((char)0x20AB)) 
            return "VND";
        if (ch == ((char)0x20AD)) 
            return "LAK";
        if (ch == ((char)0x20BA)) 
            return "TRY";
        if (ch == ((char)0x20B1)) 
            return "PHP";
        if (ch == ((char)0x17DB)) 
            return "KHR";
        if (ch == ((char)0x20B9)) 
            return "INR";
        if (ch == ((char)0x20A8)) 
            return "IDR";
        if (ch == ((char)0x20B5)) 
            return "GHS";
        if (ch == ((char)0x09F3)) 
            return "BDT";
        if (ch == ((char)0x20B8)) 
            return "KZT";
        if (ch == ((char)0x20AE)) 
            return "MNT";
        if (ch == ((char)0x0192)) 
            return "HUF";
        if (ch == ((char)0x20AA)) 
            return "ILS";
        return null;
    }

    private static final int prilNumTagBit = 0x40000000;

    public static void initialize() {
        if (m_Nums != null) 
            return;
        m_Nums = new TerminCollection();
        m_Nums.allAddStrsNormalized = true;
        m_Nums.addStr("ОДИН", 1, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ПЕРВЫЙ", 1 | prilNumTagBit, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ОДИН", 1, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("ПЕРШИЙ", 1 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("ОДНА", 1, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ОДНО", 1, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("FIRST", 1 | prilNumTagBit, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addStr("SEMEL", 1, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addStr("ДВА", 2, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ВТОРОЙ", 2 | prilNumTagBit, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ДВОЕ", 2, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ДВЕ", 2, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ДВУХ", 2, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ОБА", 2, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ОБЕ", 2, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ДВА", 2, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("ДРУГИЙ", 2 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("ДВОЄ", 2, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("ДВІ", 2, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("ДВОХ", 2, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("ОБОЄ", 2, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("ОБИДВА", 2, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("SECOND", 2 | prilNumTagBit, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addStr("BIS", 2, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addStr("ТРИ", 3, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ТРЕТИЙ", 3 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ТРЕХ", 3, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ТРОЕ", 3, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ТРИ", 3, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("ТРЕТІЙ", 3 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("ТРЬОХ", 3, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("ТРОЄ", 3, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("THIRD", 3 | prilNumTagBit, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addStr("TER", 3, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addStr("ЧЕТЫРЕ", 4, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ЧЕТВЕРТЫЙ", 4 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ЧЕТЫРЕХ", 4, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ЧЕТВЕРО", 4, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ЧОТИРИ", 4, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ЧЕТВЕРТИЙ", 4 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ЧОТИРЬОХ", 4, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("FORTH", 4 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("QUATER", 4, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ПЯТЬ", 5, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ПЯТЫЙ", 5 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ПЯТИ", 5, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ПЯТЕРО", 5, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ПЯТЬ", 5, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ПЯТИЙ", 5 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("FIFTH", 5 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("QUINQUIES", 5, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ШЕСТЬ", 6, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ШЕСТОЙ", 6 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ШЕСТИ", 6, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ШЕСТЕРО", 6, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ШІСТЬ", 6, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ШОСТИЙ", 6 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("SIX", 6, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("SIXTH", 6 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("SEXIES ", 6, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СЕМЬ", 7, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СЕДЬМОЙ", 7 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СЕМИ", 7, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СЕМЕРО", 7, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СІМ", 7, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("СЬОМИЙ", 7 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("SEVEN", 7, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("SEVENTH", 7 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("SEPTIES", 7, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ВОСЕМЬ", 8, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ВОСЬМОЙ", 8 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ВОСЬМИ", 8, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ВОСЬМЕРО", 8, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ВІСІМ", 8, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ВОСЬМИЙ", 8 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("EIGHT", 8, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("EIGHTH", 8 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("OCTIES", 8, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДЕВЯТЬ", 9, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДЕВЯТЫЙ", 9 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДЕВЯТИ", 9, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДЕВЯТЕРО", 9, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДЕВЯТЬ", 9, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДЕВЯТИЙ", 9 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("NINE", 9, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("NINTH", 9 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("NOVIES", 9, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДЕСЯТЬ", 10, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДЕСЯТЫЙ", 10 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДЕСЯТИ", 10, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДЕСЯТИРО", 10, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДЕСЯТЬ", 10, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДЕСЯТИЙ", 10 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("TEN", 10, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("TENTH", 10 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("DECIES", 10, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ОДИННАДЦАТЬ", 11, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ОДИННАДЦАТЫЙ", 11 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ОДИННАДЦАТИ", 11, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ОДИННАДЦАТИРО", 11, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ОДИНАДЦЯТЬ", 11, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ОДИНАДЦЯТИЙ", 11 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ОДИНАДЦЯТИ", 11, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ELEVEN", 11, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ELEVENTH", 11 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДВЕНАДЦАТЬ", 12, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДВЕНАДЦАТЫЙ", 12 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДВЕНАДЦАТИ", 12, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДВАНАДЦЯТЬ", 12, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДВАНАДЦЯТИЙ", 12 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДВАНАДЦЯТИ", 12, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("TWELVE", 12, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("TWELFTH", 12 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ТРИНАДЦАТЬ", 13, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ТРИНАДЦАТЫЙ", 13 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ТРИНАДЦАТИ", 13, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ТРИНАДЦЯТЬ", 13, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ТРИНАДЦЯТИЙ", 13 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ТРИНАДЦЯТИ", 13, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("THIRTEEN", 13, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("THIRTEENTH", 13 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ЧЕТЫРНАДЦАТЬ", 14, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ЧЕТЫРНАДЦАТЫЙ", 14 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ЧЕТЫРНАДЦАТИ", 14, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ЧОТИРНАДЦЯТЬ", 14, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ЧОТИРНАДЦЯТИЙ", 14 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ЧОТИРНАДЦЯТИ", 14, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("FOURTEEN", 14, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("FOURTEENTH", 14 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ПЯТНАДЦАТЬ", 15, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ПЯТНАДЦАТЫЙ", 15 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ПЯТНАДЦАТИ", 15, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ПЯТНАДЦЯТЬ", 15, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ПЯТНАДЦЯТИЙ", 15 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ПЯТНАДЦЯТИ", 15, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("FIFTEEN", 15, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("FIFTEENTH", 15 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ШЕСТНАДЦАТЬ", 16, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ШЕСТНАДЦАТЫЙ", 16 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ШЕСТНАДЦАТИ", 16, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ШІСТНАДЦЯТЬ", 16, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ШІСТНАДЦЯТИЙ", 16 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ШІСТНАДЦЯТИ", 16, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("SIXTEEN", 16, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("SIXTEENTH", 16 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СЕМНАДЦАТЬ", 17, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СЕМНАДЦАТЫЙ", 17 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СЕМНАДЦАТИ", 17, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СІМНАДЦЯТЬ", 17, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("СІМНАДЦЯТИЙ", 17 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("СІМНАДЦЯТИ", 17, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("SEVENTEEN", 17, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("SEVENTEENTH", 17 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ВОСЕМНАДЦАТЬ", 18, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ВОСЕМНАДЦАТЫЙ", 18 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ВОСЕМНАДЦАТИ", 18, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ВІСІМНАДЦЯТЬ", 18, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ВІСІМНАДЦЯТИЙ", 18 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ВІСІМНАДЦЯТИ", 18, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("EIGHTEEN", 18, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("EIGHTEENTH", 18 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДЕВЯТНАДЦАТЬ", 19, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДЕВЯТНАДЦАТЫЙ", 19 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДЕВЯТНАДЦАТИ", 19, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДЕВЯТНАДЦЯТЬ", 19, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДЕВЯТНАДЦЯТИЙ", 19 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДЕВЯТНАДЦЯТИ", 19, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("NINETEEN", 19, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("NINETEENTH", 19 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДВАДЦАТЬ", 20, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДВАДЦАТЫЙ", 20 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДВАДЦАТИ", 20, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДВАДЦЯТЬ", 20, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДВАДЦЯТИЙ", 20 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДВАДЦЯТИ", 20, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("TWENTY", 20, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("TWENTIETH", 20 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ТРИДЦАТЬ", 30, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ТРИДЦАТЫЙ", 30 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ТРИДЦАТИ", 30, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ТРИДЦЯТЬ", 30, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ТРИДЦЯТИЙ", 30 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ТРИДЦЯТИ", 30, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("THIRTY", 30, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("THIRTIETH", 30 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СОРОК", 40, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СОРОКОВОЙ", 40 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СОРОКА", 40, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СОРОК", 40, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("СОРОКОВИЙ", 40 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("FORTY", 40, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("FORTIETH", 40 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ПЯТЬДЕСЯТ", 50, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ПЯТИДЕСЯТЫЙ", 50 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ПЯТИДЕСЯТИ", 50, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ПЯТДЕСЯТ", 50, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ПЯТДЕСЯТИЙ", 50 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ПЯТДЕСЯТИ", 50, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("FIFTY", 50, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("FIFTIETH", 50 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ШЕСТЬДЕСЯТ", 60, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ШЕСТИДЕСЯТЫЙ", 60 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ШЕСТИДЕСЯТИ", 60, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ШІСТДЕСЯТ", 60, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ШЕСИДЕСЯТЫЙ", 60 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ШІСТДЕСЯТИ", 60, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("SIXTY", 60, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("SIXTIETH", 60 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СЕМЬДЕСЯТ", 70, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СЕМИДЕСЯТЫЙ", 70 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СЕМИДЕСЯТИ", 70, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СІМДЕСЯТ", 70, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("СІМДЕСЯТИЙ", 70 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("СІМДЕСЯТИ", 70, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("SEVENTY", 70, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("SEVENTIETH", 70 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("SEVENTIES", 70 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ВОСЕМЬДЕСЯТ", 80, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ВОСЬМИДЕСЯТЫЙ", 80 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ВОСЬМИДЕСЯТИ", 80, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ВІСІМДЕСЯТ", 80, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ВОСЬМИДЕСЯТИЙ", 80 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ВІСІМДЕСЯТИ", 80, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("EIGHTY", 80, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("EIGHTIETH", 80 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("EIGHTIES", 80 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДЕВЯНОСТО", 90, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДЕВЯНОСТЫЙ", 90 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДЕВЯНОСТО", 90, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДЕВЯНОСТИЙ", 90 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("NINETY", 90, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("NINETIETH", 90 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("NINETIES", 90 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СТО", 100, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СОТЫЙ", 100 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СТА", 100, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СТО", 100, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("СОТИЙ", 100 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("HUNDRED", 100, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("HUNDREDTH", 100 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДВЕСТИ", 200, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДВУХСОТЫЙ", 200 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДВУХСОТ", 200, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДВІСТІ", 200, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДВОХСОТИЙ", 200 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДВОХСОТ", 200, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ТРИСТА", 300, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ТРЕХСОТЫЙ", 300 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ТРЕХСОТ", 300, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ТРИСТА", 300, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ТРЬОХСОТИЙ", 300 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ТРЬОХСОТ", 300, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ЧЕТЫРЕСТА", 400, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ЧЕТЫРЕХСОТЫЙ", 400 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ЧОТИРИСТА", 400, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ЧОТИРЬОХСОТИЙ", 400 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ПЯТЬСОТ", 500, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ПЯТИСОТЫЙ", 500 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ПЯТСОТ", 500, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ПЯТИСОТИЙ", 500 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ШЕСТЬСОТ", 600, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ШЕСТИСОТЫЙ", 600 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ШІСТСОТ", 600, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ШЕСТИСОТИЙ", 600 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("СЕМЬСОТ", 700, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СЕМИСОТЫЙ", 700 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("СІМСОТ", 700, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("СЕМИСОТИЙ", 700 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ВОСЕМЬСОТ", 800, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ВОСЕМЬСОТЫЙ", 800 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ВОСЬМИСОТЫЙ", 800 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ВІСІМСОТ", 800, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ВОСЬМИСОТЫЙ", 800 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДЕВЯТЬСОТ", 900, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДЕВЯТЬСОТЫЙ", 900 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДЕВЯТИСОТЫЙ", 900 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДЕВЯТСОТ", 900, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДЕВЯТЬСОТЫЙ", 900 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДЕВЯТИСОТИЙ", 900 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ТЫС", 1000, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ТЫСЯЧА", 1000, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ТЫСЯЧНЫЙ", 1000 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ТИС", 1000, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ТИСЯЧА", 1000, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ТИСЯЧНИЙ", 1000 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДВУХТЫСЯЧНЫЙ", 2000 | prilNumTagBit, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("ДВОХТИСЯЧНИЙ", 2000 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("МИЛЛИОН", 1000000, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("МЛН", 1000000, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("МІЛЬЙОН", 1000000, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("МИЛЛИАРД", 1000000000, new com.pullenti.morph.MorphLang(null), false);
        m_Nums.addStr("МІЛЬЯРД", 1000000000, com.pullenti.morph.MorphLang.UA, false);
    }

    public static TerminCollection m_Nums;
    public NumberHelper() {
    }
}
