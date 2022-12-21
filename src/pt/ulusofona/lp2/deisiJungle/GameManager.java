package pt.ulusofona.lp2.deisiJungle;

import jdk.jshell.spi.ExecutionControl;

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

    ArrayList<Jogador> jogadores = new ArrayList<>(); // jogadores que estao jogoAtual jogar


    MapaJogo mapa;

    int turno;


    public GameManager() {
    }

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
    public String[][] getFoodTypes() {

        String[][] resultado = new String[5][3];

        resultado[0] = alimentos.get(0).buscaInfo();
        resultado[1] = alimentos.get(1).buscaInfo();
        resultado[2] = alimentos.get(2).buscaInfo();
        resultado[3] = alimentos.get(3).buscaInfo();
        resultado[4] = alimentos.get(4).buscaInfo();

        return resultado;
    }

    //DONE
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

        for (String[] dadosJogador : playersInfo) {
            // try catch -> trata exceçoes de forma que o programa não rebente,
            // neste caso caso o conteudo da string não seja um numero o programa não "rebenta"
            try {
                if (!resultado.contains(Integer.parseInt(dadosJogador[0]))) {
                    resultado.add(Integer.parseInt(dadosJogador[0]));
                } else {
                    return new InitializationError("Id de jogador inválido");
                }
            } catch (NumberFormatException e) {
                return new InitializationError("Id de jogador inválido");
            }
            if (dadosJogador[1] == null || dadosJogador[1].equals("")) {
                return new InitializationError("Nome de jogador inválido");
            }

            boolean existeEspecie = false; // verificação final para ver se tem ou não uma especie válida
            for (Especie e : especies) {
                if (dadosJogador[2].charAt(0) == e.buscarIdentificador()) {
                    existeEspecie = true;
                    break;
                }
            }
            if (!existeEspecie) {
                return new InitializationError("Não existe espécie válida");
            }
            Especie especie;

            char idEspecie = dadosJogador[2].charAt(0);

            if (idEspecie == 'E') {
                especie = new Elefante();

            } else if (idEspecie == 'Z') {
                especie = new Tarzan();

            } else if (idEspecie == 'T') {
                especie = new Tartaruga();

            } else if (idEspecie == 'P') {
                especie = new Passaro();

            } else {
                especie = new Leao();
            }

            jogadores.add(new Jogador(Integer.parseInt(dadosJogador[0]), dadosJogador[1],
                    especie, 1));
        }
        jogadores = ordenarJogadoresPorID();

        mapa = new MapaJogo(jungleSize);
        for (int i = 0; i < jogadores.size(); i++) {
            mapa.adicionaJogadorInicio(jogadores.get(i));
        }

        InitializationError resultadoAlimentos = verificacaoAlimentos(jungleSize, mapa, foodsInfo);

        if (resultadoAlimentos != null) {
            return resultadoAlimentos;
        }

        return null;
    }

    //DONE
    InitializationError verificacaoAlimentos(int jungleSize, MapaJogo mapaJogo, String[][] foodsInfo) {

        if (foodsInfo != null) {

           /* int nmr = 0;
            for(String[] test : foodsInfo){
                System.out.println("["+nmr+"][0]: "+test[0]);
                System.out.println("["+nmr+"][1]: "+test[1]);
                nmr++;
            } */
            for (String[] dadosAlimentos : foodsInfo) {


                if (dadosAlimentos[0] == null) {
                    return new InitializationError("O id do tipo de alimento é inválido");
                }

                boolean existeAlimento = false; // verifica se existe comida válida
                for (Alimento a : alimentos) {

                    if (dadosAlimentos[0].charAt(0) == a.buscarIdentificadorAlimento()) {
                        existeAlimento = true;
                        break;
                    }
                }
                if (!existeAlimento) {
                    return new InitializationError("Não existe comida válida");
                }
                try {
                    if (Integer.parseInt(dadosAlimentos[1]) > 1 && Integer.parseInt(dadosAlimentos[1]) < jungleSize) {
                        Alimento alimento;

                        char idAlimento = dadosAlimentos[0].charAt(0);

                        if (idAlimento == 'b') {
                            alimento = new CachoDeBananas();

                        } else if (idAlimento == 'c') {
                            alimento = new Carne();

                        } else if (idAlimento == 'm') {
                            alimento = new CogumelosMagicos();

                        } else if (idAlimento == 'e') {
                            alimento = new Erva();

                        } else {
                            alimento = new Agua();
                        }
                        Casa casa;


                        casa = mapaJogo.buscarCasa(Integer.parseInt(dadosAlimentos[1]));

                        casa.receberAlimento(alimento);

                    } else {
                        return new InitializationError("O alimento não está posicionado dentro " +
                                "dos limites do terreno");
                    }
                } catch (NumberFormatException e) {
                    return new InitializationError("O alimento não está posicionado dentro " +
                            "dos limites do terreno");
                }
            }
        }
        return null;
    }

    //DONE
    public InitializationError createInitialJungle(int jungleSize, String[][] playersInfo) {
        return createInitialJungle(jungleSize, playersInfo, null);
    }

    //DONE
    public int[] getPlayerIds(int squareNr) {
        /* ArrayList<Integer> jogs = new ArrayList<Integer>();
        for(Jogador j : jogadores){
            if(j.posicaoAtual == squareNr)
                jogs.add(j.id);
        }

        if(jogs.isEmpty())
            return new int[] {};

        int[] result = new int[jogs.size()];
        for(int i=0;i<jogs.size();i++)
            result[i] = jogs.get(i);

        return result;*/
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
        resultado[1] = casa.buscarTipoCasa(turno);
        resultado[2] = casa.buscaIdsString();
        return resultado;
    }

    //DONE
    public String[] getPlayerInfo(int playerId) {
        String[] resultado = new String[5];

        if (jogadores.isEmpty()) {
            return null;
        } else {
            for (Jogador j : jogadores) {
                j.buscarId();
                if (j.id == playerId) {
                    resultado[0] = String.valueOf(j.id);
                    resultado[1] = j.nome;
                    resultado[2] = String.valueOf(j.especie.buscarIdentificador());
                    resultado[3] = String.valueOf(j.buscarEnergia());
                    resultado[4] = "" + j.especie.buscarVelocidadeMinima() + ".." + j.especie.buscarVelocidadeMaxima();
                }
            }
            return resultado;
        }
    }

    //DONE
    public String[] getCurrentPlayerInfo() { //com turnos
        Jogador jogadorAtual = jogadores.get(turno % jogadores.size());
        String[] resultado = new String[5];

        resultado[0] = String.valueOf(jogadorAtual.id);
        resultado[1] = jogadorAtual.nome;
        resultado[2] = String.valueOf(jogadorAtual.especie.buscarIdentificador());
        resultado[3] = String.valueOf(jogadorAtual.buscarEnergia());
        resultado[4] = "" + jogadorAtual.especie.buscarVelocidadeMinima() + ".." +
                jogadorAtual.especie.buscarVelocidadeMaxima();

        return resultado;

    }

    int buscarJogadorAtualID(){
        Jogador jogadorAtual = jogadores.get(turno % jogadores.size());
        return jogadorAtual.id;
    }

    //DONE -  AINDA NAO ESTA A PASSAR
    public String[] getCurrentPlayerEnergyInfo(int nrPositions) {
        Jogador jogadorAtual = jogadores.get(turno % jogadores.size());

        if (nrPositions > 0){
            return new String[] {String.valueOf(jogadorAtual.especie.buscarConsumoEnergia() * nrPositions),
                    String.valueOf(jogadorAtual.especie.buscarGanhoEnergiaEmDescanso())};
        }
        return new String[]{};  
    }

    //DONE
    public String[][] getPlayersInfo() {
        String[][] resultado = new String[jogadores.size()][5];

        if (jogadores.isEmpty()) {
            return null;
        } else {
            for (int i = 0; i < jogadores.size(); i++) {
                resultado[i][0] = String.valueOf(jogadores.get(i).id);
                resultado[i][1] = jogadores.get(i).nome;
                resultado[i][2] = String.valueOf(jogadores.get(i).especie.buscarIdentificador());
                resultado[i][3] = String.valueOf(jogadores.get(i).buscarEnergia());
                resultado[i][4] = "" + jogadores.get(i).especie.buscarVelocidadeMinima() + ".." +
                        jogadores.get(i).especie.buscarVelocidadeMaxima();
            }
        }

        return resultado;
    }

    //NOT DONE
    public MovementResult moveCurrentPlayer(int nrSquares, boolean bypassValidations) {

        Jogador jogadorAtual = jogadores.get((turno++ % jogadores.size()));

        if(!bypassValidations && (nrSquares < -6 || nrSquares > 6)){
            return new MovementResult(MovementResultCode.INVALID_MOVEMENT,null);
        }
        if (jogadorAtual.posicaoAtual + nrSquares < 1){
            return new MovementResult(MovementResultCode.INVALID_MOVEMENT,null);
        }

        Casa casaAntiga = this.mapa.buscarCasa(jogadorAtual.posicaoAtual);

        //ACAO DE MOVIMENTO
        if (nrSquares == 0){
            jogadorAtual.ficar();
        } else if (!jogadorAtual.mover(nrSquares)){
            return new MovementResult(MovementResultCode.NO_ENERGY,null);
        }

        //GARANTIR QUE O JOGADOR NAO VAI SE MOVER PARA FORA DO MAPA
        if(jogadorAtual.posicaoAtual > mapa.tamanhoMapa() ){
            jogadorAtual.posicaoAtual = mapa.tamanhoMapa();
        }

        Casa casaAtualDoJogador = this.mapa.buscarCasa(jogadorAtual.posicaoAtual);
        if(casaAntiga.indexCasa != casaAtualDoJogador.indexCasa){
            casaAntiga.removerJogador(jogadorAtual);
            casaAtualDoJogador.adicionarJogador(jogadorAtual);
        }
        if(casaAtualDoJogador.alimento != null){
                jogadorAtual.consumir(casaAtualDoJogador.alimento,turno);
                return new MovementResult(MovementResultCode.CAUGHT_FOOD,
                        "Apanhou "+ casaAtualDoJogador.alimento.buscarNomeAlimento());
            }
            else {
                return new MovementResult(MovementResultCode.VALID_MOVEMENT,null);
            }

    }

    //DONE
    public String[] getWinnerInfo() {
        String[] resultado = new String[5];
        Jogador jogador = jogadores.get(0);

        if (jogoAcabado()) {
            int maisLonge = jogadores.get(0).buscarPosicaoAtual();

            for (int i = 1; i < jogadores.size(); i++) {

                if (jogadores.get(i).buscarPosicaoAtual() == maisLonge
                        && jogadores.get(i).buscarId() < jogador.buscarId()) {
                    jogador = jogadores.get(i);
                }
                if (jogadores.get(i).buscarPosicaoAtual() > maisLonge) {
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
        resultado[3] = String.valueOf(jogador.buscarEnergia());
        resultado[4] = "" + jogador.especie.buscarVelocidadeMinima() + ".." + jogador.especie.buscarVelocidadeMaxima();
        return resultado;
    }

    //DONE
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
        painel.setBackground(c1); // fundo do painel com jogoAtual cor criada
        painel.add(labelTexto); // adiciona texto no painel

        return painel;
    }

    //DONE
    public String whoIsTaborda() {
        return "Wrestling"; // luta livre :D
    }

    //NOT DONE
    public boolean saveGame(File file) {
        return false;
    }

    //NOT DONE
    public boolean loadGame(File file) {
        return false;
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
                    resultado.set(i, resultado.get(j));
                    resultado.set(j, temp);
                }
            }
        }
        return resultado;
    }


    boolean jogoAcabado() {

        int primeiraMaiorPosicao = -1;
        int segundaMaiorPosicao = -1;

        if (!mapa.buscarCasa(mapa.casas.size()).casaVazia()) {
            return true;
        }

        for(int j = mapa.tamanhoMapa() - 1 ; j > 0 ; j -- ){

             Casa casa = mapa.buscarCasa(j);

             if(primeiraMaiorPosicao < 0 && casa.buscaNrJogadoresNaCasa() >= 2){
                 return false;
             }

             if(!casa.casaVazia()){
                 if(primeiraMaiorPosicao > 0){

                     if(segundaMaiorPosicao < 0){
                         segundaMaiorPosicao = j;
                     }

                 } else {
                     primeiraMaiorPosicao = j;
                 }
             }
        }

        if((primeiraMaiorPosicao - segundaMaiorPosicao) > mapa.tamanhoMapa()/2){
            return true;
        }

        return false;
    }

}
