package ru.bikert.filenet;

import com.filenet.api.collection.IndependentObjectSet;
import com.filenet.api.constants.AutoUniqueName;
import com.filenet.api.constants.DefineSecurityParentage;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.*;
import com.filenet.api.property.Properties;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class FileNetManager {

    private Logger logger = LogManager.getLogger(FileNetManager.class);

    private ObjectStore objectStore;
    private Folder rootFolder;


    public FileNetManager() {
        FileNetConnection fileNetConnection = new FileNetConnection();
        fileNetConnection.connect();
        objectStore = fileNetConnection.getObjectStore();
    }

    public Folder createRootFolderIfNotExists(){

        try {
            Folder folder = Factory.Folder.fetchInstance(objectStore,"/Программа адаптации", null);
            createFoldersIfNotExists(folder);
            createEmployeeIfNotExists();
            return folder;
        }catch (Exception e){
            Folder newFolder = Factory.Folder.createInstance(objectStore, null);
            newFolder.set_Parent(objectStore.get_RootFolder());
            newFolder.set_FolderName("Программа адаптации");
            newFolder.save(RefreshMode.NO_REFRESH);
            return newFolder;
        }
    }

    private void createFoldersIfNotExists(Folder folder){
        String[] name = {"приказы", "договоры", "заявления", "cотрудники"};
        for (String s: name){
            try {
                getFolder(folder.get_PathName() + "/" + s);
            }catch (Exception e){
                Folder newFolder = Factory.Folder.createInstance(objectStore, null);
                newFolder.set_Parent(folder);
                newFolder.set_FolderName(s);
                newFolder.save(RefreshMode.NO_REFRESH);
            }
        }
    }

    private void createEmployeeIfNotExists(){
        String[] nameEmloyee = {"Senior", "Junior", "Middle"};
        String path = "/Программа адаптации/cотрудники/";
        for (String name:nameEmloyee) {
            try {
                getEmployee(name);
            }catch (Exception e){
                CustomObject employee = Factory.CustomObject.createInstance(objectStore, "Employee");
                Properties props = employee.getProperties();
                props.putValue("FullName",name);
                employee.save(RefreshMode.REFRESH);

                Folder folder = getFolder(path);
                ReferentialContainmentRelationship rel = folder.file(employee,AutoUniqueName.AUTO_UNIQUE,null,DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
                rel.save(RefreshMode.REFRESH);
            }
        }
    }
    public CustomObject getEmployee(String name){
        String path = "/Программа адаптации/cотрудники/" + name;
        CustomObject employee = Factory.CustomObject.fetchInstance(objectStore, path,null);
        return employee;
    }
    public Folder getFolder(String path){
        try {
            return Factory.Folder.fetchInstance(objectStore, path, null);
        }catch (Exception e){
            return null;
        }
    }

    public Document createDocument(String className){
        return Factory.Document.createInstance(objectStore, className);
    }

    public Document getDocument(String path){
        try {
            return Factory.Document.fetchInstance(objectStore,path,null);
        }catch (Exception e){
            return null;
        }
    }

    public boolean removeDocument(String path){
        try {
            Document document = getDocument(path);
            document.delete();
            document.save(RefreshMode.NO_REFRESH);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public IndependentObjectSet getObjectSetThroughSQL(String query){

        SearchSQL searchSQL = new SearchSQL(query);
        SearchScope searchScope = new SearchScope(objectStore);
        IndependentObjectSet objects = searchScope.fetchObjects(searchSQL, null, null, true);

        return objects;
    }
}
