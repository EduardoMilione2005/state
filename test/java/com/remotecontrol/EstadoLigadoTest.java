package com.remotecontrol;

import com.remotecontrol.remote.ControleRemoto;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("State — EstadoLigado")
class EstadoLigadoTest {

    private ControleRemoto controle;

    @BeforeEach
    void setUp() {
        controle = new ControleRemoto();
        controle.pressioarPower();
    }

    @Test
    @DisplayName("Deve estar no estado Ligado")
    void estadoLigado() {
        assertEquals("Ligado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Power deve transitar para Desligado")
    void powerDesliga() {
        controle.pressioarPower();
        assertEquals("Desligado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Aumentar volume deve incrementar em 5")
    void aumentarVolume() {
        int antes = controle.getVolume();
        controle.aumentarVolume();
        assertEquals(antes + 5, controle.getVolume());
    }

    @Test
    @DisplayName("Diminuir volume deve decrementar em 5")
    void diminuirVolume() {
        int antes = controle.getVolume();
        controle.diminuirVolume();
        assertEquals(antes - 5, controle.getVolume());
    }

    @Test
    @DisplayName("Volume não deve ultrapassar 100")
    void volumeMaximo() {
        controle.setVolume(98);
        controle.aumentarVolume();
        assertEquals(100, controle.getVolume());
        controle.aumentarVolume();
        assertEquals(100, controle.getVolume()); 
    }

    @Test
    @DisplayName("Volume não deve ficar abaixo de 0")
    void volumeMinimo() {
        controle.setVolume(2);
        controle.diminuirVolume();
        assertEquals(0, controle.getVolume());
        controle.diminuirVolume();
        assertEquals(0, controle.getVolume()); 
    }

    @Test
    @DisplayName("Próximo canal deve incrementar canal")
    void proximoCanal() {
        int antes = controle.getCanal();
        controle.proximoCanal();
        assertEquals(antes + 1, controle.getCanal());
    }

    @Test
    @DisplayName("Canal anterior deve decrementar canal")
    void canalAnterior() {
        controle.setCanal(5);
        controle.canalAnterior();
        assertEquals(4, controle.getCanal());
    }

    @Test
    @DisplayName("Canal nunca deve ficar abaixo de 1")
    void canalMinimo() {
        controle.setCanal(1);
        controle.canalAnterior();
        assertEquals(1, controle.getCanal());
    }

    @Test
    @DisplayName("Mudo deve transitar para EstadoMutado e zerar volume")
    void mudo() {
        controle.setVolume(50);
        controle.alternarMudo();
        assertEquals("Mutado", controle.getEstadoAtual().getNome());
        assertEquals(0, controle.getVolume());
    }

    @Test
    @DisplayName("Mudo deve salvar o volume anterior")
    void mudoSalvaVolume() {
        controle.setVolume(60);
        controle.alternarMudo();
        assertEquals(60, controle.getVolumeAntesMudo());
    }

    @Test
    @DisplayName("Abrir menu deve transitar para EstadoMenu")
    void abrirMenu() {
        controle.abrirMenu();
        assertEquals("Menu", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Ativar espera deve transitar para EstadoEspera")
    void ativarEspera() {
        controle.ativarEspera();
        assertEquals("Espera", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Confirmar sem menu aberto deve logar aviso e permanecer em Ligado")
    void confirmarSemMenu() {
        controle.confirmar();
        assertEquals("Ligado", controle.getEstadoAtual().getNome());
        assertTrue(controle.getLog().stream().anyMatch(e -> e.contains("Nenhum menu")));
    }
}
