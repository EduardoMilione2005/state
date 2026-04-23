package com.remotecontrol;

import com.remotecontrol.remote.ControleRemoto;
import com.remotecontrol.state.EstadoDesligado;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("State — EstadoDesligado")
class EstadoDesligadoTest {

    private ControleRemoto controle;

    @BeforeEach
    void setUp() {
        controle = new ControleRemoto();
    }

    @Test
    @DisplayName("Estado inicial deve ser Desligado")
    void estadoInicial() {
        assertInstanceOf(EstadoDesligado.class, controle.getEstadoAtual());
        assertEquals("Desligado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Power deve transitar para Ligado")
    void powerLiga() {
        controle.pressioarPower();
        assertEquals("Ligado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Aumentar volume deve ser ignorado")
    void aumentarVolumeIgnorado() {
        int volumeAntes = controle.getVolume();
        controle.aumentarVolume();
        assertEquals(volumeAntes, controle.getVolume());
        assertEquals("Desligado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Diminuir volume deve ser ignorado")
    void diminuirVolumeIgnorado() {
        int volumeAntes = controle.getVolume();
        controle.diminuirVolume();
        assertEquals(volumeAntes, controle.getVolume());
    }

    @Test
    @DisplayName("Mudo deve ser ignorado")
    void mudoIgnorado() {
        controle.alternarMudo();
        assertEquals("Desligado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Próximo canal deve ser ignorado")
    void proximoCanalIgnorado() {
        int canalAntes = controle.getCanal();
        controle.proximoCanal();
        assertEquals(canalAntes, controle.getCanal());
        assertEquals("Desligado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Canal anterior deve ser ignorado")
    void canalAnteriorIgnorado() {
        int canalAntes = controle.getCanal();
        controle.canalAnterior();
        assertEquals(canalAntes, controle.getCanal());
    }

    @Test
    @DisplayName("Abrir menu deve ser ignorado")
    void menuIgnorado() {
        controle.abrirMenu();
        assertEquals("Desligado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Confirmar deve ser ignorado")
    void confirmarIgnorado() {
        controle.confirmar();
        assertEquals("Desligado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Ativar espera deve ser ignorado")
    void esperaIgnorada() {
        controle.ativarEspera();
        assertEquals("Desligado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Log deve registrar tentativas ignoradas")
    void logRegistraIgnorados() {
        controle.aumentarVolume();
        controle.proximoCanal();
        long ignorados = controle.getLog().stream()
                .filter(e -> e.contains("ignorad") || e.contains("indisponível") || e.contains("irrelevante"))
                .count();
        assertTrue(ignorados >= 2);
    }
}
