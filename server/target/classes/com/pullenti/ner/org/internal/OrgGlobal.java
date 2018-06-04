/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.org.internal;

public class OrgGlobal {

    public static com.pullenti.ner.core.IntOntologyCollection GLOBALORGS = null;

    public static com.pullenti.ner.core.IntOntologyCollection GLOBALORGSUA = new com.pullenti.ner.core.IntOntologyCollection();

    public static void initialize() throws Exception, java.io.IOException, javax.xml.stream.XMLStreamException, org.xml.sax.SAXException {
        if (GLOBALORGS != null) 
            return;
        GLOBALORGS = new com.pullenti.ner.core.IntOntologyCollection();
        com.pullenti.ner.org.OrganizationReferent _org;
        com.pullenti.ner.core.IntOntologyItem oi;
        try (com.pullenti.ner.Processor geoProc = com.pullenti.ner.ProcessorService.createEmptyProcessor()) {
            geoProc.addAnalyzer(new com.pullenti.ner.geo.GeoAnalyzer());
            java.util.HashMap<String, com.pullenti.ner.geo.GeoReferent> geos = new java.util.HashMap< >();
            for(int k = 0; k < 3; k++) {
                com.pullenti.morph.MorphLang lang = (k == 0 ? com.pullenti.morph.MorphLang.RU : (k == 1 ? com.pullenti.morph.MorphLang.EN : com.pullenti.morph.MorphLang.UA));
                String name = (k == 0 ? "Orgs_ru.dat" : (k == 1 ? "Orgs_en.dat" : "Orgs_ua.dat"));
                byte[] dat = ResourceHelper.getBytes(name);
                if (dat == null) 
                    throw new Exception("Can't file resource file " + name + " in Organization analyzer");
                try (com.pullenti.n2j.MemoryStream tmp = new com.pullenti.n2j.MemoryStream(OrgItemTypeToken.deflate(dat))) {
                    tmp.setPosition((long)0);
                    com.pullenti.n2j.XmlDocumentWrapper xml = new com.pullenti.n2j.XmlDocumentWrapper();
                    xml.load(tmp);
                    for(org.w3c.dom.Node x : (new com.pullenti.n2j.XmlNodeListWrapper(xml.doc.getDocumentElement().getChildNodes())).arr) {
                        _org = new com.pullenti.ner.org.OrganizationReferent();
                        String abbr = null;
                        for(org.w3c.dom.Node xx : (new com.pullenti.n2j.XmlNodeListWrapper(x.getChildNodes())).arr) {
                            if (com.pullenti.n2j.Utils.stringsEq(com.pullenti.n2j.XmlDocumentWrapper.getLocalName(xx), "typ")) 
                                _org.addSlot(com.pullenti.ner.org.OrganizationReferent.ATTR_TYPE, xx.getTextContent(), false, 0);
                            else if (com.pullenti.n2j.Utils.stringsEq(com.pullenti.n2j.XmlDocumentWrapper.getLocalName(xx), "nam")) 
                                _org.addSlot(com.pullenti.ner.org.OrganizationReferent.ATTR_NAME, xx.getTextContent(), false, 0);
                            else if (com.pullenti.n2j.Utils.stringsEq(com.pullenti.n2j.XmlDocumentWrapper.getLocalName(xx), "epo")) 
                                _org.addSlot(com.pullenti.ner.org.OrganizationReferent.ATTR_EPONYM, xx.getTextContent(), false, 0);
                            else if (com.pullenti.n2j.Utils.stringsEq(com.pullenti.n2j.XmlDocumentWrapper.getLocalName(xx), "prof")) 
                                _org.addSlot(com.pullenti.ner.org.OrganizationReferent.ATTR_PROFILE, xx.getTextContent(), false, 0);
                            else if (com.pullenti.n2j.Utils.stringsEq(com.pullenti.n2j.XmlDocumentWrapper.getLocalName(xx), "abbr")) 
                                abbr = xx.getTextContent();
                            else if (com.pullenti.n2j.Utils.stringsEq(com.pullenti.n2j.XmlDocumentWrapper.getLocalName(xx), "geo")) {
                                com.pullenti.ner.geo.GeoReferent _geo;
                                com.pullenti.n2j.Outargwrapper<com.pullenti.ner.geo.GeoReferent> inoutarg1606 = new com.pullenti.n2j.Outargwrapper< >();
                                Boolean inoutres1607 = com.pullenti.n2j.Utils.tryGetValue(geos, xx.getTextContent(), inoutarg1606);
                                _geo = inoutarg1606.value;
                                if (!inoutres1607) {
                                    com.pullenti.ner.AnalysisResult ar = geoProc.process(new com.pullenti.ner.SourceOfAnalysis(xx.getTextContent()), null, lang);
                                    if (ar != null && ar.getEntities().size() == 1 && (ar.getEntities().get(0) instanceof com.pullenti.ner.geo.GeoReferent)) {
                                        _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(ar.getEntities().get(0), com.pullenti.ner.geo.GeoReferent.class);
                                        geos.put(xx.getTextContent(), _geo);
                                    }
                                    else {
                                    }
                                }
                                if (_geo != null) 
                                    _org.addSlot(com.pullenti.ner.org.OrganizationReferent.ATTR_GEO, _geo, false, 0);
                            }
                        }
                        oi = _org.createOntologyItemEx(2, true, true);
                        if (oi == null) 
                            continue;
                        if (abbr != null) 
                            oi.termins.add(new com.pullenti.ner.core.Termin(abbr, new com.pullenti.morph.MorphLang(null), false));
                        if (k == 2) 
                            GLOBALORGSUA.addItem(oi);
                        else 
                            GLOBALORGS.addItem(oi);
                    }
                }
            }
        }
        return;
    }
    public OrgGlobal() {
    }
}
