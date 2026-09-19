package br.edu.ucsal.pokesal.app;

import br.edu.ucsal.pokesal.arena.Arena;
import br.edu.ucsal.pokesal.batalha.Batalha;
import br.edu.ucsal.pokesal.enums.Status;
import br.edu.ucsal.pokesal.enums.TipoElemental;
import br.edu.ucsal.pokesal.enums.TipoTerreno;
import br.edu.ucsal.pokesal.item.Antidoto;
import br.edu.ucsal.pokesal.item.ItemCura;
import br.edu.ucsal.pokesal.pokesal.Golpe;
import br.edu.ucsal.pokesal.pokesal.Pokesal;
import br.edu.ucsal.pokesal.treinador.Mochila;
import br.edu.ucsal.pokesal.treinador.Treinador;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("TORNEIO POKÉSAL - Estacionamento UCSal\n");

        Treinador treinador1 = cadastrarTreinador(scanner, 1);
        Treinador treinador2 = cadastrarTreinador(scanner, 2);

        Arena arena = escolherArena(scanner);

        Batalha batalha = new Batalha(1, treinador1, treinador2, arena, scanner);
        batalha.iniciarBatalha();

        scanner.close();
    }

    private static Treinador cadastrarTreinador(Scanner scanner, int id) {
        System.out.println("--- Cadastro do Treinador " + id + " ---");
        System.out.print("Digite o nome do treinador: ");
        String nome = scanner.next();

        Pokesal inicial = escolherPokesalInicial(scanner, nome);
        Mochila mochila = montarMochila();

        Treinador treinador = new Treinador(id, nome, inicial, mochila);

        System.out.println(nome + " escolheu " + inicial.getNome() + "!\n");
        return treinador;
    }

    private static Pokesal escolherPokesalInicial(Scanner scanner, String nomeTreinador) {
        System.out.println(nomeTreinador + ", escolha o grupo de Pokésal inicial:");
        System.out.println("1 - BulbaSal / CharSal / SquirtSal");
        System.out.println("2 - ChikoSal / CyndaSal / TotoSal");
        int grupo = lerOpcao(scanner, 1, 2);

        System.out.println("Escolha seu Pokésal inicial:");
        Pokesal pokesal;
        if (grupo == 1) {
            System.out.println("1 - BulbaSal (Planta)");
            System.out.println("2 - CharSal (Fogo)");
            System.out.println("3 - SquirtSal (Água)");
            int escolha = lerOpcao(scanner, 1, 3);
            pokesal = switch (escolha) {
                case 1 -> criarPokesal(1, "BulbaSal", 45, 39, 49, 45, TipoElemental.PLANTA);
                case 2 -> criarPokesal(2, "CharSal", 39, 42, 43, 65, TipoElemental.FOGO);
                default -> criarPokesal(3, "SquirtSal", 44, 38, 65, 43, TipoElemental.AGUA);
            };
        } else {
            System.out.println("1 - ChikoSal (Planta)");
            System.out.println("2 - CyndaSal (Fogo)");
            System.out.println("3 - TotoSal (Água)");
            int escolha = lerOpcao(scanner, 1, 3);
            pokesal = switch (escolha) {
                case 1 -> criarPokesal(4, "ChikoSal", 45, 39, 65, 45, TipoElemental.PLANTA);
                case 2 -> criarPokesal(5, "CyndaSal", 39, 42, 43, 65, TipoElemental.FOGO);
                default -> criarPokesal(6, "TotoSal", 50, 52, 64, 43, TipoElemental.AGUA);
            };
        }

        return pokesal;
    }

    private static Pokesal criarPokesal(int id, String nome, int hpMaximo, int ataque,
                                        int defesa, int velocidade, TipoElemental tipoElemental) {
        List<Golpe> golpes = criarGolpesIniciais(tipoElemental);
        return new Pokesal(id, nome, hpMaximo, ataque, defesa, velocidade, tipoElemental, golpes);
    }

    private static List<Golpe> criarGolpesIniciais(TipoElemental tipo) {
        List<Golpe> golpes = new ArrayList<>();

        golpes.add(new Golpe("Investida", 20, 1.0, tipo, 20, Status.NENHUM, 0.0));
        golpes.add(new Golpe("Ataque de " + tipo, 25, 0.9, tipo, 15, Status.NENHUM, 0.0));
        golpes.add(criarGolpeDeStatus(tipo));
        golpes.add(new Golpe("Investida de " + tipo, 30, 0.75, tipo, 10, Status.NENHUM, 0.0));

        return golpes;
    }

    private static Golpe criarGolpeDeStatus(TipoElemental tipo) {
        return switch (tipo) {
            case FOGO -> new Golpe("Chama", 15, 0.85, tipo, 10, Status.QUEIMADO, 0.3);
            case AGUA -> new Golpe("Onda de Trovão", 15, 0.85, tipo, 10, Status.PARALISADO, 0.3);
            case PLANTA -> new Golpe("Pó Venenoso", 15, 0.85, tipo, 10, Status.ENVENENADO, 0.3);
        };

    }

    private static Mochila montarMochila() {
        Mochila mochila = new Mochila();
        mochila.adicionarItem(new ItemCura(1, "Potion", 20));
        mochila.adicionarItem(new ItemCura(2, "Super Potion", 40));
        mochila.adicionarItem(new Antidoto(3, "Antidote"));
        return mochila;
    }

    private static Arena escolherArena(Scanner scanner) {
        System.out.println("--- Escolha da Arena ---");
        System.out.println("1 - Asfalto Quente (bônus para golpes de Fogo)");
        System.out.println("2 - Poça de Chuva / Piso Escorregadio (bônus para golpes de Água)");
        System.out.println("3 - Canteiro Central (cura Pokésal do tipo Planta)");
        int opcao = lerOpcao(scanner, 1, 3);

        TipoTerreno terreno;
        String nomeArena;
        switch (opcao) {
            case 1:
                terreno = TipoTerreno.ASFALTO_QUENTE;
                nomeArena = "Estacionamento - Asfalto Quente";
                break;
            case 2:
                terreno = TipoTerreno.POCA_DE_CHUVA;
                nomeArena = "Estacionamento - Poça de Chuva";
                break;
            default:
                terreno = TipoTerreno.CANTEIRO_CENTRAL;
                nomeArena = "Estacionamento - Canteiro Central";
                break;
        }

        System.out.println();
        return new Arena(nomeArena, terreno);
    }

    private static int lerOpcao(Scanner scanner, int minimo, int maximo) {
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
}
