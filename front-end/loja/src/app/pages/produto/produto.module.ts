import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProdutoRoutingModule } from './produto-routing.module';
import { ProdutoFormComponent } from './produto-form/produto-form.component';
import { ProdutoListComponent } from './produto-list/produto-list.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { MessageService, ConfirmationService } from 'primeng/api';


@NgModule({
  declarations: [
    ProdutoFormComponent,
    ProdutoListComponent
  ],
  imports: [
    SharedModule,
    ProdutoRoutingModule
  ],
  providers: [
    MessageService,
    ConfirmationService
  ]
})
export class ProdutoModule { }
