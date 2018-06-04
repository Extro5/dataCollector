/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph.internal;

public class LazyInfo2 {

    public ByteArrayWrapper data;

    public DerivateDictionary dic;

    public int begin;

    public void loadNode(ExplanTreeNode tn) {
        synchronized (com.pullenti.morph.Explanatory.m_Lock) {
            data.seek(begin);
            ExplanSerializeHelper.deserializeTreeNode(data, dic, tn, true);
        }
    }

    public static LazyInfo2 _new5(int _arg1, ByteArrayWrapper _arg2, DerivateDictionary _arg3) {
        LazyInfo2 res = new LazyInfo2();
        res.begin = _arg1;
        res.data = _arg2;
        res.dic = _arg3;
        return res;
    }
    public LazyInfo2() {
    }
}
