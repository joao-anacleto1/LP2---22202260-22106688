package pt.ulusofona.lp2.deisiJungle;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


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
    Alimento cogumelosMagicos = new CogumeloMagico();

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

    ArrayList<Alimento> alimentosConsumidos = new ArrayList<>();

    Jogador jogadorInfo;

    MapaJogo mapa;

    int nrCasasMapa;

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
    public void createInitialJungle(int jungleSize, String[][] playersInfo, String[][] foodsInfo)
            throws InvalidInitialJungleException {
        reset();
        ArrayList<Integer> resultado = new ArrayList<>();
        turno = 0;
        nrCasasMapa = jungleSize;

        if (!(playersInfo.length >= 2 && playersInfo.length <= 4)) {
            throw new InvalidInitialJungleException("O número de jogadores não é válido",true,false);
        }

        if (!(jungleSize >= 2 * playersInfo.length)) {
            throw new InvalidInitialJungleException("Não existem pelo menos duas posições para se jogar no mapa",true,
                    false);
        }

        for (String[] dadosJogador : playersInfo) {
            // try catch -> trata exceçoes de forma que o programa não rebente,
            // neste caso caso o conteudo da string não seja um numero o programa não "rebenta"
            try {
                if (!resultado.contains(Integer.parseInt(dadosJogador[0]))) {
                    resultado.add(Integer.parseInt(dadosJogador[0]));
                } else {
                    throw new InvalidInitialJungleException("Id de jogador inválido",true,false);
                }
            } catch (NumberFormatException e) {
                throw new InvalidInitialJungleException("Id de jogador inválido",true,false);
            }
            if (dadosJogador[1] == null || dadosJogador[1].equals("")) {
                throw new InvalidInitialJungleException("Nome de jogador inválido",true,false);
            }

            boolean existeEspecie = false; // verificação final para ver se tem ou não uma especie válida
            for (Especie e : especies) {
                if (dadosJogador[2].charAt(0) == e.buscarIdentificador()) {
                    existeEspecie = true;
                    break;
                }
            }
            if (!existeEspecie) {
                throw new InvalidInitialJungleException("Não existe espécie válida",true,false);
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
        for (Jogador jogador : jogadores) {
            mapa.adicionaJogadorInicio(jogador);
        }

        verificacaoAlimentos(jungleSize, mapa, foodsInfo);
    }

    //DONE
    void verificacaoAlimentos(int jungleSize, MapaJogo mapaJogo, String[][] foodsInfo) throws
            InvalidInitialJungleException {

        nrCasasMapa = jungleSize;

        if (foodsInfo != null) {

           /* int nmr = 0;
            for(String[] test : foodsInfo){
                System.out.println("["+nmr+"][0]: "+test[0]);
                System.out.println("["+nmr+"][1]: "+test[1]);
                nmr++;
            } */
            for (String[] dadosAlimentos : foodsInfo) {


                if (dadosAlimentos[0] == null) {
                    throw new InvalidInitialJungleException("O id do tipo de alimento é inválido",false,true);
                }

                boolean existeAlimento = false; // verifica se existe comida válida
                for (Alimento a : alimentos) {

                    if (dadosAlimentos[0].charAt(0) == a.buscarIdentificadorAlimento()) {
                        existeAlimento = true;
                        break;
                    }
                }
                if (!existeAlimento) {
                    throw new InvalidInitialJungleException("Não existe comida válida",false,true);
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
                            alimento = new CogumeloMagico();

                        } else if (idAlimento == 'e') {
                            alimento = new Erva();

                        } else {
                            alimento = new Agua();
                        }
                        Casa casa;

                        casa = mapaJogo.buscarCasa(Integer.parseInt(dadosAlimentos[1]));

                        casa.receberAlimento(alimento);

                    } else {
                        throw new InvalidInitialJungleException("O alimento não está posicionado dentro " +
                                "dos limites do terreno",false,true);
                    }
                } catch (NumberFormatException e) {
                    throw new InvalidInitialJungleException("O alimento não está posicionado dentro " +
                            "dos limites do terreno",false,true);
                }
            }
        }
    }

    //DONE
    public void createInitialJungle(int jungleSize, String[][] playersInfo) throws InvalidInitialJungleException {
        createInitialJungle(jungleSize, playersInfo, null);
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
                if (j.buscarId() == playerId) {
                    resultado[0] = String.valueOf(j.buscarId());
                    resultado[1] = j.buscarNomeJogador();
                    resultado[2] = String.valueOf(j.buscarEspecie().buscarIdentificador());
                    resultado[3] = String.valueOf(j.buscarEnergia());
                    resultado[4] = "" + j.buscarEspecie().buscarVelocidadeMinima() + ".." +
                            j.buscarEspecie().buscarVelocidadeMaxima();
                }
            }
            return resultado;
        }
    }

    //DONE
    public String[] getCurrentPlayerInfo() { //com turnos
        Jogador jogadorAtual = jogadores.get(turno % jogadores.size());
        String[] resultado = new String[5];

        resultado[0] = String.valueOf(jogadorAtual.buscarId());
        resultado[1] = jogadorAtual.buscarNomeJogador();
        resultado[2] = String.valueOf(jogadorAtual.buscarEspecie().buscarIdentificador());
        resultado[3] = String.valueOf(jogadorAtual.buscarEnergia());
        resultado[4] = "" + jogadorAtual.buscarEspecie().buscarVelocidadeMinima() + ".." +
                jogadorAtual.buscarEspecie().buscarVelocidadeMaxima();

        return resultado;

    }

    //DONE
    public String[] getCurrentPlayerEnergyInfo(int nrPositions) {
        Jogador jogadorAtual = jogadores.get(turno % jogadores.size());

        return new String[]{String.valueOf(jogadorAtual.buscarEspecie().buscarConsumoEnergia() * Math.abs(nrPositions)),
                String.valueOf(jogadorAtual.buscarEspecie().buscarGanhoEnergiaEmDescanso())};
    }

    //DONE
    public String[][] getPlayersInfo() {
        String[][] resultado = new String[jogadores.size()][5];

        if (jogadores.isEmpty()) {
            return null;
        } else {
            for (int i = 0; i < jogadores.size(); i++) {
                resultado[i][0] = String.valueOf(jogadores.get(i).buscarId());
                resultado[i][1] = jogadores.get(i).buscarNome();
                resultado[i][2] = String.valueOf(jogadores.get(i).buscarEspecie().buscarIdentificador());
                resultado[i][3] = String.valueOf(jogadores.get(i).buscarEnergia());
                resultado[i][4] = "" + jogadores.get(i).buscarEspecie().buscarVelocidadeMinima() + ".." +
                        jogadores.get(i).buscarEspecie().buscarVelocidadeMaxima();
            }
        }

        return resultado;
    }

    //DONE
    public MovementResult moveCurrentPlayer(int nrSquares, boolean bypassValidations) {

        Jogador jogadorAtual = jogadores.get((turno++ % jogadores.size()));

        jogadorInfo = jogadorAtual;

        if (!bypassValidations && (nrSquares < -6 || nrSquares > 6)) {
            return new MovementResult(MovementResultCode.INVALID_MOVEMENT, null);
        }
        if (jogadorAtual.buscarPosicaoAtual() + nrSquares < 1) {
            return new MovementResult(MovementResultCode.INVALID_MOVEMENT, null);
        }
        if (jogadorAtual.buscarPosicaoAtual() + nrSquares > mapa.buscarTamanhoMapa()) {
            return new MovementResult(MovementResultCode.INVALID_MOVEMENT, null);
        }

        Casa casaAntiga = this.mapa.buscarCasa(jogadorAtual.buscarPosicaoAtual());

        //ACAO DE MOVIMENTO
        if (nrSquares == 0) {
            jogadorAtual.ficar();
        } else {
            MovementResultCode movementResultCode = jogadorAtual.mover(nrSquares, !bypassValidations);

            if (movementResultCode == MovementResultCode.NO_ENERGY) {
                return new MovementResult(MovementResultCode.NO_ENERGY, null);

            } else if (movementResultCode == MovementResultCode.INVALID_MOVEMENT && !bypassValidations) {
                return new MovementResult(MovementResultCode.INVALID_MOVEMENT, null);
            }

        }


        Casa casaAtualDoJogador = this.mapa.buscarCasa(jogadorAtual.buscarPosicaoAtual());
        if (casaAntiga.buscarIndexCasa() != casaAtualDoJogador.buscarIndexCasa()) {
            casaAntiga.removerJogador(jogadorAtual);
            casaAtualDoJogador.adicionarJogador(jogadorAtual);
        }

        Alimento alimentoCasaAtual = casaAtualDoJogador.buscarAlimento();

        if (alimentoCasaAtual != null &&
                jogadorAtual.consumirAlimento(alimentoCasaAtual, turno)) {
            alimentosConsumidos.add(alimentoCasaAtual);
            return new MovementResult(MovementResultCode.CAUGHT_FOOD,
                    "Apanhou " + alimentoCasaAtual.buscarNomeAlimento());
        } else {
            return new MovementResult(MovementResultCode.VALID_MOVEMENT, null);
        }
    }

    //DONE
    public String[] getWinnerInfo() {
        String[] resultado = new String[5];
        Jogador jogador = jogadores.get(0);

        if (jogoAcabado() == 1) {
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
        }

        if (jogoAcabado() == 2) {
            //ordenar por posicao
            for (int i = 0; i < jogadores.size(); i++) {

                // Inner nested loop pointing 1 index ahead
                for (int j = i + 1; j < jogadores.size(); j++) {

                    Jogador t;

                    if (jogadores.get(j).buscarPosicaoAtual() > jogadores.get(i).buscarPosicaoAtual()) {
                        t = jogadores.get(i);
                        jogadores.set(i, jogadores.get(j));
                        jogadores.set(j, t);
                    }
                }
            }
            jogador = jogadores.get(1);
            ordenarJogadoresPorID();
        }

        if (jogoAcabado() == 0) {
            return null;
        }

        resultado[0] = String.valueOf(jogador.buscarId());
        resultado[1] = jogador.buscarNome();
        resultado[2] = String.valueOf(jogador.buscarEspecie().buscarIdentificador());
        resultado[3] = String.valueOf(jogador.buscarEnergia());
        resultado[4] = "" + jogador.buscarEspecie().buscarVelocidadeMinima() + ".." +
                jogador.buscarEspecie().buscarVelocidadeMaxima();
        return resultado;
    }

    //DONE
    public ArrayList<String> getGameResults() {
        ArrayList<String> resultado = new ArrayList<>();
        int posicaoChegada = 1;

        if (jogadores.get(0).buscarPosicaoAtual() - jogadores.get(1).buscarPosicaoAtual() > mapa.buscarTamanhoMapa()/2) {


            String res = "#" + posicaoChegada + " " + jogadores.get(1).buscarNome() + ", " +
                    jogadores.get(1).buscarNomeEspecie() + ", " + jogadores.get(1).buscarPosicaoAtual() +
                    ", " + jogadores.get(1).buscarPosicaoPercorrida() + ", " + jogadores.get(1).buscarSomarComida();
            resultado.add(res);
            posicaoChegada += 1;

            res = "#" + posicaoChegada + " " + jogadores.get(0).buscarNome() + ", " +
                    jogadores.get(0).buscarNomeEspecie()+ ", " + jogadores.get(0).buscarPosicaoAtual() +
                    ", " + jogadores.get(0).buscarPosicaoPercorrida() + ", " + jogadores.get(0).buscarSomarComida();
            resultado.add(res);
            posicaoChegada += 1;

            for (int i = jogadores.get(1).buscarPosicaoAtual() - 1; i > 0; i--) {
                ArrayList<Jogador> jogadores = mapa.buscarCasa(i).ordernarIds();

                for (Jogador jogador : jogadores) {
                    String nomeJogador = jogador.buscarNomeJogador();
                    String nomeEspecie = jogador.buscarNomeEspecie();
                    int posicaoNoMapa = jogador.buscarPosicaoAtual();

                    res = "#" + posicaoChegada + " " + nomeJogador + ", " + nomeEspecie + ", " + posicaoNoMapa +
                            ", " + jogador.buscarPosicaoPercorrida() + ", " + jogador.buscarSomarComida();
                    resultado.add(res);
                    posicaoChegada += 1;
                }
            }
        } else {

            for (int i = mapa.buscarTamanhoMapa(); i > 0; i--) {
                ArrayList<Jogador> jogadores = mapa.buscarCasa(i).ordernarIds();

                for (Jogador jogador : jogadores) {
                    String nomeJogador = jogador.buscarNomeJogador();
                    String nomeEspecie = jogador.buscarNomeEspecie();
                    int posicaoNoMapa = jogador.buscarPosicaoAtual();
                    String res = "#" + posicaoChegada + " " + nomeJogador + ", " + nomeEspecie + ", " + posicaoNoMapa +
                            ", " + jogador.buscarPosicaoPercorrida() + ", " + jogador.buscarSomarComida();
                    resultado.add(res);
                    posicaoChegada += 1;
                }
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

    //DONE
    public boolean saveGame(File file) {

        try (BufferedWriter buff = new BufferedWriter(new FileWriter(file));) {

            // generic parameters

            buff.write(nrCasasMapa + "\n");
            buff.write(turno + "\n");


            // buff.write("JOGADORES" + "\n");


            // jogadores

            for (int i = 0; i < 4; i++) {

                if (!(i < jogadores.size())) {
                    buff.write("NONE\n");

                } else {
                    Jogador jogadorAtual = jogadores.get(i);

                    buff.write(jogadorAtual.buscarEspecie().buscarIdentificador() + ":" + jogadorAtual.buscarId() +
                            ":" + jogadorAtual.buscarNomeJogador() + ":" + jogadorAtual.buscarPosicaoAtual() + ":" +
                            jogadorAtual.buscarEnergia() + ":" + jogadorAtual.buscarQtdDeBananasIngeridas() + "\n");
                }

            }

            //buff.write("ALIMENTOS" + "\n");

            // alimentos

            for (Casa casa : mapa.casas) {

                if (casa.buscarAlimento() == null) {

                    buff.write(casa.buscarIndexCasa() + ":" + "NONE" + ":" + "NONE" + ":" + "NONE" + "\n");

                } else if (casa.buscarAlimento().buscarIdentificadorAlimento() == 'm') {

                    buff.write(casa.buscarIndexCasa() + ":" + casa.buscarAlimento().buscarIdentificadorAlimento() +
                            ":" + casa.buscarAlimento().buscarEnergiaCogumelo((CogumeloMagico) casa.buscarAlimento()) +
                            ":" + "NONE" + "\n");

                } else if (casa.buscarAlimento().buscarIdentificadorAlimento() == 'b') {

                    buff.write(casa.buscarIndexCasa() + ":" + casa.buscarAlimento().buscarIdentificadorAlimento() +
                            ":" + "NONE" + ":" +
                            casa.buscarAlimento().buscarBananasNoCacho((CachoDeBananas) casa.buscarAlimento())
                            + "\n");

                } else {
                    buff.write(casa.buscarIndexCasa() + ":" + casa.buscarAlimento().buscarIdentificadorAlimento() +
                            ":" + "NONE" + ":" + "NONE" + "\n");
                }

            }

        } catch (IOException io) {
            return false;
        }


        return true;

    }

    //DONE
    public boolean loadGame(File file) {

        try {

            reset();
            Scanner scanner = new Scanner(file);

            nrCasasMapa = Integer.parseInt(scanner.nextLine());
            turno = Integer.parseInt(scanner.nextLine());

            mapa = new MapaJogo(nrCasasMapa);

            // TODO

            int countLine = 3;
            String line;
            String[] splited;

            // Jogadores

            while (scanner.hasNextLine()) {

                line = scanner.nextLine();


                if (countLine >= 3 && countLine <= 6) {

                    if (line.equals("NONE")) {
                        countLine++;
                        continue;
                    }

                    splited = line.split(":");

                    char idEspecie = splited[0].charAt(0);

                    Especie especie = criaEspecie(idEspecie);


                    Jogador jogador = new Jogador(Integer.parseInt(splited[1]), splited[2], especie,
                            Integer.parseInt(splited[3]));

                    jogador.setEnergia(Integer.parseInt(splited[4]));
                    jogador.setQtdDeBananasIngeridas(Integer.parseInt(splited[5]));


                    jogadores.add(jogador);

                    for (Casa casa : mapa.casas) {
                        for (Jogador jogadorInicial : jogadores) {
                            if (jogadorInicial.buscarPosicaoAtual() == casa.buscarIndexCasa()) {
                                casa.adicionarJogador(jogadorInicial);
                            }
                        }
                    }

                } else {

                    // TODO alimentos

                    splited = line.split(":");

                    char idAlimento = splited[1].charAt(0);

                    if (idAlimento != 'N') {

                        Alimento alimento = criaAlimento(splited, idAlimento);

                        Casa casa = mapa.buscarCasa(Integer.parseInt(splited[0]));

                        casa.receberAlimento(alimento);
                    }
                }

                countLine++;

            }


        } catch (IOException io) {
            return false;
        }

        return true;
    }

    // FUNÇÕES AUXILIARES

    public void reset() {
        mapa = null;
        jogadores = new ArrayList<>();
    }

    int buscarJogadorAtualID() {
        Jogador jogadorAtual = jogadores.get(turno % jogadores.size());
        return jogadorAtual.buscarId();
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

    void ordenaJogadoresPorPosicao(){
        //https://www.geeksforgeeks.org/sorting-in-java/--
        for (int i = 0; i < jogadores.size(); i++) {

            // Inner nested loop pointing 1 index ahead
            for (int j = i + 1; j < jogadores.size(); j++) {

                // Checking elements
                Jogador temp;
                if (jogadores.get(j).buscarPosicaoAtual() > jogadores.get(i).buscarPosicaoAtual()) {

                    // Swapping
                    temp = jogadores.get(i);
                    jogadores.set(i, jogadores.get(j));
                    jogadores.set(j, temp);
                }
            }
        }
    }

    Especie criaEspecie(char idEspecie) {

        if (idEspecie == 'E') {
            return new Elefante();

        } else if (idEspecie == 'Z') {
            return new Tarzan();

        } else if (idEspecie == 'T') {
            return new Tartaruga();

        } else if (idEspecie == 'P') {
            return new Passaro();

        } else {
            return new Leao();
        }

    }

    public Alimento criaAlimento(String[] splitted, char idAlimento) {
        Alimento alimento = null;
        if (idAlimento == 'b') {
            alimento = new CachoDeBananas();

            int nrBananas = Integer.parseInt(splitted[3]);

            ((CachoDeBananas) alimento).alteraBananas(nrBananas);

        } else if (idAlimento == 'c') {
            alimento = new Carne();

        } else if (idAlimento == 'm') {
            alimento = new CogumeloMagico();

            int nrCogumelos = Integer.parseInt(splitted[2]);

            ((CogumeloMagico) alimento).alteraCogumelos(nrCogumelos);

        } else if (idAlimento == 'e') {
            alimento = new Erva();

        } else if (idAlimento == 'a') {
            alimento = new Agua();
        }
        return alimento;
    }

    int jogoAcabado() {

        int primeiraMaiorPosicao = -1;
        int segundaMaiorPosicao = -1;

        if (!mapa.buscarCasa(mapa.casas.size()).casaVazia()) {
            return 1;
        }

        for (int j = mapa.buscarTamanhoMapa() - 1; j > 0; j--) {

            Casa casa = mapa.buscarCasa(j);

            if (primeiraMaiorPosicao < 0 && casa.buscaNrJogadoresNaCasa() >= 2) {
                return 0;
            }

            if (!casa.casaVazia()) {
                if (primeiraMaiorPosicao > 0) {

                    if (segundaMaiorPosicao < 0) {
                        segundaMaiorPosicao = j;
                    }

                } else {
                    primeiraMaiorPosicao = j;
                }
            }
        }

        if ((primeiraMaiorPosicao - segundaMaiorPosicao) > mapa.buscarTamanhoMapa() / 2) {
            return 2;
        }
        return 0;
    }


}