package pt.ulusofona.lp2.deisiJungle;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;


public class GameManager {

    Especie elefante = new Especie("Elefante", 'E', "elephant.png",180,
            4,10,1,6);

    Especie leao = new Especie("Leão", 'L', "lion.png",80,
            2,10,4,6);

    Especie tartaruga = new Especie("Tartaruga", 'T', "turtle.png",150,
            1,5,1,3);

    Especie passaro = new Especie("Pássaro", 'P', "bird.png",70,
            4,50,5,6);

    Especie tarzan = new Especie("Tarzan", 'Z', "tarzan.png",70,
            2,20,1,6);


    ArrayList<Especie> especies = new ArrayList<>() {
        {
            add(elefante);
            add(leao);
            add(tartaruga);
            add(passaro);
            add(tarzan);
        }
    };


    Alimento erva = new Alimento('e',"Erva","grass.png");

    Alimento agua = new Alimento('a',"Agua","water.png");

    Alimento cachoDeBananas = new Alimento('b',"Cacho de bananas",
            "bananas.png");

    Alimento carne = new Alimento('c',"Carne","meat.png");

    Alimento cogumelosMagicos = new Alimento('m',"Cogumelos magicos",
            "mushroom.png");

    ArrayList<Alimento> alimentos = new ArrayList<>(){
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

        String[][] resultado = new String[especies.size()][];

        for (int i = 0; i < especies.size(); i++) {
            resultado[i] = new String[]{
                    String.valueOf(especies.get(i).buscarIdentificador()),
                    especies.get(i).buscarNome(),
                    especies.get(i).buscarImagem(),
                    String.valueOf(especies.get(i).buscarEnergiaInicial()),
                    String.valueOf(especies.get(i).buscarConsumoEnergia()),
                    String.valueOf(especies.get(i).buscarGanhoEnergiaEmDescanso()),
                    "" + especies.get(i).buscarVelocidadeMinima() + ".." + especies.get(i).buscarVelocidadeMaxima()
            };
        }
        return resultado;
    }

    //DONE
    public String[][] getFoodTypes(){

        String[][] resultado = new String[alimentos.size()][];

        for(int i = 0; i < alimentos.size(); i++){
            resultado[i] = new String[]{
                    String.valueOf(alimentos.get(i).buscarIdentificadorElemento()),
                    alimentos.get(i).buscarNomeAlimento(),
                    alimentos.get(i).buscarImagem()
            };
        }
        return resultado;
    }

    //INCOMPLETA - FALTA FAZER AS 2 VERIFICACOES DO ENUNCIADO A AMARELO
    public InitializationError createInitialJungle(int jungleSize, int initialEnergy, String[][] foodsInfo) {
        reset();
        ArrayList<Integer> resultado = new ArrayList<>();
        turno = 0;

        if (!(foodsInfo.length >= 2 && foodsInfo.length <= 4)) {
            return new InitializationError("O número de jogadores não é válido");
        }

        if (!(jungleSize >= 2 * foodsInfo.length)) {
            return new InitializationError("Não existem pelo menos duas posições para se jogar no mapa");
        }

        for (String[] strings : foodsInfo) {
            // try catch -> trata exceçoes de forma que o programa não rebente,
            // neste caso caso o conteudo da string não seja um numero o programa não "rebenta"
            try {
                if (!resultado.contains(Integer.parseInt(strings[0]))) {
                    resultado.add(Integer.parseInt(strings[0]));
                } else {
                    return new InitializationError("Id de jogador inválido");
                }
            } catch (NumberFormatException e) {
                return new InitializationError("Id de jogador inválido");
            }
            if (strings[1] == null || strings[1].equals("")) {
                return new InitializationError("Nome de jogador inválido");
            }
            boolean existeComida = false; // verificação final para ver se tem ou não uma especie válida
            for (int i = 0; i < alimentos.size(); i++) {
                if (strings[2].charAt(0) == alimentos.get(i).buscarIdentificadorElemento()) {
                    existeComida = true;
                    break;
                }
            }
            if (!existeComida) {
                return new InitializationError("Não existe comida válida");
            }
            jogadores.add(new Jogador(Integer.parseInt(strings[0]), strings[1],
                    buscarEspecieAtravesDoId(strings[2].charAt(0)), initialEnergy, 1));
        }


        jogadores = ordenarJogadoresPorID();

        mapa = new MapaJogo(jungleSize);
        for (int i = 0; i < jogadores.size(); i++) {
            mapa.adicionaJogadorInicio(jogadores.get(i));
        }
        return new InitializationError("null");
    }

    //NOT DONE - VER PPT
    public InitializationError createInitialJungle(int jungleSize, String[][] playersInfo){
        return null;
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





