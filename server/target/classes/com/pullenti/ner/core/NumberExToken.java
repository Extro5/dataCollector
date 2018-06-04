/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.core;

/**
 * Число с стандартный постфиксом (мерой длины, вес, деньги и т.п.)
 */
public class NumberExToken extends com.pullenti.ner.NumberToken {

    /**
     * Это если заданы дроби ...
     */
    public double realValue;

    /**
     * Это возможно в скобках другое написание
     */
    public double altRealValue;

    public int altRestMoney = 0;

    /**
     * Тип постфикса
     */
    public NumberExType exTyp = NumberExType.UNDEFINED;

    /**
     * Это постфикс после деления, например гр./м3
     */
    public NumberExType exTyp2 = NumberExType.UNDEFINED;

    /**
     * Дополнительный параметр постфикса (для денег - 3-х значный код валюты)
     */
    public String exTypParam;

    /**
     * Это признак того, что "множитель" слипся с единицей измерения
     */
    public boolean multAfter;

    public NumberExToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end, long val, com.pullenti.ner.NumberSpellingType _typ, NumberExType _exTyp) {
        super(begin, end, val, _typ, null);
        value = val;
        typ = _typ;
        exTyp = _exTyp;
    }

    private static com.pullenti.ner.Token _tryParseFloat(com.pullenti.ner.NumberToken t, com.pullenti.n2j.Outargwrapper<Double> d) {
        d.value = (double)0;
        if (t == null || t.getNext() == null || t.typ != com.pullenti.ner.NumberSpellingType.DIGIT) 
            return null;
        java.util.ArrayList<com.pullenti.ner.NumberToken> ns = null;
        java.util.ArrayList<Character> sps = null;
        for(com.pullenti.ner.Token t1 = t; t1 != null; t1 = t1.getNext()) {
            if (t1.getNext() == null) 
                break;
            if (((t1.getNext() instanceof com.pullenti.ner.NumberToken) && (t1.getWhitespacesAfterCount() < 3) && (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t1.getNext(), com.pullenti.ner.NumberToken.class))).typ == com.pullenti.ner.NumberSpellingType.DIGIT) && t1.getNext().getLengthChar() == 3) {
                if (ns == null) {
                    ns = new java.util.ArrayList< >();
                    ns.add(t);
                    sps = new java.util.ArrayList< >();
                }
                else if (sps.get(0) != ' ') 
                    return null;
                ns.add((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t1.getNext(), com.pullenti.ner.NumberToken.class));
                sps.add(' ');
                continue;
            }
            if ((t1.getNext().isCharOf(",.") && (t1.getNext().getNext() instanceof com.pullenti.ner.NumberToken) && (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t1.getNext().getNext(), com.pullenti.ner.NumberToken.class))).typ == com.pullenti.ner.NumberSpellingType.DIGIT) && (t1.getWhitespacesAfterCount() < 2) && (t1.getNext().getWhitespacesAfterCount() < 2)) {
                if (ns == null) {
                    ns = new java.util.ArrayList< >();
                    ns.add(t);
                    sps = new java.util.ArrayList< >();
                }
                else if (t1.getNext().isWhitespaceAfter() && t1.getNext().getNext().getLengthChar() != 3 && (((t1.getNext().isChar('.') ? '.' : ','))) == sps.get(sps.size() - 1)) 
                    break;
                ns.add((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t1.getNext().getNext(), com.pullenti.ner.NumberToken.class));
                sps.add((t1.getNext().isChar('.') ? '.' : ','));
                t1 = t1.getNext();
                continue;
            }
            break;
        }
        if (sps == null) 
            return null;
        boolean isLastDrob = false;
        boolean notSetDrob = false;
        boolean merge = false;
        char m_PrevPointChar = '.';
        if (sps.size() == 1) {
            if (sps.get(0) == ' ') 
                isLastDrob = false;
            else if (ns.get(1).getLengthChar() != 3) {
                isLastDrob = true;
                if (ns.size() == 2) {
                    if (ns.get(1).getEndToken().chars.isLetter()) 
                        merge = true;
                    else if (ns.get(1).getEndToken().isChar('.') && ns.get(1).getEndToken().getPrevious() != null && ns.get(1).getEndToken().getPrevious().chars.isLetter()) 
                        merge = true;
                    if (ns.get(1).isWhitespaceBefore()) {
                        if ((ns.get(1).getEndToken() instanceof com.pullenti.ner.TextToken) && (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(ns.get(1).getEndToken(), com.pullenti.ner.TextToken.class))).term.endsWith("000")) 
                            return null;
                    }
                }
            }
            else if (ns.get(0).getLengthChar() > 3 || ns.get(0).value == ((long)0)) 
                isLastDrob = true;
            else {
                boolean ok = true;
                if (ns.size() == 2 && ns.get(1).getLengthChar() == 3) {
                    TerminToken ttt = m_Postfixes.tryParse(ns.get(1).getEndToken().getNext(), TerminParseAttr.NO);
                    if (ttt != null && ((NumberExType)ttt.termin.tag) == NumberExType.MONEY) {
                        isLastDrob = false;
                        ok = false;
                        notSetDrob = false;
                    }
                    else if (ns.get(1).getEndToken().getNext() != null && ns.get(1).getEndToken().getNext().isChar('(') && (ns.get(1).getEndToken().getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                        com.pullenti.ner.NumberToken nt1 = ((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(ns.get(1).getEndToken().getNext().getNext(), com.pullenti.ner.NumberToken.class));
                        if (nt1.value == ((ns.get(0).value * ((long)1000)) + ns.get(1).value)) {
                            isLastDrob = false;
                            ok = false;
                            notSetDrob = false;
                        }
                    }
                }
                if (ok) {
                    if (t.kit.processor.miscData.containsKey("pt")) 
                        m_PrevPointChar = (char)t.kit.processor.miscData.get("pt");
                    if (m_PrevPointChar == sps.get(0)) {
                        isLastDrob = true;
                        notSetDrob = true;
                    }
                    else {
                        isLastDrob = false;
                        notSetDrob = true;
                    }
                }
            }
        }
        else {
            char last = sps.get(sps.size() - 1);
            if (last == ' ' && sps.get(0) != last) 
                return null;
            for(int i = 0; i < (sps.size() - 1); i++) {
                if (sps.get(i) != sps.get(0)) 
                    return null;
                else if (ns.get(i + 1).getLengthChar() != 3) 
                    return null;
            }
            if (sps.get(0) != last) 
                isLastDrob = true;
            else if (ns.get(ns.size() - 1).getLengthChar() != 3) 
                return null;
        }
        for(int i = 0; i < ns.size(); i++) {
            if ((i < (ns.size() - 1)) || !isLastDrob) {
                if (i == 0) 
                    d.value = (double)ns.get(i).value;
                else 
                    d.value = (d.value * ((double)1000)) + ((double)ns.get(i).value);
                if (i == (ns.size() - 1) && !notSetDrob) {
                    if (sps.get(sps.size() - 1) == ',') 
                        m_PrevPointChar = '.';
                    else if (sps.get(sps.size() - 1) == '.') 
                        m_PrevPointChar = ',';
                }
            }
            else {
                if (!notSetDrob) {
                    m_PrevPointChar = sps.get(sps.size() - 1);
                    if (m_PrevPointChar == ',') {
                    }
                }
                double f2;
                if (merge) {
                    String sss = ((Long)ns.get(i).value).toString();
                    int kkk;
                    for(kkk = 0; kkk < (sss.length() - ns.get(i).getBeginToken().getLengthChar()); kkk++) {
                        d.value *= ((double)10);
                    }
                    f2 = (double)ns.get(i).value;
                    for(kkk = 0; kkk < ns.get(i).getBeginToken().getLengthChar(); kkk++) {
                        f2 /= ((double)10);
                    }
                    d.value += f2;
                }
                else {
                    f2 = (double)ns.get(i).value;
                    for(int kkk = 0; kkk < ns.get(i).getLengthChar(); kkk++) {
                        f2 /= ((double)10);
                    }
                    d.value += f2;
                }
            }
        }
        if (t.kit.processor.miscData.containsKey("pt")) 
            t.kit.processor.miscData.put("pt", m_PrevPointChar);
        else 
            t.kit.processor.miscData.put("pt", m_PrevPointChar);
        return ns.get(ns.size() - 1);
    }

    /**
     * Это разделитель дроби по-умолчанию, используется для случаев, когда невозможно принять однозначного решения. 
     *  Устанавливается на основе последнего успешного анализа.
     */
    public static NumberExToken tryParseFloatNumber(com.pullenti.ner.Token t) {
        boolean isNot = false;
        com.pullenti.ner.Token t0 = t;
        if (t != null && t.isHiphen()) {
            t = t.getNext();
            isNot = true;
        }
        if (!((t instanceof com.pullenti.ner.NumberToken))) 
            return null;
        double d;
        com.pullenti.n2j.Outargwrapper<Double> inoutarg490 = new com.pullenti.n2j.Outargwrapper< >();
        com.pullenti.ner.Token tt = _tryParseFloat((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class), inoutarg490);
        d = inoutarg490.value;
        if (tt == null) {
            if (t.getNext() == null || t.isWhitespaceAfter() || t.getNext().chars.isLetter()) {
                tt = t;
                d = (double)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value;
            }
            else 
                return null;
        }
        if (isNot) 
            d = -d;
        return _new489(t0, tt, (long)0, com.pullenti.ner.NumberSpellingType.DIGIT, NumberExType.UNDEFINED, d);
    }

    /**
     * Выделение стандартных мер, типа: 10 кв.м.
     */
    public static NumberExToken tryParseNumberWithPostfix(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        com.pullenti.ner.Token t0 = t;
        String isDollar = null;
        if (t.getLengthChar() == 1 && t.getNext() != null) {
            if ((((isDollar = NumberHelper.isMoneyChar(t)))) != null) 
                t = t.getNext();
        }
        com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class);
        if (nt == null) {
            if ((!((t.getPrevious() instanceof com.pullenti.ner.NumberToken)) && t.isChar('(') && (t.getNext() instanceof com.pullenti.ner.NumberToken)) && t.getNext().getNext() != null && t.getNext().getNext().isChar(')')) {
                TerminToken toks1 = m_Postfixes.tryParse(t.getNext().getNext().getNext(), TerminParseAttr.NO);
                if (toks1 != null && ((NumberExType)toks1.termin.tag) == NumberExType.MONEY) {
                    com.pullenti.ner.NumberToken nt0 = (com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class);
                    NumberExToken res = _new491(t, toks1.getEndToken(), nt0.value, nt0.typ, NumberExType.MONEY, (double)nt0.value, (double)nt0.value, toks1.getBeginToken().getMorph());
                    return _correctMoney(res, toks1.getBeginToken());
                }
            }
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class);
            if (tt == null || !tt.getMorph()._getClass().isAdjective()) 
                return null;
            String val = tt.term;
            for(int i = 4; i < (val.length() - 5); i++) {
                String v = val.substring(0, 0+(i));
                java.util.ArrayList<Termin> li = NumberHelper.m_Nums.tryAttachStr(v, tt.getMorph().getLanguage());
                if (li == null) 
                    continue;
                String vv = val.substring(i);
                java.util.ArrayList<Termin> lii = m_Postfixes.tryAttachStr(vv, tt.getMorph().getLanguage());
                if (lii != null && lii.size() > 0) {
                    NumberExToken re = _new492(t, t, (long)((int)li.get(0).tag), com.pullenti.ner.NumberSpellingType.WORDS, (NumberExType)lii.get(0).tag, t.getMorph());
                    re.realValue = (double)re.value;
                    _correctExtTypes(re);
                    return re;
                }
                break;
            }
            return null;
        }
        if (t.getNext() == null && isDollar == null) 
            return null;
        double f = (double)nt.value;
        long cel = nt.value;
        com.pullenti.ner.Token t1 = nt.getNext();
        if (((t1 != null && t1.isCharOf(",."))) || (((t1 instanceof com.pullenti.ner.NumberToken) && (t1.getWhitespacesBeforeCount() < 3)))) {
            double d;
            com.pullenti.n2j.Outargwrapper<Double> inoutarg493 = new com.pullenti.n2j.Outargwrapper< >();
            com.pullenti.ner.Token tt11 = _tryParseFloat(nt, inoutarg493);
            d = inoutarg493.value;
            if (tt11 != null) {
                t1 = tt11.getNext();
                f = d;
            }
        }
        if (t1 == null) {
            if (isDollar == null) 
                return null;
        }
        else if ((t1.getNext() != null && t1.getNext().isValue("С", "З") && t1.getNext().getNext() != null) && t1.getNext().getNext().isValue("ПОЛОВИНА", null)) {
            f += 0.5;
            t1 = t1.getNext().getNext();
        }
        if (t1 != null && t1.isHiphen() && t1.getNext() != null) 
            t1 = t1.getNext();
        boolean det = false;
        double altf = f;
        if ((t1 != null && t1.getNext() != null && t1.isChar('(')) && (((t1.getNext() instanceof com.pullenti.ner.NumberToken) || t1.getNext().isValue("НОЛЬ", null))) && t1.getNext().getNext() != null) {
            com.pullenti.ner.NumberToken nt1 = (com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t1.getNext(), com.pullenti.ner.NumberToken.class);
            long val = (long)0;
            if (nt1 != null) 
                val = nt1.value;
            if (((long)f) == val) {
                com.pullenti.ner.Token ttt = t1.getNext().getNext();
                if (ttt.isChar(')')) {
                    t1 = ttt.getNext();
                    det = true;
                }
                else if (((((ttt instanceof com.pullenti.ner.NumberToken) && ((((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(ttt, com.pullenti.ner.NumberToken.class))).value < ((long)100)) && ttt.getNext() != null) && ttt.getNext().isChar('/') && ttt.getNext().getNext() != null) && com.pullenti.n2j.Utils.stringsEq(ttt.getNext().getNext().getSourceText(), "100") && ttt.getNext().getNext().getNext() != null) && ttt.getNext().getNext().getNext().isChar(')')) {
                    int rest = getDecimalRest100(f);
                    if (rest == (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(ttt, com.pullenti.ner.NumberToken.class))).value) {
                        t1 = ttt.getNext().getNext().getNext().getNext();
                        det = true;
                    }
                }
                else if ((ttt.isValue("ЦЕЛЫХ", null) && (ttt.getNext() instanceof com.pullenti.ner.NumberToken) && ttt.getNext().getNext() != null) && ttt.getNext().getNext().getNext() != null && ttt.getNext().getNext().getNext().isChar(')')) {
                    com.pullenti.ner.NumberToken num2 = (com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(ttt.getNext(), com.pullenti.ner.NumberToken.class);
                    altf = (double)num2.value;
                    if (ttt.getNext().getNext().isValue("ДЕСЯТЫЙ", null)) 
                        altf /= ((double)10);
                    else if (ttt.getNext().getNext().isValue("СОТЫЙ", null)) 
                        altf /= ((double)100);
                    else if (ttt.getNext().getNext().isValue("ТЫСЯЧНЫЙ", null)) 
                        altf /= ((double)1000);
                    else if (ttt.getNext().getNext().isValue("ДЕСЯТИТЫСЯЧНЫЙ", null)) 
                        altf /= ((double)10000);
                    else if (ttt.getNext().getNext().isValue("СТОТЫСЯЧНЫЙ", null)) 
                        altf /= ((double)100000);
                    else if (ttt.getNext().getNext().isValue("МИЛЛИОННЫЙ", null)) 
                        altf /= ((double)1000000);
                    if (altf < 1) {
                        altf += ((double)val);
                        t1 = ttt.getNext().getNext().getNext().getNext();
                        det = true;
                    }
                }
                else {
                    TerminToken toks1 = m_Postfixes.tryParse(ttt, TerminParseAttr.NO);
                    if (toks1 != null) {
                        if (((NumberExType)toks1.termin.tag) == NumberExType.MONEY) {
                            if (toks1.getEndToken().getNext() != null && toks1.getEndToken().getNext().isChar(')')) {
                                NumberExToken res = _new491(t, toks1.getEndToken().getNext(), nt.value, nt.typ, NumberExType.MONEY, f, altf, toks1.getBeginToken().getMorph());
                                return _correctMoney(res, toks1.getBeginToken());
                            }
                        }
                    }
                    NumberExToken res2 = tryParseNumberWithPostfix(t1.getNext());
                    if (res2 != null && res2.getEndToken().getNext() != null && res2.getEndToken().getNext().isChar(')')) {
                        if (res2.value == ((int)f)) {
                            res2.setBeginToken(t);
                            res2.setEndToken(res2.getEndToken().getNext());
                            res2.altRealValue = res2.realValue;
                            res2.realValue = f;
                            _correctExtTypes(res2);
                            if (res2.getWhitespacesAfterCount() < 2) {
                                TerminToken toks2 = m_Postfixes.tryParse(res2.getEndToken().getNext(), TerminParseAttr.NO);
                                if (toks2 != null) {
                                    if (((NumberExType)toks2.termin.tag) == NumberExType.MONEY) 
                                        res2.setEndToken(toks2.getEndToken());
                                }
                            }
                            return res2;
                        }
                    }
                }
            }
            else if (nt1 != null && nt1.typ == com.pullenti.ner.NumberSpellingType.WORDS && nt.typ == com.pullenti.ner.NumberSpellingType.DIGIT) {
                altf = (double)nt1.value;
                com.pullenti.ner.Token ttt = t1.getNext().getNext();
                if (ttt.isChar(')')) {
                    t1 = ttt.getNext();
                    det = true;
                }
                if (!det) 
                    altf = f;
            }
        }
        if ((t1 != null && t1.isChar('(') && t1.getNext() != null) && t1.getNext().isValue("СУММА", null)) {
            BracketSequenceToken br = BracketHelper.tryParse(t1, BracketParseAttr.NO, 100);
            if (br != null) 
                t1 = br.getEndToken().getNext();
        }
        if (isDollar != null) {
            com.pullenti.ner.Token te = null;
            if (t1 != null) 
                te = t1.getPrevious();
            else 
                for(t1 = t0; t1 != null; t1 = t1.getNext()) {
                    if (t1.getNext() == null) 
                        te = t1;
                }
            if (te == null) 
                return null;
            long val = nt.value;
            if (te.isHiphen() && te.getNext() != null) {
                if (te.getNext().isValue("МИЛЛИОННЫЙ", null)) {
                    val *= ((long)1000000);
                    f *= ((double)1000000);
                    altf *= ((double)1000000);
                    te = te.getNext();
                }
                else if (te.getNext().isValue("МИЛЛИАРДНЫЙ", null)) {
                    val *= ((long)1000000000);
                    f *= ((double)1000000000);
                    altf *= ((double)1000000000);
                    te = te.getNext();
                }
            }
            if (!te.isWhitespaceAfter() && (te.getNext() instanceof com.pullenti.ner.TextToken)) {
                if (te.getNext().isValue("M", null)) {
                    val *= ((long)1000000);
                    f *= ((double)1000000);
                    altf *= ((double)1000000);
                    te = te.getNext();
                }
                else if (te.getNext().isValue("BN", null)) {
                    val *= ((long)1000000000);
                    f *= ((double)1000000000);
                    altf *= ((double)1000000000);
                    te = te.getNext();
                }
            }
            return _new495(t0, te, val, nt.typ, NumberExType.MONEY, f, altf, isDollar);
        }
        if (t1 == null || ((t1.isNewlineBefore() && !det))) 
            return null;
        TerminToken toks = m_Postfixes.tryParse(t1, TerminParseAttr.NO);
        if ((toks == null && det && (t1 instanceof com.pullenti.ner.NumberToken)) && (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t1, com.pullenti.ner.NumberToken.class))).value == ((long)0)) 
            toks = m_Postfixes.tryParse(t1.getNext(), TerminParseAttr.NO);
        if (toks != null) {
            t1 = toks.getEndToken();
            if (!t1.isChar('.') && t1.getNext() != null && t1.getNext().isChar('.')) {
                if ((t1 instanceof com.pullenti.ner.TextToken) && t1.isValue(toks.termin.terms.get(0).getCanonicalText(), null)) {
                }
                else 
                    t1 = t1.getNext();
            }
            if (com.pullenti.n2j.Utils.stringsEq(toks.termin.getCanonicText(), "LTL")) 
                return null;
            if (toks.getBeginToken() == t1) {
                if (t1.getMorph()._getClass().isPreposition() || t1.getMorph()._getClass().isConjunction()) {
                    if (t1.isWhitespaceBefore() && t1.isWhitespaceAfter()) 
                        return null;
                }
            }
            NumberExType ty = (NumberExType)toks.termin.tag;
            NumberExToken res = _new491(t, t1, nt.value, nt.typ, ty, f, altf, toks.getBeginToken().getMorph());
            if (ty != NumberExType.MONEY) {
                _correctExtTypes(res);
                return res;
            }
            return _correctMoney(res, toks.getBeginToken());
        }
        if (t1.isChar('%')) 
            return _new497(t, t1, nt.value, nt.typ, NumberExType.PERCENT, f, altf);
        String money = NumberHelper.isMoneyChar(t1);
        if (money != null) 
            return _new495(t, t1, nt.value, nt.typ, NumberExType.MONEY, f, altf, money);
        if (t1.getNext() != null && ((t1.getMorph()._getClass().isPreposition() || t1.getMorph()._getClass().isConjunction()))) {
            if (t1.isValue("НА", null)) {
            }
            else {
                NumberExToken nn = tryParseNumberWithPostfix(t1.getNext());
                if (nn != null) 
                    return _new499(t, t, nt.value, nt.typ, nn.exTyp, f, altf, nn.exTyp2, nn.exTypParam);
            }
        }
        if (!t1.isWhitespaceAfter() && (t1.getNext() instanceof com.pullenti.ner.NumberToken) && (t1 instanceof com.pullenti.ner.TextToken)) {
            String term = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t1, com.pullenti.ner.TextToken.class))).term;
            NumberExType ty = NumberExType.UNDEFINED;
            if (com.pullenti.n2j.Utils.stringsEq(term, "СМХ") || com.pullenti.n2j.Utils.stringsEq(term, "CMX")) 
                ty = NumberExType.SANTIMETER;
            else if (com.pullenti.n2j.Utils.stringsEq(term, "MX") || com.pullenti.n2j.Utils.stringsEq(term, "МХ")) 
                ty = NumberExType.METER;
            else if (com.pullenti.n2j.Utils.stringsEq(term, "MMX") || com.pullenti.n2j.Utils.stringsEq(term, "ММХ")) 
                ty = NumberExType.MILLIMETER;
            if (ty != NumberExType.UNDEFINED) 
                return _new500(t, t1, nt.value, nt.typ, ty, f, altf, true);
        }
        return null;
    }

    private static int getDecimalRest100(double f) {
        int rest = (((int)(((((f - com.pullenti.n2j.Utils.mathTruncate(f)) + 0.0001)) * ((double)10000))))) / 100;
        return rest;
    }

    /**
     * Это попробовать только тип (постфикс) без самого числа
     * @param t 
     * @return 
     */
    public static NumberExToken tryAttachPostfixOnly(com.pullenti.ner.Token t) {
        TerminToken tok = m_Postfixes.tryParse(t, TerminParseAttr.NO);
        if (tok == null) 
            return null;
        NumberExToken res = new NumberExToken(t, tok.getEndToken(), (long)0, com.pullenti.ner.NumberSpellingType.DIGIT, (NumberExType)tok.termin.tag);
        _correctExtTypes(res);
        return res;
    }

    private static void _correctExtTypes(NumberExToken ex) {
        com.pullenti.ner.Token t = ex.getEndToken().getNext();
        if (t == null || t.getNext() == null) 
            return;
        NumberExType ty = ex.exTyp;
        com.pullenti.n2j.Outargwrapper<NumberExType> inoutarg502 = new com.pullenti.n2j.Outargwrapper< >(ty);
        com.pullenti.ner.Token tt = _corrExTyp2(t, inoutarg502);
        ty = inoutarg502.value;
        if (tt != null) {
            ex.exTyp = ty;
            ex.setEndToken(tt);
            t = tt.getNext();
        }
        if (t == null || t.getNext() == null) 
            return;
        if (t.isCharOf("/\\") || t.isValue("НА", null)) {
        }
        else 
            return;
        TerminToken tok = m_Postfixes.tryParse(t.getNext(), TerminParseAttr.NO);
        if (tok != null && ((((NumberExType)tok.termin.tag) != NumberExType.MONEY))) {
            ex.exTyp2 = (NumberExType)tok.termin.tag;
            ex.setEndToken(tok.getEndToken());
            ty = ex.exTyp2;
            com.pullenti.n2j.Outargwrapper<NumberExType> inoutarg501 = new com.pullenti.n2j.Outargwrapper< >(ty);
            tt = _corrExTyp2(ex.getEndToken().getNext(), inoutarg501);
            ty = inoutarg501.value;
            if (tt != null) {
                ex.exTyp2 = ty;
                ex.setEndToken(tt);
                t = tt.getNext();
            }
        }
    }

    private static com.pullenti.ner.Token _corrExTyp2(com.pullenti.ner.Token t, com.pullenti.n2j.Outargwrapper<NumberExType> _typ) {
        if (t == null) 
            return null;
        int num = 0;
        com.pullenti.ner.Token tt = t;
        if (t.isChar('³')) 
            num = 3;
        else if (t.isChar('²')) 
            num = 2;
        else if (!t.isWhitespaceBefore() && (t instanceof com.pullenti.ner.NumberToken) && (((((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value == ((long)3) || (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value == ((long)2)))) 
            num = (int)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value;
        else if ((t.isChar('<') && (t.getNext() instanceof com.pullenti.ner.NumberToken) && t.getNext().getNext() != null) && t.getNext().getNext().isChar('>')) {
            num = (int)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class))).value;
            tt = t.getNext().getNext();
        }
        if (num == 3) {
            if (_typ.value == NumberExType.METER) {
                _typ.value = NumberExType.METER3;
                return tt;
            }
            if (_typ.value == NumberExType.SANTIMETER) {
                _typ.value = NumberExType.SANTIMETER3;
                return tt;
            }
        }
        if (num == 2) {
            if (_typ.value == NumberExType.METER) {
                _typ.value = NumberExType.METER2;
                return tt;
            }
            if (_typ.value == NumberExType.SANTIMETER) {
                _typ.value = NumberExType.SANTIMETER2;
                return tt;
            }
        }
        return null;
    }

    private static NumberExToken _correctMoney(NumberExToken res, com.pullenti.ner.Token t1) {
        if (t1 == null) 
            return null;
        java.util.ArrayList<TerminToken> toks = m_Postfixes.tryParseAll(t1, TerminParseAttr.NO);
        if (toks == null || toks.size() == 0) 
            return null;
        com.pullenti.ner.Token tt = toks.get(0).getEndToken().getNext();
        com.pullenti.ner.Referent r = (tt == null ? null : tt.getReferent());
        String alpha2 = null;
        if (r != null && com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), "GEO")) 
            alpha2 = r.getStringValue("ALPHA2");
        if (alpha2 != null && toks.size() > 0) {
            for(int i = toks.size() - 1; i >= 0; i--) {
                if (!toks.get(i).termin.getCanonicText().startsWith(alpha2)) 
                    toks.remove(i);
            }
            if (toks.size() == 0) 
                toks = m_Postfixes.tryParseAll(t1, TerminParseAttr.NO);
        }
        if (toks.size() > 1) {
            alpha2 = null;
            String str = toks.get(0).termin.terms.get(0).getCanonicalText();
            if (com.pullenti.n2j.Utils.stringsEq(str, "РУБЛЬ") || com.pullenti.n2j.Utils.stringsEq(str, "RUBLE")) 
                alpha2 = "RU";
            else if (com.pullenti.n2j.Utils.stringsEq(str, "ДОЛЛАР") || com.pullenti.n2j.Utils.stringsEq(str, "ДОЛАР") || com.pullenti.n2j.Utils.stringsEq(str, "DOLLAR")) 
                alpha2 = "US";
            else if (com.pullenti.n2j.Utils.stringsEq(str, "ФУНТ") || com.pullenti.n2j.Utils.stringsEq(str, "POUND")) 
                alpha2 = "UK";
            if (alpha2 != null) {
                for(int i = toks.size() - 1; i >= 0; i--) {
                    if (!toks.get(i).termin.getCanonicText().startsWith(alpha2)) 
                        toks.remove(i);
                }
            }
            alpha2 = null;
        }
        if (toks.size() < 1) 
            return null;
        res.exTypParam = toks.get(0).termin.getCanonicText();
        if (alpha2 != null && tt != null) 
            res.setEndToken(tt);
        tt = res.getEndToken().getNext();
        if (tt != null && tt.isCommaAnd()) 
            tt = tt.getNext();
        if ((tt instanceof com.pullenti.ner.NumberToken) && tt.getNext() != null && (tt.getWhitespacesAfterCount() < 4)) {
            com.pullenti.ner.Token tt1 = tt.getNext();
            if ((tt1 != null && tt1.isChar('(') && (tt1.getNext() instanceof com.pullenti.ner.NumberToken)) && tt1.getNext().getNext() != null && tt1.getNext().getNext().isChar(')')) {
                if ((((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.NumberToken.class))).value == (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(tt1.getNext(), com.pullenti.ner.NumberToken.class))).value) 
                    tt1 = tt1.getNext().getNext().getNext();
            }
            TerminToken tok = m_SmallMoney.tryParse(tt1, TerminParseAttr.NO);
            if (tok == null && tt1 != null && tt1.isChar(')')) 
                tok = m_SmallMoney.tryParse(tt1.getNext(), TerminParseAttr.NO);
            if (tok != null) {
                int max = (int)tok.termin.tag;
                int val = (int)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.NumberToken.class))).value;
                if (val < max) {
                    double f = (double)val;
                    f /= ((double)max);
                    double f0 = res.realValue - ((double)((long)res.realValue));
                    int re0 = (int)(((f0 * ((double)100)) + 0.0001));
                    if (re0 > 0 && val != re0) 
                        res.altRestMoney = val;
                    else if (f0 == 0) 
                        res.realValue += f;
                    f0 = res.altRealValue - ((double)((long)res.altRealValue));
                    re0 = (int)(((f0 * ((double)100)) + 0.0001));
                    if (re0 > 0 && val != re0) 
                        res.altRestMoney = val;
                    else if (f0 == 0) 
                        res.altRealValue += f;
                    res.setEndToken(tok.getEndToken());
                }
            }
        }
        else if ((tt instanceof com.pullenti.ner.TextToken) && tt.isValue("НОЛЬ", null)) {
            TerminToken tok = m_SmallMoney.tryParse(tt.getNext(), TerminParseAttr.NO);
            if (tok != null) 
                res.setEndToken(tok.getEndToken());
        }
        return res;
    }

    public double normalizeValue(com.pullenti.n2j.Outargwrapper<NumberExType> ty) {
        double val = realValue;
        NumberExType ety = exTyp;
        if (ty.value == ety) 
            return val;
        if (exTyp2 != NumberExType.UNDEFINED) 
            return val;
        if (ty.value == NumberExType.GRAMM) {
            if (exTyp == NumberExType.KILOGRAM) {
                val *= ((double)1000);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.MILLIGRAM) {
                val /= ((double)1000);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.TONNA) {
                val *= ((double)1000000);
                ety = ty.value;
            }
        }
        else if (ty.value == NumberExType.KILOGRAM) {
            if (exTyp == NumberExType.GRAMM) {
                val /= ((double)1000);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.TONNA) {
                val *= ((double)1000);
                ety = ty.value;
            }
        }
        else if (ty.value == NumberExType.TONNA) {
            if (exTyp == NumberExType.KILOGRAM) {
                val /= ((double)1000);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.GRAMM) {
                val /= ((double)1000000);
                ety = ty.value;
            }
        }
        else if (ty.value == NumberExType.MILLIMETER) {
            if (exTyp == NumberExType.SANTIMETER) {
                val *= ((double)10);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.METER) {
                val *= ((double)1000);
                ety = ty.value;
            }
        }
        else if (ty.value == NumberExType.SANTIMETER) {
            if (exTyp == NumberExType.MILLIMETER) {
                val *= ((double)10);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.METER) {
                val *= ((double)100);
                ety = ty.value;
            }
        }
        else if (ty.value == NumberExType.METER) {
            if (exTyp == NumberExType.KILOMETER) {
                val *= ((double)1000);
                ety = ty.value;
            }
        }
        else if (ty.value == NumberExType.LITR) {
            if (exTyp == NumberExType.MILLILITR) {
                val /= ((double)1000);
                ety = ty.value;
            }
        }
        else if (ty.value == NumberExType.MILLILITR) {
            if (exTyp == NumberExType.LITR) {
                val *= ((double)1000);
                ety = ty.value;
            }
        }
        else if (ty.value == NumberExType.GEKTAR) {
            if (exTyp == NumberExType.METER2) {
                val /= ((double)10000);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.AR) {
                val /= ((double)100);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.KILOMETER2) {
                val *= ((double)100);
                ety = ty.value;
            }
        }
        else if (ty.value == NumberExType.KILOMETER2) {
            if (exTyp == NumberExType.GEKTAR) {
                val /= ((double)100);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.AR) {
                val /= ((double)10000);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.METER2) {
                val /= ((double)1000000);
                ety = ty.value;
            }
        }
        else if (ty.value == NumberExType.METER2) {
            if (exTyp == NumberExType.AR) {
                val *= ((double)100);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.GEKTAR) {
                val *= ((double)10000);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.KILOMETER2) {
                val *= ((double)1000000);
                ety = ty.value;
            }
        }
        else if (ty.value == NumberExType.DAY) {
            if (exTyp == NumberExType.YEAR) {
                val *= ((double)365);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.MONTH) {
                val *= ((double)30);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.WEEK) {
                val *= ((double)7);
                ety = ty.value;
            }
        }
        ty.value = ety;
        return val;
    }

    public static String convertToString(double d) {
        long lo = (long)d;
        if (lo == ((long)0)) 
            return ((Double)d).toString().replace(",", ".");
        double rest = d - ((double)lo);
        if (rest < 0.000000001) 
            return ((Long)lo).toString();
        return ((Double)d).toString().replace(",", ".");
    }

    public static String exTypToString(NumberExType ty, NumberExType ty2) {
        if (ty2 != NumberExType.UNDEFINED) 
            return exTypToString(ty, NumberExType.UNDEFINED) + "/" + exTypToString(ty2, NumberExType.UNDEFINED);
        switch((ty).value()) { 
        case 1:
            return "%";
        case 13:
            return "ГР.";
        case 15:
            return "КГ.";
        case 4:
            return "КМ.";
        case 2:
            return "М.";
        case 8:
            return "КВ.М.";
        case 9:
            return "АР";
        case 10:
            return "ГА";
        case 11:
            return "КВ.КМ.";
        case 12:
            return "КУБ.М.";
        case 14:
            return "МГ.";
        case 3:
            return "ММ.";
        case 5:
            return "СМ.";
        case 6:
            return "КВ.СМ.";
        case 7:
            return "КУБ.СМ.";
        case 16:
            return "Т.";
        case 18:
            return "МЛ.";
        case 17:
            return "Л.";
        case 32:
            return "Ч.";
        case 33:
            return "МИН.";
        case 34:
            return "СЕК.";
        case 39:
            return "деньги";
        case 35:
            return "ЛЕТ";
        case 37:
            return "НЕД.";
        case 36:
            return "МЕС.";
        case 38:
            return "ДН.";
        case 40:
            return "ШТ.";
        case 41:
            return "УП.";
        case 42:
            return "РУЛОН";
        case 44:
            return "КОМПЛЕКТ";
        case 43:
            return "НАБОР";
        case 45:
            return "ПАР";
        case 46:
            return "ФЛАКОН";
        }
        return "";
    }

    @Override
    public String toString() {
        return ((Double)realValue).toString() + ((String)com.pullenti.n2j.Utils.notnull(exTypParam, exTypToString(exTyp, exTyp2)));
    }

    public static void initialize() {
        if (m_Postfixes != null) 
            return;
        m_Postfixes = new TerminCollection();
        Termin t;
        t = Termin._new503("КВАДРАТНЫЙ МЕТР", com.pullenti.morph.MorphLang.RU, true, "КВ.М.", NumberExType.METER2);
        t.addAbridge("КВ.МЕТР");
        t.addAbridge("КВ.МЕТРА");
        t.addAbridge("КВ.М.");
        m_Postfixes.add(t);
        t = Termin._new503("КВАДРАТНИЙ МЕТР", com.pullenti.morph.MorphLang.UA, true, "КВ.М.", NumberExType.METER2);
        t.addAbridge("КВ.МЕТР");
        t.addAbridge("КВ.МЕТРА");
        t.addAbridge("КВ.М.");
        m_Postfixes.add(t);
        t = Termin._new503("КВАДРАТНЫЙ КИЛОМЕТР", com.pullenti.morph.MorphLang.RU, true, "КВ.КМ.", NumberExType.KILOMETER2);
        t.addVariant("КВАДРАТНИЙ КІЛОМЕТР", true);
        t.addAbridge("КВ.КМ.");
        m_Postfixes.add(t);
        t = Termin._new503("ГЕКТАР", com.pullenti.morph.MorphLang.RU, true, "ГА", NumberExType.GEKTAR);
        t.addAbridge("ГА");
        m_Postfixes.add(t);
        t = Termin._new503("АР", com.pullenti.morph.MorphLang.RU, true, "АР", NumberExType.AR);
        t.addVariant("СОТКА", true);
        m_Postfixes.add(t);
        t = Termin._new503("КУБИЧЕСКИЙ МЕТР", com.pullenti.morph.MorphLang.RU, true, "КУБ.М.", NumberExType.METER3);
        t.addVariant("КУБІЧНИЙ МЕТР", true);
        t.addAbridge("КУБ.МЕТР");
        t.addAbridge("КУБ.М.");
        m_Postfixes.add(t);
        t = Termin._new503("МЕТР", com.pullenti.morph.MorphLang.RU, true, "М.", NumberExType.METER);
        t.addAbridge("М.");
        t.addAbridge("M.");
        m_Postfixes.add(t);
        t = Termin._new503("МЕТРОВЫЙ", com.pullenti.morph.MorphLang.RU, true, "М.", NumberExType.METER);
        t.addVariant("МЕТРОВИЙ", true);
        m_Postfixes.add(t);
        t = Termin._new503("МИЛЛИМЕТР", com.pullenti.morph.MorphLang.RU, true, "ММ.", NumberExType.MILLIMETER);
        t.addAbridge("ММ");
        t.addAbridge("MM");
        t.addVariant("МІЛІМЕТР", true);
        m_Postfixes.add(t);
        t = Termin._new503("МИЛЛИМЕТРОВЫЙ", com.pullenti.morph.MorphLang.RU, true, "ММ.", NumberExType.MILLIMETER);
        t.addVariant("МІЛІМЕТРОВИЙ", true);
        m_Postfixes.add(t);
        t = Termin._new503("САНТИМЕТР", com.pullenti.morph.MorphLang.RU, true, "СМ.", NumberExType.SANTIMETER);
        t.addAbridge("СМ");
        t.addAbridge("CM");
        m_Postfixes.add(t);
        t = Termin._new503("САНТИМЕТРОВЫЙ", com.pullenti.morph.MorphLang.RU, true, "СМ.", NumberExType.SANTIMETER);
        t.addVariant("САНТИМЕТРОВИЙ", true);
        m_Postfixes.add(t);
        t = Termin._new503("КВАДРАТНЫЙ САНТИМЕТР", com.pullenti.morph.MorphLang.RU, true, "КВ.СМ.", NumberExType.SANTIMETER2);
        t.addVariant("КВАДРАТНИЙ САНТИМЕТР", true);
        t.addAbridge("КВ.СМ.");
        t.addAbridge("СМ.КВ.");
        m_Postfixes.add(t);
        t = Termin._new503("КУБИЧЕСКИЙ САНТИМЕТР", com.pullenti.morph.MorphLang.RU, true, "КУБ.СМ.", NumberExType.SANTIMETER3);
        t.addVariant("КУБІЧНИЙ САНТИМЕТР", true);
        t.addAbridge("КУБ.САНТИМЕТР");
        t.addAbridge("КУБ.СМ.");
        t.addAbridge("СМ.КУБ.");
        m_Postfixes.add(t);
        t = Termin._new503("КИЛОМЕТР", com.pullenti.morph.MorphLang.RU, true, "КМ.", NumberExType.KILOMETER);
        t.addAbridge("КМ");
        t.addAbridge("KM");
        t.addVariant("КІЛОМЕТР", true);
        m_Postfixes.add(t);
        t = Termin._new503("КИЛОМЕТРОВЫЙ", com.pullenti.morph.MorphLang.RU, true, "КМ.", NumberExType.KILOMETER);
        t.addVariant("КІЛОМЕТРОВИЙ", true);
        m_Postfixes.add(t);
        t = Termin._new503("ГРАММ", com.pullenti.morph.MorphLang.RU, true, "ГР.", NumberExType.GRAMM);
        t.addAbridge("ГР");
        t.addAbridge("Г");
        t.addVariant("ГРАМ", true);
        m_Postfixes.add(t);
        t = Termin._new503("ГРАММОВЫЙ", com.pullenti.morph.MorphLang.RU, true, "ГР.", NumberExType.GRAMM);
        m_Postfixes.add(t);
        t = Termin._new503("КИЛОГРАММ", com.pullenti.morph.MorphLang.RU, true, "КГ.", NumberExType.KILOGRAM);
        t.addAbridge("КГ");
        t.addVariant("КІЛОГРАМ", true);
        m_Postfixes.add(t);
        t = Termin._new503("КИЛОГРАММОВЫЙ", com.pullenti.morph.MorphLang.RU, true, "КГ.", NumberExType.KILOGRAM);
        t.addVariant("КІЛОГРАМОВИЙ", true);
        m_Postfixes.add(t);
        t = Termin._new503("МИЛЛИГРАММ", com.pullenti.morph.MorphLang.RU, true, "МГ.", NumberExType.MILLIGRAM);
        t.addAbridge("МГ");
        t.addVariant("МІЛІГРАМ", true);
        m_Postfixes.add(t);
        t = Termin._new503("МИЛЛИГРАММОВЫЙ", com.pullenti.morph.MorphLang.RU, true, "МГ.", NumberExType.MILLIGRAM);
        t.addVariant("МИЛЛИГРАМОВЫЙ", true);
        t.addVariant("МІЛІГРАМОВИЙ", true);
        m_Postfixes.add(t);
        t = Termin._new503("ТОННА", com.pullenti.morph.MorphLang.RU, true, "Т.", NumberExType.TONNA);
        t.addAbridge("Т");
        t.addAbridge("T");
        m_Postfixes.add(t);
        t = Termin._new503("ТОННЫЙ", com.pullenti.morph.MorphLang.RU, true, "Т.", NumberExType.TONNA);
        t.addVariant("ТОННИЙ", true);
        m_Postfixes.add(t);
        t = Termin._new503("ЛИТР", com.pullenti.morph.MorphLang.RU, true, "Л.", NumberExType.LITR);
        t.addAbridge("Л");
        t.addVariant("ЛІТР", true);
        m_Postfixes.add(t);
        t = Termin._new503("ЛИТРОВЫЙ", com.pullenti.morph.MorphLang.RU, true, "Л.", NumberExType.LITR);
        t.addVariant("ЛІТРОВИЙ", true);
        m_Postfixes.add(t);
        t = Termin._new503("МИЛЛИЛИТР", com.pullenti.morph.MorphLang.RU, true, "МЛ.", NumberExType.MILLILITR);
        t.addAbridge("МЛ");
        t.addVariant("МІЛІЛІТР", true);
        m_Postfixes.add(t);
        t = Termin._new503("МИЛЛИЛИТРОВЫЙ", com.pullenti.morph.MorphLang.RU, true, "МЛ.", NumberExType.MILLILITR);
        t.addVariant("МІЛІЛІТРОВИЙ", true);
        m_Postfixes.add(t);
        t = Termin._new503("ВОЛЬТ", com.pullenti.morph.MorphLang.RU, true, "В", NumberExType.VOLT);
        t.addVariant("VOLT", true);
        t.addAbridge("V");
        t.addAbridge("В");
        m_Postfixes.add(t);
        t = Termin._new503("КИЛОВОЛЬТ", com.pullenti.morph.MorphLang.RU, true, "КВ", NumberExType.KILOVOLT);
        t.addVariant("KILOVOLT", true);
        t.addAbridge("KV");
        t.addAbridge("КВ");
        m_Postfixes.add(t);
        t = Termin._new503("МЕГАВОЛЬТ", com.pullenti.morph.MorphLang.RU, true, "МВ", NumberExType.MEGAVOLT);
        t.addVariant("MEGAVOLT", true);
        t.addAbridge("MV");
        t.addAbridge("МВ");
        m_Postfixes.add(t);
        t = Termin._new503("ВАТТ", com.pullenti.morph.MorphLang.RU, true, "ВТ", NumberExType.WATT);
        t.addVariant("WATT", true);
        t.addAbridge("W");
        t.addAbridge("ВТ");
        m_Postfixes.add(t);
        t = Termin._new503("КИЛОВАТТ", com.pullenti.morph.MorphLang.RU, true, "КВТ", NumberExType.KILOWATT);
        t.addVariant("KILOVOLT", true);
        t.addAbridge("KV");
        t.addAbridge("КВ");
        m_Postfixes.add(t);
        t = Termin._new503("МЕГАВАТТ", com.pullenti.morph.MorphLang.RU, true, "МВТ", NumberExType.MEGAWATT);
        t.addVariant("MEGAWATT", true);
        t.addAbridge("MW");
        t.addAbridge("МВТ");
        m_Postfixes.add(t);
        t = Termin._new503("ЧАС", com.pullenti.morph.MorphLang.RU, true, "Ч.", NumberExType.HOUR);
        t.addAbridge("Ч.");
        t.addVariant("ГОДИНА", true);
        m_Postfixes.add(t);
        t = Termin._new503("МИНУТА", com.pullenti.morph.MorphLang.RU, true, "МИН.", NumberExType.MINUTE);
        t.addAbridge("МИН.");
        t.addVariant("ХВИЛИНА", true);
        m_Postfixes.add(t);
        t = Termin._new503("СЕКУНДА", com.pullenti.morph.MorphLang.RU, true, "СЕК.", NumberExType.SECOND);
        t.addAbridge("СЕК.");
        m_Postfixes.add(t);
        t = Termin._new503("ГОД", com.pullenti.morph.MorphLang.RU, true, "Г.", NumberExType.YEAR);
        t.addAbridge("Г.");
        t.addAbridge("ЛЕТ");
        t.addVariant("ЛЕТНИЙ", true);
        m_Postfixes.add(t);
        t = Termin._new503("МЕСЯЦ", com.pullenti.morph.MorphLang.RU, true, "МЕС.", NumberExType.MONTH);
        t.addAbridge("МЕС.");
        t.addVariant("МЕСЯЧНЫЙ", true);
        t.addVariant("КАЛЕНДАРНЫЙ МЕСЯЦ", true);
        m_Postfixes.add(t);
        t = Termin._new503("ДЕНЬ", com.pullenti.morph.MorphLang.RU, true, "ДН.", NumberExType.DAY);
        t.addAbridge("ДН.");
        t.addVariant("ДНЕВНЫЙ", true);
        t.addVariant("СУТКИ", true);
        t.addVariant("СУТОЧНЫЙ", true);
        t.addVariant("КАЛЕНДАРНЫЙ ДЕНЬ", true);
        t.addVariant("РАБОЧИЙ ДЕНЬ", true);
        m_Postfixes.add(t);
        t = Termin._new503("НЕДЕЛЯ", com.pullenti.morph.MorphLang.RU, true, "НЕД.", NumberExType.WEEK);
        t.addVariant("НЕДЕЛЬНЫЙ", true);
        t.addVariant("КАЛЕНДАРНАЯ НЕДЕЛЯ", false);
        m_Postfixes.add(t);
        t = Termin._new503("ПРОЦЕНТ", com.pullenti.morph.MorphLang.RU, true, "%", NumberExType.PERCENT);
        t.addVariant("%", false);
        t.addVariant("ПРОЦ", true);
        t.addAbridge("ПРОЦ.");
        m_Postfixes.add(t);
        t = Termin._new503("ШТУКА", com.pullenti.morph.MorphLang.RU, true, "ШТ.", NumberExType.SHUK);
        t.addVariant("ШТ", false);
        t.addAbridge("ШТ.");
        t.addAbridge("ШТ-К");
        m_Postfixes.add(t);
        t = Termin._new503("УПАКОВКА", com.pullenti.morph.MorphLang.RU, true, "УП.", NumberExType.UPAK);
        t.addVariant("УПАК", true);
        t.addVariant("УП", true);
        t.addAbridge("УПАК.");
        t.addAbridge("УП.");
        t.addAbridge("УП-КА");
        m_Postfixes.add(t);
        t = Termin._new503("РУЛОН", com.pullenti.morph.MorphLang.RU, true, "РУЛОН", NumberExType.RULON);
        t.addVariant("РУЛ", true);
        t.addAbridge("РУЛ.");
        m_Postfixes.add(t);
        t = Termin._new503("НАБОР", com.pullenti.morph.MorphLang.RU, true, "НАБОР", NumberExType.NABOR);
        t.addVariant("НАБ", true);
        t.addAbridge("НАБ.");
        m_Postfixes.add(t);
        t = Termin._new503("КОМПЛЕКТ", com.pullenti.morph.MorphLang.RU, true, "КОМПЛЕКТ", NumberExType.KOMPLEKT);
        t.addVariant("КОМПЛ", true);
        t.addAbridge("КОМПЛ.");
        m_Postfixes.add(t);
        t = Termin._new503("ПАРА", com.pullenti.morph.MorphLang.RU, true, "ПАР", NumberExType.PARA);
        m_Postfixes.add(t);
        t = Termin._new503("ФЛАКОН", com.pullenti.morph.MorphLang.RU, true, "ФЛАКОН", NumberExType.FLAKON);
        t.addVariant("ФЛ", true);
        t.addAbridge("ФЛ.");
        t.addVariant("ФЛАК", true);
        t.addAbridge("ФЛАК.");
        m_Postfixes.add(t);
        m_SmallMoney = new TerminCollection();
        t = Termin._new142("УСЛОВНАЯ ЕДИНИЦА", "УЕ", NumberExType.MONEY);
        t.addAbridge("У.Е.");
        t.addAbridge("У.E.");
        t.addAbridge("Y.Е.");
        t.addAbridge("Y.E.");
        m_Postfixes.add(t);
        for(int k = 0; k < 3; k++) {
            String str = com.pullenti.ner.core.internal.ResourceHelper.getString((k == 0 ? "Money.csv" : (k == 1 ? "MoneyUA.csv" : "MoneyEN.csv")));
            if (str == null) 
                continue;
            com.pullenti.morph.MorphLang lang = (k == 0 ? com.pullenti.morph.MorphLang.RU : (k == 1 ? com.pullenti.morph.MorphLang.UA : com.pullenti.morph.MorphLang.EN));
            if (str == null) 
                continue;
            try {
                try (java.io.BufferedReader tr = new java.io.BufferedReader(new java.io.StringReader(str))) {
                    while(true) {
                        String line = tr.readLine();
                        if (line == null) 
                            break;
                        if (com.pullenti.n2j.Utils.isNullOrEmpty(line)) 
                            continue;
                        String[] parts = com.pullenti.n2j.Utils.split(line.toUpperCase(), String.valueOf(';'), false);
                        if (parts == null || parts.length != 5) 
                            continue;
                        if (com.pullenti.n2j.Utils.isNullOrEmpty(parts[1]) || com.pullenti.n2j.Utils.isNullOrEmpty(parts[2])) 
                            continue;
                        t = new Termin(null, new com.pullenti.morph.MorphLang(null), false);
                        t.initByNormalText(parts[1], lang);
                        t.setCanonicText(parts[2]);
                        t.tag = NumberExType.MONEY;
                        for(String p : com.pullenti.n2j.Utils.split(parts[0], String.valueOf(','), false)) {
                            if (com.pullenti.n2j.Utils.stringsNe(p, parts[1])) {
                                Termin t0 = new Termin(null, new com.pullenti.morph.MorphLang(null), false);
                                t0.initByNormalText(p, new com.pullenti.morph.MorphLang(null));
                                t.addVariantTerm(t0);
                            }
                        }
                        if (com.pullenti.n2j.Utils.stringsEq(parts[1], "РУБЛЬ")) 
                            t.addAbridge("РУБ.");
                        else if (com.pullenti.n2j.Utils.stringsEq(parts[1], "ГРИВНЯ")) 
                            t.addAbridge("ГРН.");
                        else if (com.pullenti.n2j.Utils.stringsEq(parts[1], "ДОЛЛАР")) {
                            t.addAbridge("ДОЛ.");
                            t.addAbridge("ДОЛЛ.");
                        }
                        else if (com.pullenti.n2j.Utils.stringsEq(parts[1], "ДОЛАР")) 
                            t.addAbridge("ДОЛ.");
                        m_Postfixes.add(t);
                        if (com.pullenti.n2j.Utils.isNullOrEmpty(parts[3])) 
                            continue;
                        int num = 0;
                        int i = parts[3].indexOf(' ');
                        if (i < 2) 
                            continue;
                        com.pullenti.n2j.Outargwrapper<Integer> inoutarg553 = new com.pullenti.n2j.Outargwrapper< >();
                        boolean inoutres554 = com.pullenti.n2j.Utils.parseInteger(parts[3].substring(0, 0+(i)), inoutarg553);
                        num = (inoutarg553.value != null ? inoutarg553.value : 0);
                        if (!inoutres554) 
                            continue;
                        String vv = parts[3].substring(i).trim();
                        t = new Termin(null, new com.pullenti.morph.MorphLang(null), false);
                        t.initByNormalText(parts[4], lang);
                        t.tag = num;
                        if (com.pullenti.n2j.Utils.stringsNe(vv, parts[4])) {
                            Termin t0 = new Termin(null, new com.pullenti.morph.MorphLang(null), false);
                            t0.initByNormalText(vv, new com.pullenti.morph.MorphLang(null));
                            t.addVariantTerm(t0);
                        }
                        if (com.pullenti.n2j.Utils.stringsEq(parts[4], "КОПЕЙКА") || com.pullenti.n2j.Utils.stringsEq(parts[4], "КОПІЙКА")) 
                            t.addAbridge("КОП.");
                        m_SmallMoney.add(t);
                    }
                }
            } catch(Exception ex) {
            }
        }
    }

    private static TerminCollection m_Postfixes;

    private static TerminCollection m_SmallMoney;

    public static NumberExToken _new489(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, long _arg3, com.pullenti.ner.NumberSpellingType _arg4, NumberExType _arg5, double _arg6) {
        NumberExToken res = new NumberExToken(_arg1, _arg2, _arg3, _arg4, _arg5);
        res.realValue = _arg6;
        return res;
    }
    public static NumberExToken _new491(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, long _arg3, com.pullenti.ner.NumberSpellingType _arg4, NumberExType _arg5, double _arg6, double _arg7, com.pullenti.ner.MorphCollection _arg8) {
        NumberExToken res = new NumberExToken(_arg1, _arg2, _arg3, _arg4, _arg5);
        res.realValue = _arg6;
        res.altRealValue = _arg7;
        res.setMorph(_arg8);
        return res;
    }
    public static NumberExToken _new492(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, long _arg3, com.pullenti.ner.NumberSpellingType _arg4, NumberExType _arg5, com.pullenti.ner.MorphCollection _arg6) {
        NumberExToken res = new NumberExToken(_arg1, _arg2, _arg3, _arg4, _arg5);
        res.setMorph(_arg6);
        return res;
    }
    public static NumberExToken _new495(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, long _arg3, com.pullenti.ner.NumberSpellingType _arg4, NumberExType _arg5, double _arg6, double _arg7, String _arg8) {
        NumberExToken res = new NumberExToken(_arg1, _arg2, _arg3, _arg4, _arg5);
        res.realValue = _arg6;
        res.altRealValue = _arg7;
        res.exTypParam = _arg8;
        return res;
    }
    public static NumberExToken _new497(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, long _arg3, com.pullenti.ner.NumberSpellingType _arg4, NumberExType _arg5, double _arg6, double _arg7) {
        NumberExToken res = new NumberExToken(_arg1, _arg2, _arg3, _arg4, _arg5);
        res.realValue = _arg6;
        res.altRealValue = _arg7;
        return res;
    }
    public static NumberExToken _new499(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, long _arg3, com.pullenti.ner.NumberSpellingType _arg4, NumberExType _arg5, double _arg6, double _arg7, NumberExType _arg8, String _arg9) {
        NumberExToken res = new NumberExToken(_arg1, _arg2, _arg3, _arg4, _arg5);
        res.realValue = _arg6;
        res.altRealValue = _arg7;
        res.exTyp2 = _arg8;
        res.exTypParam = _arg9;
        return res;
    }
    public static NumberExToken _new500(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, long _arg3, com.pullenti.ner.NumberSpellingType _arg4, NumberExType _arg5, double _arg6, double _arg7, boolean _arg8) {
        NumberExToken res = new NumberExToken(_arg1, _arg2, _arg3, _arg4, _arg5);
        res.realValue = _arg6;
        res.altRealValue = _arg7;
        res.multAfter = _arg8;
        return res;
    }
    public NumberExToken() {
        super();
    }
}
