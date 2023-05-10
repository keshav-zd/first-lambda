package com.exam1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;


public class DatabaseInsertion {

    private  String DB_URL = "jdbc:postgresql://localhost:5432/keshav";
    private  String DB_USER = "postgres";
    private String DB_PASSWORD = "k@123";
    String query;
    List<VisitoreData> visitoreDataList;
    String dim_table;
    String final_table;


    public DatabaseInsertion(String query, List<VisitoreData> visitoreDataList,String dim_table, String final_table) {
        this.query = query;
        this. visitoreDataList=visitoreDataList;
        this.dim_table=dim_table;
        this.final_table=final_table;
    }

    public void insertCirculationData() {
        try {

            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("open Driver");
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("INSERT INTO" +query+"  (branch, address, city, zip, jan, feb, mar, april,may,june,july,august,september,october,november,december,ytd, zip_location)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?)");
            System.out.println("inserting to table");
            Iterator<VisitoreData> iterator= visitoreDataList.iterator();
            while(iterator.hasNext()) {
                VisitoreData visitoreData = iterator.next();
                preparedStatement.setString(1, visitoreData.getBranch());
                preparedStatement.setString(2, visitoreData.getAddress());
                preparedStatement.setString(3, visitoreData.getCity());
                preparedStatement.setInt(4, visitoreData.getZIP());
                preparedStatement.setInt(5, visitoreData.getJan());
                preparedStatement.setInt(6, visitoreData.getFab());
                preparedStatement.setInt(7, visitoreData.getMar());
                preparedStatement.setInt(8, visitoreData.getApril());
                preparedStatement.setInt(9, visitoreData.getMay());
                preparedStatement.setInt(10, visitoreData.getJune());
                preparedStatement.setInt(11, visitoreData.getJuly());
                preparedStatement.setInt(12, visitoreData.getAugust());
                preparedStatement.setInt(13, visitoreData.getSeptember());
                preparedStatement.setInt(14, visitoreData.getOctober());
                preparedStatement.setInt(15, visitoreData.getNovember());
                preparedStatement.setInt(16, visitoreData.getDecember());
                preparedStatement.setInt(17, visitoreData.getYtd());

                if (visitoreData.getLocation() == null) {
                    preparedStatement.setNull(18, java.sql.Types.VARCHAR);
                } else {
                    preparedStatement.setString(18, visitoreData.getLocation().toString());
                }
                String sql1= "INSERT INTO " + dim_table + " (branch, address, city, zip, jan, feb, mar, april, may, june, july, august, september, october, november, december, ytd, zip_location) " +
                        "SELECT s.branch, s.address, s.city, s.zip, s.jan, s.feb, s.mar, s.april, s.may, s.june, s.july," +
                        " s.august, s.september, s.october, s.november, s.december, s.ytd, s.zip_location " +
                        "FROM " + query + " s " +
                        "LEFT JOIN " + dim_table + " d ON s.branch = d.branch " +
                        "WHERE d.branch IS NULL";

                String sql2="UPDATE " + dim_table + " \n" +
                        "SET \n" +
                        "is_active = 0,\n" +
                        "update_at = CURRENT_TIMESTAMP\n" +
                        "FROM " + query + " s\n" +
                        "WHERE " + dim_table + ".branch = s.branch\n" +
                        "AND (" + dim_table + ".address <> s.address OR " +
                        dim_table + ".city <> s.city OR " +
                        dim_table + ".zip <> s.zip OR " +
                        dim_table + ".jan <> s.jan OR " +
                        dim_table + ".feb <> s.feb OR " +
                        dim_table + ".mar <> s.mar OR " +
                        dim_table + ".april <> s.april OR " +
                        dim_table + ".may <> s.may OR " +
                        dim_table + ".june <> s.june OR " +
                        dim_table + ".july <> s.july OR " +
                        dim_table + ".august <> s.august OR " +
                        dim_table + ".september <> s.september OR " +
                        dim_table + ".october <> s.october OR " +
                        dim_table + ".november <> s.november OR " +
                        dim_table + ".december <> s.december OR " +
                        dim_table + ".ytd <> s.ytd OR " +
                        dim_table + ".zip_location <> s.zip_location) AND " +
                        dim_table + ".is_active = 1;\n"
                        ;
                String sql3="INSERT INTO " + dim_table + " (branch, address, city, zip, jan, feb, mar, april, may, june, july, august, september, october, november, december, ytd, zip_location) " +
                        "SELECT s.branch, s.address, s.city, s.zip, s.jan, s.feb, s.mar, s.april, s.may, s.june, s.july," +
                        "s.august, s.september, s.october, s.november, s.december, s.ytd, s.zip_location " +
                        "FROM " + query + " s " +
                        "LEFT JOIN " + dim_table + " d ON s.branch = d.branch " +
                        "WHERE d.branch IS NOT NULL " +
                        "AND (d.address <> s.address OR " +
                        "d.city <> s.city OR " +
                        "d.zip <> s.zip OR " +
                        "d.jan <> s.jan OR " +
                        "d.feb <> s.feb OR " +
                        "d.mar <> s.mar OR " +
                        "d.april <> s.april OR " +
                        "d.may <> s.may OR " +
                        "d.june <> s.june OR " +
                        "d.july <> s.july OR " +
                        "d.august <> s.august OR " +
                        "d.september <> s.september OR " +
                        "d.october <> s.october OR " +
                        "d.november <> s.november OR " +
                        "d.december <> s.december OR " +
                        "d.ytd <> s.ytd OR " +
                        "d.zip_location <> s.zip_location) " +
                        "AND d.update_at = (SELECT MAX(update_at) FROM "+ dim_table+" WHERE d.branch ="+ dim_table+".branch);";

            String sql4="UPDATE "+dim_table+"\n" +
                    "                    SET is_deleted=1,\n" +
                    "                    is_active=0,\n" +
                    "                    update_at = now()\n" +
                    "\t\t\t\t\tWHERE is_active=1 and NOT EXISTS (SELECT 1 FROM "+query+" WHERE "+dim_table+".branch ="+dim_table +".branch  );\n";


            String sql5="insert into "+final_table+" select s.* from " +query+ " s\n" +
                    "left join "+final_table+" f\n" +
                    "on s.branch=f.branch\n" +
                    " where f.branch is null;\t\t\n";

            String sql6="update "+final_table+"\n" +
                    "                    set\n" +
                    "                    update_at=current_timestamp,\n" +
                                         "address = s.address," +
                        "city = s.city," +
                         " zip = s.zip ,"+
                         " jan = s.jan , " +
                         " feb = s.feb , " +
                         " mar = s.mar , " +
                         " april = s.april , " +
                         " may = s.may ," +
                         " june = s.june , " +
                         " july = s.july," +
                         " august = s.august, " +
                         " september = s.september , " +
                         " october = s.october ," +
                         " november = s.november , " +
                         " december=s.december, " +
                         " ytd = s.ytd, " +
                         " zip_location = s.zip_location"+

                    "  FROM " + query + " s\n" +
                "WHERE " + final_table + ".branch = s.branch\n" +
                        "AND (" + final_table + ".address <> s.address OR " +
                    final_table + ".city <> s.city OR " +
                    final_table + ".zip <> s.zip OR " +
                    final_table + ".jan <> s.jan OR " +
                    final_table + ".feb <> s.feb OR " +
                    final_table + ".mar <> s.mar OR " +
                    final_table + ".april <> s.april OR " +
                    final_table + ".may <> s.may OR " +
                    final_table + ".june <> s.june OR " +
                    final_table + ".july <> s.july OR " +
                    final_table + ".august <> s.august OR " +
                    final_table + ".september <> s.september OR " +
                    final_table + ".october <> s.october OR " +
                    final_table + ".november <> s.november OR " +
                    final_table + ".december <> s.december OR " +
                    final_table + ".ytd <> s.ytd OR " +
                    final_table + ".zip_location <> s.zip_location);";
            String sql7 = "UPDATE " + final_table +
                        " SET is_deleted=1, is_active=0, update_at = now()" +
                        " WHERE NOT EXISTS (SELECT 1 FROM " + query + " WHERE " + final_table + ".branch = " + query + ".branch);";

                String sql8 = " truncate "+query;
                PreparedStatement ps1=connection.prepareStatement(sql1);
                PreparedStatement ps2=connection.prepareStatement(sql2);
                PreparedStatement ps3=connection.prepareStatement(sql3);
                PreparedStatement ps4=connection.prepareStatement(sql4);
                PreparedStatement ps5=connection.prepareStatement(sql5);
                PreparedStatement ps6=connection.prepareStatement(sql6);
                PreparedStatement ps7=connection.prepareStatement(sql7);
                PreparedStatement ps8=connection.prepareStatement(sql8);

                preparedStatement.executeUpdate();
                ps1.executeUpdate();
                ps2.executeUpdate();
                ps3.executeUpdate();
                ps4.executeUpdate();
                ps5.executeUpdate();
                ps6.executeUpdate();
               ps7.executeUpdate();
          //      ps8.executeUpdate();

            }

            preparedStatement.close();
            connection.close();

            System.out.println("Successfully inserted data into database.");
        } catch (SQLException e) {
            System.err.println("Failed to insert data into database. Error message: " + e.getMessage());
        }
            }



}
