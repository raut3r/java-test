package com.filip;

abstract class Employee implements calculateSalary{
    private int id;
    private String name;
    private int workingHours;

    // Getters and setters
    // NAME
    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    // WORKING HOURS
    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int newWorkingHour) {
        this.workingHours = newWorkingHour;
    }

    // ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
