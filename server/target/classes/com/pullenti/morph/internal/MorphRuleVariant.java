/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph.internal;

public class MorphRuleVariant extends com.pullenti.morph.MorphBaseInfo implements Comparable<MorphRuleVariant> {

    public MorphRuleVariant(MorphRuleVariant src) {
        super();
        if (src == null) 
            return;
        tail = src.tail;
        src.copyTo(this);
        miscInfo = src.miscInfo;
        normalTail = src.normalTail;
        fullNormalTail = src.fullNormalTail;
        rule = src.rule;
        tag = src.tag;
    }

    /**
     * Используется при построении обратного дерева
     */
    public short coef;

    public String tail;

    /**
     * Остаточная информация
     */
    public com.pullenti.morph.MorphMiscInfo miscInfo;

    public MorphRule rule;

    /**
     * Нормализованной окончание (мягкая нормализация - падеж и число)
     */
    public String normalTail;

    /**
     * Полная нормализация - для прилагательных ещё и к мужскому роду...
     */
    public String fullNormalTail;

    public Object tag;

    @Override
    public String toString() {
        return toStringEx(false);
    }

    public String toStringEx(boolean hideTails) {
        StringBuilder res = new StringBuilder();
        if (!hideTails) {
            res.append("-").append(tail);
            if (normalTail != null) 
                res.append(" [-").append(normalTail).append("]");
            if (fullNormalTail != null && com.pullenti.n2j.Utils.stringsNe(fullNormalTail, normalTail)) 
                res.append(" [-").append(fullNormalTail).append("]");
        }
        res.append(" ").append(super.toString()).append(" ").append((miscInfo == null ? "" : miscInfo.toString()));
        return res.toString().trim();
    }

    public boolean compare(MorphRuleVariant mrv) {
        if ((com.pullenti.morph.MorphClass.ooNoteq(mrv._getClass(), _getClass()) || mrv.getGender() != getGender() || mrv.getNumber() != getNumber()) || com.pullenti.morph.MorphCase.ooNoteq(mrv.getCase(), getCase())) 
            return false;
        if (mrv.miscInfo != miscInfo) 
            return false;
        if (com.pullenti.n2j.Utils.stringsNe(mrv.normalTail, normalTail)) 
            return false;
        return true;
    }

    public int calcEqCoef(com.pullenti.morph.MorphWordForm wf) {
        if (com.pullenti.morph.MorphClass.ooNoteq(_getClass(), wf._getClass())) 
            return -1;
        if (miscInfo != wf.misc) 
            return -1;
        if (!checkAccord(wf, false)) 
            return -1;
        return 1;
    }

    @Override
    public int compareTo(MorphRuleVariant other) {
        if (coef > other.coef) 
            return -1;
        if (coef < other.coef) 
            return 1;
        return 0;
    }

    public static MorphRuleVariant _new35(com.pullenti.morph.MorphMiscInfo _arg1) {
        MorphRuleVariant res = new MorphRuleVariant(null);
        res.miscInfo = _arg1;
        return res;
    }
    public MorphRuleVariant() {
        this(null);
    }
}
