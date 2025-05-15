package com.calculafrete.projeto.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/calcular-frete")
public abstract class BaseCalculaFreteEndpoint {
}
