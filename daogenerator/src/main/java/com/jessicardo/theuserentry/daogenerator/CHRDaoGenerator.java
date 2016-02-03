package com.jessicardo.mystery.daogenerator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class CHRDaoGenerator {

    private static final String OUTPUT_PACKAGE = "com.jessicardo.theuserentry.dbgen";

    private static final String OUTPUT_TEST_PACKAGE = "com.jessicardo.theuserentry.tests.dbgen";

    private static final int VERSION = 1;

    private static Entity sPersonEntity;

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(VERSION, OUTPUT_PACKAGE);
        schema.setDefaultJavaPackageDao(OUTPUT_PACKAGE);
        schema.setDefaultJavaPackageTest(OUTPUT_TEST_PACKAGE);
        schema.enableKeepSectionsByDefault();

        // Add Table Schemas
        sPersonEntity = addPerson(schema);

        try {
            String appFolder = "../app/src/main/java";
            String testsFolder = "../app/src/androidTest/java";
            // Uncomment if we want to generate tests -> unused for now
//            new DaoGenerator().generateAll(schema, appFolder, testsFolder);
            new DaoGenerator().generateAll(schema, appFolder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Entity addPerson(Schema schema) {
        Entity category = schema.addEntity("Person");
        category.addLongProperty("id").columnName("_id").primaryKey().autoincrement();
        category.addStringProperty("first_name");
        category.addStringProperty("last_name");
        category.addStringProperty("dob");
        category.addStringProperty("zip_code");
        return category;
    }
}
