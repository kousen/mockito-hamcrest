package com.oreilly.astro.json;

public class Assignment {
    private final String name;
    private final String craft;

    public Assignment(String name, String craft) {
        this.name = name;
        this.craft = craft;
    }

    public String getCraft() {
        return craft;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "name='" + name + '\'' +
                ", craft='" + craft + '\'' +
                '}';
    }
}
