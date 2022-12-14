package com.test.idea.twelve;

public class Enrolee extends Student {
    private int id;
    private String faculty;
    private String full_name;
    private String group;

    protected Enrolee() {
        in();
    }
    
    //Проставленная над методом аннотация @Override сообщает компилятору (да и читающим твой код программистам тоже): 
    //«Все ок, это не ошибка и не моя забывчивость. Я помню, что такой метод уже есть, и хочу переопределить его»
    @Override
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

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getFaculty() {
        return faculty;
    }

    @Override
    public String getFull_name() {
        return full_name;
    }

    @Override
    public String getGroup() {
        return group;
    }
}
