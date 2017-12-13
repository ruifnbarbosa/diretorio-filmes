package com.example.a029265.diretorio_filmes;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a029265.diretorio_filmes.ClassesAssistentes.AdaptadorBaseDados;
import com.example.a029265.diretorio_filmes.ClassesAssistentes.Comunicar;
import com.example.a029265.diretorio_filmes.ClassesAssistentes.Pesquisa;
import com.example.a029265.diretorio_filmes.ClassesAssistentes.PesquisaHandler;
import com.example.a029265.diretorio_filmes.ClassesAssistentes.SaxXmlParser;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    protected AsyncGenerator backgroundTask;
    protected EditText campoPesquisa;
    protected Button botaoPesquisar, botaoFavoritos, botaobotaoVerMaisTarde;
    protected ArrayList<Pesquisa> listaPesquisa;
    protected Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        campoPesquisa = findViewById(R.id.campoPesquisa);
        botaoPesquisar = findViewById(R.id.botaoPesquisar);
        botaoFavoritos = findViewById(R.id.botaoFavoritos);
        botaobotaoVerMaisTarde = findViewById(R.id.botaoVerMaisTarde);

    }

    @Override
    protected void onStart() {
        super.onStart();

        activity = this;

        botaoPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundTask = new AsyncGenerator("omdbapi.com", "/?apikey=17877af9&r=xml&s=" + campoPesquisa.getText().toString(), 80);
                backgroundTask.execute();
            }
        });
    }

    private class AsyncGenerator extends AsyncTask<Void, Void, String> {

        protected String host, path;
        protected int port;

        AsyncGenerator(String host, String path, int port) {
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
            listaPesquisa = null;
            try {
                SaxXmlParser<Pesquisa, PesquisaHandler> parser = new SaxXmlParser<Pesquisa, PesquisaHandler>();
                parser.setHandler(new PesquisaHandler());
                listaPesquisa = (ArrayList<Pesquisa>) parser.parse(new StringReader(s));

                Intent intent = new Intent(activity, Resultados.class);
                Bundle args = new Bundle();
                args.putSerializable("pesquisa", (Serializable) listaPesquisa);
                intent.putExtra("pesquisaBundle", args);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}