package pt.ulusofona.lp2.deisiJungle;

import java.util.ArrayList;

public class Casa {

    int indexCasa;
    String tipoCasa;
    ArrayList<Jogador> jogadores = new ArrayList<>();

    public Casa(int indexCasa, String tipoCasa) {
        this.indexCasa = indexCasa;
        this.tipoCasa = tipoCasa;
    }

}
