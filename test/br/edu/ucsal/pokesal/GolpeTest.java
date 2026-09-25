package br.edu.ucsal.pokesal;

import br.edu.ucsal.pokesal.enums.Status;
import br.edu.ucsal.pokesal.enums.TipoElemental;
import br.edu.ucsal.pokesal.pokesal.Golpe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GolpeTest {

    @Test
    public void testMaximoDeUsoGolpes() {
        Golpe golpe = new Golpe("Golpe Teste", 20, 1,
                TipoElemental.FOGO, 3, Status.NENHUM, 0);

        golpe.consumirUso();
        golpe.consumirUso();
        golpe.consumirUso();

        assertThrows(IllegalStateException.class, () -> golpe.consumirUso());
    }
}
