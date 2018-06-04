/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.old;

/**
 * Блок документа (часть, глава или весь документ целиком)
 */
public class DocumentBlockReferent extends com.pullenti.ner.Referent {

    public DocumentBlockReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.old.internal.MetaDocBlockInfo.globalMeta);
    }

    public static final String OBJ_TYPENAME = "DOCBLOCK";

    public static final String ATTR_NAME = "NAME";

    public static final String ATTR_NUMBER = "NUMBER";

    public static final String ATTR_CONTENT = "CONTENT";

    public static final String ATTR_PARENT = "PARENT";

    public static final String ATTR_CHILD = "CHILD";

    public static final String ATTR_TYPE = "TYPE";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        String str;
        if ((((str = getStringValue(ATTR_NUMBER)))) != null) 
            res.append(str).append(") ");
        if (getTyp() != DocumentBlockType.UNDEFINED) 
            res.append(getTyp().toString()).append(": ");
        if ((((str = getStringValue(ATTR_NAME)))) != null) 
            res.append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(str));
        else if ((((str = getStringValue(ATTR_CONTENT)))) != null) {
            boolean sp = true;
            for(char ch : str.toCharArray()) {
                if (com.pullenti.n2j.Utils.isWhitespace(ch)) {
                    if (res.length() > 100) {
                        res.append("...");
                        break;
                    }
                    if (!sp) {
                        res.append(' ');
                        sp = true;
                    }
                }
                else {
                    res.append(ch);
                    sp = false;
                }
            }
        }
        if (res.length() == 0) 
            res.append("?");
        return res.toString();
    }

    @Override
    public com.pullenti.ner.Referent getParentReferent() {
        return (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(getValue(ATTR_PARENT), com.pullenti.ner.Referent.class);
    }


    public void addParent(com.pullenti.ner.Referent value) {
        if (value == getParentReferent()) 
            return;
        addSlot(ATTR_PARENT, value, true, 0);
        if (value != null) 
            value.addSlot(ATTR_CHILD, this, false, 0);
    }

    public java.util.ArrayList<DocumentBlockReferent> getChildren() {
        java.util.ArrayList<DocumentBlockReferent> res = new java.util.ArrayList< >();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_CHILD)) {
                if (s.getValue() instanceof DocumentBlockReferent) 
                    res.add((DocumentBlockReferent)com.pullenti.n2j.Utils.cast(s.getValue(), DocumentBlockReferent.class));
            }
        }
        return res;
    }


    public DocumentBlockType getTyp() {
        String s = getStringValue(ATTR_TYPE);
        if (s == null) 
            return DocumentBlockType.UNDEFINED;
        try {
            Object res = DocumentBlockType.of(s);
            if (res instanceof DocumentBlockType) 
                return (DocumentBlockType)res;
        } catch(Exception ex1604) {
        }
        return DocumentBlockType.UNDEFINED;
    }

    public DocumentBlockType setTyp(DocumentBlockType value) {
        if (value == DocumentBlockType.UNDEFINED) 
            addSlot(ATTR_TYPE, null, true, 0);
        else 
            addSlot(ATTR_TYPE, value.toString(), true, 0);
        return value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType _typ) {
        return obj == this;
    }

    public static DocumentBlockReferent _new1605(DocumentBlockType _arg1) {
        DocumentBlockReferent res = new DocumentBlockReferent();
        res.setTyp(_arg1);
        return res;
    }
}
