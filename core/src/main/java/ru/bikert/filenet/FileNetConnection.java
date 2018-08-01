package ru.bikert.filenet;

import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.security.auth.Subject;

class FileNetConnection {
    private static Logger logger = LogManager.getLogger(FileNetConnection.class);

    private  ObjectStore objectStore;
    private  UserContext userContext;
    private  Domain domain;
    private  Subject subject;

    private static final String URL = ConstantConnect.URL;
    private static final String LOGIN = ConstantConnect.login;
    private static final String PASSWORD = ConstantConnect.password;


    public Connection connect() {
        logger.info("Start void connect");
        Connection conn = Factory.Connection.getConnection(URL);
        userContext = UserContext.get();
        subject = UserContext.createSubject(conn, LOGIN, PASSWORD, null);
        userContext.pushSubject(subject);
        logger.info("Subject push");
        domain = Factory.Domain.getInstance(conn, null);

        objectStore = Factory.ObjectStore.fetchInstance(domain,
                ConstantConnect.objectStoreName, null);
        logger.info("get object Store");
        return conn;
    }

    public void closeConnection() {
        userContext.popSubject();
        logger.info("Subject pop");
    }

    public ObjectStore getObjectStore() {
        return objectStore;
    }

}
