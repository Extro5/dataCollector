/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.uri.internal;

public class MetaUri extends com.pullenti.ner.ReferentClass {

    public MetaUri() {
        super();
        addFeature(com.pullenti.ner.uri.UriReferent.ATTR_VALUE, "Значение", 0, 1);
        addFeature(com.pullenti.ner.uri.UriReferent.ATTR_SCHEME, "Схема", 0, 1);
        addFeature(com.pullenti.ner.uri.UriReferent.ATTR_DETAIL, "Детализация", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.uri.UriReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "URI";
    }


    public static String MAILIMAGEID = "mail";

    public static String URIIMAGEID = "uri";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        com.pullenti.ner.uri.UriReferent web = (com.pullenti.ner.uri.UriReferent)com.pullenti.n2j.Utils.cast(obj, com.pullenti.ner.uri.UriReferent.class);
        if (web != null && com.pullenti.n2j.Utils.stringsEq(web.getScheme(), "mailto")) 
            return MAILIMAGEID;
        else 
            return URIIMAGEID;
    }

    public static MetaUri globalMeta = new MetaUri();
}
