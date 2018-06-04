/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph;

/**
 * Слово толкового словаря
 */
public class DerivateWord {

    public DerivateWord(DerivateGroup gr) {
        group = gr;
    }

    /**
     * Деривативаная группа, содержащая данное слово
     */
    public DerivateGroup group;

    /**
     * Само слово в нормальной форме
     */
    public String spelling;

    /**
     * Часть речи
     */
    public MorphClass _class;

    /**
     * Совершенный\несовершенный
     */
    public MorphAspect aspect = MorphAspect.UNDEFINED;

    /**
     * Действительный\страдательный
     */
    public MorphVoice voice = MorphVoice.UNDEFINED;

    /**
     * Время (для причастий)
     */
    public MorphTense tense = MorphTense.UNDEFINED;

    /**
     * Возвратный
     */
    public boolean reflexive;

    /**
     * Язык
     */
    public MorphLang lang;

    /**
     * Дополнительные характеристики
     */
    public ExplanWordAttr attrs = new ExplanWordAttr(null);

    /**
     * Возможные продолжения (следующее слово): 
     *  Пары: (Предлог, Падежи)
     */
    public java.util.HashMap<String, MorphCase> nexts;

    public Object tag;

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append(spelling);
        if (!_class.isUndefined()) 
            tmp.append(", ").append(_class.toString());
        if (aspect != MorphAspect.UNDEFINED) 
            tmp.append(", ").append((aspect == MorphAspect.PERFECTIVE ? "соверш." : "несоверш."));
        if (voice != MorphVoice.UNDEFINED) 
            tmp.append(", ").append((voice == MorphVoice.ACTIVE ? "действ." : (voice == MorphVoice.PASSIVE ? "страдат." : "средн.")));
        if (tense != MorphTense.UNDEFINED) 
            tmp.append(", ").append((tense == MorphTense.PAST ? "прош." : (tense == MorphTense.PRESENT ? "настоящ." : "будущ.")));
        if (reflexive) 
            tmp.append(", возвр.");
        if (attrs.value != ((short)0)) 
            tmp.append(", ").append(attrs.toString());
        if (nexts != null) {
            for(java.util.Map.Entry<String, MorphCase> v : nexts.entrySet()) {
                tmp.append(" -").append(v.getKey()).append("[").append(v.getValue()).append("];");
            }
        }
        return tmp.toString();
    }

    public static DerivateWord _new41(DerivateGroup _arg1, String _arg2, MorphLang _arg3, MorphClass _arg4, MorphAspect _arg5, boolean _arg6, MorphTense _arg7, MorphVoice _arg8, ExplanWordAttr _arg9) {
        DerivateWord res = new DerivateWord(_arg1);
        res.spelling = _arg2;
        res.lang = _arg3;
        res._class = _arg4;
        res.aspect = _arg5;
        res.reflexive = _arg6;
        res.tense = _arg7;
        res.voice = _arg8;
        res.attrs = _arg9;
        return res;
    }
    public DerivateWord() {
    }
}
