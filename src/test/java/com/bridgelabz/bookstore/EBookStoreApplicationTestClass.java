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
    public void setUp() throws Exception {
        RestAssured.baseURI = "http://192.168.0.127:3000";
    }

    @Test()
    public void givenEBookStore_SearchBookByAuthorFullName_ShouldReturnBooks() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("field", "Stephen King");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonObject.toJSONString())
                .when().get("/searchBook?pageNo=1");
        ResponseBody responseBody = response.body();
        System.out.println("Body" + responseBody.prettyPrint());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test
    public void givenEBookStore_SearchBookByAuthorInFirstLetter_ShouldReturnBooks() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("field", "C");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonObject.toJSONString())
                .when().get("/searchBook?pageNo=1");
        ResponseBody responseBody = response.body();
        System.out.println("Body" + responseBody.prettyPrint());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test
    public void givenEBookStore_SearchBookByTitle_ShouldReturnBook() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("field", "The Girl in Room 105");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonObject.toJSONString())
                .when().get("/searchBook?pageNo=1");
        ResponseBody responseBody = response.body();
        System.out.println("Body" + responseBody.prettyPrint());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test
    public void givenEBookStore_SearchBookByTitle_ShouldReturnFalse() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("field", " ");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonObject.toJSONString())
                .when().get("/searchBook?pageNo=1");
        ResponseBody responseBody = response.body();
        System.out.println(responseBody.prettyPrint());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 422);
    }

    @Test
    public void givenMinimumAndMaximumPriceLimit_WhenBookDetailsAvailable_ThenShouldReturnExactNumberOfBook() throws ParseException {
        JSONObject bookPriceLimits = new JSONObject();
        bookPriceLimits.put("minPrice", 100);
        bookPriceLimits.put("maxPrice", 200);
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(bookPriceLimits.toJSONString())
                .queryParam("pageNo", 1)
                .when()
                .get("/sortBooks?pageNo=1");
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        boolean status = (boolean) object.get("success");
        String message = (String) object.get("message");
        Assert.assertTrue(status);
        Assert.assertEquals("All books are sorted", message);
    }

    @Test
    public void givenMinimumPriceAndMaximumPriceInNegativeFormat_WhenNotAcceptible_ShouldGiveProperValidationMessage() throws ParseException {
        JSONObject bookPriceLimits = new JSONObject();
        bookPriceLimits.put("minPrice", -100);
        bookPriceLimits.put("maxPrice", -200);
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(bookPriceLimits.toJSONString())
                .queryParam("pageNo", 1)
                .when()
                .get("/sortBooks?pageNo=1");
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        boolean status = (boolean) object.get("success");
        String message = (String) object.get("message");
        Assert.assertTrue(status);
        Assert.assertEquals("All books are sorted", message);
        String bookData = (String) object.get("data");
        JSONObject bookDataJsonValues = (JSONObject) new JSONParser().parse(bookData.toString());
        String messageBetweenData = (String) bookDataJsonValues.get("message");
        Assert.assertEquals("Book Not Found", messageBetweenData);
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
