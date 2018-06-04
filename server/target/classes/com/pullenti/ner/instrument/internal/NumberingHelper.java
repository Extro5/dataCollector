/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.instrument.internal;

/**
 * Поддержка анализа нумерации
 */
public class NumberingHelper {

    /**
     * Разница между двумя номерами
     * @param prev 
     * @param next 
     * @param canSubNumbers может быть 1. - 1.1 - 2.
     * @return больше 0 - отличаются на это число, 0 не стыкуются
     */
    public static int calcDelta(InstrToken1 prev, InstrToken1 next, boolean canSubNumbers) {
        int n1 = prev.getLastNumber();
        int n2 = next.getLastNumber();
        if (next.getLastMinNumber() > 0) 
            n2 = next.getLastMinNumber();
        if (prev.numbers.size() == next.numbers.size()) {
            if (prev.getTypContainerRank() > 0 && prev.getTypContainerRank() == next.getTypContainerRank()) {
            }
            else if (prev.numTyp == next.numTyp) {
            }
            else 
                return 0;
            if (prev.numbers.size() > 1) {
                for(int i = 0; i < (prev.numbers.size() - 1); i++) {
                    if (com.pullenti.n2j.Utils.stringsNe(prev.numbers.get(i), next.numbers.get(i))) 
                        return 0;
                }
            }
            if (n1 >= n2) 
                return 0;
            return n2 - n1;
        }
        if (!canSubNumbers) 
            return 0;
        if ((prev.numbers.size() + 1) == next.numbers.size() && next.numbers.size() > 0) {
            if (prev.getTypContainerRank() > 0 && prev.getTypContainerRank() == next.getTypContainerRank()) {
            }
            else if (prev.numTyp == NumberTypes.DIGIT && next.numTyp == NumberTypes.TWODIGITS) {
            }
            else if (prev.numTyp == NumberTypes.TWODIGITS && next.numTyp == NumberTypes.THREEDIGITS) {
            }
            else if (prev.numTyp == NumberTypes.THREEDIGITS && next.numTyp == NumberTypes.FOURDIGITS) {
            }
            else if (prev.numTyp == NumberTypes.LETTER && next.numTyp == NumberTypes.TWODIGITS && Character.isLetter(next.numbers.get(0).charAt(0))) {
            }
            else 
                return 0;
            for(int i = 0; i < prev.numbers.size(); i++) {
                if (com.pullenti.n2j.Utils.stringsNe(prev.numbers.get(i), next.numbers.get(i))) 
                    return 0;
            }
            return n2;
        }
        if ((prev.numbers.size() - 1) == next.numbers.size() && prev.numbers.size() > 1) {
            if (prev.getTypContainerRank() > 0 && prev.getTypContainerRank() == next.getTypContainerRank()) {
            }
            else if (prev.numTyp == NumberTypes.TWODIGITS) {
                if (next.numTyp == NumberTypes.DIGIT) {
                }
                else if (next.numTyp == NumberTypes.LETTER && Character.isLetter(prev.numbers.get(0).charAt(0))) {
                }
            }
            else if (prev.numTyp == NumberTypes.THREEDIGITS && next.numTyp == NumberTypes.TWODIGITS) {
            }
            else if (prev.numTyp == NumberTypes.FOURDIGITS && next.numTyp == NumberTypes.THREEDIGITS) {
            }
            else 
                return 0;
            for(int i = 0; i < (prev.numbers.size() - 2); i++) {
                if (com.pullenti.n2j.Utils.stringsNe(prev.numbers.get(i), next.numbers.get(i))) 
                    return 0;
            }
            com.pullenti.n2j.Outargwrapper<Integer> inoutarg1400 = new com.pullenti.n2j.Outargwrapper< >();
            boolean inoutres1401 = com.pullenti.n2j.Utils.parseInteger(prev.numbers.get(prev.numbers.size() - 2), inoutarg1400);
            n1 = (inoutarg1400.value != null ? inoutarg1400.value : 0);
            if (!inoutres1401) {
                if (prev.numbers.size() == 2) 
                    n1 = prev.getFirstNumber();
                else 
                    return 0;
            }
            if ((n1 + 1) != n2) 
                return 0;
            return n2 - n1;
        }
        if ((prev.numbers.size() - 2) == next.numbers.size() && prev.numbers.size() > 2) {
            if (prev.getTypContainerRank() > 0 && prev.getTypContainerRank() == next.getTypContainerRank()) {
            }
            else if (prev.numTyp == NumberTypes.THREEDIGITS && next.numTyp == NumberTypes.DIGIT) {
            }
            else if (prev.numTyp == NumberTypes.FOURDIGITS && next.numTyp == NumberTypes.TWODIGITS) {
            }
            else 
                return 0;
            for(int i = 0; i < (prev.numbers.size() - 3); i++) {
                if (com.pullenti.n2j.Utils.stringsNe(prev.numbers.get(i), next.numbers.get(i))) 
                    return 0;
            }
            com.pullenti.n2j.Outargwrapper<Integer> inoutarg1402 = new com.pullenti.n2j.Outargwrapper< >();
            boolean inoutres1403 = com.pullenti.n2j.Utils.parseInteger(prev.numbers.get(prev.numbers.size() - 3), inoutarg1402);
            n1 = (inoutarg1402.value != null ? inoutarg1402.value : 0);
            if (!inoutres1403) 
                return 0;
            if ((n1 + 1) != n2) 
                return 0;
            return n2 - n1;
        }
        if ((prev.numbers.size() - 3) == next.numbers.size() && prev.numbers.size() > 3) {
            if (prev.getTypContainerRank() > 0 && prev.getTypContainerRank() == next.getTypContainerRank()) {
            }
            else if (prev.numTyp == NumberTypes.FOURDIGITS && next.numTyp == NumberTypes.DIGIT) {
            }
            else 
                return 0;
            for(int i = 0; i < (prev.numbers.size() - 4); i++) {
                if (com.pullenti.n2j.Utils.stringsNe(prev.numbers.get(i), next.numbers.get(i))) 
                    return 0;
            }
            com.pullenti.n2j.Outargwrapper<Integer> inoutarg1404 = new com.pullenti.n2j.Outargwrapper< >();
            boolean inoutres1405 = com.pullenti.n2j.Utils.parseInteger(prev.numbers.get(prev.numbers.size() - 4), inoutarg1404);
            n1 = (inoutarg1404.value != null ? inoutarg1404.value : 0);
            if (!inoutres1405) 
                return 0;
            if ((n1 + 1) != n2) 
                return 0;
            return n2 - n1;
        }
        return 0;
    }

