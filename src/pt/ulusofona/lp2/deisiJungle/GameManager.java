package pt.ulusofona.lp2.deisiJungle;

import javax.swing.*;
import java.util.ArrayList;


public class GameManager {

    Especie elefante = new Especie("Elefante", 'E', "elephant.png");
    Especie leao = new Especie("Leão", 'L', "lion.png");
    Especie tartaruga = new Especie("Tartaruga", 'T', "turtle.png");
    Especie passaro = new Especie("Pássaro", 'P', "bird.png");
    Especie tarzan = new Especie("Tarzan", 'Z', "tarzan.png");

    ArrayList<Especie> especies = new ArrayList<>() {
        {
            add(elefante);
            add(leao);
            add(tartaruga);
            add(passaro);
            add(tarzan);
        }
    };


    ArrayList<Jogador> jogadores = new ArrayList<>(); // jogadores que estao a jogar

    MapaJogo mapa;


    public GameManager() {

    }

    public String[][] getSpecies() {

        String[][] result = new String[especies.size()][];

        for (int i = 0; i < especies.size(); i++) {
            result[i] = new String[]{
                    String.valueOf(especies.get(i).buscarIdentificador()),
                    especies.get(i).buscarNome(),
                    especies.get(i).buscarImagem()};
        }
        return result;
    }

    public boolean createInitialJungle(int jungleSize, int initialEnergy, String[][] playersInfo) {
        ArrayList<Integer> resultado = new ArrayList<>();

        if (!(playersInfo.length >= 2 && playersInfo.length <= 4)) {
            return false;
        }

        if (!(jungleSize >= 2 * playersInfo.length)) {
            return false;
        }

        for (int j = 0; j < playersInfo.length; j++) {
            //try catch -> trata exceçoes de forma que o programa não rebente,
            //neste caso caso o conteudo da string não seja um numero o programa não "rebenta"
            try {
                if (!resultado.contains(Integer.parseInt(playersInfo[j][0]))) {
                    resultado.add(Integer.parseInt(playersInfo[j][0]));
                } else {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
            if (playersInfo[j][1] == null || playersInfo[j][1].equals("")) {
                return false;
            }
            boolean existeEspecie = false; //verificação final para ver se tem ou não uma especie válida
            for (int i = 0; i < especies.size(); i++) {
                if (playersInfo[j][2].charAt(0) == especies.get(i).buscarIdentificador()) {
                    existeEspecie = true;
                    break;
                }
            }
            if (!existeEspecie) {
                return false;
            }
            jogadores.add(new Jogador(Integer.parseInt(playersInfo[j][0]), playersInfo[j][1],
                    buscarEspecieAtravesDoId(playersInfo[j][2].charAt(0)),initialEnergy));
        }
        mapa = new MapaJogo(jungleSize);
        for (int i = 0; i < jogadores.size(); i++) {
            mapa.adicionaJogadorInicio(jogadores.get(i));
        }
        return true;
    }

    public int[] getPlayerIds(int squareNr) {
        Casa casa;
        if (mapa.verificaCasa(squareNr)) {
            casa = mapa.buscarCasa(squareNr);
            return casa.buscaJogadoresIds();
        } else {
            return new int[]{};
        }
    }

    public String[] getSquareInfo(int squareNr) {
        String[] resultado = new String[3];
        if (!(mapa.verificaCasa(squareNr))) {
            return null;
        } else {
            Casa casa = mapa.buscarCasa(squareNr);
            resultado[0] = casa.buscarImagemCasa();
            resultado[1] = casa.buscarTipoCasa();
            resultado[2] = casa.buscaIds().toString();
        }
        return resultado;
    }

    public String[] getPlayerInfo(int playerId) {
        String[] resultado = new String[4];

        if (jogadores.size() <= 0){
            return null;
        } else {
            for (Jogador j: jogadores){
                j.buscarId();
                if (j.id == playerId){
                    resultado[0] = String.valueOf(j.id);
                    resultado[1] = j.nome;
                    resultado[2] = String.valueOf(j.especie);
                    resultado[3] = String.valueOf(j.energia);
                }
            }
            return resultado;
        }
    }

    public String[] getCurrentPlayerInfo() { //com turnos
        return null;
    }

    public String[][] getPlayersInfo() {
        String [][] resultado = new String[jogadores.size()][4];

        if (jogadores.size() <= 0 ){
            return null;
        } else {
            for (Jogador j: jogadores) {
                for (int i = 0; i <= jogadores.size(); i++){ // ver o pq de null pointer exception
                    resultado[i][0] = String.valueOf(j.id);
                    resultado[i][1] = j.nome;
                    resultado[i][2] = String.valueOf(j.especie);
                    resultado[i][3] = String.valueOf(j.energia);
                }
            }
            return resultado;
        }

    }

    public boolean moveCurrentPlayer(int nrSquares, boolean bypassValidations) {
        return false;
    }

    public String[] getWinnerInfo() {
        return null;
    }

    public ArrayList<String> getGameResults() {
        //#1 <NOME>, <ESPÉCIE>, <posição do primeiro classificado>...u
        return new ArrayList<>();
    }

    public JPanel getAuthorsPanel() {
        return null;
    }

    public String whoIsTaborda() {
        return "Wrestling";
    }

    public void reset() {
        mapa = null;
        jogadores = new ArrayList<>();
    }

    public Especie buscarEspecieAtravesDoId(char id) {
        for (Especie especie : this.especies) {
            if (especie.buscarIdentificador() == id) {
                return especie;
            }
        }
        return null;
    }

}
