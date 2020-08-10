import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProdutoListComponent } from './produto-list/produto-list.component';
import { ProdutoFormComponent } from './produto-form/produto-form.component';
import { AuthGuard } from '../security/auth.guard';


const routes: Routes = [
  { path: '', component: ProdutoListComponent, canActivate: [AuthGuard] },
  { path: ':id/edit', component: ProdutoFormComponent, canActivate: [AuthGuard] },
  { path: 'new', component: ProdutoFormComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProdutoRoutingModule { }
