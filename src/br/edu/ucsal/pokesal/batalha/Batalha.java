package br.edu.ucsal.pokesal.batalha;

import br.edu.ucsal.pokesal.arena.Arena;
import br.edu.ucsal.pokesal.enums.Status;
import br.edu.ucsal.pokesal.item.Item;
import br.edu.ucsal.pokesal.pokesal.Golpe;
import br.edu.ucsal.pokesal.pokesal.Pokesal;
import br.edu.ucsal.pokesal.treinador.Mochila;
import br.edu.ucsal.pokesal.treinador.Treinador;

import java.util.Random;
import java.util.Scanner;

import java.util.List;

public class Batalha {

    public static final int MAX_ITENS_POR_BATALHA = 2;
    private static final Random RANDOM = new Random();

    private int id;
    private Treinador treinador1;
    private Treinador treinador2;
    private Arena arena;
    private Scanner scanner;
    private int numeroTurno;
    private int itensUsadosTreinador1;
    private int itensUsadosTreinador2;
    private boolean iniciada;

    public Batalha(int id, Treinador treinador1, Treinador treinador2, Arena arena, Scanner scanner) {
        this.id = id;
        this.treinador1 = treinador1;
        this.treinador2 = treinador2;
        this.arena = arena;
        this.scanner = scanner;
        this.numeroTurno = 0;
        this.itensUsadosTreinador1 = 0;
        this.itensUsadosTreinador2 = 0;
        this.iniciada = false;
    }

    public void iniciarBatalha() {
        iniciada = true;
        numeroTurno = 0;

        System.out.println("=== BATALHA INICIADA: " + treinador1.getNome() + " vs " + treinador2.getNome() + " ===");
        System.out.println("Arena: " + arena.getNome() + " (" + arena.getTipoTerreno() + ")");

        while (!verificarFimDaBatalha()) {
            executarTurno();
        }

        Treinador vencedor = obterVencedor();
        System.out.println("\n=== BATALHA ENCERRADA ===");
        if (vencedor != null) {
            System.out.println("Vencedor: " + vencedor.getNome() + "!");
        } else {
            System.out.println("A batalha terminou em empate.");
        }
    }

    public void executarTurno() {
        numeroTurno++;
        System.out.println("\n--- Turno " + numeroTurno + " ---");

        Treinador primeiro = determinarPrimeiroAtacante();
        Treinador segundo = obterAdversario(primeiro);

        realizarAcao(primeiro);

        if (!verificarFimDaBatalha()) {
            realizarAcao(segundo);
        }

        if (!verificarFimDaBatalha()) {
            aplicarEfeitosFimDoTurno();
        }
    }

    public Treinador determinarPrimeiroAtacante() {
        Pokesal p1 = treinador1.getPokesal();
        Pokesal p2 = treinador2.getPokesal();

        int velocidade1 = p1.obterVelocidadeEfetiva();
        int velocidade2 = p2.obterVelocidadeEfetiva();

        if (velocidade1 > velocidade2) {
            return treinador1;
        } else if (velocidade2 > velocidade1) {
            return treinador2;
        } else {
            return RANDOM.nextBoolean() ? treinador1 : treinador2;
        }
    }

    public void realizarAcao(Treinador treinador) {
        if (treinador.getPokesal().estaDerrotado()) {
            return;
        }

        exibirPokesal(treinador);

        boolean podeUsarItem = quantidadeItensUsados(treinador) < MAX_ITENS_POR_BATALHA
                && !treinador.getMochila().getItens().isEmpty();

        System.out.println("1 - Atacar");
        if (podeUsarItem) {
            System.out.println("2 - Usar Item");
        } else {
            System.out.println("2 - Usar Item (indisponível)");
        }
        int opcao = lerOpcao(1, 2);

        if (opcao == 1) {
            executarAtaque(treinador);
        } else if (podeUsarItem) {
            Item item = escolherItem(treinador);
            if (item != null) {
                executarUsoItem(treinador, item);
            } else {
                System.out.println(treinador.getNome() + " cancelou o uso de item. Atacando...");
                executarAtaque(treinador);
            }
        } else {
            System.out.println("Ação indisponível. " + treinador.getNome() + " atacará.");
            executarAtaque(treinador);
        }
    }

    public Golpe escolherGolpe(Treinador treinador) {
        Pokesal pokesal = treinador.getPokesal();
        List<Golpe> golpes = pokesal.getGolpes();

        System.out.println("Escolha o golpe de " + pokesal.getNome() + ":");
        for (int i = 0; i < golpes.size(); i++) {
            Golpe golpe = golpes.get(i);
            System.out.println((i + 1) + " - " + golpe.getNome()
                    + " [Poder: " + golpe.getPoder()
                    + ", Precisão: " + (int) (golpe.getPrecisao() * 100) + "%"
                    + ", Usos: " + golpe.getUsosRestantes() + "/" + golpe.getMaximosUsos() + "]"
                    + (golpe.podeUsar() ? "" : " (sem usos restantes)"));
        }

        while (true) {
            int opcao = lerOpcao(1, golpes.size());
            Golpe escolhido = golpes.get(opcao - 1);

            if (escolhido.podeUsar()) {
                return escolhido;
            }

            System.out.println("Esse golpe não possui usos restantes. Escolha outro.");
        }
    }

