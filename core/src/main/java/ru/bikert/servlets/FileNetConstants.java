package ru.bikert.servlets;

interface FileNetConstants {
    interface FolderName{
        String ROOT_FOLDER = "Программа адаптации";
        String ORDER = "приказы";
        String CONTRACT = "договоры";
        String STATEMENTS = "заявления";
    }
    interface DocumentClass{
        String ORDER = "Order";
        String CONTRACT = "Contract";
        String STATEMENTS = "Statements";
    }
    interface PropertyFileNet{
        String dateApproval = "dateApproval";           //Дата утверждения
        String documentStatus = "documentStatus";       //Статус документа
        String numberDocument = "numberDocument";       //Номер документа
        String Responsible = "Responsible1";            //Ответственный
        String receiptDate = "receiptDate";             //Дата поступления
        String Counterparty="Counterparty";             //Контрагент
        String DocumentTitle = "DocumentTitle";
    }

}
