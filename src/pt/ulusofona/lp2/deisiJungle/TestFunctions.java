package pt.ulusofona.lp2.deisiJungle;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class TestFunctions {

    @Test
    public void test_01_get_Player_Info() throws InvalidInitialJungleException {

        GameManager jogo = new GameManager();

        ArrayList<String> lista = new ArrayList<>();

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

        lista.add("GET");
        lista.add("PLAYER_INFO");
        lista.add("David");
        lista.add("Neres");

        String resultadoEsperado = "38 | David Neres | Tarzan | 70 | 1";
        String resultadoObtido = FunctionsKt.getPlayerInfo(jogo,lista);

        Assert.assertNotNull(resultadoObtido);
        Assert.assertEquals(resultadoEsperado,resultadoObtido);




    }
}
