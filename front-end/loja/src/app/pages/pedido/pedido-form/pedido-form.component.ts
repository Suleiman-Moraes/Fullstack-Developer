import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from 'src/app/shared/components/base-resource-form.component';
import { Pedido } from '../../shared/models/pedido.model';
import { PedidoService } from '../../shared/services/pedido.service';
import { FormGroup, FormArray } from '@angular/forms';
import { ClienteService } from '../../shared/services/cliente.service';
import { ProdutoService } from '../../shared/services/produto.service';
import { Cliente } from '../../shared/models/cliente.model';
import { Produto } from '../../shared/models/produto.model';
import { CalculoFreteService } from '../../shared/services/calculo-frete.service';

@Component({
  selector: 'app-pedido-form',
  templateUrl: './pedido-form.component.html',
  styleUrls: ['./pedido-form.component.css']
})
export class PedidoFormComponent extends BaseResourceFormComponent<Pedido> {

  produto: Produto;
  clienteForm: FormGroup;
  produtos: Produto[];
  clientes: Cliente[]

  private frete = 0;

  constructor(
    protected injector: Injector,
    protected pedidoService: PedidoService,
    protected produtoService: ProdutoService,
    protected calculoFreteService: CalculoFreteService,
    protected clienteService: ClienteService
  ) {
    super(injector, pedidoService, Pedido.fromJson);
  }

  public get detalhePedidosFormArray(): FormArray {
    return this.resourceForm.get('detalhePedidos') as FormArray;
  }

  get detalhePedidoForm(): FormGroup {
    return this.formBuilder.group({
      id: [null],
      quantidade: [1],
      total: [null],
      valorEpoca: [null],
      produto: this.formBuilder.group({
        id: [null],
        nome: [null],
        precoUnitario: [null],
        imagemUrl: [null],
        codigo: [null]
      })
    });
  }

  get contemDetalhes(): boolean {
    return this.detalhePedidosFormArray && this.detalhePedidosFormArray.controls.length > 0;
  }

  selecionarProduto(event) {
    if (this.produto) {
      const value = this.produto;
      this.produto = null;
      let i: number = -1;
      new Promise((resolve, reject) => {
        this.detalhePedidosFormArray.controls.forEach((d, index) => {
          if (d.get('produto').value.id == value.id) {
            i = index;
            this.detalhePedidosFormArray.controls[i].get('quantidade').setValue(this.detalhePedidosFormArray.controls[i].value.quantidade + 1);
          }
        });
        resolve();
      }).then(() => {
        this.produto = null;
        if (i >= 0) {
          this.onBlurQuantidade(i);
        }
        else {
          let detalhePedidoForm: FormGroup = this.detalhePedidoForm;
          detalhePedidoForm.get('produto').patchValue(value);
          detalhePedidoForm.get('total').setValue(1 * value.precoUnitario);
          this.detalhePedidosFormArray.push(detalhePedidoForm);
          this.calcularValorItens();
        }
      });
    }
  }

  onBlurQuantidade(i): void {
    const qtd = this.detalhePedidosFormArray.controls[i].value.quantidade;
    if (!qtd || qtd <= 0) {
      this.removerDetalhePedido(i);
    }
    else {
      this.detalhePedidosFormArray.controls[i].get('total').setValue(qtd * this.detalhePedidosFormArray.controls[i].get('produto').value.precoUnitario);
      this.calcularValorItens();
    }
  }

  removerDetalhePedido(i): void {
    this.detalhePedidosFormArray.removeAt(i);
    this.calcularValorItens();
  }

  limpar(): void {
    let cliente = this.resourceForm.value.cliente;
    this.buildResourceForm();
    this.resourceForm.get('cliente').setValue(cliente);
  }

  //METHODS PRIVATE
  protected buildResourceForm(): void {
    this.clienteForm = this.formId();
    this.resourceForm = this.formBuilder.group({
      id: [null],
      itensQuantidadeTotal: [0],
      frete: [0],
      itensValorTotal: [0],
      total: [null],
      data: [null],
      usuarioId: [null],
      cliente: [null],
      detalhePedidos: this.formBuilder.array(new Array())
    });
  }

  protected posSubmitFormSucesso(): void {
    if (this.currentAction == 'new') {
      this.toast.success('Pedido criado com sucesso!');
    }
    else {
      this.toast.success('Pedido atualizado com sucesso!');
    }

    this.router.navigate([this.urlList]);
  }

  protected posNgOnInit(): void {
    this.urlList = '/pages/pedido';
    this.getRecursos();
  }

  private getRecursos(): void {
    this.realizarRequisicaoSimples(this.clienteService.getAll(), 'clientes', () => { });
    this.realizarRequisicaoSimples(this.produtoService.getAll(), 'produtos', () => { });
  }

  private calcularValorItens(): void {
    this.resourceForm.get('itensValorTotal').setValue(0);
    this.resourceForm.get('frete').setValue(0);
    this.resourceForm.get('itensQuantidadeTotal').setValue(0);
    if (this.contemDetalhes) {
      new Promise((resolve, reject) => {
        this.detalhePedidosFormArray.controls.forEach(detalhe => {
          this.resourceForm.get('itensValorTotal').setValue(this.resourceForm.value.itensValorTotal + detalhe.value.total);
          this.resourceForm.get('itensQuantidadeTotal').setValue(this.resourceForm.value.itensQuantidadeTotal + detalhe.value.quantidade);
        });
        resolve();
      }).then(() => {
        this.realizarRequisicaoSimples(this.calculoFreteService.calcularFrete(this.resourceForm.value.itensQuantidadeTotal), 'frete', () => {
          this.resourceForm.get('frete').setValue(this.frete);
        });
      });
    }
  }
}
