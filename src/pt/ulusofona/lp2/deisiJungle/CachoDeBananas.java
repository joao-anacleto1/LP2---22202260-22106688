package pt.ulusofona.lp2.deisiJungle;

class CachoDeBananas extends Alimento {

    int bananasNoCacho;


    public CachoDeBananas() {
        this.identificadorAlimento = 'b';
        this.nomeAlimento = "Bananas";
        this.imagem = "bananas.png";
        this.bananasNoCacho = 3;
    }

    int buscarBananasNoCacho(){
        return this.bananasNoCacho;
    }

    public boolean getBanana(){

        if(bananasNoCacho == 0){
            return false;
        }
        bananasNoCacho--;
        return true;
    }
}
