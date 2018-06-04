package com.pullenti.n2j;


public class XmlWriterWrapper implements AutoCloseable {
    public javax.xml.stream.XMLStreamWriter wr;
    private java.io.FileOutputStream str;
    private java.io.CharArrayWriter saw;
    private StringBuilder sbres;
    private FileStream fstr;
    private String enc;

    public XmlWriterWrapper(String fname, String encoding) throws java.io.FileNotFoundException, javax.xml.stream.XMLStreamException { 
        str = new java.io.FileOutputStream(fname); 
        javax.xml.stream.XMLOutputFactory fact = javax.xml.stream.XMLOutputFactory.newInstance(); 
        //fact.setProperty("indent", "true"); 
        wr = fact.createXMLStreamWriter(str, encoding); 
    }
    public XmlWriterWrapper(StringBuilder res) throws javax.xml.stream.XMLStreamException {
        saw = new java.io.CharArrayWriter();
        javax.xml.stream.XMLOutputFactory fact = javax.xml.stream.XMLOutputFactory.newInstance(); 
        wr = fact.createXMLStreamWriter(saw);
        sbres = res;
    }
    public XmlWriterWrapper(FileStream fs, String encoding) throws java.io.FileNotFoundException, javax.xml.stream.XMLStreamException { 
        fstr = fs; enc = encoding;
        saw = new java.io.CharArrayWriter();
        javax.xml.stream.XMLOutputFactory fact = javax.xml.stream.XMLOutputFactory.newInstance(); 
        //fact.setProperty("indent", "true"); 
        wr = fact.createXMLStreamWriter(saw); 
    }
    @Override
    public void close() {
        try { 
            if(wr != null) wr.close(); wr = null; 
            if(str != null) str.close(); str = null; 
            if(sbres != null) {
                if(saw != null) {
                    sbres.append(saw);
                    saw.close(); saw = null;
				}
            }
            if(fstr != null) {
            	if(saw != null) {
            		String txt = saw.toString(); 
            		saw.close(); saw = null;
            		byte[] arr = Utils.encodeCharset(Utils.getCharsetByName(enc), txt);
            		fstr.write(arr, 0, arr.length);
            	}
            }
        } catch(Exception ee) { 
		    System.out.println(ee);
		} 
    }

}
