package pt.ulusofona.lp2.deisiJungle;

public class Jogador {

    int id;
    int energia; // indicada na hora do jogo
    String nome;
    Especie especie;


    public Jogador() {

    }

    public Jogador(int identificador, String nome, Especie especie) {
        this.id = identificador;
        this.nome = nome;
        this.especie = especie;
    }

    boolean validarNome(){
        if (this.nome == null){
            return false;
        }
        if (this.nome.length() <= 0){
            return false;
        }
        return true;
    }
}
