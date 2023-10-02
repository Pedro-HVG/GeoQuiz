package com.example.geoquiz_v4_sqlite;

import java.util.UUID;

public class Resposta {

    private UUID mId;
    private boolean mColou;
    private String mRespostaOferecida;
    private int mRespostaCorreta;

    public Resposta(boolean Colou, int RespostaCorreta, String RespostaOferecida) {
        this.mRespostaCorreta = RespostaCorreta;
        this.mColou = Colou;
        this.mRespostaOferecida = RespostaOferecida;
        mId = UUID.randomUUID();
    }

    UUID getId(){return mId;};

    public boolean getColou() {
        return mColou;
    }

    public void setColou(boolean colou) {
        mColou = colou;
    }

    public int getRespostaCorreta() {
        return mRespostaCorreta;
    }

    public void setRespostaCorreta(int respostaCorreta) {
        mRespostaCorreta = respostaCorreta;
    }

    public String getRespostaOferecida() {
        return mRespostaOferecida;
    }

    public void setRespostaOferecida(String RespostaOferecida) {
        mRespostaOferecida = RespostaOferecida;
    }

}
