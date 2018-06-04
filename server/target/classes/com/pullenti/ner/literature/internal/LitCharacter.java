/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature.internal;

public class LitCharacter implements Comparable<LitCharacter> {

    public java.util.ArrayList<CharacterVariant> vars = new java.util.ArrayList< >();

    public com.pullenti.morph.MorphGender getGender() {
        for(CharacterVariant v : vars) {
            com.pullenti.morph.MorphGender g = v.getGender();
            if (g != com.pullenti.morph.MorphGender.UNDEFINED) 
                return g;
        }
        return com.pullenti.morph.MorphGender.UNDEFINED;
    }


    public int getTotalCount() {
        int cou = 0;
        for(CharacterVariant v : vars) {
            cou += v.totalCount;
        }
        return cou;
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(CharacterVariant v : vars) {
            if (v != vars.get(0)) 
                res.append(" / ");
            res.append(v.toString(true, new com.pullenti.morph.MorphLang(null), 0));
        }
        if (general != null) 
            res.append(" [General: ").append(general.toString()).append("]");
        return res.toString();
    }

    public com.pullenti.ner.literature.CharacterReferent referent;

    public LitCharacter general;

    public java.util.ArrayList<LitCharacter> soldaten = new java.util.ArrayList< >();

    public boolean ignore;

    public com.pullenti.ner.literature.CharacterReferent createReferent() {
        com.pullenti.ner.literature.CharacterReferent res = new com.pullenti.ner.literature.CharacterReferent();
        com.pullenti.morph.MorphGender gen = getGender();
        if (gen == com.pullenti.morph.MorphGender.MASCULINE) 
            res.setGender(com.pullenti.ner.literature.CharacterGender.MASCULINE);
        else if (gen == com.pullenti.morph.MorphGender.FEMINIE) 
            res.setGender(com.pullenti.ner.literature.CharacterGender.FEMINIE);
        if ((toString().indexOf("МАМЕД") >= 0)) {
        }
        com.pullenti.ner.literature.CharacterType typ = com.pullenti.ner.literature.CharacterType.UNDEFINED;
        boolean hasManAttr = false;
        java.util.ArrayList<String> attrs = new java.util.ArrayList< >();
        java.util.ArrayList<String> empty = new java.util.ArrayList< >();
        for(CharacterVariant v : vars) {
            String name = v.getNormalName();
            if (name != null) {
                if (v.items.get(0).isAttr() && v.linkVariant != null) 
                    res.addSlot(com.pullenti.ner.literature.CharacterReferent.ATTR_NAME1, name, false, 0);
                else 
                    res.addSlot(com.pullenti.ner.literature.CharacterReferent.ATTR_NAME, name, false, 0);
            }
            String attr = v.getNormalAttr(getGender());
            if (attr == null) 
                continue;
            if (v.items.get(0).isAnimal()) 
                typ = com.pullenti.ner.literature.CharacterType.ANIMAL;
            else if (v.items.get(0).isMythical()) 
                typ = com.pullenti.ner.literature.CharacterType.MYTHIC;
            if (v.items.get(0).isEmoAttr()) {
                res.addSlot(com.pullenti.ner.literature.CharacterReferent.ATTR_EMO, attr, false, 0);
                continue;
            }
            if (v.items.get(0).isEmptyAttr()) {
                if (v.items.get(0).occures.isAllInDialogs()) 
                    continue;
                hasManAttr = true;
                empty.add(attr);
            }
            else {
                if (!v.items.get(0).isAnimal()) 
                    hasManAttr = true;
                if (v.linkVariant == null || v.linkVariant.result == null) 
                    attrs.add(attr);
                else {
                    if (v.items.get(0).isAnimal()) {
                        attrs.add(attr);
                        attr = "собственность";
                    }
                    com.pullenti.ner.literature.LinkReferent link = com.pullenti.ner.literature.LinkReferent._new1533(attr.toLowerCase());
                    link.setTag(v.linkVariant.result);
                    res.addSlot(com.pullenti.ner.literature.CharacterReferent.ATTR_ATTR, link, false, 0);
                }
            }
        }
        for(String a : attrs) {
            res.addSlot(com.pullenti.ner.literature.CharacterReferent.ATTR_ATTR, a, false, 0);
        }
        if (attrs.size() == 0 && res.findSlot(com.pullenti.ner.literature.CharacterReferent.ATTR_NAME, null, true) == null) {
            for(String a : empty) {
                res.addSlot(com.pullenti.ner.literature.CharacterReferent.ATTR_ATTR, a, false, 0);
            }
        }
        if (typ == com.pullenti.ner.literature.CharacterType.UNDEFINED && hasManAttr) 
            typ = com.pullenti.ner.literature.CharacterType.MAN;
        if (typ == com.pullenti.ner.literature.CharacterType.UNDEFINED) {
            for(CharacterVariant v : vars) {
                if (v.inDialogAgentCount > 0) {
                    typ = com.pullenti.ner.literature.CharacterType.MAN;
                    break;
                }
            }
        }
        if (typ != com.pullenti.ner.literature.CharacterType.UNDEFINED) 
            res.setTyp(typ);
        for(String n : res.getNames()) {
            int i = n.indexOf('-');
            if (i < 0) 
                continue;
            String attr = n.substring(i + 1).trim().toLowerCase();
            if (res.findSlot(com.pullenti.ner.literature.CharacterReferent.ATTR_ATTR, attr, true) != null) {
                com.pullenti.ner.Slot s = res.findSlot(com.pullenti.ner.literature.CharacterReferent.ATTR_NAME, n, true);
                res.getSlots().remove(s);
                res.addSlot(com.pullenti.ner.literature.CharacterReferent.ATTR_NAME, n.substring(0, 0+(i)).trim(), false, 0);
            }
        }
        return res;
    }

