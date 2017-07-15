package org.launchcode.techjobs.console;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * Created by LaunchCode
 */
public class JobData {

    private static final String DATA_FILE = "resources/job_data.csv";
    private static Boolean isDataLoaded = false;
    private static ArrayList<HashMap<String, String>> allJobs;
    public static ArrayList<String> findAll(String field) {

        loadData();

        ArrayList<String> values = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {
            String aValue = row.get(field);

            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }

        return values;
    }


    public static ArrayList<HashMap<String, String>> findAll() {

        loadData();

        return allJobs;
    }


    public static ArrayList<HashMap<String, String>> findByValue(String value) {

        loadData();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {
            for (Map.Entry<String, String> job : row.entrySet()) {
                String searchValue = job.getValue().toLowerCase();
                if (searchValue.contains(value)) {
                    jobs.add(row);
                    break;
                }

            }
        }
        return jobs;
    }

    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {

        loadData();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {

            String aValue = row.get(column).toLowerCase();

            if (aValue.contains(value)) {
                jobs.add(row);
            }
        }

        return jobs;
    }


    private static void loadData() {

        if (isDataLoaded) {
            return;
        }

        try {

            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            for (CSVRecord record : records) {
                HashMap<String, String> newJob = new HashMap<>();

                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                }

                allJobs.add(newJob);
            }

            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }

}
