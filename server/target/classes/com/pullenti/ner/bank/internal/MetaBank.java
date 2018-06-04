/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.bank.internal;

public class MetaBank extends com.pullenti.ner.ReferentClass {

    public MetaBank() {
        super();
        addFeature(com.pullenti.ner.bank.BankDataReferent.ATTR_ITEM, "Элемент", 0, 0).setShowAsParent(true);
        addFeature(com.pullenti.ner.bank.BankDataReferent.ATTR_BANK, "Банк", 0, 1);
        addFeature(com.pullenti.ner.bank.BankDataReferent.ATTR_CORBANK, "Банк К/С", 0, 1);
        addFeature(com.pullenti.ner.bank.BankDataReferent.ATTR_MISC, "Разное", 0, 0);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.bank.BankDataReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Банковские реквизиты";
    }


    public static String IMAGEID = "bankreq";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return IMAGEID;
    }

    public static MetaBank globalMeta = new MetaBank();
}
