/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.core;

/**
 * Элемент онтологического словаря
 */
public class IntOntologyItem {

    public IntOntologyItem(com.pullenti.ner.Referent r) {
        referent = r;
    }

    /**
     * Список терминов, ассоциированных со словарной записью
     */
    public java.util.ArrayList<Termin> termins = new java.util.ArrayList< >();

    /**
     * Каноноический текст
     */
    public String getCanonicText() {
        if (m_CanonicText == null && termins.size() > 0) 
            m_CanonicText = termins.get(0).getCanonicText();
        return (String)com.pullenti.n2j.Utils.notnull(m_CanonicText, "?");
    }

    /**
     * Каноноический текст
     */
    public String setCanonicText(String value) {
        m_CanonicText = value;
        return value;
    }


    private String m_CanonicText;

    /**
     * В качестве канонического текста установить самый короткий среди терминов
     * @param ignoreTerminsWithNotnullTags 
     */
    public void setShortestCanonicalText(boolean ignoreTerminsWithNotnullTags) {
        m_CanonicText = null;
        for(Termin t : termins) {
            if (ignoreTerminsWithNotnullTags && t.tag != null) 
                continue;
            if (t.terms.size() == 0) 
                continue;
            String s = t.getCanonicText();
            if (!com.pullenti.morph.LanguageHelper.isCyrillicChar(s.charAt(0))) 
                continue;
            if (m_CanonicText == null) 
                m_CanonicText = s;
            else if (s.length() < m_CanonicText.length()) 
                m_CanonicText = s;
        }
    }

    /**
     * Необязательный тип элемента
     */
    public String typ;

    /**
     * Используется произвольным образом (для некоторого дополнительного признака)
     */
    public Object miscAttr;

    /**
     * Ссылка на онтологию
     */
    public IntOntologyCollection owner;

    /**
     * Ссылка на элемент внешней онтологии
     */
    public Object ontologyElement;

    /**
     * Ссылка на сущность
     */
    public com.pullenti.ner.Referent referent;

    /**
     * Используется произвольным образом
     */
    public Object tag;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (typ != null) 
            res.append(typ).append(": ");
        res.append(getCanonicText());
        for(Termin t : termins) {
            String tt = t.toString();
            if (com.pullenti.n2j.Utils.stringsEq(tt, getCanonicText())) 
                continue;
            res.append("; ");
            res.append(tt);
        }
        if (referent != null) 
            res.append(" [").append(referent).append("]");
        return res.toString();
    }
    public IntOntologyItem() {
    }
}
