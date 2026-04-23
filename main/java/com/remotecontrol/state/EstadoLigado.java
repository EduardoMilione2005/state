package com.remotecontrol.state;

import com.remotecontrol.remote.ControleRemoto;

/**
 * STATE — ConcreteState: EstadoLigado
 *
 * A TV está ligada em uso normal.
 * Todas as operações estão disponíveis.
 */
public class EstadoLigado implements EstadoControle {

    private final ControleRemoto controle;

    public EstadoLigado(ControleRemoto controle) {
        this.controle = controle;
    }

    @Override
    public void pressioarPower() {
        controle.log("Desligando a televisão...");
        controle.setEstado(controle.getEstadoDesligado());
    }

    @Override
    public void aumentarVolume() {
        controle.setVolume(controle.getVolume() + 5);
    }

    @Override
    public void diminuirVolume() {
        controle.setVolume(controle.getVolume() - 5);
    }

    @Override
    public void alternarMudo() {
        controle.log("Ativando mudo...");
        controle.setVolumeAntesMudo(controle.getVolume());
        controle.setVolume(0);
        controle.setEstado(controle.getEstadoMutado());
    }

    @Override
    public void proximoCanal() {
        controle.setCanal(controle.getCanal() + 1);
    }

    @Override
    public void canalAnterior() {
        controle.setCanal(controle.getCanal() - 1);
    }

    @Override
    public void abrirMenu() {
        controle.log("Abrindo menu de configurações...");
        controle.setEstado(controle.getEstadoMenu());
    }

    @Override
    public void confirmar() {
        controle.log("[Ligado] Nenhum menu aberto para confirmar.");
    }

    @Override
    public void ativarEspera() {
        controle.log("Entrando em modo de espera (standby)...");
        controle.setEstado(controle.getEstadoEspera());
    }

    @Override
    public String getNome() {
        return "Ligado";
    }
}
