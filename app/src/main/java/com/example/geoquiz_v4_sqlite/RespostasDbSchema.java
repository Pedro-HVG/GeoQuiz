package com.example.geoquiz_v4_sqlite;

public class RespostasDbSchema {

    public static final class RespostasTbl{
        public static final String NOME = "Respostas";
        public static final class Cols{  //identificação das colunas da tabela Questões
            public static final String UUID = "uuid";
            public static final String RESPOSTA_CORRETA = "resp_correta";
            public static final String RESPOSTA_OFERECIDA = "resp_oferecida";
            public static final String COLOU = "colou";
        }
    }
}
