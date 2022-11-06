package pt.ulusofona.lp2.deisiJungle;

import org.junit.Test;

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
        jogo.mapa.casas.get(1).jogadores.add(new Jogador(13, "Enzo Fernandez", jogo.tartaruga));
        int resultadoEsperado = 13;
        int resultadoReal = jogo.getPlayerIds(2)[0];
        assertEquals(resultadoEsperado,resultadoReal);
    }

}
