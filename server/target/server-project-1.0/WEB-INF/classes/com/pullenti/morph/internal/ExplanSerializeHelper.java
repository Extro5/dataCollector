/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.morph.internal;

public class ExplanSerializeHelper {

    public static void serializeDD(com.pullenti.n2j.Stream res, DerivateDictionary dic) throws java.io.IOException {
        try (com.pullenti.n2j.MemoryStream tmp = new com.pullenti.n2j.MemoryStream()) {
            serializeInt(tmp, dic.m_AllGroups.size());
            for(int i = 0; i < dic.m_AllGroups.size(); i++) {
                int p0 = (int)tmp.getPosition();
                serializeInt(tmp, 0);
                serializeDerivateGroup(tmp, dic.m_AllGroups.get(i));
                dic.m_AllGroups.get(i).tag = (i + 1);
                int p1 = (int)tmp.getPosition();
                tmp.seek((long)p0, 0);
                serializeInt(tmp, p1);
                tmp.seek((long)p1, 0);
            }
            serializeTreeNode(tmp, dic.m_Root);
            com.pullenti.n2j.GZipStream deflate = new com.pullenti.n2j.GZipStream(res, true);
            tmp.writeTo(deflate);
            deflate.close();
        }
    }

    public static void deserializeDD(com.pullenti.n2j.Stream str, DerivateDictionary dic, boolean lazyLoad) throws java.io.IOException {
        try (com.pullenti.n2j.MemoryStream tmp = new com.pullenti.n2j.MemoryStream()) {
            MorphSerializeHelper.deflateGzip(str, tmp);
            ByteArrayWrapper wr = new ByteArrayWrapper(tmp.toByteArray());
            int cou = wr.deserializeInt();
            for(; cou > 0; cou--) {
                int p1 = wr.deserializeInt();
                com.pullenti.morph.DerivateGroup ew = new com.pullenti.morph.DerivateGroup();
                if (lazyLoad) {
                    ew.lazy = LazyInfo2._new5(wr.getPosition(), wr, dic);
                    wr.seek(p1);
                }
                else 
                    deserializeDerivateGroup(wr, ew);
                dic.m_AllGroups.add(ew);
            }
            dic.m_Root = new ExplanTreeNode();
            deserializeTreeNode(wr, dic, dic.m_Root, lazyLoad);
        }
    }

    private static void serializeDerivateGroup(com.pullenti.n2j.Stream res, com.pullenti.morph.DerivateGroup dg) throws java.io.IOException {
        short attrs = (short)0;
        if (dg.isDummy) 
            attrs |= ((short)1);
        if (dg.notGenerate) 
            attrs |= ((short)2);
        if (dg.getTransitive() == 0) 
            attrs |= ((short)4);
        if (dg.getTransitive() == 1) 
            attrs |= ((short)8);
        serializeShort(res, (int)attrs);
        serializeString(res, dg.prefix);
        for(int i = dg.words.size() - 1; i >= 0; i--) {
            if (com.pullenti.n2j.Utils.isNullOrEmpty(dg.words.get(i).spelling)) 
                dg.words.remove(i);
        }
        serializeShort(res, dg.words.size());
        for(com.pullenti.morph.DerivateWord w : dg.words) {
            serializeString(res, w.spelling);
            serializeShort(res, (int)w._class.value);
            serializeShort(res, (int)w.lang.value);
            serializeShort(res, (int)w.attrs.value);
            serializeShort(res, (w.nexts == null ? 0 : w.nexts.size()));
            if (w.nexts != null) {
                for(java.util.Map.Entry<String, com.pullenti.morph.MorphCase> kp : w.nexts.entrySet()) {
                    serializeString(res, kp.getKey());
                    serializeShort(res, (int)kp.getValue().value);
                }
            }
        }
    }

    public static void deserializeDerivateGroup(ByteArrayWrapper str, com.pullenti.morph.DerivateGroup dg) {
        int attr = str.deserializeShort();
        if (((attr & 1)) != 0) 
            dg.isDummy = true;
        if (((attr & 2)) != 0) 
            dg.notGenerate = true;
        if (((attr & 4)) != 0) 
            dg.m_Transitive = 0;
        if (((attr & 8)) != 0) 
            dg.m_Transitive = 1;
        dg.prefix = str.deserializeString();
        int cou = str.deserializeShort();
        for(; cou > 0; cou--) {
            com.pullenti.morph.DerivateWord w = new com.pullenti.morph.DerivateWord(dg);
            w.spelling = str.deserializeString();
            w._class = new com.pullenti.morph.MorphClass(null);
            w._class.value = (short)str.deserializeShort();
            w.lang = com.pullenti.morph.MorphLang._new6((short)str.deserializeShort());
            w.attrs.value = (short)str.deserializeShort();
            int cou1 = str.deserializeShort();
            for(; cou1 > 0; cou1--) {
                String pref = (String)com.pullenti.n2j.Utils.notnull(str.deserializeString(), "");
                com.pullenti.morph.MorphCase cas = new com.pullenti.morph.MorphCase(null);
                cas.value = (short)str.deserializeShort();
                if (w.nexts == null) 
                    w.nexts = new java.util.HashMap< >();
                w.nexts.put(pref, cas);
            }
            dg.words.add(w);
        }
    }

