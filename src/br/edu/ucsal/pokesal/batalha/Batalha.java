package br.edu.ucsal.pokesal.batalha;

import br.edu.ucsal.pokesal.arena.Arena;
import br.edu.ucsal.pokesal.item.Item;
import br.edu.ucsal.pokesal.pokesal.Golpe;
import br.edu.ucsal.pokesal.treinador.Treinador;


import java.util.Random;
import java.util.Scanner;

public class Batalha {

    private static final int MAX_ITENS_POR_BATALHA = 2;
    private static final Random RANDOM = new Random();

    private int numeroTurno;
    private int itensUsadosTreinador1;
    private int itensUsadosTreinador2;
    private boolean iniciada;
    private boolean encerrada;

    public Batalha(int id, Treinador treinador1, Treinador treinador2, Arena arena, Scanner scanner) {

    }

    public void iniciarBatalha() {

    }

    public void executarTurno() {

    }

    private Treinador determinarPrimeiroAtacante() {

    }

    private void realizarAcao(Treinador treinador) {

    }

    private Golpe escolherGolpe(Treinador treinador) {

    }

    private Item escolherItem(Treinador treinador) {

    }

    private void executarAtaque(Treinador treinador, Golpe golpe) {

    }

    private int calcularDano() {

    }

    private void executarUsoItem(Treinador treinador, Item item) {

    }

    private void aplicarEfeitosFimDoTurno() {

    }

    private boolean verificarFimDaBatalha() {

    }

    private Treinador obterAdversario(Treinador treinador) {

    }

    private int quantidadeItensUsados(Treinador treinador) {

    }

    private void exibirPokesal(Treinador treinador) {

    }

    private int lerOpcao(int minimo, int maximo) {

    }
}