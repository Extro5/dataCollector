/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.phone.internal;

public class PhoneHelper {

    public static void initialize() throws Exception, java.io.IOException {
        if (m_PhoneRoot != null) 
            return;
        m_PhoneRoot = new PhoneNode();
        m_AllCountryCodes = new java.util.HashMap< >();
        String str = com.pullenti.ner.bank.internal.ResourceHelper.getString("CountryPhoneCodes.txt");
        if (str == null) 
            throw new Exception("Can't file resource file " + "CountryPhoneCodes.txt" + " in Organization analyzer");
        try (java.io.BufferedReader r = new java.io.BufferedReader(new java.io.StringReader(str))) {
            while(true) {
                String line = r.readLine();
                if (line == null) 
                    break;
                if (com.pullenti.n2j.Utils.isNullOrEmpty(line)) 
                    continue;
                if (line.length() < 2) 
                    continue;
                String country = line.substring(0, 0+2);
                String cod = line.substring(2).trim();
                if (cod.length() < 1) 
                    continue;
                if (!m_AllCountryCodes.containsKey(country)) 
                    m_AllCountryCodes.put(country, cod);
                PhoneNode tn = m_PhoneRoot;
                for(int i = 0; i < cod.length(); i++) {
                    char dig = cod.charAt(i);
                    PhoneNode nn;
                    com.pullenti.n2j.Outargwrapper<PhoneNode> inoutarg2404 = new com.pullenti.n2j.Outargwrapper< >();
                    Boolean inoutres2405 = com.pullenti.n2j.Utils.tryGetValue(tn.children, dig, inoutarg2404);
                    nn = inoutarg2404.value;
                    if (!inoutres2405) {
                        nn = new PhoneNode();
                        nn.pref = cod.substring(0, 0+(i + 1));
                        tn.children.put(dig, nn);
                    }
                    tn = nn;
                }
                if (tn.countries == null) 
                    tn.countries = new java.util.ArrayList< >();
                tn.countries.add(country);
            }
        }
    }

    private static java.util.HashMap<String, String> m_AllCountryCodes;

    public static java.util.HashMap<String, String> getAllCountryCodes() {
        return m_AllCountryCodes;
    }

    public static class PhoneNode {
    
        public String pref;
    
        public java.util.HashMap<Character, PhoneNode> children = new java.util.HashMap< >();
    
        public java.util.ArrayList<String> countries;
    
        @Override
        public String toString() {
            if (countries == null) 
                return pref;
            StringBuilder res = new StringBuilder(pref);
            for(String c : countries) {
                res.append(" ").append(c);
            }
            return res.toString();
        }
        public PhoneNode() {
        }
    }


    private static PhoneNode m_PhoneRoot;

    /**
     * Выделить телефонный префикс из "полного" номера
     * @param fullNumber 
     * @return 
     */
    public static String getCountryPrefix(String fullNumber) {
        if (fullNumber == null) 
            return null;
        PhoneNode nod = m_PhoneRoot;
        int maxInd = -1;
        for(int i = 0; i < fullNumber.length(); i++) {
            char dig = fullNumber.charAt(i);
            PhoneNode nn;
            com.pullenti.n2j.Outargwrapper<PhoneNode> inoutarg2406 = new com.pullenti.n2j.Outargwrapper< >();
            Boolean inoutres2407 = com.pullenti.n2j.Utils.tryGetValue(nod.children, dig, inoutarg2406);
            nn = inoutarg2406.value;
            if (!inoutres2407) 
                break;
            if (nn.countries != null && nn.countries.size() > 0) 
                maxInd = i;
            nod = nn;
        }
        if (maxInd < 0) 
            return null;
        else 
            return fullNumber.substring(0, 0+(maxInd + 1));
    }
    public PhoneHelper() {
    }
    public static PhoneHelper _globalInstance;
    static {
        _globalInstance = new PhoneHelper(); 
    }
}
