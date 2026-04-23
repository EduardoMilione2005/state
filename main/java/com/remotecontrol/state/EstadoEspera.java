package com.remotecontrol.state;

import com.remotecontrol.remote.ControleRemoto;

public class EstadoEspera implements EstadoControle {

    private final ControleRemoto controle;

    public EstadoEspera(ControleRemoto controle) {
        this.controle = controle;
    }

    @Override
    public void pressioarPower() {
        controle.log("Saindo do modo de espera — TV ligando...");
        controle.setEstado(controle.getEstadoLigado());
    }

    @Override
    public void aumentarVolume() {
        controle.log("Botão de volume pressionado — saindo do standby...");
        controle.setEstado(controle.getEstadoLigado());
        controle.setVolume(controle.getVolume() + 5);
    }

    @Override
    public void diminuirVolume() {
        controle.log("Botão de volume pressionado — saindo do standby...");
        controle.setEstado(controle.getEstadoLigado());
        controle.setVolume(controle.getVolume() - 5);
    }

    @Override
    public void alternarMudo() {
        controle.log("[Espera] Botão mudo ignorado em standby.");
    }

    @Override
    public void proximoCanal() {
        controle.log("Botão de canal pressionado — saindo do standby...");
        controle.setEstado(controle.getEstadoLigado());
        controle.setCanal(controle.getCanal() + 1);
    }

    @Override
    public void canalAnterior() {
        controle.log("Botão de canal pressionado — saindo do standby...");
        controle.setEstado(controle.getEstadoLigado());
        controle.setCanal(controle.getCanal() - 1);
    }

    @Override
    public void abrirMenu() {
        controle.log("[Espera] Menu indisponível em standby.");
    }

    @Override
    public void confirmar() {
        controle.log("[Espera] Confirmação ignorada em standby.");
    }

    @Override
    public void ativarEspera() {
        controle.log("[Espera] TV já está em modo de espera.");
    }

    @Override
    public String getNome() {
        return "Espera";
    }
}
