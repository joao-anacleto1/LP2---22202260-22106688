package pt.ulusofona.lp2.deisiJungle;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

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
        jogo.mapa.casas.get(1).jogadores.add(new Jogador(13, "Enzo Fernandez", jogo.tartaruga,20,1));
        int resultadoEsperado = 13;
        int resultadoReal = jogo.getPlayerIds(1)[1];
        assertEquals(resultadoEsperado,resultadoReal);
    }

     @Test
     public void test01MovePlayer() {
         GameManager jogo = new GameManager();
         jogo.reset();
         jogo.createInitialJungle(10, 20, new String[][]
                 {
                         {"38" , "David Neres" , "Z"} ,
                         {"13" , "Enzo Fernandez" , "T"}
                 }
         );

         // Move primeiro jogador
         assertTrue(jogo.mapa.casas.get(0).jogadores.contains(jogo.jogadores.get(0)));
         assertFalse(jogo.mapa.casas.get(3).jogadores.contains(jogo.jogadores.get(0)));
         jogo.moveCurrentPlayer(3, false);
         assertFalse(jogo.mapa.casas.get(0).jogadores.contains(jogo.jogadores.get(0)));
         assertTrue(jogo.mapa.casas.get(3).jogadores.contains(jogo.jogadores.get(0)));
     }
}


