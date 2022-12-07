package pt.ulusofona.lp2.deisiJungle;

public class Alimento {

    char identificadorElemento;
    String nomeAlimento;
    String imagem;


    public Alimento(char identificadorElemento, String nomeAlimento, String imagem) {
        this.identificadorElemento = identificadorElemento;
        this.nomeAlimento = nomeAlimento;
        this.imagem = imagem;
    }

    char buscarIdentificadorElemento(){
        return this.identificadorElemento;
    }

    String buscarNomeAlimento(){return this.nomeAlimento;}

    String buscarImagem(){return this.imagem;}
}
