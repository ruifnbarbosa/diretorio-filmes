package com.example.a029265.diretorio_filmes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a029265.diretorio_filmes.ClassesAssistentes.Comunicar;
import com.example.a029265.diretorio_filmes.ClassesAssistentes.FilmeHandler;
import com.example.a029265.diretorio_filmes.ClassesAssistentes.SaxXmlParser;
import com.example.a029265.diretorio_filmes.ClassesAssistentes.TipoFilme;

import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

public class Filme extends Activity {

    protected AsyncGenerator backgroundTask;
    protected Intent intent;
    protected String id;
    protected ArrayList<TipoFilme> filmeList;

    protected TextView titulo, ano, idade, lancamento, duracao, genero, diretor, sinopse, premios, pontuacao, atores;
    protected ImageView poster;

    //tt1596343
    //http://www.omdbapi.com/?i=tt1596343&r=xml

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme);

        titulo = findViewById(R.id.titulo);
        ano = findViewById(R.id.ano);
        idade = findViewById(R.id.idade);
        lancamento = findViewById(R.id.lancamento);
        duracao = findViewById(R.id.duracao);
        genero = findViewById(R.id.genero);
        diretor = findViewById(R.id.diretor);
        sinopse = findViewById(R.id.sinopse);
        premios = findViewById(R.id.premios);
        poster = findViewById(R.id.poster);
        pontuacao = findViewById(R.id.pontuacao);
        atores = findViewById(R.id.actores);
    }

    @Override
    protected void onStart() {
        super.onStart();
        intent = getIntent();
        id = intent.getStringExtra("idFilme");

        backgroundTask = new AsyncGenerator("omdbapi.com", "/?i=" + id + "&r=xml&apikey=17877af9", 80);
        backgroundTask.execute();
    }

    private class AsyncGenerator extends AsyncTask<Void, Void, String> {

        protected String host, path;
        protected int port;

        public AsyncGenerator(String host, String path, int port) {
            this.host = host;
            this.path = path;
            this.port = port;
        }

        @Override
        protected String doInBackground(Void... voids) {
            return Comunicar.contactar2(host, path, port);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                SaxXmlParser<TipoFilme, FilmeHandler> parser = new SaxXmlParser<TipoFilme, FilmeHandler>();
                parser.setHandler(new FilmeHandler());
                filmeList = (ArrayList<TipoFilme>) parser.parse(new StringReader(s));
                TipoFilme filme = filmeList.get(0);
                new DownloadImage(poster).execute(filme.getPosterUrl());
                titulo.setText(filme.getTitulo());
                ano.setText("" + filme.getAno());
                idade.setText(filme.getIdade());
                lancamento.setText(filme.getLancamento());
                duracao.setText(filme.getDuracao());
                genero.setText(filme.getGenero());
                diretor.setText(filme.getDiretor());
                sinopse.setText(filme.getSinopse());
                premios.setText(filme.getPremios());
                pontuacao.setText("" + filme.getPontuacao());
                atores.setText(filme.getActores());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        protected ImageView poster;

        public DownloadImage(ImageView poster) {
            this.poster = poster;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap poster = null;
            try {
                InputStream inputStream = new URL(url).openStream();
                poster = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return poster;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            poster.setImageBitmap(bitmap);
        }
    }
}