    /**
     * Выделить базовую верхоуровневую последовательность номеров (строк, содержащих номера)
     * @param lines исходные строки
     * @param checkSpecTexts проверять ли строки на мусор
     * @param canSubNumbers могут ли быть подномера типа 1. - 1.1 - 2.
     * @return null если не нашли или последовательность строк с номерами
     */
    public static java.util.ArrayList<InstrToken1> extractMainSequence(java.util.ArrayList<InstrToken1> lines, boolean checkSpecTexts, boolean canSubNumbers) {
        java.util.ArrayList<InstrToken1> res = null;
        int manySpecCharLines = 0;
        for(int i = 0; i < lines.size(); i++) {
            InstrToken1 li = lines.get(i);
            if (li.allUpper && li.isStandardTitle) {
                if (res != null && res.size() > 0 && res.get(res.size() - 1).tag == null) 
                    res.get(res.size() - 1).tag = li;
            }
            if (li.numbers.size() == 0) 
                continue;
            if (li.getLastNumber() == 901) {
            }
            if (li.numTyp == NumberTypes.LETTER) {
            }
            if (li.typ != InstrToken1.Types.LINE) 
                continue;
            if (res == null) {
                res = new java.util.ArrayList< >();
                if (li.numbers.size() == 1 && com.pullenti.n2j.Utils.stringsEq(li.numbers.get(0), "1") && li.numTyp == NumberTypes.DIGIT) {
                    if ((((i + 1) < lines.size()) && lines.get(i + 1).numbers.size() == 1 && com.pullenti.n2j.Utils.stringsEq(lines.get(i + 1).numbers.get(0), "1")) && lines.get(i + 1).numTyp == NumberTypes.DIGIT) {
                        for(int ii = i + 2; ii < lines.size(); ii++) {
                            if (lines.get(ii).numTyp == NumberTypes.ROMAN && lines.get(ii).numbers.size() > 0) {
                                if (com.pullenti.n2j.Utils.stringsEq(lines.get(ii).numbers.get(0), "2")) 
                                    li.numTyp = NumberTypes.ROMAN;
                                break;
                            }
                        }
                    }
                }
            }
            else {
                if (res.get(0).numSuffix != null) {
                    if (li.numSuffix != null && com.pullenti.n2j.Utils.stringsNe(li.numSuffix, res.get(0).numSuffix)) 
                        continue;
                }
                if (res.get(0).numbers.size() != li.numbers.size()) {
                    if (li.getBeginToken().getPrevious() != null && li.getBeginToken().getPrevious().isChar(':')) 
                        continue;
                    if (res.get(0).numSuffix == null || calcDelta(res.get(res.size() - 1), li, true) != 1) 
                        continue;
                    if (!canSubNumbers) {
                        if (((i + 1) < lines.size()) && calcDelta(res.get(res.size() - 1), lines.get(i + 1), false) == 1 && calcDelta(li, lines.get(i + 1), true) == 1) {
                        }
                        else 
                            continue;
                    }
                }
                else {
                    if (res.get(0).numTyp == NumberTypes.ROMAN && li.numTyp != NumberTypes.ROMAN) 
                        continue;
                    if (res.get(0).numTyp != NumberTypes.ROMAN && li.numTyp == NumberTypes.ROMAN) 
                        continue;
                    if (res.get(0).numTyp != NumberTypes.LETTER && li.numTyp == NumberTypes.LETTER) 
                        continue;
                }
            }
            res.add(li);
            if (li.hasManySpecChars) 
                manySpecCharLines++;
        }
        if (res == null) 
            return null;
        if (checkSpecTexts) {
            if (manySpecCharLines > (res.size() / 2)) 
                return null;
        }
        for(int i = 0; i < (res.size() - 1); i++) {
            if (calcDelta(res.get(i), res.get(i + 1), false) == 2) {
                int ii0 = lines.indexOf(res.get(i));
                int ii1 = (int)com.pullenti.n2j.Utils.indexOf(lines, res.get(i + 1), ii0);
                for(int j = ii0 + 1; j < ii1; j++) {
                    if (lines.get(j).numbers.size() > 0) {
                        if (calcDelta(res.get(i), lines.get(j), true) == 1 && NumberingHelper.calcDelta(lines.get(j), res.get(i + 1), true) == 1) {
                            res.add(i + 1, lines.get(j));
                            break;
                        }
                    }
                }
            }
        }
        boolean ch = true;
        while(ch) {
            ch = false;
            for(int i = 1; i < res.size(); i++) {
                int d = calcDelta(res.get(i - 1), res.get(i), false);
                if (com.pullenti.n2j.Utils.stringsEq(res.get(i - 1).numSuffix, res.get(i).numSuffix)) {
                    if (d == 1) 
                        continue;
                    if (((d > 1 && (d < 20))) || ((d == 0 && res.get(i - 1).numTyp == res.get(i).numTyp && res.get(i - 1).numbers.size() == res.get(i).numbers.size()))) {
                        if (calcDelta(res.get(i), res.get(i - 1), false) > 0) {
                            if (res.get(i - 1).tag != null && i > 2) {
                                for(int indRemoveRange = i + res.size() - i - 1, indMinIndex = i; indRemoveRange >= indMinIndex; indRemoveRange--) res.remove(indRemoveRange);
                                ch = true;
                                i--;
                                continue;
                            }
                        }
                        if ((i + 1) < res.size()) {
                            int dd = calcDelta(res.get(i), res.get(i + 1), false);
                            if (dd == 1) 
                                continue;
                            dd = calcDelta(res.get(i - 1), res.get(i + 1), false);
                            if (dd == 1) {
                                res.remove(i);
                                i--;
                                ch = true;
                                continue;
                            }
                        }
                        else if (d > 3) {
                            res.remove(i);
                            i--;
                            ch = true;
                        }
                        continue;
                    }
                }
                int j;
                for(j = i + 1; j < res.size(); j++) {
                    int dd = calcDelta(res.get(j - 1), res.get(j), false);
                    if (dd != 1 && dd != 2) 
                        break;
                    if (com.pullenti.n2j.Utils.stringsNe(res.get(j - 1).numSuffix, res.get(j).numSuffix)) 
                        break;
                }
                if ((d == 0 && calcDelta(res.get(i - 1), res.get(i), true) == 1 && res.get(i - 1).numSuffix != null) && com.pullenti.n2j.Utils.stringsEq(res.get(i).numSuffix, res.get(i - 1).numSuffix)) 
                    d = 1;
                if (d != 1 && j > (i + 1)) {
                    for(int indRemoveRange = i + j - i - 1, indMinIndex = i; indRemoveRange >= indMinIndex; indRemoveRange--) res.remove(indRemoveRange);
                    i--;
                    ch = true;
                    continue;
                }
                if (d == 1) {
                    if ((i + 1) >= res.size()) 
                        continue;
                    int dd = calcDelta(res.get(i), res.get(i + 1), false);
                    if (dd == 1 && com.pullenti.n2j.Utils.stringsEq(res.get(i - 1).numSuffix, res.get(i + 1).numSuffix)) {
                        if (com.pullenti.n2j.Utils.stringsNe(res.get(i).numSuffix, res.get(i - 1).numSuffix)) {
                            res.get(i).numSuffix = res.get(i - 1).numSuffix;
                            res.get(i).isNumDoubt = false;
                            ch = true;
                        }
                        continue;
                    }
                }
                if ((i + 1) < res.size()) {
                    int dd = calcDelta(res.get(i - 1), res.get(i + 1), false);
                    if (dd == 1 && com.pullenti.n2j.Utils.stringsEq(res.get(i - 1).numSuffix, res.get(i + 1).numSuffix)) {
                        if (d == 1 && calcDelta(res.get(i), res.get(i + 1), true) == 1) {
                        }
                        else {
                            res.remove(i);
                            ch = true;
                            continue;
                        }
                    }
                }
                else if (d == 0 || d > 10 || com.pullenti.n2j.Utils.stringsNe(res.get(i - 1).numSuffix, res.get(i).numSuffix)) {
                    res.remove(i);
                    ch = true;
                    continue;
                }
            }
        }
        int hasSuf = 0;
        for(InstrToken1 r : res) {
            if ((r.numSuffix != null || r.getTypContainerRank() > 0 || r.numbers.size() > 1) || r.allUpper || r.numTyp == NumberTypes.ROMAN) 
                hasSuf++;
        }
        if (hasSuf == 0) {
            if (res.size() < 5) 
                return null;
        }
        if (res.size() >= 2) {
            if (res.get(0) != lines.get(0)) {
                int tot = res.get(0).getBeginToken().beginChar - lines.get(0).getBeginToken().beginChar;
                tot += (lines.get(lines.size() - 1).getEndToken().endChar - res.get(res.size() - 1).getEndToken().endChar);
                int blk = res.get(res.size() - 1).getEndToken().endChar - res.get(0).getBeginToken().beginChar;
                int i = lines.indexOf(res.get(res.size() - 1));
                if (i > 0) {
                    java.util.ArrayList<InstrToken1> lines1 = new java.util.ArrayList(lines);
                    for(int indRemoveRange = 0 + i + 1 - 1, indMinIndex = 0; indRemoveRange >= indMinIndex; indRemoveRange--) lines1.remove(indRemoveRange);
                    java.util.ArrayList<InstrToken1> res1 = extractMainSequence(lines1, checkSpecTexts, canSubNumbers);
                    if (res1 != null && res1.size() > 2) 
                        blk += (res1.get(res1.size() - 1).endChar - res1.get(0).beginChar);
                }
                if ((blk * 3) < tot) {
                    if ((blk * 5) < tot) 
                        return null;
                    for(InstrToken1 r : res) {
                        if (!r.allUpper && !r.getHasChanges()) 
                            return null;
                    }
                }
            }
            if (res.get(0).getLastNumber() == 1 && res.get(0).numbers.size() == 1) {
                java.util.ArrayList<InstrToken1> res0 = new java.util.ArrayList< >();
                res0.add(res.get(0));
                int i;
                for(i = 1; i < res.size(); i++) {
                    int j;
                    for(j = i + 1; j < res.size(); j++) {
                        if (res.get(j).getLastNumber() == 1 && res.get(j).numbers.size() == 1) 
                            break;
                    }
                    if ((j - i) < 3) 
                        break;
                    j--;
                    int jj;
                    int errs = 0;
                    for(jj = i + 1; jj < j; jj++) {
                        int d = calcDelta(res.get(jj - 1), res.get(jj), false);
                        if (d == 1) {
                        }
                        else if (d > 1 && (d < 3)) 
                            errs++;
                        else 
                            break;
                    }
                    if ((jj < j) || errs > 1) 
                        break;
                    if (j < (res.size() - 1)) {
                        if (calcDelta(res0.get(res0.size() - 1), res.get(j), false) != 1) 
                            break;
                        res0.add(res.get(j));
                    }
                    i = j;
                }
                if (i >= res.size() && res0.size() > 1) 
                    return res0;
            }
            if (res.size() > 500) 
                return null;
            return res;
        }
        if (res.size() == 1 && lines.get(0) == res.get(0)) {
            if (hasSuf > 0) 
                return res;
            if (lines.size() > 1 && lines.get(1).numbers.size() == (lines.get(0).numbers.size() + 1)) {
                for(int i = 0; i < lines.get(0).numbers.size(); i++) {
                    if (com.pullenti.n2j.Utils.stringsNe(lines.get(1).numbers.get(i), lines.get(0).numbers.get(i))) 
                        return null;
                }
                return res;
            }
        }
        return null;
    }

