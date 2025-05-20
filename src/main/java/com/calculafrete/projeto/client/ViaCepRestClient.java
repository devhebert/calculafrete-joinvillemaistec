package com.calculafrete.projeto.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ViaCepRestClient {

    private static final String VIACEP_URL = "https://viacep.com.br/ws/{cep}/json/";
    private final RestClient restClient;

    public ViaCepRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public String buscarEnderecoPorCep(String cep) {
        return restClient.get()
                .uri(VIACEP_URL, cep)
                .retrieve()
                .body(String.class);
    }
}
