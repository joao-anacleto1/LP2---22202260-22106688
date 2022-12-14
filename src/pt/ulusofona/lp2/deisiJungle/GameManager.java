package pt.ulusofona.lp2.deisiJungle;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;


public class GameManager {


    Especie elefante = new Elefante();
    Especie leao = new Leao();
    Especie tartaruga = new Tartaruga();
    Especie passaro = new Passaro();
    Especie tarzan = new Tarzan();

    ArrayList<Especie> especies = new ArrayList<>() {
        {
            add(elefante);
            add(leao);
            add(tartaruga);
            add(passaro);
            add(tarzan);
        }
    };

    Alimento erva = new Erva();
    Alimento agua = new Agua();
    Alimento cachoDeBananas = new CachoDeBananas();
    Alimento carne = new Carne();
    Alimento cogumelosMagicos = new CogumelosMagicos();

    ArrayList<Alimento> alimentos = new ArrayList<>() {
        {
            add(erva);
            add(agua);
            add(cachoDeBananas);
            add(carne);
            add(cogumelosMagicos);
        }
    };

    ArrayList<Jogador> jogadores = new ArrayList<>(); // jogadores que estao a jogar


    MapaJogo mapa;

    int turno;


    public GameManager() {}

    //DONE
    public String[][] getSpecies() {

        String[][] resultado = new String[5][7];

        resultado[0] = especies.get(0).buscaInfo();
        resultado[1] = especies.get(1).buscaInfo();
        resultado[2] = especies.get(2).buscaInfo();
        resultado[3] = especies.get(3).buscaInfo();
        resultado[4] = especies.get(4).buscaInfo();

        return resultado;
    }

    //DONE
    public String[][] getFoodTypes(){

        String[][] resultado = new String[5][3];

        resultado[0] = alimentos.get(0).buscaInfo();
        resultado[1] = alimentos.get(1).buscaInfo();
        resultado[2] = alimentos.get(2).buscaInfo();
        resultado[3] = alimentos.get(3).buscaInfo();
        resultado[4] = alimentos.get(4).buscaInfo();

        return resultado;
    }

    //NOT DONE - FALTA FAZER P FOOD INFO
    public InitializationError createInitialJungle(int jungleSize, String[][] playersInfo, String[][] foodsInfo) {
        reset();
        ArrayList<Integer> resultado = new ArrayList<>();
        turno = 0;

        if (!(playersInfo.length >= 2 && playersInfo.length <= 4)) {
            return new InitializationError("O número de jogadores não é válido");
        }

        if (!(jungleSize >= 2 * playersInfo.length)) {
            return new InitializationError("Não existem pelo menos duas posições para se jogar no mapa");
        }

        for (String[] dadosAlimento: foodsInfo){
            try {
                if (!resultado.contains(Integer.parseInt(dadosAlimento[0]))) {
                    resultado.add(Integer.parseInt(dadosAlimento[0]));
                } else {
                    return new InitializationError("Id do alimento inválido");
                }
            } catch (NumberFormatException e) {
                return new InitializationError("Id do alimento inválido");
            }

            if (Integer.parseInt(dadosAlimento[1]) != 0 && Integer.parseInt(dadosAlimento[1]) < mapa.tamanhoMapa()){
                alimentos.add(new Alimento() {

                });
            } else {
                return new InitializationError("O alimento está fora dos limites do terreno");
            }
            
        }


        for (String[] dadosJogador: playersInfo) {
            // try catch -> trata exceçoes de forma que o programa não rebente,
            // neste caso caso o conteudo da string não seja um numero o programa não "rebenta"
            try {
                if (!resultado.contains(Integer.parseInt(dadosJogador[0]))) {
                    resultado.add(Integer.parseInt(dadosJogador[0]));
                } else {
                    return new InitializationError("Id do jogador inválido");
                }
            } catch (NumberFormatException e) {
                return new InitializationError("Id do jogador inválido");
            }

            if (dadosJogador[1] == null || dadosJogador[1].equals("")) {
                return new InitializationError("Nome do jogador inválido");
            }
            boolean existeEspecie = false; // verificação final para ver se tem ou não uma especie válida
            for (Especie e: especies) {
                if (dadosJogador[2].charAt(0) == e.buscarIdentificador()) {
                    existeEspecie = true;
                    break;
                }
            }
            if (!existeEspecie) {
                return new InitializationError("Não existe espécie válida");
            }
            jogadores.add(new Jogador(Integer.parseInt(dadosJogador[0]), dadosJogador[1],
                    buscarEspecieAtravesDoId(dadosJogador[2].charAt(0)), 1));
        }

        jogadores = ordenarJogadoresPorID();

        mapa = new MapaJogo(jungleSize);
        for (int i = 0; i < jogadores.size(); i++) {
            mapa.adicionaJogadorInicio(jogadores.get(i));
        }
        return null;
    }

