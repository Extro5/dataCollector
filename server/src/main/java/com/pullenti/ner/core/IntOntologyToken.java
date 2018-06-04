/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.core;

/**
 * Это привязка элемента отнологии к тексту
 */
public class IntOntologyToken extends com.pullenti.ner.MetaToken {

    public IntOntologyToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    /**
     * Элемент словаря
     */
    public IntOntologyItem item;

    /**
     * Или просто отдельный термин
     */
    public Termin termin;

    public static IntOntologyToken _new485(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, IntOntologyItem _arg3, Termin _arg4, com.pullenti.ner.MorphCollection _arg5) {
        IntOntologyToken res = new IntOntologyToken(_arg1, _arg2);
        res.item = _arg3;
        res.termin = _arg4;
        res.setMorph(_arg5);
        return res;
    }
    public IntOntologyToken() {
        super();
    }
}
