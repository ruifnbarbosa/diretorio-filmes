package com.example.a029265.diretorio_filmes.ClassesAssistentes;

import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

public class MyXmlListHandler<E> extends DefaultHandler {
    protected List<E> osElementos;

    public List<E> obterElementos() {
        return osElementos;
    }
}