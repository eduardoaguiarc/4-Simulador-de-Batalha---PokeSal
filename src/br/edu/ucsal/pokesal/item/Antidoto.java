package br.edu.ucsal.pokesal.item;

import br.edu.ucsal.pokesal.pokesal.Pokesal;

public class Antidoto extends Item {

    public Antidoto(int id, String nome) {
        super(id, nome);
    }

    @Override
    public boolean podeUsar(Pokesal pokesal) {
        return !pokesal.estaDerrotado();
    }

    @Override
    public void aplicarEfeito(Pokesal pokesal) {
        if (podeUsar(pokesal)) {
            pokesal.removerStatus();
        } else {
            System.out.println("Não foi possível aplicar efeito.");
        }
    }
}
