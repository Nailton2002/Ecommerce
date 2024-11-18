package com.api.restfull.ecommerce.domain.entity;

import com.api.restfull.ecommerce.domain.enums.StatusOrder;
import com.api.restfull.ecommerce.domain.model.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Pedido")
@Table(name = "tb_pedido")
public class Order {

    // Identificador único do pedido
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Cliente associado ao pedido
    @ManyToOne @JoinColumn(name = "cliente_id")
    private Client cliente;
    @ManyToMany @JoinTable(name = "pedido_produto", joinColumns = @JoinColumn(name = "pedido_id"), inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private List<Product> produtos;
    // Conjunto de itens do pedido
    @OneToMany(mappedBy = "pedido")
    private List<OrderItem> itensPedidos;
    // Informações de pagamento
    @OneToMany(mappedBy = "pedido")
    private List<Payment> pagamento;
    // Status do pedido (ex: PENDENTE, APROVADO, CANCELADO, FINALIZADO)
    @Column(name = "cancelada") @Enumerated(EnumType.STRING)
    private StatusOrder statusPedidos;
    // Total calculado do pedido (cálculo dinâmico com base nos itens)
    private Double total;
    // Datas de criação e última atualização do pedido
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime dataCriacao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime dataEntregaPrevista;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private LocalDateTime dataUltimaAtualizacao;
    // Endereço de entrega
    private Address enderecoEntrega;
    // Data prevista para entrega

}
