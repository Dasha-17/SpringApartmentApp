package ru.isaeva.springcourse.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Apartment {
    private int id;

    @NotNull(message = "Номер апартаментов не может быть пустым")
    @Min(value = 1, message = "Номер апартаментов должен быть больше 0")
    private int number;

    @NotNull(message = "Номер этажа не может быть пустым")
    @Min(value = 1, message = "Номер этажа должен быть больше 0")
    private int floor;

    @Min(value = 20, message = "размер должен быть больше 20 метров квадратрных")
    private int size;

    public Apartment() {

    }

    public Apartment(int id, int number, int floor,  int size) {
        this.id = id;
        this.number = number;
        this.floor = floor;
        this.size = size;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumber( int number) {
        this.number = number;
    }

    public void setFloor ( int floor) {
        this.floor = floor;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public int getFloor() {
        return floor;
    }

    public int getSize() {
        return size;
    }
}