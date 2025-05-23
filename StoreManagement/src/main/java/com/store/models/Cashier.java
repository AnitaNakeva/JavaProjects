package com.store.models;

import java.io.Serializable;

public class Cashier implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private double salary;
    private CheckoutRegister register;

    public Cashier(int id, String name, double salary) {
        setId(id);
        setName(name);
        setSalary(salary);
        this.register = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID must be a positive number.");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if (salary < 0) throw new IllegalArgumentException("Salary cannot be negative.");
        this.salary = salary;
    }

    public CheckoutRegister getRegister() {
        return register;
    }

    public void assignRegister(CheckoutRegister register) {
        this.register = register;
        register.setCashier(this);
    }

    public void unassignRegister() {
        if (this.register != null) {
            this.register.setCashier(null);
            this.register = null;
        }
    }

    @Override
    public String toString() {
        return "Cashier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", register=" + (register != null ? register.getRegisterNumber() : "No register assigned") +
                '}';
    }
}


