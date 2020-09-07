package be.intecbrussel;

/**
 * Write a description of class be.intecbrussel.Bord here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Bord {
    public boolean moetNeem;
    public boolean turnEnded;
    public boolean geactiveerd;
    private boolean witAanZet;
    private Stuk[][] dambord;
    private int hor, ver;
    private int selectedPawnRow, selectedPawnColumn;

    public Bord() {
        hor = ver = 10;
        dambord = new Stuk[20][20];
        dambord[1][0] = new Wit(1,0);
        dambord[3][0] = new Wit(3, 0);
        dambord[3][0] = new Wit(3, 0);
        dambord[5][0] = new Wit(5, 0);
        dambord[7][0] = new Wit(7, 0);
        dambord[9][0] = new Wit(9, 0);
        dambord[0][1] = new Wit(0, 1);
        dambord[2][1] = new Wit(2, 1);
        dambord[4][1] = new Wit(4, 1);
        dambord[6][1] = new Wit(6, 1);
        dambord[8][1] = new Wit(8, 1);
        dambord[0][3] = new Wit(0, 3);
        dambord[2][3] = new Wit(2, 3);
        dambord[4][3] = new Wit(4, 3);
        dambord[6][3] = new Wit(6, 3);
        dambord[8][3] = new Wit(8, 3);
        dambord[1][2] = new Wit(1, 2);
        dambord[3][2] = new Wit(3, 2);
        dambord[3][2] = new Wit(3, 2);
        dambord[5][2] = new Wit(5, 2);
        dambord[7][2] = new Wit(7, 2);
        dambord[9][2] = new Wit(9, 2);
        dambord[0][9] = new Paars();
        dambord[2][9] = new Paars();
        dambord[4][9] = new Paars();
        dambord[6][9] = new Paars();
        dambord[8][9] = new Paars();
        dambord[1][8] = new Paars();
        dambord[3][8] = new Paars();
        dambord[5][8] = new Paars();
        dambord[7][8] = new Paars();
        dambord[9][8] = new Paars();
        dambord[1][6] = new Paars();
        dambord[3][6] = new Paars();
        dambord[5][6] = new Paars();
        dambord[7][6] = new Paars();
        dambord[9][6] = new Paars();
        dambord[0][7] = new Paars();
        dambord[2][7] = new Paars();
        dambord[4][7] = new Paars();
        dambord[6][7] = new Paars();
        dambord[8][7] = new Paars();

    }

    public int getHor() {
        return hor;
    }

    public int getVer() {
        return ver;
    }

    public Stuk getInhoud(int x, int y) {
        return dambord[x][y];
    }

    /**
     * Methode voor het starten van een activerings-
     * proces. De methode kijkt of de betsemming leeg is, en als dit niet zo is,
     * of het geaactiveerd kan worden.
     * een actief blokje is een blokje waar je een handeling mee kan uitvoeren.
     */
    public void activeren(int x, int y) {
        turnEnded = false;


        Stuk st = getInhoud(x, y);
        if (st == null) {
        } else if (st.activeerbaar()) {
            st.ActiveerHetNu();
            geactiveerd = true;

        }

    }

    /**
     * deze methode leert ons dat we op een bepaald moment moeten pakken.
     * <p>
     * Het zorgt ervoor dat enkel de objecten die een ander stuk kunnen slaan;
     * geselecteerd kunnen worden. po.stro() verwijst naar een methode die
     * het vakje dat moet pakken beschermd van de onbruikbaarheid.
     */

    public void moetPakken() {
        //habba habba
        for (int m = 0; m < 10; m++) {
            for (int n = 0; n < 10; n++) {
                for (int j = 0; j < 10; j++) {
                    for (int i = 0; i < 10; i++) {
                        Stuk st = getInhoud(m, n);
                        Stuk po = getInhoud(i, j);


                        if ((!witAanZet && n == j - 1 && po instanceof Wit) || (!witAanZet && n == j + 1 && po instanceof Wit)) {

                            if ((m == i + 1 && st instanceof Paars) || (m == i - 1 && st instanceof Paars)) {

                                if ((i + (m - i) * 2) < 10 && (i + (m - i) * 2) > -1 && (j + (n - j) * 2) < 10 && (j + (n - j) * 2) > -1) {
                                    Stuk vrij = getInhoud(i + (m - i) * 2, j + (n - j) * 2);
                                    if (vrij == null) {
                                        if (po != null) {
                                            po.stro();

                                            moetNeem = true;
                                        }

                                    }
                                }
                            }
                        } else if ((witAanZet && n == j - 1 && po instanceof Paars) || (witAanZet && n == j + 1 && po instanceof Paars)) {

                            if ((m == i + 1 && st instanceof Wit) || (m == i - 1 && st instanceof Wit)) {

                                if ((i + (m - i) * 2) < 10 && (i + (m - i) * 2) > -1 && (j + (n - j) * 2) < 10 && (j + (n - j) * 2) > -1) {
                                    Stuk vrij = getInhoud(i + (m - i) * 2, j + (n - j) * 2);
                                    if (vrij == null) {
                                        if (po != null) {

                                            po.stro();
                                            moetNeem = true;
                                        }
                                    }
                                }


                            }
                        }

                    }
                }
            }
        }
    }


    /**
     * deze methode wordt gestart om te kunnen bewegen naar een bepaalde locatie.
     * ze is opgedeeld in 2 grote gevallen:
     * -indien er een schijfje geslagen moet worden
     * -indien dit niet het geval is
     */


    public void bewegen(int x, int y) {
        geactiveerd = false;
        if (moetNeem) {
            pakken(x, y);

            moetPakken();
            Stuk st = getInhoud(x, y);

            if (moetNeem) {
                if (st == null) {
                } else {
                    st.ActiveerHetNu();
                    turnEnded = false;
                }
            } else {
                if (witAanZet) {
                    witAanZet = false;
                } else {
                    witAanZet = true;
                }
            }

        } else {
            doeDeZet(x, y);

        }
    }


    /**
     * de naam is vrij evident,
     * belangrijk hier was het disactiveren en de booleans klaar en witaanzet oppereren.
     * klaar is een essentiele boolean voor de controller.
     * wit aan zet betekend dat bij het wisselen van beurt paars aan zet wordt, en dat paarse
     * schijfjes dus kunnen bewegen.
     */
    public void doeDeZet(int row, int column) {
        Stuk destinationPawn = dambord[row][column];
        Stuk selectedPawn = getInhoud(selectedPawnRow, selectedPawnColumn);

        boolean pawnIsWhite = selectedPawn instanceof Wit;

        if (destinationPawn != null) {
            return;
        }

        boolean dir1 = row == 1 + selectedPawnRow && column == 1 + selectedPawnColumn;
        boolean dir2 = row == 1 + selectedPawnRow && column == selectedPawnColumn - 1;
        boolean dir3 = row == selectedPawnRow - 1 && column == 1 + selectedPawnColumn;
        boolean dir4 = row == selectedPawnRow - 1 && column == selectedPawnColumn - 1;
        boolean correctDestination;

        if (selectedPawn.isDam) {
            correctDestination = dir1 || dir2 || dir3 || dir4;
        } else if (pawnIsWhite) {
            correctDestination = dir1 || dir3;
        } else {
            correctDestination = dir2 || dir4;
        }

        if (correctDestination) {
            selectedPawn.Disactiveren();
            dambord[row][column] = selectedPawn;
            dambord[selectedPawnRow][selectedPawnColumn] = null;
            turnEnded = true;
            geactiveerd = false;
            witAanZet = !witAanZet;
        }
    }


    //scanner
    public void scannen() {
        for (int column = 0; column < 10; column++) {
            for (int row = 0; row < 10; row++) {
                Stuk pawn = getInhoud(row, column);
                if (pawn == null) {
                    continue;
                }

                if (pawn.geKlikt()) {
                    selectedPawnRow = row;
                    selectedPawnColumn = column;
                } else if (moetNeem) {
                    pawn.aanZet = false;
                }
            }
        }
    }

    //beurtwissel
    public void beurtwissel() {
        for (int column = 0; column < 10; column++) {
            for (int row = 0; row < 10; row++) {
                Stuk pawn = getInhoud(row, column);
                if (pawn == null){
                    continue;
                }

                if (pawn instanceof Wit){
                    pawn.aanZet = !witAanZet;
                } else {
                    pawn.aanZet = witAanZet;
                }
            }
        }
        // pakken
    }

    public void pakken(int destinationRow, int destinationColumn) {

        Stuk selectedPawn = getInhoud(selectedPawnRow, selectedPawnColumn);
        Stuk pawnToTake = getInhoud((destinationRow + selectedPawnRow) / 2, (destinationColumn + selectedPawnColumn) / 2);
        Stuk pawnDestination = getInhoud(destinationRow, destinationColumn);

        boolean selectedPawnIsWitEnPawnToTakeIsPaars = selectedPawn instanceof Wit && pawnToTake instanceof Paars;
        boolean pawnToTakeIsPaarsEnSelectedPawnIsWit = pawnToTake instanceof Wit && selectedPawn instanceof Paars;
        boolean selectedPawnAndPawnToTakeConditions = selectedPawnIsWitEnPawnToTakeIsPaars || pawnToTakeIsPaarsEnSelectedPawnIsWit;
        boolean pawnDestinationIsNull = pawnDestination == null;

        boolean pawnIsWhite = selectedPawn instanceof Wit;

        boolean dir1 = destinationRow == 2 + selectedPawnRow && destinationColumn == 2 + selectedPawnColumn;
        boolean dir2 = destinationRow == 2 + selectedPawnRow && destinationColumn == selectedPawnColumn - 2;
        boolean dir3 = destinationRow == selectedPawnRow - 2 && destinationColumn == 2 + selectedPawnColumn;
        boolean dir4 = destinationRow == selectedPawnRow - 2 && destinationColumn == selectedPawnColumn - 2;
        boolean correctDestination;

        if (selectedPawn.isDam) {
            correctDestination = dir1 || dir2 || dir3 || dir4;
        } else if (pawnIsWhite) {
            correctDestination = dir1 || dir3;
        } else {
            correctDestination = dir2 || dir4;
        }


        if (selectedPawnAndPawnToTakeConditions && pawnDestinationIsNull && correctDestination) {
            int rowOfPawnToTake = (destinationRow - selectedPawnRow) / 2 + selectedPawnRow;
            int columnOfPawnToTake = (destinationColumn - selectedPawnColumn) / 2 + selectedPawnColumn;

            dambord[rowOfPawnToTake][columnOfPawnToTake] = null;
            dambord[destinationRow][destinationColumn] = selectedPawn;
            dambord[selectedPawnRow][selectedPawnColumn] = null;

            turnEnded = true;
            Stuk newDestinationPawn = getInhoud(destinationRow, destinationColumn);
            newDestinationPawn.Disactiveren();
            moetNeem = false;
        }
    }                                       //checkitout

    public void makeDamIfItNeedsToBeADam() {
        for (int column = 0; column < 10; column++){
            Stuk row0Pawn = getInhoud(column, 0);
            Stuk row9Pawn = getInhoud(column, 9);

            if (row0Pawn instanceof Paars){
                row0Pawn.isDam = true;
            }

            if (row9Pawn instanceof Wit) {
                row9Pawn.isDam = true;
            }
        }
    }

}

