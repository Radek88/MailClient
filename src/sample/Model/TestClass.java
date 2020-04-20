package sample.Model;

import sample.View.Folders.Folders;

import javax.mail.Folder;


public class TestClass{
    public static void main(String[] args) {
        Folders foldersList = new Folders();
        for (Folder s: foldersList.returnListOfFolders()
             ) {
            System.out.println(s.getFullName());

        }
    }


}
