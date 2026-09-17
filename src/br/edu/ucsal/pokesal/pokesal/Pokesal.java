package br.edu.ucsal.pokesal.pokesal;

import br.edu.ucsal.pokesal.enums.Status;
import br.edu.ucsal.pokesal.enums.TipoElemental;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pokesal {

    private static final int MAX_GOLPES = 4;

    private final int id;
    private final String nome;
    private int hpAtual;
    private final int hpMaximo;
    private final int ataque;
    private final int defesa;
    private final int velocidade;
    private final TipoElemental tipoElemental;
    private final List<Golpe> golpes;
    private EfeitoStatus efeitoStatus;

    public Pokesal(
            int id,
            String nome,
            int hpMaximo,
            int ataque,
            int defesa,
            int velocidade,
            TipoElemental tipoElemental,
            List<Golpe> golpes
    ) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "O nome do Pokésal é obrigatório!"
            );
        }

        if (hpMaximo <= 0) {
            throw new IllegalArgumentException(
                    "O HP máximo deve ser positivo!"
            );
        }

        if (ataque < 0 || defesa < 0 || velocidade < 0) {
            throw new IllegalArgumentException(
                    "Os atributos não podem ser negativos!"
            );
        }

        if (tipoElemental == null) {
            throw new IllegalArgumentException(
                    "O tipo elemental é obrigatório!"
            );
        }

        if (golpes == null || golpes.size() > MAX_GOLPES) {
            throw new IllegalArgumentException(
                    "Informe uma lista com até quatro golpes!"
            );
        }

        this.id = id;
        this.nome = nome;
        this.hpMaximo = hpMaximo;
        this.hpAtual = hpMaximo;
        this.ataque = ataque;
        this.defesa = defesa;
        this.velocidade = velocidade;
        this.tipoElemental = tipoElemental;
        this.golpes = new ArrayList<>();

        for (Golpe golpe : golpes) {
            adicionarGolpe(golpe);
        }

        this.efeitoStatus = new EfeitoStatus(Status.NENHUM, 0);
    }

    public void adicionarGolpe(Golpe golpe) {
        if (golpe == null) {
            throw new IllegalArgumentException(
                    "O golpe não pode ser nulo!"
            );
        }

        if (golpes.size() >= MAX_GOLPES) {
            throw new IllegalStateException(
                    "O Pokésal já possui quatro golpes!"
            );
        }

        golpes.add(golpe);
    }

    public void receberDano(int dano) {
        if (dano < 0) {
            throw new IllegalArgumentException(
                    "O dano não pode ser negativo!"
            );
        }

        hpAtual = Math.max(0, hpAtual - dano);
    }

    public void recuperarHp(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException(
                    "A quantidade de cura não pode ser negativa!"
            );
        }

        int hpFaltando = hpMaximo - hpAtual;
        int curaAplicada = Math.min(quantidade, hpFaltando);

        hpAtual += curaAplicada;
    }

    public boolean estaDerrotado() {
        return hpAtual == 0;
    }

    public void aplicarStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException(
                    "O status não pode ser nulo!"
            );
        }

        if (status == Status.NENHUM) {
            removerStatus();
            return;
        }

        efeitoStatus = new EfeitoStatus(status, 0);
    }

    public void removerStatus() {
        efeitoStatus = new EfeitoStatus(Status.NENHUM, 0);
    }

    public Status obterStatus() {
        return efeitoStatus.getTipo();
    }

    public int obterAtaqueEfetivo() {
        return efeitoStatus.calcularAtaqueEfetivo(ataque);
    }

    public int obterVelocidadeEfetiva() {
        return efeitoStatus.calcularVelocidadeEfetiva(velocidade);
    }

    public void reiniciarUsosDosGolpes() {
        for (Golpe golpe : golpes) {
            golpe.reiniciarUsos();
        }
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getHpAtual() {
        return hpAtual;
    }

    public int getHpMaximo() {
        return hpMaximo;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefesa() {
        return defesa;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public TipoElemental getTipoElemental() {
        return tipoElemental;
    }

    public List<Golpe> getGolpes() {
        return Collections.unmodifiableList(golpes);
    }

    public EfeitoStatus getEfeitoStatus() {
        return efeitoStatus;
    }
}