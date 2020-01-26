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
        RestAssured.baseURI = "http://192.168.0.148:3000";
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
        System.out.println("Body" + responseBody.asString());
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
        JSONObject object = (JSONObject) new JSONParser().parse(body.asString());
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


    @Test()
    public void givenUserDetails_WhenValidUserDetails_ThenShouldAddUserInDatabase() {
        JSONObject userDetails = new JSONObject();
        userDetails.put("username", "Laxman");
        userDetails.put("mobile", "7030493048");
        userDetails.put("pincode", "431513");
        userDetails.put("locality", "Rampur");
        userDetails.put("address", "Raawan");
        userDetails.put("emailId", "raawan@gmail.com");
        userDetails.put("city", "Nanded");
        userDetails.put("landmark", "LLLL");
        userDetails.put("type", "home");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(userDetails.toJSONString())
                .when()
                .post("/addUser");
        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("application/json; charset=utf-8");
        response.then().assertThat().contentType(ContentType.JSON);
    }

    @Test
    public void givenBookIdAndUserId_WhenAvailable_ThenShouldAbleToOrderBook() {
        JSONObject orderBookDetails = new JSONObject();
        orderBookDetails.put("userId", "5e2c0eb87a7bdf43b908bf81");
        orderBookDetails.put("bookId", "5e1ff27d9d6d3b1318e58143");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(orderBookDetails.toJSONString())
                .when()
                .post("/orderBook");
        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType(ContentType.JSON);
    }

    @Test()
    public void givenUserDetails_WhenInvalidUserAddress_ThenShouldNotAddUserInDatabase() {
        JSONObject userDetails = new JSONObject();
        userDetails.put("username", "Laxman");
        userDetails.put("mobile", "7030493048");
        userDetails.put("pincode", "431534");
        userDetails.put("locality", "Ra");
        userDetails.put("address", "Raawan");
        userDetails.put("emailId", "raawan@gmail.com");
        userDetails.put("city", "Nanded");
        userDetails.put("landmark", "LLLL");
        userDetails.put("type", "home");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(userDetails.toJSONString())
                .when()
                .post("/addUser");
        response.then().assertThat().statusCode(422);
        response.then().assertThat().contentType("application/json; charset=utf-8");
        response.then().assertThat().contentType(ContentType.JSON);
    }

    @Test()
    public void givenUserDetails_WhenInvalidUserEmail_ThenShouldNotAddUserInDatabase() {
        JSONObject userDetails = new JSONObject();
        userDetails.put("username", "Laxman");
        userDetails.put("mobile", "07030493048");
        userDetails.put("pincode", "4315");
        userDetails.put("locality", "Rampur");
        userDetails.put("address", "Raawan");
        userDetails.put("emailId", "sona.gmail.com");
        userDetails.put("city", "Nanded");
        userDetails.put("landmark", "LLLL");
        userDetails.put("type", "home");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(userDetails.toJSONString())
                .when()
                .post("/addUser");
        response.then().assertThat().statusCode(422);
        response.then().assertThat().contentType("application/json; charset=utf-8");
        response.then().assertThat().contentType(ContentType.JSON);
    }
}
