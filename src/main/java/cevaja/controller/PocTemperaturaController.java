package cevaja.controller;

import cevaja.integration.service.PocTemperaturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/temperaturas")
public class PocTemperaturaController {
    private PocTemperaturaService pocTemperaturaService;

    public PocTemperaturaController(PocTemperaturaService pocTemperaturaService) {
        this.pocTemperaturaService = pocTemperaturaService;
    }

    @GetMapping
    public ResponseEntity buscarTemperatura(){
        return ResponseEntity.ok(this.pocTemperaturaService.buscarTemperatura());
    }
}
