package pt.ulusofona.lp2.deisiJungle;

public abstract class Alimento {

    protected char identificadorAlimento;
    protected String nomeAlimento;
    protected String imagem;
    protected String tooltip;


    public Alimento() {}


    char buscarIdentificadorAlimento(){
        return this.identificadorAlimento;
    }

    String buscarNomeAlimento(){return this.nomeAlimento;}

    String buscarImagem(){return this.imagem;}

    String buscarTooltip(){
        return this.tooltip;
    }

    public int buscarEnergiaCogumelo(CogumelosMagicos cogumelo){
        return cogumelo.buscarNrCogumelo();
    }

    public int buscarBananasNoCacho(CachoDeBananas banana){
        return banana.buscarBananas();
    }



    public String[] buscaInfo(){

        String[] informacoesAlimentos = new String[3];

        informacoesAlimentos[0] = "" + identificadorAlimento;
        informacoesAlimentos[1] = nomeAlimento;
        informacoesAlimentos[2] = imagem;

        return informacoesAlimentos;

    }

}
