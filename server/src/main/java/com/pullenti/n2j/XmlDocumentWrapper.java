package com.pullenti.n2j;


public class XmlDocumentWrapper {
    public javax.xml.parsers.DocumentBuilder db;
    public org.w3c.dom.Document doc;
    public XmlDocumentWrapper() { 
        javax.xml.parsers.DocumentBuilderFactory fact = javax.xml.parsers.DocumentBuilderFactory.newInstance(); 
        try { db = fact.newDocumentBuilder(); } catch(Exception e) { }
    }
    public void load(Stream str) throws org.xml.sax.SAXException, java.io.IOException {
        if(str instanceof MemoryStream) {
            doc = db.parse(((MemoryStream)str).toInputStream());
            return;
        }
        byte[] buf = new byte[10000];
        MemoryStream mem = new MemoryStream();
        while(true) {
        	int i = str.read(buf, 0, buf.length);
        	if(i <= 0) break;
        	if(i > 0) mem.write(buf, 0, i);
        }
        doc = db.parse(mem.toInputStream());
        mem.close();
    }

    public static String getLocalName(org.w3c.dom.Node nod) {
    	if(nod == null) return null;
    	String res = nod.getNodeName(); if(res == null) return null;
    	for(int i = res.length() - 1; i >=0; i--)
    		if(res.charAt(i) == ':') 
    			return res.substring(i + 1);
    	return res;
    }


}
