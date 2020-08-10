import { Component, OnInit, Injector } from '@angular/core';
import { BaseResourceListComponent } from 'src/app/shared/components/base-resource-list.component';
import { Cliente } from '../../shared/models/cliente.model';
import { ClienteService } from '../../shared/services/cliente.service';

@Component({
  selector: 'app-cliente-list',
  templateUrl: './cliente-list.component.html',
  styleUrls: ['./cliente-list.component.css']
})
export class ClienteListComponent extends BaseResourceListComponent<Cliente>{

  constructor(
    protected clienteService: ClienteService,
    protected injector: Injector
  ) {
    super(clienteService, injector);
  }
}
