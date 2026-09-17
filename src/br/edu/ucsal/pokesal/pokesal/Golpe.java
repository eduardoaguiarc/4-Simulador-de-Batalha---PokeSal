package br.edu.ucsal.pokesal.pokesal;

import br.edu.ucsal.pokesal.enums.Status;
import br.edu.ucsal.pokesal.enums.TipoElemental;

import java.util.Random;

public class Golpe {
    private String nome;
    private int poder;
    private double precisao;
    private TipoElemental tipoElemental;
    private int maximosUsos;
    private int usosRestantes;
    private Status statusAplicado;
    private double chanceStatus;

    private static final Random RANDOM = new Random();

    public Golpe(String nome, int poder, double precisao, TipoElemental tipoElemental,
                 int maximosUsos, int usosRestantes, Status statusAplicado, double chanceStatus) {

        this.nome = nome;
        this.poder = poder;
        this.precisao = precisao;
        this.tipoElemental = tipoElemental;
        this.maximosUsos = maximosUsos;
        this.usosRestantes = usosRestantes;
        this.statusAplicado = statusAplicado;
        this.chanceStatus = chanceStatus;
    }

    public boolean podeUsar() {
        return usosRestantes > 0;
    }

    public void consumirUso() {
        if (!podeUsar()) {
            throw new IllegalStateException("O golpe " + nome + " não possui mais usos restantes!");
        }

        usosRestantes--;
    }

    public boolean testarAcerto() {
        return RANDOM.nextDouble() < precisao;
    }

    public boolean testarAplicacaoStatus() {
        if (statusAplicado == Status.NENHUM) {
            return false;
        }
        return RANDOM.nextDouble() < chanceStatus;
    }

    public void reiniciarUsos() {
        usosRestantes = maximosUsos;
    }

    public String getNome() {
        return nome;
    }

    public int getPoder() {
        return poder;
    }

    public double getPrecisao() {
        return precisao;
    }

    public TipoElemental getTipoElemental() {
        return tipoElemental;
    }

    public int getMaximosUsos() {
        return maximosUsos;
    }

    public int getUsosRestantes() {
        return usosRestantes;
    }

    public Status getStatusAplicado() {
        return statusAplicado;
    }

    public double getChanceStatus() {
        return chanceStatus;
    }

    @Override
    public String toString() {
        return "Golpe{" +
                "nome='" + nome + '\'' +
                ", poder=" + poder +
                ", precisao=" + precisao +
                ", tipoElemental=" + tipoElemental +
                ", usosRestantes=" + usosRestantes + "/" + maximosUsos +
                ", statusAplicado=" + statusAplicado +
                ", chanceStatus=" + chanceStatus +
                '}';
    }
}
