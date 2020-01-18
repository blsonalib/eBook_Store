package com.bridgelabz.bookstore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EBookStore {
    @Before
    public void setUp() throws Exception {
        RestAssured.baseURI = "http://192.168.0.127:3000";
    }

    @Test()
    public void givenEBookStore_SearchBookByAuthorFullName_ShouldReturnBooks() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("field","Stephen King");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonObject.toJSONString())
                .when().get("/searchBook?pageNo=1");
        ResponseBody responseBody = response.body();
        System.out.println("Body"+responseBody.prettyPrint());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }

    @Test
    public void givenEBookStore_SearchBookByAuthorInFirstLetter_ShouldReturnBooks() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("field","C");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonObject.toJSONString())
                .when().get("/searchBook?pageNo=1");
        ResponseBody responseBody = response.body();
        System.out.println("Body"+responseBody.prettyPrint());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }

    @Test
    public void givenEBookStore_SearchBookByTitle_ShouldReturnBook() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("field","The Girl in Room 105");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonObject.toJSONString())
                .when().get("/searchBook?pageNo=1");
        ResponseBody responseBody = response.body();
        System.out.println("Body"+responseBody.prettyPrint());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }

    @Test
    public void givenEBookStore_SearchBookByTitle_ShouldReturnFalse() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("field"," ");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonObject.toJSONString())
                .when().get("/searchBook?pageNo=1");
        ResponseBody responseBody = response.body();
        System.out.println(responseBody.prettyPrint());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,422);
    }
    @Test
    public void givenMinimumAndMaximumPriceLimit_WhenBookDetailsAvailable_ThenShouldReturnExactNumberOfBook() {
        JSONObject bookPriceLimits = new JSONObject();
        bookPriceLimits.put("minPrice", "100");
        bookPriceLimits.put("maxPrice", "200");
        RestAssured.baseURI = "http://192.168.0.127:3000";
        RestAssured.given()
                .accept(ContentType.JSON)
                .body(bookPriceLimits.toJSONString())
                .queryParam("pageNo", 1)
                .when()
                .get("/sortBooks")
                .then()
                .assertThat().statusCode(200);


    }
}