    /**
     * Создать результирующий узел, представляющий номер
     * @param owner 
     * @param itok 
     */
    public static void createNumber(FragToken owner, InstrToken1 itok) {
        if (itok.numBeginToken == null || itok.numEndToken == null) 
            return;
        FragToken num = FragToken._new1406(itok.numBeginToken, itok.numEndToken, com.pullenti.ner.instrument.InstrumentKind.NUMBER, true, itok);
        owner.children.add(num);
        if (itok.numTyp == NumberTypes.TWODIGITS) {
            owner.number = itok.getFirstNumber();
            owner.subNumber = itok.getLastNumber();
        }
        else if (itok.numTyp == NumberTypes.THREEDIGITS) {
            owner.number = itok.getFirstNumber();
            owner.subNumber = itok.getMiddleNumber();
            owner.subNumber2 = itok.getLastNumber();
        }
        else if (itok.numTyp == NumberTypes.FOURDIGITS && itok.numbers.size() == 4) {
            owner.number = itok.getFirstNumber();
            owner.subNumber = com.pullenti.ner.decree.internal.PartToken.getNumber(itok.numbers.get(1));
            owner.subNumber2 = com.pullenti.ner.decree.internal.PartToken.getNumber(itok.numbers.get(2));
            owner.subNumber3 = itok.getLastNumber();
        }
        else 
            owner.number = itok.getLastNumber();
        owner.minNumber = itok.getLastMinNumber();
        owner.itok = itok;
    }

