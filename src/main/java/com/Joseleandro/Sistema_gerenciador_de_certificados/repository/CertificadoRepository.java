package com.Joseleandro.Sistema_gerenciador_de_certificados.repository;

import com.Joseleandro.Sistema_gerenciador_de_certificados.model.Certificado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificadoRepository extends JpaRepository<Certificado, Long> {
    Optional<Certificado> findByNumeroCertificadoAndCnpjClienteAndEmailCliente(
            String numeroCertificado,
            String cnpjCliente,
            String emailCliente
    );
}
