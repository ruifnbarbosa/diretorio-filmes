package com.example.a029265.diretorio_filmes.ClassesAssistentes;

import com.example.a029265.diretorio_filmes.ClassesAssistentes.MyXmlListHandler;
import com.example.a029265.diretorio_filmes.ClassesAssistentes.Pesquisa;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.ArrayList;

public class PesquisaHandler extends MyXmlListHandler<Pesquisa> {
    private String tempValue;
    protected Pesquisa tempPesquisa;

    public PesquisaHandler() {
        osElementos = new ArrayList<Pesquisa>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        tempValue = "";
        if (localName.equalsIgnoreCase("root")) {
        } else {
            tempPesquisa = new Pesquisa();
            osElementos.add(tempPesquisa);
            tempPesquisa.setId(attributes.getValue(2)); //3º do XML
            tempPesquisa.setNome(attributes.getValue(0));//1º do XML

        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        tempValue = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }
}