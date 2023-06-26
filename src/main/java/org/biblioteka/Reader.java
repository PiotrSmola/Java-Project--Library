package org.biblioteka;

import java.util.UUID;

class Reader {
    private UUID id;
    private String name;
    private String pesel;

    public Reader(String name, String pesel) throws IllegalArgumentException {
        if (!isValidPesel(pesel)) {
            throw new IllegalArgumentException("Nieprawidłowy nr PESEL");
        }
        this.id = UUID.randomUUID();
        this.name = name;
        this.pesel = pesel;
    }

    public Reader(UUID id, String name, String pesel) throws IllegalArgumentException {
        if (!isValidPesel(pesel)) {
            throw new IllegalArgumentException("Nieprawidłowy nr PESEL");
        }
        this.id = id;
        this.name = name;
        this.pesel = pesel;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPesel() {
        return pesel;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Imię i nazwisko: " + name + ", PESEL: " + pesel;
    }

    private boolean isValidPesel(String pesel) {
        if (pesel == null || pesel.length() != 11) {
            return false;
        }
        for (char ch : pesel.toCharArray()) {
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return true;
    }
}

