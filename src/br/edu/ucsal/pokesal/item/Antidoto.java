package br.edu.ucsal.pokesal.item;

import br.edu.ucsal.pokesal.pokesal.Pokesal;

/** Item que remove o status de um Pokesal que ainda pode batalhar. */
public class Antidoto extends Item {

  /**
   * Cria um antídoto com o identificador e o nome informados.
   *
   * @param id identificador do item
   * @param nome nome de exibição do item
   */
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
