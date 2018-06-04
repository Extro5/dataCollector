/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner;

/**
 * Входной токен (после морфанализа)
 */
public class TextToken extends Token {

    public TextToken(com.pullenti.morph.MorphToken source, com.pullenti.ner.core.AnalysisKit _kit) {
        super(_kit, (source == null ? 0 : source.beginChar), (source == null ? 0 : source.endChar));
        if (source == null) 
            return;
        chars = source.charInfo;
        term = source.term;
        lemma = (String)com.pullenti.n2j.Utils.notnull(source.getLemma(), term);
        maxLength = (short)term.length();
        setMorph(new MorphCollection(null));
        if (source.wordForms != null) {
            for(com.pullenti.morph.MorphWordForm wf : source.wordForms) {
                getMorph().addItem(wf);
                if (wf.normalCase != null && (maxLength < wf.normalCase.length())) 
                    maxLength = (short)wf.normalCase.length();
                if (wf.normalFull != null && (maxLength < wf.normalFull.length())) 
                    maxLength = (short)wf.normalFull.length();
            }
        }
        for(int i = 0; i < term.length(); i++) {
            char ch = term.charAt(i);
            int j;
            for(j = 0; j < getMorph().getItemsCount(); j++) {
                com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(getMorph().getIndexerItem(j), com.pullenti.morph.MorphWordForm.class);
                if (wf.normalCase != null) {
                    if (i >= wf.normalCase.length()) 
                        break;
                    if (wf.normalCase.charAt(i) != ch) 
                        break;
                }
                if (wf.normalFull != null) {
                    if (i >= wf.normalFull.length()) 
                        break;
                    if (wf.normalFull.charAt(i) != ch) 
                        break;
                }
            }
            if (j < getMorph().getItemsCount()) 
                break;
            invariantPrefixLength = (short)((i + 1));
        }
        if (getMorph().getLanguage().isUndefined() && !source.getLanguage().isUndefined()) 
            getMorph().setLanguage(source.getLanguage());
    }

    /**
     * Исходный фрагмент, слегка нормализованный (не морфологически, а символьно)
     */
    public String term;

    /**
     * А это уже лемма (нормальная форма слова)
     */
    public String lemma;

    /**
     * Это вариант до коррекции (если была коррекция)
     */
    public String term0;

    /**
     * Получить лемму (устарело, используйте Lemma)
     * @return 
     */
    public String getLemma() {
        return lemma;
    }

    /**
     * Это количество начальных символов, одинаковых для всех морфологических вариантов 
     *  (пригодится для оптимизации поиска)
     */
    public short invariantPrefixLength;

