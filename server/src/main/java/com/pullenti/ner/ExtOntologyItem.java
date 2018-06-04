/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner;

/**
 * Элемент внешней онтологии
 */
public class ExtOntologyItem {

    public ExtOntologyItem(String caption) {
        m_Caption = caption;
    }

    /**
     * Внешний идентификатор (ссылка на что угодно)
     */
    public Object extId;

    /**
     * Имя типа
     */
    public String typeName;

    /**
     * Ссылка на сущность
     */
    public Referent referent;

    private String m_Caption;

    @Override
    public String toString() {
        if (m_Caption != null) 
            return m_Caption;
        else if (referent == null) 
            return ((String)com.pullenti.n2j.Utils.notnull(typeName, "?")) + ": ?";
        else {
            String res = referent.toString();
            if (referent.getParentReferent() != null) {
                String str1 = referent.getParentReferent().toString();
                if (!(res.indexOf(str1) >= 0)) 
                    res = res + "; " + str1;
            }
            return res;
        }
    }

    public static ExtOntologyItem _new2728(Object _arg1, Referent _arg2, String _arg3) {
        ExtOntologyItem res = new ExtOntologyItem(null);
        res.extId = _arg1;
        res.referent = _arg2;
        res.typeName = _arg3;
        return res;
    }
    public ExtOntologyItem() {
        this(null);
    }
}
