package com.test.idea.twelve;

import java.util.Scanner;

public class Student {
    Scanner sc = new Scanner(System.in);
    private int id;
    private String faculty;
    private String full_name;
    private String group;

    protected void in() {
        System.out.print("Введите id студента: ");
        this.id = sc.nextInt();
        sc.nextLine();
        System.out.print("Введите направление подготовки студента: ");
        this.faculty = sc.nextLine();
        System.out.print("Введите ФИО студента: ");
        this.full_name = sc.nextLine();
        System.out.print("Введите группу студента: ");
        this.group = sc.nextLine();
    }

    protected void out() {
        System.out.println(this.id);
        System.out.println(this.faculty);
        System.out.println(this.full_name);
        System.out.println(this.group);
    }

    public int getId() {
        return id;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getGroup() {
        return group;
    }
}
