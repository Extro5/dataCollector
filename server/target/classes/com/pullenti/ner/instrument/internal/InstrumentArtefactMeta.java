/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.instrument.internal;

public class InstrumentArtefactMeta extends com.pullenti.ner.ReferentClass {

    public InstrumentArtefactMeta() {
        super();
        addFeature(com.pullenti.ner.instrument.InstrumentArtefact.ATTR_TYPE, "Тип", 0, 1);
        addFeature(com.pullenti.ner.instrument.InstrumentArtefact.ATTR_VALUE, "Значение", 0, 1);
        addFeature(com.pullenti.ner.instrument.InstrumentArtefact.ATTR_REF, "Ссылка на объект", 0, 1).setShowAsParent(true);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.instrument.InstrumentParticipant.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Артефакт";
    }


    public static String IMAGEID = "artefact";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return IMAGEID;
    }

    public static InstrumentArtefactMeta GLOBALMETA = new InstrumentArtefactMeta();
}
