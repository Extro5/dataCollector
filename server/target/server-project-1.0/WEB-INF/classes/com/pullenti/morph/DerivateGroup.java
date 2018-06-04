/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph;

/**
 * Дериватная группа
 */
public class DerivateGroup implements Comparable<DerivateGroup> {

    public DerivateGroup root;

    public String prefix;

    public java.util.ArrayList<DerivateWord> words = new java.util.ArrayList< >();

    public boolean ok;

    public boolean isDummy;

    /**
     * Не образовывать группы путём перебора приставок
     */
    public boolean notGenerate;

    /**
     * Группа сгенерирована на основе перебора приставок (runtime)
     */
    public boolean isGenerated;

    /**
     * Признак транзитивности глаголов
     */
    public int getTransitive() {
        if (m_Transitive >= 0) 
            return m_Transitive;
        if (root != null) 
            return root.getTransitive();
        return -1;
    }


    public int m_Transitive = -1;

    public boolean deleted;

    public String id;

    public java.time.LocalDateTime modified;

    /**
     * Это для локальных изменений при слиянии
     */
    public boolean changed;

    public com.pullenti.morph.internal.LazyInfo2 lazy = null;

    public Object tag;

    @Override
    public String toString() {
        String res = "?";
        if (prefix != null && root != null) 
            res = "[" + prefix + "] + <" + (root.words.size() > 0 ? root.words.get(0).spelling : "?") + ">";
        else if (words.size() > 0) 
            res = "<" + words.get(0).spelling + ">";
        if (isDummy) 
            res = "DUMMY: " + res;
        else if (isGenerated) 
            res = "GEN: " + res;
        return res;
    }

    @Override
    public int compareTo(DerivateGroup other) {
        if (words.size() == 0) 
            return (other.words.size() > 0 ? -1 : 0);
        if (other.words.size() == 0) 
            return 1;
        return com.pullenti.n2j.Utils.stringsCompare(words.get(0).spelling, other.words.get(0).spelling, false);
    }

    public DerivateGroup createByPrefix(String pref, MorphLang lang) {
        DerivateGroup res = _new40(true, this, pref);
        for(DerivateWord w : words) {
            if (!lang.isUndefined() && ((MorphLang.ooBitand(w.lang, lang))).isUndefined()) 
                continue;
            DerivateWord rw = DerivateWord._new41(res, pref + w.spelling, w.lang, w._class, w.aspect, w.reflexive, w.tense, w.voice, w.attrs);
            res.words.add(rw);
        }
        return res;
    }

    public static DerivateGroup _new40(boolean _arg1, DerivateGroup _arg2, String _arg3) {
        DerivateGroup res = new DerivateGroup();
        res.isGenerated = _arg1;
        res.root = _arg2;
        res.prefix = _arg3;
        return res;
    }
    public DerivateGroup() {
    }
}
