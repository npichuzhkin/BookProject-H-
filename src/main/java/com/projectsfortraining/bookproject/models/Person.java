package com.projectsfortraining.bookproject.models;


import javax.validation.constraints.*;

public class Person {
    private int personId;
    @NotEmpty(message = "ФИО должно быть заполнено")
    @Size(min=2, max=200, message = "ФИО должно содержать от 2 до 200 символов")
    @Pattern(regexp = "[А-Я].* [А-Я].* [А-Я].*", message = "ФИО должно быть следующего формата: Фамилия Имя Отчество")
    private String name;

    @Max(value = 3000, message = "Год должен быть целым числом и не превышать 3000")
    @Positive(message = "Год должен иметь положительное значение")
    private int yearOfBirth;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
