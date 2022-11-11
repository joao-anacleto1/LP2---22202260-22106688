package pt.ulusofona.lp2.deisiJungle;

import java.util.ArrayList;
import java.util.Objects;

public class Jogador {

    int id;
    int energia; // indicada na hora do jogo
    String nome;
    Especie especie;
    int posicaoAtual;

    public Jogador() {
    }

    public Jogador(int identificador, String nome, Especie especie, int energia, int posicaoAtual) {
        this.id = identificador;
        this.nome = nome;
        this.especie = especie;
        this.energia = energia;
        this.posicaoAtual = posicaoAtual;
    }

    boolean validarNome() {
        if (nome == null) {
            return false;
        }
        return nome.length() > 0;
    }

    int buscarId() {
        return id;
    }

    int buscarPosicaoAtual() {
        return posicaoAtual;
    }

    int buscarEnergia() {
        return energia;
    }

    void removeEnergia(int valor) {
        this.energia -= valor;
    }

    void atualizarPosicao(int casaPretendida) {
        this.posicaoAtual = casaPretendida;
    }

    String buscarNomeJogador(){
        return nome;
    }

    String buscarNomeEspecie(){
        return especie.buscarNome();
    }

}
