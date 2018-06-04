/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic.internal;

public class SynHelper {

    public static java.util.ArrayList<SynToken> analyzeSent(java.util.ArrayList<SynToken> li, History hist, boolean ignoreActions) {
        com.pullenti.ner.Token lasToken = li.get(li.size() - 1).getEndToken();
        for(int i = 0; i < li.size(); i++) {
            if (li.get(i).typ2 == Types.PROPERNAME && li.get(i).typ == Types.OBJ && li.get(i).getVal(ValTypes.NAME) != null) {
                com.pullenti.morph.MorphClass mc = li.get(i).getBeginToken().getMorphClassInDictionary();
                if (mc.isUndefined() || mc.isProper()) 
                    continue;
                String nam = (com.pullenti.morph.MorphClass.ooEq(mc, com.pullenti.morph.MorphClass.NOUN) ? li.get(i).getBeginToken().getNormalCaseText(new com.pullenti.morph.MorphClass(null), false, com.pullenti.morph.MorphGender.UNDEFINED, false) : li.get(i).getVal(ValTypes.NAME));
                if (hist.abbrs.containsKey(nam) || hist.names.containsKey(nam) || hist.aliases.containsKey(nam)) 
                    continue;
                if (li.get(i).chars.isLatinLetter()) 
                    continue;
                if (li.get(i).getBeginToken() != li.get(i).getEndToken()) 
                    continue;
                if (i == 0) {
                }
                else if (li.get(i).chars.isCapitalUpper() && com.pullenti.morph.MorphClass.ooEq(mc, com.pullenti.morph.MorphClass.NOUN) && !li.get(i - 1).canHasProperName()) {
                }
                else 
                    continue;
                li.get(i).typ2 = Types.UNDEFINED;
                li.get(i).addVal(nam, ValTypes.BASE);
                li.get(i).delVals(ValTypes.NAME);
            }
        }
        for(int k = 0; k < 2; k++) {
            Types ty = (k == 0 ? Types.PROPERNAME : Types.TIME);
            for(int i = 0; i < (li.size() - 2); i++) {
                if (((li.get(i).typ == ty || li.get(i).typ2 == ty)) && li.get(i + 1).isConjOrComma() && ((li.get(i + 2).typ == ty || li.get(i + 2).typ2 == ty))) {
                    li.get(i).typ = ty;
                    li.get(i + 2).typ = ty;
                }
            }
            if (ty == Types.TIME) {
                for(int i = 0; i < (li.size() - 1); i++) {
                    if (((li.get(i).typ == ty || li.get(i).typ2 == ty)) && ((li.get(i + 1).typ == ty || li.get(i + 1).typ2 == ty))) {
                        li.get(i).typ = ty;
                        li.get(i + 1).typ = ty;
                        if (li.get(i).ref == null && li.get(i + 1).ref == null) {
                            li.get(i).setEndToken(li.get(i + 1).getEndToken());
                            com.pullenti.ner.Token tt = li.get(i).getBeginToken();
                            if (tt.getMorph()._getClass().isPreposition()) 
                                tt = tt.getNext();
                            li.get(i).addVal(com.pullenti.ner.core.MiscHelper.getTextValue(tt, li.get(i).getEndToken(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE), ValTypes.BASE);
                            li.remove(i + 1);
                            i--;
                        }
                    }
                }
            }
        }
        hist.manageNames(li);
        ObjectHelper.processObjects(li, hist);
        if (!ignoreActions) 
            PredicateHelper.processPredicates(li);
        if (li.size() > 0) {
            if (li.get(li.size() - 1).endChar < lasToken.endChar) 
                li.add(SynToken._new2480(li.get(li.size() - 1).getEndToken().getNext(), lasToken, Types.UNDEFINED));
        }
        return li;
    }
    public SynHelper() {
    }
}
