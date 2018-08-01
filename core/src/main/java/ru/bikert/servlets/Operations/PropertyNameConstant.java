package ru.bikert.servlets.Operations;

public interface PropertyNameConstant {

        interface PathOperation {
            String DELETE = "/start/delete";
            String СREATE = "/start/create";
            String DOWNLOAD = "Download";
            String EDIT = "/start/edit";
            String REPORT = "Report";
            String TRANSFER = "Transfer";
        }

        interface DocumentClass{
            String ORDER = "Order";
            String CONTRACT = "Contract";
            String STATEMENTS = "Statements";
        }
        interface DocumentStatus{
            String First = "На ознакомлении";
            String Second  = "На утверждении";
            String Third = "Утвержден";

        }
        interface FolderNames{
            String ORDER = "приказы";
            String CONTRACT = "договоры";
            String STATEMENTS = "заявления";
        }
        interface FolderPath{
            String ROOTFOLDER = "/Программа адаптации";
            String ORDER = "/Программа адаптации/приказы";
            String CONTRACT = "/Программа адаптации/договоры";
            String STATEMENTS = "/Программа адаптации/заявления";
        }
        interface Property{

            String dateApproval = "dateApproval";           //Дата утверждения
            String documentStatus = "documentStatus";       //Статус документа
            String numberDocument = "numberDocument";       //Номер документа
            String Responsible = "Responsible1";            //Ответственный
            String receiptDate = "receiptDate";             //Дата поступления
            String Counterparty="Counterparty";             //Контрагент
            String DocumentTitle = "DocumentTitle";
        }
        interface Employee{
            String[] nameEmloyee = {"Senior", "Junior", "Middle"};
        }
    }
