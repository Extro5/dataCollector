/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.org.internal;

public class MetaOrganization extends com.pullenti.ner.ReferentClass {

    public MetaOrganization() {
        super();
        addFeature(com.pullenti.ner.org.OrganizationReferent.ATTR_NAME, "Название", 0, 0);
        addFeature(com.pullenti.ner.org.OrganizationReferent.ATTR_TYPE, "Тип", 0, 0);
        addFeature(com.pullenti.ner.org.OrganizationReferent.ATTR_EPONYM, "Эпоним (имени)", 0, 0);
        addFeature(com.pullenti.ner.org.OrganizationReferent.ATTR_NUMBER, "Номер", 0, 1);
        addFeature(com.pullenti.ner.org.OrganizationReferent.ATTR_HIGHER, "Вышестоящая организация", 0, 1);
        addFeature(com.pullenti.ner.org.OrganizationReferent.ATTR_OWNER, "Объект-владелец", 0, 1);
        addFeature(com.pullenti.ner.org.OrganizationReferent.ATTR_GEO, "Географический объект", 0, 1);
        addFeature(com.pullenti.ner.Referent.ATTR_GENERAL, "Обобщающая организация", 0, 1);
        addFeature(com.pullenti.ner.org.OrganizationReferent.ATTR_KLADR, "Код КЛАДР", 0, 1);
        addFeature(com.pullenti.ner.org.OrganizationReferent.ATTR_MISC, "Разное", 0, 0);
        addFeature(com.pullenti.ner.org.OrganizationReferent.ATTR_PROFILE, "Профиль", 0, 0);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.org.OrganizationReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Организация";
    }


    public static String ORGIMAGEID = "org";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        if (obj instanceof com.pullenti.ner.org.OrganizationReferent) {
            java.util.ArrayList<com.pullenti.ner.org.OrgProfile> prs = (((com.pullenti.ner.org.OrganizationReferent)com.pullenti.n2j.Utils.cast(obj, com.pullenti.ner.org.OrganizationReferent.class))).getProfiles();
            if (prs != null && prs.size() > 0) {
                com.pullenti.ner.org.OrgProfile pr = prs.get(prs.size() - 1);
                return pr.toString();
            }
        }
        return ORGIMAGEID;
    }

    public static MetaOrganization globalMeta = new MetaOrganization();
}
