package com.klef.jfsd.exam;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;

import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory factory = configuration.buildSessionFactory();

        
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Project p1 = new Project("E-Commerce Platform", 12, 100000.5, "Alice");
            Project p2 = new Project("AI Chatbot", 8, 75000.75, "Bob");

            session.save(p1);
            session.save(p2);

            transaction.commit();
            System.out.println("Projects saved successfully!");
        }

        try (Session session = factory.openSession()) {
            Criteria criteria = session.createCriteria(Project.class);

            criteria.setProjection(Projections.rowCount());
            Long count = (Long) criteria.uniqueResult();
            System.out.println("Total Projects: " + count);

            criteria.setProjection(Projections.max("budget"));
            Double maxBudget = (Double) criteria.uniqueResult();
            System.out.println("Maximum Budget: " + maxBudget);

            criteria.setProjection(Projections.min("budget"));
            Double minBudget = (Double) criteria.uniqueResult();
            System.out.println("Minimum Budget: " + minBudget);

            criteria.setProjection(Projections.sum("budget"));
            Double totalBudget = (Double) criteria.uniqueResult();
            System.out.println("Total Budget: " + totalBudget);

            criteria.setProjection(Projections.avg("budget"));
            Double avgBudget = (Double) criteria.uniqueResult();
            System.out.println("Average Budget: " + avgBudget);
        }

        factory.close();
    }
}
