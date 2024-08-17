package com.turistando.sistematuristando.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turistando.sistematuristando.model.GastoModel;
import com.turistando.sistematuristando.model.VeiculoModel;
import com.turistando.sistematuristando.repository.IGastoRepository;
import com.turistando.sistematuristando.repository.IVeiculoRepository;

@RestController
@RequestMapping("/gastos")
public class GastoController {

    @Autowired
    private IGastoRepository repoGasto;

    @Autowired
    private IVeiculoRepository repoVeiculo;

   @GetMapping()
    public List<GastoModel> listar() {
        return repoGasto.findAll();
    }

    @PostMapping()
    public ResponseEntity<GastoModel> registrar(@RequestBody GastoModel gasto){
        
        Optional<VeiculoModel> veiculoOpt = repoVeiculo.findById(gasto.getVeiculo().getPlaca());

        if (veiculoOpt.isPresent()) {
        gasto.setVeiculo(veiculoOpt.get());
        GastoModel obj = repoGasto.save(gasto);
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
        } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }
    
    @PutMapping()
    public ResponseEntity<GastoModel> atualizar(@RequestBody GastoModel gasto){
        GastoModel obj = repoGasto.save(gasto);
        return  new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") int id) throws Exception{
        Optional<GastoModel> obj = repoGasto.findById(id);
        if (obj.isPresent()) {
            repoGasto.deleteById(id);
            return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
   }

    @GetMapping("/{id}")
    public ResponseEntity<GastoModel> listarPorId(@PathVariable("id") int id) throws Exception{
        Optional<GastoModel> obj = repoGasto.findById(id);
        if (obj.isPresent()) {
            return new ResponseEntity<>(obj.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
