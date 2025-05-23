import io.restassured.http.ContentType;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class VerbosTest {

    @Test
    public void deveSalvarUsuario(){
        given()
                .log().all()
                .contentType("application/json")  // os dados do body devem ser tratados como um objeto json
                .body("{ \"name\": \"Jose\", \"age\": 50 }\n")
                .when()
                    .post("https://restapi.wcaquino.me/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", is("Jose"))
                .body("age", is(50))
        ;
    }

    @Test
    public void naoDeveSalvarUsuarioSemNome(){
            given()
                .log().all()
                .contentType(ContentType.JSON)  // os dados do body devem ser tratados como um objeto json
                .body("{\"age\": 50 }\n")
            .when()
                .post("https://restapi.wcaquino.me/users")
            .then()
                .log().all()
                .statusCode(400)
                .body("id", is(nullValue()))
                .body("error", is("Name é um atributo obrigatório"))
        ;
    }

    @Test
    public void deveSalvarUsuarioViaXML(){
        given()
                .log().all()
                .contentType(ContentType.XML)  // os dados do body devem ser tratados como um objeto json
                .body("<user><name>Jose</name><age>50</age></user>")
                .when()
                .post("https://restapi.wcaquino.me/usersXML")
                .then()
                .log().all()
                .statusCode(201)
                .body("user.@id", is(notNullValue()))
                .body("user.name", is("Jose"))
                .body("user.age", is("50"))
        ;
    }

    //https://restapi.wcaquino.me/users/1
    @Test
    public void deveAlterarUsuario(){
        given()
                .log().all()
                .contentType("application/json")
                .body("{ \"name\": \"Lucas\", \"age\": 25 }\n")
                .when()
                    .put("https://restapi.wcaquino.me/users/1")
                .then()
                    .log().all()
                    .statusCode(200)
                    .body("id", is(1))
                    .body("name", is("Lucas"))
                    .body("age", is(25))
                    .body("salary", is(1234.5678f))
        ;
    }

    @Test
    public void devoCustomizarURL(){
        given()
                .log().all()
                .contentType("application/json")
                .body("{ \"name\": \"Lucas\", \"age\": 25 }\n")
                .when()
                .put("https://restapi.wcaquino.me/{entidade}/{userId}", "users", "1")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(1))
                .body("name", is("Lucas"))
                .body("age", is(25))
                .body("salary", is(1234.5678f))
        ;
    }

    @Test
    public void devoCustomizarURLParte2(){
        given()
                .log().all()
                .contentType("application/json")
                .body("{ \"name\": \"Lucas\", \"age\": 25 }\n")
                .pathParam("entidade", "users")
                .pathParam("userId", 1)
                .when()
                .put("https://restapi.wcaquino.me/{entidade}/{userId}")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(1))
                .body("name", is("Lucas"))
                .body("age", is(25))
                .body("salary", is(1234.5678f))
        ;
    }

    @Test
    public void deveRemoverUsuario(){
        given()
                .log().all()
        .when()
                .delete("https://restapi.wcaquino.me/users/1")
        .then()
                .log().all()
                .statusCode(204)
        ;
    }

    @Test
    public void naoDeveRemoverUsuario(){
        given()
                .log().all()
                .when()
                    .delete("https://restapi.wcaquino.me/users/1000")
                .then()
                    .log().all()
                    .statusCode(400)
                    .body("error", is("Registro inexistente"))
        ;
    }

}

