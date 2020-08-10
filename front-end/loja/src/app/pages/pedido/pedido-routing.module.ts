import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PedidoListComponent } from './pedido-list/pedido-list.component';
import { PedidoFormComponent } from './pedido-form/pedido-form.component';
import { AuthGuard } from '../security/auth.guard';


const routes: Routes = [
  { path: '', component: PedidoListComponent, canActivate: [AuthGuard] },
  { path: 'new', component: PedidoFormComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PedidoRoutingModule { }
