package com.example.tabletwebdb.Enum;

public enum Place
{
    WAREHOUSE("склад"),
    MANAGER("менеджер"),
    INTERVIEWER("интервьюер"),
    CONTRACTOR("подрядчик"),
    OTHER("другое");

    private String name;

    Place(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


}
