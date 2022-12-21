package pt.ulusofona.lp2.deisiJungle;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TestGameManager {


    @Test
    public void test_01_CreateInitialJungle_InvalidFoodID() {
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
    public void test_02_CreateInitialJungle_FoodInvalidPosition() {
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
    public void test_03_CreateInitialJungle_Initialization() {
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


    @Test
    public void test_04_MoveCurrentPlayer() {
        GameManager jogo = new GameManager();
        jogo.reset();

        String[][] playersinfo = new String[2][3];

        playersinfo[0][0] = "4";
        playersinfo[0][1] = "Joao";
        playersinfo[0][2] = "E";

        playersinfo[1][0] = "2";
        playersinfo[1][1] = "Luiza";
        playersinfo[1][2] = "L";

        String[][] foodsInfo =  new String[2][2];

        foodsInfo[0][0] = "c";
        foodsInfo[0][1] = "6";

        foodsInfo[1][0] = "b";
        foodsInfo[1][1] = "5";

        jogo.createInitialJungle(30,playersinfo,foodsInfo);

        int idCurrentPlayerEsperado = 2;
        int idCurrentPlayerObtido = jogo.buscarJogadorAtualID();

        Assert.assertEquals(idCurrentPlayerEsperado,idCurrentPlayerObtido);

        MovementResult move = jogo.moveCurrentPlayer(-8,false);
        Assert.assertEquals(MovementResultCode.INVALID_MOVEMENT, move.code());

        move = jogo.moveCurrentPlayer(-2,false);
        Assert.assertEquals(MovementResultCode.INVALID_MOVEMENT, move.code());

        move = jogo.moveCurrentPlayer(5,false);
        Assert.assertEquals(MovementResultCode.CAUGHT_FOOD,move.code());

        move = jogo.moveCurrentPlayer(0,false);
        Assert.assertEquals(MovementResultCode.VALID_MOVEMENT,move.code());

    }

    @Test
    public void test_05_MoveCurrentPlayer() {
        GameManager jogo = new GameManager();
        jogo.reset();

        String[][] playersinfo = new String[2][4];

        playersinfo[0][0] = "1";
        playersinfo[0][1] = "Tarzan";
        playersinfo[0][2] = "Z";
        playersinfo[0][3] = "70";

        playersinfo[1][0] = "2";
        playersinfo[1][1] = "Leaooo";
        playersinfo[1][2] = "L";
        playersinfo[1][3] = "80";

        String[][] foodsInfo =  new String[1][2];

        foodsInfo[0][0] = "a";
        foodsInfo[0][1] = "4";

        jogo.createInitialJungle(30,playersinfo,foodsInfo);

        int idCurrentPlayerEsperado = 1;
        int idCurrentPlayerObtido = jogo.buscarJogadorAtualID();

        Assert.assertEquals(idCurrentPlayerEsperado,idCurrentPlayerObtido);

        MovementResult move = jogo.moveCurrentPlayer(3,false);
        Assert.assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        move = jogo.moveCurrentPlayer(5,false);
        Assert.assertEquals(MovementResultCode.VALID_MOVEMENT,move.code());

        move = jogo.moveCurrentPlayer(0,false);
        Assert.assertEquals(MovementResultCode.CAUGHT_FOOD,move.code());

        move = jogo.moveCurrentPlayer(4,false);
        Assert.assertEquals(MovementResultCode.VALID_MOVEMENT,move.code());

    }

    @Test

    public void test_06_EatCogumelos(){
        GameManager jogo = new GameManager();
        jogo.reset();

        String[][] playersinfo = new String[2][4];

        playersinfo[0][0] = "1";
        playersinfo[0][1] = "Elefante";
        playersinfo[0][2] = "Z";
        playersinfo[0][3] = "180";


        String[][] foodsInfo =  new String[1][2];

        foodsInfo[0][0] = "m";
        foodsInfo[0][1] = "2";


        jogo.createInitialJungle(30,playersinfo,foodsInfo);

        String energiaEsperada = "180";
        String energiaObtida = playersinfo[0][2];
    }



}



