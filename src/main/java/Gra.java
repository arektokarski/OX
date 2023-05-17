import java.io.Serializable;
import java.util.Arrays;

class Gra implements Serializable {
    private static final int ROZMIAR_PLANSZY = 3;

    private Symbol[][] plansza;
    private Gracz aktualnyGracz;

    public Symbol[][] getPlansza() {
        return plansza;
    }

    public void setPlansza(Symbol[][] plansza) {
        this.plansza = plansza;
    }

    public Gra() {
        plansza = new Symbol[ROZMIAR_PLANSZY][ROZMIAR_PLANSZY];
        aktualnyGracz = Gracz.GRACZ;
        startPlanszy();
    }

    private void startPlanszy() {
        for (Symbol[] row : plansza) {
            Arrays.fill(row, Symbol.PUSTY);
        }
    }

    public boolean koniecGry() {
        return zwycieskiRuch(Symbol.X) || zwycieskiRuch(Symbol.O) || pelnaPlansza();
    }

    boolean zwycieskiRuch(Symbol symbol) {
        for (int i = 0; i < ROZMIAR_PLANSZY; i++) {
            if (plansza[i][0] == symbol && plansza[i][1] == symbol && plansza[i][2] == symbol) {
                return true;
            }
            if (plansza[0][i] == symbol && plansza[1][i] == symbol && plansza[2][i] == symbol) {
                return true;
            }
        }
        if (plansza[0][0] == symbol && plansza[1][1] == symbol && plansza[2][2] == symbol) {
            return true;
        }
        if (plansza[0][2] == symbol && plansza[1][1] == symbol && plansza[2][0] == symbol) {
            return true;
        }
        return false;
    }

    private boolean pelnaPlansza() {
        for (Symbol[] row : plansza) {
            for (Symbol symbol : row) {
                if (symbol == Symbol.PUSTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public void wykonajRuch(int row, int column) {
        if (plansza[row][column] == Symbol.PUSTY) {
            plansza[row][column] = aktualnyGracz.equals(Gracz.GRACZ) ? Symbol.X : Symbol.O;
            aktualnyGracz = aktualnyGracz.equals(Gracz.GRACZ) ? Gracz.KOMPUTER : Gracz.GRACZ;
        }
    }


    public void wypiszPlansze() {
        System.out.println("  A B C");
        for (int i = 0; i < ROZMIAR_PLANSZY; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < ROZMIAR_PLANSZY; j++) {
                switch (plansza[i][j]) {
                    case X:
                        System.out.print("X ");
                        break;
                    case O:
                        System.out.print("O ");
                        break;
                    case PUSTY:
                        System.out.print("- ");
                        break;
                }
            }
            System.out.println();
        }
    }

    public Gracz getaktualnyGracz() {
        return aktualnyGracz;
    }

    public void setaktualnyGracz(Gracz aktualnyGracz) {
        this.aktualnyGracz = aktualnyGracz;
    }
}
