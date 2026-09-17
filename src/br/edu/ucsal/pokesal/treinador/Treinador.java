package br.edu.ucsal.pokesal.treinador;

import br.edu.ucsal.pokesal.item.Item;
import br.edu.ucsal.pokesal.pokesal.Pokesal;

public class Treinador {
    private int id;
    private String nome;
    private Pokesal pokesal;
    private Mochila mochila;

    public Treinador(int id, String nome, Pokesal pokesal, Mochila mochila) {
        this.id = id;
        this.nome = nome;
        this.pokesal = pokesal;
        this.mochila = mochila;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Pokesal getPokesal() {
        return pokesal;
    }

    public int getId() {
        return id;
    }

    public Mochila getMochila() {
        return mochila;
    }

    public void escolherPokesal(Pokesal pokesal) {
        if (pokesal == null) {
            throw new IllegalArgumentException(
                    "Você precisa escolher um Pokésal."
            );
        }

        if (this.pokesal != null) {
            throw new IllegalStateException(
                    "O treinador já escolheu seu Pokésal inicial."
            );
        }

        this.pokesal = pokesal;
    }

    public void usarItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException(
                    "O item não pode ser nulo."
            );
        }

        if (!mochila.possuiItem(item)) {
            throw new IllegalStateException(
                    "O treinador não possui esse item."
            );
        }

        if (!item.podeUsar(pokesal)) {
            throw new IllegalStateException(
                    "O item não pode ser usado neste momento."
            );
        }

        item.aplicarEfeito(pokesal);
        mochila.removerItem(item);
    }
}
