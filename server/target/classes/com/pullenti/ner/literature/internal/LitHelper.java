/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature.internal;

public class LitHelper {

    /**
     * Найти ближайший грагол (слева или справа), согласованный с токеном, 
     *  который считается агентом глагола
     * @param agent 
     * @return 
     */
    public static com.pullenti.ner.TextToken findNearVerb(com.pullenti.ner.Token agent, com.pullenti.morph.MorphGender gen) {
        if (agent == null) 
            return null;
        com.pullenti.ner.Token tt;
        for(tt = agent.getNext(); tt != null; tt = tt.getNext()) {
            if (!((tt instanceof com.pullenti.ner.TextToken)) || !tt.chars.isLetter()) 
                break;
            com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
            if (mc.isAdverb() || mc.isMisc()) 
                continue;
            if (mc.isPreposition()) {
                com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                if (npt1 != null) {
                    tt = npt1.getEndToken();
                    continue;
                }
            }
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (npt != null) {
                if (!npt.getMorph().getCase().isUndefined() && !npt.getMorph().getCase().isNominative()) {
                    tt = npt.getEndToken();
                    continue;
                }
            }
            break;
        }
        if (tt != null && tt.getMorphClassInDictionary().isVerb()) {
            if (checkAgentVerbAccord(agent, (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.TextToken.class), gen)) 
                return (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.TextToken.class);
        }
        if ((agent.getPrevious() instanceof com.pullenti.ner.TextToken) && agent.getPrevious().getMorphClassInDictionary().isVerb()) {
            if (checkAgentVerbAccord(agent, (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(agent.getPrevious(), com.pullenti.ner.TextToken.class), gen)) 
                return (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(agent.getPrevious(), com.pullenti.ner.TextToken.class);
        }
        return null;
    }

    public static boolean checkAgentVerbAccord(com.pullenti.ner.Token agent, com.pullenti.ner.TextToken verb, com.pullenti.morph.MorphGender gen) {
        if (agent == null || verb == null) 
            return false;
        if (verb.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
            return false;
        if (verb.getMorph().containsAttr("инф.", new com.pullenti.morph.MorphClass(null))) 
            return false;
        if (gen != com.pullenti.morph.MorphGender.UNDEFINED) {
            if ((((verb.getMorph().getGender().value()) & (gen.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                return true;
        }
        if (agent.getMorph().getGender() != com.pullenti.morph.MorphGender.UNDEFINED && verb.getMorph().getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
            if ((((agent.getMorph().getGender().value()) & (verb.getMorph().getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                return false;
        }
        return true;
    }

    public static com.pullenti.ner.ReferentToken tryAttachLink(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
        if (npt == null) 
            return null;
        CharItemToken it = CharItemToken.tryParse(npt.noun.getBeginToken(), null);
        if (it == null) 
            return null;
        if (it.typ != CharItemType.ATTR) 
            return null;
        if (it.subtyp != CharItemAttrSubtype.PERSONAFTER) 
            return null;
        com.pullenti.ner.literature.LinkReferent li = new com.pullenti.ner.literature.LinkReferent();
        li.setName(npt.getNormalCaseText(new com.pullenti.morph.MorphClass(null), true, com.pullenti.morph.MorphGender.UNDEFINED, false).toLowerCase());
        return com.pullenti.ner.ReferentToken._new709(li, t, npt.getEndToken(), npt.getMorph());
    }

    /**
     * Проверить, что это шаблон типа "шарманщик по имени ..."
     * @param t 
     * @return 
     */
    public static com.pullenti.ner.Token findByName(com.pullenti.ner.Token t, com.pullenti.n2j.Outargwrapper<com.pullenti.morph.MorphGender> gen) {
        gen.value = com.pullenti.morph.MorphGender.UNDEFINED;
        if (t == null) 
            return null;
        com.pullenti.ner.Token tt = t;
        if ((tt.getPrevious() instanceof com.pullenti.ner.ReferentToken) && com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt.getPrevious())) {
            if (tt.isValue("БЫТЬ", null)) {
                if (_isNameRes(tt.getNext())) {
                    com.pullenti.morph.MorphGender gg;
                    com.pullenti.n2j.Outargwrapper<com.pullenti.morph.MorphGender> inoutarg1535 = new com.pullenti.n2j.Outargwrapper< >();
                    com.pullenti.ner.Token tt2 = findByName(tt.getNext().getNext(), inoutarg1535);
                    gg = inoutarg1535.value;
                    if (tt2 != null) 
                        return null;
                    for(com.pullenti.morph.MorphBaseInfo it : tt.getMorph().getItems()) {
                        if (it._getClass().isVerb()) 
                            gen.value = com.pullenti.morph.MorphGender.of((gen.value.value()) | (it.getGender().value()));
                    }
                    return tt.getNext();
                }
            }
        }
        if (tt.isComma() && tt.getNext() != null) {
            tt = tt.getNext();
            if (tt.getNext() != null) {
                com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
                if (mc.isAdjective() && mc.isVerb()) {
                    gen.value = tt.getMorph().getGender();
                    tt = tt.getNext();
                }
            }
        }
        if (tt.isChar('.') && tt.getNext() != null) 
            tt = tt.getNext();
        if (tt.isAnd() && tt.getNext() != null) 
            tt = tt.getNext();
        if (!((tt instanceof com.pullenti.ner.TextToken))) 
            return null;
        if (((tt.isValue("ПО", null) || tt.isValue("ПОД", null))) && _isName(tt.getNext())) {
            if (_isNameRes(tt.getNext().getNext())) 
                return tt.getNext().getNext();
            return null;
        }
        if (tt.isValue("ЕГО", null) || tt.isValue("ЕЕ", null)) {
            gen.value = (tt.isValue("ЕГО", null) ? com.pullenti.morph.MorphGender.MASCULINE : com.pullenti.morph.MorphGender.FEMINIE);
            tt = tt.getNext();
            if (tt == null) 
                return null;
        }
        if (_isName(tt) || tt.isValue("ЗВАТЬ", null)) {
            if ((((tt = tt.getNext()))) == null) 
                return null;
            if (tt.isValue("ЕГО", null) || tt.isValue("ЕЕ", null)) {
                gen.value = (tt.isValue("ЕГО", null) ? com.pullenti.morph.MorphGender.MASCULINE : com.pullenti.morph.MorphGender.FEMINIE);
                tt = tt.getNext();
                if (tt == null) 
                    return null;
            }
            if (tt.isValue("БЫТЬ", null) && tt.getNext() != null) 
                tt = tt.getNext();
            if (_isNameRes(tt)) 
                return tt;
        }
        return null;
    }

    public static CharacterEx findAnafor(com.pullenti.ner.Token t, com.pullenti.ner.Token anaf, int begin) {
        if (t == null || !((anaf instanceof com.pullenti.ner.TextToken))) 
            return null;
        String a = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(anaf, com.pullenti.ner.TextToken.class))).term;
        com.pullenti.morph.MorphGender gen = com.pullenti.morph.MorphGender.UNDEFINED;
        if (com.pullenti.n2j.Utils.stringsEq(a, "ОН")) 
            gen = com.pullenti.morph.MorphGender.MASCULINE;
        else if (com.pullenti.n2j.Utils.stringsEq(a, "ОНА")) 
            gen = com.pullenti.morph.MorphGender.FEMINIE;
        else if (com.pullenti.n2j.Utils.stringsEq(a, "ОНО")) 
            gen = com.pullenti.morph.MorphGender.NEUTER;
        else if (anaf.isValue("ЕГО", null)) {
            gen = com.pullenti.morph.MorphGender.of((com.pullenti.morph.MorphGender.MASCULINE.value()) | (com.pullenti.morph.MorphGender.NEUTER.value()));
            if (t.getNext() instanceof com.pullenti.ner.ReferentToken) {
            }
        }
        else if (anaf.isValue("ЕЕ", null)) 
            gen = com.pullenti.morph.MorphGender.FEMINIE;
        boolean isDlg = DialogItemToken.isInDialog(t);
        int err = 0;
        for(com.pullenti.ner.Token tt = t.getPrevious(); tt != null && tt.beginChar >= begin && (err < 4); tt = tt.getPrevious()) {
            if (DialogItemToken.isInDialog(tt) != isDlg) {
                if (isDlg) 
                    break;
                else 
                    continue;
            }
            CharacterEx res = (CharacterEx)com.pullenti.n2j.Utils.cast(tt.getReferent(), CharacterEx.class);
            if (res != null) {
                if (gen == com.pullenti.morph.MorphGender.UNDEFINED) {
                    if (!tt.getMorph().getCase().isUndefined() && !tt.getMorph().getCase().isNominative()) 
                        continue;
                    return res;
                }
                if ((((gen.value()) & (res.gender.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                    if (err >= 2) {
                        if (!tt.getMorph().getCase().isUndefined() && !tt.getMorph().getCase().isNominative()) 
                            return null;
                    }
                    return res;
                }
                continue;
            }
            CharacterVariant var = (CharacterVariant)com.pullenti.n2j.Utils.cast(tt.getReferent(), CharacterVariant.class);
            if (var != null) {
                if (gen == com.pullenti.morph.MorphGender.UNDEFINED) {
                    if (!tt.getMorph().getCase().isUndefined() && !tt.getMorph().getCase().isNominative()) 
                        continue;
                }
                if ((((gen.value()) & (var.getGender().value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                    if (!tt.getMorph().getCase().isUndefined() && !tt.getMorph().getCase().isNominative()) 
                        err += 1;
                    else 
                        err += 2;
                }
                continue;
            }
            if (!((tt instanceof com.pullenti.ner.TextToken)) || !tt.chars.isLetter()) 
                continue;
            com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
            if (!mc.isNoun()) 
                continue;
            if (tt.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                continue;
            if (tt.getMorph().getGender() != com.pullenti.morph.MorphGender.UNDEFINED && gen != com.pullenti.morph.MorphGender.UNDEFINED) {
                if ((((gen.value()) & (tt.getMorph().getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                    continue;
            }
            if (!tt.getMorph().getCase().isUndefined() && !tt.getMorph().getCase().isNominative()) 
                continue;
            if (tt.getPrevious() != null && tt.getPrevious().getMorph()._getClass().isPreposition()) 
                continue;
            err++;
        }
        return null;
    }

    private static boolean _isNameRes(com.pullenti.ner.Token t) {
        if (!((t instanceof com.pullenti.ner.ReferentToken))) {
            if (!((t instanceof com.pullenti.ner.TextToken))) 
                return false;
            if (t.chars.isCapitalUpper() && t.chars.isLetter()) 
                return true;
            return false;
        }
        return (t.getReferent() instanceof CharacterEx) || (t.getReferent() instanceof CharacterVariant);
    }

    private static boolean _isName(com.pullenti.ner.Token t) {
        if (!((t instanceof com.pullenti.ner.TextToken))) 
            return false;
        if (((t.isValue("ИМЯ", "ІМЯ") || t.isValue("ПРОЗВИЩЕ", "ПРІЗВИСЬКО") || t.isValue("ПРОЗВАНИЕ", "ПРОЗВАННЯ")) || t.isValue("КЛИЧКА", "КЛИЧКА") || t.isValue("ФАМИЛИЯ", "ПРІЗВИЩЕ")) || t.isValue("ПСЕВДОНИМ", null)) 
            return true;
        return false;
    }
    public LitHelper() {
    }
}
