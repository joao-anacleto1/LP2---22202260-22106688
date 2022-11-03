package pt.ulusofona.lp2.deisiJungle;

public class Jogador {

    int id;
    int energia;
    String nome;
    Especie especie;


    public Jogador() {

    }

    public Jogador(int identificador, String nome, Especie especie) {
        this.id = identificador;
        this.nome = nome;
        this.especie = especie;
    }
}
