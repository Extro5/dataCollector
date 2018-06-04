/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic.internal;

/**
 * Анализатор предложений
 */
public class SentenceContainer {

    public java.util.ArrayList<SynToken> baseSent = new java.util.ArrayList< >();

    public java.util.ArrayList<java.util.ArrayList<SynToken>> slaveSents = new java.util.ArrayList< >();

    public int _do(java.util.ArrayList<SynToken> all, int ind) {
        baseSent.clear();
        slaveSents.clear();
        if (ind >= all.size()) 
            return -1;
        int i;
        if (all.get(ind).typ == Types.BRACKETOPEN) {
            for(i = ind + 1; i < all.size(); i++) {
                if (all.get(i).typ == Types.BRACKETCLOSE) 
                    break;
            }
            if (i < all.size()) {
                int j0 = i + 1;
                int acts = 0;
                boolean ok = true;
                for(; j0 < all.size(); j0++) {
                    if (all.get(j0).typ == Types.COMMA || all.get(j0).getBeginToken().isHiphen()) {
                    }
                    else 
                        break;
                }
                int j;
                for(j = j0; j < all.size(); j++) {
                    if (all.get(j).typ == Types.SEQEND) 
                        break;
                    else if (all.get(j).typ == Types.ACT) 
                        acts++;
                    else if ((all.get(j).typ != Types.OBJ && all.get(j).typ != Types.TIME && all.get(j).typ != Types.EMPTY) && all.get(j).typ != Types.PROPERNAME && all.get(j).typ != Types.NUMBER) {
                        ok = false;
                        break;
                    }
                }
                if (ok && acts == 1) {
                    java.util.ArrayList<SynToken> li = null;
                    for(int ii = ind + 1; ii < i; ii++) {
                        if (all.get(ii).typ == Types.SEQEND) {
                            li = null;
                            continue;
                        }
                        if (li == null) 
                            slaveSents.add((li = new java.util.ArrayList< >()));
                        li.add(all.get(ii));
                        all.get(ii).level = 1;
                    }
                    for(int ii = j0; ii < j; ii++) {
                        baseSent.add(all.get(ii));
                    }
                    if (j < all.size()) 
                        j++;
                    return j;
                }
            }
        }
        int objs = 0;
        for(i = ind; i < all.size(); i++) {
            if (all.get(i).typ == Types.SEQEND) {
                i++;
                break;
            }
            else {
                baseSent.add(all.get(i));
                if (all.get(i).typ == Types.OBJ || all.get(i).typ == Types.PROPERNAME) 
                    objs++;
                if (all.get(i).typ == Types.ACT && all.get(i).getActCanBeSpeech() && objs > 0) {
                    boolean ok = false;
                    int i0 = i + 1;
                    for(; i0 < all.size(); i0++) {
                        if ((all.get(i0).typ == Types.WHAT || all.get(i0).typ == Types.COMMA || all.get(i0).getBeginToken().isHiphen()) || all.get(i0).getBeginToken().isChar(':')) 
                            ok = true;
                        else 
                            break;
                    }
                    if (ok && ((i0 + 1) < all.size())) {
                        boolean br = false;
                        if (all.get(i0).typ == Types.BRACKETOPEN) {
                            br = true;
                            i0++;
                        }
                        java.util.ArrayList<SynToken> li = null;
                        for(int ii = i0; ii < all.size(); ii++) {
                            if (all.get(ii).typ == Types.BRACKETCLOSE && br) {
                                br = false;
                                li = null;
                                continue;
                            }
                            if (all.get(ii).typ == Types.SEQEND) {
                                if (!br) 
                                    return ii + 1;
                                li = null;
                                continue;
                            }
                            if (li == null) 
                                slaveSents.add((li = new java.util.ArrayList< >()));
                            li.add(all.get(ii));
                            all.get(ii).level = 1;
                        }
                        return all.size();
                    }
                }
            }
        }
        if (baseSent.size() > 0 && baseSent.get(0).getBeginToken().isValue("ПО", "ЗА") && ((baseSent.get(0).isRootValue("ДАННЫЕ", "ДАНИМИ") || baseSent.get(0).isRootValue("СООБЩЕНИЕ", "ПОВІДОМЛЕННЯМ") || baseSent.get(0).isRootValue("ИНФОРМАЦИЯ", "ІНФОРМАЦІЯ")))) {
            int ii;
            boolean ok = false;
            for(ii = 1; ii < (baseSent.size() - 1); ii++) {
                if (baseSent.get(ii).typ == Types.COMMA && baseSent.get(ii + 1).typ != Types.ACTPRICH) {
                    ok = true;
                    break;
                }
                else if (baseSent.get(ii).typ == Types.ACTPRICH || baseSent.get(ii).typ == Types.ACT) 
                    break;
            }
            if (ok) {
                java.util.ArrayList<SynToken> li = new java.util.ArrayList< >();
                slaveSents.add(li);
                for(int jj = ii + 1; jj < baseSent.size(); jj++) {
                    li.add(baseSent.get(jj));
                }
                for(int indRemoveRange = ii + baseSent.size() - ii - 1, indMinIndex = ii; indRemoveRange >= indMinIndex; indRemoveRange--) baseSent.remove(indRemoveRange);
                SynToken st = SynToken._new2480(baseSent.get(0).getBeginToken(), baseSent.get(baseSent.size() - 1).getEndToken(), Types.ACT);
                st.setBase((baseSent.get(0).getBeginToken().getMorph().getLanguage().isUa() ? "ПОВІДОМЛЯТИ" : "СООБЩАТЬ"));
                baseSent.add(st);
                return i;
            }
        }
        int ret = i;
        objs = 0;
        for(i = baseSent.size() - 1; i >= 0; i--) {
            if (baseSent.get(i).typ == Types.OBJ || baseSent.get(i).typ == Types.PROPERNAME) 
                objs++;
            else if (baseSent.get(i).typ == Types.ACT) {
                if (!baseSent.get(i).getActCanBeSpeech() || objs == 0) 
                    break;
                boolean ok = true;
                for(--i; i > 0; i--) {
                    if (baseSent.get(i).typ == Types.ADVERB || baseSent.get(i).typ == Types.EMPTY) {
                    }
                    else if (baseSent.get(i).typ == Types.COMMA) 
                        break;
                    else {
                        ok = false;
                        break;
                    }
                }
                if (ok && i > 0) {
                    java.util.ArrayList<SynToken> li = new java.util.ArrayList< >();
                    slaveSents.add(li);
                    for(int ii = 0; ii < i; ii++) {
                        li.add(baseSent.get(ii));
                    }
                    for(int indRemoveRange = 0 + i + 1 - 1, indMinIndex = 0; indRemoveRange >= indMinIndex; indRemoveRange--) baseSent.remove(indRemoveRange);
                    break;
                }
            }
            else if (baseSent.get(i).typ == Types.ACTPRICH) 
                break;
        }
        return ret;
    }
    public SentenceContainer() {
    }
}
