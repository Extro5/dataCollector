/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.resume.internal;

/**
 * Это для поддержки резюме
 */
public class ResumeToken extends com.pullenti.ner.MetaToken {

    public ResumeToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public ResumeTokenType typ = ResumeTokenType.UNDEFINED;

    public java.util.ArrayList<com.pullenti.ner.Referent> refs = new java.util.ArrayList< >();

    public static ResumeToken tryParse(com.pullenti.ner.Token t, ResumeToken prev) {
        if (t == null) 
            return null;
        return null;
    }
    public ResumeToken() {
        super();
    }
}
