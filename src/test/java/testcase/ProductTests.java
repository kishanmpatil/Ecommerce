package testcase;
import com.aventstack.extentreports.gherkin.model.Given;
import com.github.fge.jackson.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import pojo.Product;
import routes.Routes;
import utils.ConfigReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloads.Payload;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static java.lang.Math.log;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.List;



@Slf4j
public class ProductTests extends BaseClass {

    @Test
    public void testGetAllProducts() {
        given()

                .when()
                .get(Routes.GET_ALL_PRODUCTS)
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .log().body();

    }
//2 test to retrive a single product by id

     @Test
    public void testGetSingleProductById() {
        int productId = configReader.getIntProperty("productId");
        given()
                .pathParam("id", productId)
                .when()
                .get(Routes.GET_PRODUCTS_BY_ID)
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .log().body();
    }


    //3.test to retrive a limited products
     @Test
    public void testGetLimitedProducts() {
        int productId = configReader.getIntProperty("productId");
        given()
                .pathParam("limit", 3)
                .when()
                .get(Routes.GET_PRODUCTS_WITH_LIMIT)
                .then()
                .statusCode(200)
                .body("size()", equalTo(3))
                .log().body();
    }

    //4. test to retrive product sorted in decending order
     @Test
    public void testGetSortedProducts() {


        Response response = given()
                .pathParam("order", "desc")
                .when()
                .get(Routes.GET_PRODUCTS_SORTED)
                .then()
                .statusCode(200)
                .extract().response();
        List<Integer> productIds = response.jsonPath().getList("id", Integer.class);
        assertThat(isSortedDescending(productIds), is(true));

    }

    // 5 Test to retrive the product sorted in Asscending order
    @Test
    public void testGetSortedProductsAsc() {


        Response response = given()
                .pathParam("order", "asc")
                .when()
                .get(Routes.GET_PRODUCTS_SORTED)
                .then()
                .statusCode(200)
                .extract().response();
        List<Integer> productIds = response.jsonPath().getList("id", Integer.class);
        assertThat(isSortedAscending(productIds), is(true));

    }


    //6 Test to get all product categories
    @Test

    public void testGetAllCategories() {
        given()

                .when()

                .get(Routes.GET_ALL_CATEGORIES)
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));

    }

//7. GET_PRODUCTS_BY_CATEGORY ="/products/category/{category}";

    @Test

    public void testGetProductsByCategory() {
        given()
                .pathParam("category", "electronics")
                .when()
                .get(Routes.GET_PRODUCTS_BY_CATEGORY)

                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("category", notNullValue())
                .body("category", everyItem(equalTo("electronics")))
                .log().body();
    }

    //8. Test to Add new   product
     @Test
    public void testAddNewProduct() {
        Product newProduct = Payload.productPayload();

        int productId = given()
                .contentType(ContentType.JSON)
                .body(newProduct)

                .when()
                .post(Routes.CREATE_PRODUCT)

                .then()
                .log().body()
                .statusCode(200)
                .body("id", notNullValue())
                .body("title", equalTo(newProduct.getTitle()))
                .extract().jsonPath().getInt("id");
        System.out.println(productId);
    }

// 9 test to update an existing product

    @Test

    public void testUpdateProduct() {
        int productId = configReader.getIntProperty("productId");
        Product updatedPayload = Payload.productPayload();

        given()

                .contentType(ContentType.JSON)
                .body(updatedPayload)
                .pathParam("id", productId)
                .when()
                .put(Routes.UPDATE_PRODUCT)
                .then()
                .log().body()
                .statusCode(200)
                .body("title", equalTo(updatedPayload.getTitle()));


    }


// 10 test to delet product

    @Test
public void testDeletProduct(){

    int productId = configReader.getIntProperty("productId");

    given()
            .pathParam("id",productId)
            .when()
            .delete(Routes.DELETE_PRODUCT)

            .then()
            .statusCode(200);





    }



}








































