package ru.kata.spring.boot_security.demo.model;

public class LongId {
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LongId(Long id) {
        this.id = id;
    }
}
