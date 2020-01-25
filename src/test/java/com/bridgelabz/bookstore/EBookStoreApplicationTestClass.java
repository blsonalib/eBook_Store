package com.bridgelabz.bookstore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EBookStoreApplicationTestClass {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://13.234.136.55:3000";
    }

    @Test()
    public void givenEBookStore_WhenSearchBookByAuthorFullName_ThenShouldReturnBooksFound() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .queryParam("field", "Stephen King")
                .when().get("/searchBook");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        JSONObject object = (JSONObject) new JSONParser().parse(response.asString());
        boolean successStatus = (boolean) object.get("success");
        String responseMessage = (String) object.get("message");
        Assert.assertTrue(successStatus);
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals("Books Found", responseMessage);
    }

    @Test
    public void givenEBookStore_WhenSearchBookByAuthorInFirstLetter_ThenShouldReturnBooksFound() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .queryParam("field", "c")
                .when().get("/searchBook");
        ResponseBody responseBody = response.body();
        System.out.println("Body" + responseBody.prettyPrint());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        JSONObject object = (JSONObject) new JSONParser().parse(response.asString());
        boolean successStatus = (boolean) object.get("success");
        String responseMessage = (String) object.get("message");
        Assert.assertTrue(successStatus);
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals("Books Found", responseMessage);
    }

    @Test
    public void givenEBookStore_WhenSearchBookByTitle_ThenShouldReturnBooksFound() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .queryParam("field", "The Girl in Room 105")
                .when()
                .get("/searchBook");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        JSONObject object = (JSONObject) new JSONParser().parse(response.asString());
        boolean successStatus = (boolean) object.get("success");
        String responseMessage = (String) object.get("message");
        Assert.assertTrue(successStatus);
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals("Books Found", responseMessage);
    }

    @Test
    public void givenEBookStore_WhenSearchBookByTitle_ThenShouldReturnValidationError() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .queryParam("field", "")
                .when().get("/searchBook");
        ResponseBody responseBody = response.body();
        JSONObject object = (JSONObject) new JSONParser().parse(response.asString());
        boolean successStatus = (boolean) object.get("success");
        String responseMessage = (String) object.get("message");
        int statusCode = response.getStatusCode();
        Assert.assertFalse(successStatus);
        Assert.assertEquals(statusCode, 422);
        Assert.assertEquals("Validation Error", responseMessage);
    }

    @Test
    public void givenMinimumAndMaximumPriceLimit_WhenBookDetailsAvailable_ThenShouldReturnExactListOfBooks() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .queryParam("minPrice", 100)
                .queryParam("maxPrice", 200)
                .when()
                .get("/sortBooks");
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        boolean status = (boolean) object.get("success");
        String message = (String) object.get("message");
        Assert.assertTrue(status);
        Assert.assertEquals("All books are sorted", message);
    }

    @Test
    public void givenBooks_WhenBookShowDetail_ThenShouldReturnCorrectBookDetail() {
        JSONObject showBookDetail = new JSONObject();
        RestAssured.given()
                .accept(ContentType.JSON)
                .body(showBookDetail.toJSONString())
                .queryParam("pageNo", 1)
                .when()
                .get("/books")
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    public void givenBooks_WhenBookShowDetail_ThenShouldReturnPageNumber2() {
        JSONObject showBookDetail = new JSONObject();
        RestAssured.given()
                .accept(ContentType.JSON)
                .body(showBookDetail.toJSONString())
                .queryParam("pageNo", 2)
                .when()
                .get("/books")
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    public void givenBooksList_WhenBookPaginationMode_ThenShouldReturnCorrect() {
        JSONObject showBookDetail = new JSONObject();
        RestAssured.given()
                .accept(ContentType.JSON)
                .body(showBookDetail.toJSONString())
                .queryParam("pageNo", 1)
                .when()
                .get("/books")
                .then()
                .assertThat().statusCode(200);
    }
}
