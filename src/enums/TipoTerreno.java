package enums;

public enum TipoTerreno {
    ASFALTO_QUENTE("Asfalto quente"),
    POCA_DE_CHUVA("Poça de chuva"),
    CANTEIRO_CENTRAL("Canteiro central");

    private final String descricao;

    TipoTerreno(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
