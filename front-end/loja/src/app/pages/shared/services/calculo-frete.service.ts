import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ResponseApi } from 'src/app/shared/models/response-api.model';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CalculoFreteService {

  url = environment.URL_CALCULOFRETE_SERVICE_API;

  constructor(
    private http: HttpClient
  ) { }

  calcularFrete(qtd): Observable<ResponseApi> {
    return this.http.get<ResponseApi>(
      `${this.url}/calculo/${qtd}`
    );
  }
}
