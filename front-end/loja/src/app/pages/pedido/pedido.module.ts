import { NgModule } from '@angular/core';
import { PedidoRoutingModule } from './pedido-routing.module';
import { PedidoFormComponent } from './pedido-form/pedido-form.component';
import { PedidoListComponent } from './pedido-list/pedido-list.component';
import { SharedModule } from 'src/app/shared/shared.module';


@NgModule({
  declarations: [
    PedidoFormComponent,
    PedidoListComponent
  ],
  imports: [
    SharedModule,
    PedidoRoutingModule
  ]
})
export class PedidoModule { }
