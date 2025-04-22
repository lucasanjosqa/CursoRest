package br.ce.wcaquino.rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;

//  https://restapi.wcaquino.me/ola
//  protocolo: https | endereço: restapi.wcaquino.me | recurso: ola
public class OlaMundo {
    public static void main(String[] args) {
        Response response = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/ola");
        System.out.println(response.getBody().asString());
    }
}