package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserService userSer = new UserServiceImpl();
        userSer.createUsersTable(); // Создание таблицы юзеров

        // _____________Добавление в таблицу людей____________________ //
        userSer.saveUser("Ivan", "Sinicin", (byte) 8);
        userSer.saveUser("Alexey", "Kanovalov", (byte) 22);
        userSer.saveUser("Alice", "Romanova", (byte) 19);
        userSer.saveUser("Marya", "Bondar", (byte) 37);

        List<User> userList = userSer.getAllUsers(); // Получение всех юзеров и запись их в лист
        userList.stream().forEach(System.out::println); // Вывод юзеров в консоль
        userSer.cleanUsersTable(); // Очистка таблицы
        userSer.dropUsersTable(); // Удаление таблицы
    }
}
