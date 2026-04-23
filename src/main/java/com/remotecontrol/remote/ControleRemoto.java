package com.remotecontrol.remote;

import com.remotecontrol.state.EstadoControle;
import com.remotecontrol.state.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControleRemoto {

    private EstadoControle estadoAtual;

    private final EstadoControle estadoDesligado;
    private final EstadoControle estadoLigado;
    private final EstadoControle estadoMutado;
    private final EstadoControle estadoMenu;
    private final EstadoControle estadoEspera;

    private int volume         = 30;
    private int canal          = 1;
    private int volumeAntesMudo = 30;

    private final List<String> log = new ArrayList<>();

    public ControleRemoto() {
        estadoDesligado = new EstadoDesligado(this);
        estadoLigado    = new EstadoLigado(this);
        estadoMutado    = new EstadoMutado(this);
        estadoMenu      = new EstadoMenu(this);
        estadoEspera    = new EstadoEspera(this);

        estadoAtual = estadoDesligado;
        log("Controle remoto iniciado. Estado: " + estadoAtual.getNome());
    }


    public void pressioarPower()   { estadoAtual.pressioarPower(); }
    public void aumentarVolume()   { estadoAtual.aumentarVolume(); }
    public void diminuirVolume()   { estadoAtual.diminuirVolume(); }
    public void alternarMudo()     { estadoAtual.alternarMudo(); }
    public void proximoCanal()     { estadoAtual.proximoCanal(); }
    public void canalAnterior()    { estadoAtual.canalAnterior(); }
    public void abrirMenu()        { estadoAtual.abrirMenu(); }
    public void confirmar()        { estadoAtual.confirmar(); }
    public void ativarEspera()     { estadoAtual.ativarEspera(); }


    public void setEstado(EstadoControle novoEstado) {
        log("Transição: [" + estadoAtual.getNome() + "] → [" + novoEstado.getNome() + "]");
        estadoAtual = novoEstado;
    }


    public EstadoControle getEstadoDesligado() { return estadoDesligado; }
    public EstadoControle getEstadoLigado()    { return estadoLigado; }
    public EstadoControle getEstadoMutado()    { return estadoMutado; }
    public EstadoControle getEstadoMenu()      { return estadoMenu; }
    public EstadoControle getEstadoEspera()    { return estadoEspera; }
    public EstadoControle getEstadoAtual()     { return estadoAtual; }


    public int getVolume() { return volume; }

    public void setVolume(int v) {
        volume = Math.max(0, Math.min(100, v));
        log("Volume ajustado para " + volume + ".");
    }

    public int getVolumeAntesMudo() { return volumeAntesMudo; }
    public void setVolumeAntesMudo(int v) { volumeAntesMudo = v; }


    public int getCanal() { return canal; }

    public void setCanal(int c) {
        canal = Math.max(1, c);
        log("Canal mudado para " + canal + ".");
    }


    public void log(String mensagem) {
        log.add(mensagem);
    }

    public List<String> getLog() {
        return Collections.unmodifiableList(log);
    }

    @Override
    public String toString() {
        return "ControleRemoto{estado='" + estadoAtual.getNome()
                + "', volume=" + volume
                + ", canal=" + canal + "}";
    }
}
