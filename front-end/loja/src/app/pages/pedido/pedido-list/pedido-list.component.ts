import { Component, OnInit, Injector } from '@angular/core';
import { BaseResourceListComponent } from 'src/app/shared/components/base-resource-list.component';
import { Pedido } from '../../shared/models/pedido.model';
import { PedidoService } from '../../shared/services/pedido.service';

@Component({
  selector: 'app-pedido-list',
  templateUrl: './pedido-list.component.html',
  styleUrls: ['./pedido-list.component.css']
})
export class PedidoListComponent extends BaseResourceListComponent<Pedido>{

  constructor(
    protected pedidoService: PedidoService,
    protected injector: Injector
  ) {
    super(pedidoService, injector);
  }
}
