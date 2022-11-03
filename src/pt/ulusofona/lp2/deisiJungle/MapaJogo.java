package pt.ulusofona.lp2.deisiJungle;

import java.util.ArrayList;

public class MapaJogo {

    ArrayList<Casa> casas;

    public MapaJogo(int tamanho) {
        casas = criadorMapa(tamanho);
    }


    ArrayList<Casa> criadorMapa(int tamanho) {

        ArrayList<Casa> mapa = new ArrayList<>();

        for (int j = 0; j < tamanho; j++) {

            Casa novaCasa;
            if(j == tamanho - 1) {
                novaCasa = new Casa(j + 1, "Meta");
            } else {
                novaCasa = new Casa(j + 1, "Vazio");
            }
            mapa.add(novaCasa);
        }
        return mapa;
    }

}