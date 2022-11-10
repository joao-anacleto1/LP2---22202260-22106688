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
    int turno;


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
        reset();
        ArrayList<Integer> resultado = new ArrayList<>();
        turno = 0;

        if (!(playersInfo.length >= 2 && playersInfo.length <= 4)) {
            return false;
        }

        if (!(jungleSize >= 2 * playersInfo.length)) {
            return false;
        }

        for (String[] strings : playersInfo) {
            //try catch -> trata exceçoes de forma que o programa não rebente,
            //neste caso caso o conteudo da string não seja um numero o programa não "rebenta"
            try {
                if (!resultado.contains(Integer.parseInt(strings[0]))) {
                    resultado.add(Integer.parseInt(strings[0]));
                } else {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
            if (strings[1] == null || strings[1].equals("")) {
                return false;
            }
            boolean existeEspecie = false; //verificação final para ver se tem ou não uma especie válida
            for (int i = 0; i < especies.size(); i++) {
                if (strings[2].charAt(0) == especies.get(i).buscarIdentificador()) {
                    existeEspecie = true;
                    break;
                }
            }
            if (!existeEspecie) {
                return false;
            }
            jogadores.add(new Jogador(Integer.parseInt(strings[0]), strings[1],
                    buscarEspecieAtravesDoId(strings[2].charAt(0)), initialEnergy, 1));
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
        }
        Casa casa = mapa.buscarCasa(squareNr);
        resultado[0] = casa.buscarImagemCasa();
        resultado[1] = casa.buscarTipoCasa();
        resultado[2] = casa.buscaIdsString();
        return resultado;
    }

    public String[] getPlayerInfo(int playerId) {
        String[] resultado = new String[4];

        if (jogadores.isEmpty()) {
            return null;
        } else {
            for (Jogador j : jogadores) {
                j.buscarId();
                if (j.id == playerId) {
                    resultado[0] = String.valueOf(j.id);
                    resultado[1] = j.nome;
                    resultado[2] = String.valueOf(j.especie.buscarIdentificador());
                    resultado[3] = String.valueOf(j.energia);
                }
            }
            return resultado;
        }
    }

    public String[] getCurrentPlayerInfo() { //com turnos
        Jogador jogadorAtual = jogadores.get(turno);
        String[] resultado = new String[4];

        resultado[0] = String.valueOf(jogadorAtual.id);
        resultado[1] = jogadorAtual.nome;
        resultado[2] = String.valueOf(jogadorAtual.especie.buscarIdentificador());
        resultado[3] = String.valueOf(jogadorAtual.energia);

        return resultado;

    }

    public String[][] getPlayersInfo() {
        String[][] resultado = new String[jogadores.size()][4];

        if (jogadores.isEmpty()) {
            return null;
        } else {
            for (int i = 0; i < jogadores.size(); i++) {
                resultado[i][0] = String.valueOf(jogadores.get(i).id);
                resultado[i][1] = jogadores.get(i).nome;
                resultado[i][2] = String.valueOf(jogadores.get(i).especie.buscarIdentificador());
                resultado[i][3] = String.valueOf(jogadores.get(i).energia);
            }
        }

        return resultado;
    }

    public boolean moveCurrentPlayer(int nrSquares, boolean bypassValidations) {
        if (!bypassValidations) {
            if (nrSquares < 1 || nrSquares > 6) {
                return false;
            }
        }

        if (jogadores.get(turno).buscarEnergia() >= 2) {
            int proximaPosicao = jogadores.get(turno).buscarPosicaoAtual() + nrSquares;
            if (proximaPosicao > mapa.tamanhoMapa()) {
                proximaPosicao = mapa.tamanhoMapa();
            }
            mapa.moverJogadores(jogadores.get(turno), proximaPosicao, 2);
        }

        if (turno == jogadores.size() - 1) {
            turno = 0;
        } else {
            turno += 1;
        }
        return true;
    }


    public String[] getWinnerInfo() {
        String[] resultado = new String[4];
        Jogador jogador = jogadores.get(0);
        if (jogoAcabado()) {
            int maisLonge = jogadores.get(0).buscarPosicaoAtual();
            for (int i = 1; i < jogadores.size(); i++) {
                if (jogadores.get(i).buscarPosicaoAtual() >= maisLonge) {
                    if (jogadores.get(i).buscarPosicaoAtual() == maisLonge
                            && jogadores.get(i).buscarId() < jogador.buscarId()) {
                        maisLonge = jogadores.get(i).buscarPosicaoAtual();
                        jogador = jogadores.get(i);
                    } else {
                        maisLonge = jogadores.get(i).buscarPosicaoAtual();
                        jogador = jogadores.get(i);
                    }
                }
            }
        }else{
            return null;
        }
        resultado[0] = String.valueOf(jogador.id);
        resultado[1] = jogador.nome;
        resultado[2] = String.valueOf(jogador.especie.buscarIdentificador());
        resultado[3] = String.valueOf(jogador.energia);
        return resultado;
    }

    public ArrayList<String> getGameResults() {
        //#1 <NOME>, <ESPÉCIE>, <posição do primeiro classificado>...u
        return new ArrayList<>();
    }

    public JPanel getAuthorsPanel() {
        JFrame janela = new JFrame("Créditos");
        JPanel painelCreditos = new JPanel();
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.add(painelCreditos);
        painelCreditos.setSize(300, 300);
        painelCreditos.setVisible(true);

        return painelCreditos;
    }

    public String whoIsTaborda() {
        return "Wrestling";
    }


    // FUNÇÕES AUXILIARES

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

    boolean jogoAcabado() {
        if (!mapa.buscarCasa(mapa.casas.size()).casaVazia()) {
            return true;
        }
        boolean temEnergia = false;

        for (int j = 0; j < jogadores.size(); j++) {
            if (jogadores.get(j).buscarEnergia() >= 2) {
                temEnergia = true;
            }
        }
        return temEnergia;
    }
}
