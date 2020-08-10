import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from 'src/app/shared/service/base-resource.service';
import { Cliente } from '../models/cliente.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ClienteService extends BaseResourceService<Cliente>{

  url = environment.URL_LOJA_SERVICE_API + '/cliente';

  constructor(protected injector: Injector) {
    super(environment.URL_LOJA_SERVICE_API + '/cliente', injector, Cliente.fromJson);
  }

  // findByDebitoId(id): Observable<ResponseApi> {
  //   return this.http.get(`${DIVIDAATIVA_API_DARE_DEBITO_ID}/${id}`).pipe(
  //     map(this.fromJsonResponseApi.bind(this)),
  //     catchError(this.handleError)
  //   )
  // }
}
