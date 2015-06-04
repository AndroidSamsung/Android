package com.vladymix.emthorarios.utils;

/**
 * Created by Fabricio on 04/06/2015.
 */
public class Linea {
    int GroupNumber;
    String Line;
    String Label;
    String NameA;
    String NameB;
    String Direction;

    public String getDirection() {
        return Direction;
    }

    public void setDirection(String direction) {
        Direction = direction;
    }

    public Linea(String line, String label, String nameA, String nameB) {
        Line = line;
        Label = label;
        NameA = nameA;
        NameB = nameB;
    }

    public Linea(String line, String label,String direction, String nameA, String nameB) {
        Line = line;
        Label = label;
        NameA = nameA;
        NameB = nameB;
        Direction = direction;
    }

    public Linea(String label, String nameA, String nameB) {
        Label = label;
        NameA = nameA;
        NameB = nameB;
    }

    public int getGroupNumber() {
        return GroupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        GroupNumber = groupNumber;
    }

    public String getLine() {
        return Line;
    }

    public void setLine(String line) {
        Line = line;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public String getNameA() {
        return NameA;
    }

    public void setNameA(String nameA) {
        NameA = nameA;
    }

    public String getNameB() {
        return NameB;
    }

    public void setNameB(String nameB) {
        NameB = nameB;
    }
}
