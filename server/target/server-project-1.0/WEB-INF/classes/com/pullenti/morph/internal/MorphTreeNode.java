/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph.internal;

public class MorphTreeNode {

    /**
     * Движение дальше по дереву
     */
    public java.util.HashMap<Short, MorphTreeNode> nodes;

    /**
     * Конечные правила
     */
    public java.util.ArrayList<MorphRule> rules;

    public java.util.ArrayList<MorphRuleVariant> reverceVariants;

    public int calcTotalNodes() {
        int res = 0;
        if (nodes != null) {
            for(java.util.Map.Entry<Short, MorphTreeNode> v : nodes.entrySet()) {
                res += (v.getValue().calcTotalNodes() + 1);
            }
        }
        return res;
    }

    @Override
    public String toString() {
        return "?" + " (" + calcTotalNodes() + ", " + (rules == null ? 0 : rules.size()) + ")";
    }

    public LazyInfo lazy = null;

    public void load() {
        if (lazy == null) 
            return;
        lazy.loadNode(this);
        lazy = null;
    }
    public MorphTreeNode() {
    }
}
