package de.coding.kata.nim.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/users/{playerName}/games", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerEndpoint {

}
