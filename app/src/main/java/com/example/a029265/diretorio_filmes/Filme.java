package com.example.a029265.diretorio_filmes;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.a029265.diretorio_filmes.ClassesAssistentes.Comunicar;

public class Filme extends Activity {

    protected AsyncGenerator backgroundTask;
    protected Intent intent;
    protected String id;

    //tt1596343
    //http://www.omdbapi.com/?i=tt1596343&r=xml

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme);
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
                Toast.makeText(Filme.this, s, Toast.LENGTH_SHORT).show();
                //SaxXmlParser<String, FilmeHandler> parser = new SaxXmlParser<String, FilmeHandler>();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
