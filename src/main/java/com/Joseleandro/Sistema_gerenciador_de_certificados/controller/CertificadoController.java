package com.Joseleandro.Sistema_gerenciador_de_certificados.controller;

import com.Joseleandro.Sistema_gerenciador_de_certificados.model.Certificado;
import com.Joseleandro.Sistema_gerenciador_de_certificados.repository.CertificadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/certificados")
public class CertificadoController {

    @Autowired
    private CertificadoRepository certificadoRepository;

    @PostMapping
    public ResponseEntity<String> enviarCertificado(
            @RequestParam("cnpj_cliente") String cnpjCliente,
            @RequestParam("email_cliente") String emailCliente,
            @RequestParam("numero_certificado") String numeroCertificado,
            @RequestParam("nome_arquivo_certificado") MultipartFile arquivo) {

        String pastaDestino = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;
        File pasta = new File(pastaDestino);
        if (!pasta.exists()) {
            pasta.mkdirs(); // cria a pasta se não existir
        }

        String nomeArquivo = numeroCertificado + ".pdf";
        File destino = new File(pastaDestino + nomeArquivo);
        try {
            // Salva o PDF no disco
            arquivo.transferTo(destino);

            Certificado cert = new Certificado();
            cert.setCnpjCliente(cnpjCliente);
            cert.setEmailCliente(emailCliente);
            cert.setNumeroCertificado(numeroCertificado);
            cert.setNomeArquivoCertificado(nomeArquivo);
            cert.setNomeArquivosPadroes("padrao-" + numeroCertificado);

            certificadoRepository.save(cert);
            return ResponseEntity.ok("Certificado enviado com sucesso!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao salvar arquivo: " + e.getMessage());
        }

    }
}
