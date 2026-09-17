package br.edu.ucsal.pokesal.treinador;

import br.edu.ucsal.pokesal.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Mochila {

    private List<Item> itens;

    public Mochila() {
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("O item não pode ser nulo.");
        }

        itens.add(item);
    }

    public List<Item> getItens() {
        return List.copyOf(itens);
    }

    public boolean possuiItem(Item item) {
        if (item == null) {
            return  false;
        }

        return itens.contains(item);
    }

    public boolean removerItem(Item item) {
        if (item == null) {
            return false;
        }

        return itens.remove(item);
    }
}
