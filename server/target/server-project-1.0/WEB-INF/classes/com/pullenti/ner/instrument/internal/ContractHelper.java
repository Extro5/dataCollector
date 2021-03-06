/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.instrument.internal;

public class ContractHelper {

    /**
     * Объединение абзацев в один фрагмент, если переход на новую строку 
     *  является сомнительным (для договоров обычно кривые документы)
     * @param fr 
     */
    public static void correctDummyNewlines(FragToken fr) {
        int i;
        for(i = 0; i < fr.children.size(); i++) {
            FragToken ch = fr.children.get(i);
            if ((ch.kind == com.pullenti.ner.instrument.InstrumentKind.KEYWORD || ch.kind == com.pullenti.ner.instrument.InstrumentKind.NUMBER || ch.kind == com.pullenti.ner.instrument.InstrumentKind.NAME) || ch.kind == com.pullenti.ner.instrument.InstrumentKind.EDITIONS || ch.kind == com.pullenti.ner.instrument.InstrumentKind.COMMENT) {
            }
            else 
                break;
        }
        if ((i < fr.children.size()) && fr.children.get(i).kind == com.pullenti.ner.instrument.InstrumentKind.INDENTION) {
            int j;
            for(j = i + 1; j < fr.children.size(); j++) {
                if (fr.children.get(j).kind != com.pullenti.ner.instrument.InstrumentKind.INDENTION) 
                    break;
                else if (_calcNewlineBetweenCoef(fr.children.get(j - 1), fr.children.get(j)) > 0) 
                    break;
            }
            if (j >= fr.children.size()) {
                j--;
                fr.children.get(i).kind = com.pullenti.ner.instrument.InstrumentKind.CONTENT;
                fr.children.get(i).number = 0;
                fr.children.get(i).setEndToken(fr.children.get(j).getEndToken());
                if ((i + 1) < fr.children.size()) 
                    for(int indRemoveRange = i + 1 + fr.children.size() - i - 1 - 1, indMinIndex = i + 1; indRemoveRange >= indMinIndex; indRemoveRange--) fr.children.remove(indRemoveRange);
                if (fr.kind == com.pullenti.ner.instrument.InstrumentKind.PREAMBLE && fr.children.size() == 1) 
                    fr.children.clear();
            }
            else {
                boolean ch = false;
                for(j = i + 1; j < fr.children.size(); j++) {
                    if (fr.children.get(j - 1).kind == com.pullenti.ner.instrument.InstrumentKind.INDENTION && fr.children.get(j).kind == com.pullenti.ner.instrument.InstrumentKind.INDENTION && (_calcNewlineBetweenCoef(fr.children.get(j - 1), fr.children.get(j)) < 0)) {
                        fr.children.get(j - 1).setEndToken(fr.children.get(j).getEndToken());
                        fr.children.remove(j);
                        j--;
                        ch = true;
                    }
                }
                if (ch) {
                    int num = 1;
                    for(j = i; j < fr.children.size(); j++) {
                        if (fr.children.get(j).kind == com.pullenti.ner.instrument.InstrumentKind.INDENTION) 
                            fr.children.get(j).number = num++;
                    }
                }
            }
        }
        for(FragToken ch : fr.children) {
            correctDummyNewlines(ch);
        }
    }

    private static int _calcNewlineBetweenCoef(FragToken fr1, FragToken fr2) {
        if (fr1.getNewlinesAfterCount() > 1) 
            return 1;
        for(com.pullenti.ner.Token tt = fr1.getBeginToken(); tt != null && tt.endChar <= fr1.endChar; tt = tt.getNext()) {
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt, false, false)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.CANBEMANYLINES, 100);
                if (br != null && br.endChar >= fr2.beginChar) 
                    return -1;
            }
        }
        com.pullenti.ner.Token t = fr1.getEndToken();
        if (t.isCharOf(":;.")) 
            return 1;
        if ((t instanceof com.pullenti.ner.TextToken) && ((t.getMorph()._getClass().isPreposition() || t.getMorph()._getClass().isConjunction()))) 
            return -1;
        com.pullenti.ner.Token t1 = fr2.getBeginToken();
        if (t1 instanceof com.pullenti.ner.TextToken) {
            if (t1.chars.isAllLower()) 
                return -1;
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t1, false, false)) {
                if (t.chars.isAllLower()) 
                    return -1;
            }
        }
        else if (t1 instanceof com.pullenti.ner.NumberToken) {
            if (t.chars.isAllLower()) 
                return -1;
        }
        if (t.chars.isAllLower()) {
            if (fr2.getEndToken().isChar(';')) 
                return -1;
        }
        return 0;
    }
    public ContractHelper() {
    }
}