    /**
     * Максимальная длина среди морфвариантов
     */
    public short maxLength;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(term);
        for(com.pullenti.morph.MorphBaseInfo l : getMorph().getItems()) {
            res.append(", ").append(l.toString());
        }
        return res.toString();
    }

    /**
     * Попробовать привязать словарь
     * @param dict 
     * @return 
     */
    public Object checkValue(java.util.HashMap<String, Object> dict) {
        if (dict == null) 
            return null;
        Object res;
        com.pullenti.n2j.Outargwrapper<Object> inoutarg2782 = new com.pullenti.n2j.Outargwrapper< >();
        Boolean inoutres2783 = com.pullenti.n2j.Utils.tryGetValue(dict, term, inoutarg2782);
        res = inoutarg2782.value;
        if (inoutres2783) 
            return res;
        if (getMorph() != null) {
            for(com.pullenti.morph.MorphBaseInfo it : getMorph().getItems()) {
                com.pullenti.morph.MorphWordForm mf = (com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(it, com.pullenti.morph.MorphWordForm.class);
                if (mf != null) {
                    if (mf.normalCase != null) {
                        com.pullenti.n2j.Outargwrapper<Object> inoutarg2778 = new com.pullenti.n2j.Outargwrapper< >();
                        Boolean inoutres2779 = com.pullenti.n2j.Utils.tryGetValue(dict, mf.normalCase, inoutarg2778);
                        res = inoutarg2778.value;
                        if (inoutres2779) 
                            return res;
                    }
                    if (mf.normalFull != null && com.pullenti.n2j.Utils.stringsNe(mf.normalCase, mf.normalFull)) {
                        com.pullenti.n2j.Outargwrapper<Object> inoutarg2780 = new com.pullenti.n2j.Outargwrapper< >();
                        Boolean inoutres2781 = com.pullenti.n2j.Utils.tryGetValue(dict, mf.normalFull, inoutarg2780);
                        res = inoutarg2780.value;
                        if (inoutres2781) 
                            return res;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String getSourceText() {
        return super.getSourceText();
    }

    @Override
    public boolean isValue(String _term, String termUA) {
        if (termUA != null && getMorph().getLanguage().isUa()) {
            if (isValue(termUA, null)) 
                return true;
        }
        if (_term == null) 
            return false;
        if (invariantPrefixLength > _term.length()) 
            return false;
        if (maxLength >= term.length() && (maxLength < _term.length())) 
            return false;
        if (com.pullenti.n2j.Utils.stringsEq(_term, term)) 
            return true;
        for(com.pullenti.morph.MorphBaseInfo wf : getMorph().getItems()) {
            if (com.pullenti.n2j.Utils.stringsEq((((com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class))).normalCase, _term) || com.pullenti.n2j.Utils.stringsEq((((com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class))).normalFull, _term)) 
                return true;
        }
        return false;
    }

    @Override
    public String getNormalCaseText(com.pullenti.morph.MorphClass mc, boolean singleNumber, com.pullenti.morph.MorphGender gender, boolean keepChars) {
        boolean empty = true;
        for(com.pullenti.morph.MorphBaseInfo it : getMorph().getItems()) {
            if (mc != null && !mc.isUndefined()) {
                int cc = ((int)it._getClass().value) & ((int)mc.value);
                if (cc == 0) 
                    continue;
                if (com.pullenti.morph.MorphClass.isMiscInt(cc) && !com.pullenti.morph.MorphClass.isProperInt(cc) && mc.value != it._getClass().value) 
                    continue;
            }
            com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(it, com.pullenti.morph.MorphWordForm.class);
            boolean normalFull = false;
            if (gender != com.pullenti.morph.MorphGender.UNDEFINED) {
                if ((((it.getGender().value()) & (gender.value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                    if ((gender == com.pullenti.morph.MorphGender.MASCULINE && ((it.getGender() != com.pullenti.morph.MorphGender.UNDEFINED || it.getNumber() == com.pullenti.morph.MorphNumber.PLURAL)) && wf != null) && wf.normalFull != null) 
                        normalFull = true;
                    else if (gender == com.pullenti.morph.MorphGender.MASCULINE && it._getClass().isPersonalPronoun()) {
                    }
                    else 
                        continue;
                }
            }
            if (!it.getCase().isUndefined()) 
                empty = false;
            if (wf != null) {
                String res;
                if (singleNumber && it.getNumber() == com.pullenti.morph.MorphNumber.PLURAL && wf.normalFull != null) {
                    int le = wf.normalCase.length();
                    if ((le == (wf.normalFull.length() + 2) && le > 4 && wf.normalCase.charAt(le - 2) == 'С') && wf.normalCase.charAt(le - 1) == 'Я') 
                        res = wf.normalCase;
                    else 
                        res = (normalFull ? wf.normalFull : wf.normalFull);
                }
                else 
                    res = (normalFull ? wf.normalFull : ((String)com.pullenti.n2j.Utils.notnull(wf.normalCase, term)));
                if (singleNumber && mc != null && com.pullenti.morph.MorphClass.ooEq(mc, com.pullenti.morph.MorphClass.NOUN)) {
                    if (com.pullenti.n2j.Utils.stringsEq(res, "ДЕТИ")) 
                        res = "РЕБЕНОК";
                }
                if (keepChars) {
                    if (chars.isAllLower()) 
                        res = res.toLowerCase();
                    else if (chars.isCapitalUpper()) 
                        res = com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(res);
                }
                return res;
            }
        }
        if (!empty) 
            return null;
        String te = null;
        if (singleNumber && mc != null) {
            com.pullenti.morph.MorphBaseInfo bi = com.pullenti.morph.MorphBaseInfo._new486(new com.pullenti.morph.MorphClass(mc), gender, com.pullenti.morph.MorphNumber.SINGULAR, getMorph().getLanguage());
            String vars = com.pullenti.morph.Morphology.getWordform(term, bi);
            if (vars != null) 
                te = vars;
        }
        if (chars.isCyrillicLetter() && te == null && term.length() > 3) {
            char ch0 = term.charAt(term.length() - 1);
            char ch1 = term.charAt(term.length() - 2);
            if (ch0 == 'М' && ((ch1 == 'О' || ch1 == 'А'))) 
                te = term.substring(0, 0+(term.length() - 2));
            else if (!com.pullenti.morph.LanguageHelper.isCyrillicVowel(ch1) && com.pullenti.morph.LanguageHelper.isCyrillicVowel(ch0)) 
                te = term.substring(0, 0+(term.length() - 1));
        }
        if (te == null) 
            te = term;
        if (keepChars) {
            if (chars.isAllLower()) 
                return te.toLowerCase();
            else if (chars.isCapitalUpper()) 
                return com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(te);
        }
        return te;
    }

    public static java.util.ArrayList<TextToken> getSourceTextTokens(Token begin, Token end) {
        java.util.ArrayList<TextToken> res = new java.util.ArrayList< >();
        for(Token t = begin; t != null && t != end.getNext() && t.endChar <= end.endChar; t = t.getNext()) {
            if (t instanceof TextToken) 
                res.add((TextToken)com.pullenti.n2j.Utils.cast(t, TextToken.class));
            else if (t instanceof MetaToken) 
                res.addAll(getSourceTextTokens((((MetaToken)com.pullenti.n2j.Utils.cast(t, MetaToken.class))).getBeginToken(), (((MetaToken)com.pullenti.n2j.Utils.cast(t, MetaToken.class))).getEndToken()));
        }
        return res;
    }

    /**
     * Признак того, что это чистый глагол
     */
    public boolean isPureVerb() {
        boolean ret = false;
        if ((isValue("МОЖНО", null) || isValue("МОЖЕТ", null) || isValue("ДОЛЖНЫЙ", null)) || isValue("НУЖНО", null)) 
            return true;
        for(com.pullenti.morph.MorphBaseInfo it : getMorph().getItems()) {
            if ((it instanceof com.pullenti.morph.MorphWordForm) && (((com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(it, com.pullenti.morph.MorphWordForm.class))).isInDictionary()) {
                if (it._getClass().isVerb() && it.getCase().isUndefined()) 
                    ret = true;
                else if (!it._getClass().isVerb()) {
                    if (it._getClass().isAdjective() && it.containsAttr("к.ф.", new com.pullenti.morph.MorphClass())) {
                    }
                    else 
                        return false;
                }
            }
        }
        return ret;
    }


    /**
     * Проверка, что это глагол типа БЫТЬ, ЯВЛЯТЬСЯ и т.п.
     */
    public boolean isVerbBe() {
        if (isValue("БЫТЬ", null) || isValue("ЕСТЬ", null) || isValue("ЯВЛЯТЬ", null)) 
            return true;
        if (com.pullenti.n2j.Utils.stringsEq(term, "IS") || com.pullenti.n2j.Utils.stringsEq(term, "WAS") || com.pullenti.n2j.Utils.stringsEq(term, "BECAME")) 
            return true;
        if (com.pullenti.n2j.Utils.stringsEq(term, "Є")) 
            return true;
        return false;
    }


    @Override
    public void serialize(com.pullenti.n2j.Stream stream) throws java.io.IOException {
        super.serialize(stream);
        com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, term);
        com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, lemma);
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, invariantPrefixLength);
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, maxLength);
    }

    @Override
    public void deserialize(com.pullenti.n2j.Stream stream, com.pullenti.ner.core.AnalysisKit _kit) throws java.io.IOException {
        super.deserialize(stream, _kit);
        term = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
        lemma = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
        invariantPrefixLength = com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream);
        maxLength = com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream);
    }

    public static TextToken _new475(com.pullenti.morph.MorphToken _arg1, com.pullenti.ner.core.AnalysisKit _arg2, String _arg3) {
        TextToken res = new TextToken(_arg1, _arg2);
        res.term0 = _arg3;
        return res;
    }
    public static TextToken _new478(com.pullenti.morph.MorphToken _arg1, com.pullenti.ner.core.AnalysisKit _arg2, com.pullenti.morph.CharsInfo _arg3, int _arg4, int _arg5, String _arg6) {
        TextToken res = new TextToken(_arg1, _arg2);
        res.chars = _arg3;
        res.beginChar = _arg4;
        res.endChar = _arg5;
        res.term0 = _arg6;
        return res;
    }
    public TextToken() {
        super();
    }
}