    @Override
    public int compareTo(LitCharacter other) {
        int cou1 = (referent == null ? getTotalCount() : referent.getOccurrence().size());
        int cou2 = (other.referent == null ? other.getTotalCount() : other.referent.getOccurrence().size());
        if (cou1 > cou2) 
            return -1;
        if (cou1 < cou2) 
            return 1;
        return 0;
    }

    public int canBeOneCharacter(LitCharacter cha) {
        com.pullenti.morph.MorphGender g1 = getGender();
        com.pullenti.morph.MorphGender g2 = cha.getGender();
        if (g1 != com.pullenti.morph.MorphGender.UNDEFINED && g2 != com.pullenti.morph.MorphGender.UNDEFINED) {
            if (g1 != g2) 
                return -1;
        }
        int co;
        int max = 0;
        for(CharacterVariant v : vars) {
            if (v.items.size() > 1 || !v.items.get(0).isAttr()) {
                for(CharacterVariant vv : cha.vars) {
                    if (vv != v) {
                        if (vv.items.size() > 1 || !vv.items.get(0).isAttr()) {
                            if ((((co = v.canBeOneCharacter(vv)))) > max) 
                                max = co;
                        }
                    }
                }
            }
        }
        if (max > 0) {
            java.util.ArrayList<CharItem> surNames1 = new java.util.ArrayList< >();
            java.util.ArrayList<CharItem> surNames2 = new java.util.ArrayList< >();
            for(int k = 0; k < 2; k++) {
                surNames1.clear();
                for(CharacterVariant v : vars) {
                    for(CharItemVar it : v.items) {
                        if (it.isAttr()) 
                            continue;
                        if (((k == 0 && it.isCanBeFirstName())) || ((k == 1 && ((!it.isCanBeFirstName() || it.isCanBeLastName()))))) {
                            if (!surNames1.contains(it.item)) 
                                surNames1.add(it.item);
                        }
                    }
                }
                if (surNames1.size() == 0) 
                    continue;
                surNames2.clear();
                for(CharacterVariant v : cha.vars) {
                    for(CharItemVar it : v.items) {
                        if (it.isAttr()) 
                            continue;
                        if (((k == 0 && it.isCanBeFirstName())) || ((k == 1 && ((!it.isCanBeFirstName() || it.isCanBeLastName()))))) {
                            if (!surNames2.contains(it.item)) 
                                surNames2.add(it.item);
                        }
                    }
                }
                if (surNames1.size() > 0 && surNames2.size() > 0) {
                    for(CharItem s : surNames1) {
                        if (!surNames2.contains(s)) 
                            return 0;
                    }
                }
            }
            return max + 1;
        }
        if ((vars.size() == 1 && vars.get(0).items.size() == 1 && vars.get(0).items.get(0).isAttr()) && !vars.get(0).items.get(0).isCanBePersonAfter() && !vars.get(0).items.get(0).isEmptyAttr()) 
            return 1;
        if ((cha.vars.size() == 1 && cha.vars.get(0).items.size() == 1 && cha.vars.get(0).items.get(0).isAttr()) && !cha.vars.get(0).items.get(0).isCanBePersonAfter() && !cha.vars.get(0).items.get(0).isEmptyAttr()) 
            return 1;
        return 0;
    }

    public void mergeWith(LitCharacter cha) {
        if (this == cha) 
            return;
        for(CharacterVariant v : cha.vars) {
            v.result = this;
            vars.add(v);
        }
        cha.vars.clear();
    }
    public LitCharacter() {
    }
}
