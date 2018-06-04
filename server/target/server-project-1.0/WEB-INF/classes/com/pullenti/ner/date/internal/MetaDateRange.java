/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.date.internal;

public class MetaDateRange extends com.pullenti.ner.ReferentClass {

    public MetaDateRange() {
        super();
        addFeature(com.pullenti.ner.date.DateRangeReferent.ATTR_FROM, "Начало периода", 0, 1);
        addFeature(com.pullenti.ner.date.DateRangeReferent.ATTR_TO, "Конец периода", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.date.DateRangeReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Период";
    }


    public static String DATERANGEIMAGEID = "daterange";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return DATERANGEIMAGEID;
    }

    public static MetaDateRange GLOBALMETA = new MetaDateRange();
}
