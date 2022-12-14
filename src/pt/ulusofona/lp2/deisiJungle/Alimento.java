package pt.ulusofona.lp2.deisiJungle;

public class Alimento {

    protected char identificadorElemento;
    protected String nomeAlimento;
    protected String imagem;


    public Alimento() {}

    char buscarIdentificadorElemento(){
        return this.identificadorElemento;
    }

    String buscarNomeAlimento(){return this.nomeAlimento;}

    String buscarImagem(){return this.imagem;}

    public String[] buscaInfo(){

        String[] informacoesAlimentos = new String[3];

        informacoesAlimentos[0] = "" + this.identificadorElemento;
        informacoesAlimentos[1] = nomeAlimento;
        informacoesAlimentos[2] = imagem;


        return informacoesAlimentos;

    }

}
