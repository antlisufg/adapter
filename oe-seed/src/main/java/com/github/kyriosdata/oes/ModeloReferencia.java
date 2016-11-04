/*
 * Copyright (c) 2016 Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.oes;

import com.github.kyriosdata.seed.Seed;

public class ModeloReferencia {

    public static final int DV_BOOLEAN = 0;

    // Referência para a raiz.
    private int raiz = -1;

    // Serviço de serialização
    private Seed seed;

    /**
     * Cria um objeto em conformidade com o tipo definido
     * pelo Modelo de Referência do openEHR.
     *
     * @param tipo Tipo do objeto (MR do openEHR).
     *
     * @return Referência para o objeto criado.
     */
    public ModeloReferencia(int tipo) {
        byte[] metaInformacao = null;
        switch (tipo) {
            case DV_BOOLEAN:
                metaInformacao = new byte[] { 1, Seed.BOOLEAN };
                break;
            default:
                throw new IllegalArgumentException("tipo");
        }

        seed = Seed.serializa(metaInformacao);
    }

    public void dvBoolean(boolean valor) {
        seed.defineBoolean(0, valor);
    }
}