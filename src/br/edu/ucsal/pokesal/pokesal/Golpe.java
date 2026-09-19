package br.edu.ucsal.pokesal.pokesal;

import br.edu.ucsal.pokesal.enums.Status;
import br.edu.ucsal.pokesal.enums.TipoElemental;
import java.util.Random;

/**
 * Representa um golpe com poder, precisão, usos e aplicação de status.
 */
public class Golpe {
  private String nome;
  private int poder;
  private double precisao;
  private TipoElemental tipoElemental;
  private int maximosUsos;
  private int usosRestantes;
  private Status statusAplicado;
  private double chanceStatus;

  private static final Random RANDOM = new Random();

  /**
   * Cria um golpe e valida seus atributos.
   *
   * @param nome nome do golpe
   * @param poder poder de ataque, maior ou igual a zero
   * @param precisao probabilidade de acerto, entre zero e um
   * @param tipoElemental tipo elemental do golpe
   * @param maximosUsos quantidade máxima de usos, maior que zero
   * @param statusAplicado status que o golpe pode aplicar
   * @param chanceStatus probabilidade de aplicar o status, entre zero e um
   * @throws IllegalArgumentException se algum atributo obrigatório for inválido
   */
  public Golpe(String nome, int poder, double precisao, TipoElemental tipoElemental,
      int maximosUsos, Status statusAplicado, double chanceStatus) {

    if (nome == null || nome.isBlank()) {
      throw new IllegalArgumentException("O nome do golpe é obrigatório!");
    }
    if (poder < 0) {
      throw new IllegalArgumentException("O poder do golpe não pode ser negativo!");
    }
    if (precisao < 0.0 || precisao > 1.0) {
      throw new IllegalArgumentException("A precisão deve estar entre 0.0 e 1.0!");
    }
    if (tipoElemental == null) {
      throw new IllegalArgumentException("O tipo elemental do golpe é obrigatório!");
    }
    if (maximosUsos <= 0) {
      throw new IllegalArgumentException("O golpe deve ter ao menos 1 uso permitido!");
    }
    if (chanceStatus < 0.0 || chanceStatus > 1.0) {
      throw new IllegalArgumentException("A chance de status deve estar entre 0.0 e 1.0!");
    }

    this.nome = nome;
    this.poder = poder;
    this.precisao = precisao;
    this.tipoElemental = tipoElemental;
    this.maximosUsos = maximosUsos;
    this.usosRestantes = maximosUsos;
    this.statusAplicado = statusAplicado;
    this.chanceStatus = chanceStatus;

    if (statusAplicado == Status.NENHUM) {
      this.chanceStatus = 0.0;
    } else {
      this.chanceStatus = chanceStatus;
    }
  }

  /**
   * Verifica se o golpe ainda possui usos disponíveis.
   *
   * @return verdadeiro quando há pelo menos um uso restante
   */
  public boolean podeUsar() {
    return usosRestantes > 0;
  }

  /**
   * Consome um dos usos disponíveis do golpe.
   *
   * @throws IllegalStateException se o golpe não possuir usos restantes
   */
  public void consumirUso() {
    if (!podeUsar()) {
      throw new IllegalStateException("O golpe " + nome + " não possui mais usos restantes!");
    }

    usosRestantes--;
  }

  /**
   * Sorteia o acerto de acordo com a precisão do golpe.
   *
   * @return verdadeiro quando o golpe acerta
   */
  public boolean testarAcerto() {
    return RANDOM.nextDouble() < precisao;
  }

  /**
   * Sorteia a aplicação do status associado ao golpe.
   *
   * @return verdadeiro quando o status deve ser aplicado
   */
  public boolean testarAplicacaoStatus() {
    if (statusAplicado == Status.NENHUM) {
      return false;
    }
    return RANDOM.nextDouble() < chanceStatus;
  }

  /**
   * Restaura a quantidade máxima de usos do golpe.
   */
  public void reiniciarUsos() {
    usosRestantes = maximosUsos;
  }

  public String getNome() {
    return nome;
  }

  public int getPoder() {
    return poder;
  }

  public double getPrecisao() {
    return precisao;
  }

  public TipoElemental getTipoElemental() {
    return tipoElemental;
  }

  public int getMaximosUsos() {
    return maximosUsos;
  }

  public int getUsosRestantes() {
    return usosRestantes;
  }

  public Status getStatusAplicado() {
    return statusAplicado;
  }

  public double getChanceStatus() {
    return chanceStatus;
  }

  @Override
  public String toString() {
    return "Golpe{"
        + "nome='" + nome + '\''
        + ", poder=" + poder
        + ", precisao=" + precisao
        + ", tipoElemental=" + tipoElemental
        + ", usosRestantes=" + usosRestantes + "/" + maximosUsos
        + ", statusAplicado=" + statusAplicado
        + ", chanceStatus=" + chanceStatus
        + '}';
  }
}
