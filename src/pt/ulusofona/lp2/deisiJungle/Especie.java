package pt.ulusofona.lp2.deisiJungle;

public class Especie {

    String nome;
    char identificador;
    String imagem;

    public Especie(String nome, char identificador, String imagem) {
        this.nome = nome;
        this.identificador = identificador;
        this.imagem = imagem;
    }

    String buscarNome() {
        return this.nome;
    }

    char buscarIdentificador() {
        return this.identificador;
    }

    String buscarImagem() {
        return this.imagem;
    }


}
