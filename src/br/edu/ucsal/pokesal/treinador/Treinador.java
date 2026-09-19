package br.edu.ucsal.pokesal.treinador;

import br.edu.ucsal.pokesal.item.Item;
import br.edu.ucsal.pokesal.pokesal.Pokesal;

/** Representa um treinador, seu Pokésal e a mochila de itens. */
public class Treinador {
  private int id;
  private String nome;
  private Pokesal pokesal;
  private Mochila mochila;

  /**
   * Cria um treinador com o Pokésal e a mochila informados.
   *
   * @param id identificador do treinador
   * @param nome nome do treinador
   * @param pokesal Pokésal inicial, ou {@code null} se ainda não foi escolhido
   * @param mochila mochila que armazena os itens do treinador
   */
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

  /**
   * Define o Pokésal inicial de um treinador que ainda não possui um.
   *
   * @param pokesal Pokésal escolhido pelo treinador
   * @throws IllegalArgumentException se o Pokésal for nulo
   * @throws IllegalStateException se o treinador já possuir um Pokésal
   */
  public void escolherPokesal(Pokesal pokesal) {
    if (pokesal == null) {
      throw new IllegalArgumentException("Você precisa escolher um Pokésal.");
    }

    if (this.pokesal != null) {
      throw new IllegalStateException("O treinador já escolheu seu Pokésal inicial.");
    }

    this.pokesal = pokesal;
  }

  /**
   * Aplica o efeito de um item no Pokésal e remove o item da mochila.
   *
   * @param item item da mochila que será utilizado
   * @throws IllegalArgumentException se o item for nulo
   * @throws IllegalStateException se o item não estiver na mochila ou não puder ser usado
   */
  public void usarItem(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("O item não pode ser nulo.");
    }

    if (!mochila.possuiItem(item)) {
      throw new IllegalStateException("O treinador não possui esse item.");
    }

    if (!item.podeUsar(pokesal)) {
      throw new IllegalStateException("O item não pode ser usado neste momento.");
    }

    item.aplicarEfeito(pokesal);
    mochila.removerItem(item);
  }
}
