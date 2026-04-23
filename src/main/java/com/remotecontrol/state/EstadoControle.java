package com.remotecontrol.state;

public interface EstadoControle {

    void pressioarPower();

    void aumentarVolume();

    void diminuirVolume();

    void alternarMudo();

    void proximoCanal();

    void canalAnterior();

    void abrirMenu();

    void confirmar();

    void ativarEspera();

    String getNome();
}