    /**
     * Распарсить нумерацию
     * @param t 
     * @param res 
     */
    public static void parseNumber(com.pullenti.ner.Token t, InstrToken1 res, InstrToken1 prev) {
        _parseNumber(t, res, prev);
        if ((res.numbers.size() > 0 && res.numEndToken != null && !res.isNewlineAfter()) && res.numEndToken.getNext() != null && res.numEndToken.getNext().isHiphen()) {
            InstrToken1 res1 = new InstrToken1(res.numEndToken.getNext().getNext(), res.numEndToken.getNext().getNext());
            _parseNumber(res1.getBeginToken(), res1, res);
            if (res1.numbers.size() == res.numbers.size()) {
                int i;
                for(i = 0; i < (res.numbers.size() - 1); i++) {
                    if (com.pullenti.n2j.Utils.stringsNe(res.numbers.get(i), res1.numbers.get(i))) 
                        break;
                }
                if (i >= (res.numbers.size() - 1) && (res.getLastNumber() < res1.getLastNumber()) && res1.numEndToken != null) {
                    res.minNumber = res.numbers.get(res.numbers.size() - 1);
                    com.pullenti.n2j.Utils.putArrayValue(res.numbers, res.numbers.size() - 1, res1.numbers.get(res.numbers.size() - 1));
                    res.numSuffix = res1.numSuffix;
                    res.setEndToken((res.numEndToken = res1.numEndToken));
                }
            }
        }
        if (res.numbers.size() > 0 && res.numEndToken != null && res.typ == InstrToken1.Types.LINE) {
            com.pullenti.ner.Token tt = res.numEndToken;
            boolean ok = true;
            if (tt.getNext() != null && tt.getNext().isHiphen()) 
                ok = false;
            else if (!tt.isWhitespaceAfter()) {
                if (tt.getNext() != null && ((tt.getNext().chars.isCapitalUpper() || tt.getNext().chars.isAllUpper() || (tt.getNext() instanceof com.pullenti.ner.ReferentToken)))) {
                }
                else 
                    ok = false;
            }
            if (!ok) {
                res.numbers.clear();
                res.numEndToken = (res.numBeginToken = null);
            }
        }
    }

