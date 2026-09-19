package br.edu.ucsal.pokesal.enums;

/** Estados de um Pokesal e suas descrições para exibição no console. */
public enum Status {
  NENHUM("Nenhum"),
  QUEIMADO("Queimado"),
  ENVENENADO("Envenenado"),
  PARALISADO("Paralisado");

  private final String descricao;

  Status(String descricao) {
    this.descricao = descricao;
  }

  /**
   * Retorna a descrição do status para exibição no console.
   *
   * @return descrição legível do status
   */
  public String getDescricao() {
    return descricao;
  }
}
