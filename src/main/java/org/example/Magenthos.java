package org.example;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import utils.MongoDBUtils;
import utils.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.mongodb.client.model.Filters.eq;

public class Magenthos {
    public static void main(String[] args) {
        MongoDBUtils dbUtils = MongoDBUtils.getInstance();
        String[] tenants = {"tenant3_1","tenant3_2", "tenant3_3", "tenant3_4", "tenant3_5" };
        int numThreads = tenants.length * 3; // Two threads per tenant
        System.out.println("Total Tenants:"+numThreads);

        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < tenants.length; i++) {
            String tenantId = tenants[i];
            for (int j = 0; j < 2; j++) { // Two threads per tenant
                executorService.execute(() -> {
                    for (int k = 0; k < 100; k++) { //numRecordsPerTenant 10000
                        dbUtils.insertDocument("forethought3", "ai", dbUtils.createDocument(tenantId, k));
                    }
                });
            }
        }
        executorService.shutdown();
    }
}
