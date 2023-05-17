import java.io.*;
import java.util.*;

public class OX {
    private static final String PLIK = "ox.ser";
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    public static void main(String[] args) {
        Gra gra;
        System.out.println("Witaj w grze kółko i krzyżyk :)");
        if (zapisanaGra()) {
            gra = zaladujGre();
            gra.wypiszPlansze();
            usunPlik();
            System.out.println("Zapisana gra została wczytana...");
        } else {
            gra = new Gra();
            wybierzGracza(gra);
            ktoZaczyna(gra);
        }

        while (!gra.koniecGry()) {


            if (gra.getaktualnyGracz() == Gracz.GRACZ) {
                String move = wyboryGracza("Twój ruch (np., A1): lub Q (QUIT) żeby zakończyć lub S (SUSPEND) żeby zawiesić i zapisać grę: ");
                if (koniecGry(move)) {
                    System.out.println("Koniec gry...");
                    return;
                } else if (zawieszenieGry(move)) {
                    zapisGry(gra);
                    System.out.println("Gra została zawieszona i zapisana, możesz dokończyć później...");
                    return;
                } else {
                    if (!ruchGracza(gra, move)) {
                        System.out.println("Coś źle wpisałeś, spróbuj jeszcze raz!");
                    }
                }
            } else {
                ruchKomputera(gra);
                gra.wypiszPlansze();
            }
        }
        if (gra.zwycieskiRuch(Symbol.O))
        {
            pokazZwyciesce(gra);
        }
        else {
        gra.wypiszPlansze();
        pokazZwyciesce(gra);}
    }

    private static boolean zapisanaGra() {
        File zapisanyPlik = new File(PLIK);
        return zapisanyPlik.exists();
    }

    private static Gra zaladujGre() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(PLIK))) {
            return (Gra) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Błąd podczas wczytywania gry...");
            return new Gra();
        }
    }

    private static void usunPlik() {
        File zapisanyPlik = new File(PLIK);
        zapisanyPlik.delete();
    }

    private static void wybierzGracza(Gra gra) {
        String symbol;
        do {
            symbol = wyboryGracza("Wynierz symbol (X/O): ");
        } while (!symbol.equalsIgnoreCase("X") && !symbol.equalsIgnoreCase("O"));

        gra.setaktualnyGracz(symbol.equalsIgnoreCase("X") ? Gracz.GRACZ : Gracz.KOMPUTER);
    }

    private static void ktoZaczyna(Gra gra) {
        String zaczynajacy;
        do {
            zaczynajacy = wyboryGracza("Wybierz kto zaczyna Gracz czy Komputer (G/K): ");
        } while (!zaczynajacy.equalsIgnoreCase("G") && !zaczynajacy.equalsIgnoreCase("K"));

        gra.setaktualnyGracz(zaczynajacy.equalsIgnoreCase("G") ? Gracz.GRACZ : Gracz.KOMPUTER);
    }

    private static boolean ruchGracza(Gra gra, String move) {
        if (move.length() != 2) {
            return false;
        }

        char columnChar = Character.toUpperCase(move.charAt(0));
        int wiersz = move.charAt(1) - '1';
        int kolumna = columnChar - 'A';

        if (wiersz < 0 || wiersz >= 3 || kolumna < 0 || kolumna >= 3) {
            return false;
        }

        gra.wykonajRuch(wiersz, kolumna);
        return true;
    }

    private static void ruchKomputera(Gra gra) {
        int wiersz, kolumna;
        do {
            wiersz = random.nextInt(3);
            kolumna = random.nextInt(3);
        } while (gra.getPlansza()[wiersz][kolumna] != Symbol.PUSTY);

        gra.wykonajRuch(wiersz, kolumna);
    }

    private static void zapisGry(Gra gra) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(PLIK))) {
            outputStream.writeObject(gra);
            System.out.println("Gra zapisana.");
        } catch (IOException e) {
            System.out.println("Błąd podczas zapisywania gry.");
        }
    }

    private static void pokazZwyciesce(Gra gra) {
        if (gra.zwycieskiRuch(Symbol.X)) {
            System.out.println("Wygrałeś!!!");
        } else if (gra.zwycieskiRuch(Symbol.O)) {
            System.out.println("Wygrał komputer!");
        } else {
            System.out.println("Remis!");
        }
    }

    private static String wyboryGracza(String wybor) {
        System.out.print(wybor);
        return scanner.nextLine();
    }

    private static boolean koniecGry(String input) {
        return input.equalsIgnoreCase("Q") || input.equalsIgnoreCase("QUIT");
    }

    private static boolean zawieszenieGry(String input) {

        return input.equalsIgnoreCase("S") || input.equalsIgnoreCase("SUSPEND");
    }
}
