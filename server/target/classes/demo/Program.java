/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.

 * This class is generated using the convertor N2JP from Pullenti C#.NET project.

 * See www.pullenti.ru/downloadpage.aspx.

 * 
 */

package demo;

import com.pullenti.morph.MorphLang;
import com.pullenti.ner.Sdk;

public class Program {

    public static void main(String[] args) throws Exception, java.io.IOException {
        com.pullenti.n2j.Stopwatch sw = com.pullenti.n2j.Utils.startNewStopwatch();
        System.out.print("Initializing ... ");
        Sdk.initialize(MorphLang.ooBitor(MorphLang.RU, MorphLang.EN));
        sw.stop();
        System.out.println("OK (by " + ((int)sw.getElapsedMilliseconds()) + " ms), version " + com.pullenti.ner.ProcessorService.getVersion());
        String txt = "Сабанов Владислав 1995 года рождения. Живу в Москве. Моя почта cool.extro5@yandex.ru. Мой телефон 89172550006";
        System.out.println("Text: " + txt);
        try (com.pullenti.ner.Processor proc = com.pullenti.ner.ProcessorService.createProcessor()) {
            com.pullenti.ner.AnalysisResult ar = proc.process(new com.pullenti.ner.SourceOfAnalysis(txt), null, new com.pullenti.morph.MorphLang(null));
            System.out.println("\r\n==========================================\r\nEntities: ");
            for(com.pullenti.ner.Referent e : ar.getEntities()) {
                System.out.println(e.getTypeName() + ": " + e.toString());
                for(com.pullenti.ner.Slot s : e.getSlots()) {
                    System.out.println("   " + s.getTypeName() + ": " + s.getValue());
                }
            }
            System.out.println("\r\n==========================================\r\nNoun groups: ");
            for(com.pullenti.ner.Token t = ar.firstToken; t != null; t = t.getNext()) {
                if (t.getReferent() != null) 
                    continue;
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.ADJECTIVECANBELAST, 0);
                if (npt == null) 
                    continue;
                System.out.println(npt);
                t = npt.getEndToken();
            }
        }
        try (com.pullenti.ner.Processor proc = com.pullenti.ner.ProcessorService.createSpecificProcessor(com.pullenti.ner.keyword.KeywordAnalyzer.ANALYZER_NAME)) {
            com.pullenti.ner.AnalysisResult ar = proc.process(new com.pullenti.ner.SourceOfAnalysis(txt), null, new com.pullenti.morph.MorphLang(null));
            System.out.println("\r\n==========================================\r\nKeywords1: ");
            for(com.pullenti.ner.Referent e : ar.getEntities()) {
                if (e instanceof com.pullenti.ner.keyword.KeywordReferent) 
                    System.out.println(e);
            }
            System.out.println("\r\n==========================================\r\nKeywords2: ");
            for(com.pullenti.ner.Token t = ar.firstToken; t != null; t = t.getNext()) {
                if (t instanceof com.pullenti.ner.ReferentToken) {
                    com.pullenti.ner.keyword.KeywordReferent kw = (com.pullenti.ner.keyword.KeywordReferent)com.pullenti.n2j.Utils.cast(t.getReferent(), com.pullenti.ner.keyword.KeywordReferent.class);
                    if (kw == null) 
                        continue;
                    String kwstr = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class), com.pullenti.ner.core.GetTextAttr.of((com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE.value()) | (com.pullenti.ner.core.GetTextAttr.KEEPREGISTER.value())));
                    System.out.println(kwstr + " = " + kw);
                }
            }
        }
        System.out.println("Over!");
    }
    public Program() {
    }
}
