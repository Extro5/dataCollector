/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.org.properties;

public class Resources {
    static String[] m_Names = new String[] { "festival.png", "train.png", "federation.png", "avia.png", "church.png", "holding.png", "hotel.png", "justice.png", "music.png", "politics.png", "science.png", "sport.png", "study.png", "dep.png", "medicine.png", "gov.png", "factory.png", "culture.png", "bank.png", "press.png", "org.png", "media.png", "military.png", "trade.png", "party.png", "NameNouns_ru.dat", "NameNouns_ua.dat", "Orgs_en.dat", "Orgs_ru.dat", "Orgs_ua.dat", "OrgTypes.dat", "pattrs_en.dat", "pattrs_ru.dat", "pattrs_ua.dat" }; 
    public static String[] getNames() { return m_Names; } 
    public static com.pullenti.n2j.Stream getStream(String name) throws java.io.IOException {
        try {
            for (int k = 0; k < 2; k++) {
                for (int i = 0; i < m_Names.length; i++) {
                    if ((k == 0 && name.equalsIgnoreCase(m_Names[i]))
                            || (k == 1 && name.endsWith(m_Names[i]))) {
                        java.io.InputStream istr = Resources.class.getResourceAsStream(m_Names[i]);
                        if (istr == null) throw new Exception("Can't find resource file " + m_Names[i]);
                        byte[] dat = com.pullenti.n2j.Utils.readAllBytes(istr);
                        istr.close();
                        return new com.pullenti.n2j.MemoryStream(dat);
                    }
                }
            }
        } catch (Exception ee) {
               System.out.println(ee);
        }
        return null;
    }
    public static Object getResourceInfo(String name) {
        try {
            for (int k = 0; k < 2; k++) {
                for (int i = 0; i < m_Names.length; i++) {
                    if ((k == 0 && name.equalsIgnoreCase(m_Names[i]))
                            || (k == 1 && name.endsWith(m_Names[i]))) {
                        java.io.InputStream istr = Resources.class.getResourceAsStream(m_Names[i]);
                        if (istr == null) return null;
                        istr.close();
                        return m_Names[i];
                    }
                }
            }
        } catch (Exception ee) {
        }
        return null;
    }
}
