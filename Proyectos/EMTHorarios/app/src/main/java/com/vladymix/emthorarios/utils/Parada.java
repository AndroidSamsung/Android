package com.vladymix.emthorarios.utils;

import java.util.List;

/**
 * Created by Fabricio on 04/06/2015.
 */
public class Parada {
    String Node;
    String PosxNode;
    String PosyNode;
    String Name;
    String Lines;
    String PostalAdress;
    List<Linea> ListaLineas;

    public String getPostalAdress() {
        return PostalAdress;
    }

    public void setPostalAdress(String postalAdress) {
        PostalAdress = postalAdress;
    }

    public Parada(String node, String name) {
        Node = node;
        Name = name;
    }

    public Parada(){

    }
    public Parada( String name, String node, String posxNode, String posyNode) {
        PosxNode = posxNode;
        PosyNode = posyNode;
        Name = name;
        Node = node;
    }

    public String getNode() {
        return Node;
    }

    public void setNode(String node) {
        Node = node;
    }

    public String getPosxNode() {
        return PosxNode;
    }

    public void setPosxNode(String posxNode) {
        PosxNode = posxNode;
    }

    public String getPosyNode() {
        return PosyNode;
    }

    public void setPosyNode(String posyNode) {
        PosyNode = posyNode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLines() {
        return Lines;
    }

    public void setLines(String lines) {
        Lines = lines;
    }

    public List<Linea> getListaLineas() {
        return ListaLineas;
    }

    public void setListaLineas(List<Linea> listaLineas) {
        ListaLineas = listaLineas;
    }
}
