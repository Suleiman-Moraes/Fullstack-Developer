import { NgModule } from '@angular/core';
import { CommonModule, registerLocaleData } from '@angular/common';
import { FormFieldErrorComponent } from './components/form-field-error/form-field-error.component';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { PageHeaderComponent } from './components/page-header/page-header.component';
import { PaginatorModule } from 'primeng/paginator';
import { ToastModule } from 'primeng/toast';
import { HttpClientModule } from '@angular/common/http';
import { InputPadraoComponent } from './components/input-padrao/input-padrao.component';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { DropdownModule } from 'primeng/dropdown';
import { InputNumberModule } from 'primeng/inputnumber';
import localePt from '@angular/common/locales/pt';

registerLocaleData(localePt, 'pt');

@NgModule({
  declarations: [
    FormFieldErrorComponent,
    PageHeaderComponent,
    InputPadraoComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    PaginatorModule,
    ToastModule,
    HttpClientModule,
    CurrencyMaskModule,
    DropdownModule
  ],
  exports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    PaginatorModule,
    ToastModule,
    HttpClientModule,
    CurrencyMaskModule,
    DropdownModule,
    InputNumberModule,

    //shared components
    FormFieldErrorComponent,
    PageHeaderComponent,
    InputPadraoComponent
  ]
})
export class SharedModule { }
