/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.repository;

/**
 * Элемент примера сущности в тексте
 */
public class RepositoryItemSample implements Comparable<Object> {

    public RepositoryItemSample(String txt, int begin, int end, boolean isRoot) {
        if (txt == null) 
            return;
        String bas = txt.substring(begin, (begin)+((end - begin) + 1));
        if ((bas.indexOf("\n") >= 0)) 
            bas = bas.replace('\n', ' ');
        if ((bas.indexOf("\r") >= 0)) 
            bas = bas.replace('\r', ' ');
        bodyPeace = bas;
        StringBuilder tmp = new StringBuilder();
        int cou = 0;
        int i;
        for(i = begin - 1; i > 0; i--) {
            if (Character.isLetterOrDigit(txt.charAt(i))) 
                cou++;
            else if (cou > 20) 
                break;
        }
        boolean sp = true;
        char ch = (char)0;
        if (i < 0) 
            i = 0;
        for(; i < begin; i++) {
            if (com.pullenti.n2j.Utils.isWhitespace(txt.charAt(i))) {
                if (sp) 
                    continue;
                tmp.append(' ');
                sp = true;
                continue;
            }
            sp = false;
            if (txt.charAt(i) != ch) 
                tmp.append(txt.charAt(i));
            ch = (char)0;
            if (txt.charAt(i) == '-' || txt.charAt(i) == '_') 
                ch = txt.charAt(i);
        }
        headPeace = tmp.toString();
        tmp.setLength(0);
        cou = 0;
        sp = false;
        for(i = end + 1; i < txt.length(); i++) {
            if (Character.isLetterOrDigit(txt.charAt(i))) 
                cou++;
            else if (cou > 20) 
                break;
            if (com.pullenti.n2j.Utils.isWhitespace(txt.charAt(i))) {
                if (sp) 
                    continue;
                tmp.append(' ');
                sp = true;
                continue;
            }
            sp = false;
            if (txt.charAt(i) != ch) 
                tmp.append(txt.charAt(i));
            ch = (char)0;
            if (txt.charAt(i) == '-' || txt.charAt(i) == '_') 
                ch = txt.charAt(i);
        }
        tailPeace = tmp.toString();
    }

    /**
     * Фрагмент перед
     */
    public String headPeace;

    /**
     * Сам фрагмент
     */
    public String bodyPeace;

    /**
     * Фрагмент после
     */
    public String tailPeace;

    /**
     * Признак того, что этот текстовой фрагмент был использован для "первого" выделения сущности, 
     *  а не привязки к ранее выделенному.
     */
    public boolean isEssential;

    @Override
    public String toString() {
        return bodyPeace;
    }

    /**
     * Представить в виде списка классов
     * @param samples 
     * @return 
     */
    public static java.util.ArrayList<RepositoryItemSample> deserialize(String samples) {
        if (com.pullenti.n2j.Utils.isNullOrEmpty(samples)) 
            return null;
        try {
            com.pullenti.n2j.XmlDocumentWrapper xml = new com.pullenti.n2j.XmlDocumentWrapper();
            xml.doc = xml.db.parse(new org.xml.sax.InputSource(new java.io.StringReader(samples)));
            java.util.ArrayList<RepositoryItemSample> res = new java.util.ArrayList< >();
            for(org.w3c.dom.Node x : (new com.pullenti.n2j.XmlNodeListWrapper(xml.doc.getDocumentElement().getChildNodes())).arr) {
                RepositoryItemSample s = new RepositoryItemSample(null, 0, 0, false);
                if (com.pullenti.n2j.Utils.getXmlAttrByName(x, "E") != null) 
                    s.isEssential = true;
                for(org.w3c.dom.Node xx : (new com.pullenti.n2j.XmlNodeListWrapper(x.getChildNodes())).arr) {
                    if (com.pullenti.n2j.Utils.stringsEq(xx.getNodeName(), "B")) 
                        s.bodyPeace = xx.getTextContent();
                    else if (com.pullenti.n2j.Utils.stringsEq(xx.getNodeName(), "H")) 
                        s.headPeace = xx.getTextContent();
                    else if (com.pullenti.n2j.Utils.stringsEq(xx.getNodeName(), "T")) 
                        s.tailPeace = xx.getTextContent();
                }
                res.add(s);
            }
            return res;
        } catch(Exception ex) {
            return null;
        }
    }

    public static String serialize(java.util.ArrayList<RepositoryItemSample> samples) throws javax.xml.stream.XMLStreamException {
        if (samples == null || samples.size() == 0) 
            return null;
        StringBuilder res = new StringBuilder();
        try (com.pullenti.n2j.XmlWriterWrapper xml = new com.pullenti.n2j.XmlWriterWrapper(res)) {
            xml.wr.writeStartElement("S");
            for(RepositoryItemSample s : samples) {
                xml.wr.writeStartElement("I");
                if (s.isEssential) 
                    xml.wr.writeAttribute("E", "true");
                if (!com.pullenti.n2j.Utils.isNullOrEmpty(s.headPeace)) {
                    xml.wr.writeStartElement("H");
                    xml.wr.writeCharacters(com.pullenti.ner.core.MiscHelper._corrXmlText(s.headPeace));
                    xml.wr.writeEndElement();
                }
                if (!com.pullenti.n2j.Utils.isNullOrEmpty(s.bodyPeace)) {
                    xml.wr.writeStartElement("B");
                    xml.wr.writeCharacters(com.pullenti.ner.core.MiscHelper._corrXmlText(s.bodyPeace));
                    xml.wr.writeEndElement();
                }
                if (!com.pullenti.n2j.Utils.isNullOrEmpty(s.tailPeace)) {
                    xml.wr.writeStartElement("T");
                    xml.wr.writeCharacters(com.pullenti.ner.core.MiscHelper._corrXmlText(s.tailPeace));
                    xml.wr.writeEndElement();
                }
                xml.wr.writeEndElement();
            }
            xml.wr.writeEndElement();
        }
        int i = res.toString().indexOf('>');
        if (i > 10 && res.charAt(1) == '?') 
            res.delete(0, 0+(i + 1));
        for(i = 0; i < res.length(); i++) {
            char ch = res.charAt(i);
            int cod = (int)ch;
            if ((cod < 0x80) && cod >= 0x20) 
                continue;
            if (com.pullenti.morph.LanguageHelper.isCyrillicChar(ch)) 
                continue;
            res.delete(i, (i)+1);
            res.insert(i, "&#x" + String.format("%04X", cod) + ";");
        }
        return res.toString();
    }

    @Override
    public int compareTo(Object obj) {
        RepositoryItemSample s = (RepositoryItemSample)com.pullenti.n2j.Utils.cast(obj, RepositoryItemSample.class);
        if (isEssential != s.isEssential) 
            return (isEssential ? -1 : 1);
        int i = bodyPeace.length() - s.bodyPeace.length();
        if (i > 0) 
            return -1;
        if (i < 0) 
            return 1;
        return com.pullenti.n2j.Utils.stringsCompare((String)com.pullenti.n2j.Utils.notnull(bodyPeace, ""), (String)com.pullenti.n2j.Utils.notnull(s.bodyPeace, ""), false);
    }
    public RepositoryItemSample() {
        this(null, 0, 0, false);
    }
}
