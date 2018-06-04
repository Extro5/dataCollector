/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner;

/**
 * Числовой токен (числительное)
 */
public class NumberToken extends MetaToken {

    public NumberToken(Token begin, Token end, long val, NumberSpellingType _typ, com.pullenti.ner.core.AnalysisKit _kit) {
        super(begin, end, _kit);
        value = val;
        typ = _typ;
    }

    /**
     * Числовое значение
     */
    public long value;

    /**
     * Тип написания
     */
    public NumberSpellingType typ = NumberSpellingType.DIGIT;

    @Override
    public boolean isNumber() {
        return true;
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(value).append(" ").append(typ.toString());
        if (getMorph() != null) 
            res.append(" ").append(getMorph().toString());
        return res.toString();
    }

    @Override
    public String getNormalCaseText(com.pullenti.morph.MorphClass mc, boolean singleNumber, com.pullenti.morph.MorphGender gender, boolean keepChars) {
        return ((Long)value).toString();
    }

    @Override
    public void serialize(com.pullenti.n2j.Stream stream) throws java.io.IOException {
        super.serialize(stream);
        stream.write(java.nio.ByteBuffer.allocate(8).order(java.nio.ByteOrder.LITTLE_ENDIAN).putLong(value).array(), 0, 8);
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, typ.value());
    }

    @Override
    public void deserialize(com.pullenti.n2j.Stream stream, com.pullenti.ner.core.AnalysisKit _kit) throws java.io.IOException {
        super.deserialize(stream, _kit);
        byte[] buf = new byte[8];
        stream.read(buf, 0, 8);
        value = java.nio.ByteBuffer.wrap(buf, 0, 8).order(java.nio.ByteOrder.LITTLE_ENDIAN).getLong();
        typ = NumberSpellingType.of(com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream));
    }

    public static NumberToken _new559(Token _arg1, Token _arg2, long _arg3, NumberSpellingType _arg4, MorphCollection _arg5) {
        NumberToken res = new NumberToken(_arg1, _arg2, _arg3, _arg4, null);
        res.setMorph(_arg5);
        return res;
    }
    public NumberToken() {
        super();
    }
}
