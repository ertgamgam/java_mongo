package com.mycompany.mongodb;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.Scanner;

public class CRUDMongo {

    static MongoClient mongoClient;
    static DB db;
    static DBCollection dBCollection;

    public static void main(String[] args) throws UnknownHostException {

        mongoClient = new MongoClient();
        DB db = mongoClient.getDB("SchoolDB");
        dBCollection = db.getCollection("Teachers");

        int closeProgram = 0;
        while (closeProgram != 1) {
            Scanner scan = new Scanner(System.in);
            System.out.println("MAIN MENU");
            System.out.println("1-List all the teachers");
            System.out.println("2-Insert a teacher");
            System.out.println("3-Delete a teacher");
            System.out.println("4-Update a teacher");
            System.out.println("Otherwise -Exit");
            int m = scan.nextInt();

            switch (m) {
                case 1:
                    printTeachers();
                    break;
                case 2:
                    insertNewTeacher();
                    break;
                case 3:
                    deleteATeacher();
                    break;
                case 4:
                    updateATeacher();
                    break;
                default:
                    closeProgram = 1;
                    break;
            }
        }
        mongoClient.close();
    }

    public static void printTeachers() {
        try {
            DBObject query = BasicDBObjectBuilder.start().get();
            DBCursor dBCursor = dBCollection.find(query);

            while (dBCursor.hasNext()) {
                System.out.println(dBCursor.next());
            }
        } catch (Exception e) {
            System.out.println("DB Connection Problem!!! = " + e);
        }

    }

    public static void insertNewTeacher() {
        try {
            Scanner sc = new Scanner(System.in);
            BasicDBObjectBuilder bObjectBuilder = getTeacherInfoWithIdFromUser();

            DBObject dBObject = bObjectBuilder.get();
            dBCollection.insert(dBObject);
            System.out.println("Teacher is added");
        } catch (Exception e) {
            System.out.println("Teacher isn't added = " + e);
        }

    }

    public static void deleteATeacher() {
        try {
            Scanner s = new Scanner(System.in);
            System.out.println("ID=");
            int id = s.nextInt();

            DBObject query = BasicDBObjectBuilder.start().add("id", id).get();
            dBCollection.remove(query);
            System.out.println("Teacher id deleted.");
        } catch (Exception e) {
            System.out.println("Teacher isn't deleted. = " + e);
        }
    }

    public static void updateATeacher() {
        try {
            Scanner s = new Scanner(System.in);
            System.out.println("ID=");
            int id = s.nextInt();
            DBObject query = BasicDBObjectBuilder.start().add("id", id).get();

            BasicDBObjectBuilder bObjectBuilder = getTeacherInfoWithoutIDFromUser();
            bObjectBuilder.add("id", id);
            DBObject bObject = bObjectBuilder.get();

            dBCollection.update(query, bObject);

            System.out.println("Teacher is updated.");
        } catch (Exception e) {
            System.out.println("Teacher isn't updated. = " + e);
        }

    }

    public static BasicDBObjectBuilder getTeacherInfoWithoutIDFromUser() {
        Scanner sc = new Scanner(System.in);

        BasicDBObjectBuilder bObjectBuilder = BasicDBObjectBuilder.start();
        System.out.println("Name= ");
        String name = sc.nextLine();
        bObjectBuilder.add("Name", name);

        System.out.println("Place= ");
        String place = sc.nextLine();
        bObjectBuilder.add("Place", place);

        System.out.println("Department Id= ");
        int did = sc.nextInt();
        bObjectBuilder.add("did", did);

        return bObjectBuilder;
    }

    public static BasicDBObjectBuilder getTeacherInfoWithIdFromUser() {
        BasicDBObjectBuilder bObjectBuilder = getTeacherInfoWithoutIDFromUser();

        Scanner sc = new Scanner(System.in);
        System.out.println("Id= ");
        int id = sc.nextInt();
        bObjectBuilder.add("id", id);
        return bObjectBuilder;
    }

}
