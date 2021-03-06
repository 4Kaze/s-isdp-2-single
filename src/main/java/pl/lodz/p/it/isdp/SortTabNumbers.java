package pl.lodz.p.it.isdp;

import java.util.Arrays;

/**
 * Uwaga: Klasa zawiera błędy, które trzeba zidentyfikować i usunąć z
 * zastosowaniem trybu debug (nadzorowane wykonanie z wykorzystanie debuggera), tak by 
 * tablica była inicjowana wartościami losowymi a następnie sortowana.
 */
public class SortTabNumbers {

    private final long tab[];

    public SortTabNumbers(final int max) {
        tab = new long[max];
        for (int i = 0; i < tab.length; i++) {
            tab[i] = (long) (Math.random() * Long.MAX_VALUE);
        }
    }

    /*
     * W metodzie należy zminimalizować liczbę wykonywanych porównań
     * opdpowiednio ustalając wartości początkową dla zmienej j.
     */

    /*
    * W obecnej formie skryptu nie można zoptymalizować.
    * Aby stało się to możliwe, należy zamienić sprawdzane indeksy tablicy tak aby wskazywały na elementy,
    * które znajdują się obok siebie.
     */
    public void sort() {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab.length-1-i; j++) {
                if (tab[j] > tab[j+1]) {
                    swap(j, j+1);
                }
            }
        }
    }

    private void swap(final int i, final int j) {
        long temp = tab[i];
        tab[i] = tab[j];
        tab[j] = temp;
    }

    public boolean checkMinOrderSort() {
        for (int k = 0; k < tab.length - 1; k++) {
            if (tab[k] > tab[k + 1]) {
                return false;
            }
        }
        return true;
    }
    
    public long[] getTab() {
        return this.tab;
    }

    @Override
    public String toString() {
        return "tab=" + Arrays.toString(tab);
    }
}
