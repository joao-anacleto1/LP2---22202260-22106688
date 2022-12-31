package pt.ulusofona.lp2.deisiJungle;

import java.util.ArrayList;

public class MapaJogo {

    ArrayList<Casa> casas;

    public MapaJogo(int tamanho) {
        this.casas = criadorMapa(tamanho);
    }


    ArrayList<Casa> criadorMapa(int tamanho) {

        ArrayList<Casa> mapa = new ArrayList<>();

        for (int j = 0; j < tamanho; j++) {

            Casa novaCasa;
            if (j == tamanho - 1) {
                novaCasa = new Casa(j + 1, "Meta", "finish.png", null);
            } else {
                novaCasa = new Casa(j + 1, "Vazio", "blank.png", null);
            }
            mapa.add(novaCasa);
        }
        return mapa;
    }

    ArrayList<Casa> buscarCasas() {
        return this.casas;
    }

    int buscarTamanhoMapa() {
        return casas.size();
    }

    boolean verificaCasa(int l) {
        if (l > casas.size() || l < 1) {
            return false;
        }
        return true;
    }

    Casa buscarCasa(int indexNrCasa) {
        for (Casa casa : this.casas) {
            if (casa.buscarIndexCasa() == indexNrCasa) {
                return casa;
            }
        }
        return null;
    }

    boolean moverJogadores(Jogador jogador, int posicaoCasaEsperada, int energiaPorJogada) {
        Casa casaAtual = buscarCasa(jogador.buscarPosicaoAtual());
        if (casaAtual == null || !casaAtual.removerJogador(jogador)) {
            return false;
        }

        Casa casaEsperada = buscarCasa(posicaoCasaEsperada);

        if (casaEsperada == null || !casaEsperada.adicionarJogador(jogador)) {
            return false;
        }

        // atualiza posição atual do jogador
        jogador.atualizarPosicao(posicaoCasaEsperada);
        // atualiza energia do jogador
        jogador.removeEnergia(energiaPorJogada);
        return true;
    }


    void adicionaJogadorInicio(Jogador jogador) {
        casas.get(0).adicionarJogador(jogador);
    }

}
