/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.core;

/**
 * Выделение именных групп (существительсно с согласованными прилагательными (если они есть).
 */
public class NounPhraseHelper {

    /**
     * Попробовать создать именную группу с указанного токена
     * @param t начальный токен
     * @param typ параметры (можно битовую маску)
     * @param maxCharPos максимальная позиция в тексте, до которой выделять, если 0, то без ограничений
     * @return именная группа или null
     */
    public static NounPhraseToken tryParse(com.pullenti.ner.Token t, NounPhraseParseAttr typ, int maxCharPos) {
        NounPhraseToken res = _NounPraseHelperInt.tryParse(t, typ, maxCharPos);
        if (res != null) 
            return res;
        if ((((typ.value()) & (NounPhraseParseAttr.PARSEPREPOSITION.value()))) != (NounPhraseParseAttr.NO.value())) {
            if ((t instanceof com.pullenti.ner.TextToken) && t.getMorph()._getClass().isPreposition() && (t.getWhitespacesAfterCount() < 3)) {
                res = _NounPraseHelperInt.tryParse(t.getNext(), typ, maxCharPos);
                if (res != null) {
                    com.pullenti.morph.MorphCase mc = com.pullenti.morph.LanguageHelper.getCaseAfterPreposition((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).lemma);
                    res.preposition = t;
                    res.setBeginToken(t);
                    if (!((com.pullenti.morph.MorphCase.ooBitand(mc, res.getMorph().getCase()))).isUndefined()) 
                        res.getMorph().removeItems(mc);
                    else if (t.getMorph()._getClass().isAdverb()) 
                        return null;
                    return res;
                }
            }
        }
        return null;
    }
    public NounPhraseHelper() {
    }
}
