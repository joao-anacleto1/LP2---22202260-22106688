package pt.ulusofona.lp2.deisiJungle;

import java.util.ArrayList;

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

    boolean validarNome(){
        if (nome == null){
            return false;
        }
        return nome.length() > 0;
    }

    int buscarId(){
        return id;
    }

    int buscarPosicaoAtual(){
        return posicaoAtual;
    }

}
