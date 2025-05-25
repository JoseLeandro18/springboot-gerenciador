package com.Joseleandro.Sistema_gerenciador_de_certificados.controller;

import com.Joseleandro.Sistema_gerenciador_de_certificados.model.Certificado;
import com.Joseleandro.Sistema_gerenciador_de_certificados.repository.CertificadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.Optional;

@Controller
public class VerificacaoController {

    @Autowired
    private CertificadoRepository certificadoRepository;

    @GetMapping("/verificar")
    public String mostrarFormulario() {
        return "verificarCertificado";
    }

    @PostMapping("/verificar")
    public String verificar(
            @RequestParam("cnpj_cliente") String cnpjCliente,
            @RequestParam("email_cliente") String emailCliente,
            @RequestParam("numero_certificado") String numeroCertificado,
            Model model
    ) {
        emailCliente = emailCliente.trim().toLowerCase();
        String cnpjNumeros = cnpjCliente.replaceAll("[^0-9]", "");

        if (cnpjNumeros.length() == 14) {
            cnpjCliente = cnpjNumeros.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})",
                    "$1.$2.$3/$4-$5");
        }

        numeroCertificado = numeroCertificado.trim();

        Optional<Certificado> optional = certificadoRepository.findByNumeroCertificadoAndCnpjClienteAndEmailCliente(
                numeroCertificado, cnpjCliente, emailCliente
        );

        if (optional.isPresent()) {
            String nomeArquivo = optional.get().getNomeArquivoCertificado();
            model.addAttribute("arquivoEncontrado", true);
            model.addAttribute("nomeArquivo", nomeArquivo);
        } else {
            model.addAttribute("erro", "Certificado não encontrado. Verifique os dados.");
        }

        return "verificarCertificado";
    }

    @GetMapping("/download/{nome}")
    public ResponseEntity<FileSystemResource> download(@PathVariable("nome") String nomeArquivo) {
        File arquivo = new File(System.getProperty("user.dir") + File.separator + "uploads" + File.separator + nomeArquivo);

        if (!arquivo.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nomeArquivo)
                .body(new FileSystemResource(arquivo));
    }
}
