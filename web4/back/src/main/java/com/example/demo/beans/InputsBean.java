package com.example.demo.beans;

import com.example.demo.EJBbeans.ResultEJB;
import com.example.demo.EJBbeans.UserEJB;
import com.example.demo.entities.ResultEntity;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;

@Path("/result")
public class InputsBean implements Serializable {

    private static final long serialVersionUID = 1L; // Для сериализации

    @EJB
    private ResultEJB resultEJB;

    @EJB
    private UserEJB userEJB;

    private String x;
    private String y;
    private String r;

    /**
     * Валидация входных данных.
     */
    private boolean validate() {
        return isValidX(x) && isValidY(y) && isValidR(r);
    }

    /**
     * Валидация X.
     */
    private boolean isValidX(String x) {
        return x.matches("^(?:-[1-4]|[0-4])$")
                && -4 <= Double.parseDouble(x) && Double.parseDouble(x) <= 4;
    }

    /**
     * Валидация Y.
     */
    private boolean isValidY(String y) {
        return y.matches("^(?:-?[0-4][.,]\\d+|(?:-[1-5]|[0-3])([.,]0+)?)$")
                && Double.parseDouble(y) >= -5 && Double.parseDouble(y) <= 3;
    }

    /**
     * Валидация R.
     */
    private boolean isValidR(String r) {
        return r.matches("^(?:-[1-4]|[0-4])$")
                && -4 <= Double.parseDouble(r) && Double.parseDouble(r) <= 4;
    }

    /**
     * Проверка попадания точки в область.
     */
    private boolean checkAreaHit(double x, double y, double r) {
        if (r < 0) {
            r = -r;
            x = -x;
            y = -y;
        }
        if (x >= 0) {
            return y <= 0 && x <= r / 2 && Math.abs(y) < r;
        } else {
            return y >= 0
                    ? Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r / 2, 2)
                    : 2 * y >= -x - r;
        }
    }

    /**
     * Добавление результата в базу данных через форму.
     */
    @POST
    @Path("/add-to-db")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addToDB(@CookieParam("token") String token, ResultEntity result) {
        return processResult(token, result, true);
    }

    /**
     * Добавление результата в базу данных через клик на графике.
     */
    @POST
    @Path("/add-to-db-click")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addToDBClick(@CookieParam("token") String token, ResultEntity result) {
        return processResult(token, result, false);
    }

    /**
     * Общий метод для обработки добавления результатов.
     */
    private Response processResult(String token, ResultEntity result, boolean fullValidation) {
        if (token == null || token.isEmpty()) {
            return Response.status(Response.Status.ACCEPTED).entity("Вы не авторизованы").build();
        }

        String login = userEJB.getAssociatedLogin(token);
        if (login == null) {
            return Response.status(Response.Status.ACCEPTED).entity("Не удалось определить имя пользователя").build();
        }

        setX(result.getX());
        setY(result.getY());
        setR(result.getR());

        if ((fullValidation && validate()) || (!fullValidation && isValidR(r))) {
            resultEJB.addToDB(
                    x,
                    y,
                    r,
                    checkAreaHit(Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(r)),
                    login
            );
            return Response.status(Response.Status.CREATED).build();
        }

        return Response.status(Response.Status.ACCEPTED).entity("Серверу были переданы неверные данные").build();
    }

    /**
     * Получение всех результатов.
     */
    @POST
    @Path("/view-all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEntities() {
        return Response.status(Response.Status.ACCEPTED).entity(resultEJB.getEntities()).build();
    }

    /**
     * Очистка результатов пользователя.
     */
    @POST
    @Path("/clear")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response clear(@CookieParam("token") String token) {
        if (token == null || token.isEmpty()) {
            return Response.status(Response.Status.ACCEPTED).entity("Вы не авторизованы").build();
        }

        String login = userEJB.getAssociatedLogin(token);
        if (login == null) {
            return Response.status(Response.Status.ACCEPTED).entity("Не удалось определить имя пользователя").build();
        }

        resultEJB.clear(login);
        return Response.ok().build();
    }

    public void setX(String x) {
        this.x = x.replace(',', '.');
    }

    public void setY(String y) {
        this.y = y.replace(',', '.');
    }

    public void setR(String r) {
        this.r = r.replace(',', '.');
    }
}