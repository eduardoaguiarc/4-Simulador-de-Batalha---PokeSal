package br.edu.ucsal.pokesal.pokesal;

import br.edu.ucsal.pokesal.enums.Status;
import br.edu.ucsal.pokesal.enums.TipoElemental;

public class Golpe {
    private String nome;
    private int poder;
    private double precisao;
    private TipoElemental tipoElemental;
    private int maximosUsos;
    private int usosRestantes;
    private Status statusAplicado;
    private double chanceStatus;

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
}
