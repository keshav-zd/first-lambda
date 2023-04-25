package com.keshav;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class S3ToRedshiftHandler implements RequestHandler<S3Event, String> {

    private final AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
    private final String redshiftUrl = "jdbc:redshift://myworkgroup-keshav.442597812037.us-east-1.redshift-serverless.amazonaws.com:5439/dev";
    private final String redshiftUser = "admin";
    private final String redshiftPassword = "Zd123456";

    public String handleRequest(S3Event event, Context context) {
        try {
            // Retrieve the S3 object key from the Lambda event
            S3EventNotification.S3EventNotificationRecord record = event.getRecords().get(0);
            String s3Bucket = record.getS3().getBucket().getName();
            String s3Key = record.getS3().getObject().getKey();

            // Get the S3 object
            S3Object s3Object = s3.getObject(new GetObjectRequest(s3Bucket, s3Key));

            // Load the CSV data into Redshift
            Connection conn = DriverManager.getConnection(redshiftUrl, redshiftUser, redshiftPassword);
            PreparedStatement statement = conn.prepareStatement("COPY my_table FROM s3://mybucket-keshav/sampletest.csv CREDENTIALS arn:aws:iam::442597812037:role/service-role/keshav-lambda-role-xcg54z2u CSV");
            statement.setString(1, "s3://" + s3Bucket + "/" + s3Key);
            statement.setString(2, "aws_iam_role=arn:aws:iam::442597812037:role/service-role/keshav-lambda-role-xcg54z2u");
            statement.executeUpdate();
            statement.close();
            conn.close();

            return "Successfully loaded CSV data from S3 to Redshift";
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load CSV data from S3 to Redshift", e);
        }
    }

}
