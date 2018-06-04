/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner;

/**
 * Базовый элемент
 */
public class Token {

    public Token(com.pullenti.ner.core.AnalysisKit _kit, int begin, int end) {
        kit = _kit;
        beginChar = begin;
        endChar = end;
    }

    /**
     * Аналитический контейнер
     */
    public com.pullenti.ner.core.AnalysisKit kit;

    /**
     * Начальная позиция
     */
    public int beginChar;

    /**
     * Конечная позиция
     */
    public int endChar;

    /**
     * Длина в исходных символах
     */
    public int getLengthChar() {
        return (endChar - beginChar) + 1;
    }


    /**
     * Используется произвольным образом
     */
    public Object tag;

    /**
     * Предыдущий токен
     */
    public Token getPrevious() {
        return m_Previous;
    }

    /**
     * Предыдущий токен
     */
    public Token setPrevious(Token value) {
        m_Previous = value;
        if (value != null) 
            value.m_Next = this;
        m_Attrs = (short)0;
        return value;
    }


    public Token m_Previous;

    /**
     * Следующий токен
     */
    public Token getNext() {
        return m_Next;
    }

    /**
     * Следующий токен
     */
    public Token setNext(Token value) {
        m_Next = value;
        if (value != null) 
            value.m_Previous = this;
        m_Attrs = (short)0;
        return value;
    }


    public Token m_Next;

    /**
     * Морфологическая информация
     */
    public MorphCollection getMorph() {
        if (m_Morph == null) 
            m_Morph = new MorphCollection(null);
        return m_Morph;
    }

    /**
     * Морфологическая информация
     */
    public MorphCollection setMorph(MorphCollection value) {
        m_Morph = value;
        return value;
    }


    private MorphCollection m_Morph;

    /**
     * Информация о символах
     */
    public com.pullenti.morph.CharsInfo chars;

    @Override
    public String toString() {
        return kit.getSofa().getText().substring(beginChar, (beginChar)+((endChar + 1) - beginChar));
    }

    private short m_Attrs;

    private boolean getAttr(int i) {
        char ch;
        if (((((int)m_Attrs) & 1)) == 0) {
            m_Attrs = (short)1;
            if (m_Previous == null) {
                setAttr(1, true);
                setAttr(3, true);
            }
            else 
                for(int j = m_Previous.endChar + 1; j < beginChar; j++) {
                    if (com.pullenti.n2j.Utils.isWhitespace(((ch = kit.getSofa().getText().charAt(j))))) {
                        setAttr(1, true);
                        if (((int)ch) == 0xD || ((int)ch) == 0xA || ch == '\f') 
                            setAttr(3, true);
                    }
                }
            if (m_Next == null) {
                setAttr(2, true);
                setAttr(4, true);
            }
            else 
                for(int j = endChar + 1; j < m_Next.beginChar; j++) {
                    if (com.pullenti.n2j.Utils.isWhitespace((ch = kit.getSofa().getText().charAt(j)))) {
                        setAttr(2, true);
                        if (((int)ch) == 0xD || ((int)ch) == 0xA || ch == '\f') 
                            setAttr(4, true);
                    }
                }
        }
        return ((((((int)m_Attrs) >> i)) & 1)) != 0;
    }

    protected void setAttr(int i, boolean val) {
        if (val) 
            m_Attrs |= ((short)((1 << i)));
        else 
            m_Attrs &= ((short)(~((1 << i))));
    }

    /**
     * Наличие пробельных символов перед
     */
    public boolean isWhitespaceBefore() {
        return getAttr(1);
    }

    /**
     * Наличие пробельных символов перед
     */
    public boolean setWhitespaceBefore(boolean value) {
        setAttr(1, value);
        return value;
    }


    /**
     * Наличие пробельных символов после
     */
    public boolean isWhitespaceAfter() {
        return getAttr(2);
    }

