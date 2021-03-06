/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.old.internal;

public class Line extends com.pullenti.ner.MetaToken {

    private Line(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    public int getCharsCount() {
        int cou = 0;
        for(com.pullenti.ner.Token t = getBeginToken(); t != null; t = t.getNext()) {
            cou += t.getLengthChar();
            if (t == getEndToken()) 
                break;
        }
        return cou;
    }


    public boolean isPureEn() {
        int en = 0;
        int ru = 0;
        for(com.pullenti.ner.Token t = getBeginToken(); t != null && t.endChar <= endChar; t = t.getNext()) {
            if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter()) {
                if (t.chars.isCyrillicLetter()) 
                    ru++;
                else if (t.chars.isLatinLetter()) 
                    en++;
            }
        }
        if (en > 0 && ru == 0) 
            return true;
        return false;
    }


    public boolean isPureRu() {
        int en = 0;
        int ru = 0;
        for(com.pullenti.ner.Token t = getBeginToken(); t != null && t.endChar <= endChar; t = t.getNext()) {
            if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter()) {
                if (t.chars.isCyrillicLetter()) 
                    ru++;
                else if (t.chars.isLatinLetter()) 
                    en++;
            }
        }
        if (ru > 0 && en == 0) 
            return true;
        return false;
    }


    public static java.util.ArrayList<Line> parse(com.pullenti.ner.Token t0, int maxLines, int maxChars, int maxEndChar) {
        java.util.ArrayList<Line> res = new java.util.ArrayList< >();
        int totalChars = 0;
        for(com.pullenti.ner.Token t = t0; t != null; t = t.getNext()) {
            if (maxEndChar > 0) {
                if (t.beginChar > maxEndChar) 
                    break;
            }
            com.pullenti.ner.Token t1;
            for(t1 = t; t1 != null && t1.getNext() != null; t1 = t1.getNext()) {
                if (t1.isNewlineAfter()) {
                    if (t1.getNext() == null || com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t1.getNext())) 
                        break;
                }
                if (t1 == t && t.isNewlineBefore() && (t.getReferent() instanceof com.pullenti.ner.person.PersonReferent)) {
                    if (t1.getNext() == null) 
                        continue;
                    if ((t1.getNext() instanceof com.pullenti.ner.TextToken) && t1.getNext().chars.isLetter() && !t1.getNext().chars.isAllLower()) 
                        break;
                }
            }
            if (t1 == null) 
                t1 = t;
            com.pullenti.ner.titlepage.internal.TitleItemToken tit = com.pullenti.ner.titlepage.internal.TitleItemToken.tryAttach(t);
            if (tit != null) {
                if (tit.typ == com.pullenti.ner.titlepage.internal.TitleItemToken.Types.KEYWORDS) 
                    break;
            }
            com.pullenti.ner.core.internal.BlockTitleToken bl = com.pullenti.ner.core.internal.BlockTitleToken.tryAttach(t, false, null);
            if (bl != null) {
                if (bl.typ != com.pullenti.ner.core.internal.BlkTyps.UNDEFINED) 
                    break;
            }
            Line l = new Line(t, t1);
            res.add(l);
            totalChars += l.getCharsCount();
            if (res.size() >= maxLines || totalChars >= maxChars) 
                break;
            t = t1;
        }
        return res;
    }
    public Line() {
        super();
    }
}
