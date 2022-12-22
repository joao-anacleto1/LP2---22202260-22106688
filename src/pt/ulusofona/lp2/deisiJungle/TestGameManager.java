package pt.ulusofona.lp2.deisiJungle;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
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

        int energiaEsperada = 70;
        int energiaObtida =  jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);

        Assert.assertEquals(idCurrentPlayerEsperado,idCurrentPlayerObtido);

        MovementResult move = jogo.moveCurrentPlayer(3,false);
        Assert.assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = 76;
        energiaObtida =  jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);


        move = jogo.moveCurrentPlayer(5,false);
        Assert.assertEquals(MovementResultCode.VALID_MOVEMENT,move.code());

        energiaEsperada = 70;
        energiaObtida =  jogo.jogadores.get(1).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);

        move = jogo.moveCurrentPlayer(0,false);
        Assert.assertEquals(MovementResultCode.CAUGHT_FOOD,move.code());

        move = jogo.moveCurrentPlayer(4,false);
        Assert.assertEquals(MovementResultCode.VALID_MOVEMENT,move.code());

    }

    @Test
    public void test_07_moveCurrentPlayer_HerbivorosAndOmnivorosEatGrass(){
        GameManager jogo = new GameManager();
        jogo.reset();

        String[][] playersinfo = new String[2][4];

        playersinfo[0][0] = "1";
        playersinfo[0][1] = "Tarzan";
        playersinfo[0][2] = "Z";
        playersinfo[0][3] = "70";

        playersinfo[1][0] = "2";
        playersinfo[1][1] = "Elefante";
        playersinfo[1][2] = "E";
        playersinfo[1][3] = "180";


        String[][] foodsInfo =  new String[1][2];

        foodsInfo[0][0] = "e";
        foodsInfo[0][1] = "4";


        jogo.createInitialJungle(30,playersinfo,foodsInfo);

        int energiaEsperada = 70;
        int energiaObtida =  jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(3,false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (70 - (2*3)) + 20;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);

        move = jogo.moveCurrentPlayer(3,false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (180 - (4*3) + 20);
        energiaObtida = jogo.jogadores.get(1).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);

    }

    @Test
    public void test_08_moveCurrentPlayer_CarnivorosEatGrass(){
        GameManager jogo = new GameManager();
        jogo.reset();

        String[][] playersinfo = new String[2][4];

        playersinfo[0][0] = "1";
        playersinfo[0][1] = "Leao1";
        playersinfo[0][2] = "L";
        playersinfo[0][3] = "80";

        playersinfo[1][0] = "2";
        playersinfo[1][1] = "Leao2";
        playersinfo[1][2] = "L";
        playersinfo[1][3] = "80";


        String[][] foodsInfo =  new String[1][2];

        foodsInfo[0][0] = "e";
        foodsInfo[0][1] = "4";


        jogo.createInitialJungle(30,playersinfo,foodsInfo);

        int energiaEsperada = 80;
        int energiaObtida =  jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(3,false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (80 - (2*3)) - 20;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);
    }

    @Test
    public void test_09_moveCurrentPlayer_CarnivorosAndHerbivorosDrinkWater(){
        GameManager jogo = new GameManager();
        jogo.reset();

        String[][] playersinfo = new String[2][4];

        playersinfo[0][0] = "1";
        playersinfo[0][1] = "Leao";
        playersinfo[0][2] = "L";
        playersinfo[0][3] = "80";

        playersinfo[1][0] = "2";
        playersinfo[1][1] = "Elefante";
        playersinfo[1][2] = "E";
        playersinfo[1][3] = "180";


        String[][] foodsInfo =  new String[1][2];

        foodsInfo[0][0] = "a";
        foodsInfo[0][1] = "4";


        jogo.createInitialJungle(30,playersinfo,foodsInfo);

        int energiaEsperada = 80;
        int energiaObtida =  jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(3,false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (80 - (2*3)) + 15;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);

        move = jogo.moveCurrentPlayer(3,false);
        assertEquals(MovementResultCode.CAUGHT_FOOD,move.code());

        energiaEsperada = (180 - (4*3)) + 15;
        energiaObtida = jogo.jogadores.get(1).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);
    }

    @Test
    public void test_10_moveCurrentPlayer_OmnivorosDrinkWater(){
        GameManager jogo = new GameManager();
        jogo.reset();

        String[][] playersinfo = new String[2][4];

        playersinfo[0][0] = "1";
        playersinfo[0][1] = "Tarzan1";
        playersinfo[0][2] = "Z";
        playersinfo[0][3] = "70";

        playersinfo[1][0] = "2";
        playersinfo[1][1] = "Tarzan2";
        playersinfo[1][2] = "Z";
        playersinfo[1][3] = "70";


        String[][] foodsInfo =  new String[1][2];

        foodsInfo[0][0] = "a";
        foodsInfo[0][1] = "4";


        jogo.createInitialJungle(30,playersinfo,foodsInfo);

        int energiaEsperada = 70;
        int energiaObtida =  jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(3,false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (int) ((70 - (2*3)) + ((70 - (2*3)) * 0.20));
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);

    }

    @Test
    public void test_11_moveCurrentPlayer_EatBananas_LastPlayerDontEatBecauseDontHaveMoreBananasInBunch(){
        GameManager jogo = new GameManager();
        jogo.reset();

        String[][] playersinfo = new String[4][4];

        playersinfo[0][0] = "1";
        playersinfo[0][1] = "Leao";
        playersinfo[0][2] = "L";
        playersinfo[0][3] = "80";

        playersinfo[1][0] = "2";
        playersinfo[1][1] = "Elefante";
        playersinfo[1][2] = "E";
        playersinfo[1][3] = "180";

        playersinfo[2][0] = "3";
        playersinfo[2][1] = "Tarzan";
        playersinfo[2][2] = "Z";
        playersinfo[2][3] = "70";

        playersinfo[3][0] = "4";
        playersinfo[3][1] = "Passaro";
        playersinfo[3][2] = "P";
        playersinfo[3][3] = "70";


        String[][] foodsInfo =  new String[1][2];

        foodsInfo[0][0] = "b";
        foodsInfo[0][1] = "4";


        jogo.createInitialJungle(30,playersinfo,foodsInfo);

        int energiaEsperada = 80;
        int energiaObtida =  jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(3,false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (80 - (2*3) + 40);
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);

        move = jogo.moveCurrentPlayer(3,false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = 200; //200 porque (180 - (4*3)) + 40 ultrapassa os 200 e o maxEnergia = 200
        energiaObtida = jogo.jogadores.get(1).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);

        move = jogo.moveCurrentPlayer(3,false);
        assertEquals(MovementResultCode.CAUGHT_FOOD,move.code());

        energiaEsperada = (70 - (2*3)) + 40;
        energiaObtida = jogo.jogadores.get(2).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);

        move = jogo.moveCurrentPlayer(3,false);
        assertEquals(MovementResultCode.CAUGHT_FOOD,move.code());

        energiaEsperada = (70 - (4*3)); //JA NAO EXISTEM MAIS BANANAS NO CACHO!
        energiaObtida = jogo.jogadores.get(3).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);
    }

    @Test
    public void test_12_moveCurrentPlayer_EatBananas_BananasGastricDifficulties(){
        GameManager jogo = new GameManager();
        jogo.reset();

        String[][] playersinfo = new String[2][4];

        playersinfo[0][0] = "1";
        playersinfo[0][1] = "Leao";
        playersinfo[0][2] = "L";
        playersinfo[0][3] = "80";

        playersinfo[1][0] = "2";
        playersinfo[1][1] = "Tarzan";
        playersinfo[1][2] = "Z";
        playersinfo[1][3] = "70";


        String[][] foodsInfo =  new String[1][2];

        foodsInfo[0][0] = "b";
        foodsInfo[0][1] = "4";


        jogo.createInitialJungle(30,playersinfo,foodsInfo);

        int energiaEsperada = 80;
        int energiaObtida =  jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(3,false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (80 - (2*3)) + 40; //= 114
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);

        move = jogo.moveCurrentPlayer(3,false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (70 - (2*3)) + 40; //= 104
        energiaObtida = jogo.jogadores.get(1).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);

        move = jogo.moveCurrentPlayer(0,false);
        assertEquals(MovementResultCode.CAUGHT_FOOD,move.code());

        energiaEsperada = (114 + 10) - 40; //+10
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);

    }
    

    @Test
    public void test_06_moveCurrentPlayer_EatCogumelos(){
        GameManager jogo = new GameManager();
        jogo.reset();

        String[][] playersinfo = new String[2][4];

        playersinfo[0][0] = "1";
        playersinfo[0][1] = "Tarzannnnnnnnnnnnnnnnnn";
        playersinfo[0][2] = "Z";
        playersinfo[0][3] = "70";

        playersinfo[1][0] = "2";
        playersinfo[1][1] = "Leaooo";
        playersinfo[1][2] = "L";
        playersinfo[1][3] = "80";


        String[][] foodsInfo =  new String[1][2];

        foodsInfo[0][0] = "m";
        foodsInfo[0][1] = "4";


        jogo.createInitialJungle(30,playersinfo,foodsInfo);

        int energiaEsperada = 70;
        int energiaObtida =  jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(3,false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        Alimento alimento = jogo.mapa.buscarCasa(4).alimento;
        int nrRandom = ((CogumelosMagicos)alimento).buscarNrCogumelo();

        energiaEsperada = (70 - (3*2)) - ((int) ((70 - (3*2)) * (nrRandom / 100.0)));
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada,energiaObtida);


    }





}



