package com.example.a029265.diretorio_filmes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a029265.diretorio_filmes.ClassesAssistentes.AdaptadorBaseDados;
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
    protected Button botaoFavoritos, botaoVerMaisTarde;
    protected Activity activity;

    protected AdaptadorBaseDados adaptadorBaseDados;

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

        botaoFavoritos = findViewById(R.id.adicionarFavoritos);
        botaoVerMaisTarde = findViewById(R.id.botaoVerMaisTarde);
    }

    @Override
    protected void onStart() {
        super.onStart();
        intent = getIntent();
        id = intent.getStringExtra("idFilme");
        activity = this;

        backgroundTask = new AsyncGenerator("omdbapi.com", "/?i=" + id + "&r=xml&apikey=17877af9", 80);
        backgroundTask.execute();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptadorBaseDados.close();
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
                new DownloadImage(poster).execute(filmeList.get(0).getPosterUrl());
                titulo.setText(filmeList.get(0).getTitulo());
                ano.setText("" + filmeList.get(0).getAno());
                idade.setText(filmeList.get(0).getIdade());
                lancamento.setText(filmeList.get(0).getLancamento());
                duracao.setText(filmeList.get(0).getDuracao());
                genero.setText(filmeList.get(0).getGenero());
                diretor.setText(filmeList.get(0).getDiretor());
                sinopse.setText(filmeList.get(0).getSinopse());
                premios.setText(filmeList.get(0).getPremios());
                pontuacao.setText("" + filmeList.get(0).getPontuacao());
                atores.setText(filmeList.get(0).getActores());

                SystemClock.sleep(2);

                adaptadorBaseDados = new AdaptadorBaseDados(activity).open();

                if (adaptadorBaseDados.existeFavoritos(filmeList.get(0).getIdFilme())) {
                    botaoFavoritos.setText("Já adicionado aos Favoritos");
                    botaoFavoritos.setEnabled(false);
                } else {
                    botaoFavoritos.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            adaptadorBaseDados.inserirBaseDados(filmeList.get(0).getIdFilme(), filmeList.get(0).getTitulo(), filmeList.get(0).getPontuacao(), 1);
                            botaoFavoritos.setText("Já adicionado aos Favoritos");
                            botaoFavoritos.setEnabled(false);
                        }
                    });
                }

                if (adaptadorBaseDados.existeMaisTarde(filmeList.get(0).getIdFilme())) {
                    botaoVerMaisTarde.setText("Já adicionado á Lista");
                    botaoVerMaisTarde.setEnabled(false);
                } else {
                    botaoVerMaisTarde.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            adaptadorBaseDados.inserirBaseDados(filmeList.get(0).getIdFilme(), filmeList.get(0).getTitulo(), filmeList.get(0).getPontuacao(), 2);
                            botaoVerMaisTarde.setText("Já adicionado á Lista");
                            botaoVerMaisTarde.setEnabled(false);
                        }
                    });
                }

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