package com.remotecontrol.state;

import com.remotecontrol.remote.ControleRemoto;

/**
 * STATE — ConcreteState: EstadoMenu
 *
 * O menu de configurações está aberto na tela.
 * Navegar canais passa a navegar itens do menu.
 * Volume continua funcionando normalmente.
 * Confirmar seleciona o item e fecha o menu (volta a Ligado).
 * Abrir menu novamente fecha o menu (toggle).
 */
public class EstadoMenu implements EstadoControle {

    private final ControleRemoto controle;
    private int itemSelecionado = 0;

    // Itens do menu para simulação
    private static final String[] ITENS_MENU = {
        "Brilho", "Contraste", "Idioma", "Rede", "Sair"
    };

    public EstadoMenu(ControleRemoto controle) {
        this.controle = controle;
    }

    @Override
    public void pressioarPower() {
        controle.log("[Menu] Desligando TV (menu fechado automaticamente)...");
        itemSelecionado = 0;
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
        controle.log("[Menu] Mudo ativado (menu permanece aberto).");
        controle.setVolumeAntesMudo(controle.getVolume());
        controle.setVolume(0);
        // Permanece no estado Menu — mudo durante navegação
    }

    @Override
    public void proximoCanal() {
        // No menu, navega para o próximo item
        itemSelecionado = (itemSelecionado + 1) % ITENS_MENU.length;
        controle.log("[Menu] Item selecionado: " + ITENS_MENU[itemSelecionado] + ".");
    }

    @Override
    public void canalAnterior() {
        itemSelecionado = (itemSelecionado - 1 + ITENS_MENU.length) % ITENS_MENU.length;
        controle.log("[Menu] Item selecionado: " + ITENS_MENU[itemSelecionado] + ".");
    }

    @Override
    public void abrirMenu() {
        // Pressionar menu novamente fecha o menu (toggle)
        controle.log("Fechando menu de configurações...");
        itemSelecionado = 0;
        controle.setEstado(controle.getEstadoLigado());
    }

    @Override
    public void confirmar() {
        controle.log("[Menu] Confirmado: '" + ITENS_MENU[itemSelecionado] + "'. Fechando menu.");
        itemSelecionado = 0;
        controle.setEstado(controle.getEstadoLigado());
    }

    @Override
    public void ativarEspera() {
        controle.log("[Menu] Entrando em espera (menu fechado automaticamente)...");
        itemSelecionado = 0;
        controle.setEstado(controle.getEstadoEspera());
    }

    @Override
    public String getNome() {
        return "Menu";
    }

    public int getItemSelecionado() {
        return itemSelecionado;
    }

    public String getNomeItemSelecionado() {
        return ITENS_MENU[itemSelecionado];
    }

    public static String[] getItensMenu() {
        return ITENS_MENU.clone();
    }
}
