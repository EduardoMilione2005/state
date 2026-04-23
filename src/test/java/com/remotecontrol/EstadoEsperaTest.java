package com.remotecontrol;

import com.remotecontrol.remote.ControleRemoto;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("State — EstadoEspera (Standby)")
class EstadoEsperaTest {

    private ControleRemoto controle;

    @BeforeEach
    void setUp() {
        controle = new ControleRemoto();
        controle.pressioarPower();
        controle.ativarEspera();
    }

    @Test
    @DisplayName("Deve estar no estado Espera")
    void estadoEspera() {
        assertEquals("Espera", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Power deve acordar a TV para Ligado")
    void powerAcorda() {
        controle.pressioarPower();
        assertEquals("Ligado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Aumentar volume deve acordar a TV e incrementar volume")
    void aumentarVolumeAcorda() {
        int antes = controle.getVolume();
        controle.aumentarVolume();
        assertEquals("Ligado", controle.getEstadoAtual().getNome());
        assertEquals(antes + 5, controle.getVolume());
    }

    @Test
    @DisplayName("Diminuir volume deve acordar a TV e decrementar volume")
    void diminuirVolumeAcorda() {
        int antes = controle.getVolume();
        controle.diminuirVolume();
        assertEquals("Ligado", controle.getEstadoAtual().getNome());
        assertEquals(antes - 5, controle.getVolume());
    }

    @Test
    @DisplayName("Próximo canal deve acordar a TV e mudar canal")
    void proximoCanalAcorda() {
        int canalAntes = controle.getCanal();
        controle.proximoCanal();
        assertEquals("Ligado", controle.getEstadoAtual().getNome());
        assertEquals(canalAntes + 1, controle.getCanal());
    }

    @Test
    @DisplayName("Canal anterior deve acordar a TV e mudar canal")
    void canalAnteriorAcorda() {
        controle.setCanal(5);
        controle.ativarEspera();
        controle.canalAnterior();
        assertEquals("Ligado", controle.getEstadoAtual().getNome());
        assertEquals(4, controle.getCanal());
    }

    @Test
    @DisplayName("Mudo deve ser ignorado em standby")
    void mudoIgnorado() {
        controle.alternarMudo();
        assertEquals("Espera", controle.getEstadoAtual().getNome());
        assertTrue(controle.getLog().stream().anyMatch(e -> e.contains("ignorado")));
    }

    @Test
    @DisplayName("Abrir menu deve ser ignorado em standby")
    void menuIgnorado() {
        controle.abrirMenu();
        assertEquals("Espera", controle.getEstadoAtual().getNome());
        assertTrue(controle.getLog().stream().anyMatch(e -> e.contains("indisponível")));
    }

    @Test
    @DisplayName("Confirmar deve ser ignorado em standby")
    void confirmarIgnorado() {
        controle.confirmar();
        assertEquals("Espera", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Ativar espera quando já em espera deve logar aviso")
    void esperaEmEspera() {
        controle.ativarEspera();
        assertEquals("Espera", controle.getEstadoAtual().getNome());
        assertTrue(controle.getLog().stream().anyMatch(e -> e.contains("já está em modo de espera")));
    }
}
