package com.remotecontrol;

import com.remotecontrol.remote.ControleRemoto;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("State — Integração (fluxos de transição)")
class IntegracaoStateTest {

    private ControleRemoto controle;

    @BeforeEach
    void setUp() {
        controle = new ControleRemoto();
    }

    @Test
    @DisplayName("Fluxo: Desligado → Ligado → Desligado")
    void fluxoLigarDesligar() {
        assertEquals("Desligado", controle.getEstadoAtual().getNome());
        controle.pressioarPower();
        assertEquals("Ligado", controle.getEstadoAtual().getNome());
        controle.pressioarPower();
        assertEquals("Desligado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Fluxo: Ligado → Mutado → Ligado (via alternarMudo)")
    void fluxoMutoToggle() {
        controle.pressioarPower();
        controle.setVolume(50);
        controle.alternarMudo();
        assertEquals("Mutado", controle.getEstadoAtual().getNome());
        assertEquals(0, controle.getVolume());
        controle.alternarMudo();
        assertEquals("Ligado", controle.getEstadoAtual().getNome());
        assertEquals(50, controle.getVolume());
    }

    @Test
    @DisplayName("Fluxo: Ligado → Menu → Ligado (via confirmar)")
    void fluxoMenuConfirmar() {
        controle.pressioarPower();
        controle.abrirMenu();
        assertEquals("Menu", controle.getEstadoAtual().getNome());
        controle.confirmar();
        assertEquals("Ligado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Fluxo: Ligado → Menu → Ligado (via toggle)")
    void fluxoMenuToggle() {
        controle.pressioarPower();
        controle.abrirMenu();
        controle.abrirMenu();
        assertEquals("Ligado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Fluxo: Ligado → Espera → Ligado (via power)")
    void fluxoEsperaPower() {
        controle.pressioarPower();
        controle.ativarEspera();
        assertEquals("Espera", controle.getEstadoAtual().getNome());
        controle.pressioarPower();
        assertEquals("Ligado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Fluxo: Mutado → Desligado via power")
    void fluxoMutadoDesligar() {
        controle.pressioarPower();
        controle.alternarMudo();
        assertEquals("Mutado", controle.getEstadoAtual().getNome());
        controle.pressioarPower();
        assertEquals("Desligado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Fluxo: Menu → Desligado via power")
    void fluxoMenuDesligar() {
        controle.pressioarPower();
        controle.abrirMenu();
        controle.pressioarPower();
        assertEquals("Desligado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Fluxo: Menu → Espera via ativarEspera")
    void fluxoMenuEspera() {
        controle.pressioarPower();
        controle.abrirMenu();
        controle.ativarEspera();
        assertEquals("Espera", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Fluxo: Espera → Ligado via próximo canal (preserva canal correto)")
    void fluxoEsperaCanalPreservado() {
        controle.pressioarPower();
        controle.setCanal(10);
        controle.ativarEspera();
        controle.proximoCanal();
        assertEquals("Ligado", controle.getEstadoAtual().getNome());
        assertEquals(11, controle.getCanal());
    }

    @Test
    @DisplayName("Log deve registrar todas as transições em ordem")
    void logTransicoes() {
        controle.pressioarPower();
        controle.alternarMudo();
        controle.alternarMudo();
        controle.abrirMenu();
        controle.confirmar();
        controle.ativarEspera();
        controle.pressioarPower();
        controle.pressioarPower();

        long transicoes = controle.getLog().stream()
                .filter(e -> e.startsWith("Transição:"))
                .count();

        assertEquals(8, transicoes);
    }

    @Test
    @DisplayName("Múltiplos ciclos ligar/desligar devem funcionar corretamente")
    void multiplosCiclos() {
        for (int i = 0; i < 5; i++) {
            controle.pressioarPower();
            assertEquals("Ligado", controle.getEstadoAtual().getNome());
            controle.pressioarPower();
            assertEquals("Desligado", controle.getEstadoAtual().getNome());
        }
    }

    @Test
    @DisplayName("Estado não deve mudar quando ação é ignorada")
    void estadoInalteradoQuandoIgnorado() {
        controle.aumentarVolume();
        controle.diminuirVolume();
        controle.proximoCanal();
        controle.canalAnterior();
        controle.abrirMenu();
        controle.confirmar();
        controle.alternarMudo();
        controle.ativarEspera();

        assertEquals("Desligado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("toString deve refletir estado atual")
    void toStringRefleteEstado() {
        String desligado = controle.toString();
        assertTrue(desligado.contains("Desligado"));

        controle.pressioarPower();
        String ligado = controle.toString();
        assertTrue(ligado.contains("Ligado"));
        assertTrue(ligado.contains("volume="));
        assertTrue(ligado.contains("canal="));
    }
}
