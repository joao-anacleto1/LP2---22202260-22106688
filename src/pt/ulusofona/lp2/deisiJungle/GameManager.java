package pt.ulusofona.lp2.deisiJungle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;


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


        jogadores = ordenarJogadoresPorID();

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

                if (turno == jogadores.size() - 1) {
                    turno = 0;
                } else {
                    turno += 1;
                }

                return false;
            }
        }
        if (jogadores.get(turno).buscarEnergia() >= 2) {
            int proximaPosicao = jogadores.get(turno).buscarPosicaoAtual() + nrSquares;
            mapa.moverJogadores(jogadores.get(turno), proximaPosicao, 2);

            if (turno == jogadores.size() - 1) {
                turno = 0;
            } else {
                turno += 1;
            }
            return true;

        } else if (jogadores.get(turno).buscarPosicaoAtual() + nrSquares >= mapa.tamanhoMapa()) {
            int proximaPosicao = mapa.tamanhoMapa();

            mapa.moverJogadores(jogadores.get(turno), proximaPosicao, 2);

            if (turno == jogadores.size() - 1) {
                turno = 0;
            } else {
                turno += 1;
            }
            return true;

        } else {

            if (turno == jogadores.size() - 1) {
                turno = 0;
            } else {
                turno += 1;
            }
            return false;
        }

    }


    public String[] getWinnerInfo() {
        String[] resultado = new String[4];
        Jogador jogador = jogadores.get(0);

        if (jogoAcabado()) {
            int maisLonge = jogadores.get(0).buscarPosicaoAtual();

            for (int i = 1; i < jogadores.size(); i++) {

                    if (jogadores.get(i).buscarPosicaoAtual() == maisLonge
                            && jogadores.get(i).buscarId() < jogador.buscarId()) {
                        jogador = jogadores.get(i);
                    }
                    if(jogadores.get(i).buscarPosicaoAtual() > maisLonge) {
                        maisLonge = jogadores.get(i).buscarPosicaoAtual();
                        jogador = jogadores.get(i);
                    }
                }

        } else {
            return null;
        }
        resultado[0] = String.valueOf(jogador.id);
        resultado[1] = jogador.nome;
        resultado[2] = String.valueOf(jogador.especie.buscarIdentificador());
        resultado[3] = String.valueOf(jogador.energia);
        return resultado;
    }

    public ArrayList<String> getGameResults() {
        ArrayList<String> resultado = new ArrayList<>();
        int posicaoChegada = 1;

        for (int i = mapa.tamanhoMapa(); i > 0; i--) {
            ArrayList<Jogador> jogadores = mapa.buscarCasa(i).ordernarIds();

            for (int j = 0; j < jogadores.size(); j++) {
                String nomeJogador = jogadores.get(j).buscarNomeJogador();
                String nomeEspecie = jogadores.get(j).buscarNomeEspecie();
                int posicaoNoMapa = jogadores.get(j).buscarPosicaoAtual();
                String res = "#" + posicaoChegada + " " + nomeJogador + ", " + nomeEspecie + ", " + posicaoNoMapa;
                resultado.add(res);
                posicaoChegada += 1;
            }
        }

        return resultado;
    }

    public JPanel getAuthorsPanel() {


        JPanel painel = new JPanel();
        JFrame frame = new JFrame("Créditos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // fechar a janela ao clicar no X
        frame.add(painel); // adiciona o painel na jframe
        painel.setSize(300, 300); // definir tamanho do painel
        painel.setLayout(null);
        painel.setVisible(true); // fazer o painel visivel

        return painel;
    }

    public String whoIsTaborda() {
        return "Wrestling";
    }


    // FUNÇÕES AUXILIARES

    public void reset() {
        mapa = null;
        jogadores = new ArrayList<>();
    }

    public ArrayList<Jogador> ordenarJogadoresPorID() {

        ArrayList<Jogador> resultado = jogadores;

        for (int i = 0; i < resultado.size(); i++) {

            // Inner nested loop pointing 1 index ahead
            for (int j = i + 1; j < resultado.size(); j++) {

                // Checking elements
                Jogador temp;
                if (resultado.get(j).buscarId() < resultado.get(i).buscarId()) {

                    // Swapping
                    temp = resultado.get(i);
                    resultado.set(i,resultado.get(j));
                    resultado.set(j,temp);
                }
            }
        }
        return resultado;
    }

        public Especie buscarEspecieAtravesDoId ( char id){
            for (Especie especie : this.especies) {
                if (especie.buscarIdentificador() == id) {
                    return especie;
                }
            }
            return null;
        }

        boolean jogoAcabado () {
            if (!mapa.buscarCasa(mapa.casas.size()).casaVazia()) {
                return true;
            }
            boolean temEnergia = false;

            for (int j = 0; j < jogadores.size(); j++) {
                if (jogadores.get(j).buscarEnergia() >= 2) {
                    temEnergia = true;
                }
            }
            return !temEnergia;
        }
    }



