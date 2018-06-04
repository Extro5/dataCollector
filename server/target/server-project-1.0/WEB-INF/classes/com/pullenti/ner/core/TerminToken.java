/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.core;

/**
 * Результат привязки термина
 */
public class TerminToken extends com.pullenti.ner.MetaToken {

    public TerminToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    public Termin termin;

    public boolean abridgeWithoutPoint;

    public static TerminToken _new594(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3) {
        TerminToken res = new TerminToken(_arg1, _arg2);
        res.abridgeWithoutPoint = _arg3;
        return res;
    }
    public static TerminToken _new597(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Termin _arg3) {
        TerminToken res = new TerminToken(_arg1, _arg2);
        res.termin = _arg3;
        return res;
    }
    public static TerminToken _new602(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.MorphCollection _arg3) {
        TerminToken res = new TerminToken(_arg1, _arg2);
        res.setMorph(_arg3);
        return res;
    }
    public TerminToken() {
        super();
    }
}
