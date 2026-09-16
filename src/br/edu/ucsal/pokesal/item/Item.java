package br.edu.ucsal.pokesal.item;

public abstract class Item {
    private int id;
    private String nome;

    public Item(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }


    public abstract boolean podeUsar(Pokesal pokesal);

    public abstract void aplicarEfeito(Pokesal pokesal);
}
