import { NgModule } from '@angular/core';
import { PagesRoutingModule } from './pages-routing.module';
import { SharedModule } from '../shared/shared.module';
import { HomeComponent } from './home/home.component';
import { MessageService, ConfirmationService } from 'primeng/api';
import { NotFoundComponent } from './not-found/not-found.component';


@NgModule({
  declarations: [
    HomeComponent,
    NotFoundComponent
  ],
  imports: [
    SharedModule,
    PagesRoutingModule
  ],
  providers: [
    MessageService,
    ConfirmationService
  ]
})
export class PagesModule { }
