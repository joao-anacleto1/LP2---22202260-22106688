package pt.ulusofona.lp2.deisiJungle;

class CachoDeBananas extends Alimento {

    int bananasNoCacho;


    public CachoDeBananas() {
        this.identificadorAlimento = 'b';
        this.nomeAlimento = "Bananas";
        this.imagem = "bananas.png";
        this.bananasNoCacho = 3;
        this.tooltip = "Bananas : " + bananasNoCacho +" : + 40 energia";
    }

    public int buscarBananas(){
        return this.bananasNoCacho;
    }

    public void alteraBananas(int valor){
        this.bananasNoCacho = valor;
    }

    public boolean buscarBananasNoCacho(){

        if(bananasNoCacho == 0){
            return false;
        }
        bananasNoCacho--;
        this.tooltip = "Bananas : " + bananasNoCacho + " : + 40 energia";
        return true;
    }
}
