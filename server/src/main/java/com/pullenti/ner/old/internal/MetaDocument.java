/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.old.internal;

public class MetaDocument extends com.pullenti.ner.titlepage.internal.MetaTitleInfo {

    public MetaDocument() {
        super();
        addFeature(com.pullenti.ner.old.DocumentBlockReferent.ATTR_CHILD, "Внутренний блок", 0, 0);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.old.DocumentReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Документ";
    }


    public static String DOCIMAGEID = "doc";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return DOCIMAGEID;
    }

    public static MetaDocument globalMeta = new MetaDocument();
}
