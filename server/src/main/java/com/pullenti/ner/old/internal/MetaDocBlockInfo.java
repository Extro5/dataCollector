/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.old.internal;

public class MetaDocBlockInfo extends com.pullenti.ner.ReferentClass {

    public MetaDocBlockInfo() {
        super();
        addFeature(com.pullenti.ner.old.DocumentBlockReferent.ATTR_NAME, "Название", 0, 0);
        addFeature(com.pullenti.ner.old.DocumentBlockReferent.ATTR_TYPE, "Тип", 0, 0);
        addFeature(com.pullenti.ner.old.DocumentBlockReferent.ATTR_CONTENT, "Содержимое (текст)", 0, 0);
        addFeature(com.pullenti.ner.old.DocumentBlockReferent.ATTR_PARENT, "Владелец", 0, 1);
        addFeature(com.pullenti.ner.old.DocumentBlockReferent.ATTR_CHILD, "Внутренний блок", 0, 0);
        addFeature(com.pullenti.ner.old.DocumentBlockReferent.ATTR_NUMBER, "Номер", 0, 0);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.old.DocumentBlockReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Блок документа";
    }


    public static String BLOCKIMAGEID = "block_text";

    public static String DOCIMAGEID = "block_doc";

    public static String CHAPTERIMAGEID = "block_parent";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        com.pullenti.ner.old.DocumentBlockReferent db = (com.pullenti.ner.old.DocumentBlockReferent)com.pullenti.n2j.Utils.cast(obj, com.pullenti.ner.old.DocumentBlockReferent.class);
        if (db == null) 
            return BLOCKIMAGEID;
        if (db.findSlot(com.pullenti.ner.old.DocumentBlockReferent.ATTR_PARENT, null, true) == null) 
            return DOCIMAGEID;
        if (db.findSlot(com.pullenti.ner.old.DocumentBlockReferent.ATTR_CONTENT, null, true) != null) 
            return BLOCKIMAGEID;
        return CHAPTERIMAGEID;
    }

    public static MetaDocBlockInfo globalMeta = new MetaDocBlockInfo();
}
