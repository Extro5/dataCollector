package com.pullenti.n2j;


public class XmlAttrListWrapper {
    public java.util.ArrayList<org.w3c.dom.Attr> arr;
    public org.w3c.dom.NamedNodeMap nl;
    public XmlAttrListWrapper(org.w3c.dom.NamedNodeMap src) { 
        nl = src; 
        arr = new java.util.ArrayList<>(); 
        for(int i = 0; i < nl.getLength(); i++) arr.add((org.w3c.dom.Attr)nl.item(i)); 
    }
}
