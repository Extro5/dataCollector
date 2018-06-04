/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph.internal;

public class LazyInfo {

    public MorphEngine engine;

    public ByteArrayWrapper data;

    public int begin;

    public void loadNode(MorphTreeNode tn) {
        synchronized (engine.m_Lock) {
            data.seek(begin);
            MorphSerializeHelper.deserializeMorphTreeNodeLazy(data, tn, engine);
        }
    }
    public LazyInfo() {
    }
}