    public Item escolherItem(Treinador treinador) {
        if (quantidadeItensUsados(treinador) >= MAX_ITENS_POR_BATALHA) {
            System.out.println(treinador.getNome() + " já utilizou o máximo de itens nesta batalha.");
            return null;
        }

        Mochila mochila = treinador.getMochila();
        List<Item> itens = mochila.getItens();

        if (itens.isEmpty()) {
            System.out.println(treinador.getNome() + " não possui itens na mochila.");
            return null;
        }

        System.out.println("Escolha o item de " + treinador.getNome() + ":");
        for (int i = 0; i < itens.size(); i++) {
            System.out.println((i + 1) + " - " + itens.get(i).getNome());
        }

        int opcao = lerOpcao(1, itens.size());
        return itens.get(opcao - 1);
    }

    public void executarAtaque(Treinador treinador) {
        Treinador adversario = obterAdversario(treinador);
        Golpe golpe = escolherGolpe(treinador);
        Pokesal atacante = treinador.getPokesal();
        Pokesal defensor = adversario.getPokesal();

        golpe.consumirUso();

        if (!golpe.testarAcerto()) {
            System.out.println(atacante.getNome() + " usou " + golpe.getNome() + ", mas o golpe errou!");
            return;
        }

        int dano = calcularDano(atacante, defensor, golpe);
        defensor.receberDano(dano);

        System.out.println(atacante.getNome() + " usou " + golpe.getNome() + " e causou " + dano
                + " de dano em " + defensor.getNome() + "! (HP restante: "
                + Math.max(defensor.getHpAtual(), 0) + "/" + defensor.getHpMaximo() + ")");

        if (!defensor.estaDerrotado()
                && golpe.getStatusAplicado() != null
                && golpe.getStatusAplicado() != Status.NENHUM
                && golpe.testarAplicacaoStatus()) {
            defensor.aplicarStatus(golpe.getStatusAplicado());
            System.out.println(defensor.getNome() + " foi afetado por " + golpe.getStatusAplicado() + "!");
        }
    }

    private int calcularDano(Pokesal atacante, Pokesal defensor, Golpe golpe) {
        int ataqueEfetivo = atacante.obterAtaqueEfetivo();
        double multiplicadorTipo = golpe.getTipoElemental().calcularMultiplicadorContra(defensor.getTipoElemental());
        double multiplicadorTerreno = arena.calcularMultiplicadorDano(golpe.getTipoElemental());

        double danoBase = (ataqueEfetivo + golpe.getPoder()) - defensor.getDefesa();
        if (danoBase < 1) {
            danoBase = 1;
        }

        double danoFinal = danoBase * multiplicadorTipo * multiplicadorTerreno;
        return (int) Math.round(danoFinal);
    }

    public void executarUsoItem(Treinador treinador, Item item) {
        if (quantidadeItensUsados(treinador) >= MAX_ITENS_POR_BATALHA) {
            System.out.println(treinador.getNome() + " já atingiu o limite de itens por batalha.");
            return;
        }

        Pokesal pokesal = treinador.getPokesal();

        if (!item.podeUsar(pokesal)) {
            System.out.println("O item " + item.getNome() + " não pode ser usado agora.");
            return;
        }

        item.aplicarEfeito(pokesal);
        treinador.getMochila().removerItem(item);
        registrarUsoItem(treinador);

        System.out.println(treinador.getNome() + " usou " + item.getNome() + " em " + pokesal.getNome() + "!");
    }

    private void registrarUsoItem(Treinador treinador) {
        if (treinador == treinador1) {
            itensUsadosTreinador1++;
        } else if (treinador == treinador2) {
            itensUsadosTreinador2++;
        }
    }

    public void aplicarEfeitosFimDoTurno() {
        aplicarEfeitoStatusETerreno(treinador1.getPokesal());
        aplicarEfeitoStatusETerreno(treinador2.getPokesal());
    }

    private void aplicarEfeitoStatusETerreno(Pokesal pokesal) {
        if (pokesal.estaDerrotado()) {
            return;
        }

        arena.aplicarEfeitoFimDoTurno(pokesal);

        if (!pokesal.estaDerrotado() && pokesal.obterStatus() != Status.NENHUM) {
            pokesal.getEfeitoStatus().aplicarFimDoTurno(pokesal);
        }
    }

    public boolean verificarFimDaBatalha() {
        return treinador1.getPokesal().estaDerrotado() || treinador2.getPokesal().estaDerrotado();
    }

    public Treinador obterAdversario(Treinador treinador) {
        if (treinador == treinador1) {
            return treinador2;
        } else if (treinador == treinador2) {
            return treinador1;
        }
        throw new IllegalArgumentException("Treinador não participa desta batalha.");
    }

    public int quantidadeItensUsados(Treinador treinador) {
        if (treinador == treinador1) {
            return itensUsadosTreinador1;
        } else if (treinador == treinador2) {
            return itensUsadosTreinador2;
        }
        throw new IllegalArgumentException("Treinador não participa desta batalha.");
    }

    public void exibirPokesal(Treinador treinador) {
        Pokesal pokesal = treinador.getPokesal();
        System.out.println("\n" + treinador.getNome() + " - " + pokesal.getNome()
                + " | HP: " + Math.max(pokesal.getHpAtual(), 0) + "/" + pokesal.getHpMaximo()
                + " | Status: " + pokesal.obterStatus());
    }

    public int lerOpcao(int minimo, int maximo) {
        int opcao;
        while (true) {
            System.out.print("Escolha uma opção (" + minimo + "-" + maximo + "): ");
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                if (opcao >= minimo && opcao <= maximo) {
                    return opcao;
                }
            } else {
                scanner.next();
            }
            System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private Treinador obterVencedor() {
        boolean p1Derrotado = treinador1.getPokesal().estaDerrotado();
        boolean p2Derrotado = treinador2.getPokesal().estaDerrotado();

        if (p1Derrotado && !p2Derrotado) {
            return treinador2;
        } else if (p2Derrotado && !p1Derrotado) {
            return treinador1;
        }
        return null;
    }
}