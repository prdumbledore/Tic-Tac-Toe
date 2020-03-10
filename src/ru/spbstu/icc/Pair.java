package ru.spbstu.icc;

import java.util.Objects;

public class Pair {
    private int row;
    private int column;

    public Pair(Integer first, Integer second) {
        this.row = first;
        this.column = second;
    }

    public int row() {
        return this.row;
    }

    public int column() {
        return this.column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return row == pair.row &&
                column == pair.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "Pair(" + "row=" + row + ", column=" + column + ')';
    }
}
