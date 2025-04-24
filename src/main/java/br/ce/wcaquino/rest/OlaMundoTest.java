package br.ce.wcaquino.rest;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matcher.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OlaMundoTest {

    @Test
    public void testOlaMundo(){
        Response response = request(Method.GET, "http://restapi.wcaquino.me:80/ola");
        Assert.assertEquals("Ola Mundo!", response.getBody().asString());
        Assert.assertEquals(200, response.statusCode());

        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);
    }

    @Test
    public void devoConhecerOutrasFormasRestAssured() {
        Response response = request(Method.GET, "http://restapi.wcaquino.me:80/ola");
        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);

        get("http://restapi.wcaquino.me:80/ola").then().statusCode(200);
        given()
                .when()
                    .get("http://restapi.wcaquino.me:80/ola")
                .then()
//                .assertThat()
                    .statusCode(200);
    }

    @Test
    public void devoConhecerMatcherHamcrest() {
        assertThat("Maria", is("Maria"));
        assertThat(128, CoreMatchers.is(128));
        assertThat(128, CoreMatchers.isA(Integer.class));

        List<Integer> impares = Arrays.asList(1, 3, 5, 7, 9);
        assertThat(impares, hasSize(5));
        assertThat(impares, contains(1, 3, 5, 7, 9));
        assertThat(impares, containsInAnyOrder(1, 5, 3, 7, 7));
        assertThat(impares, hasItem(1));
        assertThat(impares, hasItems(1, 5));

        assertThat("Maria", is(not("João")));
        assertThat("Maria", not("João"));
        assertThat("Maria", anyOf(is("Maria"), is("Joaquina")));
        assertThat("Joaquina", allOf(startsWith("Joa"), endsWith("ina"), containsString("qui")));
    }

    @Test
    public void devoValidarBody() {
        given()
                .when()
                    .get("http://restapi.wcaquino.me:80/ola")
                .then()
                    .statusCode(200)
                    .body(is("Ola Mundo!"))
                    .body(containsString("Mundo"))
                    .body(is(not(nullValue())));
    }
}
