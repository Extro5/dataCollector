/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.decree.internal;

public class MetaDecreePart extends com.pullenti.ner.ReferentClass {

    public MetaDecreePart() {
        super();
        addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_NAME, "Наименование", 0, 0);
        addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_OWNER, "Владелец", 0, 1);
        addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_LOCALTYP, "Локальный тип", 0, 1);
        addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_SECTION, "Раздел", 0, 1);
        addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_SUBSECTION, "Подраздел", 0, 1);
        addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_APPENDIX, "Приложение", 0, 1);
        addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_CHAPTER, "Глава", 0, 1);
        addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_PREAMBLE, "Преамбула", 0, 1);
        addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_CLAUSE, "Статья", 0, 1);
        addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_PART, "Часть", 0, 1);
        addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_DOCPART, "Часть документа", 0, 1);
        addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_PARAGRAPH, "Параграф", 0, 1);
        addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_SUBPARAGRAPH, "Подпараграф", 0, 1);
        addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_ITEM, "Пункт", 0, 1);
        addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_SUBITEM, "Подпункт", 0, 1);
        addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_INDENTION, "Абзац", 0, 1);
        addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_SUBINDENTION, "Подабзац", 0, 1);
        addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_SUBPROGRAM, "Подпрограмма", 0, 1);
        addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_ADDAGREE, "Допсоглашение", 0, 1);
        addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_NOTICE, "Примечание", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.decree.DecreeReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Ссылка на часть НПА";
    }


    public static String PARTIMAGEID = "part";

    public static String PARTLOCIMAGEID = "partloc";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        com.pullenti.ner.decree.DecreePartReferent dpr = (com.pullenti.ner.decree.DecreePartReferent)com.pullenti.n2j.Utils.cast(obj, com.pullenti.ner.decree.DecreePartReferent.class);
        if (dpr != null) {
            if (dpr.getOwner() == null) 
                return PARTLOCIMAGEID;
        }
        return PARTIMAGEID;
    }

    public static MetaDecreePart GLOBALMETA = new MetaDecreePart();
}
