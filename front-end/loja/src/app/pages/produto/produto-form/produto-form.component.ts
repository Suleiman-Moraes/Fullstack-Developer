import { Component, OnInit, Injector } from '@angular/core';
import { BaseResourceFormComponent } from 'src/app/shared/components/base-resource-form.component';
import { Produto } from '../../shared/models/produto.model';
import { ProdutoService } from '../../shared/services/produto.service';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-produto-form',
  templateUrl: './produto-form.component.html',
  styleUrls: ['./produto-form.component.css']
})
export class ProdutoFormComponent extends BaseResourceFormComponent<Produto>{

  // user: Usuario = new Usuario();

  constructor(
    protected injector: Injector,
    protected produtoService: ProdutoService
  ) {
    super(injector, produtoService, Produto.fromJson);
  }

  //METHODS PRIVATE
  protected buildResourceForm(): void {
    this.resourceForm = this.formBuilder.group({
      id: [null],
      nome: [null, [Validators.required, Validators.maxLength(255)]],
      precoUnitario: [null, [Validators.required]],
      imagemUrl: [null, [Validators.maxLength(255)]],
      codigo: [null, [Validators.required, Validators.maxLength(255)]]
    });
  }

  protected posSubmitFormSucesso(): void {
    if (this.currentAction == 'new') {
      this.toast.success('Produto criado com sucesso!');
    }
    else {
      this.toast.success('Produto atualizado com sucesso!');
    }

    this.router.navigate([this.urlList]);
  }

  protected posNgOnInit(): void {
    this.urlList = '/pages/produto';
    // this.user = this.authenticationService.currentUserValue.user;
    // if(!this.user.usuarioInterno){
    //   this.router.navigate(['404']);
    // }
  }

  protected createPageTitle(): string {
    return 'Novo Produto';
  }

  protected editionPageTitle(): string {
    return 'Edição de Produto';
  }
}