    /**
     * Наличие пробельных символов после
     */
    public boolean setWhitespaceAfter(boolean value) {
        setAttr(2, value);
        return value;
    }


    /**
     * Элемент начинается с новой строки. 
     *  Для 1-го элемента всегда true.
     */
    public boolean isNewlineBefore() {
        return getAttr(3);
    }

    /**
     * Элемент начинается с новой строки. 
     *  Для 1-го элемента всегда true.
     */
    public boolean setNewlineBefore(boolean value) {
        setAttr(3, value);
        return value;
    }


    /**
     * Элемент заканчивает строку. 
     *  Для последнего элемента всегда true.
     */
    public boolean isNewlineAfter() {
        return getAttr(4);
    }

    /**
     * Элемент заканчивает строку. 
     *  Для последнего элемента всегда true.
     */
    public boolean setNewlineAfter(boolean value) {
        setAttr(4, value);
        return value;
    }


    /**
     * Это используется внутренним образом
     */
    public boolean getInnerBool() {
        return getAttr(5);
    }

    /**
     * Это используется внутренним образом
     */
    public boolean setInnerBool(boolean value) {
        setAttr(5, value);
        return value;
    }


    /**
     * Это используется внутренним образом  
     *  (признак того, что здесь не начинается именная группа, чтобы повторно не пытаться выделять)
     */
    public boolean getNotNounPhrase() {
        return getAttr(6);
    }

    /**
     * Это используется внутренним образом  
     *  (признак того, что здесь не начинается именная группа, чтобы повторно не пытаться выделять)
     */
    public boolean setNotNounPhrase(boolean value) {
        setAttr(6, value);
        return value;
    }


    /**
     * Количество пробелов перед, переход на новую строку = 10, табуляция = 5
     */
    public int getWhitespacesBeforeCount() {
        if (getPrevious() == null) 
            return 100;
        if ((getPrevious().endChar + 1) == beginChar) 
            return 0;
        return calcWhitespaces(getPrevious().endChar + 1, beginChar - 1);
    }


    /**
     * Количество переходов на новую строку перед
     */
    public int getNewlinesBeforeCount() {
        char ch0 = (char)0;
        int res = 0;
        String txt = kit.getSofa().getText();
        for(int p = beginChar - 1; p >= 0; p--) {
            char ch = txt.charAt(p);
            if (((int)ch) == 0xA) 
                res++;
            else if (((int)ch) == 0xD && ((int)ch0) != 0xA) 
                res++;
            else if (ch == '\f') 
                res += 10;
            else if (!com.pullenti.n2j.Utils.isWhitespace(ch)) 
                break;
            ch0 = ch;
        }
        return res;
    }


    /**
     * Количество переходов на новую строку перед
     */
    public int getNewlinesAfterCount() {
        char ch0 = (char)0;
        int res = 0;
        String txt = kit.getSofa().getText();
        for(int p = endChar + 1; p < txt.length(); p++) {
            char ch = txt.charAt(p);
            if (((int)ch) == 0xD) 
                res++;
            else if (((int)ch) == 0xA && ((int)ch0) != 0xD) 
                res++;
            else if (ch == '\f') 
                res += 10;
            else if (!com.pullenti.n2j.Utils.isWhitespace(ch)) 
                break;
            ch0 = ch;
        }
        return res;
    }


    /**
     * Количество пробелов перед, переход на новую строку = 10, табуляция = 5
     */
    public int getWhitespacesAfterCount() {
        if (getNext() == null) 
            return 100;
        if ((endChar + 1) == getNext().beginChar) 
            return 0;
        return calcWhitespaces(endChar + 1, getNext().beginChar - 1);
    }