    private static void _parseNumber(com.pullenti.ner.Token t, InstrToken1 res, InstrToken1 prev) {
        if ((t instanceof com.pullenti.ner.NumberToken) && (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).typ == com.pullenti.ner.NumberSpellingType.DIGIT && ((((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value < ((long)3000))) {
            if (res.numbers.size() >= 4) {
            }
            if (t.getMorph()._getClass().isAdjective() && res.getTypContainerRank() == 0) 
                return;
            if (com.pullenti.ner.core.NumberExToken.tryParseNumberWithPostfix(t) != null) 
                return;
            if ((t.getNext() != null && (t.getWhitespacesAfterCount() < 3) && t.getNext().chars.isLetter()) && t.getNext().chars.isAllLower()) {
                if (!t.isWhitespaceAfter() && t.getNext().getLengthChar() == 1) {
                }
                else 
                    return;
            }
            if (res.numTyp == NumberTypes.UNDEFINED) 
                res.numTyp = NumberTypes.DIGIT;
            else 
                res.numTyp = NumberTypes.COMBO;
            if (res.numbers.size() > 0 && t.isWhitespaceBefore()) 
                return;
            if (res.numbers.size() == 0) 
                res.numBeginToken = t;
            if ((t.getNext() != null && t.getNext().isHiphen() && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) && (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t.getNext().getNext(), com.pullenti.ner.NumberToken.class))).value > (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value) {
                res.minNumber = ((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value).toString();
                t = t.getNext().getNext();
            }
            else if (((t.getNext() != null && t.getNext().isCharOf(")") && t.getNext().getNext() != null) && t.getNext().getNext().isHiphen() && (t.getNext().getNext().getNext() instanceof com.pullenti.ner.NumberToken)) && (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t.getNext().getNext().getNext(), com.pullenti.ner.NumberToken.class))).value > (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value) {
                res.minNumber = ((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value).toString();
                t = t.getNext().getNext().getNext();
            }
            res.numbers.add(((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value).toString());
            res.setEndToken((res.numEndToken = t));
            res.numSuffix = null;
            for(com.pullenti.ner.Token ttt = t.getNext(); ttt != null && (res.numbers.size() < 4); ttt = ttt.getNext()) {
                boolean ok1 = false;
                if ((ttt.isCharOf("._") && !ttt.isWhitespaceAfter() && (ttt.getNext() instanceof com.pullenti.ner.NumberToken)) && (((((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(ttt.getNext(), com.pullenti.ner.NumberToken.class))).typ == com.pullenti.ner.NumberSpellingType.DIGIT || ((((((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(ttt.getNext(), com.pullenti.ner.NumberToken.class))).typ == com.pullenti.ner.NumberSpellingType.WORDS)) && ttt.getNext().chars.isLatinLetter() && !ttt.isWhitespaceAfter())))) 
                    ok1 = true;
                else if ((ttt.isCharOf("(<") && (ttt.getNext() instanceof com.pullenti.ner.NumberToken) && ttt.getNext().getNext() != null) && ttt.getNext().getNext().isCharOf(")>")) 
                    ok1 = true;
                if (ok1) {
                    ttt = ttt.getNext();
                    res.numbers.add(((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(ttt, com.pullenti.ner.NumberToken.class))).value).toString());
                    res.numTyp = (res.numbers.size() == 2 ? NumberTypes.TWODIGITS : ((res.numbers.size() == 3 ? NumberTypes.THREEDIGITS : NumberTypes.FOURDIGITS)));
                    if ((ttt.getNext() != null && ttt.getNext().isCharOf(")>") && ttt.getNext().getNext() != null) && ttt.getNext().getNext().isChar('.')) 
                        ttt = ttt.getNext();
                    t = res.setEndToken((res.numEndToken = ttt));
                    continue;
                }
                if (((ttt instanceof com.pullenti.ner.TextToken) && ttt.getLengthChar() == 1 && ttt.chars.isLetter()) && !ttt.isWhitespaceBefore() && res.numbers.size() == 1) {
                    res.numbers.add((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(ttt, com.pullenti.ner.TextToken.class))).term);
                    res.numTyp = NumberTypes.COMBO;
                    t = res.setEndToken((res.numEndToken = ttt));
                    continue;
                }
                break;
            }
            if (t.getNext() != null && t.getNext().isCharOf(").")) {
                res.numSuffix = t.getNext().getSourceText();
                t = res.setEndToken((res.numEndToken = t.getNext()));
            }
            return;
        }
        if (((t instanceof com.pullenti.ner.NumberToken) && (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).typ == com.pullenti.ner.NumberSpellingType.WORDS && res.getTypContainerRank() > 0) && res.numbers.size() == 0) {
            res.numbers.add(((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value).toString());
            res.numTyp = NumberTypes.DIGIT;
            res.numBeginToken = t;
            if (t.getNext() != null && t.getNext().isChar('.')) {
                t = t.getNext();
                res.numSuffix = ".";
            }
            res.setEndToken((res.numEndToken = t));
            return;
        }
        com.pullenti.ner.NumberToken nt = com.pullenti.ner.core.NumberHelper.tryParseRoman(t);
        if ((nt != null && nt.value == ((long)10) && t.getNext() != null) && t.getNext().isChar(')')) 
            nt = null;
        if (nt != null && nt.value == ((long)100)) 
            nt = null;
        if (nt != null && nt.typ == com.pullenti.ner.NumberSpellingType.ROMAN) {
            if (res.numTyp == NumberTypes.UNDEFINED) 
                res.numTyp = NumberTypes.ROMAN;
            else 
                res.numTyp = NumberTypes.COMBO;
            if (res.numbers.size() > 0 && t.isWhitespaceBefore()) 
                return;
            if (res.numbers.size() == 0) 
                res.numBeginToken = t;
            res.numbers.add(((Long)nt.value).toString());
            t = res.setEndToken((res.numEndToken = nt.getEndToken()));
            if (res.numTyp == NumberTypes.ROMAN && ((res.typ == InstrToken1.Types.CHAPTER || res.typ == InstrToken1.Types.SECTION || res.typ == InstrToken1.Types.LINE))) {
                if ((t.getNext() != null && t.getNext().isCharOf("._<") && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) && (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t.getNext().getNext(), com.pullenti.ner.NumberToken.class))).typ == com.pullenti.ner.NumberSpellingType.DIGIT) {
                    t = t.getNext().getNext();
                    res.numbers.add(((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value).toString());
                    res.numTyp = NumberTypes.TWODIGITS;
                    if (t.getNext() != null && t.getNext().isChar('>')) 
                        t = t.getNext();
                    res.setEndToken((res.numEndToken = t));
                    if ((t.getNext() != null && t.getNext().isCharOf("._<") && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) && (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t.getNext().getNext(), com.pullenti.ner.NumberToken.class))).typ == com.pullenti.ner.NumberSpellingType.DIGIT) {
                        t = t.getNext().getNext();
                        res.numbers.add(((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value).toString());
                        res.numTyp = NumberTypes.THREEDIGITS;
                        if (t.getNext() != null && t.getNext().isChar('>')) 
                            t = t.getNext();
                        res.setEndToken((res.numEndToken = t));
                    }
                }
            }
            if (t.getNext() != null && t.getNext().isCharOf(").")) {
                res.numSuffix = t.getNext().getSourceText();
                t = res.setEndToken((res.numEndToken = t.getNext()));
            }
            return;
        }
        if (((t instanceof com.pullenti.ner.TextToken) && t.getLengthChar() == 1 && t.chars.isLetter()) && t == res.getBeginToken()) {
            if ((!t.isWhitespaceAfter() && (t.getNext() instanceof com.pullenti.ner.NumberToken) && t.getNext().getNext() != null) && t.getNext().getNext().isChar('.')) {
                res.numBeginToken = t;
                res.numTyp = NumberTypes.DIGIT;
                res.numbers.add(((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class))).value).toString());
                res.numSuffix = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term + ".";
                t = res.setEndToken((res.numEndToken = t.getNext().getNext()));
                return;
            }
            if (t.getNext() != null && t.getNext().isCharOf(".)")) {
                if (((t.getNext().isChar('.') && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken) && t.getNext().getNext().getNext() != null) && t.getNext().getNext().getNext().isChar(')') && !t.getNext().isWhitespaceAfter()) && !t.getNext().getNext().isWhitespaceAfter()) {
                    res.numTyp = NumberTypes.TWODIGITS;
                    res.numbers.add((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term);
                    res.numbers.add(((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t.getNext().getNext(), com.pullenti.ner.NumberToken.class))).value).toString());
                    res.numSuffix = ")";
                    res.numBeginToken = t;
                    t = res.setEndToken((res.numEndToken = t.getNext().getNext().getNext()));
                    return;
                }
                if (t.getNext().isChar('.') && ((t.chars.isAllUpper() || (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)))) {
                }
                else {
                    InstrToken1 tmp1 = new InstrToken1(t, t.getNext());
                    tmp1.numbers.add((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term);
                    if (tmp1.getLastNumber() > 1 && t.getNext().isCharOf(".") && ((prev == null || (prev.getLastNumber() + 1) != tmp1.getLastNumber()))) {
                    }
                    else {
                        if (res.numbers.size() == 0) 
                            res.numBeginToken = t;
                        res.numTyp = NumberTypes.LETTER;
                        res.numbers.add((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term);
                        res.numBeginToken = t;
                        t = res.setEndToken((res.numEndToken = t.getNext()));
                        res.numSuffix = t.getSourceText();
                        return;
                    }
                }
            }
        }
    }

    public static boolean correctChildNumbers(FragToken root, java.util.ArrayList<FragToken> children) {
        boolean hasNum = false;
        if (root.number > 0) {
            for(FragToken ch : root.children) {
                if (ch.kind == com.pullenti.ner.instrument.InstrumentKind.NUMBER) {
                    hasNum = true;
                    break;
                }
                else if (ch.kind != com.pullenti.ner.instrument.InstrumentKind.KEYWORD) 
                    break;
            }
        }
        if (!hasNum) 
            return false;
        if (root.subNumber == 0) {
            boolean ok = true;
            for(FragToken ch : children) {
                if (ch.number > 0) {
                    if (ch.number == root.number && ch.subNumber > 0) {
                    }
                    else 
                        ok = false;
                }
            }
            if (ok) {
                for(FragToken ch : children) {
                    if (ch.number > 0) {
                        ch.number = ch.subNumber;
                        ch.subNumber = ch.subNumber2;
                        ch.subNumber2 = ch.subNumber3;
                        ch.subNumber3 = 0;
                    }
                }
            }
            return ok;
        }
        if (root.subNumber > 0 && root.subNumber2 == 0) {
            boolean ok = true;
            for(FragToken ch : children) {
                if (ch.number > 0) {
                    if (ch.number == root.number && ch.subNumber == root.subNumber && ch.subNumber2 > 0) {
                    }
                    else 
                        ok = false;
                }
            }
            if (ok) {
                for(FragToken ch : children) {
                    if (ch.number > 0) {
                        ch.number = ch.subNumber2;
                        ch.subNumber = ch.subNumber3;
                        ch.subNumber2 = (ch.subNumber3 = 0);
                    }
                }
            }
            return ok;
        }
        if (root.subNumber > 0 && root.subNumber2 > 0 && root.subNumber3 == 0) {
            boolean ok = true;
            for(FragToken ch : children) {
                if (ch.number > 0) {
                    if ((ch.number == root.number && ch.subNumber == root.subNumber && ch.subNumber2 == root.subNumber2) && ch.subNumber3 > 0) {
                    }
                    else 
                        ok = false;
                }
            }
            if (ok) {
                for(FragToken ch : children) {
                    if (ch.number > 0) {
                        ch.number = ch.subNumber3;
                        ch.subNumber = (ch.subNumber2 = (ch.subNumber3 = 0));
                    }
                }
            }
            return ok;
        }
        return false;
    }

    public static java.util.ArrayList<String> createDiap(String s1, String s2) {
        int n1;
        int n2;
        int i;
        String pref = null;
        if (s2.startsWith(s1)) {
            i = s1.length();
            if (((i + 1) < s2.length()) && s2.charAt(i) == '.' && Character.isDigit(s2.charAt(i + 1))) {
                com.pullenti.n2j.Outargwrapper<Integer> inoutarg1407 = new com.pullenti.n2j.Outargwrapper< >();
                boolean inoutres1408 = com.pullenti.n2j.Utils.parseInteger(s2.substring(i + 1), inoutarg1407);
                n2 = (inoutarg1407.value != null ? inoutarg1407.value : 0);
                if (inoutres1408) {
                    java.util.ArrayList<String> res0 = new java.util.ArrayList< >();
                    res0.add(s1);
                    for(i = 1; i <= n2; i++) {
                        res0.add(s1 + "." + i);
                    }
                    return res0;
                }
            }
        }
        if ((((i = s1.lastIndexOf('.')))) > 0) {
            pref = s1.substring(0, 0+(i + 1));
            com.pullenti.n2j.Outargwrapper<Integer> inoutarg1411 = new com.pullenti.n2j.Outargwrapper< >();
            boolean inoutres1412 = com.pullenti.n2j.Utils.parseInteger(s1.substring(i + 1), inoutarg1411);
            n1 = (inoutarg1411.value != null ? inoutarg1411.value : 0);
            if (!inoutres1412) 
                return null;
            if (!s2.startsWith(pref)) 
                return null;
            com.pullenti.n2j.Outargwrapper<Integer> inoutarg1409 = new com.pullenti.n2j.Outargwrapper< >();
            boolean inoutres1410 = com.pullenti.n2j.Utils.parseInteger(s2.substring(i + 1), inoutarg1409);
            n2 = (inoutarg1409.value != null ? inoutarg1409.value : 0);
            if (!inoutres1410) 
                return null;
        }
        else {
            com.pullenti.n2j.Outargwrapper<Integer> inoutarg1415 = new com.pullenti.n2j.Outargwrapper< >();
            boolean inoutres1416 = com.pullenti.n2j.Utils.parseInteger(s1, inoutarg1415);
            n1 = (inoutarg1415.value != null ? inoutarg1415.value : 0);
            if (!inoutres1416) 
                return null;
            com.pullenti.n2j.Outargwrapper<Integer> inoutarg1413 = new com.pullenti.n2j.Outargwrapper< >();
            boolean inoutres1414 = com.pullenti.n2j.Utils.parseInteger(s2, inoutarg1413);
            n2 = (inoutarg1413.value != null ? inoutarg1413.value : 0);
            if (!inoutres1414) 
                return null;
        }
        if (n2 <= n1) 
            return null;
        java.util.ArrayList<String> res = new java.util.ArrayList< >();
        for(i = n1; i <= n2; i++) {
            if (pref == null) 
                res.add(((Integer)i).toString());
            else 
                res.add(pref + ((((Integer)i).toString())));
        }
        return res;
    }
    public NumberingHelper() {
    }
}
