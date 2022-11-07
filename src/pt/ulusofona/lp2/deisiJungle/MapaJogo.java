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
            if (j == tamanho - 1) {
                novaCasa = new Casa(j + 1, "Meta","finish.png");
            } else {
                novaCasa = new Casa(j + 1, "Vazio","blank.png");
            }
            mapa.add(novaCasa);
        }
        return mapa;
    }

    boolean verificaCasa(int l) {
        if (l > casas.size()) {
            return false;
        }
        return true;
    }

    Casa buscarCasa(int indexNrCasa) {
        return casas.get(indexNrCasa - 1);
    }
}