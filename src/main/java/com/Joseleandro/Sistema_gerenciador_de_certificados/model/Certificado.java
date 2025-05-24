package com.Joseleandro.Sistema_gerenciador_de_certificados.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity // Diz que essa classe é uma tabela no banco de dados
@Data // Lombok gera automaticamente getters, setters, toString, etc.
public class Certificado {

    @Id // Define a chave primária (primary key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera ID automático
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroCertificado;

    @Column(nullable = false)
    private String cnpjCliente;

    @Column(nullable = false)
    private String emailCliente;

    @Column(nullable = false, unique = true)
    private String nomeArquivoCertificado;

    @Column(nullable = false, unique = true)
    private String nomeArquivosPadroes;
}
