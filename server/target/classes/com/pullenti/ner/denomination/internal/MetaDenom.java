/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.denomination.internal;

public class MetaDenom extends com.pullenti.ner.ReferentClass {

    public MetaDenom() {
        super();
        addFeature(com.pullenti.ner.denomination.DenominationReferent.ATTR_VALUE, "Значение", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.denomination.DenominationReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Обозначение";
    }


    public static String DENOMIMAGEID = "denom";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return DENOMIMAGEID;
    }

    public static MetaDenom globalMeta = new MetaDenom();
}
