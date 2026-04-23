package com.remotecontrol;

import com.remotecontrol.remote.ControleRemoto;
import com.remotecontrol.state.EstadoMenu;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("State — EstadoMenu")
class EstadoMenuTest {

    private ControleRemoto controle;

    @BeforeEach
    void setUp() {
        controle = new ControleRemoto();
        controle.pressioarPower();
        controle.abrirMenu();     
    }

    @Test
    @DisplayName("Deve estar no estado Menu")
    void estadoMenu() {
        assertEquals("Menu", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Próximo canal deve navegar para próximo item do menu")
    void proximoItemMenu() {
        EstadoMenu menu = (EstadoMenu) controle.getEstadoMenu();
        int itemAntes = menu.getItemSelecionado();
        controle.proximoCanal();
        assertEquals(itemAntes + 1, menu.getItemSelecionado());
        assertEquals("Menu", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Canal anterior deve navegar para item anterior do menu")
    void itemAnteriorMenu() {
        EstadoMenu menu = (EstadoMenu) controle.getEstadoMenu();
        controle.proximoCanal(); 
        controle.canalAnterior(); 
        assertEquals(0, menu.getItemSelecionado());
    }

    @Test
    @DisplayName("Navegação do menu deve ser circular (wrap-around)")
    void navegacaoCircular() {
        EstadoMenu menu = (EstadoMenu) controle.getEstadoMenu();
        int totalItens = EstadoMenu.getItensMenu().length;
        
        for (int i = 0; i < totalItens; i++) {
            controle.proximoCanal();
        }
        assertEquals(0, menu.getItemSelecionado());
    }

    @Test
    @DisplayName("Confirmar deve fechar menu e voltar para Ligado")
    void confirmarFechaMenu() {
        controle.proximoCanal();
        controle.confirmar();
        assertEquals("Ligado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Após confirmar, itemSelecionado deve ser resetado para 0")
    void itemResetadoAposConfirmar() {
        controle.proximoCanal();
        controle.confirmar();
        controle.abrirMenu();
        EstadoMenu menu = (EstadoMenu) controle.getEstadoMenu();
        assertEquals(0, menu.getItemSelecionado());
    }

    @Test
    @DisplayName("Abrir menu novamente deve fechar (toggle) e voltar para Ligado")
    void toggleMenu() {
        controle.abrirMenu(); 
        assertEquals("Ligado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Volume deve funcionar normalmente com menu aberto")
    void volumeNoMenu() {
        int antes = controle.getVolume();
        controle.aumentarVolume();
        assertEquals(antes + 5, controle.getVolume());
        assertEquals("Menu", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Power no menu deve desligar a TV")
    void powerNoMenu() {
        controle.pressioarPower();
        assertEquals("Desligado", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Espera no menu deve fechar menu e ir para Espera")
    void esperaNoMenu() {
        controle.ativarEspera();
        assertEquals("Espera", controle.getEstadoAtual().getNome());
    }

    @Test
    @DisplayName("Log deve registrar nome do item confirmado")
    void logConfirmacao() {
        EstadoMenu menu = (EstadoMenu) controle.getEstadoMenu();
        controle.proximoCanal();
        String nomeItem = menu.getNomeItemSelecionado();
        controle.confirmar();
        assertTrue(controle.getLog().stream().anyMatch(e -> e.contains(nomeItem)));
    }
}
