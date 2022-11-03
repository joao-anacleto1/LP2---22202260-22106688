package pt.ulusofona.lp2.deisiJungle;

public class Especie {

    String nome;
    String identificador;
    String imagem;

    public Especie() {

    }

    public Especie(String nome, String identificador, String imagem) {
        this.nome = nome;
        this.identificador = identificador;
        this.imagem = imagem;
    }

    String buscarNome() {
        return this.nome;
    }

    String buscarIdentificador() {
        return this.identificador;
    }

    String buscarImagem() {
        return this.imagem;
    }


}
