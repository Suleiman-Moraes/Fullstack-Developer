import toastr from "toastr";
import { MessageService, ConfirmationService } from 'primeng/api';
import { BaseResourceService } from 'src/app/shared/service/base-resource.service';
import { BaseResourceModel } from 'src/app/shared/models/base-resource.model';
import { OnInit, AfterContentChecked, Injector } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs/operators';
import { ResponseApi } from '../models/response-api.model';
import { BlockUI, NgBlockUI } from 'ng-block-ui';

export abstract class BaseResourceFormComponent<T extends BaseResourceModel> implements OnInit, AfterContentChecked {

    currentAction: string;
    pageTitle: string;
    resource: T;
    resourceForm: FormGroup;
    urlList: string = '/dividaativa';
    maxDate: Date = new Date();
    minDate: Date = new Date();
    tipos: string[] = ['pdf', 'doc', 'docx', 'png', 'PNG', 'jpg', 'xls'];
    @BlockUI() blockUI: NgBlockUI;

    protected route: ActivatedRoute;
    protected router: Router;
    protected formBuilder: FormBuilder;
    messageService: MessageService;
    // protected authenticationService: AuthenticationService;
    confirmationService: ConfirmationService;
    protected toast;

    constructor(
        protected injector: Injector,
        protected resourceService: BaseResourceService<T>,
        protected jsonDataToResourceFn: (jsonData: any) => T
    ) {
        this.route = this.injector.get(ActivatedRoute);
        this.router = this.injector.get(Router);
        this.formBuilder = this.injector.get(FormBuilder);
        this.messageService = this.injector.get(MessageService);
        // this.authenticationService = this.injector.get(AuthenticationService);
        this.confirmationService = this.injector.get(ConfirmationService);
        this.toast = toastr;
    }

    ptBR = {
        firstDayOfWeek: 0,
        dayNames: ["Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"],
        dayNamesShort: ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab"],
        dayNamesMin: ["Do", "Se", "Te", "Qu", "Qu", "Se", "Sa"],
        monthNames: ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"],
        monthNamesShort: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"],
        today: 'Hoje',
        clear: 'Limpar'
    };

    situacoes = {
        ATIVO: 'Ativo',
        INATIVO: 'Inativo'
    };

    /*checkDate(event: KeyboardEvent){
        if (event.code == 'Backspace') {
          return;
        }
        let diaEMes: string[] = new String(this.resourceForm.value.dataFeriadoFixo).split('/');
        let dia: number = new Number(diaEMes[0]).valueOf();
        let mes: number = new Number(diaEMes[1]).valueOf();
        if (mes > 12) {
          mes = 12;
        }
        if (dia > this.yearMap.get(mes)) {
          dia = this.yearMap.get(mes);
        }
        this.resourceForm.get('dataFeriadoFixo').setValue(dia.toString() + '/' + mes.toString())
      }
    }*/

    ngOnInit() {
        this.setCurrentAction();
        this.buildResourceForm();
        this.loadResource();
        this.posNgOnInit();
    }

    ngAfterContentChecked() {
        this.setPageTitle();
        this.posNgAfterContentChecked();
    }

    get situacaoOptions(): Array<any> {
        return this.getTypes(this.situacoes);
    }

    convertToNumber(string: string): number {
        return new Number(string).valueOf()
    }

    submitForm(): void {
        this.blockUI.start();
        Object.keys(this.resourceForm.controls).forEach(field => {
            const control = this.resourceForm.get(field);
            if (control instanceof FormControl) {
                control.markAsTouched({ onlySelf: true });
            }
        });
        this.beforeSubmitForm();
        this.resource = this.jsonDataToResourceFn(this.resourceForm.value);
        this.resourceService.enviarFormulario(this.resource, (this.resource.id != null && this.resource.id > 0)).subscribe(
            responseApi => {
                this.blockUI.stop();
                this.tratarResponseSubimit(responseApi);
            }, err => {
                this.blockUI.stop();
                this.tratarErro(err);
            }
        );
    }

    confirmDialog(message: string, header: string, icon: string): void {
        this.confirmationService.confirm({
            message: message,
            header: header,
            icon: icon,
            acceptLabel: 'Sim',
            rejectLabel: 'Não',
            accept: () => {
                this.acceptOrRejectConfirmDialog(true);
            },
            reject: () => {
                this.acceptOrRejectConfirmDialog(false);
            }
        });
    }

    pasteEventListen(event: ClipboardEvent) {
        let pasteValue = event.clipboardData.getData('Text');
        let observers: any[] = new Array();
        observers.push(this.mudaValue);

        observers.forEach(element => {
            element['listened'](pasteValue, this.resourceForm)
        });
    }

    inativarCampo(value: boolean, name: string, form: FormGroup): void {
        if (value) {
            form.get(name).setValue('Não Possui');
        }
        else {
            form.get(name).setValue(null);
        }
    }

