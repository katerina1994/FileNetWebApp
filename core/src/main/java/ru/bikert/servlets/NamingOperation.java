package ru.bikert.servlets;

public interface NamingOperation {

    String PRINTHIERARCHY = "printHierarchy";
    String CURRENTCHILDREN = "getCurrentChildren";
    String CREATE = "create";
    String DELETE = "delete";
    String TRANSFER = "transfer";
    String EDIT = "edit";
    String REPORT = "report";
    String DOWNLOAD = "download";

    interface NameButtonOperation{
        String DELETE = "Удалить";
        String СREATE = "Создать";
        String DOWNLOAD = "Выгрузить вложение";
        String EDIT = "Изменить вложение";
        String REPORT = "Отчет по документам";
        String TRANSFER = "Движение документа";
    }

}
