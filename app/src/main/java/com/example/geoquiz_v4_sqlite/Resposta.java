package com.example.geoquiz_v4_sqlite;

import java.util.UUID;

public class Resposta {

    private UUID mId;
    private boolean mColou;
    private String mRespostaOferecida;
    private boolean mRespostaCorreta;

    public Resposta(boolean Colou, boolean RespostaCorreta, String RespostaOferecida) {
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

    public boolean getRespostaCorreta() {
        return mRespostaCorreta;
    }

    public void setRespostaCorreta(boolean respostaCorreta) {
        mRespostaCorreta = respostaCorreta;
    }

    public String getRespostaOferecida() {
        return mRespostaOferecida;
    }

    public void setRespostaOferecida(String RespostaOferecida) {
        mRespostaOferecida = RespostaOferecida;
    }

}