    realizarParseDosCamposMoney(objeto) {
        if (objeto && (objeto + '').indexOf(',') > 0) {
            return Number((objeto + '').replace(/\./, '').replace(',', '.'));
        }
        return objeto;
    }

    isNotNulAndNotEmpty(x): boolean {
        return x && x != '';
    }

    download(filename, text) {
        var element = document.createElement('a');
        element.setAttribute('href', text);
        element.setAttribute('download', filename);
        element.style.display = 'none';
        document.body.appendChild(element);
        element.click();
        document.body.removeChild(element);
    }

    //PRIVATES METHODS
    protected setCurrentAction() {
        if (this.route.snapshot.url[0].path == "new") {
            this.currentAction = "new";
        }
        else {
            this.currentAction = "edit";
        }
    }

    protected setPageTitle() {
        if (this.currentAction == "new") {
            this.pageTitle = this.createPageTitle();
        }
        else {
            this.pageTitle = this.editionPageTitle();
        }
    }

    protected createPageTitle(): string {
        return 'Novo';
    }

    protected editionPageTitle(): string {
        return 'Edição';
    }

    protected showError(detail: string) {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: detail });
    }

    protected loadResource(): void {
        if (this.currentAction == 'edit') {
            let id: string = '';
            this.route.paramMap.pipe(
                switchMap(params => params.get('id'))
            ).subscribe(
                (param) => {
                    id += param;
                }
            );
            this.realizarRequisicaoSimples(this.resourceService.getById(Number(id)), 'resource', () => {
                if (this.resource.id == null) {
                    this.showError('Nenhum Registro encontrado.');
                }
                this.resourceForm.patchValue(this.resource);
                this.posLoadResource();
            });
        }
    }

    protected tratarErro(err: any): void {
        if (err.status != 400) {
            // this.erroServidor();
        }
        else {
            this.posTratarErro();
            Object.keys(this.resourceForm.controls).forEach(field => {
                const control = this.resourceForm.get(field);
                if (control instanceof FormControl) {
                    control.markAsTouched({ onlySelf: true });
                }
            });
            err.error.erros.forEach(x => {
                this.showError(x);
            });
        }
    }

    protected erroServidor(): void {
        // this.authenticationService.logout();
        toastr.error("Erro no servidor, tente novamente mais tarde");
        this.router.navigate(["login"]);
    }


    protected selecionarCombosDiversas(form: FormGroup, lista: any, id: number, controlName: string): void {
        lista.forEach((x: BaseResourceModel) => {
            if (x.id == id) {
                form.get(controlName).setValue(x);
                return;
            }
        });
    }

    protected getTypes(type: any): any {
        return Object.entries(type).map(
            ([value, text]) => {
                return {
                    text: text,
                    value: value
                }
            }
        );
    }

    protected tratarResponseSubimit(responseApi: ResponseApi): void {
        if (responseApi.data != null) {
            this.posSubmitFormSucesso();
        }
        else {
            responseApi.erros.forEach(x => {
                this.showError(x);
            });
        }
    }

    protected mudaValue = {
        listened(valorPaste: string, resourceForm) {
            let somenteNumeroELetra: boolean = valorPaste.replace(/[1234567890.,]+/, '').length == 0
            let valorOld = resourceForm.get('valor').value;
            let podeMudar: boolean = false;
            let result: string = "";
            if (somenteNumeroELetra) {
                valorPaste = valorPaste.replace('.', '');
                let valoresVirgula: string[] = valorPaste.split(',');
                result = valoresVirgula[0] + ',' + (valoresVirgula[1] != undefined ? valoresVirgula[1] : '00');
                for (let i = 2; i < valoresVirgula.length; i++) {
                    result += valoresVirgula[i];
                }
            }
            else {
                result = valorOld;
            }
            valorPaste = result;
            resourceForm.get('valor').setValue(result);
        },
    }

    protected formId(required?): FormGroup {
        return this.formBuilder.group({
            id: [null, (required ? Validators.required : null)]
        });
    }

    protected realizarRequisicaoSimples(metodo, atributo, func): void {
        metodo.subscribe(
            (responseApi: ResponseApi) => {
                if (responseApi.data == null) {
                    responseApi.erros.forEach(x => {
                        this.showError(x);
                    });
                }
                else {
                    this[atributo] = responseApi.data;
                    if (func != null) {
                        func();
                    }
                }
            }, err => {
                this.tratarErro(err);
            }
        );
    }

    //OPICIONAIS
    protected posLoadResource(): void { }
    protected posTratarErro(): void { }
    protected posNgOnInit(): void { }
    protected posNgAfterContentChecked(): void { }
    protected beforeSubmitForm(): void { }
    protected acceptOrRejectConfirmDialog(aceito: boolean): void { }


    //ABSTRACT
    protected abstract buildResourceForm(): void;
    protected abstract posSubmitFormSucesso(): void;
}
