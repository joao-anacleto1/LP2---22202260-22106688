package pt.ulusofona.lp2.deisiJungle;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TestGameManager {


    @Test
    public void testCreateInitialJungle_InvalidFoodID() {
        GameManager jogo = new GameManager();
        jogo.reset();
        InitializationError resultadoReal = jogo.createInitialJungle(5, new String[][]{

                        {"38", "David Neres", "Z"},
                        {"13", "Enzo Fernandez", "T"}
                }
                , new String[][]
                        {
                                {null, "5"},
                                {null, "6"}
                        }

        );
        InitializationError resultadoEsperado = new InitializationError("O id do tipo de alimento é inválido");
        assertEquals(resultadoEsperado.getMessage(), resultadoReal.getMessage());
    }


    @Test
    public void testCreateInitialJungle_FoodInvalidPosition() {
        GameManager jogo = new GameManager();
        jogo.reset();
        InitializationError resultadoReal = jogo.createInitialJungle(5, new String[][]{

                        {"38", "David Neres", "Z"},
                        {"13", "Enzo Fernandez", "T"}
                }
                , new String[][]
                        {
                                {"e", "-20"},
                                {"e", "2"}
                        }

        );
        InitializationError resultadoEsperado = new InitializationError(
                "O alimento não está posicionado dentro dos limites do terreno");

        assertEquals(resultadoEsperado.getMessage(), resultadoReal.getMessage());
    }

    @Test
    public void testCreateInitialJungle_Initialization() {
        GameManager jogo = new GameManager();
        jogo.reset();
        InitializationError resultadoReal = jogo.createInitialJungle(5, new String[][]{

                        {"38", "David Neres", "Z"},
                        {"13", "Enzo Fernandez", "T"}
                }
                , new String[][]
                        {
                                {"e", "3"},
                                {"e", "2"}
                        }

        );
        InitializationError resultadoEsperado = null;

        assertEquals(resultadoEsperado, resultadoReal);
    }

}



