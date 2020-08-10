import { Injectable, Injector } from '@angular/core';
import { environment } from 'src/environments/environment';
import { BaseResourceService } from 'src/app/shared/service/base-resource.service';
import { Produto } from '../models/produto.model';

@Injectable({
  providedIn: 'root'
})
export class ProdutoService extends BaseResourceService<Produto>{

  url = environment.URL_LOJA_SERVICE_API + '/produto';

  constructor(protected injector: Injector) {
    super(environment.URL_LOJA_SERVICE_API + '/produto', injector, Produto.fromJson);
  }
}