    private int calcWhitespaces(int p0, int p1) {
        if ((p0 < 0) || p0 > p1 || p1 >= kit.getSofa().getText().length()) 
            return -1;
        int res = 0;
        for(int i = p0; i <= p1; i++) {
            char ch = kit.getTextCharacter(i);
            if (ch == '\r' || ch == '\n') {
                res += 10;
                char ch1 = kit.getTextCharacter(i + 1);
                if (ch != ch1 && ((ch1 == '\r' || ch1 == '\n'))) 
                    i++;
            }
            else if (ch == '\t') 
                res += 5;
            else if (ch == '\u0007') 
                res += 100;
            else if (ch == '\f') 
                res += 100;
            else 
                res++;
        }
        return res;
    }

    /**
     * Это символ переноса
     */
    public boolean isHiphen() {
        char ch = kit.getSofa().getText().charAt(beginChar);
        return com.pullenti.morph.LanguageHelper.isHiphen(ch);
    }


    /**
     * Это спец-символы для табличных элементов (7h, 1Eh, 1Fh)
     */
    public boolean isTableControlChar() {
        char ch = kit.getSofa().getText().charAt(beginChar);
        return ((int)ch) == 7 || ((int)ch) == 0x1F || ((int)ch) == 0x1E;
    }


    /**
     * Это соединительный союз И (на всех языках)
     */
    public boolean isAnd() {
        if (!getMorph()._getClass().isConjunction()) {
            if (getLengthChar() == 1 && isChar('&')) 
                return true;
            return false;
        }
        TextToken tt = (TextToken)com.pullenti.n2j.Utils.cast(this, TextToken.class);
        if (tt == null) 
            return false;
        String val = tt.term;
        if (com.pullenti.n2j.Utils.stringsEq(val, "И") || com.pullenti.n2j.Utils.stringsEq(val, "AND") || com.pullenti.n2j.Utils.stringsEq(val, "UND")) 
            return true;
        if (tt.getMorph().getLanguage().isUa()) {
            if (com.pullenti.n2j.Utils.stringsEq(val, "І") || com.pullenti.n2j.Utils.stringsEq(val, "ТА")) 
                return true;
        }
        return false;
    }


    /**
     * Это соединительный союз ИЛИ (на всех языках)
     */
    public boolean isOr() {
        if (!getMorph()._getClass().isConjunction()) 
            return false;
        TextToken tt = (TextToken)com.pullenti.n2j.Utils.cast(this, TextToken.class);
        if (tt == null) 
            return false;
        String val = tt.term;
        if (com.pullenti.n2j.Utils.stringsEq(val, "ИЛИ") || com.pullenti.n2j.Utils.stringsEq(val, "OR")) 
            return true;
        if (tt.getMorph().getLanguage().isUa()) {
            if (com.pullenti.n2j.Utils.stringsEq(val, "АБО")) 
                return true;
        }
        return false;
    }


    /**
     * Это запятая
     */
    public boolean isComma() {
        return isChar(',');
    }


    /**
     * Это запятая или союз И
     */
    public boolean isCommaAnd() {
        return isComma() || isAnd();
    }


    /**
     * Токен состоит из символа
     * @param ch проверяемый символ
     * @return 
     */
    public boolean isChar(char ch) {
        return kit.getSofa().getText().charAt(beginChar) == ch;
    }

    /**
     * Токен состоит из одного символа, который есть в указанной строке
     * @param _chars строка возможных символов
     * @return 
     */
    public boolean isCharOf(String _chars) {
        if (this instanceof ReferentToken) 
            return false;
        return _chars.indexOf(kit.getSofa().getText().charAt(beginChar)) >= 0;
    }

    public boolean isValue(String term, String termUA) {
        if (this instanceof MetaToken) 
            return (((MetaToken)com.pullenti.n2j.Utils.cast(this, MetaToken.class))).getBeginToken().isValue(term, termUA);
        return false;
    }

    /**
     * Признак того, что это буквенный текстовой токен (TextToken)
     */
    public boolean isLetters() {
        TextToken tt = (TextToken)com.pullenti.n2j.Utils.cast(this, TextToken.class);
        if (tt == null) 
            return false;
        return Character.isLetter(tt.term.charAt(0));
    }


