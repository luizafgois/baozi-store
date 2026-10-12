
package baozi_store.controller;

import baozi_store.model.Pedido;
import baozi_store.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pedido> criar(@RequestBody Pedido pedido) {
        Pedido novoPedido = pedidoService.salvar(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(
            @PathVariable Long id,
            @RequestBody Pedido pedido) {

        return pedidoService.buscarPorId(id)
                .map(pedidoExistente -> {
                    pedido.setId(id);
                    return ResponseEntity.ok(pedidoService.salvar(pedido));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(pedido -> {
                    pedidoService.excluir(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
