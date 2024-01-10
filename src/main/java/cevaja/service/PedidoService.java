package cevaja.service;

import cevaja.integration.service.PocTemperaturaService;
import cevaja.model.Pedido;
import cevaja.model.TipoCerveja;
import cevaja.model.Usuario;
import cevaja.model.dto.PedidoDTO;
import cevaja.repository.PedidoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class PedidoService {
    private UsuarioService usuarioService;
    private PocTemperaturaService pocTemperaturaService;
    private PedidoRepository pedidoRepository;

    private static final int QUANTIDADE_PARA_DESCONTO = 10;

    public PedidoService(UsuarioService usuarioService, PocTemperaturaService pocTemperaturaService, PedidoRepository pedidoRepository) {
        this.usuarioService = usuarioService;
        this.pocTemperaturaService = pocTemperaturaService;
        this.pedidoRepository = pedidoRepository;
    }

    public BigDecimal adicionar(PedidoDTO pedidoDTO) {
        Usuario usuarioExiste = usuarioService.buscarPeloLogin(pedidoDTO.getUsuario().getUsernameLogin());
        if (usuarioExiste == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não cadastrado: " + pedidoDTO.getUsuario().getUsernameLogin());
        }
        LocalDate dataNascimento = usuarioExiste.getDataNascimento();
        LocalDate dataAtual = LocalDate.now();
        int idade = Period.between(dataNascimento, dataAtual).getYears();

        if(idade < 18){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário menor de idade: " + pedidoDTO.getUsuario().getUsernameLogin());
        }

        List<TipoCerveja> tiposCerveja = pedidoDTO.getTipoCerveja();
        int quantidadeTotalCervejas = calcularTotalCervejas(pedidoDTO);

        if (quantidadeTotalCervejas >= QUANTIDADE_PARA_DESCONTO) {
            BigDecimal desconto = new BigDecimal("0.10");
            for(TipoCerveja tipoCerveja: tiposCerveja){
                BigDecimal valorAtual = tipoCerveja.getValor();
                BigDecimal valorDesconto = valorAtual.multiply(desconto);
                BigDecimal novoValor = valorAtual.subtract(valorDesconto);
                tipoCerveja.setValor(novoValor);
            }
        }

        double temperaturaAtual = pocTemperaturaService.buscarTemperatura().getCurrent().getTemp_c();
        if (temperaturaAtual <= 22){
            BigDecimal descontoTemperatura = new BigDecimal("0.15");
            for(TipoCerveja tipoCerveja : tiposCerveja){
                BigDecimal valorAtual = tipoCerveja.getValor();
                BigDecimal valorDescontoTemperatura = valorAtual.multiply(descontoTemperatura);
                BigDecimal novoValorTemperatura = valorAtual.subtract(valorDescontoTemperatura);
                tipoCerveja.setValor(novoValorTemperatura);
            }
        }
        BigDecimal valorTotalPedido = BigDecimal.ZERO;
        for (TipoCerveja tipoCerveja: tiposCerveja){
            BigDecimal valorTotalItem = tipoCerveja.getValor().multiply(BigDecimal.valueOf(tipoCerveja.getQuantidade()));
            valorTotalPedido = valorTotalPedido.add(valorTotalItem);
        }
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuarioExiste);
        pedido.setTiposCerveja(tiposCerveja);

        System.out.println("Adicionando pedido no Banco de Dados...");
        //pedidoRepository.save(pedido);

        return valorTotalPedido;
    }

    private int calcularTotalCervejas(PedidoDTO pedidoDTO) {
        List<TipoCerveja> tiposCerveja = pedidoDTO.getTipoCerveja();
        int quantidadeTotalCervejas = 0;
        for (TipoCerveja tipoCerveja: tiposCerveja) {
            quantidadeTotalCervejas = quantidadeTotalCervejas + tipoCerveja.getQuantidade();
        }

        return quantidadeTotalCervejas;
    }

}
