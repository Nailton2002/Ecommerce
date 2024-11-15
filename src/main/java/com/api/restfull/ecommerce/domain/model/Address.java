package com.api.restfull.ecommerce.domain.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String complemento;
    private String logradouro;
    private String bairro;
    private String numero;
    private String cidade;
    private String cep;
    private String uf;

//    public void updateInformation(DadosEndereco dados) {
//        if (dados.logradouro() != null) {
//            this.logradouro = dados.logradouro();
//        }
//        if (dados.bairro() != null) {
//            this.bairro = dados.bairro();
//        }
//        if (dados.cep() != null) {
//            this.cep = dados.cep();
//        }
//        if (dados.numero() != null) {
//            this.numero = dados.numero();
//        }
//        if (dados.complemento() != null) {
//            this.complemento = dados.complemento();
//        }
//        if (dados.cidade() != null) {
//            this.cidade = dados.cidade();
//        }
//        if (dados.uf() != null) {
//            this.uf = dados.uf();
//        }
//    }
}
