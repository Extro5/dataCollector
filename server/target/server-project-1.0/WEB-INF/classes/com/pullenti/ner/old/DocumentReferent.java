/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.old;

public class DocumentReferent extends com.pullenti.ner.titlepage.TitlePageReferent {

    public DocumentReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.old.internal.MetaDocument.globalMeta);
    }

    public static final String OBJ_TYPENAME = "DOCUMENT";

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        return obj == this;
    }

    public java.util.ArrayList<DocumentBlockReferent> getChildren() {
        java.util.ArrayList<DocumentBlockReferent> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), DocumentBlockReferent.ATTR_CHILD)) {
                if (s.getValue() instanceof DocumentBlockReferent) 
                    res.add((DocumentBlockReferent)com.pullenti.n2j.Utils.cast(s.getValue(), DocumentBlockReferent.class));
            }
        }
        return res;
    }

}