    private static void serializeTreeNode(com.pullenti.n2j.Stream res, ExplanTreeNode tn) throws java.io.IOException {
        if (tn.groups == null) 
            serializeShort(res, 0);
        else if (tn.groups instanceof com.pullenti.morph.DerivateGroup) {
            serializeShort(res, 1);
            serializeInt(res, (int)(((com.pullenti.morph.DerivateGroup)com.pullenti.n2j.Utils.cast(tn.groups, com.pullenti.morph.DerivateGroup.class))).tag);
        }
        else {
            java.util.ArrayList<com.pullenti.morph.DerivateGroup> li = (java.util.ArrayList<com.pullenti.morph.DerivateGroup>)com.pullenti.n2j.Utils.cast(tn.groups, java.util.ArrayList.class);
            if (li != null) {
                serializeShort(res, li.size());
                for(com.pullenti.morph.DerivateGroup gr : li) {
                    serializeInt(res, (int)gr.tag);
                }
            }
            else 
                serializeShort(res, 0);
        }
        if (tn.nodes == null || tn.nodes.size() == 0) 
            serializeShort(res, 0);
        else {
            serializeShort(res, tn.nodes.size());
            for(java.util.Map.Entry<Short, ExplanTreeNode> n : tn.nodes.entrySet()) {
                serializeShort(res, (int)(short)n.getKey());
                int p0 = (int)res.getPosition();
                serializeInt(res, 0);
                serializeTreeNode(res, n.getValue());
                int p1 = (int)res.getPosition();
                res.setPosition((long)p0);
                serializeInt(res, p1);
                res.setPosition((long)p1);
            }
        }
    }

    public static void deserializeTreeNode(ByteArrayWrapper str, DerivateDictionary dic, ExplanTreeNode tn, boolean lazyLoad) {
        int cou = str.deserializeShort();
        java.util.ArrayList<com.pullenti.morph.DerivateGroup> li = (cou > 1 ? new java.util.ArrayList< >() : null);
        for(; cou > 0; cou--) {
            int id = str.deserializeInt();
            if (id > 0 && id <= dic.m_AllGroups.size()) {
                com.pullenti.morph.DerivateGroup gr = dic.m_AllGroups.get(id - 1);
                if (gr.lazy != null) {
                    int p0 = str.getPosition();
                    str.seek(gr.lazy.begin);
                    deserializeDerivateGroup(str, gr);
                    gr.lazy = null;
                    str.seek(p0);
                }
                if (li != null) 
                    li.add(gr);
                else 
                    tn.groups = gr;
            }
        }
        if (li != null) 
            tn.groups = li;
        cou = str.deserializeShort();
        if (cou == 0) 
            return;
        for(; cou > 0; cou--) {
            short ke = (short)str.deserializeShort();
            int p1 = str.deserializeInt();
            ExplanTreeNode tn1 = new ExplanTreeNode();
            if (tn.nodes == null) 
                tn.nodes = new java.util.HashMap< >();
            if (!tn.nodes.containsKey(ke)) 
                tn.nodes.put(ke, tn1);
            if (lazyLoad) {
                tn1.lazy = new LazyInfo2();
                tn1.lazy.begin = str.getPosition();
                tn1.lazy.data = str;
                tn1.lazy.dic = dic;
                str.seek(p1);
            }
            else 
                deserializeTreeNode(str, dic, tn1, false);
        }
    }

    private static void serializeByte(com.pullenti.n2j.Stream res, byte val) throws java.io.IOException {
        res.write(val);
    }

    private static void serializeShort(com.pullenti.n2j.Stream res, int val) throws java.io.IOException {
        res.write((byte)val);
        res.write((byte)((val >> 8)));
    }

    private static void serializeInt(com.pullenti.n2j.Stream res, int val) throws java.io.IOException {
        res.write((byte)val);
        res.write((byte)((val >> 8)));
        res.write((byte)((val >> 16)));
        res.write((byte)((val >> 24)));
    }

    private static void serializeString(com.pullenti.n2j.Stream res, String s) throws java.io.IOException {
        if (s == null) 
            res.write((byte)0xFF);
        else if (s.length() == 0) 
            res.write((byte)0);
        else {
            byte[] data = com.pullenti.n2j.Utils.encodeCharset(java.nio.charset.Charset.forName("UTF-8"), s);
            res.write((byte)data.length);
            res.write(data, 0, data.length);
        }
    }
    public ExplanSerializeHelper() {
    }
}
