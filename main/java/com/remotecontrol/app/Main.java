package com.remotecontrol.app;

import com.remotecontrol.remote.ControleRemoto;

public class Main {

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║  Controle Remoto — Padrão State          ║");
        System.out.println("╚══════════════════════════════════════════╝\n");

        ControleRemoto controle = new ControleRemoto();

        secao("1. TV DESLIGADA — botões ignorados");
        controle.aumentarVolume();
        controle.proximoCanal();
        controle.abrirMenu();

        secao("2. LIGANDO A TV");
        controle.pressioarPower();
        controle.aumentarVolume();
        controle.aumentarVolume();
        controle.proximoCanal();
        controle.proximoCanal();
        controle.proximoCanal();
        System.out.println("  " + controle);

        secao("3. ATIVANDO MUDO");
        controle.alternarMudo();
        controle.proximoCanal();           // canal ainda funciona
        System.out.println("  " + controle);

        secao("4. DESATIVANDO MUDO (via volume)");
        controle.aumentarVolume();
        System.out.println("  " + controle);

        secao("5. MENU DE CONFIGURAÇÕES");
        controle.abrirMenu();
        controle.proximoCanal();           // navega item do menu
        controle.proximoCanal();
        controle.confirmar();              // confirma e fecha menu
        System.out.println("  " + controle);

        secao("6. MODO DE ESPERA (STANDBY)");
        controle.ativarEspera();
        controle.abrirMenu();             // ignorado em standby
        controle.alternarMudo();          // ignorado em standby
        controle.proximoCanal();          // acorda a TV e muda canal
        System.out.println("  " + controle);

        secao("7. DESLIGANDO");
        controle.pressioarPower();
        System.out.println("  " + controle);

        secao("LOG COMPLETO DE EVENTOS");
        controle.getLog().forEach(e -> System.out.println("  › " + e));
    }

    private static void secao(String titulo) {
        System.out.println("\n── " + titulo + " ──");
    }
}