    /**
     * Это число (в различных вариантах задания)
     */
    public boolean isNumber() {
        return false;
    }


    /**
     * Это сущность (Referent)
     */
    public boolean isReferent() {
        return false;
    }


    /**
     * Ссылка на сущность (для ReferentToken)
     */
    public Referent getReferent() {
        if (!((this instanceof ReferentToken))) 
            return null;
        return (((ReferentToken)com.pullenti.n2j.Utils.cast(this, ReferentToken.class))).referent;
    }

    /**
     * Получить список ссылок на все сущности, скрывающиеся под элементом 
     *  (дело в том, что одни сущности могут поглощать дркгие, например, адрес поглотит город)
     * @return 
     */
    public java.util.ArrayList<Referent> getReferents() {
        MetaToken rt = (MetaToken)com.pullenti.n2j.Utils.cast(this, MetaToken.class);
        if (rt == null) 
            return null;
        java.util.ArrayList<Referent> res = new java.util.ArrayList< >();
        if ((rt instanceof ReferentToken) && (((ReferentToken)com.pullenti.n2j.Utils.cast(rt, ReferentToken.class))).referent != null) 
            res.add((((ReferentToken)com.pullenti.n2j.Utils.cast(rt, ReferentToken.class))).referent);
        for(Token t = rt.getBeginToken(); t != null && t.endChar <= endChar; t = t.getNext()) {
            java.util.ArrayList<Referent> li = t.getReferents();
            if (li == null) 
                continue;
            for(Referent r : li) {
                if (!res.contains(r)) 
                    res.add(r);
            }
        }
        return res;
    }

    /**
     * Получить связанный с токеном текст в именительном падеже
     * @param mc 
     * @param singleNumber переводить ли в единственное число
     * @return 
     */
    public String getNormalCaseText(com.pullenti.morph.MorphClass mc, boolean singleNumber, com.pullenti.morph.MorphGender gender, boolean keepChars) {
        return toString();
    }

    /**
     * Получить чистый фрагмент исходного текста
     * @return 
     */
    public String getSourceText() {
        int len = (endChar + 1) - beginChar;
        if ((len < 1) || (beginChar < 0)) 
            return null;
        if ((beginChar + len) > kit.getSofa().getText().length()) 
            return null;
        return kit.getSofa().getText().substring(beginChar, (beginChar)+(len));
    }

    /**
     * Проверка, что это текстовый токен и есть в словаре соотв. тип
     * @param cla 
     * @return 
     */
    public com.pullenti.morph.MorphClass getMorphClassInDictionary() {
        TextToken tt = (TextToken)com.pullenti.n2j.Utils.cast(this, TextToken.class);
        if (tt == null) 
            return getMorph()._getClass();
        com.pullenti.morph.MorphClass res = new com.pullenti.morph.MorphClass(null);
        for(com.pullenti.morph.MorphBaseInfo wf : tt.getMorph().getItems()) {
            if ((wf instanceof com.pullenti.morph.MorphWordForm) && (((com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class))).isInDictionary()) 
                res = com.pullenti.morph.MorphClass.ooBitor(res, wf._getClass());
        }
        return res;
    }

    public void serialize(com.pullenti.n2j.Stream stream) throws java.io.IOException {
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, beginChar);
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, endChar);
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, (int)m_Attrs);
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, (int)chars.value);
        m_Morph.serialize(stream);
    }

    public void deserialize(com.pullenti.n2j.Stream stream, com.pullenti.ner.core.AnalysisKit _kit) throws java.io.IOException {
        kit = _kit;
        beginChar = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        endChar = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        m_Attrs = (short)com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        chars = com.pullenti.morph.CharsInfo._new2785((short)com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream));
        m_Morph = new MorphCollection(null);
        m_Morph.deserialize(stream);
    }
    public Token() {
    }
}
