package br.edu.ucsal.pokesal.enums;

public enum TipoElemental {
    FOGO("Fogo"),
    AGUA("Água"),
    PLANTA("Planta");

    private String descricao;

    private static final double MULTIPLICADOR_SUPER_EFETIVO = 2.0;
    private static final double MULTIPLICADOR_POUCO_EFETIVO = 0.5;
    private static final double MULTIPLICADOR_NEUTRO = 1.0;

    TipoElemental(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public double calcularMultiplicadorContra(TipoElemental defensor) {
        if (defensor == null) {
            throw new IllegalArgumentException("O tipo do defensor é obrigatório!");
        }

        return switch (this) {
            case FOGO -> switch (defensor) {
                case PLANTA -> MULTIPLICADOR_SUPER_EFETIVO;

                case AGUA -> MULTIPLICADOR_POUCO_EFETIVO;

                case FOGO -> MULTIPLICADOR_NEUTRO;
            };

            case AGUA -> switch (defensor) {
                case FOGO -> MULTIPLICADOR_SUPER_EFETIVO;

                case PLANTA -> MULTIPLICADOR_POUCO_EFETIVO;

                case AGUA -> MULTIPLICADOR_NEUTRO;
            };

            case PLANTA -> switch (defensor) {
                case AGUA -> MULTIPLICADOR_SUPER_EFETIVO;

                case FOGO -> MULTIPLICADOR_POUCO_EFETIVO;

                case PLANTA -> MULTIPLICADOR_NEUTRO;
            };
        };
    }
}
