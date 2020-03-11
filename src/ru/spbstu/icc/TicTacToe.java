package ru.spbstu.icc;

import java.util.Arrays;

public class TicTacToe {

    enum CellValue {
        EMPTY(' '), CROSS('X'), ZERO('O');

        private char Char;

        CellValue(char symbol) {
            this.Char = symbol;
        }

        public char getChar() {
            return Char;
        }
    }

    private final int size;
    private CellValue[][] field;

    private final Pair[] steps = new Pair[]{
            new Pair(1, 1),
            new Pair(-1, -1),
            new Pair(-1, 1),
            new Pair(1, -1),
            new Pair(0, 1),
            new Pair(0, -1),
            new Pair(1, 0),
            new Pair(-1, 0),
    };

    private int maxCross = 0;
    private int maxZero = 0;

    /******** Конструкторы ********/

    public TicTacToe(int size) {
        if (size >= 3)
            this.size = size;
        else throw new IllegalArgumentException();
        field = new CellValue[this.size][this.size];
        for (int row = 0; row < this.size; row++)
            for (int col = 0; col < this.size; col++)
                field[row][col] = CellValue.EMPTY;
    }

    /**
     * Дефолтное поле, если не указали размер поля
     */
    public TicTacToe() {
        this(3);
    }

    /******** Методы ********/

    private boolean inside(int row, int column) {
        return (row < size && column < size && row >= 0 && column >= 0);
    }

    private boolean setSymbol(int row, int column, CellValue symbol) {
        if (inside(row, column)) {
            if (field[row][column] == CellValue.EMPTY && field[row][column] != symbol) {
                field[row][column] = symbol;
                if (symbol == CellValue.CROSS) maxCross = findMaxSequenceAfterSetSymbol(row, column, symbol, maxCross);
                else maxZero = findMaxSequenceAfterSetSymbol(row, column, symbol, maxZero);
                return true;
            } else return false;
        } else throw new IllegalArgumentException("Указанные значения выходят за рамки поля");
    }

    /**
     * @param row номер строки, в которую добавили символ
     * @param column номер столбца, в которую добавили символ
     * @param symbol символ, который добавили в ячейку
     * @param maxSymbol вспомогательная переменная, для нахождения максимальной последовательности этого символа
     *
     * После добавления в поле некоторого символа, максимальная последовательность для этого символа может изменится.
     * Программа проходит по вертикали, горизонтали и 2 диагоналям, на которых лежит ячейка, в которую только что добавили символ.
     *
     * Сокращения:
     * For First Direction = FFD   -  первое направление по диагонали от точки  ⇖ или ⇗
     * For Second Direction = FSD   -  второе направление по диагонали от точки  ⇘ или ⇙
     */
    private int findMaxSequenceAfterSetSymbol(int row, int column, CellValue symbol, int maxSymbol) {
        for (int i = 0; i < 8; i += 2) {
            int counterFFD = 1;
            int counterFSD = 1;
            int rFFD = row + steps[i].row();
            int cFFD = column + steps[i].column();
            int rFSD = row + steps[i + 1].row();
            int cFSD = column + steps[i + 1].column();
            while (inside(rFFD, cFFD) && field[rFFD][cFFD] == symbol) {
                rFFD += steps[i].row();
                cFFD += steps[i].column();
                counterFFD++;
            }
            while (inside(rFSD, cFSD) && field[rFSD][cFSD] == symbol) {
                rFSD += steps[i + 1].row();
                cFSD += steps[i + 1].column();
                counterFSD++;
            }
            int counter = counterFFD + counterFSD - 1;
            if (counter > maxSymbol) return counter;
        }
        return maxSymbol;
    }

    public boolean setCross(int row, int column) {
        return this.setSymbol(row, column, CellValue.CROSS);
    }

    public boolean setZero(int row, int column) {
        return this.setSymbol(row, column, CellValue.ZERO);
    }

    public boolean clearSymbol(int row, int column) {
        if (inside(row, column)) {
            if (field[row][column] == CellValue.EMPTY) return false;
            CellValue symbol = field[row][column];
            field[row][column] = CellValue.EMPTY;
            if (symbol == CellValue.CROSS) maxCross = findMaxSequenceAfterClearSymbol(symbol);
            else maxZero = findMaxSequenceAfterClearSymbol(symbol);
            return true;
        } else throw new IllegalArgumentException("Указанные значения выходят за рамки поля");
    }

    /**
     * @param symbol символ, который удалили из ячейки
     *
     * После удаления из поля некоторого символа, максимальная последовательность для этого символа может изменится.
     * Программа проходит по всем вертикалям, горизонталям и диагоналям и ищет новую максимальную последовательность для этого символа.
     * Сначала программа проходит по диагоналям, а затем проверяет горизонтали и вертикали.
     */
    private int findMaxSequenceAfterClearSymbol(CellValue symbol) {
        int col = this.size;
        int formal = 0;
        for (int row = 0; row < this.size; row++) {
            col--;
            int r = 0;
            int c = col;
            formal = findMaxDiagonallySequenceAfterClearSymbol(r, c, symbol, formal, 0); //first diagonal
            r = 6;
            c = col;
            formal = findMaxDiagonallySequenceAfterClearSymbol(r, c, symbol, formal, 2); //second diagonal
            r = row;
            c = 0;
            formal = findMaxStraightSequenceAfterClearSymbol(r, c, symbol, formal, 4); //horizontal
            r = 0;
            c = col;
            formal = findMaxStraightSequenceAfterClearSymbol(r, c, symbol, formal, 6); //vertical
        }
        return formal;
    }

    private int findMaxDiagonallySequenceAfterClearSymbol(int row, int column, CellValue symbol, int formal, int i) {
        int firstDirection = 0;
        int secondDirection = 0;
        while (inside(row, column) && inside(column, row)) {
            if (field[row][column] == symbol) firstDirection++;
            else {
                if (firstDirection > formal) formal = firstDirection;
                firstDirection = 0;
            }
            if (field[column][row] == symbol) secondDirection++;
            else {
                if (secondDirection > formal) formal = secondDirection;
                secondDirection = 0;
            }
            row += steps[i].row();
            column += steps[i].column();
        }
        if (firstDirection > formal) formal = firstDirection;
        else if (secondDirection > formal) formal = secondDirection;
        return formal;
    }

    private int findMaxStraightSequenceAfterClearSymbol(int row, int column, CellValue symbol, int formal, int i) {
        int straightLine = 0;
        while (inside(row, column)) {
            if (field[row][column] == symbol) straightLine++;
            else {
                if (straightLine > formal) formal = straightLine;
                straightLine = 0;
            }
            row += steps[i].row();
            column += steps[i].column();
        }
        if (straightLine > formal) formal = straightLine;
        return formal;
    }

    public int getMaxCrossSequence() {
        return maxCross;
    }

    public int getMaxZeroSequence() {
        return maxZero;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result.append(field[i][j]);
                result.append("   ");
            }
            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicTacToe ticTacToe = (TicTacToe) o;
        return Arrays.equals(field, ticTacToe.field);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(field);
    }
}