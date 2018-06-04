/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph.internal;

public class ExplanTreeNode {

    /**
     * Движение дальше по дереву
     */
    public java.util.HashMap<Short, ExplanTreeNode> nodes;

    /**
     * Конечные правила
     */
    public Object groups;

    public LazyInfo2 lazy = null;

    public void load() {
        if (lazy == null) 
            return;
        lazy.loadNode(this);
        lazy = null;
    }

    public void addGroup(com.pullenti.morph.DerivateGroup gr) {
        if (groups == null) {
            groups = gr;
            return;
        }
        java.util.ArrayList<com.pullenti.morph.DerivateGroup> li = (java.util.ArrayList<com.pullenti.morph.DerivateGroup>)com.pullenti.n2j.Utils.cast(groups, java.util.ArrayList.class);
        if (li == null) {
            li = new java.util.ArrayList< >();
            if (groups instanceof com.pullenti.morph.DerivateGroup) 
                li.add((com.pullenti.morph.DerivateGroup)com.pullenti.n2j.Utils.cast(groups, com.pullenti.morph.DerivateGroup.class));
        }
        if (!li.contains(gr)) 
            li.add(gr);
        groups = li;
    }
    public ExplanTreeNode() {
    }
}
