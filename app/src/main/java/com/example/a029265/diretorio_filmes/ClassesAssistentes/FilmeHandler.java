package com.example.a029265.diretorio_filmes.ClassesAssistentes;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.ArrayList;

public class FilmeHandler extends MyXmlListHandler<TipoFilme> {
    private String tempValue;
    protected TipoFilme tempFilme;

    public FilmeHandler() {
        osElementos = new ArrayList<TipoFilme>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        tempValue = "";
        if (localName.equalsIgnoreCase("root")) {
        } else {
            tempFilme = new TipoFilme();
            osElementos.add(tempFilme);
            tempFilme.setIdFilme(attributes.getValue(17));
            tempFilme.setTitulo(attributes.getValue(0));
            tempFilme.setIdade(attributes.getValue(2));
            tempFilme.setLancamento(attributes.getValue(3));
            tempFilme.setDuracao(attributes.getValue(4));
            tempFilme.setGenero(attributes.getValue(5));
            tempFilme.setDiretor(attributes.getValue(6));
            tempFilme.setSinopse(attributes.getValue(9));
            tempFilme.setPremios(attributes.getValue(12));
            tempFilme.setPosterUrl(attributes.getValue(13));
            tempFilme.setActores(attributes.getValue(8));
            tempFilme.setAno(Integer.parseInt(attributes.getValue(1)));
            tempFilme.setPontuacao(Float.parseFloat(attributes.getValue(15)));
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