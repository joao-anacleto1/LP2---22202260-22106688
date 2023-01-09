package pt.ulusofona.lp2.deisiJungle;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class TestFunctions {

    @Test
    public void test_01_get_Player_Info() throws InvalidInitialJungleException {

        GameManager jogo = new GameManager();

        String lista ;

        FunctionsKt.router();

        jogo.createInitialJungle(5, new String[][]{

                        {"38", "David Neres", "Z"},
                        {"13", "Enzo Fernandez", "T"}
                }
                , new String[][]
                        {
                                {"a", "2"},
                                {"a", "3"}
                        }

        );


        lista = "David Neres";

        String resultadoEsperado = "38 | David Neres | Tarzan | 70 | 1";
        String resultadoObtido = FunctionsKt.get_Player_Info(jogo,lista);

        Assert.assertNotNull(resultadoObtido);
        Assert.assertEquals(resultadoEsperado,resultadoObtido);
    }



    @Test
    public void test_02_get_Top_Energetic_Omnivores() throws InvalidInitialJungleException {

        GameManager jogo = new GameManager();

        String lista = "";

        FunctionsKt.router();

        jogo.createInitialJungle(5, new String[][]{

                        {"38", "David Neres", "Z"},
                        {"13", "Enzo Fernandez", "T"}
                }
                , new String[][]
                        {
                                {"a", "2"},
                                {"a", "3"}
                        }

        );

        jogo.moveCurrentPlayer(2,false);
        jogo.moveCurrentPlayer(3,false);


        lista = "2";

        String resultadoEsperado = "Enzo Fernandez:177" + "\n" + "David Neres:64";
        String resultadoObtido = FunctionsKt.get_Top_Energetic_Omnivoros(jogo,lista);

        Assert.assertNotNull(resultadoObtido);
        Assert.assertEquals(resultadoEsperado,resultadoObtido);
    }


    @Test
    public void test_03_get_Most_Travelled() throws InvalidInitialJungleException {

        GameManager jogo = new GameManager();

        String lista = "";

        FunctionsKt.router();

        jogo.createInitialJungle(8, new String[][]{

                        {"10", "David Neres", "Z"},
                        {"11", "Enzo Fernandez", "T"},
                        {"12", "Joao Mario","Z"}
                }
                , new String[][]
                        {
                                {"a", "2"},
                                {"a", "3"}
                        }

        );

        jogo.moveCurrentPlayer(2,false);
        jogo.moveCurrentPlayer(3,false);
        jogo.moveCurrentPlayer(4,false);


        String resultadoEsperado = "Joao Mario:Z:4" + "\n" + "Enzo Fernandez:T:3"+ "\n" + "David Neres:Z:2"+
                "\n" + "Total:9";
        String resultadoObtido = FunctionsKt.get_most_traveled(jogo,lista);

        Assert.assertNotNull(resultadoObtido);
        Assert.assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void test_04_get_Players_By_Species() throws InvalidInitialJungleException {

        GameManager jogo = new GameManager();

        String especie = "E";

        FunctionsKt.router();

        jogo.createInitialJungle(8, new String[][]{

                        {"10", "Igor", "E"},
                        {"11", "Luiza", "E"},
                        {"12", "Pedro","Z"},
                        {"13", "Joao", "E"}
                }
                , new String[][]
                        {
                                {"a", "2"},
                                {"a", "3"}
                        }

        );


        String resultadoEsperado = "Luiza,Joao,Igor";
        String resultadoObtido = FunctionsKt.getPlayersBySpecies(jogo,especie);

        Assert.assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void test_05_get_Players_By_Species_Invalid_Species() throws InvalidInitialJungleException {

        GameManager jogo = new GameManager();

        String especie = "D";

        FunctionsKt.router();

        jogo.createInitialJungle(8, new String[][]{

                        {"10", "Igor", "E"},
                        {"11", "Luiza", "E"},
                        {"12", "Pedro","Z"},
                        {"13", "Joao", "E"}
                }
                , new String[][]
                        {
                                {"a", "2"},
                                {"a", "3"}
                        }

        );


        String resultadoEsperado = "";
        String resultadoObtido = FunctionsKt.getPlayersBySpecies(jogo,especie);

        Assert.assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void test_06_get_Players_By_Species_No__Player_Associated() throws InvalidInitialJungleException {

        GameManager jogo = new GameManager();

        String especie = "E";

        FunctionsKt.router();

        jogo.createInitialJungle(8, new String[][]{

                        {"10", "Igor", "L"},
                        {"11", "Luiza", "L"},
                        {"12", "Pedro","Z"},
                        {"13", "Joao", "P"}
                }
                , new String[][]
                        {
                                {"a", "2"},
                                {"a", "3"}
                        }

        );


        String resultadoEsperado = "";
        String resultadoObtido = FunctionsKt.getPlayersBySpecies(jogo,especie);

        Assert.assertEquals(resultadoEsperado,resultadoObtido);
    }


    @Test
    public void test_07_post_Move_Invalid_Movement() throws InvalidInitialJungleException {

        GameManager jogo = new GameManager();

        String param = "10";

        FunctionsKt.router();

        jogo.createInitialJungle(8, new String[][]{

                        {"10", "Igor", "L"},
                        {"11", "Luiza", "L"},
                        {"12", "Pedro","Z"},
                        {"13", "Joao", "P"}
                }
                , new String[][]
                        {
                                {"a", "2"},
                                {"a", "3"}
                        }

        );




        String resultadoEsperado = "Movimento invalido";
        String resultadoObtido = FunctionsKt.post_move(jogo, param);

        Assert.assertEquals(resultadoEsperado,resultadoObtido);
    }

}
