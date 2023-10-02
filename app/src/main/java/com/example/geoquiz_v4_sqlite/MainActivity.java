package com.example.geoquiz_v4_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/*
  Modelo de projeto para a Atividade 1.
  Será preciso adicionar o cadastro das respostas do usuário ao Quiz, conforme
  definido no Canvas.

  GitHub: https://github.com/udofritzke/GeoQuiz
 */

public class MainActivity extends AppCompatActivity {
    private Button mBotaoVerdadeiro;
    private Button mBotaoFalso;
    private Button mBotaoProximo;
    private Button mBotaoCadastra;
    private Button mBotaoMostra;
    private Button mBotaoDeleta;

    private Button mBotaoCola;

    private TextView mTextViewQuestao;
    private TextView mTextViewRespostasArmazenadas;

    private static final String TAG = "QuizActivity";
    private static final String CHAVE_INDICE = "INDICE";
    private static final int CODIGO_REQUISICAO_COLA = 0;

    private Questao[] mBancoDeQuestoes = new Questao[]{
            new Questao(R.string.questao_suez, true),
            new Questao(R.string.questao_alemanha, false)
    };

    QuestaoDB mQuestoesDb;

    RespostaDB mRespostaDB;

    private int mIndiceAtual = 0;

    private boolean mEhColador;
    private String mResposta;

    @Override
    protected void onCreate(Bundle instanciaSalva) {
        super.onCreate(instanciaSalva);
        setContentView(R.layout.activity_main);
        //Log.d(TAG, "onCreate()");
        if (instanciaSalva != null) {
            mIndiceAtual = instanciaSalva.getInt(CHAVE_INDICE, 0);
        }

        mTextViewQuestao = (TextView) findViewById(R.id.view_texto_da_questao);
        atualizaQuestao();

        mBotaoVerdadeiro = (Button) findViewById(R.id.botao_verdadeiro);
        // utilização de classe anônima interna
        mBotaoVerdadeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mResposta = "Verdadeiro";
                verificaResposta(true);
            }
        });

        mBotaoFalso = (Button) findViewById(R.id.botao_falso);
        mBotaoFalso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mResposta = "Falso";
                verificaResposta(false);
            }
        });
        mBotaoProximo = (Button) findViewById(R.id.botao_proximo);
        mBotaoProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIndiceAtual = (mIndiceAtual + 1) % mBancoDeQuestoes.length;
                mEhColador = false;
                atualizaQuestao();
            }
        });

        mBotaoCola = (Button) findViewById(R.id.botao_cola);
        mBotaoCola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inicia ColaActivity
                // Intent intent = new Intent(MainActivity.this, ColaActivity.class);
                boolean respostaEVerdadeira = mBancoDeQuestoes[mIndiceAtual].isRespostaCorreta();
                Intent intent = ColaActivity.novoIntent(MainActivity.this, respostaEVerdadeira);
                //startActivity(intent);
                startActivityForResult(intent, CODIGO_REQUISICAO_COLA);
            }
        });

        //mBotaoCadastra = (Button) findViewById(R.id.botao_cadastra);
        //mBotaoCadastra.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
                /*
                  Acesso ao SQLite
                */
      //          if (mQuestoesDb == null) {
      //              mQuestoesDb = new QuestaoDB(getBaseContext());
       //         }
      //          int indice = 0;
        //        mQuestoesDb.addQuestao(mBancoDeQuestoes[indice++]);
          //      mQuestoesDb.addQuestao(mBancoDeQuestoes[indice++]);
            //}
       // });

        //Cursor cur = mQuestoesDb.queryQuestao ("_id = ?", val);////(null, null);
        //String [] val = {"1"};
        mBotaoMostra = (Button) findViewById(R.id.botao_mostra_respostas);
        mBotaoMostra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                  Acesso ao SQLite
                */
                if (mRespostaDB == null) return;
                if (mTextViewRespostasArmazenadas == null) {
                    mTextViewRespostasArmazenadas = (TextView) findViewById(R.id.texto_respostas_a_apresentar);
                } else {
                    mTextViewRespostasArmazenadas.setText("");
                }
                Cursor cursor = mRespostaDB.queryResposta(null, null);
                if (cursor != null) {
                    if (cursor.getCount() == 0) {
                        mTextViewRespostasArmazenadas.setText("Nada a apresentar");
                        Log.i("MSGS", "Nenhum resultado");
                    }
                    //Log.i("MSGS", Integer.toString(cursor.getCount()));
                    //Log.i("MSGS", "cursor não nulo!");
                    try {
                        cursor.moveToFirst();
                        while (!cursor.isAfterLast()) {
                            String texto = cursor.getString(cursor.getColumnIndex(RespostasDbSchema.RespostasTbl.Cols.RESPOSTA_OFERECIDA));
                            Log.i("MSGS", texto);

                            mTextViewRespostasArmazenadas.append(texto + "\n");
                            cursor.moveToNext();
                        }
                    } finally {
                        cursor.close();
                    }
                } else
                    Log.i("MSGS", "cursor nulo!");
            }
        });
        mBotaoDeleta = (Button) findViewById(R.id.botao_deleta);
        mBotaoDeleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                  Acesso ao SQLite
                */
                if (mRespostaDB != null) {
                    mRespostaDB.removeBanco();
                    if (mTextViewRespostasArmazenadas == null) {
                        mTextViewRespostasArmazenadas = (TextView) findViewById(R.id.texto_respostas_a_apresentar);
                    }
                    mTextViewRespostasArmazenadas.setText("");
                }
            }
        });

    }

    private void atualizaQuestao() {
        int questao = mBancoDeQuestoes[mIndiceAtual].getTextoRespostaId();
        mTextViewQuestao.setText(questao);
    }

    private void verificaResposta(boolean respostaPressionada) {
        boolean respostaCorreta = mBancoDeQuestoes[mIndiceAtual].isRespostaCorreta();
        int idMensagemResposta = 0;

        if (mEhColador) {
            idMensagemResposta = R.string.toast_julgamento;
        } else {
            if (respostaPressionada == respostaCorreta) {
                idMensagemResposta = R.string.toast_correto;

            } else
                idMensagemResposta = R.string.toast_incorreto;
        }
        /*
                  Acesso ao SQLite
                */
        if (mRespostaDB == null) {
            mRespostaDB = new RespostaDB(getBaseContext());
        }
        Resposta r = new Resposta(mEhColador, respostaCorreta, mResposta);
        mRespostaDB.addResposta(r);
        Toast.makeText(this, idMensagemResposta, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle instanciaSalva) {
        super.onSaveInstanceState(instanciaSalva);
        Log.i(TAG, "onSaveInstanceState()");
        instanciaSalva.putInt(CHAVE_INDICE, mIndiceAtual);
    }

    @Override
    protected void onActivityResult(int codigoRequisicao, int codigoResultado, Intent dados) {
        if (codigoResultado != Activity.RESULT_OK) {
            return;
        }
        if (codigoRequisicao == CODIGO_REQUISICAO_COLA) {
            if (dados == null) {
                return;
            }
            mEhColador = ColaActivity.foiMostradaResposta(dados);
        }
    }
}