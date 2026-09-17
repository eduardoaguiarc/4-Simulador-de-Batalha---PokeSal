package br.edu.ucsal.pokesal.item;

import br.edu.ucsal.pokesal.pokesal.Pokesal;

public class ItemCura extends Item {
    private int quantidadeCura;

    public ItemCura(int id, String nome, int quantidadeCura) {
        super(id, nome);
        this.quantidadeCura = quantidadeCura;
    }

    @Override
    public boolean podeUsar(Pokesal pokesal) {
        return !pokesal.estaDerrotado();
    }

    @Override
    public void aplicarEfeito(Pokesal pokesal) {
        if (podeUsar(pokesal)) {
            pokesal.recuperarHp(quantidadeCura);
        } else {
            System.out.println("Não foi possível aplicar efeito.");
        }
    }
}
