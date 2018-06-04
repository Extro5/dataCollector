/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner;

/**
 * Токен - надстройка над диапазоном других токенов
 */
public class MetaToken extends Token {

    public MetaToken(Token begin, Token end, com.pullenti.ner.core.AnalysisKit _kit) {
        super((com.pullenti.ner.core.AnalysisKit)com.pullenti.n2j.Utils.notnull(_kit, (begin != null ? begin.kit : null)), (begin == null ? 0 : begin.beginChar), (end == null ? 0 : end.endChar));
        if (begin == this || end == this) {
        }
        m_BeginToken = begin;
        m_EndToken = end;
        if (begin == null || end == null) 
            return;
        chars = begin.chars;
        if (begin != end) {
            for(Token t = begin.getNext(); t != null; t = t.getNext()) {
                if (t.chars.isLetter()) {
                    if (chars.isCapitalUpper() && t.chars.isAllLower()) {
                    }
                    else 
                        chars = com.pullenti.morph.CharsInfo.ooBitand(chars, t.chars);
                }
                if (t == end) 
                    break;
            }
        }
    }

    private void _RefreshCharsInfo() {
        if (m_BeginToken == null) 
            return;
        chars = m_BeginToken.chars;
        int cou = 0;
        if (m_BeginToken != m_EndToken && m_EndToken != null) {
            for(Token t = m_BeginToken.getNext(); t != null; t = t.getNext()) {
                if ((++cou) > 100) 
                    break;
                if (t.endChar > m_EndToken.endChar) 
                    break;
                if (t.chars.isLetter()) 
                    chars = com.pullenti.morph.CharsInfo.ooBitand(chars, t.chars);
                if (t == m_EndToken) 
                    break;
            }
        }
    }

    /**
     * Начальный токен диапазона
     */
    public Token getBeginToken() {
        return m_BeginToken;
    }

    /**
     * Начальный токен диапазона
     */
    public Token setBeginToken(Token value) {
        if (m_BeginToken != value) {
            if (m_BeginToken == this) {
            }
            else {
                m_BeginToken = value;
                if (value != null) 
                    beginChar = m_BeginToken.beginChar;
                _RefreshCharsInfo();
            }
        }
        return value;
    }


    public Token m_BeginToken;

    /**
     * Конечный токен диапазона
     */
    public Token getEndToken() {
        return m_EndToken;
    }

    /**
     * Конечный токен диапазона
     */
    public Token setEndToken(Token value) {
        if (m_EndToken != value) {
            if (m_EndToken == this) {
            }
            else {
                m_EndToken = value;
                if (value != null) 
                    endChar = m_EndToken.endChar;
                _RefreshCharsInfo();
            }
        }
        return value;
    }


    public Token m_EndToken;

    /**
     * Количество токенов в диапазоне
     */
    public int getTokensCount() {
        int count = 1;
        for(Token t = m_BeginToken; t != m_EndToken && t != null; t = t.getNext()) {
            if (count > 1 && t == m_BeginToken) 
                break;
            count++;
        }
        return count;
    }


    @Override
    public boolean isWhitespaceBefore() {
        return m_BeginToken.isWhitespaceBefore();
    }


    @Override
    public boolean isWhitespaceAfter() {
        return m_EndToken.isWhitespaceAfter();
    }


    @Override
    public boolean isNewlineBefore() {
        return m_BeginToken.isNewlineBefore();
    }


    @Override
    public boolean isNewlineAfter() {
        return m_EndToken.isNewlineAfter();
    }


    @Override
    public int getWhitespacesBeforeCount() {
        return m_BeginToken.getWhitespacesBeforeCount();
    }


    @Override
    public int getWhitespacesAfterCount() {
        return m_EndToken.getWhitespacesAfterCount();
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(Token t = m_BeginToken; t != null; t = t.getNext()) {
            if (res.length() > 0 && t.isWhitespaceBefore()) 
                res.append(' ');
            res.append(t.getSourceText());
            if (t == m_EndToken) 
                break;
        }
        return res.toString();
    }

    public static boolean check(java.util.ArrayList<ReferentToken> li) {
        if (li == null || (li.size() < 1)) 
            return false;
        int i;
        for(i = 0; i < (li.size() - 1); i++) {
            if (li.get(i).beginChar > li.get(i).endChar) 
                return false;
            if (li.get(i).endChar >= li.get(i + 1).beginChar) 
                return false;
        }
        if (li.get(i).beginChar > li.get(i).endChar) 
            return false;
        return true;
    }

    @Override
    public String getNormalCaseText(com.pullenti.morph.MorphClass mc, boolean singleNumber, com.pullenti.morph.MorphGender gender, boolean keepChars) {
        com.pullenti.ner.core.GetTextAttr attr = com.pullenti.ner.core.GetTextAttr.NO;
        if (singleNumber) 
            attr = com.pullenti.ner.core.GetTextAttr.of((attr.value()) | (com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE.value()));
        else 
            attr = com.pullenti.ner.core.GetTextAttr.of((attr.value()) | (com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE.value()));
        if (keepChars) 
            attr = com.pullenti.ner.core.GetTextAttr.of((attr.value()) | (com.pullenti.ner.core.GetTextAttr.KEEPREGISTER.value()));
        if (getBeginToken() == getEndToken()) 
            return getBeginToken().getNormalCaseText(mc, singleNumber, gender, keepChars);
        else 
            return com.pullenti.ner.core.MiscHelper.getTextValue(getBeginToken(), getEndToken(), attr);
    }

    public static MetaToken _new564(Token _arg1, Token _arg2, MorphCollection _arg3) {
        MetaToken res = new MetaToken(_arg1, _arg2, null);
        res.setMorph(_arg3);
        return res;
    }
    public static MetaToken _new799(Token _arg1, Token _arg2, Object _arg3) {
        MetaToken res = new MetaToken(_arg1, _arg2, null);
        res.tag = _arg3;
        return res;
    }
    public static MetaToken _new2144(Token _arg1, Token _arg2, Object _arg3, MorphCollection _arg4) {
        MetaToken res = new MetaToken(_arg1, _arg2, null);
        res.tag = _arg3;
        res.setMorph(_arg4);
        return res;
    }
    public MetaToken() {
        super();
    }
}
