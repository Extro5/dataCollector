/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature.internal;

public class MergeContainer {

    public static class CharacterPair {
    
        public com.pullenti.ner.literature.internal.CharacterEx char1;
    
        public com.pullenti.ner.literature.internal.CharacterEx char2;
    
        public int count;
    
        @Override
        public String toString() {
            return ((Integer)count).toString() + ": " + char1.toString(true, new com.pullenti.morph.MorphLang(null), 0) + " + " + char2.toString(true, new com.pullenti.morph.MorphLang(null), 0);
        }
    
        public static CharacterPair _new1536(com.pullenti.ner.literature.internal.CharacterEx _arg1, com.pullenti.ner.literature.internal.CharacterEx _arg2, int _arg3) {
            CharacterPair res = new CharacterPair();
            res.char1 = _arg1;
            res.char2 = _arg2;
            res.count = _arg3;
            return res;
        }
        public CharacterPair() {
        }
    }


    public java.util.ArrayList<CharacterPair> pairs = new java.util.ArrayList< >();

    public void add(CharacterEx ch1, CharacterEx ch2) {
        if (ch1 == ch2) 
            return;
        for(CharacterPair cp : pairs) {
            if (((cp.char1 == ch1 && cp.char2 == ch2)) || ((cp.char2 == ch1 && cp.char1 == ch2))) {
                cp.count++;
                return;
            }
        }
        pairs.add(CharacterPair._new1536(ch1, ch2, 1));
    }

    public void del(CharacterEx ch) {
        for(int i = pairs.size() - 1; i >= 0; i--) {
            if (pairs.get(i).char1 == ch || pairs.get(i).char2 == ch) 
                pairs.remove(i);
        }
    }

    public java.util.ArrayList<CharacterEx> getOthers(CharacterEx ch) {
        java.util.ArrayList<CharacterEx> res = null;
        for(CharacterPair p : pairs) {
            if (p.char1 == ch) {
                if (res == null) 
                    res = new java.util.ArrayList< >();
                if (!res.contains(p.char2)) 
                    res.add(p.char2);
            }
            else if (p.char2 == ch) {
                if (res == null) 
                    res = new java.util.ArrayList< >();
                if (!res.contains(p.char1)) 
                    res.add(p.char1);
            }
        }
        return res;
    }

    public static void mergeDublicates(java.util.ArrayList<CharacterEx> chars) {
        java.util.ArrayList<CharacterEx> eq = new java.util.ArrayList< >();
        for(int i = 0; i < (chars.size() - 1); i++) {
            if (chars.get(i).real == null) {
                for(int j = i + 1; j < chars.size(); j++) {
                    if (chars.get(j).real == null && chars.get(j).isFullEquivalentWith(chars.get(i))) {
                        chars.get(i).mergeWith(chars.get(j));
                        chars.remove(j);
                        j--;
                    }
                }
            }
        }
        for(CharacterEx ch : chars) {
            if (ch.real != null) 
                continue;
            while(true) {
                eq.clear();
                for(CharacterEx r : chars) {
                    if (r == ch || r.real != null) 
                        continue;
                    if (r.canBeMergedByResult(ch)) {
                        if (!r.canNotBeEqual(ch)) 
                            eq.add(r);
                    }
                }
                if (eq.size() >= 1) {
                    boolean ok = true;
                    for(int i = 0; i < (eq.size() - 1); i++) {
                        for(int j = i + 1; j < eq.size(); j++) {
                            if (ok) {
                                if (!eq.get(i).canBeMergedByResult(eq.get(j))) 
                                    ok = false;
                                else if (eq.get(i).canNotBeEqual(eq.get(j))) 
                                    ok = false;
                            }
                        }
                    }
                    if (ok) {
                        for(CharacterEx e : eq) {
                            ch.mergeWith(e);
                        }
                        continue;
                    }
                }
                break;
            }
        }
        for(int i = chars.size() - 1; i >= 0; i--) {
            if (chars.get(i).real != null) 
                chars.remove(i);
        }
    }
    public MergeContainer() {
    }
    public static MergeContainer _globalInstance;
    static {
        _globalInstance = new MergeContainer(); 
    }
}
