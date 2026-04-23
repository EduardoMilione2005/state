package com.remotecontrol.state;

import com.remotecontrol.remote.ControleRemoto;

/**
 * STATE — ConcreteState: EstadoMutado
 *
 * A TV está ligada, mas com o som desativado (mudo).
 * Trocar canal e abrir menu continuam funcionando.
 * Aumentar/diminuir volume desativa o mudo automaticamente.
 */
public class EstadoMutado implements EstadoControle {

    private final ControleRemoto controle;

    public EstadoMutado(ControleRemoto controle) {
        this.controle = controle;
    }

    @Override
    public void pressioarPower() {
        controle.log("Desligando a televisão (estava no mudo)...");
        controle.setEstado(controle.getEstadoDesligado());
    }

    @Override
    public void aumentarVolume() {
        // Ajustar volume desativa o mudo — comportamento intuitivo
        controle.log("Volume aumentado — mudo desativado automaticamente.");
        controle.setVolume(controle.getVolumeAntesMudo() + 5);
        controle.setEstado(controle.getEstadoLigado());
    }

    @Override
    public void diminuirVolume() {
        controle.log("Volume diminuído — mudo desativado automaticamente.");
        int novoVolume = controle.getVolumeAntesMudo() - 5;
        controle.setVolume(Math.max(0, novoVolume));
        controle.setEstado(controle.getEstadoLigado());
    }

    @Override
    public void alternarMudo() {
        controle.log("Desativando mudo — restaurando volume para " + controle.getVolumeAntesMudo() + ".");
        controle.setVolume(controle.getVolumeAntesMudo());
        controle.setEstado(controle.getEstadoLigado());
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
        controle.log("Abrindo menu (som continua mudo)...");
        controle.setEstado(controle.getEstadoMenu());
    }

    @Override
    public void confirmar() {
        controle.log("[Mutado] Nenhum menu aberto para confirmar.");
    }

    @Override
    public void ativarEspera() {
        controle.log("Entrando em espera (estava no mudo)...");
        controle.setEstado(controle.getEstadoEspera());
    }

    @Override
    public String getNome() {
        return "Mutado";
    }
}
