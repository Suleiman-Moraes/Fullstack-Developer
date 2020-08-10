import { NgModule } from '@angular/core';
import { SecurityRoutingModule } from './security-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { LoginComponent } from './login/login.component';


@NgModule({
  declarations: [

  LoginComponent],
  imports: [
    SharedModule,
    SecurityRoutingModule
  ]
})
export class SecurityModule { }
