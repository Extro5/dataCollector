/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner;

/**
 * Это заглушка референта при десериализации
 */
public class ProxyReferent {

    public String value;

    public String identity;

    public Referent referent;

    public Slot ownerSlot;

    public Referent ownerReferent;

    @Override
    public String toString() {
        return value;
    }

    public static ProxyReferent _new2761(String _arg1, Referent _arg2) {
        ProxyReferent res = new ProxyReferent();
        res.value = _arg1;
        res.ownerReferent = _arg2;
        return res;
    }
    public ProxyReferent() {
    }
}
