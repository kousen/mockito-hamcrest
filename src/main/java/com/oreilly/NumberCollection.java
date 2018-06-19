package com.oreilly;

import java.util.List;

public class NumberCollection {
    private List<Integer> numbers;

    public NumberCollection(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public int getTotal() {
        int total = 0;
        for (int i = 0; i < numbers.size(); i++) {
            total += numbers.get(i);
        }
        return total;
    }
}
