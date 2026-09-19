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

  public String getDescricao() {
    return descricao;
  }
}
