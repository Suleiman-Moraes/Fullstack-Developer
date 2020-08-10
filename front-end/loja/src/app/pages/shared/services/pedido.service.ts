import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from 'src/app/shared/service/base-resource.service';
import { Pedido } from '../models/pedido.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PedidoService extends BaseResourceService<Pedido>{

  url = environment.URL_LOJA_SERVICE_API + '/pedido';

  constructor(protected injector: Injector) {
    super(environment.URL_LOJA_SERVICE_API + '/pedido', injector, Pedido.fromJson);
  }
}
