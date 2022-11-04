package pt.ulusofona.lp2.deisiJungle;

import javax.swing.*;
import java.util.ArrayList;

public class GameManager {

    Especie elefante = new Especie("Elefante", "E", "elephant.png");
    Especie leao = new Especie("Leão", "L", "lion.png");
    Especie tartaruga = new Especie("Tartaruga", "T", "turtle.png");
    Especie passaro = new Especie("Pássaro", "P", "bird.png");
    Especie tarzan = new Especie("Tarzan", "Z", "tarzan.png");

    ArrayList<Especie> especies = new ArrayList<>() {
        {
            add(elefante);
            add(leao);
            add(tartaruga);
            add(passaro);
            add(tarzan);
        }
    };

    ArrayList<Jogador> jogadores = new ArrayList<>();


    public GameManager() {

    }

    public String[][] getSpecies() {

        String[][] result = new String[especies.size()][];

        for (int i = 0; i < especies.size(); i++) {
            result[i] = new String[]{
                    especies.get(i).buscarIdentificador(),
                    especies.get(i).buscarNome(),
                    especies.get(i).buscarImagem()};
        }
        return result;
    }

    public boolean createInitialJungle(int jungleSize, int initialEnergy, String[][] playersInfo){
        return false;
    }

    public int[] getPlayerIds(int squareNr){
        return null;
    }

    public String[] getSquareInfo(int squareNr){
        return null;
    }

    public String[] getPlayerInfo(int playerId){
        return null;
    }

    public String[] getCurrentPlayerInfo(){
        return null;
    }

    public String[][] getPlayersInfo(){
        return null;
    }

    public boolean moveCurrentPlayer(int nrSquares, boolean bypassValidations){
        return false;
    }

    public String[] getWinnerInfo(){
        return null;
    }

    public ArrayList<String> getGameResults(){
        //#1 <NOME>, <ESPÉCIE>, <posição do primeiro classificado>
        return new ArrayList<>();
    }

    public JPanel getAuthorsPanel(){
        return null;
    }

    public String whoIsTaborda(){
        return "Athletics";
    }

}
