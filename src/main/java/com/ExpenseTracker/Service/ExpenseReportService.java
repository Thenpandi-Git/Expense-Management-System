package com.ExpenseTracker.Service;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

@Service
public class ExpenseReportService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Document> getCategorySummary(String userId) {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("userId").is(userId)),
                Aggregation.group("category")
                        .sum("amount").as("totalAmount")
        );

        return mongoTemplate.aggregate(
                aggregation, "expenses", Document.class
        ).getMappedResults();
    }
    
    public List<Document> getPaymentModeSummary(String userId) {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("userId").is(userId)),
                Aggregation.group("paymentMode")
                        .sum("amount").as("totalAmount")
        );

        return mongoTemplate.aggregate(
                aggregation, "expenses", Document.class
        ).getMappedResults();
    }

    public List<Document> getMonthlySummary(String userId) {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("userId").is(userId)),
                Aggregation.project()
                        .andExpression("month(date)").as("month")
                        .and("amount").as("amount"),
                Aggregation.group("month")
                		.sum("amount").as("totalAmount"),
                Aggregation.sort(Sort.Direction.ASC, "_id")
        );

        return mongoTemplate.aggregate(
                aggregation, "expenses", Document.class
        ).getMappedResults();
    }
    
    public List<Document> getTopCategories(String userId, int limit) {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("userId").is(userId)),
                Aggregation.group("category").sum("amount").as("totalAmount"),
                Aggregation.sort(Sort.Direction.DESC, "totalAmount"),
                Aggregation.limit(limit)
        );

        return mongoTemplate.aggregate(
                aggregation, "expenses", Document.class
        ).getMappedResults();
    }
    
    public List<Document> getDailyTrend(String userId) {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("userId").is(userId)),
                Aggregation.group("date")
                        .sum("amount").as("totalAmount"),
                Aggregation.sort(Sort.Direction.ASC, "_id")
        );

        return mongoTemplate.aggregate(
                aggregation, "expenses", Document.class
        ).getMappedResults();
    }

    public Document getIncomeExpenseSummary(String userId) {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("userId").is(userId)),
                Aggregation.group("type")
                        .sum("amount").as("totalAmount")
        );

        List<Document> results = mongoTemplate.aggregate(
                aggregation,
                "expenses",
                Document.class
        ).getMappedResults();

        double totalIncome = 0.0;
        double totalExpense = 0.0;

        for (Document doc : results) {

            String type = doc.getString("_id");   // INCOME or EXPENSE
            Number amount = doc.get("totalAmount", Number.class);

            if (amount == null) continue;

            if ("INCOME".equalsIgnoreCase(type)) {
                totalIncome = amount.doubleValue();
            } 
            else if ("EXPENSE".equalsIgnoreCase(type)) {
                totalExpense = amount.doubleValue();
            }
        }

        Document response = new Document();
        response.put("income", totalIncome);
        response.put("expense", totalExpense);
        response.put("balance", totalIncome - totalExpense);

        return response;
    }






    
    
    
}
