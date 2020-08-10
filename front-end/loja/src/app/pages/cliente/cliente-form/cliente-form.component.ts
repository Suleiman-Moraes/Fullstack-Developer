import { Component, OnInit, Injector } from '@angular/core';
import { Cliente } from '../../shared/models/cliente.model';
import { BaseResourceFormComponent } from 'src/app/shared/components/base-resource-form.component';
import { ClienteService } from '../../shared/services/cliente.service';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-cliente-form',
  templateUrl: './cliente-form.component.html',
  styleUrls: ['./cliente-form.component.css']
})
export class ClienteFormComponent extends BaseResourceFormComponent<Cliente>{

  // user: Usuario = new Usuario();

  constructor(
    protected injector: Injector,
    protected clienteService: ClienteService
  ) {
    super(injector, clienteService, Cliente.fromJson);
  }

  //METHODS PRIVATE
  protected buildResourceForm(): void {
    this.resourceForm = this.formBuilder.group({
      id: [null],
      nome: [null, [Validators.required, Validators.maxLength(255)]],
      codigo: [null, [Validators.required, Validators.maxLength(255)]]
    });
  }

  protected posSubmitFormSucesso(): void {
    if (this.currentAction == 'new') {
      this.toast.success('Cliente criado com sucesso!');
    }
    else {
      this.toast.success('Cliente atualizado com sucesso!');
    }

    this.router.navigate([this.urlList]);
  }

  protected posNgOnInit(): void {
    this.urlList = '/pages/cliente';
    // this.user = this.authenticationService.currentUserValue.user;
    // if(!this.user.usuarioInterno){
    //   this.router.navigate(['404']);
    // }
  }

  protected createPageTitle(): string {
    return 'Novo Cliente';
  }

  protected editionPageTitle(): string {
    return 'Edição de Cliente';
  }
}
