import { NgModule } from '@angular/core';
import { ClienteRoutingModule } from './cliente-routing.module';
import { ClienteFormComponent } from './cliente-form/cliente-form.component';
import { ClienteListComponent } from './cliente-list/cliente-list.component';
import { SharedModule } from 'src/app/shared/shared.module';


@NgModule({
  declarations: [
    ClienteFormComponent,
    ClienteListComponent
  ],
  imports: [
    SharedModule,
    ClienteRoutingModule
  ]
})
export class ClienteModule { }
