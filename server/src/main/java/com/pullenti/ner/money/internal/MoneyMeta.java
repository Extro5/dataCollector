/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.money.internal;

public class MoneyMeta extends com.pullenti.ner.ReferentClass {

    public MoneyMeta() {
        super();
        addFeature(com.pullenti.ner.money.MoneyReferent.ATTR_CURRENCY, "Валюта", 1, 1);
        addFeature(com.pullenti.ner.money.MoneyReferent.ATTR_VALUE, "Значение", 1, 1);
        addFeature(com.pullenti.ner.money.MoneyReferent.ATTR_REST, "Остаток (100)", 0, 1);
        addFeature(com.pullenti.ner.money.MoneyReferent.ATTR_ALTVALUE, "Другое значение", 1, 1);
        addFeature(com.pullenti.ner.money.MoneyReferent.ATTR_ALTREST, "Другой остаток (100)", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.money.MoneyReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Денежная сумма";
    }


    public static String IMAGEID = "sum";

    public static String IMAGE2ID = "sumerr";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        com.pullenti.ner.money.MoneyReferent m = (com.pullenti.ner.money.MoneyReferent)com.pullenti.n2j.Utils.cast(obj, com.pullenti.ner.money.MoneyReferent.class);
        if (m != null) {
            if (m.getAltValue() != null || m.getAltRest() != null) 
                return IMAGE2ID;
        }
        return IMAGEID;
    }

    public static MoneyMeta GLOBALMETA = new MoneyMeta();
}
