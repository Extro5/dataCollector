/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.person.internal;

public class MetaPersonIdentity extends com.pullenti.ner.ReferentClass {

    public MetaPersonIdentity() {
        super();
        addFeature(com.pullenti.ner.person.PersonIdentityReferent.ATTR_TYPE, "Тип", 1, 1);
        addFeature(com.pullenti.ner.person.PersonIdentityReferent.ATTR_NUMBER, "Номер", 1, 1);
        addFeature(com.pullenti.ner.person.PersonIdentityReferent.ATTR_DATE, "Дата выдачи", 0, 1);
        addFeature(com.pullenti.ner.person.PersonIdentityReferent.ATTR_ORG, "Кто выдал", 0, 1);
        addFeature(com.pullenti.ner.person.PersonIdentityReferent.ATTR_ADDRESS, "Адрес регистрации", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.person.PersonIdentityReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Удостоверение личности";
    }


    public static String IMAGEID = "identity";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return IMAGEID;
    }

    public static MetaPersonIdentity globalMeta = new MetaPersonIdentity();
}
