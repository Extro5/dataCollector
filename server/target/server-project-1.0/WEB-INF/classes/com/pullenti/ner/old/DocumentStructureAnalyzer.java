/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.old;

public class DocumentStructureAnalyzer extends com.pullenti.ner.Analyzer {

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    public static final String ANALYZER_NAME = "DOCSTRUCT";

    @Override
    public String getCaption() {
        return "Структура документа";
    }


    @Override
    public String getDescription() {
        return "Разбор структуры документа на разделы и подразделы";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new DocumentStructureAnalyzer();
    }

    private com.pullenti.ner.titlepage.TitlePageAnalyzer m_TitlePageAnalyzer = new com.pullenti.ner.titlepage.TitlePageAnalyzer();

    /**
     * Этот анализатор является специфическим
     */
    @Override
    public boolean isSpecific() {
        return true;
    }


    @Override
    public int getProgressWeight() {
        return 1;
    }


    @Override
    public java.util.Collection<com.pullenti.ner.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.ReferentClass[] {com.pullenti.ner.old.internal.MetaDocBlockInfo.globalMeta, com.pullenti.ner.old.internal.MetaDocument.globalMeta});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap< >();
        res.put(com.pullenti.ner.old.internal.MetaDocBlockInfo.BLOCKIMAGEID, com.pullenti.ner.booklink.internal.ResourceHelper.getBytes("block_text.png"));
        res.put(com.pullenti.ner.old.internal.MetaDocBlockInfo.DOCIMAGEID, com.pullenti.ner.booklink.internal.ResourceHelper.getBytes("block_doc.png"));
        res.put(com.pullenti.ner.old.internal.MetaDocBlockInfo.CHAPTERIMAGEID, com.pullenti.ner.booklink.internal.ResourceHelper.getBytes("block_parent.png"));
        res.put(com.pullenti.ner.old.internal.MetaDocument.DOCIMAGEID, com.pullenti.ner.booklink.internal.ResourceHelper.getBytes("block_doc.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.n2j.Utils.stringsEq(type, DocumentBlockReferent.OBJ_TYPENAME)) 
            return new DocumentBlockReferent();
        if (com.pullenti.n2j.Utils.stringsEq(type, DocumentReferent.OBJ_TYPENAME)) 
            return new DocumentReferent();
        return null;
    }

    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        com.pullenti.ner.core.AnalyzerData ad = kit.getAnalyzerData(this);
        com.pullenti.ner.old.internal.DocStructItem ogl = null;
        java.util.ArrayList<com.pullenti.ner.old.internal.DocStructItem> items = new java.util.ArrayList< >();
        com.pullenti.ner.Token lastToken = null;
        for(com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            lastToken = t;
            com.pullenti.ner.old.internal.DocStructItem ds = com.pullenti.ner.old.internal.DocStructItem.tryAttach(t, null, false);
            if (ds == null) 
                continue;
            if (ds.typ == com.pullenti.ner.old.internal.DocStructItem.Typs.INDEX) {
                if (ds.contentItems != null) 
                    ogl = ds;
            }
            else 
                items.add(ds);
        }
        com.pullenti.ner.ReferentToken tr = null;
        com.pullenti.ner.ReferentToken tail = null;
        if (ogl != null) {
            if (ogl.beginChar < (lastToken.endChar / 3)) {
                if (ogl.getBeginToken().getPrevious() != null) 
                    tr = m_TitlePageAnalyzer.processReferent1(kit.firstToken, ogl.getBeginToken().getPrevious());
                items.clear();
                for(com.pullenti.ner.Token t = ogl.getEndToken().getNext(); t != null; t = t.getNext()) {
                    if (!t.isNewlineBefore()) 
                        continue;
                    com.pullenti.ner.old.internal.DocStructItem ds = com.pullenti.ner.old.internal.DocStructItem.tryAttach(t, ogl.contentItems, false);
                    if (ds != null && ds.typ != com.pullenti.ner.old.internal.DocStructItem.Typs.INDEX) {
                        items.add(ds);
                        t = ds.getEndToken();
                    }
                }
            }
            else if (ogl.endChar > ((lastToken.endChar * 5) / 6)) {
                com.pullenti.ner.Token et = ogl.getBeginToken();
                if (items.size() > 0 && (items.get(0).beginChar < et.beginChar)) 
                    et = items.get(0).getBeginToken();
                if (ogl.getBeginToken().getPrevious() != null) 
                    tr = m_TitlePageAnalyzer.processReferent1(kit.firstToken, (et.getPrevious() == null ? et : null));
                items.clear();
                for(com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
                    if (t.endChar >= ogl.beginChar) 
                        break;
                    com.pullenti.ner.old.internal.DocStructItem ds = com.pullenti.ner.old.internal.DocStructItem.tryAttach(t, ogl.contentItems, false);
                    if (ds != null && ds.typ != com.pullenti.ner.old.internal.DocStructItem.Typs.INDEX) 
                        items.add(ds);
                }
                if (ogl.getEndToken().getNext() != null && ((ogl.getEndToken().getNext().beginChar + 10) < lastToken.endChar)) {
                    String ttt = kit.getSofa().substring(ogl.getEndToken().getNext().beginChar, (lastToken.endChar - ogl.getEndToken().getNext().beginChar) + 1).trim();
                    if (ttt != null && ttt.length() > 10) {
                        DocumentBlockReferent bt = DocumentBlockReferent._new1605(DocumentBlockType.TAIL);
                        bt.addSlot(DocumentBlockReferent.ATTR_CONTENT, ttt, true, 0);
                        tail = new com.pullenti.ner.ReferentToken(bt, ogl.getEndToken().getNext(), lastToken, null);
                    }
                }
                if (ogl.getBeginToken().getPrevious() != null) 
                    lastToken = ogl.getBeginToken().getPrevious();
            }
            else {
            }
        }
        if (items.size() == 0) 
            return;
        DocumentReferent res = (DocumentReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(new DocumentReferent()), DocumentReferent.class);
        if (tr != null) {
            for(com.pullenti.ner.Slot sl : tr.referent.getSlots()) {
                res.addSlot(sl.getTypeName(), sl.getValue(), false, 0);
            }
        }
        com.pullenti.ner.Token t0 = items.get(0).getBeginToken();
        if (ogl != null && (ogl.beginChar < t0.beginChar)) 
            t0 = ogl.getBeginToken();
        if (t0.getPrevious() != null && t0.getPrevious().endChar > 20) {
            DocumentBlockReferent blk = new DocumentBlockReferent();
            blk = (DocumentBlockReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(blk), DocumentBlockReferent.class);
            blk.addParent(res);
            blk.addSlot(DocumentBlockReferent.ATTR_CONTENT, kit.getSofa().substring(0, t0.getPrevious().endChar + 1).trim(), true, 0);
            blk.setTyp(DocumentBlockType.TITLE);
            blk.addOccurence(new com.pullenti.ner.TextAnnotation(kit.firstToken, t0.getPrevious(), blk));
        }
        for(int i = 0; i < items.size(); i++) {
            com.pullenti.ner.Token last = lastToken;
            if ((i + 1) < items.size()) 
                last = items.get(i + 1).getBeginToken().getPrevious();
            if (last == null) 
                break;
            DocumentBlockReferent blk = new DocumentBlockReferent();
            blk = (DocumentBlockReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(blk), DocumentBlockReferent.class);
            blk.addParent(res);
            blk.addSlot(DocumentBlockReferent.ATTR_NAME, items.get(i).value, true, 0);
            blk.addOccurence(new com.pullenti.ner.TextAnnotation(items.get(i).getBeginToken(), items.get(i).getEndToken(), blk));
            int cou = (last.endChar - items.get(i).getEndToken().getNext().beginChar) + 1;
            String txt = kit.getSofa().substring(items.get(i).getEndToken().getNext().beginChar, cou).trim();
            if (!com.pullenti.n2j.Utils.isNullOrEmpty(txt) && txt.length() > 20) {
                DocumentBlockReferent cnt = new DocumentBlockReferent();
                cnt = (DocumentBlockReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(cnt), DocumentBlockReferent.class);
                cnt.addParent(blk);
                cnt.addSlot(DocumentBlockReferent.ATTR_CONTENT, txt, true, 0);
                cnt.addOccurence(new com.pullenti.ner.TextAnnotation(items.get(i).getEndToken().getNext(), last, cnt));
                switch((items.get(i).typ).value()) { 
                case 2:
                    cnt.setTyp(DocumentBlockType.INTRODUCTION);
                    break;
                case 5:
                    cnt.setTyp(DocumentBlockType.CONCLUSION);
                    break;
                case 3:
                    cnt.setTyp(DocumentBlockType.LITERATURE);
                    break;
                case 4:
                    cnt.setTyp(DocumentBlockType.APPENDIX);
                    break;
                }
            }
        }
        if (tail != null && (tail.referent instanceof DocumentBlockReferent)) {
            tail.referent = ad.registerReferent(tail.referent);
            (((DocumentBlockReferent)com.pullenti.n2j.Utils.cast(tail.referent, DocumentBlockReferent.class))).addParent(res);
        }
    }
    public DocumentStructureAnalyzer() {
        super();
    }
}
