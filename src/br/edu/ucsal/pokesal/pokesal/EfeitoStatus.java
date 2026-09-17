package br.edu.ucsal.pokesal.pokesal;

import br.edu.ucsal.pokesal.enums.Status;

public class EfeitoStatus {

    private static final double REDUCAO_ATAQUE_QUEIMADO = 0.5;
    private static final double REDUCAO_VELOCIDADE_PARALISADO = 0.5;
    private static final double DANO_QUEIMADO_PERCENTUAL = 0.065;
    private static final double DANO_VENENO_PERCENTUAL = 0.125;

    private Status tipo;
    private int turnosAtivos;


    public EfeitoStatus(Status tipo, int turnosAtivos) {
        this.tipo = tipo;
        this.turnosAtivos = turnosAtivos;
    }

    public Status getTipo() {
        return tipo;
    }

    public void aplicarFimDoTurno(Pokesal alvo) {
        if (alvo == null || tipo == null || tipo == Status.NENHUM || alvo.estaDerrotado()) {
            return;
        }

        switch (tipo) {
            case QUEIMADO -> {
                int danoQueimadura = (int) Math.round(alvo.getHpMaximo() * DANO_QUEIMADO_PERCENTUAL);
                alvo.receberDano(danoQueimadura);
            }
            case ENVENENADO -> {
                int danoVeneno = (int) Math.round(alvo.getHpMaximo() * DANO_VENENO_PERCENTUAL);
                alvo.receberDano(danoVeneno);
            }
        }

        if (turnosAtivos > 0) {
            turnosAtivos--;
            if (turnosAtivos == 0) {
                tipo = Status.NENHUM;
            }
        }
    }

    public int calcularAtaqueEfetivo(int ataqueBase) {
        if (tipo == Status.QUEIMADO) {
            return (int) Math.round(ataqueBase * REDUCAO_ATAQUE_QUEIMADO);
        }
        return ataqueBase;
    }

    public int calcularVelocidadeEfetiva(int velocidadeBase) {
        if (tipo == Status.PARALISADO) {
            return (int) Math.round(velocidadeBase * REDUCAO_VELOCIDADE_PARALISADO);
        }
        return velocidadeBase;
    }
}
