package cevaja.service;

import cevaja.model.TipoCerveja;

import cevaja.model.Usuario;
import cevaja.model.dto.TipoCervejaDTO;

import cevaja.repository.CervejaRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CervejaService {

    private CervejaRepository cervejaRepository;

    public CervejaService(CervejaRepository cervejaRepository) {
        this.cervejaRepository = cervejaRepository;
    }

    public List<TipoCervejaDTO> listaTiposCervejas() {
        return cervejaRepository.findAll().stream()
                .map(cerveja -> new TipoCervejaDTO(cerveja.getNome(), cerveja.getValor()))
                .collect(Collectors.toList());
    }

    public TipoCerveja buscarPeloNome(String nome) {
        return cervejaRepository.findByNomeIgnoreCase(nome);
    }
    public void  adicionar(TipoCervejaDTO tipoCervejaDTO) {
        String tipoCervejaAdicionado = tipoCervejaDTO.getNome();
        TipoCerveja tipoCervejaExistente = buscarPeloNome(tipoCervejaAdicionado);

        if (tipoCervejaExistente != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possivel adicionar um tipo de cerveja repetido. O tipo " +
                    tipoCervejaAdicionado + " ja existe no banco de dados.");
        } else{
            TipoCerveja novoTipoCerveja = new TipoCerveja();
            novoTipoCerveja.setNome(tipoCervejaDTO.getNome());
            novoTipoCerveja.setValor(tipoCervejaDTO.getValor());

            cervejaRepository.save(novoTipoCerveja);
        }
    }

    public TipoCerveja removerPeloNome(String nome) {
        TipoCerveja tipoCervejaRemoverPleoNome = cervejaRepository.findByNomeIgnoreCase(nome);
        if (tipoCervejaRemoverPleoNome == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possivel remover um tipo de cerveja inexistente. O tipo " + nome + " não existe no banco de dados.");
        } else{
            cervejaRepository.delete(tipoCervejaRemoverPleoNome);
        }
        return tipoCervejaRemoverPleoNome;
    }

    public TipoCerveja alterarValorTipoCerveja(String nome, BigDecimal valor) {
        TipoCerveja cervejaParaAlterar = cervejaRepository.findByNomeIgnoreCase(nome);

        if (cervejaParaAlterar == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de Cerveja não encontrado: " + nome);
        }else{
            cervejaParaAlterar.setValor(valor);

            cervejaRepository.save(cervejaParaAlterar);
            return cervejaParaAlterar;
        }

    }
}

