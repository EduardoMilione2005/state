package com.remotecontrol.state;

import com.remotecontrol.remote.ControleRemoto;

public class EstadoDesligado implements EstadoControle {

    private final ControleRemoto controle;

    public EstadoDesligado(ControleRemoto controle) {
        this.controle = controle;
    }

    @Override
    public void pressioarPower() {
        controle.log("Ligando a televisão...");
        controle.setEstado(controle.getEstadoLigado());
    }

    @Override
    public void aumentarVolume() {
        controle.log("[Desligado] Botão de volume ignorado — TV desligada.");
    }

    @Override
    public void diminuirVolume() {
        controle.log("[Desligado] Botão de volume ignorado — TV desligada.");
    }

    @Override
    public void alternarMudo() {
        controle.log("[Desligado] Mudo ignorado — TV desligada.");
    }

    @Override
    public void proximoCanal() {
        controle.log("[Desligado] Troca de canal ignorada — TV desligada.");
    }

    @Override
    public void canalAnterior() {
        controle.log("[Desligado] Troca de canal ignorada — TV desligada.");
    }

    @Override
    public void abrirMenu() {
        controle.log("[Desligado] Menu indisponível — TV desligada.");
    }

    @Override
    public void confirmar() {
        controle.log("[Desligado] Confirmação ignorada — TV desligada.");
    }

    @Override
    public void ativarEspera() {
        controle.log("[Desligado] TV já está desligada; espera irrelevante.");
    }

    @Override
    public String getNome() {
        return "Desligado";
    }
}
