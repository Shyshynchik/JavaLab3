package cli;

import converter.Converter;
import mysql.connection.MySQLConnection;
import mysql.entity.Laptop;
import mysql.entity.Tablet;
import mysql.service.DBService;
import mysql.service.LaptopService;
import mysql.service.TabletService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

public class Cli {
    private final static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final static Connection connection = MySQLConnection.connect();
    private final static TabletService tabletService = new TabletService(connection);
    private final static LaptopService laptopService = new LaptopService(connection);

    public static void start() {
        System.out.println("Добро пожаловать в подобие командной строки");
        while (true) {
            try {
                System.out.println("""
                        Список команд:
                        1 : Работа с CSV
                        2 : Добавить данные
                        3 : Вывести все данные
                        4 : Вывести определенные данные
                        5 : Dirty Read (in progress)
                        6 : Очистить БД""");

                int command = Integer.parseInt(reader.readLine());

                if (command == 1) {
                    workWithCSV();
                } else if (command == 2) {
                    addData();
                } else if (command == 3) {
                    findAllData();
                } else if (command == 4) {
                    findData();
                }  else if (command == 5) {
                    dirtyRead();
                } else if (command == 6) {
                    deleteAllData();
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Неверная команда");
            }
        }
    }
    /**
     * 1 : Работа с CSV
     */
    private static void workWithCSV() {
        Converter converter = new Converter(connection);
        converter.setCsvHandler();
        while (true) {
            try {
                System.out.println("""
                        Список команд:
                        1 : Перенести из CSV в БД
                        2 : Перенести из БД в CSV
                        3 : Завершить""");
                int command = Integer.parseInt(reader.readLine());
                if (command == 1) {
                    converter.fromCsvToSQL();
                    System.out.println("Запись завершена");
                } else if (command == 2) {
                    converter.fromSQLToCSV();
                    System.out.println("Запись завершена");
                } else {
                    converter.logClose();
                    System.out.println("Работа с csv завершена");
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 2 : Добавить данные
     */
    private static void addData() {
        while (true) {
            try {
                System.out.println("""
                        Список команд:
                        1 : Добавить ноутбук
                        2 : Добавить планшет
                        3 : Завершить""");
                int command = Integer.parseInt(reader.readLine());
                if (command == 1) {
                    System.out.println(addLaptop());
                    System.out.println("Запись завершена");
                } else if (command == 2) {
                    System.out.println(addTablet());
                    System.out.println("Запись завершена");
                } else {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 2.1 : Добавить ноутбук
     */
    private static Laptop addLaptop() {
        System.out.println("""
                Введите через запятую следущие данные:
                Производителя
                Диагональ
                ОЗУ
                Процессор
                Видеокарту""");
        Laptop laptop = new Laptop();
        try {
            String[] command = reader.readLine().split(",");
            laptop = laptopService.add(command[0], Double.parseDouble(command[1]),
                    Integer.parseInt(command[2]), command[3], command[4]);
        } catch (IOException e) {
            System.out.println("Ошибка добавления");
        }

        return laptop;
    }

    /**
     * 2.2 : Добавить планшет
     */
    private static Tablet addTablet() {
        System.out.println("""
                Введите через запятую следущие данные:
                Производителя
                Диагональ
                ОЗУ
                Операционную систему
                Память""");
        Tablet tablet = new Tablet();
        try {
            String[] command = reader.readLine().split(",");
            tablet = tabletService.add(command[0], Double.parseDouble(command[1]),
                    Integer.parseInt(command[2]), command[3], Integer.parseInt(command[4]));
        } catch (IOException e) {
            System.out.println("Ошибка добавления");
        }

        return tablet;
    }

    /**
     * 3 : Вывести все данные
     */
    private static void findAllData() {
        System.out.println(tabletService.getAll());
        System.out.println(laptopService.getAll());
    }

    /**
     * 4 : Вывести определенные данные
     */
    private static void findData() {
        try {
            System.out.println("""
                    Введите через запятую критерии поиска, если он не нужен введите -:
                    Брэнд
                    Процессор
                    Операционная система
                    Видеокарта
                    (Операционная система для планшетов, процессор и видеокарта для ноутбуков)""");
            String[] command = reader.readLine().split(",");
            if (command[1].equals("-") && command[2].equals("-") && command[3].equals("-")) {
                System.out.println(laptopService.find(command[0], command[1], command[3]));
                System.out.println(tabletService.find(command[0], command[2]));
            } else if (command[2].equals("-")) {
                System.out.println(laptopService.find(command[0], command[1], command[3]));
            } else if (command[1].equals("-") && command[3].equals("-")) {
                System.out.println(tabletService.find(command[0], command[2]));
            } else {
                System.out.println("Для подобного варианта не существует данных");
            }
        } catch (Exception e) {
            System.out.println("Ошибка добавления");
        }
    }

    /**
     * 5 : Dirty read
     */
    private static void dirtyRead() {

        try {
            System.out.println("""
                    Введите тип изоляции транзакции -:
                    1 : Read uncommitted
                    2 : Read committed
                    3 : Repeatable read
                    4 : Serializable""");
            String command = reader.readLine();
            int transaction = 0;

            switch (command) {
                case "1":
                    transaction = Connection.TRANSACTION_READ_UNCOMMITTED;
                    break;
                case "2":
                    transaction = Connection.TRANSACTION_READ_COMMITTED;
                    break;
                case "3":
                    transaction = Connection.TRANSACTION_REPEATABLE_READ;
                    break;
                case "4":
                    transaction = Connection.TRANSACTION_SERIALIZABLE;
                    break;
                default:
                    return;
            }

            int finalTransaction = transaction;

            Runnable r1 = () -> {
                System.out.printf("%s started... \n", Thread.currentThread().getName());
                transactionOne(finalTransaction);
                System.out.printf("%s ended... \n", Thread.currentThread().getName());
            };
            Runnable r2 = () -> {
                System.out.printf("%s started... \n", Thread.currentThread().getName());
                transactionTwo(finalTransaction);
                System.out.printf("%s ended... \n", Thread.currentThread().getName());
            };

            Thread myThread1 = new Thread(r1, "MyThread one");
            Thread myThread2 = new Thread(r2, "MyThread two");
            myThread1.start();
            myThread2.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 5.1 MyThread one
     */
    private static void transactionOne(int transaction) {
        try {
            if (connection != null) {
                connection.setTransactionIsolation(transaction);
                connection.setAutoCommit(false);

                Converter converter = new Converter(connection);
                converter.setCsvHandler();

                converter.fromCsvToSQL();
                converter.logClose();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + laptopService.getAll());
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 5.2 MyThread two
     */
    private static void transactionTwo(int transaction) {
        Connection connect = MySQLConnection.connect();
        LaptopService laptopServiceL = new LaptopService(connect);
        try {
            if (connect != null) {
                connect.setTransactionIsolation(transaction);
                connect.setAutoCommit(false);

                System.out.println(Thread.currentThread().getName() + laptopServiceL.getAll());



                laptopServiceL.add("Asus", 12.2, 12, "samara", "tomsk");
                laptopServiceL.add("Asus", 13.3, 13, "lada", "rostov");
                laptopServiceL.add("Asus", 14.4, 14, "vesta", "moscow");

                System.out.println(Thread.currentThread().getName() + laptopServiceL.getAll());

                try {
                    Thread.sleep(80000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                connect.commit();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 6 : Очистить БД
     */
    private static void deleteAllData() {
        DBService dbService = new DBService(connection);
        dbService.deleteData();
    }
}
