package br.edu.ucsal.pokesal.arena;

import br.edu.ucsal.pokesal.enums.TipoElemental;
import br.edu.ucsal.pokesal.enums.TipoTerreno;
import br.edu.ucsal.pokesal.pokesal.Pokesal;

public class Arena {

    private static final double BONUS_FOGO = 0.15;
    private static final double BONUS_AGUA = 0.10;
    private static final double TAXA_CURA_PLANTA = 0.05;
    private static final double MULTIPLICADOR_NEUTRO = 1.0;

    private String nome;
    private TipoTerreno tipoTerreno;

    public Arena(String nome, TipoTerreno tipoTerreno) {
        this.nome = nome;
        this.tipoTerreno = tipoTerreno;
    }

    public double calcularMultiplicadorDano(TipoElemental tipoGolpe) {
        if (tipoGolpe == null) {
            return MULTIPLICADOR_NEUTRO;
        }

        switch (tipoTerreno) {
            case ASFALTO_QUENTE -> {
                if (tipoGolpe == TipoElemental.FOGO) {
                    return MULTIPLICADOR_NEUTRO + BONUS_FOGO;
                }
            }

            case POCA_DE_CHUVA -> {
                if (tipoGolpe == TipoElemental.AGUA) {
                    return MULTIPLICADOR_NEUTRO + BONUS_AGUA;
                }
            }
        }

        return MULTIPLICADOR_NEUTRO;
    }

    public void aplicarEfeitoFimDoTurno(Pokesal alvo) {
        if (alvo == null || alvo.estaDerrotado()) {
            return;
        }

        if (tipoTerreno == TipoTerreno.CANTEIRO_CENTRAL && alvo.getTipoElemental() == TipoElemental.PLANTA) {
            int cura = (int) Math.round(alvo.getHpMaximo() * TAXA_CURA_PLANTA);
            alvo.recuperarHp(cura);
        }
    }
}
