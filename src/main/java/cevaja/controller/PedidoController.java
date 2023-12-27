package cevaja.controller;

import cevaja.model.dto.PedidoDTO;
import cevaja.model.dto.TipoCervejaDTO;
import cevaja.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@Controller
@RequestMapping("v1")
public class PedidoController {
    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }
    @PostMapping("/pedidos")
    public ResponseEntity<BigDecimal> adicionarPedido(@RequestBody PedidoDTO pedidoDTO){
        BigDecimal valorTotalPedido = pedidoService.adicionar(pedidoDTO);
        return new ResponseEntity<>(valorTotalPedido, HttpStatus.CREATED);
    }

}

