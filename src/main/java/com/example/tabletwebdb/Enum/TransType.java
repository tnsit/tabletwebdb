package com.example.tabletwebdb.Enum;

public enum TransType
{
    SHIPMENT("отправка в регионы"),
    MANAGER("выдача менеджеру"),
    INTERVIEWER("выдача интервьюеру"),
    RETURN("возврат на склад"),
    INTERVIEWER_RETURN("возврат от интервьюера"),
    BETWEEN("между менеджерами"),
    OTHER("другое");

    private String name;

    TransType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
