package org.example.util;

public class IdGenerator {
    private Integer id = 0;

    public Integer createId() {
        return id++;
    }
}
