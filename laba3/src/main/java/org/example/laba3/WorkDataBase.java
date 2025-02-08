package org.example.laba3;

import com.google.gson.Gson;
import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Named("workDataBase")
@ApplicationScoped
@ManagedBean
public class WorkDataBase implements Serializable {
    public List<Result> getResults() {
        try (Connection connection = DbUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * from results")) {
            List<Result> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(new Result(resultSet.getDouble("x"), resultSet.getDouble("y"),
                        resultSet.getDouble("r"), resultSet.getBoolean("isHit")));
            }
            System.out.println(results +"\n");
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getResultsAsJson() {
        Gson gson = new Gson();
        return gson.toJson(getResults());
    }

    public void addResult(DotBean dotBean) {
        if (dotBean.getX() == null || dotBean.getY() == null || dotBean.getR() == null) {
            return;
        }
        System.out.println("Saving result with R: " + dotBean.getR());
        if(DotBean.checkX(dotBean.getX()) && DotBean.checkY(dotBean.getY())) {
            Result result = new Result(dotBean.getX(), dotBean.getY(), dotBean.getR(), Result.dotIsHit(dotBean.getX(), dotBean.getY(), dotBean.getR()));
            try (Connection connection = DbUtil.getConnection();
                 PreparedStatement ps = connection.prepareStatement("INSERT INTO results ( X, Y, R, isHit) VALUES (?, ?, ?, ?)")) {
                ps.setDouble(1, result.getX());
                ps.setDouble(2, result.getY());
                ps.setDouble(3, result.getR());
                ps.setBoolean(4, result.isHit());
                ps.execute();
                PrimeFaces.current().ajax().update("resultsTable");
            } catch (Exception e) {
                System.out.println("Ошибка");
            }
        }
    }
}
