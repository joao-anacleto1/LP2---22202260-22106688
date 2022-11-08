package pt.ulusofona.lp2.deisiJungle;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class TestGameManager {

    @Test
    public void testGetPlayerIds() {
        GameManager jogo = new GameManager();
        jogo.reset();
        jogo.createInitialJungle(5, 20, new String[][]
                {
                        {"38" , "David Neres" , "Z"} ,
                        {"13" , "Enzo Fernandez" , "T"}
                }
        );
        //jogo.mapa.casas.get(1).jogadores.add(new Jogador(13, "Enzo Fernandez", jogo.tartaruga,20));
        System.out.println(Arrays.toString(jogo.getPlayerIds(1)));
        System.out.println(Arrays.toString(jogo.getPlayerIds(2)));
        int resultadoEsperado = 13;
        int resultadoReal = jogo.getPlayerIds(1)[0];
        assertEquals(resultadoEsperado,resultadoReal);
    }

}
