/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.core;

/**
 * Представление последовательности, обрамлённой кавычками (скобками)
 */
public class BracketSequenceToken extends com.pullenti.ner.MetaToken {

    public BracketSequenceToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    /**
     * Внутренние подпоследовательности. 
     *  Например, "О внесении изменений (2010-2011)", содержит внутри (2010-2011)
     */
    public java.util.ArrayList<BracketSequenceToken> internal = new java.util.ArrayList< >();

    /**
     * Признак обрамления кавычками (если false, то м.б. [...], (...), {...})
     */
    public boolean isQuoteType() {
        return "{([".indexOf(getOpenChar()) < 0;
    }


    /**
     * Открывающий символ
     */
    public char getOpenChar() {
        return getBeginToken().kit.getTextCharacter(getBeginToken().beginChar);
    }


    /**
     * Закрывающий символ
     */
    public char getCloseChar() {
        return getEndToken().kit.getTextCharacter(getEndToken().beginChar);
    }


    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getNormalCaseText(com.pullenti.morph.MorphClass mc, boolean singleNumber, com.pullenti.morph.MorphGender gender, boolean keepChars) {
        GetTextAttr attr = GetTextAttr.NO;
        if (singleNumber) 
            attr = GetTextAttr.of((attr.value()) | (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE.value()));
        else 
            attr = GetTextAttr.of((attr.value()) | (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE.value()));
        if (keepChars) 
            attr = GetTextAttr.of((attr.value()) | (GetTextAttr.KEEPREGISTER.value()));
        return MiscHelper.getTextValue(getBeginToken(), getEndToken(), attr);
    }
    public BracketSequenceToken() {
        super();
    }
}
