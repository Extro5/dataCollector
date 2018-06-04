/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph.internal;

/**
 * Сделан специально для Питона - а то стандартым способом через MemoryStream 
 *  жутко тормозит, придётся делать самим
 */
public class ByteArrayWrapper {

    private byte[] m_Array;

    private int m_Pos;

    private int m_Len;

    public ByteArrayWrapper(byte[] arr) {
        m_Array = arr;
        m_Pos = 0;
        m_Len = m_Array.length;
    }

    public boolean isEOF() {
        return m_Pos >= m_Len;
    }


    public void back() {
        m_Pos--;
    }

    public void seek(int pos) {
        m_Pos = pos;
    }

    public int getPosition() {
        return m_Pos;
    }


    public byte deserializeByte() {
        if (m_Pos >= m_Len) 
            return (byte)0;
        return m_Array[m_Pos++];
    }

    public int deserializeShort() {
        if ((m_Pos + 1) >= m_Len) 
            return 0;
        byte b0 = m_Array[m_Pos++];
        byte b1 = m_Array[m_Pos++];
        int res = Byte.toUnsignedInt(b1);
        res <<= 8;
        return (res | (Byte.toUnsignedInt(b0)));
    }

    public int deserializeInt() {
        if ((m_Pos + 1) >= m_Len) 
            return 0;
        byte b0 = m_Array[m_Pos++];
        byte b1 = m_Array[m_Pos++];
        byte b2 = m_Array[m_Pos++];
        byte b3 = m_Array[m_Pos++];
        int res = Byte.toUnsignedInt(b3);
        res <<= 8;
        res |= (Byte.toUnsignedInt(b2));
        res <<= 8;
        res |= (Byte.toUnsignedInt(b1));
        res <<= 8;
        return (res | (Byte.toUnsignedInt(b0)));
    }

    public String deserializeString() {
        if (m_Pos >= m_Len) 
            return null;
        byte len = m_Array[m_Pos++];
        if (len == ((byte)0xFF)) 
            return null;
        if (len == ((byte)0)) 
            return "";
        if ((m_Pos + (Byte.toUnsignedInt(len))) > m_Len) 
            return null;
        String res = com.pullenti.n2j.Utils.decodeCharset(java.nio.charset.Charset.forName("UTF-8"), m_Array, m_Pos, len);
        m_Pos += (Byte.toUnsignedInt(len));
        return res;
    }
    public ByteArrayWrapper() {
    }
}
