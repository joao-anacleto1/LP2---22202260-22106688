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
                        {"38", "David Neres", "Z"},
                        {"13", "Enzo Fernandez", "T"}
                }
        );
        int resultadoEsperado = 38;
        int resultadoReal = jogo.getPlayerIds(1)[1];
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void test01MovePlayer() {
        GameManager jogo = new GameManager();
        jogo.reset();
        jogo.createInitialJungle(10, 20, new String[][]
                {
                        {"38", "David Neres", "Z"},
                        {"13", "Enzo Fernandez", "T"}
                }
        );

        // Move primeiro jogador
        assertTrue(jogo.mapa.casas.get(0).jogadores.contains(jogo.jogadores.get(0)));
        assertFalse(jogo.mapa.casas.get(3).jogadores.contains(jogo.jogadores.get(0)));
        jogo.moveCurrentPlayer(3, false);
        assertFalse(jogo.mapa.casas.get(0).jogadores.contains(jogo.jogadores.get(0)));
        assertTrue(jogo.mapa.casas.get(3).jogadores.contains(jogo.jogadores.get(0)));
    }

    @Test
    public void test02MovePlayer() {
        GameManager jogo = new GameManager();
        jogo.reset();
        jogo.createInitialJungle(10, 20, new String[][]
                {
                        {"38", "David Neres", "Z"},
                        {"13", "Enzo Fernandez", "T"}
                }
        );
        jogo.moveCurrentPlayer(3, false);
        assertTrue(jogo.mapa.casas.get(0).jogadores.contains(jogo.jogadores.get(1)));

    }

    @Test
    public void test03MovePlayer() {
        GameManager jogo = new GameManager();
        jogo.reset();
        jogo.createInitialJungle(10, 20, new String[][]
                {
                        {"9", "Gabi", "Z"},
                        {"14", "Arrascaeta", "T"}
                }
        );
        jogo.moveCurrentPlayer(3, false);
        assertFalse(jogo.mapa.casas.get(1).jogadores.contains(jogo.jogadores.get(1)));

    }

    @Test
    public void test04MovePlayer() {
        GameManager jogo = new GameManager();
        jogo.reset();
        jogo.createInitialJungle(10, 20, new String[][]
                {
                        {"9", "Gabi", "Z"},
                        {"14", "Arrascaeta", "T"}
                }
        );

        // Move primeiro jogador
        assertTrue(jogo.mapa.casas.get(0).jogadores.contains(jogo.jogadores.get(0)));
        assertFalse(jogo.mapa.casas.get(3).jogadores.contains(jogo.jogadores.get(0)));
        jogo.moveCurrentPlayer(3, false);
        assertFalse(jogo.mapa.casas.get(0).jogadores.contains(jogo.jogadores.get(0)));
        assertTrue(jogo.mapa.casas.get(3).jogadores.contains(jogo.jogadores.get(0)));
    }

    @Test
    public void test01GetWinnerInfo() {
        GameManager jogo = new GameManager();
        jogo.reset();
        jogo.createInitialJungle(10, 4, new String[][]
                {
                        {"38", "David Neres", "Z"},
                        {"13", "Enzo Fernandez", "T"}
                }
        );
        jogo.moveCurrentPlayer(3, false);
        jogo.moveCurrentPlayer(3, false);
        jogo.moveCurrentPlayer(3, false);
        jogo.moveCurrentPlayer(3, false);
        assertEquals("13",jogo.getWinnerInfo()[0]);

    }
}




