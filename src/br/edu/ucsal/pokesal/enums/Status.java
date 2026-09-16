package br.edu.ucsal.pokesal.enums;

public enum Status {
    /*

     Usei descrição para melhorar a visualização no console,
     vamos utilizar as constantes ainda das br.edu.ucsal.pokesal.app.enums.

     */

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
