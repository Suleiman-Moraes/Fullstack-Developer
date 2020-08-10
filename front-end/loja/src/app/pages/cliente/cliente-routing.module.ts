import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ClienteListComponent } from './cliente-list/cliente-list.component';
import { ClienteFormComponent } from './cliente-form/cliente-form.component';
import { AuthGuard } from '../security/auth.guard';

const routes: Routes = [
  { path: '', component: ClienteListComponent, canActivate: [AuthGuard] },
  { path: ':id/edit', component: ClienteFormComponent, canActivate: [AuthGuard] },
  { path: 'new', component: ClienteFormComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClienteRoutingModule { }
