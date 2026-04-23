package com.remotecontrol;

import com.remotecontrol.remote.ControleRemoto;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("State — EstadoMutado")
class EstadoMutadoTest {

    private ControleRemoto controle;

    @BeforeEach
    void setUp() {
        controle = new ControleRemoto();
        controle.pressioarPower();
        controle.setVolume(40);
        controle.alternarMudo();
    }

    @Test
    @DisplayName("Deve estar no estado Mutado")
    void estadoMutado() {
        assertEquals("Mutado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Volume deve ser 0 enquanto mutado")
    void volumeZero() {
        assertEquals(0, controle.getVolume());
    }

    @Test
    @DisplayName("alternarMudo deve restaurar volume e voltar para Ligado")
    void desativarMudo() {
        controle.alternarMudo();
        assertEquals("Ligado", controle.getEstadoAtual().getNome());
        assertEquals(40, controle.getVolume());
    }

    @Test
    @DisplayName("Aumentar volume deve desativar mudo e incrementar")
    void aumentarVolumeDesativaMudo() {
        controle.aumentarVolume();
        assertEquals("Ligado", controle.getEstadoAtual().getNome());
        assertEquals(45, controle.getVolume());
    }

    @Test
    @DisplayName("Diminuir volume deve desativar mudo e decrementar")
    void diminuirVolumeDesativaMudo() {
        controle.diminuirVolume();
        assertEquals("Ligado", controle.getEstadoAtual().getNome());
        assertEquals(35, controle.getVolume());
    }

    @Test
    @DisplayName("Diminuir volume com volumeAntes=0 deve ficar em 0")
    void diminuirVolumeSemVolume() {
        controle.alternarMudo();
        controle.setVolume(0);
        controle.alternarMudo();
        controle.diminuirVolume();
        assertEquals(0, controle.getVolume());
    }

    @Test
    @DisplayName("Trocar canal deve funcionar normalmente enquanto mutado")
    void trocaCanalFunciona() {
        int canalAntes = controle.getCanal();
        controle.proximoCanal();
        assertEquals(canalAntes + 1, controle.getCanal());
        assertEquals("Mutado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Canal anterior deve funcionar normalmente enquanto mutado")
    void canalAnteriorFunciona() {
        controle.setCanal(5);
        controle.canalAnterior();
        assertEquals(4, controle.getCanal());
        assertEquals("Mutado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Power no estado Mutado deve desligar a TV")
    void powerDesliga() {
        controle.pressioarPower();
        assertEquals("Desligado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Abrir menu enquanto mutado deve ir para Menu")
    void abrirMenuMutado() {
        controle.abrirMenu();
        assertEquals("Menu", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Ativar espera enquanto mutado deve ir para Espera")
    void esperaMutado() {
        controle.ativarEspera();
        assertEquals("Espera", controle.getEstadoAtual().getNome());
    }
}
