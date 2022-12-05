/*
Zharov Andrey Alekseevich
Bulatov Alexey Evgenievich
Denisova Irina Sergeevna
Rusakov Georgy Vyacheslavovich
Ergardt Maxim Vladimirovich
Zakharov Denis Evgenievich
Soboleva Elina Petrovna
 */

package com.test.idea.twelve;

//импортируем библиотеки для работы с excel
import com.test.idea.EXL;
import com.test.idea.sql;
//для считывания данных с клавиатуры
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
//для работы с sql
import com.mysql.cj.jdbc.Driver;
//в особенности потом понадобятся Connection, ResultSet и Statement
import java.sql.*;


//главный КЛАСС
public class MAIN {

    //точка входа в программу + вывод информации об ошибках с бд
    //КЛАСС main()
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        //классу scanner присваиваем в качестве аргумента system.in
        Scanner scan = new Scanner(System.in);

        //начальное значение для switch case
        int x = 0;
        String s = "";

        //ввод названия таблицы с клавиатуры
        System.out.println("Введите название таблицы: ");
        String tablename = scan.nextLine();

        //цикл работает до тех пор, пока пользователь не введет 5
        while (!"7".equals(s)) {
            System.out.println();
            System.out.println("1 - Вывести все таблицы из текущей БД");
            System.out.println("2 - Создать таблицу в текущей БД");
            System.out.println("3 - Ввести данные о всех студентах и сохранить их в MySQL с последующим табличным (форматированным) выводом в консоль");
            System.out.println("4 - Сохранить итоговые результаты из MySQL в Excel и вывести их в консоль");
            System.out.println("5 - Вывести данные о студенте по ID из MySQL");
            System.out.println("6 - Удалить данные о студенте из MySQL по ID");
            System.out.println("7 - Выход из программы");
            s = scan.next();

            //пробуем перевести пользовательский ввод в int
            try {
                x = Integer.parseInt(s);
            }
            //выдаем сообщение об ошибке ввода, и так до тех пор, пока пользователь не введет число
            catch (NumberFormatException e) {
                System.out.println("Неверный формат ввода.");
            }

            //оператор switch для множества развилок
            //эквивалентно оператору if
            switch (x) {

                //если пользователь ввел цифру 1, то...
                case 1 -> {
                    sql.TablesOutput();
                }

                //если пользователь ввел цифру 2, то...
                case 2 -> {
                    String query = " (ID int, faculty varchar(2047), full_name varchar(2047), group1 varchar(2047))";
                    sql.CreatingSQLTable(tablename, query);
                }

                //если пользователь ввел цифу 3, то...
                case 3 -> {

                    //регистрируем драйвер для дальнейшей работы (управление jdbc)
                    //КЛАСС DriverManager, МЕТОД registerDriver
                    DriverManager.registerDriver(new Driver());

                    //имя драйвера
                    //КЛАСС Class, МЕТОД forName
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    //пытаемся установить соединение с заданным url бд
                    //ОБЪЕКТ Connection для работы с бд
                    //КЛАСС DriverManager, МЕТОД getConnection
                    Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "кщще");
                    System.out.println("Успешно законнектились к БД!");


                    System.out.print("Введите кол-во студентов: ");
                    int amount = scan.nextInt();
                    scan.nextLine();
                    Student[] result_arr = new Student[amount];
                    System.out.println();
                    for (int i = 0; i < result_arr.length; i += 1) {
                        Enrolee enrolee = new Enrolee();
                        result_arr[i] = enrolee;
                    }

                    boolean issorted = false;
                    while (!issorted) {
                        issorted = true;
                        for (int i = 0; i < result_arr.length - 1; i += 1) {
                            List<String> before = Arrays.asList(result_arr[i].getFull_name(), result_arr[i + 1].getFull_name());
                            List<String> after = Arrays.asList(result_arr[i].getFull_name(), result_arr[i + 1].getFull_name());
                            after.sort(null);
                            if (!before.equals(after)) {
                                Student buffer = result_arr[i];
                                result_arr[i] = result_arr[i + 1];
                                result_arr[i + 1] = buffer;
                                issorted = false;
                            }
                        }
                    }

                    System.out.println("_____________________________________________________________________________________________________________________________________");
                    System.out.printf("| %30s | %30s | %30s | %30s | \n", "id", "faculty", "full_name", "group");
                    for (Student i : result_arr) {
                        System.out.printf("| %30s | %30s | %30s | %30s | \n", i.getId(), i.getFaculty(), i.getFull_name(), i.getGroup());
                    }
                    System.out.println("_____________________________________________________________________________________________________________________________________");

                    String query2 = "INSERT INTO " + tablename + " (ID, faculty, full_name, group1) VALUES (?, ?, ?, ?)";
                    PreparedStatement stmt3 = con2.prepareStatement(query2);
                    for (int i = 0; i < result_arr.length; i += 1) {
                        stmt3.setInt(1, result_arr[i].getId());
                        stmt3.setString(2, result_arr[i].getFaculty());
                        stmt3.setString(3, result_arr[i].getFull_name());
                        stmt3.setString(4, result_arr[i].getGroup());
                        stmt3.executeUpdate();
                    }

                    System.out.println("Данные в MySQL успешно внесены!");

                    ResultSet rs2 = stmt3.executeQuery("SELECT * FROM " + tablename + "");

                    ///вывод количества строк в таблице
                    //создаем sql запрос
                    String query = "select count(*) from " + tablename;

                    //пробуем выполнить запрос через try - catch
                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "кщще");
                         Statement stmt = con.createStatement();
                         ResultSet rs = stmt.executeQuery(query)) {
                        while (rs.next()) {
                            int count = rs.getInt(1);
                            System.out.println("Всего внесено строк в таблицу " + tablename + " : " + count);
                        }
                    } catch (SQLException sqlEx) {
                        sqlEx.printStackTrace();
                    }
                }
                //если пользователь ввел цифру 4, то...
                case 4 -> {
                    EXL.ExcelConvector(tablename);
                }

                //для поиска по id
                case 5 -> {

                    try {
                        DriverManager.registerDriver(new Driver());
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con5 = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "кщще");

                        System.out.println("\nВведите ID студента:");
                        int id = scan.nextInt();

                        Statement stmt = con5.createStatement();
                        String query = "SELECT * FROM " + tablename + " WHERE ID = " + id + "";
                        ResultSet rs = stmt.executeQuery(query);
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int columnsNumber = rsmd.getColumnCount();

                        System.out.println("\nID\tНаправление\tИмя\tГруппа");

                        while (rs.next()) {
                            for(int i = 1 ; i <= columnsNumber; i++){
                                System.out.print(rs.getString(i) + "\t");
                            }
                            System.out.println();
                        }
                    } catch (Exception e){
                        System.out.println(e);
                    }
                }

                //для удаления по id
                case 6 -> {

                    try {
                        DriverManager.registerDriver(new Driver());
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con6 = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "кщще");

                        System.out.println("\nВведите ID студента:");
                        int id = scan.nextInt();

                        Statement stmt = con6.createStatement();
                        String query = "DELETE FROM " + tablename + " WHERE ID = " + id + "";
                        stmt.executeUpdate(query);
                        System.out.println("Студент с указанным ID удален из таблицы.");
                    } catch (Exception e){
                        System.out.println(e);
                    }
                }

                //если пользователь введет 5, то выйдет из программы
                case 7 -> {
                    System.out.println("Вышли из нашей программы.");
                }
            }
        }
    }
}
