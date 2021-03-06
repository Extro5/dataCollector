/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.semantic.internal;

/**
 * Это для поддержки получения встроенных ресурсов
 */
public class ResourceHelper {

    /**
     * Получить встроенный ресурс
     * @param name имя, на который оканчивается ресурс
     * @return 
     */
    public static byte[] getBytes(String name) {
        // ignored:  assembly = ResourceHelper.class.;
        String[] names = com.pullenti.ner.semantic.properties.Resources.getNames();
        for(String n : names) {
            if (n.toUpperCase().endsWith(name.toUpperCase())) {
                try {
                    Object inf = com.pullenti.ner.semantic.properties.Resources.getResourceInfo(n);
                    if (inf == null) 
                        continue;
                    try (com.pullenti.n2j.Stream stream = com.pullenti.ner.semantic.properties.Resources.getStream(n)) {
                        byte[] buf = new byte[(int)stream.length()];
                        stream.read(buf, 0, buf.length);
                        return buf;
                    }
                } catch(Exception ex) {
                }
            }
        }
        return null;
    }

    public static String getString(String name) {
        byte[] arr = getBytes(name);
        if (arr == null) 
            return null;
        if ((arr.length > 3 && arr[0] == ((byte)0xEF) && arr[1] == ((byte)0xBB)) && arr[2] == ((byte)0xBF)) 
            return com.pullenti.n2j.Utils.decodeCharset(java.nio.charset.Charset.forName("UTF-8"), arr, 3, arr.length - 3);
        else 
            return com.pullenti.n2j.Utils.decodeCharset(java.nio.charset.Charset.forName("UTF-8"), arr, 0, -1);
    }
    public ResourceHelper() {
    }
}
