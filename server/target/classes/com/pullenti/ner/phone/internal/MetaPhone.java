/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.phone.internal;

public class MetaPhone extends com.pullenti.ner.ReferentClass {

    public MetaPhone() {
        super();
        addFeature(com.pullenti.ner.phone.PhoneReferent.ATTR_NUNBER, "Номер", 1, 1);
        addFeature(com.pullenti.ner.phone.PhoneReferent.ATTR_ADDNUMBER, "Добавочный номер", 0, 1);
        addFeature(com.pullenti.ner.phone.PhoneReferent.ATTR_COUNTRYCODE, "Код страны", 0, 1);
        addFeature(com.pullenti.ner.phone.PhoneReferent.ATTR_GENERAL, "Обобщающий номер", 0, 1);
        addFeature(com.pullenti.ner.phone.PhoneReferent.ATTR_KIND, "Тип", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.phone.PhoneReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Телефонный номер";
    }


    public static String PHONEIMAGEID = "phone";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return PHONEIMAGEID;
    }

    public static MetaPhone globalMeta = new MetaPhone();
}