    //DONE
    public InitializationError createInitialJungle(int jungleSize, String[][] playersInfo){
        return createInitialJungle(jungleSize,playersInfo,null);
    }

    //DONE
    public int[] getPlayerIds(int squareNr) {
        Casa casa;
        if (mapa.verificaCasa(squareNr)) {
            casa = mapa.buscarCasa(squareNr);
            return casa.buscaJogadoresIds();
        } else {
            return new int[]{};
        }
    }

    //DONE
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

    //DONE
    public String[] getPlayerInfo(int playerId) {
        String[] resultado = new String[4];

        if (jogadores.isEmpty()) {
            return null;
        } else {
            for (Jogador j: jogadores) {
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

    //DONE E ATUALIZADA RELATIVAMENTE Á SEGUNDA PARTE
    public String[] getCurrentPlayerInfo() { //com turnos
        Jogador jogadorAtual = jogadores.get(turno);
        String[] resultado = new String[5];

        resultado[0] = String.valueOf(jogadorAtual.id);
        resultado[1] = jogadorAtual.nome;
        resultado[2] = String.valueOf(jogadorAtual.especie.buscarIdentificador());
        resultado[3] = String.valueOf(jogadorAtual.energia);
        resultado[4] = "" + jogadorAtual.especie.buscarVelocidadeMinima() + ".." +
                jogadorAtual.especie.buscarVelocidadeMaxima();

        return resultado;

    }

    //NOT DONE
    public String[] getCurrentPlayerEnergyInfo(int nrPositions){return null;}

    //DONE E ATUALIZADA RELATIVAMENTE Á SEGUNDA PARTE
    public String[][] getPlayersInfo() {
        String[][] resultado = new String[jogadores.size()][5];

        if (jogadores.isEmpty()) {
            return null;
        } else {
            for (int i = 0; i < jogadores.size(); i++) {
                resultado[i][0] = String.valueOf(jogadores.get(i).id);
                resultado[i][1] = jogadores.get(i).nome;
                resultado[i][2] = String.valueOf(jogadores.get(i).especie.buscarIdentificador());
                resultado[i][3] = String.valueOf(jogadores.get(i).energia);
                resultado[i][4] = "" + jogadores.get(i).especie.buscarVelocidadeMinima() + ".." +
                        jogadores.get(i).especie.buscarVelocidadeMaxima();
            }
        }

        return resultado;
    }

    //NOT DONE
    public MovementResult moveCurrentPlayer(int nrSquares, boolean bypassValidations) {

        if (!bypassValidations) {
            if (nrSquares < 1 || nrSquares > 6) {

                if (turno == jogadores.size() - 1) {
                    turno = 0;
                } else {
                    turno += 1;
                }

                //return false;
                return null;
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
            //return true;
            return null;

        } else if (jogadores.get(turno).buscarPosicaoAtual() + nrSquares >= mapa.tamanhoMapa()) {
            int proximaPosicao = mapa.tamanhoMapa();

            mapa.moverJogadores(jogadores.get(turno), proximaPosicao, 2);

            if (turno == jogadores.size() - 1) {
                turno = 0;
            } else {
                turno += 1;
            }
            //return true;
            return null;

        } else {

            if (turno == jogadores.size() - 1) {
                turno = 0;
            } else {
                turno += 1;
            }
            //return false;
            return null;
        }

    }

    //DONE
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

    //NOT DONE
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

    //DONE
    public JPanel getAuthorsPanel() {

        Color c1 = new Color(0x9fb4c7); // cor de fundo para o painel

        JLabel labelTexto = new JLabel("<html>Projeto realizado por:<br/>- João Anacleto<br/>- " +
                "Luiza Vidal<br/><br/>Universidade Lusófona - Engenharia Informática<br/><br/>Linguagens " +
                "de Programação II</html>");

        JPanel painel = new JPanel();
        painel.setSize(300, 300); // define tamanho do painel
        painel.setVisible(true); // torna o painel visivel
        painel.setBackground(c1); // fundo do painel com a cor criada
        painel.add(labelTexto); // adiciona texto no painel

        return painel;
    }

    //DONE
    public String whoIsTaborda() {
        return "Wrestling"; // luta livre :D
    }

    //NOT DONE
    public boolean saveGame(File file){return false;}

    //NOT DONE
    public boolean loadGame(File file){return false;}

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

