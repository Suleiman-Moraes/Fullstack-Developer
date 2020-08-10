import { Component, OnInit, Injector } from '@angular/core';
import { BaseResourceListComponent } from 'src/app/shared/components/base-resource-list.component';
import { Produto } from '../../shared/models/produto.model';
import { ProdutoService } from '../../shared/services/produto.service';

@Component({
  selector: 'app-produto-list',
  templateUrl: './produto-list.component.html',
  styleUrls: ['./produto-list.component.css']
})
export class ProdutoListComponent extends BaseResourceListComponent<Produto>{

  constructor(
    protected produtoService: ProdutoService,
    protected injector: Injector
  ) {
    super(produtoService, injector);
  }
}
