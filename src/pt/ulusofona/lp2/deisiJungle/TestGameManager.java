package pt.ulusofona.lp2.deisiJungle;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestGameManager {


    @Test
    public void test_01_CreateInitialJungle_InvalidFoodID() {
        GameManager jogo = new GameManager();
        jogo.reset();

        try {
            jogo.createInitialJungle(5, new String[][]{

                            {"38", "David Neres", "Z"},
                            {"13", "Enzo Fernandez", "T"}
                    }
                    , new String[][]
                            {
                                    {null, "5"},
                                    {null, "6"}
                            }

            );


        } catch (InvalidInitialJungleException ex) {
            assertFalse(ex.isInvalidPlayer());
            assertTrue(ex.isInvalidFood());
            assertEquals("O id do tipo de alimento é inválido",ex.getMessage());
        }


    }


    @Test
    public void test_02_CreateInitialJungle_FoodInvalidPosition() {
        GameManager jogo = new GameManager();
        jogo.reset();

        try {
            jogo.createInitialJungle(5, new String[][]{

                            {"38", "David Neres", "Z"},
                            {"13", "Enzo Fernandez", "T"}
                    }
                    , new String[][]
                            {
                                    {"e", "-20"},
                                    {"e", "2"}
                            }

            );

        } catch (InvalidInitialJungleException ex) {
            assertFalse(ex.isInvalidPlayer());
            assertTrue(ex.isInvalidFood());
            assertEquals("O alimento não está posicionado dentro dos limites do terreno",ex.getMessage());
        }
    }

    @Test
    public void test_03_CreateInitialJungle_Initialization() {
        GameManager jogo = new GameManager();
        jogo.reset();

        try {
            jogo.createInitialJungle(5, new String[][]{

                            {"38", "David Neres", "Z"},
                            {"13", "Enzo Fernandez", "T"}
                    }
                    , new String[][]
                            {
                                    {"e", "3"},
                                    {"e", "2"}
                            }

            );

        } catch (InvalidInitialJungleException ex) {
            assertFalse(ex.isInvalidPlayer());
            assertTrue(ex.isInvalidFood());
            assertNull(ex.getMessage());
        }
    }


    @Test
    public void test_04_MoveCurrentPlayer()  throws InvalidInitialJungleException {
        GameManager jogo = new GameManager();
        jogo.reset();

        String[][] playersinfo = new String[2][3];

        playersinfo[0][0] = "4";
        playersinfo[0][1] = "Joao";
        playersinfo[0][2] = "E";

        playersinfo[1][0] = "2";
        playersinfo[1][1] = "Luiza";
        playersinfo[1][2] = "L";

        String[][] foodsInfo = new String[2][2];

        foodsInfo[0][0] = "c";
        foodsInfo[0][1] = "6";

        foodsInfo[1][0] = "b";
        foodsInfo[1][1] = "5";

        jogo.createInitialJungle(30, playersinfo, foodsInfo);

        int idCurrentPlayerEsperado = 2;
        int idCurrentPlayerObtido = jogo.buscarJogadorAtualID();

        Assert.assertEquals(idCurrentPlayerEsperado, idCurrentPlayerObtido);

        MovementResult move = jogo.moveCurrentPlayer(-8, false);
        Assert.assertEquals(MovementResultCode.INVALID_MOVEMENT, move.code());

        move = jogo.moveCurrentPlayer(-2, false);
        Assert.assertEquals(MovementResultCode.INVALID_MOVEMENT, move.code());

        move = jogo.moveCurrentPlayer(5, false);
        Assert.assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        move = jogo.moveCurrentPlayer(0, false);
        Assert.assertEquals(MovementResultCode.VALID_MOVEMENT, move.code());

    }

    @Test
    public void test_05_MoveCurrentPlayer()  throws InvalidInitialJungleException {
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

        String[][] foodsInfo = new String[1][2];

        foodsInfo[0][0] = "a";
        foodsInfo[0][1] = "4";

        jogo.createInitialJungle(30, playersinfo, foodsInfo);

        int idCurrentPlayerEsperado = 1;
        int idCurrentPlayerObtido = jogo.buscarJogadorAtualID();

        int energiaEsperada = 70;
        int energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        Assert.assertEquals(idCurrentPlayerEsperado, idCurrentPlayerObtido);

        MovementResult move = jogo.moveCurrentPlayer(3, false);
        Assert.assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = 76;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);


        move = jogo.moveCurrentPlayer(5, false);
        Assert.assertEquals(MovementResultCode.VALID_MOVEMENT, move.code());

        energiaEsperada = 70;
        energiaObtida = jogo.jogadores.get(1).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(0, false);
        Assert.assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        move = jogo.moveCurrentPlayer(4, false);
        Assert.assertEquals(MovementResultCode.VALID_MOVEMENT, move.code());

    }

    @Test
    public void test_07_moveCurrentPlayer_HerbivorosAndOmnivorosEatGrass()  throws InvalidInitialJungleException {
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


        String[][] foodsInfo = new String[1][2];

        foodsInfo[0][0] = "e";
        foodsInfo[0][1] = "4";


        jogo.createInitialJungle(30, playersinfo, foodsInfo);

        int energiaEsperada = 70;
        int energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(3, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (70 - (2 * 3)) + 20;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(3, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (180 - (4 * 3) + 20);
        energiaObtida = jogo.jogadores.get(1).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

    }

    @Test
    public void test_08_moveCurrentPlayer_CarnivorosEatGrass()  throws InvalidInitialJungleException {
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


        String[][] foodsInfo = new String[1][2];

        foodsInfo[0][0] = "e";
        foodsInfo[0][1] = "5";


        jogo.createInitialJungle(30, playersinfo, foodsInfo);

        int energiaEsperada = 80;
        int energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(4, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (80 - (2 * 4)) - 20;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);
    }

    @Test
    public void test_09_moveCurrentPlayer_CarnivorosAndHerbivorosDrinkWater()  throws InvalidInitialJungleException {
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


        String[][] foodsInfo = new String[1][2];

        foodsInfo[0][0] = "a";
        foodsInfo[0][1] = "5";


        jogo.createInitialJungle(30, playersinfo, foodsInfo);

        int energiaEsperada = 80;
        int energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(4, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (80 - (2 * 4)) + 15;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(4, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (180 - (4 * 4)) + 15;
        energiaObtida = jogo.jogadores.get(1).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);
    }

    @Test
    public void test_10_moveCurrentPlayer_OmnivorosDrinkWater()  throws InvalidInitialJungleException {
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


        String[][] foodsInfo = new String[1][2];

        foodsInfo[0][0] = "a";
        foodsInfo[0][1] = "4";


        jogo.createInitialJungle(30, playersinfo, foodsInfo);

        int energiaEsperada = 70;
        int energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(3, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (int) ((70 - (2 * 3)) + ((70 - (2 * 3)) * 0.20));
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

    }


    @Test
    public void test_11_moveCurrentPlayer_EatBananas_LastPlayerDontEatBecauseDontHaveMoreBananasInBunchAndInvalidMov
            ()
            throws InvalidInitialJungleException {

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


        String[][] foodsInfo = new String[1][2];

        foodsInfo[0][0] = "b";
        foodsInfo[0][1] = "6";

        jogo.createInitialJungle(30, playersinfo, foodsInfo);

        int energiaEsperada = 80;
        int energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(5, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (80 - (2 * 5) + 40);
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(5, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = 200; //200 porque (180 - (4*3)) + 40 ultrapassa os 200 e o maxEnergia = 200
        energiaObtida = jogo.jogadores.get(1).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(5, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (70 - (2 * 5)) + 40;
        energiaObtida = jogo.jogadores.get(2).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(5, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());
        //INVALID_MOVEMENT pq o passaro só pode andar 5 a 6 squares,por causa da velocidade min e max, deste modo
        //o movimento é invalido

        energiaEsperada = (70 - (4 * 5)); //JA NAO EXISTEM MAIS BANANAS NO CACHO!
        energiaObtida = jogo.jogadores.get(3).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

    }


    @Test
    public void test_12_moveCurrentPlayer_EatBananas_LastPlayerDontEatBecauseDontHaveMoreBananasInBunchAndCaughtFood()
            throws InvalidInitialJungleException {
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


        String[][] foodsInfo = new String[1][2];

        foodsInfo[0][0] = "b";
        foodsInfo[0][1] = "6";


        jogo.createInitialJungle(30, playersinfo, foodsInfo);

        int energiaEsperada = 80;
        int energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(5, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (80 - (2 * 5) + 40);
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(5, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = 200; //200 porque (180 - (4*3)) + 40 ultrapassa os 200 e o maxEnergia = 200
        energiaObtida = jogo.jogadores.get(1).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(5, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (70 - (2 * 5)) + 40;
        energiaObtida = jogo.jogadores.get(2).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(5, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());


        energiaEsperada = (70 - (4 * 5)); //JA NAO EXISTEM MAIS BANANAS NO CACHO!
        energiaObtida = jogo.jogadores.get(3).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);
    }

    @Test
    public void test_13_moveCurrentPlayer_EatBananas_BananasGastricDifficulties() throws InvalidInitialJungleException {
        GameManager jogo = new GameManager();
        jogo.reset();

        String[][] playersinfo = new String[2][4];

        playersinfo[0][0] = "1";
        playersinfo[0][1] = "Leao";
        playersinfo[0][2] = "L";
        playersinfo[0][3] = "80";

        playersinfo[1][0] = "2";
        playersinfo[1][1] = "Tarzan2";
        playersinfo[1][2] = "Z";
        playersinfo[1][3] = "70";


        String[][] foodsInfo = new String[1][2];

        foodsInfo[0][0] = "b";
        foodsInfo[0][1] = "5";


        jogo.createInitialJungle(30, playersinfo, foodsInfo);

        int energiaEsperada = 80;
        int energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(4, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (80 - (2 * 4)) + 40; //= 112
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(4, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (70 - (2 * 4)) + 40; //= 102
        energiaObtida = jogo.jogadores.get(1).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(0, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (112 + 10) - 40; //+10 porque ficou em descanso e -40 pq comeu 2 bananas
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

    }

    @Test
    public void test_14_moveCurrentPlayer_CarnivorosAndOmnivorosEatMeat() throws InvalidInitialJungleException {
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


        String[][] foodsInfo = new String[1][2];

        foodsInfo[0][0] = "c";
        foodsInfo[0][1] = "5";


        jogo.createInitialJungle(30, playersinfo, foodsInfo);

        int energiaEsperada = 80;
        int energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(4, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (80 - (2 * 4)) + 50;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(4, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (70 - (2 * 4)) + 50;
        energiaObtida = jogo.jogadores.get(1).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);


    }

    @Test
    public void test_15_moveCurrentPlayer_HerbivorosEatMeat() throws InvalidInitialJungleException {
        GameManager jogo = new GameManager();
        jogo.reset();

        String[][] playersinfo = new String[2][4];

        playersinfo[0][0] = "1";
        playersinfo[0][1] = "Elefante";
        playersinfo[0][2] = "E";
        playersinfo[0][3] = "180";

        playersinfo[1][0] = "2";
        playersinfo[1][1] = "Elefante1";
        playersinfo[1][2] = "E";
        playersinfo[1][3] = "180";


        String[][] foodsInfo = new String[1][2];

        foodsInfo[0][0] = "c";
        foodsInfo[0][1] = "5";


        jogo.createInitialJungle(30, playersinfo, foodsInfo);

        int energiaEsperada = 180;
        int energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(4, false);
        assertEquals(MovementResultCode.VALID_MOVEMENT, move.code()); //o elefante ignora a carne pois é herbivoro

        energiaEsperada = (180 - (4 * 4));
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(4, false);
        assertEquals(MovementResultCode.VALID_MOVEMENT, move.code()); //o elefante ignora a carne pois é herbivoro

        energiaEsperada = (180 - (4 * 4));
        energiaObtida = jogo.jogadores.get(1).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

    }

    @Test
    public void test_16_moveCurrentPlayer_CarnivorosAndOmnivorosEatMeat() throws InvalidInitialJungleException {
        GameManager jogo = new GameManager();
        jogo.reset();

        String[][] playersinfo = new String[2][4];

        playersinfo[0][0] = "1";
        playersinfo[0][1] = "Tarzan";
        playersinfo[0][2] = "Z";
        playersinfo[0][3] = "80";

        playersinfo[1][0] = "2";
        playersinfo[1][1] = "Tarzan";
        playersinfo[1][2] = "Z";
        playersinfo[1][3] = "70";


        String[][] foodsInfo = new String[1][2];

        foodsInfo[0][0] = "c";
        foodsInfo[0][1] = "5";

        jogo.createInitialJungle(30, playersinfo, foodsInfo);

        int energiaEsperada = 70;
        int energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(0, false);
        assertEquals(MovementResultCode.VALID_MOVEMENT, move.code());

        energiaEsperada = 90;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(0, false);
        assertEquals(MovementResultCode.VALID_MOVEMENT, move.code());

        energiaEsperada = 90;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(0, false);
        assertEquals(MovementResultCode.VALID_MOVEMENT, move.code());

        energiaEsperada = 110;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(0, false);
        assertEquals(MovementResultCode.VALID_MOVEMENT, move.code());

        energiaEsperada = 110;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(0, false);
        assertEquals(MovementResultCode.VALID_MOVEMENT, move.code());

        energiaEsperada = 130;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(0, false);
        assertEquals(MovementResultCode.VALID_MOVEMENT, move.code());

        energiaEsperada = 130;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(0, false);
        assertEquals(MovementResultCode.VALID_MOVEMENT, move.code());

        energiaEsperada = 150;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(0, false);
        assertEquals(MovementResultCode.VALID_MOVEMENT, move.code());

        energiaEsperada = 150;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(0, false);
        assertEquals(MovementResultCode.VALID_MOVEMENT, move.code());

        energiaEsperada = 170;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(0, false);
        assertEquals(MovementResultCode.VALID_MOVEMENT, move.code());

        energiaEsperada = 170;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(0, false);
        assertEquals(MovementResultCode.VALID_MOVEMENT, move.code());

        energiaEsperada = 190;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(0, false);
        assertEquals(MovementResultCode.VALID_MOVEMENT, move.code());

        energiaEsperada = 190;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(4, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (190 - (2 * 4)) / 2;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(4, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (190 - (2 * 4)) / 2;
        energiaObtida = jogo.jogadores.get(1).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

    }

    @Test
    public void test_17_moveCurrentPlayer_CarnivorosEatCogumelosMagicos() throws InvalidInitialJungleException {
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


        String[][] foodsInfo = new String[1][2];

        foodsInfo[0][0] = "m";
        foodsInfo[0][1] = "5";

        jogo.createInitialJungle(30, playersinfo, foodsInfo);

        ((CogumeloMagico) jogo.mapa.buscarCasa(5).buscarAlimento()).setNrCogumelo(25);

        int energiaEsperada = 80;
        int energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(4, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (int) ((80 - (2 * 4)) - ((80 - (2 * 4)) * 0.25)); //54
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(4, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (int) ((80 - (2 * 4)) - ((80 - (2 * 4)) * 0.25)); //54
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);


    }

    @Test
    public void test_18_moveCurrentPlayer_HerbivorosEatCogumelosMagicos() throws InvalidInitialJungleException {
        GameManager jogo = new GameManager();
        jogo.reset();

        String[][] playersinfo = new String[2][4];

        playersinfo[0][0] = "1";
        playersinfo[0][1] = "Elefante1";
        playersinfo[0][2] = "E";
        playersinfo[0][3] = "180";

        playersinfo[1][0] = "2";
        playersinfo[1][1] = "Elefante2";
        playersinfo[1][2] = "E";
        playersinfo[1][3] = "180";

        String[][] foodsInfo = new String[1][2];

        foodsInfo[0][0] = "m";
        foodsInfo[0][1] = "5";

        jogo.createInitialJungle(30, playersinfo, foodsInfo);

        ((CogumeloMagico) jogo.mapa.buscarCasa(5).buscarAlimento()).setNrCogumelo(25);

        int energiaEsperada = 180;
        int energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(4, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (int) ((180 - (4 * 4)) - ((180 - (4 * 4)) * 0.25)); //54
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        move = jogo.moveCurrentPlayer(4, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = (int) ((180 - (4 * 4)) - ((180 - (4 * 4)) * 0.25)); //54
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);
    }

    @Test
    public void test_19_moveCurrentPlayer_OmnivorosEatCogumelosMagicos() throws InvalidInitialJungleException {
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

        String[][] foodsInfo = new String[1][2];

        foodsInfo[0][0] = "m";
        foodsInfo[0][1] = "5";

        jogo.createInitialJungle(30, playersinfo, foodsInfo);

        ((CogumeloMagico) jogo.mapa.buscarCasa(5).buscarAlimento()).setNrCogumelo(25);

        int energiaEsperada = 70;
        int energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(4, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        energiaEsperada = 47;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);


    }

    @Test
    public void test_20_CreateInitialJungle_InvalidPlayersId() {
        GameManager jogo = new GameManager();
        jogo.reset();

        try{
            jogo.createInitialJungle(5, new String[][]{

                            {"1", "David Neres", "Z"},
                            {"1", "Enzo Fernandez", "T"}
                    }
                    , new String[][]
                            {
                                    {"e", "3"},
                                    {"e", "4"}
                            }

            );

        }catch (InvalidInitialJungleException ex){
            assertTrue(ex.isInvalidPlayer());
            assertFalse(ex.isInvalidFood());
            assertEquals("Id de jogador inválido",ex.getMessage());
        }

    }

    @Test
    public void test_21_CreateInitialJungle_InvalidPlayersId() {
        GameManager jogo = new GameManager();
        jogo.reset();

        try{
            jogo.createInitialJungle(5, new String[][]{

                            {"100000000000000000000123243342431321312231", "David Neres", "Z"},
                            {"2", "Enzo Fernandez", "T"}
                    }
                    , new String[][]
                            {
                                    {"e", "3"},
                                    {"e", "4"}
                            }

            );

        }catch (InvalidInitialJungleException ex){
            assertTrue(ex.isInvalidPlayer());
            assertFalse(ex.isInvalidFood());
            assertEquals("Id de jogador inválido",ex.getMessage());
        }

    }


    @Test
    public void test_22_CreateInitialJungle_NamePlayersNull() {
        GameManager jogo = new GameManager();
        jogo.reset();

        try{
            jogo.createInitialJungle(5, new String[][]{

                            {"1", null, "Z"},
                            {"2", "Enzo Fernandez", "T"}
                    }
                    , new String[][]
                            {
                                    {"e", "3"},
                                    {"e", "4"}
                            }

            );

        }catch (InvalidInitialJungleException ex){
            assertTrue(ex.isInvalidPlayer());
            assertFalse(ex.isInvalidFood());
            assertEquals("Nome de jogador inválido",ex.getMessage());
        }

    }

    @Test
    public void test_23_CreateInitialJungle_NoneNamePlayers() {
        GameManager jogo = new GameManager();
        jogo.reset();

        try{
            jogo.createInitialJungle(5, new String[][]{

                            {"1", "Joao Felix", "Z"},
                            {"2", "", "T"}
                    }
                    , new String[][]
                            {
                                    {"e", "3"},
                                    {"e", "4"}
                            }

            );

        }catch (InvalidInitialJungleException ex){
            assertTrue(ex.isInvalidPlayer());
            assertFalse(ex.isInvalidFood());
            assertEquals("Nome de jogador inválido",ex.getMessage());
        }

    }

    @Test
    public void test_24_CreateInitialJungle_InvalidSpecie() {
        GameManager jogo = new GameManager();
        jogo.reset();

        try{
            jogo.createInitialJungle(5, new String[][]{

                            {"1", "Pepe Porto", "C"},
                            {"2", "Enzo Fernandez", "T"}
                    }
                    , new String[][]
                            {
                                    {"e", "3"},
                                    {"e", "4"}
                            }

            );

        }catch (InvalidInitialJungleException ex){
            assertTrue(ex.isInvalidPlayer());
            assertFalse(ex.isInvalidFood());
            assertEquals("Não existe espécie válida",ex.getMessage());
        }

    }

    @Test
    public void test_25_CreateInitialJungle_PassTerrainLimits() {
        GameManager jogo = new GameManager();
        jogo.reset();
        try{
            jogo.createInitialJungle(5, new String[][]{

                            {"1", "Pepe Porto", "Z"},
                            {"2", "Enzo Fernandez", "T"}
                    }
                    , new String[][]
                            {
                                    {"e", "20"}, //fora dos limites do terreno, jungleSize = 5
                                    {"e", "4"}
                            }

            );
        }catch (InvalidInitialJungleException ex){
            assertFalse(ex.isInvalidPlayer());
            assertTrue(ex.isInvalidFood());
            assertEquals("O alimento não está posicionado dentro dos limites do terreno",ex.getMessage());
        }


    }

    @Test
    public void test_26_CreateInitialJungle_FoodInInicialPosition() {
        GameManager jogo = new GameManager();
        jogo.reset();

        try{
            jogo.createInitialJungle(5, new String[][]{

                            {"1", "Pepe Porto", "Z"},
                            {"2", "Enzo Fernandez", "T"}
                    }
                    , new String[][]
                            {
                                    {"e", "1"}, //alimento na posicao inicial
                                    {"e", "4"}
                            }

            );

        }catch (InvalidInitialJungleException ex){
            assertFalse(ex.isInvalidPlayer());
            assertTrue(ex.isInvalidFood());
            assertEquals("O alimento não está posicionado dentro dos limites do terreno",ex.getMessage());
        }

    }

    @Test
    public void test_27_CreateInitialJungle_FoodInFinalPosition() {
        GameManager jogo = new GameManager();
        jogo.reset();

        try {
            jogo.createInitialJungle(5, new String[][]{

                            {"1", "Pepe Porto", "Z"},
                            {"2", "Enzo Fernandez", "T"}
                    }
                    , new String[][]
                            {
                                    {"e", "2"},
                                    {"e", "5"} //alimento na posicao final
                            }

            );

        } catch (InvalidInitialJungleException ex) {
            assertFalse(ex.isInvalidPlayer());
            assertTrue(ex.isInvalidFood());
            assertEquals("O alimento não está posicionado dentro dos limites do terreno",ex.getMessage());
        }
    }

    @Test
    public void test_28_CreateInitialJungle_NumbersOfPlayersInvalid() {
        GameManager jogo = new GameManager();
        jogo.reset();

        try{
            jogo.createInitialJungle(5, new String[][]{

                            {"1", "Pepe Porto", "Z"}

                    }
                    , new String[][]
                            {
                                    {"e", "2"},
                                    {"e", "3"}
                            }

            );

        }catch (InvalidInitialJungleException ex){
        assertTrue(ex.isInvalidPlayer());
        assertFalse(ex.isInvalidFood());
        assertEquals("O número de jogadores não é válido",ex.getMessage());
    }

    }

    @Test
    public void test_29_CreateInitialJungle_NumbersOfPositionsInvalidForPlayers() {
        GameManager jogo = new GameManager();
        jogo.reset();

        try {
            jogo.createInitialJungle(2, new String[][]{

                            {"1", "Pepe Porto", "Z"},
                            {"2", "Alexander Bah", "T"}

                    }
                    , new String[][]
                            {
                                    {"e", "2"},
                                    {"e", "3"}
                            }

            );


        }catch (InvalidInitialJungleException ex){
            assertTrue(ex.isInvalidPlayer());
            assertFalse(ex.isInvalidFood());
            assertEquals("Não existem pelo menos duas posições para se jogar no mapa",ex.getMessage());
        }

    }

    @Test
    public void test_30_moveCurrentPlayer_MoveWitBypass()  throws InvalidInitialJungleException {
        GameManager jogo = new GameManager();
        jogo.reset();

        String[][] playersinfo = new String[2][4];

        playersinfo[0][0] = "1";
        playersinfo[0][1] = "Leao";
        playersinfo[0][2] = "L";
        playersinfo[0][3] = "80";

        playersinfo[1][0] = "2";
        playersinfo[1][1] = "Tarzan2";
        playersinfo[1][2] = "Z";
        playersinfo[1][3] = "70";


        String[][] foodsInfo = new String[1][2];

        foodsInfo[0][0] = "b";
        foodsInfo[0][1] = "8";

        jogo.createInitialJungle(30, playersinfo, foodsInfo);


        MovementResult move = jogo.moveCurrentPlayer(7, true);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

    }

    @Test
    public void test_31_moveCurrentPlayer_EatCogumelos()  throws InvalidInitialJungleException {
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


        String[][] foodsInfo = new String[1][2];

        foodsInfo[0][0] = "m";
        foodsInfo[0][1] = "4";


        jogo.createInitialJungle(30, playersinfo, foodsInfo);

        int energiaEsperada = 70;
        int energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(3, false);
        assertEquals(MovementResultCode.CAUGHT_FOOD, move.code());

        Alimento alimento = jogo.mapa.buscarCasa(4).buscarAlimento();
        int nrRandom = ((CogumeloMagico) alimento).buscarNrCogumelo();

        energiaEsperada = (70 - (3 * 2)) - ((int) ((70 - (3 * 2)) * (nrRandom / 100.0)));
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

    }

    @Test
    public void test_32_moveCurrentPlayer_Fora_Do_Mapa()  throws InvalidInitialJungleException {
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


        String[][] foodsInfo = new String[1][2];

        foodsInfo[0][0] = "m";
        foodsInfo[0][1] = "4";


        jogo.createInitialJungle(8, playersinfo, foodsInfo);

        int energiaEsperada = 70;
        int energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(8, false);
        assertEquals(MovementResultCode.INVALID_MOVEMENT, move.code());

    }

    @Test
    public void test_33_moveCurrentPlayer_Unicornio_Doesnt_Eat_01()  throws InvalidInitialJungleException {
        GameManager jogo = new GameManager();
        jogo.reset();

        String[][] playersinfo = new String[2][4];

        playersinfo[0][0] = "1";
        playersinfo[0][1] = "Unicornio1";
        playersinfo[0][2] = "U";
        playersinfo[0][3] = "200";

        playersinfo[1][0] = "2";
        playersinfo[1][1] = "Unicornio2";
        playersinfo[1][2] = "U";
        playersinfo[1][3] = "200";


        String[][] foodsInfo = new String[1][2];

        foodsInfo[0][0] = "a";
        foodsInfo[0][1] = "4";


        jogo.createInitialJungle(30, playersinfo, foodsInfo);

        int energiaEsperada = 200;
        int energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(3, false);
        assertEquals(MovementResultCode.VALID_MOVEMENT, move.code());

        energiaEsperada = 176;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

    }

    @Test
    public void test_33_moveCurrentPlayer_Unicornio_Doesnt_Eat_02()  throws InvalidInitialJungleException {
        GameManager jogo = new GameManager();
        jogo.reset();

        String[][] playersinfo = new String[2][4];

        playersinfo[0][0] = "1";
        playersinfo[0][1] = "Unicornio1";
        playersinfo[0][2] = "U";
        playersinfo[0][3] = "200";

        playersinfo[1][0] = "2";
        playersinfo[1][1] = "Unicornio2";
        playersinfo[1][2] = "U";
        playersinfo[1][3] = "200";


        String[][] foodsInfo = new String[1][2];

        foodsInfo[0][0] = "a";
        foodsInfo[0][1] = "4";


        jogo.createInitialJungle(30, playersinfo, foodsInfo);

        int energiaEsperada = 200;
        int energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

        MovementResult move = jogo.moveCurrentPlayer(4, false);
        assertEquals(MovementResultCode.VALID_MOVEMENT, move.code());

        energiaEsperada = 170;
        energiaObtida = jogo.jogadores.get(0).buscarEnergia();
        assertEquals(energiaEsperada, energiaObtida);

    }




}
