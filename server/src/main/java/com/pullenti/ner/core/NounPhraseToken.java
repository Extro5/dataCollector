/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.core;

/**
 * Токен для представления именной группы
 */
public class NounPhraseToken extends com.pullenti.ner.MetaToken {

    public NounPhraseToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    /**
     * Корень группы (существительное, местоимение или сущность)
     */
    public com.pullenti.ner.MetaToken noun;

    /**
     * Прилагательные (и причастия, если задан ключ ParseVerbs)
     */
    public java.util.ArrayList<com.pullenti.ner.MetaToken> adjectives = new java.util.ArrayList< >();

    /**
     * Наречия (если задан ключ ParseAdverbs при выделении)
     */
    public java.util.ArrayList<com.pullenti.ner.TextToken> adverbs = null;

    /**
     * Для случая "по современным на данный момент представлениям" - 
     *  это будет "данный момент"
     */
    public NounPhraseToken internalNoun;

    /**
     * Токен с анафорической ссылкой-местоимением (если есть), например: старшего своего брата
     */
    public com.pullenti.ner.Token anafor;

    /**
     * Начальный предлог предлог (если задан ключ ParsePreposition)
     */
    public com.pullenti.ner.Token preposition;

    @Override
    public String getNormalCaseText(com.pullenti.morph.MorphClass mc, boolean singleNumber, com.pullenti.morph.MorphGender gender, boolean keepChars) {
        StringBuilder res = new StringBuilder();
        if (gender == com.pullenti.morph.MorphGender.UNDEFINED) 
            gender = getMorph().getGender();
        if (adverbs != null && adverbs.size() > 0) {
            int i = 0;
            if (adjectives.size() > 0) {
                for(int j = 0; j < adjectives.size(); j++) {
                    String s = adjectives.get(j).getNormalCaseText(com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphClass.PRONOUN), singleNumber, gender, keepChars);
                    res.append(((String)com.pullenti.n2j.Utils.notnull(s, "?"))).append(" ");
                    for(; i < adverbs.size(); i++) {
                        if (adverbs.get(i).beginChar < adjectives.get(j).beginChar) 
                            res.append(adjectives.get(i).getNormalCaseText(com.pullenti.morph.MorphClass.ADVERB, false, com.pullenti.morph.MorphGender.UNDEFINED, false)).append(" ");
                        else 
                            break;
                    }
                }
            }
            for(; i < adverbs.size(); i++) {
                res.append(adjectives.get(i).getNormalCaseText(com.pullenti.morph.MorphClass.ADVERB, false, com.pullenti.morph.MorphGender.UNDEFINED, false)).append(" ");
            }
        }
        else 
            for(com.pullenti.ner.MetaToken t : adjectives) {
                String s = t.getNormalCaseText(com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphClass.PRONOUN), singleNumber, gender, keepChars);
                res.append(((String)com.pullenti.n2j.Utils.notnull(s, "?"))).append(" ");
            }
        String r = null;
        if ((noun.getBeginToken() instanceof com.pullenti.ner.ReferentToken) && noun.getBeginToken() == noun.getEndToken()) 
            r = noun.getBeginToken().getNormalCaseText(new com.pullenti.morph.MorphClass(null), singleNumber, gender, keepChars);
        else 
            r = noun.getNormalCaseText(com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphClass.PRONOUN), singleNumber, gender, keepChars);
        if (r == null || com.pullenti.n2j.Utils.stringsEq(r, "?")) 
            r = noun.getNormalCaseText(new com.pullenti.morph.MorphClass(null), singleNumber, com.pullenti.morph.MorphGender.UNDEFINED, false);
        res.append((String)com.pullenti.n2j.Utils.notnull(r, (noun != null ? noun.toString() : null)));
        return res.toString();
    }

    public String getNormalCaseTextWithoutAdjective(int adjIndex) {
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < adjectives.size(); i++) {
            if (i != adjIndex) {
                String s = adjectives.get(i).getNormalCaseText(com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphClass.PRONOUN), false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                res.append(((String)com.pullenti.n2j.Utils.notnull(s, "?"))).append(" ");
            }
        }
        String r = noun.getNormalCaseText(com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphClass.PRONOUN), false, com.pullenti.morph.MorphGender.UNDEFINED, false);
        if (r == null) 
            r = noun.getNormalCaseText(new com.pullenti.morph.MorphClass(null), false, com.pullenti.morph.MorphGender.UNDEFINED, false);
        res.append((String)com.pullenti.n2j.Utils.notnull(r, (noun != null ? noun.toString() : null)));
        return res.toString();
    }

    /**
     * Сгенерировать текст именной группы в нужном падеже и числе
     * @param cas 
     * @param plural 
     * @return 
     */
    public String getMorphVariant(com.pullenti.morph.MorphCase cas, boolean plural) {
        com.pullenti.morph.MorphBaseInfo mi = com.pullenti.morph.MorphBaseInfo._new488(cas, com.pullenti.morph.MorphLang.RU);
        if (plural) 
            mi.setNumber(com.pullenti.morph.MorphNumber.PLURAL);
        else 
            mi.setNumber(com.pullenti.morph.MorphNumber.SINGULAR);
        String res = null;
        for(com.pullenti.ner.MetaToken a : adjectives) {
            String tt = MiscHelper.getTextValueOfMetaToken(a, GetTextAttr.NO);
            if (a.getBeginToken() != a.getEndToken() || !((a.getBeginToken() instanceof com.pullenti.ner.TextToken))) {
            }
            else {
                String tt2 = com.pullenti.morph.Morphology.getWordform(tt, mi);
                if (tt2 != null) 
                    tt = tt2;
            }
            if (res == null) 
                res = tt;
            else 
                res = res + " " + tt;
        }
        if (noun != null) {
            String tt = MiscHelper.getTextValueOfMetaToken(noun, GetTextAttr.NO);
            if (noun.getBeginToken() != noun.getEndToken() || !((noun.getBeginToken() instanceof com.pullenti.ner.TextToken))) {
            }
            else {
                String tt2 = com.pullenti.morph.Morphology.getWordform(tt, mi);
                if (tt2 != null) 
                    tt = tt2;
            }
            if (res == null) 
                res = tt;
            else 
                res = res + " " + tt;
        }
        return res;
    }

    @Override
    public String toString() {
        if (internalNoun == null) 
            return ((String)com.pullenti.n2j.Utils.notnull(getNormalCaseText(new com.pullenti.morph.MorphClass(null), false, com.pullenti.morph.MorphGender.UNDEFINED, false), "?")) + " " + getMorph().toString();
        else 
            return ((String)com.pullenti.n2j.Utils.notnull(getNormalCaseText(new com.pullenti.morph.MorphClass(null), false, com.pullenti.morph.MorphGender.UNDEFINED, false), "?")) + " " + getMorph().toString() + " / " + internalNoun.toString();
    }

    public void removeLastNounWord() {
        if (noun != null) {
            for(com.pullenti.morph.MorphBaseInfo it : noun.getMorph().getItems()) {
                com.pullenti.ner.internal.NounPhraseItemTextVar ii = (com.pullenti.ner.internal.NounPhraseItemTextVar)com.pullenti.n2j.Utils.cast(it, com.pullenti.ner.internal.NounPhraseItemTextVar.class);
                if (ii == null || ii.normalValue == null) 
                    continue;
                int j = ii.normalValue.indexOf('-');
                if (j > 0) 
                    ii.normalValue = ii.normalValue.substring(0, 0+(j));
                if (ii.singleNumberValue != null) {
                    if ((((j = ii.singleNumberValue.indexOf('-')))) > 0) 
                        ii.singleNumberValue = ii.singleNumberValue.substring(0, 0+(j));
                }
            }
        }
    }
    public NounPhraseToken() {
        super();
    }
}
