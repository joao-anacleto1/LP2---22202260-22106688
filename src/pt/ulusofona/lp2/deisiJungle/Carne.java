package pt.ulusofona.lp2.deisiJungle;

class Carne extends Alimento{

    public Carne() {
        this.identificadorAlimento = 'c';
        this.nomeAlimento = "Carne";
        this.imagem = "meat.png";
        this.tooltip = "";
    }

    boolean eToxica(int turno){
        return turno > 12;
    }

    void insereTooltipCarne(int turno){
        if(!eToxica(turno)){
            this.tooltip = "Carne : + 50 energia : " + turno + " jogadas";
        }else{
            this.tooltip = "Carne toxica";
        }
    }
}
